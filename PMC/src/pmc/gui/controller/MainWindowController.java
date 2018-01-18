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
import javafx.scene.control.MenuBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import pmc.be.Movie;
import pmc.gui.model.MainWindowModel;

/**
 *
 * @author janvanzetten
 */
public class MainWindowController implements Initializable
{

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

    private MainWindowModel mainModel = new MainWindowModel();

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        mainModel.changeMenubarForMac(menubar, stackPaneFiltering, stackPaneMovieView);
        //initializeTableView();
        mainModel.initializeTableView(tblviewMovies, tblcolTitle, tblcolGenre, tblcolTime, tblcolImdbRating, tblcolPersonalRating);
        mainModel.initializeGenre(genreVBox);
    }

    /**
     *
     * @throws IOException
     */
    public void afterInitialization() throws IOException
    {
        mainModel.startDeletePopupWindow();
        mainModel.getAllMovies();
    }

    @FXML
    private void handleNew(ActionEvent event)
    {
        mainModel.startNewMovieWindow();
    }

    @FXML
    private void handleQuit(ActionEvent event)
    {
        System.exit(0);
    }

    @FXML
    private void handleEdit(ActionEvent event)
    {
        mainModel.startEditMovieWindow();
    }

    @FXML
    private void handleDelete(ActionEvent event)
    {
        mainModel.deleteMovie();
    }

    @FXML
    private void handleAbout(ActionEvent event)
    {
        mainModel.startModalWindow("AboutView", "About", 330, 310, false);
    }

    @FXML
    private void handleMinImdb(MouseEvent event)
    {
        mainModel.setMinImdbRating(slrMinImdb.getValue());
        mainModel.addToFiltered();
    }

    @FXML
    private void handleMinPersonal(MouseEvent event)
    {
        mainModel.setMinPersonalRating((int) slrMinPersonal.getValue());
        mainModel.addToFiltered();
    }

    @FXML
    private void handleTxtFilter(KeyEvent event)
    {
        mainModel.setFilterString(txtfldFilter.getText());
        mainModel.addToFiltered();
    }

    @FXML
    private void handleNewGenre(ActionEvent event)
    {
        mainModel.newGenre();
    }

    @FXML
    private void handleDeleteGenre(ActionEvent event)
    {
        mainModel.deleteUnusedGenres();
    }

    /**
     *
     * @param movie
     */
    public void updateTable(Movie movie)
    {
        mainModel.removeMovieFromTable(movie);
    }

    @FXML
    private void OpenSettings(ActionEvent event) {
        mainModel.openSettings();
    }
}
