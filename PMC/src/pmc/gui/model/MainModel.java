/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.gui.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pmc.be.Genre;
import pmc.be.IMDbMovieFilter;
import pmc.be.Movie;
import pmc.be.MovieFilter;
import pmc.be.PersonalMovieFilter;
import pmc.bll.BLLManager;

/**
 *
 * @author janvanzetten
 */
public class MainModel
{
    private ObservableList<Movie> movies;
    private ObservableList<Movie> filteredMovies;
    private double minImdbRating;
    private int minPersonalRating;
    private String filterString;

    private BLLManager bllManager;

    public MainModel()
    {
        bllManager = new BLLManager();
        this.movies = FXCollections.observableArrayList();
        this.filteredMovies = FXCollections.observableArrayList();
        minImdbRating = 0.0;
        minPersonalRating = 0;
        filterString = "";
    }

    /**
     * Add one movie to the movies Observable List.
     * @param movie to add.
     */
    public void addMovieToObsLst(Movie movie)
    {
        movies.add(movie);
        addToFiltered();
    }

    /**
     * Change the movies Observable List to given list.
     * @param movies list to change to.
     */
    public void changeMoviesInObsLst(List<Movie> movies)
    {
        this.movies.clear();
        this.movies.addAll(movies);
        addToFiltered();
    }

    /**
     * Get movies Observable List.
     * @return movies Observable List.
     */
    public ObservableList<Movie> getFilteredMovies()
    {
        return filteredMovies;
    }

    public void addToFiltered()
    {
        List<MovieFilter> movieFilters = new ArrayList<>();
        MovieFilter imdbMovieFilter = new IMDbMovieFilter(0.0, minImdbRating);;
        MovieFilter personalMovieFilter = new PersonalMovieFilter(0, minPersonalRating);;

        movieFilters.add(imdbMovieFilter);
        movieFilters.add(personalMovieFilter);

        filteredMovies.clear();

        for (Movie movie : movies)
        {
            movieFilters.set(0, new IMDbMovieFilter(movie.getImdbRating(), minImdbRating));
            movieFilters.set(1, new PersonalMovieFilter(movie.getPersonalRating(), minPersonalRating));

            int meetsRestrictions = 0;
            for (MovieFilter movieFilter : movieFilters)
            {
                if (movieFilter.meetsRestrictions())
                {
                    meetsRestrictions++;
                }
            }

            if (meetsRestrictions == movieFilters.size())
            {
                if (filterString.equalsIgnoreCase("") || filterString == null)
                {
                    filteredMovies.add(movie);
                }
                else
                {
                    String genres = "";
                    if (movie.getGenres() != null)
                    {
                        for (Genre genre : movie.getGenres())
                        {
                            if (genres.equalsIgnoreCase(""))
                            {
                                genres = genre.getName();
                            }
                            else
                            {
                                genres += genre.getName();
                            }
                        }
                    }

                    for (String string : filterString.split(" "))
                    {
                        if (movie.getName().toLowerCase().contains(string.toLowerCase())
                                || String.valueOf(movie.getYear()).toLowerCase().contains(string.toLowerCase())
                                || genres.toLowerCase().contains(string.toLowerCase())
                                || movie.getDirectors().toLowerCase().contains(string.toLowerCase()))
                        {
                            filteredMovies.add(movie);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void setFilterString(String filterString)
    {
        this.filterString = filterString;
    }

    public void setMinImdbRating(double minImdbRating)
    {
        this.minImdbRating = minImdbRating;
    }

    public void setMinPersonalRating(int minPersonalRating)
    {
        this.minPersonalRating = minPersonalRating;
    }

    /**
     * Makes the Menubar look nice on Mac. if it is a Mac the Menubar is placed
     * on the where top of the screen where it normally is placed on a Mac. it
     * then takes the stackpanes and sets the top anchor to 0 for avoiding empty
     * space
     * @param menubar
     * @param stackPaneFiltering
     * @param stackPaneMovieView
     */
    public void changeMenubarForMac(MenuBar menubar, StackPane stackPaneFiltering, StackPane stackPaneMovieView)
    {
        if (System.getProperty("os.name").startsWith("Mac"))
        {
            menubar.useSystemMenuBarProperty().set(true);
            menubar.setMinHeight(0.0);
            menubar.setPrefHeight(0.0);
            menubar.setMaxHeight(0.0);
            AnchorPane.setTopAnchor(stackPaneFiltering, 0.0);
            AnchorPane.setTopAnchor(stackPaneMovieView, 0.0);
        }
    }

    public void setCurrentMovie(Movie currentMovie)
    {
        bllManager.setCurrentMovie(currentMovie);
    }

    public void initializeTableView(TableView<Movie> tblviewMovies, TableColumn<Movie, String> tblcolTitle, TableColumn<Movie, String> tblcolGenre, TableColumn<Movie, String> tblcolTime, TableColumn<Movie, String> tblcolImdbRating, TableColumn<Movie, String> tblcolPersonalRating) {
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
                    setCurrentMovie(currentMovie);
                    handleMovieDetails();
                }
                else if (event.getClickCount() == 1 && (!row.isEmpty()))
                {
                    setCurrentMovie(row.getItem());
                }
            });
            return row;
        });


        // Set Observable List.
        tblviewMovies.setItems(filteredMovies);
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
    
    public BLLManager getBLLManager() {
        return bllManager;
    }
}
