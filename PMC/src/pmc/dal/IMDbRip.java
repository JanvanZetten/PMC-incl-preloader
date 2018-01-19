package pmc.dal;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * En Gruppe
 * @author Alex, Asbjørn & Jan
 */
public class IMDbRip
{
    private final String NO_DIRECTOR = "None";
    private final String MOVIE_SITE = "imdb.com/title/";
    private final String NOT_A_MOVIE_SITE = "Not a IMDb movie website! ";
    private final String ERROR_READING_IMDB = "Error reading information from IMDb! ";
    private final String ERROR_COPYING_IMAGE = "Error copying image! ";
    private final String ERROR_BINARY_IMAGE = "Error turning image into binary data! ";
    private final String IMAGE_EXTENSION = "jpg";
    private final String IMAGE_DIRECTORY = "./images/";
    private final String IMAGE_SEPERATOR = "_";
    private final String NAME_TAG = "itemprop=\"name\" class=\"\"";
    private final String YEAR_TAG = "id=\"titleYear\"";
    private final String DURATION_TAG = "time itemprop=\"duration\"";
    private final String GENRE_TAG = "class=\"itemprop\" itemprop=\"genre\"";
    private final String RATING_TAG = "span itemprop=\"ratingValue\"";
    private final String DIRECTOR_TAG = "itemprop=\"director\"";
    private final String SUMMARY_TAG = "class=\"summary_text\" itemprop=\"description\"";
    private final String IMAGE_TAG = "class=\"poster\"";

    private boolean rippedAllInformation;
    private String name;
    private int year;
    private int duration;
    private List<String> genres;
    private double rating;
    private String directors;
    private String summary;
    private String imagePath;
    private byte[] imageInBytes;

    /**
     * Constructor for IMDbRip. Run ripInformationAsFile() method, which rips
     * IMDb URL Movie site.
     * @param imdbUrl IMDb movie site.
     * @throws DALException
     */
    public IMDbRip(String imdbUrl) throws DALException
    {
        rippedAllInformation = false;
        name = null;
        genres = new ArrayList<>();
        directors = null;
        summary = null;
        imagePath = null;

        ripInformationAsFile(imdbUrl);
    }

