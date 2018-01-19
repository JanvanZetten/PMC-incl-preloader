package pmc.dal;

import java.util.List;
import pmc.be.Genre;
import pmc.be.Movie;

/**
 * En Gruppe
 * @author Alex, Asbjørn & Jan
 */
public class DALManager
{
    private DBManager database = new DBManager();

    /**
     * gets all the genres
     *
     * @return list of genre objects
     * @throws pmc.dal.DALException
     */
    public List<Genre> getAllGenres() throws DALException
    {
        return database.getAllGenres();
    }

    /**
     * gets all the movies
     *
     * @return list of movie objects
     * @throws pmc.dal.DALException
     */
    public List<Movie> getAllMovies() throws DALException
    {
        return database.getAllMovies();
    }

    /**
     * Make a new Genre
     *
     * @param name the name of the new Genre
     * @return the newly made genre if it succeds
     * @throws pmc.dal.DALException
     */
    public Genre addGenre(String name) throws DALException
    {
        if (database.checkForExistingGenre(name))
        {
            return database.addNewGenre(name);
        }
        else
        {
            throw new DALException("Genre already exists");
        }
    }

    /**
     * Make a new Movie
     *
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
     * @throws DALException
     */
    public Movie addMovie(String name, String filePath, List<Genre> genres,
            double imdbRating, int personalRating, String Directors,
            int duration, String ImdbUrl, int year, String summary, byte[] imageInBytes) throws DALException
    {

        if (database.checkForMovie(name, ImdbUrl))
        {

            return database.addMovie(name, filePath, genres, imdbRating, personalRating, Directors, duration, ImdbUrl, year, imageInBytes, summary);
        }
        else
        {
            throw new DALException("Movie with name or imdb link already exists");
        }
    }

    /**
     * Delets the given genre. There should be no movies with this genre for it
     * to be deleted
     *
     * @param genre the genre to delete
     * @return true if the genre is deleted
     * @throws DALException
     */
    public boolean deleteGenre(Genre genre) throws DALException
    {
        return database.deleteGenre(genre);
    }

    /**
     * Deletes the given movie.
     *
     * @param movie the movie to delete
     * @return true if movie is deleted
     * @throws DALException
     */
    public boolean deleteMovie(Movie movie) throws DALException
    {
        return database.deleteMovie(movie);
    }

    /**
     * Updates the movie with the same id as the given movie object
     *
     * @param updatedMovie should be the updated version with the same id
     * @throws DALException
     */
    public void updateMovie(Movie updatedMovie) throws DALException
    {
        database.updateMovie(updatedMovie);
    }

    /**
     * delets all unused genres
     * @return a list of deleted ids
     * @throws pmc.dal.DALException
     */
    public List<Integer> deleteUnusedGenres() throws DALException
    {
        return database.deleteUnusedGenres();
    }
}
