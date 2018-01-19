package pmc.dal;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 * En Gruppe
 * @author Alex, Asbjørn & Jan
 */
public class Bytes2Image
{
    private final String IMAGE_EXTENSION = ".jpg";
    private final String PATH_NAME_SEPERATOR = "_";
    private final String IMAGE_DIRECTORY = "./images/";
    private final String ERROR_MESSAGE = "Error getting image from binary data! ";

    private byte[] imageInBytes;
    private String imagePath;

    /**
     * Converts image, as byte array, to a file.
     * @param name
     * @param year
     * @param imageInBytes
     * @throws DALException
     */
    public Bytes2Image(String name, int year, byte[] imageInBytes) throws DALException
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
                fileName += string.replaceAll("\\W+", "") + PATH_NAME_SEPERATOR;
            }
            fileName += year + IMAGE_EXTENSION;

            // Create directory if it is not there.
            File dir = new File(IMAGE_DIRECTORY);
            dir.mkdir();

            // Write imagePath to file.
            File outputfile = new File(IMAGE_DIRECTORY + fileName);
            ImageIO.write(imgFromDb, IMAGE_EXTENSION.substring(1), outputfile);

            // Saved data to variables.
            this.imageInBytes = imageInBytes;
            this.imagePath = fileName;
        }
        catch (IOException ex)
        {
            throw new DALException(ERROR_MESSAGE + ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Returns image as byte array.
     * @return
     */
    public byte[] getImageInBytes()
    {
        return imageInBytes;
    }

    /**
     * Returns image path.
     * @return
     */
    public String getImagePath()
    {
        return imagePath;
    }
}
