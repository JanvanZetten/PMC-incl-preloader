/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import pmc.be.ControllerSetup;

/**
 * FXML Controller class
 *
 * @author Asbamz
 */
public class EditMovieController implements Initializable, ControllerSetup
{
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
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
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
                Alert alertError = new Alert(Alert.AlertType.WARNING, "Not all values are set! " + ex.getMessage(), ButtonType.OK);
                alertError.showAndWait();
                return;
            }
            emm.setValues(txtfldName.getText(), yearInInt, txtfldDirectors.getText(), txtareaSummary.getText());
            emm.updateMovie(btnUpdate);
        }
        else
        {
            Alert alertError = new Alert(Alert.AlertType.WARNING, "Not all values are set!", ButtonType.OK);
            alertError.showAndWait();
        }
    }

    @Override
    public void setup(Stage thisStage, BLLManager bllManager)
    {
        emm = new EditMovieModel(bllManager);
        Movie movie = emm.getMovie();
        lblTopBar.setText("Edit - " + movie.getName());
        txtfldName.setText(movie.getName());
        txtfldYear.setText(String.valueOf(movie.getYear()));
        txtfldDirectors.setText(movie.getDirectors());
        txtareaSummary.setText(movie.getSummary());
        emm.initializeGenre(vboxGenre);
    }
}
