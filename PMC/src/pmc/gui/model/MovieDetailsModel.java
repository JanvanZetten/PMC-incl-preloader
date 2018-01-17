/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.gui.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
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

/**
 *
 * @author Alex
 */
public class MovieDetailsModel
{

    BLLManager bllManager;
    MoviePlayer mp;

    public void initialize(URL url, ResourceBundle rb)
    {
    }

    public void setBLLManager(BLLManager bllManager)
    {
        this.bllManager = bllManager;
        this.mp = new MoviePlayer();
    }

    //Removes the "Copy IMBd link to clipboard" button if no IMDb link is present.
    public void determineIMDbLink(Button btnCopyLink)
    {
        if (bllManager.getCurrentMovie().getImdbUrl().isEmpty())
        {
            btnCopyLink.setDisable(true);
            btnCopyLink.setText("No IMDb link found");
        }
    }

    //Sets the image of the movie poster.
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
            File file = new File("src/pmc/gui/resources/noimage.png");
            Image image = new Image(file.toURI().toString());
            imageMoviePoster.setImage(image);
        }
    }

    //Sets the image of the rating star.
    public void setRatingImage(ImageView imageMoviePoster)
    {
        File file = new File("src/pmc/gui/resources/star.png");
        Image image = new Image(file.toURI().toString());
        imageMoviePoster.setImage(image);
    }

    //Sets the title and year of the movie in one string.
    public void setTitleAndYear(Label lblTitleAndYear)
    {
        String titleAndYear = bllManager.getCurrentMovie().getName() + ", " + bllManager.getCurrentMovie().getYear();
        lblTitleAndYear.setText(titleAndYear);
    }

    //Sets the different genres associated with the movie and removes the first
    //and last character from the string, as they contain [ and ] respectively.
    public void setGenres(Label lblGenres)
    {
        if (!bllManager.getCurrentMovie().getGenres().toString().isEmpty())
        {
            String string = bllManager.getCurrentMovie().getGenres().toString();
            lblGenres.setText(string.substring(1, string.length() - 1));
        }
        else
        {
            lblGenres.setText("No genres found");
        }
    }

    //Sets the director of the movie.
    public void setDirector(Label lblDirector)
    {
        if (!bllManager.getCurrentMovie().getDirectors().isEmpty())
        {
            lblDirector.setText(bllManager.getCurrentMovie().getDirectors());
        }
        else
        {
            lblDirector.setText("No director found");
        }
    }

    //Sets what score the movie has.
    public void setScore(Label lblImdb, Label lblPersonal)
    {
        Movie currentMovie = bllManager.getCurrentMovie();
        if (currentMovie.getImdbRating() != -1){
            lblImdb.setText(currentMovie.getImdbRating() + "");
        }
        else {
            lblImdb.setText("None");
        }
        if (currentMovie.getPersonalRating() != -1){
            lblPersonal.setText(currentMovie.getPersonalRating() + "");
        }
        else{
            lblPersonal.setText("None");
        }
    }

    //Plays the movie upon pressing the "Watch Movie" button.
    public void playMovie()
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

    //Sets the user's clipboard to contain the IMDb link.
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

    public void tellLastSeen()
    {
        System.out.println(bllManager.getCurrentMovie().getLastView());
    }
}
