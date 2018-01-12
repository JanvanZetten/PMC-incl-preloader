/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.be;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.transformation.FilteredList;

/**
 *
 * @author janvanzetten
 */
public class GenreMovieFilter implements MovieFilter {

    private boolean bypass;
    private List<Genre> genresInMovie;
    private List<String> ChosenGenres;

    /**
     * Constucter for making a Genre movie filter. if a null is passed in one of
     * the conditions. the meetRestictions will always return true
     * @param genresInMovie
     * @param ChosenGenres
     */
    public GenreMovieFilter(List<Genre> genresInMovie, List<String> ChosenGenres) {
        if (genresInMovie != null && ChosenGenres != null) {
            if (ChosenGenres.size() > 0) {
                bypass = false;
                this.genresInMovie = genresInMovie;
                this.ChosenGenres = ChosenGenres;
            } else {
                bypass = true;
            }
        } else {
            bypass = true;
        }
    }

    @Override
    public boolean meetsRestrictions() {
        if (bypass) {
            return true;
        }
        for (String ChosenGenre : ChosenGenres) {
            for (Genre genre : genresInMovie) {
                if (ChosenGenre.equalsIgnoreCase(genre.getName())) {
                    return true;
                }
            }

        }
        return false;
    }

}
