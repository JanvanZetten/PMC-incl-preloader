/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.dal;

import java.util.List;
import pmc.be.Genre;
import pmc.be.Movie;

/**
 *
 * @author janvanzetten
 */
public class DALManager {
    private DBManager Database = new DBManager();
    
    /**
     * gets all the genres
     * @return list of genre objects
     * @throws pmc.dal.DalExeption
     */
    public List<Genre> getAllGenres() throws DalExeption{
        return Database.getAllGenres();
    }
    
    /**
     * gets all the movies
     * @return list of movie objects
     * @throws pmc.dal.DalExeption
     */
    public List<Movie> getAllMovies() throws DalExeption{
        return Database.getAllMovies();
    }
    
    /**
     * Make a new Genre
     * @param name the name of the new Genre
     * @return the newly made genre if it succeds
     */
    public Genre addGenre(String name) throws DalExeption{
        return Database.addNewGenre(name);
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
     * @throws DalExeption 
     */
    public Movie addMovie(String name, String filePath, List<Genre> genres,
            double imdbRating, int personalRating, String Directors,
            int duration, String ImdbUrl, int year, byte[] imageInBytes) throws DalExeption{
        
        return Database.addMovie(name, filePath, genres, imdbRating, personalRating, 
                Directors, duration, ImdbUrl, year, imageInBytes);
               
    }
    
    /**
     * Delets the given genre. There should be no movies with this genre for it to be deleted 
     * @param genre the genre to delete
     * @return true if the genre is deleted
     * @throws DalExeption 
     */    
    public boolean deleteGenre(Genre genre) throws DalExeption{
        return Database.deleteGenre(genre);
    }
    
    /**
     * Deletes the given movie.
     * @param movie the movie to delete
     * @return true if movie is deleted
     * @throws DalExeption 
     */
    public boolean deleteMovie(Movie movie) throws DalExeption{
        return Database.deleteMovie(movie);
    }
    
    /**
     * Updates the movie with the same id as the given movie object
     * @param updatedMovie should be the updated version with the same id
     * @throws DalExeption 
     */
    public void updateMovie(Movie updatedMovie) throws DalExeption{
        Database.updateMovie(updatedMovie);
    }
    
    
}
