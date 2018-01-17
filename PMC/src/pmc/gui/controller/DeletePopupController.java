/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import pmc.bll.BLLManager;
import pmc.gui.model.DeletePopupModel;
import pmc.gui.model.DeletePopupModel.HBoxCell;

/**
 * FXML Controller class
 *
 * @author Alex
 */
public class DeletePopupController implements Initializable {

    @FXML
    private ListView<HBoxCell> tblMovies;
    
    DeletePopupModel model;
    @FXML
    private Button btnDone;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = new DeletePopupModel();
        
    }
    
    public void setup()
    {
        
        model.setList(tblMovies);
    }

    @FXML
    private void handleDoneAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnDone.getScene().getWindow();
        stage.close();
        
        
    }

    public void setBLLManager(BLLManager bllManager) {
        model.setBLLManager(bllManager);
    }
}
