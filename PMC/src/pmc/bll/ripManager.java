/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.bll;

import java.util.List;
import pmc.dal.DALException;
import pmc.dal.IMDbRip;

/**
 *
 * @author janvanzetten
 */
public class ripManager
{
    IMDbRip ripper;

    public ripManager(String Url) throws BLLException
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

    public boolean rippedAllInformation()
    {
        return ripper.rippedAllInformation();
    }

    public String getName()
    {
        return ripper.getName();
    }

    public int getYear()
    {
        return ripper.getYear();
    }

    public int getDuration()
    {
        return ripper.getDuration();
    }

    public List<String> getGenres()
    {
        return ripper.getGenres();
    }

    public double getRating()
    {
        return ripper.getRating();
    }

    public String getDirectors()
    {
        return ripper.getDirectors();
    }

    public String getImagePath()
    {
        return ripper.getImagePath();
    }

    public byte[] getImageInBytes()
    {
        return ripper.getImageInBytes();
    }

}
