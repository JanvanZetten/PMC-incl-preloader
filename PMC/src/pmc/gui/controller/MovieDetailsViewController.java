/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Alex
 */
public class MovieDetailsViewController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
