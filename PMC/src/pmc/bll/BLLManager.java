/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.bll;

import java.time.LocalDate;
import java.util.List;
import pmc.be.Genre;
import pmc.be.Movie;
import pmc.be.Settings;
import pmc.dal.DALManager;
import pmc.dal.DALException;
import pmc.dal.ItemData;
import pmc.dal.SettingsData;

/**
 *
 * @author janvanzetten
 */
public class BLLManager
{

    DALManager dalManager;
    ItemData ID;
    SettingsData settingsdata;

    public BLLManager()
    {
        dalManager = new DALManager();
        ID = new ItemData();
        settingsdata = new SettingsData();
    }

    public void setCurrentMovie(Movie currentMovie)
    {
        ID.setCurrentMovie(currentMovie);
    }

    public Movie getCurrentMovie()
    {
        return ID.getCurrentMovie();
    }

    /**
     * gets all the genres
     * @return list of genre objects
     * @throws BLLException
     */
    public List<Genre> getAllGenres() throws BLLException
    {
        try
        {
            return dalManager.getAllGenres();
        }
        catch (DALException ex)
        {
            throw new BLLException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * gets all the movies
     * @return list of movie objects
     * @throws BLLException
     */
    public List<Movie> getAllMovies() throws BLLException
    {
        try
        {
            return dalManager.getAllMovies();
        }
        catch (DALException ex)
        {
            throw new BLLException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Make a new Genre
     * @param name the name of the new Genre
     * @return the newly made genre if it succeeds
     * @throws pmc.bll.BLLException
     */
    public Genre addGenre(String name) throws BLLException
    {
        try
        {
            return dalManager.addGenre(name);
        }
        catch (DALException ex)
        {
            throw new BLLException(ex.getMessage(), ex.getCause());
        }
    }
    
    /**
     * Make a new Movie
     * @param name
     * @param filePath
     * @param genres
     * @param imdbRating
     * @param personalRating
     * @param Directors
     * @param duration
     * @param ImdbUrl
     * @param year
     * @param summary
     * @param imageInBytes
     * @return a Movie object with lastView of -1
     * @throws BLLException
     */
    public Movie addMovie(String name, String filePath, List<Genre> genres,
            double imdbRating, int personalRating, String Directors,
            int duration, String ImdbUrl, int year, String summary, byte[] imageInBytes) throws BLLException
    {
        try
        {
            return dalManager.addMovie(name, filePath, genres, imdbRating, personalRating, Directors, duration, ImdbUrl, year, summary, imageInBytes);
        }
        catch (DALException ex)
        {
            throw new BLLException(ex.getMessage(), ex.getCause());
        }

    }

    /**
     * Deletes the given genre. There should be no movies with this genre for it
     * to be deleted
     * @param genre the genre to delete
     * @return true if the genre is deleted
     * @throws BLLException
     */
    public boolean deleteGenre(Genre genre) throws BLLException
    {
        try
        {
            return dalManager.deleteGenre(genre);
        }
        catch (DALException ex)
        {
            throw new BLLException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Deletes the given movie.
     * @param movie the movie to delete
     * @return true if movie is deleted
     * @throws BLLException
     */
    public boolean deleteMovie(Movie movie) throws BLLException
    {
        try
        {
            return dalManager.deleteMovie(movie);
        }
        catch (DALException ex)
        {
            throw new BLLException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Updates the movie with the same id as the given movie object
     * @param updatedMovie should be the updated version with the same id
     * @throws BLLException
     */
    public void updateMovie(Movie updatedMovie) throws BLLException
    {
        try
        {
            dalManager.updateMovie(updatedMovie);
        }
        catch (DALException ex)
        {
            throw new BLLException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * delete unused genres
     * @return a list of ids of the deleted genres
     * @throws BLLException 
     */
    public List<Integer> deleteUnusedGenres() throws BLLException 
    {
        try 
        {
            return dalManager.deleteUnusedGenres();
        } 
        catch (DALException ex) 
        {
            throw new BLLException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Finds movies that are over the set interval and have a lower rating than 6 on
     * when starting the program.
     * @throws pmc.bll.BLLException
     */
    public void setOutdatedMovies() throws BLLException 
    {
        try 
        {
        List<Movie> movies = dalManager.getAllMovies();
        for (Movie movy : movies) 
        {
            if (checkOutdated(movy))
                {
                    ID.addToTBDeletedList(movy);
                }
            }
        }
        catch (DALException ex) 
        {
            throw new BLLException(ex.getMessage(), ex.getCause());
        }
    }
    
    public List<Movie> getTBDeletedList() 
    {
        return ID.getTBDeletedList();
    }
    
    /**
     * return an int with the date in format yyyymmdd
     * @return 
     */
    public int getCurrentDateAsInt()
    {
        String date = LocalDate.now().toString();
        date = date.replaceAll("-", "");
        return Integer.parseInt(date);
    }
    
    
    /**
     * Save the Settings 
     * @param settings the settings to save
     * @throws pmc.bll.BLLException
     */
    public void saveSettings(Settings settings) throws BLLException{
        try {
            settingsdata.saveSettings(settings);
        } catch (DALException ex) {
            throw new BLLException(ex.getMessage(), ex.getCause());
        }
    }
    
    
    /**
     * Load the earlyer saved settings our if it exists else the defualt settings
     * @return
     * @throws BLLException 
     */
    public Settings loadSettings() throws BLLException{
        try {
            return settingsdata.loadSettings();
        } catch (DALException ex) {
            throw new BLLException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Check for outdated. Outdated when personalscore is under 6 and lastviwed has to be more then the interval back from the current date
     * @param movie the movie object to check
     * @return true if oudated
     * @throws BLLException 
     */
    private boolean checkOutdated(Movie movie) throws BLLException {
        //if the score is 6 or more it will never be outdated
        if (movie.getPersonalRating() >= 6){
                return false;
            }
        
        try {
            int interval = settingsdata.loadSettings().getInterval();
            //if the interval is -1 it meens never
            if (interval == -1){
                return false;
            }
            
            int lastview = movie.getLastView();
            
            if (lastview == -1){
                return false;
            }
            
            int lastviewMonths = (lastview/100) % 100;
            
            lastviewMonths += interval;
            
            int extraYears = 0;
            
            while(lastviewMonths > 12){
                extraYears ++;
                lastviewMonths -= 12;
            }
            int years = (lastview/10000 + extraYears);
            
            int lastViewPlusIntervalDate = (years*10000) + (lastviewMonths * 100) + (lastview % 100);
            
            return getCurrentDateAsInt() >= lastViewPlusIntervalDate;
            
        } catch (DALException ex) {
            throw new BLLException(ex.getMessage(), ex.getCause());
        }

    }
    
    
}
