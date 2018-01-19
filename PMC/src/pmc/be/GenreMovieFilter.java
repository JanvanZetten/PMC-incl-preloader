package pmc.be;

import java.util.List;

/**
 * En Gruppe
 * @author Alex, Asbjørn & Jan
 */
public class GenreMovieFilter implements MovieFilter
{
    private boolean bypass;
    private List<Genre> genresInMovie;
    private List<String> ChosenGenres;

    /**
     * Constructor for making a Genre movie filter. if a null is passed in one
     * of the conditions. the meetRestictions will always return true
     * @param genresInMovie
     * @param ChosenGenres
     */
    public GenreMovieFilter(List<Genre> genresInMovie, List<String> ChosenGenres)
    {
        if (genresInMovie != null && ChosenGenres != null)
        {
            if (ChosenGenres.size() > 0)
            {
                bypass = false;
                this.genresInMovie = genresInMovie;
                this.ChosenGenres = ChosenGenres;
            }
            else
            {
                bypass = true;
            }
        }
        else
        {
            bypass = true;
        }
    }

    /**
     * Checks if restrictions are meet.
     * @return
     */
    @Override
    public boolean meetsRestrictions()
    {
        if (bypass)
        {
            return true;
        }
        for (String ChosenGenre : ChosenGenres)
        {
            for (Genre genre : genresInMovie)
            {
                if (ChosenGenre.equalsIgnoreCase(genre.getName()))
                {
                    return true;
                }
            }
        }
        return false;
    }

}
