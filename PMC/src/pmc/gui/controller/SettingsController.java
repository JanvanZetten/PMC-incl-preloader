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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pmc.bll.BLLManager;
import pmc.gui.model.SettingsModel;

/**
 * FXML Controller class
 *
 * @author janvanzetten
 */
public class SettingsController implements Initializable {

    @FXML
    private TextField TxtBxFolderLocation;
    @FXML
    private ComboBox<String> cbbxInterval;
    
    private SettingsModel model;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = new SettingsModel();
        model.setUI(TxtBxFolderLocation, cbbxInterval);
    }

    @FXML
    private void handleSave(ActionEvent event) {
        model.saveSettings(TxtBxFolderLocation, cbbxInterval);
        
    }

    @FXML
    private void handelCancel(ActionEvent event) {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

}
