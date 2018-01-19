package pmc.be;

/**
 * En Gruppe
 * @author Alex, Asbjørn & Jan
 */
public class Genre
{
    private int id;
    private String name;

    /**
     * The constructor for the Genre object
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

    /**
     * Overrides toString method.
     * @return
     */
    @Override
    public String toString()
    {
        return name;
    }

    /**
     * Now compares on id.
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Genre)
        {
            if (obj != null)
            {
                Genre objG = (Genre) obj;
                return objG.getId() == this.getId();
            }
        }
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }
}
