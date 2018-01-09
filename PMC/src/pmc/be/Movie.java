/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.be;

import java.util.List;

/**
 *
 * @author janvanzetten
 */
public class Movie {
    
    private String name;
    
    private int Id;
    
    private int personalRating;
    
    private int ImdbRating;
    
    private int lastView;
    
    private String FilePath;

    private List<Genre> Genres;

    /**
     * the constructor for the Movie object
     * @param Id the unique object id 
     * @param name the movie name
     * @param FilePath the file path
     * @param Genres the list of genres
     */
    public Movie(int Id, String name, String FilePath, List<Genre> Genres) {
        this.name = name;
        this.Id = Id;
        this.FilePath = FilePath;
        this.Genres = Genres;
        personalRating = -1;
        ImdbRating = -1;
        lastView = -1;
    }
    
    
    /**
     * Get a list with the movies genres
     * @return 
     */
    public List<Genre> getGenres(){
        return Genres;
    }
    
    /**
     * Add a Genre to the list og genres
     * @param genre 
     */
    public void addGenre(Genre genre){
        Genres.add(genre);
    }
    
    /**
     * Replace the current list of Genres with this list
     * @param Genres the new list
     */
    public void setGenres(List<Genre> Genres){
        this.Genres = Genres;
    }      
            
    /**
     * Get the value of FilePath
     *
     * @return the value of FilePath
     */
    public String getFilePath() {
        return FilePath;
    }

    /**
     * Set the value of FilePath
     *
     * @param FilePath new value of FilePath
     */
    public void setFilePath(String FilePath) {
        this.FilePath = FilePath;
    }


    /**
     * Get the value of lastView
     *
     * @return the value of lastView
     */
    public int getLastView() {
        return lastView;
    }

    /**
     * Set the value of lastView
     *
     * @param lastView new value of lastView
     */
    public void setLastView(int lastView) {
        this.lastView = lastView;
    }

    

    /**
     * Get the value of ImdbRating
     *
     * @return the value of ImdbRating
     */
    public int getImdbRating() {
        return ImdbRating;
    }

    /**
     * Set the value of ImdbRating
     *
     * @param ImdbRating new value of ImdbRating
     */
    public void setImdbRating(int ImdbRating) {
        this.ImdbRating = ImdbRating;
    }


    /**
     * Get the value of personalRating
     *
     * @return the value of personalRating
     */
    public int getPersonalRating() {
        return personalRating;
    }

    /**
     * Set the value of personalRating
     *
     * @param personalRating new value of personalRating
     */
    public void setPersonalRating(int personalRating) {
        this.personalRating = personalRating;
    }


    /**
     * Get the value of Id
     *
     * @return the value of Id
     */
    public int getId() {
        return Id;
    }

    /**
     * Set the value of Id
     *
     * @param Id new value of Id
     */
    public void setId(int Id) {
        this.Id = Id;
    }


    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

}
