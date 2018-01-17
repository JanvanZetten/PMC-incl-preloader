/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.gui.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import pmc.be.Genre;
import pmc.be.Movie;
import pmc.bll.BLLException;
import pmc.bll.BLLManager;
import pmc.bll.RipManager;

/**
 *
 * @author janvanzetten
 */
public class AddMovieModel
{
    private final String MOVIE_DIR = "/Movies/";

    private Path to;
    private Path from;
    private File selectedFile;
    private String path;
    BLLManager bll = new BLLManager();

    public void browseMovie(TextField textfieldPath) throws IOException
    {
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("MPEG4 Video Files", "*.mp4", "*.mpeg4");
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(filter);
        String currentDir = System.getProperty("user.dir") + File.separator;
        File dir = new File(currentDir);
        fc.setInitialDirectory(dir);
        fc.setTitle("Attach a file");
        selectedFile = fc.showOpenDialog(null);
        textfieldPath.setText(selectedFile.getAbsolutePath());
    }

    /**
     * Saves the movie in the database with the given url
     * @param url imdb url
     * @return true if succeded
     */
    public Movie save(String url, MainWindowModel mainModel)
    {
        // Removes unneccesary tags.
        if (url.toLowerCase().contains("?"))
        {
            url = url.split("\\?")[0];
        }

        //System.out.println(url);
        if (!url.isEmpty() && selectedFile != null)
        {

            if (selectedFile != null)
            {
                from = Paths.get(selectedFile.toURI());

                try
                {
                    String currentDir = System.getProperty("user.dir") + File.separator;
                    String fileName = selectedFile.getName().split("\\.")[0];
                    String extension = "." + selectedFile.getName().split("\\.")[1];
                    String newFilename;
                    File f = new File(currentDir + MOVIE_DIR + fileName + extension);
                    int version = 1;
                    while (f.exists())
                    {
                        newFilename = fileName + version;
                        f = new File(currentDir + MOVIE_DIR + newFilename + extension);
                        version++;
                    }
                    to = Paths.get(currentDir + MOVIE_DIR + f.getName());
                    Files.copy(from, to, REPLACE_EXISTING);
                    path = MOVIE_DIR + f.getName();
                }
                catch (IOException ex)
                {
                    Alert alertError = new Alert(Alert.AlertType.ERROR, "Could not copy movie file: " + ex.getMessage(), ButtonType.OK);
                    alertError.showAndWait();
                    return null;
                }
            }

            try
            {
                RipManager rip = new RipManager(url);

                List<Genre> genresInMovie = new ArrayList<>();
                List<Genre> allExistingGenres = bll.getAllGenres();

                boolean found;

                for (String genre : rip.getGenres())
                {
                    found = false;
                    for (Genre existingGenre : allExistingGenres)
                    {
                        if (existingGenre.getName().equalsIgnoreCase(genre))
                        {
                            genresInMovie.add(existingGenre);
                            found = true;
                            break;
                        }
                    }
                    if (!found)
                    {

                        genresInMovie.add(mainModel.addGenre(genre));

                    }
                }

                //System.out.println(Arrays.toString(rip.getImageInBytes()));
                Movie newMovie = bll.addMovie(rip.getName(), path, genresInMovie, rip.getRating(), -1, rip.getDirectors(), rip.getDuration(), url, rip.getYear(), rip.getSummary(), rip.getImageInBytes());

                return newMovie;

            }
            catch (BLLException ex)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Could not save Movie:\n" + ex.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        }
        return null;
    }

    public boolean pathSet()
    {
        return selectedFile != null;
    }
}
