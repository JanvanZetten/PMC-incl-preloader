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
import pmc.be.Movie;
import pmc.gui.model.MainModel;

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

    private MainModel mainModel = new MainModel();

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        mainModel.changeMenubarForMac(menubar, stackPaneFiltering, stackPaneMovieView);
        //initializeTableView();
        mainModel.initializeTableView(tblviewMovies, tblcolTitle, tblcolGenre, tblcolTime, tblcolImdbRating, tblcolPersonalRating);
    }

//        FXMLLoader fxLoader = new FXMLLoader(getClass().getResource("/pmc/gui/view/MovieDetailsView.fxml"));
//        
//        MovieDetailsController cont = new FXMLLoader().getController();
//            
//            cont.setBLLManager(mainModel.getBLLManager());
//        Parent root = fxLoader.load();
//        Scene scene = new Scene(root);
//        
//        
//            
//
//        newStage.setTitle("PMC - ");
//        newStage.getIcons().add(new Image("pmc/gui/resources/logo.png"));
//        newStage.setScene(scene);
//        newStage.setMinWidth(620);
//        newStage.setMinHeight(394);
//        newStage.showAndWait();
    @FXML
    private void handleNew(ActionEvent event)
    {
        mainModel.newMovie();
    }

    @FXML
    private void handleQuit(ActionEvent event)
    {
        System.exit(0);
    }

    @FXML
    private void handleEdit(ActionEvent event)
    {
    }

    @FXML
    private void handleDelete(ActionEvent event)
    {
    }

    @FXML
    private void handleAbout(ActionEvent event)
    {
        try
        {
            mainModel.startModalWindow("About", 330, 310);
        }
        catch (IOException ex)
        {
            throw new RuntimeException("Could not open window!");
        }
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
}
