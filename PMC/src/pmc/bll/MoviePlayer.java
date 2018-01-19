package pmc.bll;

import java.awt.Desktop;
import java.io.File;
import pmc.be.Movie;
import pmc.dal.DALException;
import pmc.dal.SettingsData;

/**
 * En Gruppe
 * @author Alex, Asbjørn & Jan
 */
public class MoviePlayer
{
    private final String CURRENT_DIR;
    private final String MOVIE_DIR = "/Movies/";

    private BLLManager bllManager;

    /**
     * Get source folder and creates it if it is not already created.
     * @throws DALException
     * @throws SecurityException
     */
    public MoviePlayer() throws DALException, SecurityException
    {
        bllManager = new BLLManager();

        CURRENT_DIR = (new SettingsData()).loadSettings().getMovieLocation() + File.separator;
        File theDir = new File(CURRENT_DIR + MOVIE_DIR);
        if (!theDir.exists())
        {
            theDir.mkdir();
        }
    }

    /**
     * Creates the absolute path for the movie and launch it. The last viewed
     * date is set.
     * @param movie wanted to play.
     * @throws BLLException
     */
    public void playMovie(Movie movie) throws BLLException
    {
        File dir = new File(CURRENT_DIR);

        try
        {
            Desktop.getDesktop().open(new File(dir + movie.getFilePath()));
        }
        catch (Exception ex)
        {
            throw new BLLException(ex.getMessage(), ex.getCause());
        }

        setLastViewed(movie);
    }

    /**
     * Get the current date and removes symbols so that it can be saves as an
     * integer. Sets the last viewed date.
     * @param movie
     * @throws BLLException
     */
    private void setLastViewed(Movie movie) throws BLLException
    {
        int dateAsInt = bllManager.getCurrentDateAsInt();
        movie.setLastView(dateAsInt);
        bllManager.updateMovie(movie);
    }
}
