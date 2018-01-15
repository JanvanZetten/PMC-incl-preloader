/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.gui.model;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pmc.be.Genre;
import pmc.be.Movie;
import pmc.bll.BLLException;
import pmc.bll.BLLManager;

/**
 *
 * @author Asbamz
 */
public class EditMovieModel
{
    private BLLManager bllManager;

    private Movie movie;
    private String name;
    private int year;
    private List<Genre> genres;
    private List<Integer> selectedGenres;
    private String directors;
    private String summary;

    public EditMovieModel(BLLManager bllManager)
    {
        this.bllManager = bllManager;
        movie = bllManager.getCurrentMovie();
    }

    /**
     * Runs updateMovie in BLLManager if all values are set.
     */
    public void updateMovie(Button btnUpdate)
    {
        Movie movie = bllManager.getCurrentMovie();
        movie.setName(name);
        movie.setYear(year);

        List<Genre> newGenres = new ArrayList<>();
        for (Integer selectedGenre : selectedGenres)
        {
            newGenres.add(genres.get(selectedGenre));
        }

        if (newGenres.size() > 0)
        {
            movie.setGenres(newGenres);
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No genre is selected!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        movie.setDirectors(directors);
        movie.setSummary(summary);
        try
        {
            bllManager.updateMovie(movie);
            Stage stage = (Stage) btnUpdate.getScene().getWindow();
            stage.close();
        }
        catch (BLLException ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not update Movie " + movie.getName() + ": " + ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Gets Movie object.
     * @return
     */
    public Movie getMovie()
    {
        return movie;
    }

    /**
     * Set all values.
     * @param name
     * @param year
     * @param directors
     * @param summary
     */
    public void setValues(String name, int year, String directors, String summary)
    {
        this.name = name;
        this.year = year;
        this.directors = directors;
        this.summary = summary;
    }

    /**
     * Gets all the genres and puts them into the vbox as chekboxes with the
     * genres name as label
     *
     * @param genreVBox the checkbox for putting them in
     */
    public void initializeGenre(VBox genreVBox)
    {
        List<CheckBox> genreList = new ArrayList<>();
        selectedGenres = new ArrayList<>();

        try
        {
            genres = bllManager.getAllGenres();
        }
        catch (BLLException ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load Genres, check internet connection:\n" + ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
            return;
        }

        if (genres != null)
        {
            for (Genre genre : genres)
            {
                CheckBox checkbox = new CheckBox(genre.getName());
                checkbox.setOnMouseReleased(mouseEvent ->
                {
                    int index = genres.indexOf(genre);
                    if (selectedGenres.contains(index))
                    {
                        selectedGenres.remove(selectedGenres.indexOf(index));
                    }
                    else
                    {
                        selectedGenres.add(index);
                    }
                });

                genreList.add(checkbox);

                if (movie.getGenres().contains(genre))
                {
                    checkbox.selectedProperty().set(true);
                    selectedGenres.add(genres.indexOf(genre));
                }
            }

            genreVBox.getChildren().addAll(genreList);
        }

    }
}
