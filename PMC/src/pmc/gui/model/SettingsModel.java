package pmc.gui.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import pmc.be.Settings;
import pmc.bll.BLLException;
import pmc.bll.BLLManager;

/**
 * En Gruppe
 * @author Alex, Asbjørn & Jan
 */
public class SettingsModel
{
    private final String ITEM1 = "1 month";
    private final String ITEM2 = "2 months";
    private final String ITEM3 = "4 months";
    private final String ITEM4 = "8 months";
    private final String ITEM5 = "1 year";
    private final String ITEM6 = "2 years";
    private final String ITEM7 = "4 years";
    private final String ITEM8 = "Never";
    private final String ON_ERROR_COPYING = "Could not copy: ";
    private final String ON_ERROR_LOAD_LOCATION = "Could not load current location";
    private final String ON_ERROR_UNVALID_DIR = "Not a valid directory name";
    private final String MOVIE_DIR = "/Movies/";

    private ObservableList<String> obsIntervals;

    /**
     * Constructor sets up Observable List with options.
     */
    public SettingsModel()
    {
        obsIntervals = FXCollections.observableArrayList(ITEM1, ITEM2, ITEM3, ITEM4, ITEM5, ITEM6, ITEM7, ITEM8);
    }

    /**
     * Sets the UI
     * @param txtBxFolderLocation
     * @param cbbxInterval
     */
    public void setUI(TextField txtBxFolderLocation, ComboBox<String> cbbxInterval)
    {
        try
        {
            BLLManager bll = new BLLManager();
            txtBxFolderLocation.setText(bll.loadSettings().getMovieLocation());
            cbbxInterval.setItems(obsIntervals);
            cbbxInterval.getSelectionModel().select(ITEM6);
        }
        catch (BLLException ex)
        {
            Alert error = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            error.showAndWait();
        }
    }

    /**
     * Saves the settings from the textfield and the combobox. and makes a movie
     * directory if it is not existing
     *
     * @param txtBxFolderLocation
     * @param cbbxInterval
     */
    public void saveSettings(TextField txtBxFolderLocation, ComboBox<String> cbbxInterval)
    {
        BLLManager bll = new BLLManager();

        int interval;
        String movieLocation;
        String previousLocation = null;

        try
        {
            previousLocation = bll.loadSettings().getMovieLocation();
        }
        catch (BLLException ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ON_ERROR_LOAD_LOCATION, ButtonType.OK);
            alert.showAndWait();
        }

        movieLocation = txtBxFolderLocation.getText();

        //make a folder if it does not exist
        if (!Files.exists(Paths.get(movieLocation + MOVIE_DIR)))
        {
            if (!new File(movieLocation + MOVIE_DIR).mkdirs())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, ON_ERROR_UNVALID_DIR, ButtonType.OK);
                alert.showAndWait();
                return;
            }
            //copy files from previous location
        }

        //if it is a diffrent location then the previous copy all files from the previous location
        if (!previousLocation.equals(movieLocation))
        {
            if (!previousLocation.isEmpty())
            {
                if (Files.exists(Paths.get(previousLocation + MOVIE_DIR)))
                {
                    File directory2 = new File(previousLocation + MOVIE_DIR);
                    File[] movieFiles = directory2.listFiles();
                    for (File movieFile : movieFiles)
                    {
                        try
                        {
                            Files.copy(movieFile.toPath(), new File(movieLocation + MOVIE_DIR + movieFile.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                        }
                        catch (IOException ex)
                        {
                            Alert alert = new Alert(Alert.AlertType.ERROR, ON_ERROR_COPYING + movieFile.getName(), ButtonType.OK);
                            alert.showAndWait();
                        }
                    }
                }
            }
        }

        // Interval from selected combobox item.
        switch (cbbxInterval.getSelectionModel().getSelectedItem())
        {
            case ITEM1:
                interval = 1;
                break;
            case ITEM2:
                interval = 2;
                break;
            case ITEM3:
                interval = 4;
                break;
            case ITEM4:
                interval = 8;
                break;
            case ITEM5:
                interval = 12;
                break;
            case ITEM6:
                interval = 24;
                break;
            case ITEM7:
                interval = 48;
                break;
            default:
                interval = -1;
                break;
        }

        // Save settings to a file.
        try
        {
            bll.saveSettings(new Settings(interval, movieLocation));
        }
        catch (BLLException ex)
        {
            Alert error = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            error.showAndWait();
        }
    }

}
