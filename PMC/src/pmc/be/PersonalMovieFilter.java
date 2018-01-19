package pmc.be;

/**
 * En Gruppe
 * @author Alex, Asbjørn & Jan
 */
public class PersonalMovieFilter implements MovieFilter
{
    private int personalRating;
    private int minPersonalRating;

    /**
     * Set filters target and minimum value.
     * @param personalRating
     * @param minPersonalRating
     */
    public PersonalMovieFilter(int personalRating, int minPersonalRating)
    {
        this.personalRating = personalRating;
        this.minPersonalRating = minPersonalRating;
    }

    /**
     * Compares variables.
     * @return
     */
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
