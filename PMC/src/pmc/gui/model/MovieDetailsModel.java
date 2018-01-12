/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.gui.model;

import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import pmc.bll.BLLManager;

/**
 *
 * @author Alex
 */
public class MovieDetailsModel
{

    BLLManager bllManager;

    public void initialize(URL url, ResourceBundle rb)
    {
    }

    public void setBLLManager(BLLManager bllManager)
    {
        this.bllManager = bllManager;
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

//    //Sets the description of the movie in the description text area.
//    public void setDescription(TextArea textareaDescription) {
//        textareaDescription.setText(bllManager.getCurrentMovie().);
//    }
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
    public void setScore(Label lblScore)
    {
        if (bllManager.getCurrentMovie().getImdbRating() != -1)
        {
            lblScore.setText(bllManager.getCurrentMovie().getImdbRating() + "");
        }
        else
        {
            lblScore.setText("0");
        }
    }

    //Plays the movie upon pressing the "Watch Movie" button.
    public void playMovie() throws IOException
    {
        String currentDir = System.getProperty("user.dir") + File.separator;
        File dir = new File(currentDir);

        Desktop.getDesktop().open(new File(dir + "\\Movies\\Guy runs into wall.mp4"));
    }

    //Sets the user's clipboard to contain the IMDb link.
    public void setClipboard()
    {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(bllManager.getCurrentMovie().getImdbUrl());
        clipboard.setContent(content);

    }
}
