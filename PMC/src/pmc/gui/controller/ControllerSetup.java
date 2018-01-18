package pmc.gui.controller;

import javafx.stage.Stage;
import pmc.bll.BLLManager;
import pmc.gui.model.MainWindowModel;

/**
 * En Gruppe
 * @author janvanzetten, Alex & Asbamz
 */
public interface ControllerSetup
{
    /**
     * Pass Stage, MainWindowModel and BLLManager to controller.
     * @param thisStage
     * @param mainWindowModel
     * @param bllManager
     */
    public void setup(Stage thisStage, MainWindowModel mainWindowModel, BLLManager bllManager);
}
