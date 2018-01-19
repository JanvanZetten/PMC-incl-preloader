package pmc.be;

import java.util.List;
import pmc.dal.DALException;
import pmc.dal.Bytes2Image;

/**
 * En Gruppe
 * @author Alex, Asbjørn & Jan
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
    private String summary;
    private int lastView;
    private String filePath;
    private String imagePath;
    private byte[] imageInBytes;
    private String imdbUrl;

    /**
     * The constructor for the Movie object
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
     * Contructor with all data.
     * @param id
     * @param name
     * @param year
     * @param duration
     * @param genres
     * @param personalRating
     * @param imdbRating
     * @param directors
     * @param lastView
     * @param filePath
     * @param imdbUrl
     */
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
     * @throws pmc.dal.DALException
     */
    public void setImage(byte[] imageInBytes) throws DALException
    {
        if (imageInBytes.length != 0)
        {
            Bytes2Image b2i = new Bytes2Image(name, year, imageInBytes);
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

    /**
     * Get year.
     * @return
     */
    public int getYear()
    {
        return year;
    }

    /**
     * Set year.
     * @param year
     */
    public void setYear(int year)
    {
        this.year = year;
    }

    /**
     * Get duration.
     * @return
     */
    public int getDuration()
    {
        return duration;
    }

    /**
     * Set duration.
     * @param duration
     */
    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    /**
     * Get directors.
     * @return
     */
    public String getDirectors()
    {
        return directors;
    }

    /**
     * Set directors.
     * @param directors
     */
    public void setDirectors(String directors)
    {
        this.directors = directors;
    }

    /**
     * Get summary.
     * @return
     */
    public String getSummary()
    {
        return summary;
    }

    /**
     * Set summary.
     * @param summary
     */
    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    /**
     * Get image path.
     * @return
     */
    public String getImage()
    {
        return imagePath;
    }

    /**
     * Set image path.
     * @param image
     */
    public void setImagePath(String image)
    {
        this.imagePath = image;
    }

    /**
     * Get image as byte array.
     * @return
     */
    public byte[] getImageInBytes()
    {
        return imageInBytes;
    }

    /**
     * Set image as byte array.
     * @param imageInBytes
     */
    public void setImageInBytes(byte[] imageInBytes)
    {
        this.imageInBytes = imageInBytes;
    }

    /**
     * Get IMDb URL.
     * @return
     */
    public String getImdbUrl()
    {
        return imdbUrl;
    }

    /**
     * Set IMDb URL.
     * @param imdbUrl
     */
    public void setImdbUrl(String imdbUrl)
    {
        this.imdbUrl = imdbUrl;
    }

    /**
     * Override toString method.
     * @return
     */
    @Override
    public String toString()
    {
        return "Movie{" + "id=" + id + ", name=" + name + ", year=" + year + ", duration=" + duration + ", genres=" + genres + ", personalRating=" + personalRating + ", imdbRating=" + imdbRating + ", directors=" + directors + ", summary=" + summary + ", lastView=" + lastView + ", filePath=" + filePath + ", imagePath=" + imagePath + ", imdbUrl=" + imdbUrl + '}';
    }
}
