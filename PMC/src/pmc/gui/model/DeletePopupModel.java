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
        
//    public void setList(ListView<Movie> tblMovies) {
//    
//        ObservableList<Movie> list = FXCollections.observableArrayList(
//                "Item 1", "Item 2", "Item 3", "Item 4");
//        ListView<Movie> lv = new ListView<>(list);
//        lv.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
//            @Override
//            public ListCell<String> call(ListView<String> param) {
//                return new XCell();
//            }
//        });
//        
//    }
//    
//    static class XCell extends ListCell<String> {
//        HBox hbox = new HBox();
//        Label label = new Label("(empty)");
//        Pane pane = new Pane();
//        Button button = new Button("(>)");
//        String lastItem;
//
//        public XCell() {
//            super();
//            hbox.getChildren().addAll(label, pane, button);
//            HBox.setHgrow(pane, Priority.ALWAYS);
//            button.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//                    System.out.println(lastItem + " : " + event);
//                }
//            });
//        }
//
//        @Override
//        protected void updateItem(String item, boolean empty) {
//            super.updateItem(item, empty);
//            setText(null);  // No text in label of super class
//            if (empty) {
//                lastItem = null;
//                setGraphic(null);
//            } else {
//                lastItem = item;
//                label.setText(item!=null ? item : "<null>");
//                setGraphic(hbox);
//            }
//        }
//    }
//    
    
    
    

    

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
        for (int i = 0; i < 5; i++) {
            tbl.add(new HBoxCell("Item " + i, "Delete", "  ", "Ignore"));
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
