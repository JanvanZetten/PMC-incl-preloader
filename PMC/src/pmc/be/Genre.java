/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.be;

/**
 *
 * @author janvanzetten
 */
public class Genre {
    
    private int id;
    
    private String Name;

    /**
     * the constucter for the Genre object
     * @param id the Genres id
     * @param Name the Genres Name
     */
    public Genre(int id, String Name) {
        this.id = id;
        this.Name = Name;
    }

    
    /**
     * Get the value of Name
     *
     * @return the value of Name
     */
    public String getName() {
        return Name;
    }

    /**
     * Set the value of Name
     *
     * @param Name new value of Name
     */
    public void setName(String Name) {
        this.Name = Name;
    }


    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(int id) {
        this.id = id;
    }

}
