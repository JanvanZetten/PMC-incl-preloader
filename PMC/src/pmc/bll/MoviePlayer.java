/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.bll;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import pmc.be.Movie;

/**
 *
 * @author Asbamz
 */
public class MoviePlayer
{
    BLLManager bllManager;

    public MoviePlayer()
    {
        bllManager = new BLLManager();
    }

    /**
     * Creates the absolute path for the movie and launch it. The last viewed
     * date is set.
     * @param movie wanted to play.
     * @throws BLLException
     */
    public void playMovie(Movie movie) throws BLLException
    {
        String currentDir = System.getProperty("user.dir") + File.separator;
        File dir = new File(currentDir);

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
        String date = LocalDate.now().toString();
        date = date.replaceAll("-", "");
        int dateAsInt = Integer.parseInt(date);
        movie.setLastView(dateAsInt);
        bllManager.updateMovie(movie);
    }
}
