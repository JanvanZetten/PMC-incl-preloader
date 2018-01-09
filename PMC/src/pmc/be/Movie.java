/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.be;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.imageio.ImageIO;
import pmc.dal.IMDbRip;

/**
 *
 * @author janvanzetten
 */
public class Movie
{

    private int id;

    private String name;

    private int year;

    private String duration;

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
     */
    public Movie(String imdbUrl, String filePath)
    {
        IMDbRip imdbRip = new IMDbRip(imdbUrl);

        if (imdbRip.rippedAllInformation())
        {
            this.name = imdbRip.getName();
            this.year = Integer.parseInt(imdbRip.getYear());
            this.duration = imdbRip.getDuration();

            List<String> gs = imdbRip.getGenres();
            for (String g : gs)
            {
            }

            try
            {
                this.imdbRating = Double.parseDouble(imdbRip.getRating());
            }
            catch (NumberFormatException ex)
            {
                this.imdbRating = 0.0;
            }

            this.directors = imdbRip.getDirectors();
            this.filePath = filePath;
            this.imagePath = imdbRip.getImage();
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

    /**
     * Set imagePath from byte array and saves the imagePath as a file. Requires that
 the name and year is set. Used to get imagePath from database.
     * @param imageInBytes Image expressed as byte array.
     */
    public void setImage(byte[] imageInBytes)
    {
        try
        {
            // Open stream for given byte array.
            InputStream in = new ByteArrayInputStream(imageInBytes);
            BufferedImage imgFromDb = ImageIO.read(in);

            // Create file name.
            String fileName = "";
            for (String string : name.split(" "))
            {
                fileName += string + "_";
            }
            fileName += year + ".jpg";

            // Create directory if it is not there.
            File dir = new File("./images/");
            dir.mkdir();

            // Write imagePath to file.
            File outputfile = new File("./images/" + fileName);
            ImageIO.write(imgFromDb, "jpg", outputfile);

            // Saved data to variables.
            this.imageInBytes = imageInBytes;
            this.imagePath = fileName;
        }
        catch (IOException ex)
        {
            throw new RuntimeException("Error getting image from binary data!");
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

    public String getDuration()
    {
        return duration;
    }

    public void setDuration(String duration)
    {
        this.duration = duration;
    }

    public String getDirectors()
    {
        return directors;
    }

    public void setDirector(String directors)
    {
        this.directors = directors;
    }

    public String getImage()
    {
        return imagePath;
    }

    public void setImage(String image)
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

}
