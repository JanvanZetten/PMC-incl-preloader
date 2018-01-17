/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.be;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author janvanzetten
 */
public class Settings implements Serializable{
    
    int interval;
    String movieLocation;

    public Settings() {
        interval = 20000;
        movieLocation = System.getProperty("user.dir") + File.separator;
    }

    public Settings(int interval, String movieLocation) {
        this.interval = interval;
        this.movieLocation = movieLocation;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getMovieLocation() {
        return movieLocation;
    }

    public void setMovieLocation(String movieLocation) {
        this.movieLocation = movieLocation;
    }
    
    
    
    
    
    
}
