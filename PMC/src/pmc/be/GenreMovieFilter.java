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
    private List<String> chosenGenres;

    /**
     * Constructor for making a Genre movie filter. if a null is passed in one
     * of the conditions. the meetRestictions will always return true
     * @param genresInMovie
     * @param chosenGenres
     */
    public GenreMovieFilter(List<Genre> genresInMovie, List<String> chosenGenres)
    {
        if (genresInMovie != null && chosenGenres != null)
        {
            if (chosenGenres.size() > 0)
            {
                bypass = false;
                this.genresInMovie = genresInMovie;
                this.chosenGenres = chosenGenres;
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
        for (String ChosenGenre : chosenGenres)
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
