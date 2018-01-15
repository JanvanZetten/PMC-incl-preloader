/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.gui.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * FXML Controller class
 *
 * @author Alex
 */
public class DeletePopupViewController implements Initializable {

    @FXML
    private ListView<HBoxCell> tblMovies;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<HBoxCell> tbl = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            tbl.add(new HBoxCell("Item " + i, "Delete", "  ", "Ignore"));
        }
        
        //det er den vi fylder ind i vores combox så den viser en liste af string object.
                ObservableList<HBoxCell> ol = FXCollections.observableArrayList();
                // fylder listen med businessRoles
                ol.addAll(tbl);
                
        tblMovies.setItems(ol);
    }

    public static class HBoxCell extends HBox {

        Label label = new Label();
        Button button1 = new Button();
        Label filler = new Label();
        Button button2 = new Button();

        HBoxCell(String labelText, String buttonText1, String fillerText, String buttonText2) {
            super();

            label.setText(labelText);
            label.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(label, Priority.ALWAYS);

            button1.setText(buttonText1);
            button1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("WORKS");
                }
            });
            
            filler.setText(fillerText);
            button2.setText(buttonText2);

            this.getChildren().addAll(label, button1, filler, button2);
        }
    }
    
    private void handleClose() {
        
    }

}
