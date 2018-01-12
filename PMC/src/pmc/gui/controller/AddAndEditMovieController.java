/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import pmc.gui.model.AddMovieModel;

/**
 * FXML Controller class
 *
 * @author janvanzetten
 */
public class AddAndEditMovieController implements Initializable
{

    @FXML
    private TextField textfieldPath;
    @FXML
    private ProgressBar pbLoading;
    @FXML
    private WebView WebView;

    private WebEngine webEngine;
    private AddMovieModel model;
    private Worker<Void> worker;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        model = new AddMovieModel();
        webEngine = WebView.getEngine();

        // A Worker load the page
        worker = webEngine.getLoadWorker();

        // Bind the progress property of ProgressBar
        // with progress property of Worker
        pbLoading.progressProperty().bind(worker.progressProperty());

        webEngine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<State>()
        {
            @Override
            public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue)
            {
                if (newValue == State.SUCCEEDED)
                {
                    pbLoading.setVisible(false);
                }
                else if (newValue == State.RUNNING)
                {
                    pbLoading.setVisible(true);
                }
            }
        });

        webEngine.load("http://www.imdb.com");
    }

    @FXML
    private void saveMovieAction(ActionEvent event)
    {
        String url = webEngine.getLocation();
        System.out.println(url);
        if (model.save(url))
        {
            Button button = (Button) event.getSource();
            Stage stage = (Stage) button.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void browseMovieFileAction(ActionEvent event) throws IOException
    {
        model.browseMovie(textfieldPath);
    }

    /**
     * Bind width of progress bar to stage width. Set topbar text.
     */
    public void setupStageDependant(Stage stage)
    {
        pbLoading.prefWidthProperty().bind(stage.widthProperty());

        // Listening to the status of worker
        worker.stateProperty().addListener(new ChangeListener<State>()
        {
            @Override
            public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue)
            {
                if (newValue == Worker.State.SUCCEEDED)
                {
                    stage.setTitle("PMC - " + webEngine.getLocation());
                }
            }
        });
    }
}
