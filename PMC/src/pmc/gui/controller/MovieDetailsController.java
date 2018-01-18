package pmc.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pmc.bll.BLLManager;
import pmc.gui.model.MovieDetailsModel;
import pmc.gui.model.MainWindowModel;

/**
 * En Gruppe
 * @author janvanzetten, Alex & Asbamz
 */
public class MovieDetailsController implements Initializable, ControllerSetup
{
    @FXML
    private TextArea textareaDescription;
    @FXML
    private ImageView imageMoviePoster;
    @FXML
    private ImageView imageRatingStar;
    @FXML
    private Label lblTitleAndYear;
    @FXML
    private Label lblGenres;
    @FXML
    private Label lblDirector;
    @FXML
    private Button btnCopyLink;
    @FXML
    private Button btnClose;
    @FXML
    private Button watchMovie;
    @FXML
    private Label lblImdb;
    @FXML
    private Label lblPersonal;

    MovieDetailsModel model;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        model = new MovieDetailsModel();
    }

    /**
     * Plays the movie selected.
     * @throws IOException
     */
    @FXML
    private void watchMovieAction() throws IOException
    {
        model.playMovie();
        //model.setNewLastView();
    }

    /**
     * Close window on press.
     */
    @FXML
    private void closeWindowAction()
    {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    /**
     * Copy link to clipboard on press.
     */
    @FXML
    private void getLinkAction()
    {
        model.setClipboard();
        btnCopyLink.setText("Copied to clipboard");
    }

    /**
     * All information about movie is set.
     * @param thisStage
     * @param mainWindowModel
     * @param bllManager
     */
    @Override
    public void setup(Stage thisStage, MainWindowModel mainWindowModel, BLLManager bllManager)
    {
        model.setBLLManager(bllManager);
        model.determineIMDbLink(btnCopyLink);
        model.setPosterImage(imageMoviePoster);
        model.setRatingImage(imageRatingStar);
        model.setDescription(textareaDescription);
        model.setTitleAndYear(lblTitleAndYear);
        model.setGenres(lblGenres);
        model.setDirector(lblDirector);
        model.setScore(lblImdb, lblPersonal);
    }

}
