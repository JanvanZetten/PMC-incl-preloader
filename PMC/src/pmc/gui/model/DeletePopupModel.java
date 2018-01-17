/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.gui.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import pmc.be.Movie;
import pmc.bll.BLLManager;

/**
 *
 * @author Alex
 */
public class DeletePopupModel {

    BLLManager bllManager;

    public DeletePopupModel() {
        bllManager = new BLLManager();
        
    }
  
    public void openMain() throws IOException {
        Stage newStage = new Stage();
        newStage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxLoader = new FXMLLoader(getClass().getResource("/pmc/gui/view/MainWindowView.fxml"));
        Parent root = fxLoader.load();
        Scene scene = new Scene(root);
        newStage.setTitle("Private Movie Collection");
        newStage.getIcons().add(new Image("pmc/gui/resources/logo.png"));
        newStage.setScene(scene);
        newStage.setMinWidth(620);
        newStage.setMinHeight(394);
        newStage.show();
    }
    
    public void setList(ListView<HBoxCell> tblMovies) {
        List<HBoxCell> tbl = new ArrayList<>();
        List<Movie> movies = bllManager.getTBDeletedList();
        
//        movies = new ArrayList<>();
        
        System.out.println(movies.size());
//        System.out.println(movies.get(1));
        
        for (int i = 0; i < 2; i++) {
            tbl.add(new HBoxCell("name", "Delete", "  ", "Ignore"));
        }

        ObservableList<HBoxCell> ol = FXCollections.observableArrayList();
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
            button2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("WORKS TOO");
                }
            });

            this.getChildren().addAll(label, button1, filler, button2);
        }
    }

}
