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
 * En Gruppe
 * @author Alex, Asbjørn & Jan
 */
public class DeletePopupModel
{
    BLLManager bllManager;
    MainWindowModel mwm;
    HBoxCell hBoxCell;

    /**
     * Sets the list of movies that have been deemed old by the BLLManager on
     * startup.
     * @param tblMovies
     */
    public void setList(ListView<HBoxCell> tblMovies)
    {
        List<HBoxCell> tbl = new ArrayList<>();
        List<Movie> movies = bllManager.getTBDeletedList();

        for (int i = 0; i < movies.size(); i++)
        {
            tbl.add(new HBoxCell(movies.get(i).getName(), "Delete", "  ", "Ignore", movies.get(i), mwm));
        }

        ObservableList<HBoxCell> ol = FXCollections.observableArrayList();
        ol.addAll(tbl);
        tblMovies.setItems(ol);
    }

    /**
     * Sets the BLLManager instance to be the same as the one in the MainModel.
     * @param mainWindowModel
     * @param bllManager
     */
    public void setup(MainWindowModel mainWindowModel, BLLManager bllManager)
    {
        this.bllManager = bllManager;
        this.mwm = mainWindowModel;
    }
}
