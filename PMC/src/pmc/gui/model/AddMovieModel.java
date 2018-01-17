/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.gui.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        if (selectedFile != null)
        {
            from = Paths.get(selectedFile.toURI());
            to = Paths.get(dir + "/Movies/" + selectedFile.getName());
            textfieldPath.setText(selectedFile.getName());
            path = ("/Movies/" + selectedFile.getName());
            System.out.println(path);

//            Files.copy(from, to, REPLACE_EXISTING);
        }
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

        System.out.println(url);

        if (!url.isEmpty() && !path.isEmpty())
        {
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
        return path != null;
    }
}
