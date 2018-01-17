/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.dal;

import pmc.be.Movie;

/**
 *
 * @author Alex
 */
public class ItemData {
    
    private Movie currentMovie;
    
    public ItemData() {
    }

    public void setCurrentMovie(Movie currentMovie) {
        this.currentMovie = currentMovie;
    }
    
    public Movie getCurrentMovie() {
        return currentMovie;
    }
    
}
