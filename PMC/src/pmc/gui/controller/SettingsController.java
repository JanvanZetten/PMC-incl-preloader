package pmc.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pmc.gui.model.SettingsModel;

/**
 * En Gruppe
 * @author Alex, Asbjørn & Jan
 */
public class SettingsController implements Initializable
{
    @FXML
    private TextField TxtBxFolderLocation;
    @FXML
    private ComboBox<String> cbbxInterval;

    private SettingsModel model;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        model = new SettingsModel();
        model.setUI(TxtBxFolderLocation, cbbxInterval);
    }

    /**
     * Handle press on save button. In which case the settings are saved. The
     * window is closed.
     * @param event
     */
    @FXML
    private void handleSave(ActionEvent event)
    {
        model.saveSettings(TxtBxFolderLocation, cbbxInterval);
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    /**
     * Handles press on cancel button. Closes window.
     * @param event
     */
    @FXML
    private void handelCancel(ActionEvent event)
    {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
}
