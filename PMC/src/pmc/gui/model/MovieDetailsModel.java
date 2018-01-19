package pmc.gui.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import pmc.be.Movie;
import pmc.bll.BLLException;
import pmc.bll.BLLManager;
import pmc.bll.MoviePlayer;
import pmc.dal.DALException;

/**
 * En Gruppe
 * @author Alex, Asbjørn & Jan
 */
public class MovieDetailsModel
{
    private final String ON_ERROR_PLAYING_MOVIE = "Launching movie: ";
    private final String RATING_NEGATIVE_ONE = "None";
    private final String DIRECTORS_NONE = "No director found";
    private final String GENRES_NONE = "No genres found";
    private final String STAR_DIRECTORY = "src/pmc/gui/resources/star.png";
    private final String NO_IMAGE_DIRECTORY = "src/pmc/gui/resources/noimage.png";
    private final String NO_IMDB_HYPERLINK = "No IMDb link found";

    BLLManager bllManager;
    MoviePlayer mp;

    /**
     * Sets the BLLManager instance to be the same as the one in the MainModel.
     * @param bllManager
     */
    public void setBLLManager(BLLManager bllManager)
    {
        this.bllManager = bllManager;
        try
        {
            this.mp = new MoviePlayer();
        }
        catch (DALException | SecurityException ex)
        {

            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Removes the "Copy IMBd link to clipboard" button if no IMDb link is
     * present.
     * @param btnCopyLink
     */
    public void determineIMDbLink(Button btnCopyLink)
    {
        if (bllManager.getCurrentMovie().getImdbUrl().isEmpty())
        {
            btnCopyLink.setDisable(true);
            btnCopyLink.setText(NO_IMDB_HYPERLINK);
        }
    }

    /**
     * Sets the image of the movie poster.
     * @param imageMoviePoster
     */
    public void setPosterImage(ImageView imageMoviePoster)
    {
        if (bllManager.getCurrentMovie().getImageInBytes() != null)
        {
            if (bllManager.getCurrentMovie().getImageInBytes().length != 0)
            {
                Image img = new Image(new ByteArrayInputStream(bllManager.getCurrentMovie().getImageInBytes()));
                imageMoviePoster.setImage(img);
            }
        }
        else
        {
            File file = new File(NO_IMAGE_DIRECTORY);
            Image image = new Image(file.toURI().toString());
            imageMoviePoster.setImage(image);
        }
    }

    /**
     * Sets the image of the rating star.
     * @param imageMoviePoster
     */
    public void setRatingImage(ImageView imageMoviePoster)
    {
        File file = new File(STAR_DIRECTORY);
        Image image = new Image(file.toURI().toString());
        imageMoviePoster.setImage(image);
    }

    /**
     * Sets the title and year of the movie in one string.
     * @param lblTitleAndYear
     */
    public void setTitleAndYear(Label lblTitleAndYear)
    {
        if (bllManager.getCurrentMovie().getYear() == 0)
        {
            String titleAndYear = bllManager.getCurrentMovie().getName();
            lblTitleAndYear.setText(titleAndYear);
        }
        else
        {
            String titleAndYear = bllManager.getCurrentMovie().getName() + ", " + bllManager.getCurrentMovie().getYear();
            lblTitleAndYear.setText(titleAndYear);
        }

    }

    /**
     * Sets the different genres associated with the movie and removes the first
     * and last character from the string, as they contain [ and ] respectively.
     * @param lblGenres
     */
    public void setGenres(Label lblGenres)
    {
        if (!bllManager.getCurrentMovie().getGenres().toString().isEmpty())
        {
            String string = bllManager.getCurrentMovie().getGenres().toString();
            lblGenres.setText(string.substring(1, string.length() - 1));
        }
        else
        {
            lblGenres.setText(GENRES_NONE);
        }
    }

    /**
     * Sets the director of the movie.
     * @param lblDirector
     */
    public void setDirector(Label lblDirector)
    {
        if (!bllManager.getCurrentMovie().getDirectors().isEmpty())
        {
            lblDirector.setText(bllManager.getCurrentMovie().getDirectors());
        }
        else
        {
            lblDirector.setText(DIRECTORS_NONE);
        }
    }

    /**
     * Sets what score the movie has.
     * @param lblImdb
     * @param lblPersonal
     */
    public void setScore(Label lblImdb, Label lblPersonal)
    {
        Movie currentMovie = bllManager.getCurrentMovie();
        if (currentMovie.getImdbRating() != -1)
        {
            lblImdb.setText(currentMovie.getImdbRating() + "");
        }
        else
        {
            lblImdb.setText(RATING_NEGATIVE_ONE);
        }
        if (currentMovie.getPersonalRating() != -1)
        {
            lblPersonal.setText(currentMovie.getPersonalRating() + "");
        }
        else
        {
            lblPersonal.setText(RATING_NEGATIVE_ONE);
        }
    }

    /**
     * Plays the movie upon pressing the "Watch Movie" button.
     */
    public void playMovie()
    {
        try
        {
            mp.playMovie(bllManager.getCurrentMovie());
        }
        catch (BLLException ex)
        {
            Alert alertError = new Alert(Alert.AlertType.ERROR, ON_ERROR_PLAYING_MOVIE + ex.getMessage(), ButtonType.OK);
            alertError.showAndWait();
        }
    }

    /**
     * Sets the user's clipboard to contain the IMDb link.
     */
    public void setClipboard()
    {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(bllManager.getCurrentMovie().getImdbUrl());
        clipboard.setContent(content);

    }

    /**
     * Sets the description in the text area, if it is not null
     * @param textareaDescription
     */
    public void setDescription(TextArea textareaDescription)
    {
        String summary = bllManager.getCurrentMovie().getSummary();
        //System.out.println(summary);
        if (summary != null)
        {
            textareaDescription.setText(summary);
            textareaDescription.setWrapText(true);
        }
    }
}
