/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.bll;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import pmc.be.Movie;
import pmc.gui.controller.MainWindowController;
import pmc.gui.model.DeletePopupModel;

/**
 *
 * @author Alex
 */
public class HBoxCell extends HBox {

        Label label = new Label();
        Button button1 = new Button();
        Label filler = new Label();
        Button button2 = new Button();

        public HBoxCell(String labelText, String buttonText1, String fillerText, String buttonText2, Movie movie, BLLManager bllManager) {
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

