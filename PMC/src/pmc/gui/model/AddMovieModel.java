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
import pmc.dal.DALException;
import pmc.dal.SettingsData;

/**
 * En Gruppe
 * @author janvanzetten, Alex & Asbamz
 */
public class AddMovieModel
{
    private final String MOVIE_DIR = "/Movies/";
    private final FileChooser.ExtensionFilter FILE_CHOOSER_FILTER = new FileChooser.ExtensionFilter("MPEG4 Video Files", "*.mp4", "*.mpeg4");
    private final String CURRENT_DIR;
    private final String FILE_CHOOSER_TITLE = "Attach a file";
    private final String ON_SAVE_ERROR = "Could not save Movie:\n";
    private final String ON_COPY_ERROR = "Could not copy Movie file:\n";

    private Path to;
    private Path from;
    private File selectedFile;
    private String path;
    BLLManager bll = new BLLManager();

    /**
     * Gets source directory and creates folder if not already there.
     * @throws DALException
     * @throws SecurityException
     */
    public AddMovieModel() throws DALException, SecurityException
    {
        CURRENT_DIR = (new SettingsData()).loadSettings().getMovieLocation() + File.separator;
        File theDir = new File(CURRENT_DIR + MOVIE_DIR);
        if (!theDir.exists())
        {
            theDir.mkdir();
        }
    }

    /**
     * Run file chooser and updates text field.
     * @param textfieldPath
     * @throws IOException
     */
    public void browseMovie(TextField textfieldPath) throws IOException
    {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(FILE_CHOOSER_FILTER);
        File dir = new File(CURRENT_DIR);
        fc.setInitialDirectory(dir);
        fc.setTitle(FILE_CHOOSER_TITLE);
        selectedFile = fc.showOpenDialog(textfieldPath.getScene().getWindow());
        if (selectedFile != null)
        {
            textfieldPath.setText(selectedFile.getAbsolutePath());
        }
    }

    /**
     * Saves the movie in the database with the given url
     * @param url imdb url
     * @param mainModel
     * @return true if succeded
     */
    public Movie save(String url, MainWindowModel mainModel)
    {
        // Save only runs if a website and a file is selected.
        if (!url.isEmpty() && selectedFile != null)
        {
            // Removes unneccesary tags.
            if (url.toLowerCase().contains("?"))
            {
                url = url.split("\\?")[0];
            }

            // Get files path
            from = Paths.get(selectedFile.toURI());
            try
            {
                // Get file name, extension and makes a container for a new file name.
                String fileName = selectedFile.getName().split("\\.")[0];
                String extension = "." + selectedFile.getName().split("\\.")[1];
                String newFilename;

                // Create files with known information.
                File f = new File(CURRENT_DIR + MOVIE_DIR + fileName + extension);
                int version = 1;

                // Runs for as long the file already exists.
                while (f.exists())
                {
                    // If file already exists the filename is changed to add version.
                    // (test) -> (test1) -> (test2)
                    newFilename = fileName + version;
                    f = new File(CURRENT_DIR + MOVIE_DIR + newFilename + extension);
                    version++;
                }

                // Makes new path and copies it.
                to = Paths.get(CURRENT_DIR + MOVIE_DIR + f.getName());
                Files.copy(from, to, REPLACE_EXISTING);

                // Relative directory for movie.
                path = MOVIE_DIR + f.getName();
            }
            catch (IOException ex)
            {
                Alert alertError = new Alert(Alert.AlertType.ERROR, ON_COPY_ERROR + ex.getMessage(), ButtonType.OK);
                alertError.showAndWait();
                return null;
            }

            try
            {
                RipManager rip = new RipManager(url);

                List<Genre> genresInMovie = new ArrayList<>();
                List<Genre> allExistingGenres = bll.getAllGenres();

                boolean found;

                // Checks if genre found on website already exists in database.
                // If not the genre is added to the database.
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

                // Add Movie to database and creates movie.
                Movie newMovie = bll.addMovie(rip.getName(), path, genresInMovie, rip.getRating(), -1, rip.getDirectors(), rip.getDuration(), url, rip.getYear(), rip.getSummary(), rip.getImageInBytes());

                // Return Movie.
                return newMovie;
            }
            catch (BLLException ex)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, ON_SAVE_ERROR + ex.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        }
        return null;
    }

    /**
     * Checks whether or not a file is selected.
     * @return
     */
    public boolean fileSelected()
    {
        return selectedFile != null;
    }
}
