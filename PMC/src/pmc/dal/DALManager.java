/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.dal;

import java.util.List;
import pmc.be.Genre;
import pmc.be.Movie;

/**
 *
 * @author janvanzetten
 */
public class DALManager {
    private DBManager Database = new DBManager();
    
    /**
     * gets all the genres
     * @return list of genre objects
     */
    public List<Genre> getAllGenres(){
        return Database.getAllGenres();
    }
    
    /**
     * gets all the movies
     * @return list of movie objects
     */
    public List<Movie> getAllMovies(){
        return Database.getAllMovies();
    }
    
    
}
