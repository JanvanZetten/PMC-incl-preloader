package pmc.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pmc.be.Movie;
import pmc.bll.BLLManager;
import pmc.gui.model.EditMovieModel;
import pmc.gui.model.MainWindowModel;

/**
 * En Gruppe
 * @author Alex, Asbjørn & Jan
 */
public class EditMovieController implements Initializable, ControllerSetup
{
    private final String WINDOW_NAME_PREFIX = "Edit - ";
    private final String ON_WARNING_NOT_ALL_SET = "Not all values are set! ";

    @FXML
    private Label lblTopBar;
    @FXML
    private TextField txtfldName;
    @FXML
    private TextField txtfldYear;
    @FXML
    private TextField txtfldDirectors;
    @FXML
    private VBox vboxGenre;
    @FXML
    private TextArea txtareaSummary;
    @FXML
    private Button btnUpdate;

    private EditMovieModel emm;

    /**
     * Does nothing.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
    }

    /**
     * Run on action btnUpdate. Check all fields and run emm setValues and
     * updateMovie.
     * @param event
     */
    @FXML
    private void handleUpdate(ActionEvent event)
    {
        if ((txtfldName.getText() != null && !txtfldName.getText().equalsIgnoreCase(""))
                && (txtfldYear.getText() != null && !txtfldYear.getText().equalsIgnoreCase(""))
                && (txtfldDirectors.getText() != null && !txtfldDirectors.getText().equalsIgnoreCase("")))
        {
            int yearInInt;
            try
            {
                yearInInt = Integer.parseInt(txtfldYear.getText());
            }
            catch (NumberFormatException ex)
            {
                Alert alertError = new Alert(Alert.AlertType.WARNING, ON_WARNING_NOT_ALL_SET + ex.getMessage(), ButtonType.OK);
                alertError.showAndWait();
                return;
            }
            emm.setValues(txtfldName.getText(), yearInInt, txtfldDirectors.getText(), txtareaSummary.getText());
            emm.updateMovie(btnUpdate);
        }
        else
        {
            Alert alertError = new Alert(Alert.AlertType.WARNING, ON_WARNING_NOT_ALL_SET, ButtonType.OK);
            alertError.showAndWait();
        }
    }

    /**
     * Load information about movie.
     * @param thisStage
     * @param mainWindowModel
     * @param bllManager
     */
    @Override
    public void setup(Stage thisStage, MainWindowModel mainWindowModel, BLLManager bllManager)
    {
        emm = new EditMovieModel(bllManager);
        Movie movie = emm.getMovie();
        lblTopBar.setText(WINDOW_NAME_PREFIX + movie.getName());
        txtfldName.setText(movie.getName());
        txtfldYear.setText(String.valueOf(movie.getYear()));
        txtfldDirectors.setText(movie.getDirectors());
        txtareaSummary.setText(movie.getSummary());
        emm.initializeGenre(vboxGenre);
    }
}
