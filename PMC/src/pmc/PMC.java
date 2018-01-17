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

import pmc.bll.BLLManager;

/**
 *
 * @author janvanzetten
 */
public class PMC extends Application
{
    private Scene scene;

    /**
     * this does the slow stuff because of preloader
     * @throws Exception
     */
    @Override
    public void init() throws Exception 
    {
            Parent root = FXMLLoader.load(getClass().getResource("gui/view/MainWindowView.fxml"));
            scene = new Scene(root);
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
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

}
