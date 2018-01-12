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
//import javafx.scene.web.WebView;

/**
 *
 * @author Jan, Alex og Asbjørn.
 */
public class IMDbRip
{
    //private WebView webView;
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

        /*
        //Working with JavaFX WebView
        this.webView = new WebView();
        webView.getEngine().getLoadWorker().progressProperty().addListener(
                (o,old, progress) -> {
                    Document doc = webView.getEngine().getDocument();
                    ripInformation(doc);
                });
        webView.getEngine().load(imdbUrl);
         */
        ripInformationAsFile(imdbUrl);
    }

    /**
     * rip IMDb Website by reading it as a stream.
     * @param imdbUrl String containing URL.
     */
    private void ripInformationAsFile(String imdbUrl) throws DALException
    {
        // In case the website is not a IMDb Movie URL.
        if (!imdbUrl.toLowerCase().contains("imdb.com/title/"))
        {
            throw new DALException("Not a IMDb movie website! " + imdbUrl);
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
                    if (inputLine.contains("itemprop=\"name\" class=\"\""))
                    {
                        name = inputLine.split(">")[1].split("&")[0].trim();
                        //System.out.println("Name: " + name);
                    }

                    // Check for year.
                    if (inputLine.contains("id=\"titleYear\""))
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
                    if (inputLine.contains("span itemprop=\"ratingValue\""))
                    {
                        rating = Double.parseDouble(inputLine.split(">")[2].split("<")[0].trim());
                        //System.out.println("Rating: " + rating);
                    }

                    // Check for duration.
                    if (inputLine.contains("time itemprop=\"duration\"") && duration == 0)
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
                    if (inputLine.contains("class=\"itemprop\" itemprop=\"genre\""))
                    {
                        genres.add(inputLine.split(">")[2].split("<")[0].trim());
                        //System.out.println("Genre " + (genres.size()) + ": " + genres.get(genres.size() - 1));
                    }

                    // Check for directors.
                    if (inputLine.contains("itemprop=\"director\"") || wasDirector == 1)
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
                    if (inputLine.contains("class=\"summary_text\" itemprop=\"description\""))
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
                    if (inputLine.contains("class=\"poster\"") || wasImage == 1 || wasImage == 2)
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
        }
        // In case of an exception throw new exception.
        catch (IOException | NumberFormatException ex)
        {
            throw new DALException("Error reading information from IMDb! " + ex.getMessage(), ex.getCause());
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
                fileName += string.replaceAll("\\W+", "") + "_";
            }
            fileName += year + imageUrl.substring(imageUrl.length() - 4);

            // Create directory if it is not there.
            File dir = new File("./images/");
            dir.mkdir();

            // Copy image to directory with given name.
            Files.copy(inImg, Paths.get("./images/" + fileName), StandardCopyOption.REPLACE_EXISTING);

            // Save path as Sting.
            imagePath = "./images/" + fileName;

            //System.out.println("Saved image to: " + image);
        }
        catch (IOException ex)
        {
            throw new DALException("Error copying image! " + ex.getMessage(), ex.getCause());
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
                ImageIO.write(imm, "jpg", baos);
                baos.flush();

                // Save result.
                imageInBytes = baos.toByteArray();
            }
        }
        catch (IOException ex)
        {
            throw new DALException("Error turning image into binary data! " + ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Rip all information using JavaFX WebView.
     * @return
     */
    /*
    private void ripInformation(Document doc)
    {
        if (doc != null && !rippedAllInformation)
        {
            try
            {
                String txt;
                Element field;

                field = (Element) XPathFactory.newInstance().newXPath().evaluate(
                        //"//*[@id='navbar-form']//*[@name='q']",
                        "//*[@itemprop='name']",
                        doc, XPathConstants.NODE);
                if (field != null)
                {
                    txt = field.getTextContent().trim();
                    System.out.println("Before: " + txt);
                    String[] txtArr = txt.split("\\(");
                    name = txtArr[0].substring(0, txtArr[0].length() - 1);
                    year = txtArr[1].substring(0, txtArr[1].length() - 1);
                    System.out.println("Name: " + name);
                    System.out.println("Year: " + year);
                }

                if (field == null)
                {
                    System.out.println("NOT GOOD");
                    throw new RuntimeException("Not a IMDb movie URL!");
                }

                field = (Element) XPathFactory.newInstance().newXPath().evaluate(
                        "//*[@itemprop='duration']",
                        doc, XPathConstants.NODE);
                if (field != null)
                {
                    duration = field.getTextContent().trim();
                    System.out.println("Duration: " + duration);
                }

                if (field == null)
                {
                    System.out.println("NOT GOOD");
                    throw new RuntimeException("Not a IMDb movie URL!");
                }

                NodeList nodeArr = (NodeList) XPathFactory.newInstance().newXPath().evaluate(
                        "//*[@itemprop='genre']",
                        doc, XPathConstants.NODESET);
                genres = new ArrayList<>();
                for (int i = 0; i < nodeArr.getLength() - 1; i++)
                {
                    Element el = (Element) nodeArr.item(i);
                    txt = el.getTextContent().trim();
                    genres.add(txt);
                    System.out.println("Genre " + (i + 1) + ": " + txt);
                }

                field = (Element) XPathFactory.newInstance().newXPath().evaluate(
                        "//*[@itemprop='ratingValue']",
                        doc, XPathConstants.NODE);
                if (field != null)
                {
                    rating = field.getTextContent().trim();
                    System.out.println("Rating: " + rating);
                }

                if (field == null)
                {
                    System.out.println("NOT GOOD");
                    throw new RuntimeException("Not a IMDb movie URL!");
                }

                nodeArr = (NodeList) XPathFactory.newInstance().newXPath().evaluate(
                        "//*[@itemprop='director']",
                        doc, XPathConstants.NODESET);
                directors = new ArrayList<>();
                for (int i = 0; i < nodeArr.getLength(); i++)
                {
                    Element el = (Element) nodeArr.item(i);
                    txt = el.getTextContent().trim();
                    directors.add(txt);
                    System.out.println("Director " + (i + 1) + ": " + txt);
                }

                if (field == null)
                {
                    System.out.println("NOT GOOD");
                    throw new RuntimeException("Not a IMDb movie URL!");
                }

                field = (Element) XPathFactory.newInstance().newXPath().evaluate(
                        "//*[@itemprop='image']",
                        doc, XPathConstants.NODE);
                if (field != null && name != null && year != null)
                {
                    String imageUrl = field.getAttributes().getNamedItem("src").getNodeValue();
                    System.out.println("Image URL: " + imageUrl);
                    try (InputStream in = new URL(imageUrl).openStream())
                    {
                        String fileName = "";
                        for (String string : name.split(" "))
                        {
                            fileName += string + "_";
                        }
                        fileName += year + imageUrl.substring(imageUrl.length() - 4);

                        File dir = new File("./images/");
                        dir.mkdir();
                        Files.copy(in, Paths.get("./images/" + fileName), StandardCopyOption.REPLACE_EXISTING);
                        image = "./images/" + fileName;
                        System.out.println("Saved image to " + image);
                    }
                    catch (Exception ex)
                    {
                        throw new RuntimeException("Error copying image!");
                    }
                }

                if (field != null)
                {
                    System.out.println("GOOD");
                    rippedAllInformation = true;
                }
                else
                {
                    System.out.println("NOT GOOD");
                    throw new RuntimeException("Not a IMDb movie URL!");
                }
            }
            catch (XPathException e)
            {
                throw new RuntimeException("Error ripping information from IMDb!");
            }
        }
    }
     */
    /**
     * If all information was ripped as expected.
     * @return
     */
    public boolean rippedAllInformation()
    {
        return rippedAllInformation;
    }

    public String getName()
    {
        return name;
    }

    public int getYear()
    {
        return year;
    }

    public int getDuration()
    {
        return duration;
    }

    public List<String> getGenres()
    {
        return genres;
    }

    public double getRating()
    {
        return rating;
    }

    public String getDirectors()
    {
        return directors;
    }

    public String getImagePath()
    {
        return imagePath;
    }

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
