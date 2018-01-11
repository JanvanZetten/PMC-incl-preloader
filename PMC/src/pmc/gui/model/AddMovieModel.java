/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.gui.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import pmc.bll.BLLManager;

/**
 *
 * @author janvanzetten
 */
public class AddMovieModel {
    
    private Path to;
    private Path from;
    private File selectedFile;
    BLLManager bll = new BLLManager();

    public void browseMovie(TextField textfieldPath) throws IOException {
//        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("mp4", "mpeg4");
        FileChooser fc = new FileChooser();
//        fc.getExtensionFilters().add(filter);
        String currentDir = System.getProperty("user.dir") + File.separator;
        File dir = new File(currentDir);
//        fc.setInitialDirectory(dir);
        fc.setTitle("Attach a file");
        selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            from = Paths.get(selectedFile.toURI());
            to = Paths.get(dir + "/Movies/" + selectedFile.getName());
            textfieldPath.setText(selectedFile.getName());
            
//            Files.copy(from, to, REPLACE_EXISTING);
    }}

    public boolean save(String url) {
        return false;
    }
    
}
