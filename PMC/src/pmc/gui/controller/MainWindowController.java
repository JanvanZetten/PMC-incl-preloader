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
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import pmc.gui.model.MainModel;

/**
 *
 * @author janvanzetten
 */
public class MainWindowController implements Initializable {
    
    
    @FXML
    private MenuBar Menubar;
    @FXML
    private StackPane stackPaneFiltering;
    @FXML
    private StackPane StackPaneMovieView;
    
    private MainModel mainModel = new MainModel();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainModel.changeMenubarForMac(Menubar, stackPaneFiltering, StackPaneMovieView);
        
    }    
    
}