    /**
     * Rip IMDb Website by reading it as a stream.
     * @param imdbUrl String containing URL.
     */
    private void ripInformationAsFile(String imdbUrl) throws DALException
    {
        // In case the website is not a IMDb Movie URL.
        if (!imdbUrl.toLowerCase().contains(MOVIE_SITE))
        {
            throw new DALException(NOT_A_MOVIE_SITE + imdbUrl);
        }

        // Removes unneccesary tags.
        if (imdbUrl.toLowerCase().contains("?"))
        {
            imdbUrl = imdbUrl.split("\\?")[0];
        }

        try
        {
            // Reading website as file.
            URL imdb = new URL(imdbUrl);
            try (BufferedReader in = new BufferedReader(new InputStreamReader(imdb.openStream())))
            {
                boolean wasYear = false;
                int wasDuration = 0;
                int wasDirector = 0;
                boolean wasSummary = false;
                int wasImage = 0;
                String inputLine;

                //System.out.println("START");
                // Check each line.
                while ((inputLine = in.readLine()) != null)
                {
                    // Check for movie title.
                    if (inputLine.contains(NAME_TAG))
                    {
                        name = inputLine.split(">")[1].split("&")[0].trim();
                        //System.out.println("Name: " + name);
                    }

                    // Check for year.
                    if (inputLine.contains(YEAR_TAG))
                    {
                        wasYear = true;
                    }
                    else if (wasYear)
                    {
                        year = Integer.parseInt(inputLine.split(">")[1].split("<")[0].trim());
                        //System.out.println("Year: " + year);
                        wasYear = false;
                    }

                    // Check for rating.
                    if (inputLine.contains(RATING_TAG))
                    {
                        rating = Double.parseDouble(inputLine.split(">")[2].split("<")[0].trim());
                        //System.out.println("Rating: " + rating);
                    }

                    // Check for duration.
                    if (inputLine.contains(DURATION_TAG) && duration == 0)
                    {
                        wasDuration++;
                    }
                    else if (wasDuration > 0)
                    {
                        String txt = inputLine.trim();
                        if (txt.toLowerCase().contains("h"))
                        {
                            int hour = Integer.parseInt(txt.split("h")[0].trim()) * 60;
                            int min = Integer.parseInt(txt.split("h")[1].split("min")[0].trim());
                            duration = hour + min;
                        }
                        else
                        {
                            int min = Integer.parseInt(txt.split("min")[0].trim());
                            duration = min;
                        }
                        //System.out.println("Duration: " + duration);
                        wasDuration = 0;
                    }

                    // Check for genres.
                    if (inputLine.contains(GENRE_TAG))
                    {
                        genres.add(inputLine.split(">")[2].split("<")[0].trim());
                        //System.out.println("Genre " + (genres.size()) + ": " + genres.get(genres.size() - 1));
                    }

                    // Check for directors.
                    if (inputLine.contains(DIRECTOR_TAG) || wasDirector == 1)
                    {
                        wasDirector++;
                    }
                    else if (wasDirector > 1)
                    {
                        if (directors == null)
                        {
                            directors = inputLine.split(">")[2].split("<")[0].trim();
                        }
                        else
                        {
                            directors += ", " + inputLine.split(">")[2].split("<")[0].trim();
                        }

                        //System.out.println("Director " + directors);
                        wasDirector = 0;
                    }

                    // Check for summary.
                    if (inputLine.contains(SUMMARY_TAG))
                    {
                        wasSummary = true;
                    }
                    else if (wasSummary)
                    {
                        summary = inputLine.trim();
                        //System.out.println("Summary: " + summary);
                        wasSummary = false;
                    }

                    // Check for image and save to harddisk.
                    if (inputLine.contains(IMAGE_TAG) || wasImage == 1 || wasImage == 2)
                    {
                        wasImage++;
                    }
                    else if (wasImage > 2)
                    {
                        String imageUrl = inputLine.split("\"")[1];
                        //System.out.println("ImageURL: " + imageUrl);

                        handleImage(imageUrl);

                        wasImage = 0;
                    }
                }
            }
            rippedAllInformation = true;
        }
        // In case of an exception throw new exception.
        catch (IOException | NumberFormatException ex)
        {
            throw new DALException(ERROR_READING_IMDB + ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Saves the image as a file and in a byte array. Adds the path as String to
     * image.
     * @param imageUrl URL of image.
     */
    private void handleImage(String imageUrl) throws DALException
    {
        // Open stream for image.
        try (InputStream inImg = new URL(imageUrl).openStream())
        {
            // Create file name.
            String fileName = "";
            for (String string : name.split(" "))
            {
                fileName += string.replaceAll("\\W+", "") + IMAGE_SEPERATOR;
            }
            fileName += year + imageUrl.substring(imageUrl.length() - 4);

            // Create directory if it is not there.
            File dir = new File(IMAGE_DIRECTORY);
            dir.mkdir();

            // Copy image to directory with given name.
            Files.copy(inImg, Paths.get(IMAGE_DIRECTORY + fileName), StandardCopyOption.REPLACE_EXISTING);

            // Save path as Sting.
            imagePath = IMAGE_DIRECTORY + fileName;

            //System.out.println("Saved image to: " + image);
        }
        catch (IOException ex)
        {
            throw new DALException(ERROR_COPYING_IMAGE + ex.getMessage(), ex.getCause());
        }

        // Converts the image to a byte array.
        try
        {
            // Get image as BufferedImage
            BufferedImage imm = ImageIO.read(new File(imagePath));
            // Write BufferedImage to ByteArrayOutputStream.
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream())
            {
                //use another encoding if JPG is innappropriate for you
                ImageIO.write(imm, IMAGE_EXTENSION, baos);
                baos.flush();

                // Save result.
                imageInBytes = baos.toByteArray();
            }
        }
        catch (IOException ex)
        {
            throw new DALException(ERROR_BINARY_IMAGE + ex.getMessage(), ex.getCause());
        }
    }

    /**
     * If all information was ripped as expected.
     * @return
     */
    public boolean rippedAllInformation()
    {
        return rippedAllInformation;
    }

    /**
     * Returns name of ripped movie.
     * @return
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns year of ripped movie.
     * @return
     */
    public int getYear()
    {
        return year;
    }

    /**
     * Returns duration of ripped movie.
     * @return
     */
    public int getDuration()
    {
        return duration;
    }

    /**
     * Returns genres of ripped movie.
     * @return
     */
    public List<String> getGenres()
    {
        return genres;
    }

    /**
     * Returns rating of ripped movie.
     * @return
     */
    public double getRating()
    {
        return rating;
    }

    /**
     * Returns directors of ripped movie.
     * @return
     */
    public String getDirectors()
    {
        if (directors == null)
        {
            return NO_DIRECTOR;
        }
        return directors;
    }

    /**
     * Returns summary of ripped movie.
     * @return
     */
    public String getSummary()
    {
        return summary;
    }

    /**
     * Returns image path to image of ripped movie.
     * @return
     */
    public String getImagePath()
    {
        return imagePath;
    }

    /**
     * Returns image of ripped movie in byte array.
     * @return
     */
    public byte[] getImageInBytes()
    {
        return imageInBytes;
    }

    /**
     * Override original toString() method to return all information in one
     * string.
     * @return String containing all information.
     */
    @Override
    public String toString()
    {
        return "IMDbRip{" + "name=" + name + ", year=" + year + ", duration=" + duration + ", genres=" + genres + ", rating=" + rating + ", directors=" + directors + ", image=" + imagePath + '}';
    }
}
