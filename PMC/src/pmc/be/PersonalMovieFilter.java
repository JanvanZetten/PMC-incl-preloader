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
public class PersonalMovieFilter implements MovieFilter
{
    private int personalRating;
    private int minPersonalRating;

    public PersonalMovieFilter(int personalRating, int minPersonalRating)
    {
        this.personalRating = personalRating;
        this.minPersonalRating = minPersonalRating;
    }

    @Override
    public boolean meetsRestrictions()
    {
        if (personalRating >= minPersonalRating)
        {
            return true;
        }
        else if (personalRating == -1 && minPersonalRating == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
