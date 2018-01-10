/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pmc.be.Genre;
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
    private Slider slrMinImdb;
    @FXML
    private Slider slrMinPersonal;

    private MainModel mainModel = new MainModel();

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        mainModel.changeMenubarForMac(menubar, stackPaneFiltering, stackPaneMovieView);
        initializeTableView();
    }

    /**
     * Setup TableView.
     */
    private void initializeTableView()
    {
        // Set values for Table Cells.
        tblcolTitle.setCellValueFactory(new PropertyValueFactory("name"));
        tblcolGenre.setCellValueFactory((TableColumn.CellDataFeatures<Movie, String> param) ->
        {
            List<Genre> gs = param.getValue().getGenres();
            String txt = "";
            if (gs != null)
            {
                for (Genre g : gs)
                {
                    if (txt.equalsIgnoreCase(""))
                    {
                        txt = g.getName();
                    }
                    else
                    {
                        txt += ", " + g.getName();
                    }
                }
            }
            return new ReadOnlyObjectWrapper<>(txt);
        });
        tblcolTime.setCellValueFactory((TableColumn.CellDataFeatures<Movie, String> param) ->
        {
            int duration = param.getValue().getDuration();
            int min = duration % 60;
            int hour = (duration - min) / 60;
            if (min < 10)
            {
                return new ReadOnlyObjectWrapper<>(hour + "t 0" + min + "min");
            }
            return new ReadOnlyObjectWrapper<>(hour + "t " + min + "min");
        });
        tblcolTime.setStyle("-fx-alignment: CENTER-RIGHT;");
        tblcolImdbRating.setCellValueFactory(new PropertyValueFactory("imdbRating"));
        tblcolImdbRating.setStyle("-fx-alignment: CENTER;");
        tblcolPersonalRating.setCellValueFactory(new PropertyValueFactory("personalRating"));
        tblcolPersonalRating.setStyle("-fx-alignment: CENTER;");

        // Set doubleclick on row.
        tblviewMovies.setRowFactory(tv ->
        {
            TableRow<Movie> row = new TableRow<>();
            row.setOnMouseClicked(event ->
            {
                if (event.getClickCount() == 2 && (!row.isEmpty()))
                {
                    Movie currentMovie = row.getItem();
                    mainModel.setCurrentMovie(currentMovie);
                    System.out.println(currentMovie);
                    handleMovieDetails();
                    
                }
            });
            return row;
        });

        // Add test Movie.
        mainModel.addMovieToObsLst(new Movie("http://www.imdb.com/title/tt0468569/", "pmc/Movies/Guy runs into wall.mp4"));
        mainModel.addMovieToObsLst(new Movie("http://www.imdb.com/title/tt1345836/", "pmc/Movies/Guy runs into wall.mp4"));
        mainModel.addMovieToObsLst(new Movie("http://www.imdb.com/title/tt0097965/", "pmc/Movies/Guy runs into wall.mp4"));
        mainModel.addMovieToObsLst(new Movie("http://www.imdb.com/title/tt1570728/?ref_=nv_sr_1", "pmc/Movies/Guy runs into wall.mp4"));

        // Set Observable List.
        tblviewMovies.setItems(mainModel.getFilteredMovies());
    }

    private void handleMovieDetails()
    {
        try
        {
            startModalWindow("MovieDetailsView", 620, 394);
        }
        catch (IOException ex)
        {
            throw new RuntimeException("Could not open window!");
        }
    }

    @FXML
    private void handleNew(ActionEvent event)
    {
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
            startModalWindow("About", 330, 310);
        }
        catch (IOException ex)
        {
            throw new RuntimeException("Could not open window!");
        }
    }

    /**
     * Starts a new window by sending in the name of the view in the parameters.
     */
    private void startModalWindow(String windowView, int minWidth, int minHeight) throws IOException
    {
        Stage newStage = new Stage();
        newStage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxLoader = new FXMLLoader(getClass().getResource("/pmc/gui/view/" + windowView + ".fxml"));
        Parent root = fxLoader.load();
        Scene scene = new Scene(root);

        newStage.setTitle("PMC - " + windowView);
        newStage.getIcons().add(new Image("pmc/gui/resources/logo.png"));
        newStage.setScene(scene);
        newStage.setMinWidth(minWidth);
        newStage.setMinHeight(minHeight);
        newStage.showAndWait();
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
}
