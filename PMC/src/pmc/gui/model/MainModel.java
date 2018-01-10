/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.gui.model;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import pmc.be.Movie;

/**
 *
 * @author janvanzetten
 */
public class MainModel
{

    private ObservableList<Movie> movies;

    public MainModel()
    {
        this.movies = FXCollections.observableArrayList();;
    }

    /**
     * Add one movie to the movies Observable List.
     * @param movie to add.
     */
    public void addMovieToObsLst(Movie movie)
    {
        movies.add(movie);
    }

    /**
     * Change the movies Observable List to given list.
     * @param movies list to change to.
     */
    public void changeMoviesInObsLst(List<Movie> movies)
    {
        this.movies.clear();
        this.movies.addAll(movies);
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
    public ObservableList<Movie> getMovies()
    {
        return movies;
    }

}
