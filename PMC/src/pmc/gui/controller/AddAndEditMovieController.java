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
import javafx.scene.web.WebView;

/**
 * FXML Controller class
 *
 * @author janvanzetten
 */
public class AddAndEditMovieController implements Initializable {

    @FXML
    private WebView WebView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void saveMovieAction(ActionEvent event) {
    }

    @FXML
    private void browseMovieFileAction(ActionEvent event) {
    }
    
}
