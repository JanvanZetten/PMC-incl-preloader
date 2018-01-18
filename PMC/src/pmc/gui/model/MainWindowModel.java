/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.gui.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pmc.be.Genre;
import pmc.be.IMDbMovieFilter;
import pmc.be.Movie;
import pmc.be.MovieFilter;
import pmc.be.PersonalMovieFilter;
import pmc.be.GenreMovieFilter;
import pmc.bll.BLLException;
import pmc.bll.BLLManager;
import pmc.bll.MoviePlayer;
import pmc.dal.DALException;
import pmc.be.ControllerSetup;

/**
 *
 * @author janvanzetten
 */
public class MainWindowModel
{
    private final String ON_ERROR_GET_ALL = "Could not load information,\n Check connecetion to database\n";
    private final String ON_WARNING_NO_MOVIE_SELECTED = "You have to select a movie!";
    private final String ON_CONFIRMATION_DELETE = "Are you sure you want to delete: ";
    private final String CHANGE_RATING_HEADER = "";
    private final String CHANGE_RATING_TITLE = "Personalrating:";
    private final String CHANGE_RATING_TEXT = "Rating from 0 to 10";
    private final String ON_WARNING_CHANGE_RATING_NUMBER = "Not a number between 0 and 10";
    private final String ON_ERROR_CHANGE_RATING = "Could not change rating\n";
    private final String NEW_GENRE_TITLE = "New Genre";
    private final String NEW_GENRE_TEXT = "Name of new Genre";
    private final String VIEW_DIRECTORY = "/pmc/gui/view/";
    private final String VIEW_EXTENSION = ".fxml";
    private final String WINDOW_PRETEXT = "PMC - ";
    private final String LOGO_DIRECTORY = "pmc/gui/resources/logo.png";
    private final String ON_ERROR_OPEN_WINDOW = "Could not open Window ";
    private final String ON_ERROR_LOADING_OUTDATED = "Could not get outdated movies:\n";

    private TableView<Movie> tblview;
    private ObservableList<Movie> movies;
    private ObservableList<Movie> filteredMovies;
    private SortedList<Movie> sortedMovies;
    private double minImdbRating;
    private int minPersonalRating;
    private String filterString;
    private List<CheckBox> genreFilterList;
    private List<Genre> allGenres;

    private BLLManager bllManager;
    private MoviePlayer mp;
    private List<String> selectedGenres;
    private VBox genreVBox;
    private TableRow<Movie> selectedRow;

    public MainWindowModel()
    {
        bllManager = new BLLManager();
        mp = new MoviePlayer();
        this.movies = FXCollections.observableArrayList();
        this.filteredMovies = FXCollections.observableArrayList();
        this.sortedMovies = new SortedList(filteredMovies);
        genreFilterList = new ArrayList();
        selectedGenres = new ArrayList();
        minImdbRating = 0.0;
        minPersonalRating = 0;
        filterString = "";
    }

    /**
     * Add one movie to the movies Observable List.
     *
     * @param movie to add.
     */
    public void addMovieToObsLst(Movie movie)
    {
        movies.add(movie);
        addToFiltered();
    }

    /**
     * Change the movies Observable List to given list.
     *
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
     *
     * @return movies Observable List.
     */
    public ObservableList<Movie> getFilteredMovies()
    {
        return filteredMovies;
    }

