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
                fileName += string.replaceAll("\\W+", "") + "_";
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
            throw new DALException("Error getting image from binary data! " + ex.getMessage(), ex.getCause());
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
