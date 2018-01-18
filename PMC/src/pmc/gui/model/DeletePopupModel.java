/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.gui.model;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import pmc.be.Movie;
import pmc.bll.BLLManager;
import pmc.bll.HBoxCell;

/**
 *
 * @author Alex
 */
public class DeletePopupModel {

    BLLManager bllManager;
    HBoxCell hBoxCell;

    public DeletePopupModel() {
    }

    /**
     * Sets the list of movies that have been deemed old by the BLLManager on
     * startup.
     * @param tblMovies 
     */
    public void setList(ListView<HBoxCell> tblMovies) {
        List<HBoxCell> tbl = new ArrayList<>();
        List<Movie> movies = bllManager.getTBDeletedList();

        System.out.println(movies.size());

        for (int i = 0; i < movies.size(); i++) {
            tbl.add(new HBoxCell(movies.get(i).getName(), "Delete", "  ", "Ignore", movies.get(i), bllManager));
        }

        ObservableList<HBoxCell> ol = FXCollections.observableArrayList();
        ol.addAll(tbl);
        tblMovies.setItems(ol);
    }

    /**
     * Sets the BLLManager instance to be the same as the one in the MainModel.
     * @param bllManager 
     */
    public void setBLLManager(BLLManager bllManager) {
        this.bllManager = bllManager;
    }

}
