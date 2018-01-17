/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.dal;

import java.util.ArrayList;
import java.util.List;
import pmc.be.Movie;

/**
 *
 * @author Alex
 */
public class ItemData {
    
    private Movie currentMovie;
    private List<Movie> TBDeleted;
    
    public ItemData() {
        TBDeleted = new ArrayList<Movie>();
    }

    public void setCurrentMovie(Movie currentMovie) {
        this.currentMovie = currentMovie;
    }
    
    public Movie getCurrentMovie() {
        return currentMovie;
    }

    public void addToTBDeletedList(Movie movy) {
        TBDeleted.add(movy);
    }

    public List<Movie> getTBDeletedList() {
        return TBDeleted;
    }
    
}
