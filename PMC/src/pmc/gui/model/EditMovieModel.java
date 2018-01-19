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
 * En Gruppe
 * @author Alex, Asbjørn & Jan
 */
public class EditMovieModel
{
    private final String ON_WARNING_NO_GENRE = "No genre is selected!\n";
    private final String ON_ERROR_UPDATE_MOVIE = "Could not update Movie\n";
    private final String ON_ERROR_LOADING_GENRE = "Could not load Genres, check internet connection:\n";

    private BLLManager bllManager;

    private Movie movie;
    private String name;
    private int year;
    private List<Genre> genres;
    private List<Integer> selectedGenres;
    private String directors;
    private String summary;

    /**
     * Gets BLLManager from parameter and get current movie.
     * @param bllManager
     */
    public EditMovieModel(BLLManager bllManager)
    {
        this.bllManager = bllManager;
        movie = bllManager.getCurrentMovie();
    }

    /**
     * Runs updateMovie in BLLManager if all values are set.
     * @param btnUpdate
     */
    public void updateMovie(Button btnUpdate)
    {
        // Change name and year to data from textfields.
        movie.setName(name);
        movie.setYear(year);

        // Get all selected genres.
        List<Genre> newGenres = new ArrayList<>();
        for (Integer selectedGenre : selectedGenres)
        {
            newGenres.add(genres.get(selectedGenre));
        }

        // Change to new genres if any was selected. If not show error and return.
        if (newGenres.size() > 0)
        {
            movie.setGenres(newGenres);
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING, ON_WARNING_NO_GENRE, ButtonType.OK);
            alert.showAndWait();
            return;
        }

        // Change directors and summary for movie.
        movie.setDirectors(directors);
        movie.setSummary(summary);

        // Send update to DAL and close window on success. Show error on failure.
        try
        {
            bllManager.updateMovie(movie);
            Stage stage = (Stage) btnUpdate.getScene().getWindow();
            stage.close();
        }
        catch (BLLException ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ON_ERROR_UPDATE_MOVIE + movie.getName() + ": " + ex.getMessage(), ButtonType.OK);
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
     * Gets all the genres and puts them into the vbox as checkboxes with the
     * genres name as label.
     *
     * @param genreVBox the checkbox to put them in.
     */
    public void initializeGenre(VBox genreVBox)
    {
        // Container for checkboxes and initiates selectedGenres.
        List<CheckBox> genreList = new ArrayList<>();
        selectedGenres = new ArrayList<>();

        // Try to get all genres from database. Show error and return on failure.
        try
        {
            genres = bllManager.getAllGenres();
        }
        catch (BLLException ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ON_ERROR_LOADING_GENRE + ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
            return;
        }

        // If genres is set.
        if (genres != null)
        {
            // For all genre.
            for (Genre genre : genres)
            {
                // Create new checkbox with the genres name.
                CheckBox checkbox = new CheckBox(genre.getName());

                // Set on mouseclick release.
                checkbox.setOnMouseReleased(mouseEvent ->
                {
                    // Get this genres index in container.
                    int index = genres.indexOf(genre);

                    // If this genre index is in selectedGenres.
                    if (selectedGenres.contains(index))
                    {
                        // Remove index from selectedGenres.
                        selectedGenres.remove(selectedGenres.indexOf(index));
                    }
                    else
                    {
                        // Add index to selectedGenres.
                        selectedGenres.add(index);
                    }
                });

                // Add checkbox to a List of checkboxes.
                genreList.add(checkbox);

                // If movie has this genre.
                if (movie.getGenres().contains(genre))
                {
                    // Set selected to true and add it to selectedGenres.
                    checkbox.selectedProperty().set(true);
                    selectedGenres.add(genres.indexOf(genre));
                }
            }

            // Add all checkboxes to VBox.
            genreVBox.getChildren().addAll(genreList);
        }
    }
}
