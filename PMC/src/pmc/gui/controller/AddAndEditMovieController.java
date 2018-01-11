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
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import pmc.gui.model.addSongModel;

/**
 * FXML Controller class
 *
 * @author janvanzetten
 */
public class AddAndEditMovieController implements Initializable {

    @FXML
    private WebView WebView;
    
    WebEngine webEngine;
    String fileLocation;
    addSongModel model;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        webEngine = WebView.getEngine();
        webEngine.load("http://www.imdb.com");
    }    
    
    @FXML
    private void saveMovieAction(ActionEvent event) {
        String url = webEngine.getLocation();
        System.out.println(url);
        if (model.save(url)){
            Button button = (Button) event.getSource();
            Stage stage = (Stage) button.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void browseMovieFileAction(ActionEvent event) {
        model.browseMovie();
    }
    
}
