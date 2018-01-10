/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.gui.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import pmc.gui.model.MovieDetailsModel;

/**
 * FXML Controller class
 *
 * @author Alex
 */
public class MovieDetailsController implements Initializable {

    @FXML
    private TextArea textareaDescription;
    @FXML
    private ImageView imageMoviePoster;
    @FXML
    private Label lblTitleAndYear;
    @FXML
    private Label lblGenres;
    @FXML
    private Label lblDirector;

    MovieDetailsModel model;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = new MovieDetailsModel();

        model.setImage(imageMoviePoster);
//        model.setTitleAndYear(lblTitleAndYear);
//        model.setDescription(textareaDescription);
//        model.setGenres(lblGenres);
//        model.setDirector(lblDirector);
    }

    @FXML
    private void handleButtonAction() throws IOException {
        String currentDir = System.getProperty("user.dir") + File.separator;
        File dir = new File(currentDir);
        System.out.println(dir + "\\Movies\\Guy runs into wall.mp4");
        Desktop.getDesktop().open(new File(dir + "\\Movies\\Guy runs into wall.mp4"));
        

    }

}
