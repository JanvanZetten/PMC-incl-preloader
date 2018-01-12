/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.be;

import java.util.List;
import pmc.dal.DALException;
import pmc.dal.IMDbRip;
import pmc.dal.bytes2Image;

/**
 *
 * @author janvanzetten
 */
public class Movie
{

    private int id;

    private String name;

    private int year;

    private int duration;

    private List<Genre> genres;

    private int personalRating;

    private double imdbRating;

    private String directors;

    private int lastView;

    private String filePath;

    private String imagePath;

    private byte[] imageInBytes;

    private String imdbUrl;

    /**
     * Make Movie object from IMDbRip.
     * @param imdbUrl IMDb Movie site.
     * @param filePath Path to movie file.
     * @throws pmc.dal.DALException
     */
    public Movie(String imdbUrl, String filePath) throws DALException
    {
        IMDbRip imdbRip = new IMDbRip(imdbUrl);

        if (imdbRip.rippedAllInformation())
        {
            this.name = imdbRip.getName();
            this.year = imdbRip.getYear();
            this.duration = imdbRip.getDuration();

            List<String> gs = imdbRip.getGenres();
            for (String g : gs)
            {
            }

            this.imdbRating = imdbRip.getRating();
            this.directors = imdbRip.getDirectors();
            this.filePath = filePath;
            this.imagePath = imdbRip.getImagePath();
            this.imageInBytes = imdbRip.getImageInBytes();
            this.imdbUrl = imdbUrl;
        }
        else
        {
            throw new RuntimeException("Error did not get all information from IMDb URL!");
        }
    }

    /**
     * the constructor for the Movie object
     * @param id the unique object id
     * @param name the movie name
     * @param filePath the file path
     * @param genres the list of genres
     */
    public Movie(int id, String name, String filePath, List<Genre> genres)
    {
        this.name = name;
        this.id = id;
        this.filePath = filePath;
        this.genres = genres;
        personalRating = -1;
        imdbRating = -1;
        lastView = -1;
    }

    public Movie(int id, String name, int year, int duration, List<Genre> genres, int personalRating, double imdbRating, String directors, int lastView, String filePath, String imdbUrl)
    {
        this.id = id;
        this.name = name;
        this.year = year;
        this.duration = duration;
        this.genres = genres;
        this.personalRating = personalRating;
        this.imdbRating = imdbRating;
        this.directors = directors;
        this.lastView = lastView;
        this.filePath = filePath;
        this.imdbUrl = imdbUrl;
    }

    /**
     * Set imagePath from byte array and saves the imagePath as a file. Requires
     * that the name and year is set. Used to get imagePath from database.
     * @param imageInBytes Image expressed as byte array.
     */
    public void setImage(byte[] imageInBytes) throws DALException
    {
        if (imageInBytes.length != 0)
        {
            bytes2Image b2i = new bytes2Image(name, year, imageInBytes);
            this.imageInBytes = b2i.getImageInBytes();
            this.imagePath = b2i.getImagePath();
        }
    }

    /**
     * Get a list with the movies genres
     * @return
     */
    public List<Genre> getGenres()
    {
        return genres;
    }

    /**
     * Add a Genre to the list og genres
     * @param genre
     */
    public void addGenre(Genre genre)
    {
        genres.add(genre);
    }

    /**
     * Replace the current list of Genres with this list
     * @param Genres the new list
     */
    public void setGenres(List<Genre> Genres)
    {
        this.genres = Genres;
    }

    /**
     * Get the value of FilePath
     *
     * @return the value of FilePath
     */
    public String getFilePath()
    {
        return filePath;
    }

    /**
     * Set the value of FilePath
     *
     * @param filePath new value of FilePath
     */
    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    /**
     * Get the value of lastView
     *
     * @return the value of lastView
     */
    public int getLastView()
    {
        return lastView;
    }

    /**
     * Set the value of lastView
     *
     * @param lastView new value of lastView
     */
    public void setLastView(int lastView)
    {
        this.lastView = lastView;
    }

    /**
     * Get the value of ImdbRating
     *
     * @return the value of ImdbRating
     */
    public double getImdbRating()
    {
        return imdbRating;
    }

    /**
     * Set the value of ImdbRating
     *
     * @param imdbRating new value of ImdbRating
     */
    public void setImdbRating(double imdbRating)
    {
        this.imdbRating = imdbRating;
    }

    /**
     * Get the value of personalRating
     *
     * @return the value of personalRating
     */
    public int getPersonalRating()
    {
        return personalRating;
    }

    /**
     * Set the value of personalRating
     *
     * @param personalRating new value of personalRating
     */
    public void setPersonalRating(int personalRating)
    {
        this.personalRating = personalRating;
    }

    /**
     * Get the value of Id
     *
     * @return the value of Id
     */
    public int getId()
    {
        return id;
    }

    /**
     * Set the value of Id
     *
     * @param id new value of Id
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public String getDirectors()
    {
        return directors;
    }

    public void setDirectors(String directors)
    {
        this.directors = directors;
    }

    public String getImage()
    {
        return imagePath;
    }

    public void setImagePath(String image)
    {
        this.imagePath = image;
    }

    public byte[] getImageInBytes()
    {
        return imageInBytes;
    }

    public void setImageInBytes(byte[] imageInBytes)
    {
        this.imageInBytes = imageInBytes;
    }

    public String getImdbUrl()
    {
        return imdbUrl;
    }

    public void setImdbUrl(String imdbUrl)
    {
        this.imdbUrl = imdbUrl;
    }

    @Override
    public String toString()
    {
        return "Movie{" + "id=" + id + ", name=" + name + ", year=" + year + ", duration=" + duration + ", genres=" + genres + ", personalRating=" + personalRating + ", imdbRating=" + imdbRating + ", directors=" + directors + ", lastView=" + lastView + ", filePath=" + filePath + ", imagePath=" + imagePath + ", imdbUrl=" + imdbUrl + '}';
    }
}
