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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author janvanzetten
 */
public class SettingsController implements Initializable {

    @FXML
    private TextField TxtBxFolderLocation;
    @FXML
    private ComboBox<?> cbbxInterval;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleSave(ActionEvent event) {
    }

    @FXML
    private void handelCancel(ActionEvent event) {
    }
    
}
