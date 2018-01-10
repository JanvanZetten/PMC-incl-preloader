/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.be;

/**
 *
 * @author Asbamz
 */
public class IMDbMovieFilter implements MovieFilter
{
    private double imdbRating;
    private double minImdbRating;

    public IMDbMovieFilter(double imdbRating, double minImdbRating)
    {
        this.imdbRating = imdbRating;
        this.minImdbRating = minImdbRating;
    }

    @Override
    public boolean meetsRestrictions()
    {
        if (imdbRating >= minImdbRating)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
