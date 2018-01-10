/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.gui.model;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
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
}