    /**
     * Copies movies entries, which meets restrictions, to filteredMovies.
     */
    public void addToFiltered()
    {
        // Creates filters. Add them to an array.
        List<MovieFilter> movieFilters = new ArrayList<>();
        MovieFilter imdbMovieFilter = new IMDbMovieFilter(0.0, minImdbRating);;
        MovieFilter personalMovieFilter = new PersonalMovieFilter(0, minPersonalRating);;
        MovieFilter genreMovieFilter = new GenreMovieFilter(null, null);

        movieFilters.add(imdbMovieFilter);
        movieFilters.add(personalMovieFilter);
        movieFilters.add(genreMovieFilter);

        // Clears all entries in filteredMovies.
        filteredMovies.clear();

        for (Movie movie : movies)
        {
            // Sets filters for movie information.
            movieFilters.set(0, new IMDbMovieFilter(movie.getImdbRating(), minImdbRating));
            movieFilters.set(1, new PersonalMovieFilter(movie.getPersonalRating(), minPersonalRating));
            movieFilters.set(2, new GenreMovieFilter(movie.getGenres(), selectedGenres));

            // Count restrictions met.
            int meetsRestrictions = 0;
            for (MovieFilter movieFilter : movieFilters)
            {
                if (movieFilter.meetsRestrictions())
                {
                    meetsRestrictions++;
                }
            }

            // If all restrictions was met.
            if (meetsRestrictions == movieFilters.size())
            {
                // Add movie if textfield filter is empty
                if (filterString.equalsIgnoreCase("") || filterString == null)
                {
                    filteredMovies.add(movie);
                }
                else
                {
                    // For each word in string.
                    for (String string : filterString.split(" "))
                    {
                        // Check if word is in either name, year or directors.
                        if (movie.getName().toLowerCase().contains(string.toLowerCase())
                                || String.valueOf(movie.getYear()).toLowerCase().contains(string.toLowerCase())
                                || movie.getDirectors().toLowerCase().contains(string.toLowerCase()))
                        {
                            // If so add it and break for each loop.
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

    public void setCurrentMovie(Movie currentMovie)
    {
        bllManager.setCurrentMovie(currentMovie);
    }

    /**
     * Makes the Menubar look nice on Mac. if it is a Mac the Menubar is placed
     * on the where top of the screen where it normally is placed on a Mac. it
     * then takes the stackpanes and sets the top anchor to 0 for avoiding empty
     * space
     *
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

    /**
     * Setup of the tableview
     *
     * @param tblviewMovies the table
     * @param tblcolTitle first colon
     * @param tblcolGenre second colon
     * @param tblcolTime third colon
     * @param tblcolImdbRating fourth colon
     * @param tblcolPersonalRating fifth colon
     */
    public void initializeTableView(TableView<Movie> tblviewMovies, TableColumn<Movie, String> tblcolTitle, TableColumn<Movie, String> tblcolGenre, TableColumn<Movie, String> tblcolTime, TableColumn<Movie, String> tblcolImdbRating, TableColumn<Movie, String> tblcolPersonalRating)
    {
        tblview = tblviewMovies;

        // Set values for Table Cells.
        tblcolTitle.setCellValueFactory(new PropertyValueFactory("name"));
        tblcolGenre.setCellValueFactory((TableColumn.CellDataFeatures<Movie, String> param)
                ->
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
        tblcolTime.setCellValueFactory((TableColumn.CellDataFeatures<Movie, String> param)
                ->
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
        tblcolPersonalRating.setCellValueFactory((TableColumn.CellDataFeatures<Movie, String> param)
                ->
        {
            if (param.getValue().getPersonalRating() == -1)
            {
                return new ReadOnlyObjectWrapper<>("None");
            }
            else
            {
                return new ReadOnlyObjectWrapper<>(param.getValue().getPersonalRating() + "");
            }
        });
        tblcolPersonalRating.setStyle("-fx-alignment: CENTER;");

        // Set row events.
        tblviewMovies.setRowFactory(tv
                ->
        {
            TableRow<Movie> row = new TableRow<>();

            // Doubleclick opens movie details. Single click set current movie.
            row.setOnMouseClicked(event
                    ->
            {
                if (event.getClickCount() == 2 && (!row.isEmpty()))
                {
                    startMovieDetailsWIndow();
                }
                else if (event.getClickCount() == 1 && (!row.isEmpty()))
                {
                    setCurrentMovie(row.getItem());
                }
            });

            // Setting up context menu AKA rightclick menu.
            final ContextMenu contextMenu = new ContextMenu();

            // Plays the selected movie.
            final MenuItem item1 = new MenuItem("Play Movie");
            item1.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    try
                    {
                        mp.playMovie(bllManager.getCurrentMovie());
                    }
                    catch (BLLException ex)
                    {
                        Alert alertError = new Alert(Alert.AlertType.ERROR, "Launching movie: " + ex.getMessage(), ButtonType.OK);
                        alertError.showAndWait();
                    }
                }
            });

            // Show details of the selected movie.
            final MenuItem item2 = new MenuItem("Open Movie");
            item2.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    startMovieDetailsWIndow();
                }
            });

            // Changes the current movies personal rating.
            final MenuItem item3 = new MenuItem("Change Personalrating");
            item3.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    changeRating();
                }
            });

            // Deletes the selected movie.
            final MenuItem item4 = new MenuItem("Delete Movie");
            item4.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    deleteMovie();
                }
            });

            // Adds menu items into the context menu.
            contextMenu.getItems().add(item1);
            contextMenu.getItems().add(item2);
            contextMenu.getItems().add(item3);
            contextMenu.getItems().add(item4);
            contextMenu.setMaxSize(50, 50);

            // Set context menu on row, but use a binding to make it only show for non-empty rows:
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );

            return row;
        });

        // Set Observable List.
        tblviewMovies.setItems(sortedMovies);
        sortedMovies.comparatorProperty().bind(tblviewMovies.comparatorProperty());
        getAllMovies();
    }

    /**
     * Gets all the movies and stores them in the movie list and adds them to
     * the filtered list. If an error occurred it will show an alert message.
     */
    private void getAllMovies()
    {
        try
        {
            changeMoviesInObsLst(bllManager.getAllMovies());
        }
        catch (BLLException ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ON_ERROR_GET_ALL + ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Gets all the genres and puts them into the vbox as chekboxes with the
     * genres name as label
     *
     * @param genreVBox the checkbox for putting them in
     */
    public void initializeGenre(VBox genreVBox)
    {
        this.genreVBox = genreVBox;
        allGenres = null;
        try
        {
            allGenres = bllManager.getAllGenres();
        }
        catch (BLLException ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ON_ERROR_GET_ALL + ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
        if (allGenres != null)
        {
            updateGenresInVBox(allGenres);
        }

    }

    /**
     * Adds a new genre. Remember to call initialize before this is called.
     *
     * @param name
     * @return the newly made genre
     */
    public Genre addGenre(String name)
    {
        Genre newGenre;
        try
        {
            newGenre = bllManager.addGenre(name);

            if (newGenre != null)
            {
                allGenres.add(newGenre);
                updateGenresInVBox(allGenres);

                return newGenre;
            }
        }
        catch (BLLException ex)
        {
            Alert alertError = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alertError.showAndWait();
        }
        return null;
    }

    /**
     * Updates genre in VBox.
     * @param genres
     */
    private void updateGenresInVBox(List<Genre> genres)
    {
        genreFilterList.clear();
        for (Genre genre : genres)
        {
            CheckBox checkbox = new CheckBox(genre.getName());
            checkbox.setOnMouseReleased(mouseEvent -> checkGenreFilter());
            genreFilterList.add(checkbox);
        }
        genreVBox.getChildren().clear();
        genreVBox.getChildren().addAll(genreFilterList);
    }

    /**
     * checks if at least one of the genre chekboxes is set selcted if it is it
     * will filter the shown list.
     */
    private void checkGenreFilter()
    {
        selectedGenres.clear();
        List<CheckBox> selected = new ArrayList<>();
        for (CheckBox checkBox : genreFilterList)
        {
            if (checkBox.isSelected())
            {
                selectedGenres.add(checkBox.getText());
            }
        }
        addToFiltered();
    }

    /**
     * does everything needed for deleting the current movie inclusive making a
     * confirmation window
     */
    public void deleteMovie()
    {
        if (bllManager.getCurrentMovie() == null)
        {
            Alert alertError = new Alert(Alert.AlertType.WARNING, ON_WARNING_NO_MOVIE_SELECTED, ButtonType.OK);
            alertError.showAndWait();
            return;
        }
        Movie movieToDelete = bllManager.getCurrentMovie();
        //make sure user wnt to delete
        Alert alert = new Alert(AlertType.CONFIRMATION, ON_CONFIRMATION_DELETE + movieToDelete.getName() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            try
            {
                if (bllManager.deleteMovie(movieToDelete))
                {
                    removeMovieFromTable(movieToDelete);
                }
            }
            catch (BLLException ex)
            {
                Alert alertError = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
                alertError.showAndWait();
            }

        }
        bllManager.setCurrentMovie(tblview.getSelectionModel().getSelectedItem());
    }

    /**
     * Removes movie from table.
     * @param movieToDelete
     */
    public void removeMovieFromTable(Movie movieToDelete)
    {
        movies.remove(movieToDelete);
        filteredMovies.remove(movieToDelete);
    }

    /**
     * Opens a dialog box for changing the personal rating of the current movie,
     * and assigns this value to the current movies personal rating.
     */
    private void changeRating()
    {
        int before = bllManager.getCurrentMovie().getPersonalRating();
        while (true)
        {
            TextInputDialog TID = new TextInputDialog();
            TID.setHeaderText(CHANGE_RATING_HEADER);
            TID.setTitle(CHANGE_RATING_TITLE);
            TID.setContentText(CHANGE_RATING_TEXT);
            Optional<String> input = TID.showAndWait();
            if (input.isPresent())
            {
                try
                {
                    int i = Integer.parseInt(input.get());
                    if (i < 0 || i > 10)
                    {
                        throw new NumberFormatException();
                    }
                    bllManager.getCurrentMovie().setPersonalRating(i);
                    bllManager.updateMovie(bllManager.getCurrentMovie());
                    updateMovieList(bllManager.getCurrentMovie());
                    return;
                }
                catch (NumberFormatException e)
                {
                    Alert alertError = new Alert(Alert.AlertType.WARNING, ON_WARNING_CHANGE_RATING_NUMBER, ButtonType.OK);
                    alertError.showAndWait();
                }
                catch (BLLException ex)
                {
                    bllManager.getCurrentMovie().setPersonalRating(before);
                    Alert alertError = new Alert(Alert.AlertType.ERROR, ON_ERROR_CHANGE_RATING + ex.getMessage(), ButtonType.OK);
                    alertError.showAndWait();
                    return;
                }
            }
            else
            {
                return;
            }
        }
    }

    /**
     * Updates the updated movie in the observable lists: movies and
     * filtredMovies
     *
     * @param Updatedmovie
     */
    private void updateMovieList(Movie Updatedmovie)
    {
        for (Movie movy : movies)
        {
            if (movy.getId() == Updatedmovie.getId())
            {
                movy = Updatedmovie;
            }
        }
        addToFiltered();
    }

    /**
     * Opens a input dialog for the new genre and it will then make a new genre
     * in database if it is not existing yet
     */
    public void newGenre()
    {
        TextInputDialog TID = new TextInputDialog();
        TID.setTitle(NEW_GENRE_TITLE);
        TID.setContentText(NEW_GENRE_TEXT);
        Optional<String> input = TID.showAndWait();
        if (input.isPresent())
        {
            addGenre(input.get());
        }
    }

    /**
     * Deletes all unused genres from the list and from the database
     */
    public void deleteUnusedGenres()
    {
        try
        {
            List<Integer> deletedIds = bllManager.deleteUnusedGenres();
            for (Integer deletedId : deletedIds)
            {
                for (Genre allGenre : allGenres)
                {
                    if (allGenre.getId() == deletedId)
                    {
                        allGenres.remove(allGenre);
                        break;
                    }
                }
            }
            updateGenresInVBox(allGenres);
        }
        catch (BLLException ex)
        {
            Alert alertError = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alertError.showAndWait();
        }
    }

    private void startMovieDetailsWIndow()
    {
        startModalWindowWithSetup("MovieDetailsView", "Movie Details", 620, 394, false);
    }

    public void startEditMovieWindow()
    {
        if (bllManager.getCurrentMovie() == null)
        {
            Alert alertError = new Alert(Alert.AlertType.WARNING, ON_WARNING_NO_MOVIE_SELECTED, ButtonType.OK);
            alertError.showAndWait();
            return;
        }

        startModalWindowWithSetup("EditMovieView", "Edit Movie", 620, 394, false);
    }

    /**
     * opens the window for adding a new movie
     */
    public void startNewMovieWindow()
    {
        startModalWindowWithSetup("AddMovieView", "Add Movie", 500, 500, true);
    }

    /**
     * Starts a new window by sending in the name of the view in the parameters.
     * @param windowView file name. (without .fxml)
     * @param windowName name written in top bar.
     * @param minWidth minimum width of window.
     * @param minHeight minimum height of window.
     * @param maximized should the window startup maximized?
     */
    public void startModalWindow(String windowView, String windowName, int minWidth, int minHeight, boolean maximized)
    {
        try
        {
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader fxLoader = new FXMLLoader(getClass().getResource(VIEW_DIRECTORY + windowView + VIEW_EXTENSION));
            Parent root = fxLoader.load();
            Scene scene = new Scene(root);
            newStage.setTitle(WINDOW_PRETEXT + windowName);
            newStage.getIcons().add(new Image(LOGO_DIRECTORY));
            newStage.setScene(scene);
            newStage.setMinWidth(minWidth);
            newStage.setMinHeight(minHeight);
            newStage.setMaximized(maximized);
            newStage.showAndWait();
        }
        catch (IOException ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ON_ERROR_OPEN_WINDOW + windowName + ":\n" + ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Starts a new window and set it up using the method setup() in the
     * interface ControllerSetup.
     * @param windowView file name. (without .fxml)
     * @param windowName name written in top bar.
     * @param minWidth minimum width of window.
     * @param minHeight minimum height of window.
     * @param maximized should the window startup maximized?
     */
    public void startModalWindowWithSetup(String windowView, String windowName, int minWidth, int minHeight, boolean maximized)
    {
        try
        {
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader fxLoader = new FXMLLoader(getClass().getResource(VIEW_DIRECTORY + windowView + VIEW_EXTENSION));
            Parent root = fxLoader.load();

            ControllerSetup cont = fxLoader.getController();
            cont.setup(newStage, this, bllManager);

            Scene scene = new Scene(root);
            newStage.setTitle(WINDOW_PRETEXT + windowName);
            newStage.getIcons().add(new Image(LOGO_DIRECTORY));
            newStage.setScene(scene);
            newStage.setMinWidth(minWidth);
            newStage.setMinHeight(minHeight);
            newStage.setMaximized(maximized);
            newStage.showAndWait();
        }
        catch (IOException ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ON_ERROR_OPEN_WINDOW + windowName + ":\n" + ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void startDeletePopupWindow()
    {
        try
        {
            bllManager.setOutdatedMovies();
        } catch (BLLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ON_ERROR_LOADING_OUTDATED + ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }

        startModalWindowWithSetup("DeletePopupView", "Are you gonna watch these?", 620, 394, false);
    }

}
