/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.bll;

import java.util.List;
import pmc.be.Genre;
import pmc.be.Movie;
import pmc.dal.DALManager;
import pmc.dal.DALException;
import pmc.dal.SelectedItemData;

/**
 *
 * @author janvanzetten
 */
public class BLLManager
{

    DALManager dalManager;
    SelectedItemData SID;

    public BLLManager()
    {
        dalManager = new DALManager();
        SID = new SelectedItemData();
    }

    public void setCurrentMovie(Movie currentMovie)
    {
        SID.setCurrentMovie(currentMovie);
    }

    public Movie getCurrentMovie()
    {
        return SID.getCurrentMovie();
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
     * @param imageInBytes
     * @return a Movie object with lastView of -1
     * @throws BLLException
     */
    public Movie addMovie(String name, String filePath, List<Genre> genres,
            double imdbRating, int personalRating, String Directors,
            int duration, String ImdbUrl, int year, byte[] imageInBytes) throws BLLException
    {
        try
        {
            return dalManager.addMovie(name, filePath, genres, imdbRating, personalRating,
                    Directors, duration, ImdbUrl, year, imageInBytes);
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
}
