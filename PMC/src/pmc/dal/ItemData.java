package pmc.dal;

import java.util.ArrayList;
import java.util.List;
import pmc.be.Movie;

/**
 * En Gruppe
 * @author janvanzetten, Alex & Asbamz
 */
public class ItemData
{
    private Movie currentMovie;
    private List<Movie> tBDeleted;

    /**
     * Initiates new ArrayList;
     */
    public ItemData()
    {
        tBDeleted = new ArrayList<Movie>();
    }

    /**
     * Set currentMovie to given movie.
     * @param currentMovie
     */
    public void setCurrentMovie(Movie currentMovie)
    {
        this.currentMovie = currentMovie;
    }

    /**
     * Returns currentMovie.
     * @return
     */
    public Movie getCurrentMovie()
    {
        return currentMovie;
    }

    /**
     * Add movie to Deleted list.
     * @param movie
     */
    public void addToTBDeletedList(Movie movie)
    {
        tBDeleted.add(movie);
    }

    /**
     * Return deleted list.
     * @return
     */
    public List<Movie> getTBDeletedList()
    {
        return tBDeleted;
    }
}
