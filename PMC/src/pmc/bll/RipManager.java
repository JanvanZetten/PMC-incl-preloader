package pmc.bll;

import java.util.List;
import pmc.dal.DALException;
import pmc.dal.IMDbRip;

/**
 * En Gruppe
 * @author janvanzetten, Alex & Asbamz
 */
public class RipManager
{
    IMDbRip ripper;

    /**
     * Creates IMDbRip object with given URL.
     * @param Url
     * @throws BLLException
     */
    public RipManager(String Url) throws BLLException
    {
        try
        {
            ripper = new IMDbRip(Url);
        }
        catch (DALException ex)
        {
            throw new BLLException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Returns rippedAllInformation.
     * @return
     */
    public boolean rippedAllInformation()
    {
        return ripper.rippedAllInformation();
    }

    /**
     * Returns name of ripped movie.
     * @return
     */
    public String getName()
    {
        return ripper.getName();
    }

    /**
     * Returns year of ripped movie.
     * @return
     */
    public int getYear()
    {
        return ripper.getYear();
    }

    /**
     * Returns duration of ripped movie.
     * @return
     */
    public int getDuration()
    {
        return ripper.getDuration();
    }

    /**
     * Returns genres of ripped movie.
     * @return
     */
    public List<String> getGenres()
    {
        return ripper.getGenres();
    }

    /**
     * Returns rating of ripped movie.
     * @return
     */
    public double getRating()
    {
        return ripper.getRating();
    }

    /**
     * Returns directors of ripped movie.
     * @return
     */
    public String getDirectors()
    {
        return ripper.getDirectors();
    }

    /**
     * Returns image of ripped movie path.
     * @return
     */
    public String getImagePath()
    {
        return ripper.getImagePath();
    }

    /**
     * Returns image of ripped movie in byte array.
     * @return
     */
    public byte[] getImageInBytes()
    {
        return ripper.getImageInBytes();
    }

    /**
     * Returns summary of ripped movie.
     * @return
     */
    public String getSummary()
    {
        return ripper.getSummary();
    }

}
