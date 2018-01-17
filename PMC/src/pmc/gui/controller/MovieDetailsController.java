/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pmc.bll.BLLManager;

import pmc.gui.model.MovieDetailsModel;

/**
 * FXML Controller class
 *
 * @author Alex
 */
public class MovieDetailsController implements Initializable
{

    @FXML
    private TextArea textareaDescription;
    @FXML
    private ImageView imageMoviePoster;
    @FXML
    private ImageView imageRatingStar;
    @FXML
    private Label lblTitleAndYear;
    @FXML
    private Label lblGenres;
    @FXML
    private Label lblDirector;
    private Label lblScore;
    @FXML
    private Button btnCopyLink;
    @FXML
    private Button btnClose;
    @FXML
    private Button WatchMovie;

    MovieDetailsModel model;
    @FXML
    private Label lblImdb;
    @FXML
    private Label lblPersonal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        model = new MovieDetailsModel();
    }

    public void setElements(BLLManager bllManager)
    {
        model.setBLLManager(bllManager);
        model.determineIMDbLink(btnCopyLink);
        model.setPosterImage(imageMoviePoster);
        model.setRatingImage(imageRatingStar);
        model.setDescription(textareaDescription);
        model.setTitleAndYear(lblTitleAndYear);
        model.setGenres(lblGenres);
        model.setDirector(lblDirector);
        model.setScore(lblImdb, lblPersonal);
        
    }

    //Plays the movie selected.
    @FXML
    private void watchMovieAction() throws IOException
    {
        model.playMovie();
        //model.setNewLastView();
    }

    @FXML
    private void closeWindowAction()
    {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void getLinkAction()
    {
        model.setClipboard();
        btnCopyLink.setText("Copied to clipboard");
    }

}
