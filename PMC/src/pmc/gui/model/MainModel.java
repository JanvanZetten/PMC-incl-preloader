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
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
import pmc.bll.BLLException;
import pmc.bll.BLLManager;
import pmc.gui.controller.AddAndEditMovieController;
import pmc.gui.controller.MovieDetailsController;

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

    /**
     * setup of the tableview
     * @param tblviewMovies the table
     * @param tblcolTitle first colon
     * @param tblcolGenre second colon
     * @param tblcolTime third colon
     * @param tblcolImdbRating fourth colon
     * @param tblcolPersonalRating fifth colon
     */
    public void initializeTableView(TableView<Movie> tblviewMovies, TableColumn<Movie, String> tblcolTitle, TableColumn<Movie, String> tblcolGenre, TableColumn<Movie, String> tblcolTime, TableColumn<Movie, String> tblcolImdbRating, TableColumn<Movie, String> tblcolPersonalRating)
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
        getAllMovies();
    }

    private void handleMovieDetails()
    {
        try
        {
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);

            FXMLLoader fxLoader = new FXMLLoader(getClass().getResource("/pmc/gui/view/MovieDetailsView.fxml"));

            Parent root = fxLoader.load();

            MovieDetailsController cont = fxLoader.getController();

            cont.setBLLManager(getBLLManager());

            cont.setElements();

            Scene scene = new Scene(root);

            newStage.setTitle("PMC - Movie Details");
            newStage.getIcons().add(new Image("pmc/gui/resources/logo.png"));
            newStage.setScene(scene);
            newStage.setMinWidth(620);
            newStage.setMinHeight(394);

            newStage.showAndWait();

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
//            Alert alert = new Alert(Alert.AlertType.WARNING, "error" + ex.getMessage(), ButtonType.OK);
//            alert.showAndWait();
        }
    }

    /**
     * Starts a new window by sending in the name of the view in the parameters.
     */
    public void startModalWindow(String windowView, int minWidth, int minHeight) throws IOException
    {
        Stage newStage = new Stage();
        newStage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxLoader = new FXMLLoader(getClass().getResource("/pmc/gui/view/" + windowView + ".fxml"));
        Parent root = fxLoader.load();
        Scene scene = new Scene(root);

        newStage.setTitle("PMC");
        newStage.getIcons().add(new Image("pmc/gui/resources/logo.png"));
        newStage.setScene(scene);
        newStage.setMinWidth(minWidth);
        newStage.setMinHeight(minHeight);
        newStage.showAndWait();
    }

    /**
     * Gets all the movies and stores them in the movie list and adds them to
     * the filtered list. if error shows it will show an alert message.
     */
    private void getAllMovies()
    {
        try
        {
            changeMoviesInObsLst(bllManager.getAllMovies());
        }
        catch (BLLException ex)
        {
            System.out.println("error: Check database Connection!!");
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING, "Could not load information,\n check connecetion to database\n message: " + ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    public BLLManager getBLLManager()
    {
        return bllManager;
    }

    /**
     * opens the window for adding a new movie
     */
    public void newMovie()
    {
        try
        {

            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader fxLoader = new FXMLLoader(getClass().getResource("/pmc/gui/view/AddAndEditMovieView.fxml"));
            Parent root = fxLoader.load();
            Scene scene = new Scene(root);

            AddAndEditMovieController cont = fxLoader.getController();
            cont.setupStageDependant(newStage);
            cont.setMainModel(this);

            newStage.setTitle("PMC");
            newStage.getIcons().add(new Image("pmc/gui/resources/logo.png"));
            newStage.setScene(scene);
            newStage.setMinWidth(500);
            newStage.setMinHeight(500);
            newStage.setMaximized(true);
            newStage.showAndWait();
        }
        catch (IOException ex)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Could not open Window new Movie:\n" + ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void contextMenuHandler(TableView<Movie> tblviewMovies)
    {

        //Plays the selected song.
        MenuItem item1 = new MenuItem("Open Movie");
        item1.setOnAction((ActionEvent e)
                ->
        {
            System.out.println("Hi");

        });

        //Sets the created MenuItems into the context menu for the table.
        final ContextMenu contextMenu = new ContextMenu(item1);
        contextMenu.setMaxSize(50, 50);
        tblviewMovies.setContextMenu(contextMenu);
    }

}
