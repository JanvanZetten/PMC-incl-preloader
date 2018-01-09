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
public class Genre
{

    private int id;

    private String name;

    /**
     * the constucter for the Genre object
     * @param id the Genres id
     * @param name the Genres Name
     */
    public Genre(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    /**
     * Get the value of Name
     *
     * @return the value of Name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Set the value of Name
     *
     * @param name new value of Name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public int getId()
    {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(int id)
    {
        this.id = id;
    }

}
