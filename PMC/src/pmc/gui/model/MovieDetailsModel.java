/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.gui.model;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import pmc.bll.BLLManager;
import pmc.be.Movie;

/**
 *
 * @author Alex
 */
public class MovieDetailsModel {
    
    BLLManager bllManager;
    
    public void initialize(URL url, ResourceBundle rb) {
    }

//    //Removes the "Copy IMBd link to clipboard" button if no IMDb link is present.
//    public void determineIMDbLink(Button btnCopyLink) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    
    //Sets the image of the movie poster.
    public void setPosterImage(ImageView imageMoviePoster) {
        File file = new File("src/pmc/gui/resources/timmy.jpg");
        Image image = new Image(file.toURI().toString());
        imageMoviePoster.setImage(image);
    }
    
    //Sets the image of the rating star.
    public void setRatingImage(ImageView imageMoviePoster) {
        File file = new File("src/pmc/gui/resources/star.png");
        Image image = new Image(file.toURI().toString());
        imageMoviePoster.setImage(image);
    }

    //Sets the title and year of the movie in one string.
    public void setTitleAndYear(Label lblTitleAndYear) {
        String titleAndYear = bllManager.getCurrentMovie().getName() + bllManager.getCurrentMovie().getYear() + "";
        lblTitleAndYear.setText(titleAndYear);
    }

//    //Sets the description of the movie in the description text area.
//    public void setDescription(TextArea textareaDescription) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    //Sets the different genres associated with the movie.
//    public void setGenres(Label lblGenres) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    //Sets the director of the movie.
//    public void setDirector(Label lblDirector) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    
//    //Sets what score the movie has.
//    public void setScore(Label lblScore) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    //Plays the movie upon pressing the "Watch Movie" button.
    public void playMovie() throws IOException {
        String currentDir = System.getProperty("user.dir") + File.separator;
        File dir = new File(currentDir);
        
        Desktop.getDesktop().open(new File(dir + "\\Movies\\Guy runs into wall.mp4"));
    }

    //Sets the user's clipboard to contain the IMDb link.
    public void setClipboard() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString("google.com");
        clipboard.setContent(content);
        
    }    
    
    public void setBLLManager(BLLManager bllManager) {
        this.bllManager = bllManager;
    }
}
