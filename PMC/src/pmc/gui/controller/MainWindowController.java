/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pmc.be.Movie;
import pmc.gui.model.MainModel;

/**
 *
 * @author janvanzetten
 */
public class MainWindowController implements Initializable
{

    @FXML
    private MenuBar Menubar;
    @FXML
    private StackPane stackPaneFiltering;
    @FXML
    private StackPane StackPaneMovieView;
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

    private MainModel mainModel = new MainModel();

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        mainModel.changeMenubarForMac(Menubar, stackPaneFiltering, StackPaneMovieView);

        tblcolTitle.setCellValueFactory(new PropertyValueFactory("name"));
        tblcolGenre.setCellValueFactory(new PropertyValueFactory("genres"));
        tblcolTime.setCellValueFactory((TableColumn.CellDataFeatures<Movie, String> param) ->
        {
            int duration = param.getValue().getDuration();
            int min = duration % 60;
            int hour = (duration - min) / 60;
            return new ReadOnlyObjectWrapper<>(hour + "t " + min + "min");
        });
        tblcolImdbRating.setCellValueFactory(new PropertyValueFactory("imdbRating"));
        tblcolPersonalRating.setCellValueFactory(new PropertyValueFactory("personalRating"));

        mainModel.addMovieToObsLst(new Movie("http://www.imdb.com/title/tt1570728/?ref_=nv_sr_1", "pmc/Movies/Guy runs into wall.mp4"));

        tblviewMovies.setItems(mainModel.getMovies());
    }

    @FXML
    private void handleAbout(ActionEvent event)
    {
        try
        {
            startModalWindow("About");
        }
        catch (IOException ex)
        {
            throw new RuntimeException("Could not open window!");
        }
    }

    /**
     * Starts a new window by sending in the name of the view in the parameters.
     */
    private void startModalWindow(String windowView) throws IOException
    {
        Stage newStage = new Stage();
        newStage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxLoader = new FXMLLoader(getClass().getResource("/pmc/gui/view/" + windowView + ".fxml"));
        Parent root = fxLoader.load();
        Scene scene = new Scene(root);

        newStage.setTitle("PMC - " + windowView);
        newStage.getIcons().add(new Image("pmc/gui/resources/logo.png"));
        newStage.setScene(scene);
        newStage.showAndWait();
    }
}
