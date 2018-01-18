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
    
    private int interval;
    private String movieLocation;
    private final int TWO_YEARS = 24;

    public Settings() {
        interval = TWO_YEARS;
        movieLocation = System.getProperty("user.dir") + File.separator;
    }

    /**
     * constructer
     * @param interval as int in months
     * @param movieLocation movie folder location as String
     */
    public Settings(int interval, String movieLocation) {
        this.interval = interval;
        this.movieLocation = movieLocation;
    }

    /**
     * returns the interval in months
     * @return interval in months
     */
    public int getInterval() {
        return interval;
    }

    /**
     * set the interval
     * @param interval as int in months
     */
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
