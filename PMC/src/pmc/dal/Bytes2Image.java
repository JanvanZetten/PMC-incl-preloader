/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.dal;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author Asbamz
 */
public class Bytes2Image
{
    private final String IMAGE_EXTENSION = ".jpg";
    private final String PATH_NAME_SEPERATOR = "_";
    private final String IMAGE_DIRECTORY = "./images/";
    private final String ERROR_MESSAGE = "Error getting image from binary data! ";

    private byte[] imageInBytes;
    private String imagePath;

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

    public byte[] getImageInBytes()
    {
        return imageInBytes;
    }

    public String getImagePath()
    {
        return imagePath;
    }

}
