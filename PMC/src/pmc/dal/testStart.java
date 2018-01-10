/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.dal;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import pmc.be.Genre;
import pmc.be.Movie;

/**
 *
 * @author janvanzetten
 */
public class testStart extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        DALManager dal = new DALManager();

        Genre testGenre = dal.addGenre("Thriller");
        Genre testGenre2 = dal.addGenre("Action");

        List<Genre> genreList = new ArrayList<>();
        genreList.add(testGenre);
        genreList.add(testGenre2);
        
//        File fi = new File("pmc/gui/resources/logo.png");
//        byte[] fileContent = Files.readAllBytes(fi.toPath());
        
        File fnew=new File("/gui/resources/logo.png");
        BufferedImage originalImage=ImageIO.read(fnew);
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        ImageIO.write(originalImage, "png", baos );
        baos.flush();
        byte[] imageInByte=baos.toByteArray();
        baos.close();

        dal.addMovie("007", "some file placement", genreList, 9.0, 5, "En Gruppe", 1000, "www.imdb.com", 2018, imageInByte);

        for (Genre allGenre : dal.getAllGenres()) {
            System.out.println(allGenre.getName());
        }
        System.out.println("Movies:");
        for (Movie allMovy : dal.getAllMovies()) {
            System.out.println(allMovy.getName());
            System.out.println(allMovy.getDirectors());
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
