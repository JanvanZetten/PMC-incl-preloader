/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author janvanzetten
 */
public class SettingsModel
{

    private ObservableList<String> ObsIntervals;

    public SettingsModel()
    {
        ObsIntervals = FXCollections.observableArrayList("1 month", "2 months", "4 months", "8 months", "1 year", "2 years", "4 years", "Never");
    }

    /**
     * sets the UI
     * @param TxtBxFolderLocation
     * @param cbbxInterval
     */
    public void setUI(TextField TxtBxFolderLocation, ComboBox<String> cbbxInterval)
    {
        try
        {
            BLLManager bll = new BLLManager();
            TxtBxFolderLocation.setText(bll.loadSettings().getMovieLocation());
            cbbxInterval.setItems(ObsIntervals);
            cbbxInterval.getSelectionModel().select("2 years");
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
     * @param TxtBxFolderLocation
     * @param cbbxInterval
     */
    public void saveSettings(TextField TxtBxFolderLocation, ComboBox<String> cbbxInterval)
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
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load current location", ButtonType.OK);
            alert.showAndWait();
        }

        movieLocation = TxtBxFolderLocation.getText();

        if (!new File(movieLocation).exists())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Not a valid directory name", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        //Cheks if movie folder exists
        boolean moviefolderExists = false;
        File directory = new File(movieLocation);
        File[] subdirs = directory.listFiles();
        for (File dir : subdirs)
        {
            if (dir.getName().equals("Movies"))
            {
                moviefolderExists = true;
            }
        }

        //make a folder if it does not exist
        if (!moviefolderExists)
        {
            new File(movieLocation + File.pathSeparator + "Movies").mkdirs();
            //copy files from previous location
        }

        //if it is a diffrent location then the previous copy all files from the previous location
        if (!previousLocation.equals(movieLocation))
        {
            if (!previousLocation.isEmpty())
            {
                if (Files.exists(Paths.get(previousLocation)))
                {
                    File directory2 = new File(previousLocation + File.separator + "Movies");
                    File[] movieFiles = directory2.listFiles();
                    for (File movieFile : movieFiles)
                    {
                        try
                        {
                            Files.copy(movieFile.toPath(), new File(movieLocation + File.separator + "Movies" + File.separator + movieFile.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                        }
                        catch (IOException ex)
                        {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not copy:" + movieFile.getName(), ButtonType.OK);
                            alert.showAndWait();
                        }
                    }
                }
            }
        }

        switch (cbbxInterval.getSelectionModel().getSelectedItem())
        {
            case "1 month":
                interval = 1;
                break;
            case "2 months":
                interval = 2;
                break;
            case "4 months":
                interval = 4;
                break;
            case "8 months":
                interval = 8;
                break;
            case "1 year":
                interval = 12;
                break;
            case "2 years":
                interval = 24;
                break;
            case "4 years":
                interval = 48;
                break;
            default:
                interval = -1;
                break;
        }

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
