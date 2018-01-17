/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.gui.model;

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
public class SettingsModel {
    
    
    private ObservableList<String> ObsIntervals;

    public SettingsModel() {
        ObsIntervals = FXCollections.observableArrayList("1 month", "2 months", "4 months", "8 months", "1 year", "2 years", "4 years", "Never");
    }
    
    
    public void setUI(TextField TxtBxFolderLocation, ComboBox<String> cbbxInterval){
        try {
            BLLManager bll = new BLLManager();
            TxtBxFolderLocation.setText(bll.loadSettings().getMovieLocation());
            cbbxInterval.setItems(ObsIntervals);
            cbbxInterval.getSelectionModel().select("2 years");
        } catch (BLLException ex) {
            Alert error = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            error.showAndWait();
        }
    }
    
    public void saveSettings(TextField TxtBxFolderLocation, ComboBox<String> cbbxInterval){
        BLLManager bll = new BLLManager();
        int interval;
        String movieLocation;
        
        movieLocation = TxtBxFolderLocation.getText();
        
        switch (cbbxInterval.getSelectionModel().getSelectedItem()){
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
        
        try {
            bll.saveSettings(new Settings(interval, movieLocation));
        } catch (BLLException ex) {
            Alert error = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            error.showAndWait();
        }
    }
    
}
