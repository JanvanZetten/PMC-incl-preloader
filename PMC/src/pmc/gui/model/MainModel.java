/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.gui.model;

import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author janvanzetten
 */
public class MainModel {

    /**
     * Makes the Menubar look nice on Mac.
     * if it is a Mac the Menubar is placed on the where top of the screen where it normally is placed on a Mac. 
     * it then takes the stackpanes and sets the top anchor to 0 for avoiding empty space
     * @param Menubar
     * @param stackPaneFiltering
     * @param StackPaneMovieView 
     */
    public void changeMenubarForMac(MenuBar Menubar, StackPane stackPaneFiltering, StackPane StackPaneMovieView) {
        if (System.getProperty("os.name").startsWith("Mac")){
            Menubar.useSystemMenuBarProperty().set(true);
            AnchorPane.setTopAnchor(stackPaneFiltering,0.0);
            AnchorPane.setTopAnchor(StackPaneMovieView,0.0);
        }
    }
    
}
