/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pmc.gui.controller.MainWindowController;

/**
 *En Gruppe
 * @author Alex, Asbjørn & Jan
 */
public class PMC extends Application
{
    private Scene scene;
    private MainWindowController cont;

    /**
     * this does the slow stuff because of preloader
     * @throws Exception
     */
    @Override
    public void init() throws Exception
    {
        FXMLLoader fxLoader = new FXMLLoader(getClass().getResource("gui/view/MainWindowView.fxml"));
        Parent root = fxLoader.load();
        scene = new Scene(root);

        cont = fxLoader.getController();
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setScene(scene);
        stage.setTitle("PMC");
        stage.getIcons().add(new Image("pmc/gui/resources/logo.png"));
        stage.setMinWidth(620);
        stage.setMinHeight(420);
        stage.show();
        cont.afterInitialization();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

}
