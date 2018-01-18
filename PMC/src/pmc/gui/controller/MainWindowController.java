package pmc.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pmc.be.Movie;
import pmc.gui.model.MainWindowModel;

/**
 * En Gruppe
 * @author janvanzetten, Alex & Asbamz
 */
public class MainWindowController implements Initializable
{
    private final String VIEW_DIRECTORY = "/pmc/gui/view/";
    private final String VIEW_EXTENSION = ".fxml";
    private final String WINDOW_PRETEXT = "PMC - ";
    private final String LOGO_DIRECTORY = "pmc/gui/resources/logo.png";

    @FXML
    private MenuBar menubar;
    @FXML
    private StackPane stackPaneFiltering;
    @FXML
    private StackPane stackPaneMovieView;
    @FXML
    private TableView<Movie> tblviewMovies;
    @FXML
    private TableColumn<Movie, String> tblcolTitle;
    @FXML
    private TableColumn<Movie, String> tblcolGenre;
    @FXML
    private TableColumn<Movie, String> tblcolTime;
    @FXML
    private TableColumn<Movie, String> tblcolImdbRating;
    @FXML
    private TableColumn<Movie, String> tblcolPersonalRating;
    @FXML
    private TextField txtfldFilter;
    @FXML
    private Slider slrMinImdb;
    @FXML
    private Slider slrMinPersonal;
    @FXML
    private VBox genreVBox;

    private Scene deleteWindow;
    private MainWindowModel mainModel = new MainWindowModel();

    /**
     * Setup menubar for mac; table view; genre checkboxes; and initializes
     * delete popup window.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        mainModel.changeMenubarForMac(menubar, stackPaneFiltering, stackPaneMovieView);
        mainModel.initializeTableView(tblviewMovies, tblcolTitle, tblcolGenre, tblcolTime, tblcolImdbRating, tblcolPersonalRating);
        mainModel.initializeGenre(genreVBox);
        deleteWindow = mainModel.startDeletePopupWindow();
    }

    /**
     * Run delete popup window in case some movies is needed deleting. In which
     * case it also refresh the movie list.
     * @throws IOException
     */
    public void afterInitialization() throws IOException
    {
        if (deleteWindow != null)
        {
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setTitle(WINDOW_PRETEXT + "Are you gonna watch these?");
            newStage.getIcons().add(new Image(LOGO_DIRECTORY));
            newStage.setScene(deleteWindow);
            newStage.setMinWidth(620);
            newStage.setMinHeight(394);
            newStage.setMaximized(false);
            newStage.showAndWait();
        }
    }

    /**
     * Start new movie window on press.
     * @param event
     */
    @FXML
    private void handleNew(ActionEvent event)
    {
        mainModel.startNewMovieWindow();
    }

    /**
     * Shutdown program without errors.
     * @param event
     */
    @FXML
    private void handleQuit(ActionEvent event)
    {
        System.exit(0);
    }

    /**
     * Start edit movie window on press.
     * @param event
     */
    @FXML
    private void handleEdit(ActionEvent event)
    {
        mainModel.startEditMovieWindow();
    }

    /**
     * Delete selected movie on press.
     * @param event
     */
    @FXML
    private void handleDelete(ActionEvent event)
    {
        mainModel.deleteMovie();
    }

    /**
     * Start about window on press.
     * @param event
     */
    @FXML
    private void handleAbout(ActionEvent event)
    {
        mainModel.startModalWindow("AboutView", "About", 330, 310, false);
    }

    /**
     * Set min IMDb rating and run filtering.
     * @param event
     */
    @FXML
    private void handleMinImdb(MouseEvent event)
    {
        mainModel.setMinImdbRating(slrMinImdb.getValue());
        mainModel.addToFiltered();
    }

    /**
     * Set min personal rating and run filtering.
     * @param event
     */
    @FXML
    private void handleMinPersonal(MouseEvent event)
    {
        mainModel.setMinPersonalRating((int) slrMinPersonal.getValue());
        mainModel.addToFiltered();
    }

    /**
     * Set string for filter and run filtering.
     * @param event
     */
    @FXML
    private void handleTxtFilter(KeyEvent event)
    {
        mainModel.setFilterString(txtfldFilter.getText());
        mainModel.addToFiltered();
    }

    /**
     * Start new genre window on press.
     * @param event
     */
    @FXML
    private void handleNewGenre(ActionEvent event)
    {
        mainModel.newGenre();
    }

    /**
     * Delete genres, which is not in use, on press.
     * @param event
     */
    @FXML
    private void handleDeleteGenre(ActionEvent event)
    {
        mainModel.deleteUnusedGenres();
    }

    /**
     * Start settings window on press.
     * @param event
     */
    @FXML
    private void OpenSettings(ActionEvent event)
    {
        mainModel.openSettings();
    }
}
