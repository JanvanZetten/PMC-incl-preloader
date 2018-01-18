package pmc.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import pmc.bll.BLLManager;
import pmc.gui.model.DeletePopupModel;
import pmc.bll.HBoxCell;
import pmc.gui.model.MainWindowModel;

/**
 * En Gruppe
 * @author janvanzetten, Alex & Asbamz
 */
public class DeletePopupController implements Initializable, ControllerSetup
{
    @FXML
    private ListView<HBoxCell> tblMovies;
    @FXML
    private Button btnDone;

    DeletePopupModel model;

    /**
     * Initiates model.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        model = new DeletePopupModel();
    }

    /**
     * Close window on press.
     * @param event
     * @throws IOException
     */
    @FXML
    private void handleDoneAction(ActionEvent event) throws IOException
    {
        Stage stage = (Stage) btnDone.getScene().getWindow();
        stage.close();
    }

    /**
     * Setup model.
     * @param thisStage
     * @param mainWindowModel
     * @param bllManager
     */
    @Override
    public void setup(Stage thisStage, MainWindowModel mainWindowModel, BLLManager bllManager)
    {
        model.setup(mainWindowModel, bllManager);
        model.setList(tblMovies);
    }
}
