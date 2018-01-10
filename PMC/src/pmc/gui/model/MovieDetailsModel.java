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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

/**
 *
 * @author Alex
 */
public class MovieDetailsModel {
    
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setPosterImage(ImageView imageMoviePoster) {
        File file = new File("src/pmc/gui/resources/timmy.jpg");
        Image image = new Image(file.toURI().toString());
        imageMoviePoster.setImage(image);
    }
    
    public void setRatingImage(ImageView imageMoviePoster) {
        File file = new File("src/pmc/gui/resources/star.png");
        Image image = new Image(file.toURI().toString());
        imageMoviePoster.setImage(image);
    }

    public void setTitleAndYear(Label lblTitleAndYear) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setDescription(TextArea textareaDescription) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setGenres(Label lblGenres) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setDirector(Label lblDirector) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setScore(Label lblScore) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void playMovie() throws IOException {
        String currentDir = System.getProperty("user.dir") + File.separator;
        File dir = new File(currentDir);
        
        Desktop.getDesktop().open(new File(dir + "\\Movies\\Guy runs into wall.mp4"));
    }

    public void setClipboard() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString("google.com");
        clipboard.setContent(content);
    }
    
}
