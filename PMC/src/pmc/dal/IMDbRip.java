package pmc.dal;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
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
    private String year;
    private String duration;
    private List<String> genres;
    private String rating;
    private List<String> directors;
    private String image;

    public IMDbRip(String imdbUrl)
    {
        rippedAllInformation = false;
        name = null;
        year = null;
        duration = null;
        genres = new ArrayList<>();
        rating = null;
        directors = new ArrayList<>();
        image = null;

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
        ripInformationByFile(imdbUrl);
    }

    /**
     * rip IMDb Website by reading it as a stream.
     * @param imdbUrl String containing URL.
     */
    private void ripInformationByFile(String imdbUrl)
    {
        try
        {
            // Reading website as file.
            URL imdb = new URL(imdbUrl);
            try (BufferedReader in = new BufferedReader(new InputStreamReader(imdb.openStream())))
            {
                boolean wasYear = false;
                boolean wasDuration = false;
                int wasDirector = 0;
                int wasImage = 0;
                String inputLine;

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
                        year = inputLine.split(">")[1].split("<")[0].trim();
                        //System.out.println("Year: " + year);
                        wasYear = false;
                    }

                    // Check for rating.
                    if (inputLine.contains("span itemprop=\"ratingValue\""))
                    {
                        rating = inputLine.split(">")[2].split("<")[0].trim();
                        //System.out.println("Rating: " + rating);
                    }

                    // Check for duration.
                    if (inputLine.contains("time itemprop=\"duration\""))
                    {
                        wasDuration = true;
                    }
                    else if (wasDuration && duration == null)
                    {
                        duration = inputLine.trim();
                        //System.out.println("Duration: " + duration);
                        wasDuration = false;
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
                        directors.add(inputLine.split(">")[2].split("<")[0].trim());
                        //System.out.println("Director " + (directors.size()) + ": " + directors.get(directors.size() - 1));
                        wasDirector = 0;
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

                        try (InputStream inImg = new URL(imageUrl).openStream())
                        {
                            String fileName = "";
                            for (String string : name.split(" "))
                            {
                                fileName += string + "_";
                            }
                            fileName += year + imageUrl.substring(imageUrl.length() - 4);

                            File dir = new File("./images/");
                            dir.mkdir();
                            Files.copy(inImg, Paths.get("./images/" + fileName), StandardCopyOption.REPLACE_EXISTING);
                            image = "./images/" + fileName;
                            //System.out.println("Saved image to: " + image);
                        }
                        catch (Exception ex)
                        {
                            throw new RuntimeException("Error copying image!");
                        }

                        wasImage = 0;
                    }
                }

                // If all variables are set it ripped all information.
                if (name != null
                        && year != null
                        && duration != null
                        && genres.size() > 0
                        && rating != null
                        && directors.size() > 0
                        && image != null)
                {
                    rippedAllInformation = true;
                }
                else
                {
                    throw new RuntimeException("Not an IMDb movie page!");
                }
            }
        }
        // In case of an exception throw new exception.
        catch (Exception ex)
        {
            throw new RuntimeException("Error reading information from IMDb!");
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

    public String getYear()
    {
        return year;
    }

    public String getDuration()
    {
        return duration;
    }

    public List<String> getGenres()
    {
        return genres;
    }

    public String getRating()
    {
        return rating;
    }

    public List<String> getDirectors()
    {
        return directors;
    }

    public String getImage()
    {
        return image;
    }

    @Override
    public String toString()
    {
        return "IMDbRip{" + "name=" + name + ", year=" + year + ", duration=" + duration + ", genres=" + genres + ", rating=" + rating + ", directors=" + directors + ", image=" + image + '}';
    }
}
