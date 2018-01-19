package pmc.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import pmc.be.Movie;
import pmc.bll.BLLManager;
import pmc.dal.DALException;
import pmc.gui.model.AddMovieModel;
import pmc.gui.model.MainWindowModel;

/**
 * En Gruppe
 * @author Alex, Asbjørn & Jan
 */
public class AddMovieController implements Initializable, ControllerSetup
{
    @FXML
    private TextField textfieldPath;
    @FXML
    private ProgressBar pbLoading;
    @FXML
    private WebView webView;
    @FXML
    private Button btnSave;

    private WebEngine webEngine;
    private AddMovieModel model;
    private Worker<Void> worker;
    private MainWindowModel mainModel;

    /**
     * Initiates model. Setup web engine, binds progress bar to web engine
     * loader, and loaf IMDb.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            model = new AddMovieModel();
        }
        catch (DALException | SecurityException ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
            return;
        }
        webEngine = webView.getEngine();

        // A Worker load the page.
        worker = webEngine.getLoadWorker();

        // Bind the progress property of ProgressBar with progress property of Worker.
        pbLoading.progressProperty().bind(worker.progressProperty());

        // Hide the progress bar when the site is not loading.
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

        btnSave.setDisable(true);

        webEngine.load("http://www.imdb.com");
    }

    /**
     * Button action which execute the save method in model. If the movie was
     * saved the window is closed.
     *
     * @param event
     */
    @FXML
    private void saveMovieAction(ActionEvent event)
    {
        String url = webEngine.getLocation();

        Movie newMovie = model.save(url, mainModel);

        if (newMovie != null)
        {
            mainModel.addMovieToObsLst(newMovie);
            Button button = (Button) event.getSource();
            Stage stage = (Stage) button.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Open file chooser on press.
     * @param event
     * @throws IOException
     */
    @FXML
    private void browseMovieFileAction(ActionEvent event) throws IOException
    {
        model.browseMovie(textfieldPath);
        isAllDataSet();
    }

    /**
     * Checks if needed data, for saving, is set.
     */
    private void isAllDataSet()
    {
        if (webEngine.getLocation().toLowerCase().contains("imdb.com/title/") && model.fileSelected())
        {
            btnSave.setDisable(false);
        }
    }

    /**
     * Bind width of progress bar to the stage width. Sets topbar text.
     * @param mainWindowModel
     */
    @Override
    public void setup(Stage thisStage, MainWindowModel mainWindowModel, BLLManager bllManager)
    {
        mainModel = mainWindowModel;

        pbLoading.prefWidthProperty().bind(thisStage.widthProperty());

        // Save button is only active if the the webview is in a IMDb Movie site.
        worker.stateProperty().addListener(new ChangeListener<State>()
        {
            @Override
            public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue)
            {
                if (newValue == Worker.State.SUCCEEDED)
                {
                    thisStage.setTitle("PMC - " + webEngine.getLocation());
                    isAllDataSet();
                }
                else
                {
                    btnSave.setDisable(true);
                }
            }
        });
    }
}
