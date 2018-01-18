package pmc.be;

import java.io.File;
import java.io.Serializable;

/**
 * En Gruppe
 * @author janvanzetten, Alex & Asbamz
 */
public class Settings implements Serializable
{
    private final int TWO_YEARS = 24;

    private int interval;
    private String movieLocation;

    /**
     * Set default interval and movie location.
     */
    public Settings()
    {
        interval = TWO_YEARS;
        movieLocation = System.getProperty("user.dir") + File.separator;
    }

    /**
     * Constructor.
     * @param interval as int in months
     * @param movieLocation movie folder location as String
     */
    public Settings(int interval, String movieLocation)
    {
        this.interval = interval;
        this.movieLocation = movieLocation;
    }

    /**
     * Returns the interval in months
     * @return interval in months
     */
    public int getInterval()
    {
        return interval;
    }

    /**
     * Set the interval
     * @param interval as int in months
     */
    public void setInterval(int interval)
    {
        this.interval = interval;
    }

    /**
     * Returns movie location.
     * @return
     */
    public String getMovieLocation()
    {
        return movieLocation;
    }

    /**
     * Sets movie location.
     * @param movieLocation
     */
    public void setMovieLocation(String movieLocation)
    {
        this.movieLocation = movieLocation;
    }

}
