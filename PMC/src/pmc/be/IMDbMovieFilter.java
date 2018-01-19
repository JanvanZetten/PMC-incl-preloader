package pmc.be;

/**
 * En Gruppe
 * @author Alex, Asbjørn & Jan
 */
public class IMDbMovieFilter implements MovieFilter
{
    private double imdbRating;
    private double minImdbRating;

    /**
     * Set filters target and minimum value.
     * @param imdbRating
     * @param minImdbRating
     */
    public IMDbMovieFilter(double imdbRating, double minImdbRating)
    {
        this.imdbRating = imdbRating;
        this.minImdbRating = minImdbRating;
    }

    /**
     * Compares variables.
     * @return
     */
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
