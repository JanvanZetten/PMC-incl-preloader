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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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

    public void playMovie(Movie movie) throws BLLException
    {
        String currentDir = System.getProperty("user.dir") + File.separator;
        File dir = new File(currentDir);

        try
        {
            Desktop.getDesktop().open(new File(dir + movie.getFilePath()));
        }
        catch (IOException ex)
        {
            throw new BLLException(ex.getMessage(), ex.getCause());
        }

        setLastViewed(movie);
    }

    private void setLastViewed(Movie movie) throws BLLException
    {

        String date = LocalDate.now().toString();
        date = date.replaceAll("-", "");
        int dateAsInt = Integer.parseInt(date);
        movie.setLastView(dateAsInt);
        bllManager.updateMovie(movie);
    }
}
