/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.gui.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pmc.be.Movie;
import pmc.bll.BLLException;
import pmc.bll.BLLManager;
import pmc.gui.controller.MainWindowController;

/**
 *
 * @author Alex
 */
public class DeletePopupModel {

    BLLManager bllManager;

    public DeletePopupModel() {
    }

    public void setList(ListView<HBoxCell> tblMovies) {
        List<HBoxCell> tbl = new ArrayList<>();
        List<Movie> movies = bllManager.getTBDeletedList();

        System.out.println(movies.size());

        for (int i = 0; i < movies.size(); i++) {
            tbl.add(new HBoxCell(movies.get(i).getName(), "Delete", "  ", "Ignore", movies.get(i)));
        }

        ObservableList<HBoxCell> ol = FXCollections.observableArrayList();
        ol.addAll(tbl);
        tblMovies.setItems(ol);
    }

    public void setBLLManager(BLLManager bllManager) {
        this.bllManager = bllManager;
    }

    public class HBoxCell extends HBox {

        Label label = new Label();
        Button button1 = new Button();
        Label filler = new Label();
        Button button2 = new Button();

        HBoxCell(String labelText, String buttonText1, String fillerText, String buttonText2, Movie movie) {
            super();

            label.setText(labelText);
            label.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(label, Priority.ALWAYS);

            button1.setText(buttonText1);
            button1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    button1.setText("Are you sure?");
                    if (button1.getText().equals("Are you sure?")) {
                    try {
                        bllManager.deleteMovie(movie);
                    } catch (BLLException ex) {
                        Logger.getLogger(DeletePopupModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        FXMLLoader fxLoader = new FXMLLoader(getClass().getResource("/pmc/gui/view/MainWindowView.fxml"));
                        MainWindowController cont = fxLoader.getController();
                        cont.updateTable(movie);
                        button1.setText("Deleted");
                    }

                }
            });

            filler.setText(fillerText);

            button2.setText(buttonText2);
            button2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    movie.setLastView(movie.getLastView() + 20000);
                    button2.setText("Ignored");
                }
            });

            this.getChildren().addAll(label, button1, filler, button2);
        }
    }

}
