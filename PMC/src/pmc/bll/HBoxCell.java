package pmc.bll;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import pmc.be.Movie;
import pmc.gui.model.MainWindowModel;

/**
 * En Gruppe
 * @author Alex, Asbjørn & Jan
 */
public class HBoxCell extends HBox
{
    private final String DELETE_CONFIRMATION = "Are you sure?";
    private final String DELETE_DONE = "Deleted";

    private Label label = new Label();
    private Button button1 = new Button();
    private Label filler = new Label();
    private Button button2 = new Button();

    /**
     * Creates HBox from super class. Sets JavaFX Nodes.
     * @param labelText
     * @param buttonText1
     * @param fillerText
     * @param buttonText2
     * @param movie
     * @param mwm
     */
    public HBoxCell(String labelText, String buttonText1, String fillerText, String buttonText2, Movie movie, MainWindowModel mwm)
    {
        super();

        label.setText(labelText);
        label.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(label, Priority.ALWAYS);

        button1.setText(buttonText1);
        button1.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {

                if (button1.getText().equals(DELETE_CONFIRMATION))
                {
                    mwm.deleteMovie(movie);
                    button1.setText(DELETE_DONE);
                }
                if (!button1.getText().equals(DELETE_DONE))
                {
                    button1.setText(DELETE_CONFIRMATION);
                }

            }
        });

        filler.setText(fillerText);

        button2.setText(buttonText2);
        button2.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                movie.setLastView(movie.getLastView() + 20000);
                button2.setText("Ignored");
            }
        });

        this.getChildren().addAll(label, button1, filler, button2);
    }

}
