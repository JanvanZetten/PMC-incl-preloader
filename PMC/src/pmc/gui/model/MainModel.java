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
import pmc.be.IMDbMovieFilter;
import pmc.be.Movie;
import pmc.be.MovieFilter;
import pmc.be.PersonalMovieFilter;

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

    public MainModel()
    {
        this.movies = FXCollections.observableArrayList();
        this.filteredMovies = FXCollections.observableArrayList();
        minImdbRating = 0.0;
        minPersonalRating = 0;
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
     * Makes the Menubar look nice on Mac. if it is a Mac the Menubar is placed
     * on the where top of the screen where it normally is placed on a Mac. it
     * then takes the stackpanes and sets the top anchor to 0 for avoiding empty
     * space
     * @param Menubar
     * @param stackPaneFiltering
     * @param StackPaneMovieView
     */
    public void changeMenubarForMac(MenuBar Menubar, StackPane stackPaneFiltering, StackPane StackPaneMovieView)
    {
        if (System.getProperty("os.name").startsWith("Mac"))
        {
            Menubar.useSystemMenuBarProperty().set(true);
            AnchorPane.setTopAnchor(stackPaneFiltering, 0.0);
            AnchorPane.setTopAnchor(StackPaneMovieView, 0.0);
        }
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
            System.out.println(meetsRestrictions + " " + movieFilters.size());
            if (meetsRestrictions == movieFilters.size())
            {
                filteredMovies.add(movie);
            }
        }
    }

    public void setMinImdbRating(double minImdbRating)
    {
        this.minImdbRating = minImdbRating;
    }

    public void setMinPersonalRating(int minPersonalRating)
    {
        this.minPersonalRating = minPersonalRating;
    }

}
