/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.bll;

import pmc.be.Movie;
import pmc.dal.SelectedItemData;

/**
 *
 * @author janvanzetten
 */
public class BLLManager {
    
    SelectedItemData SID;
    
    public BLLManager() {
        SID = new SelectedItemData();
    }

    public void setCurrentMovie(Movie currentMovie) {
        SID.setCurrentMovie(currentMovie);
    }
    
    public Movie getCurrentMovie() {
        return SID.getCurrentMovie();
    }
    
}
