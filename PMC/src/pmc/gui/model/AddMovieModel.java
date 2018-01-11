/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.gui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import pmc.be.Genre;
import pmc.bll.BLLException;
import pmc.bll.BLLManager;
import pmc.bll.ripManager;

/**
 *
 * @author janvanzetten
 */
public class AddMovieModel {

    BLLManager bll = new BLLManager();
    String path;

    public void browseMovie() {
        //TODO browse for movie file and assign to path

    }

    
    /**
     * Saves the movie in the database with the given url
     * @param url imdb url 
     * @return true if succeded
     */
    public boolean save(String url) {  

        if (!(url.isEmpty() && path.isEmpty())) {
            try {
                ripManager rip = new ripManager(url);

                List<Genre> genresInMovie = new ArrayList<>();
                List<Genre> allExistingGenres = bll.getAllGenres();

                boolean found;

                for (String genre : rip.getGenres()) {
                    found = false;
                    for (Genre existingGenre : allExistingGenres) {
                        if (existingGenre.getName().equalsIgnoreCase(genre)) {
                            genresInMovie.add(existingGenre);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {

                        genresInMovie.add(bll.addGenre(genre));

                    }
                }

                bll.addMovie(rip.getName(), path, genresInMovie, rip.getRating(), -1, rip.getDirectors(), rip.getDuration(), url, rip.getYear(), rip.getImageInBytes());

                return true;

            } catch (BLLException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Could not save Movie:\n" + ex.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        }
        return false;
    }

}
