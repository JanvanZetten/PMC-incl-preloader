/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.be;

import javafx.stage.Stage;
import pmc.bll.BLLManager;

/**
 *
 * @author Asbamz
 */
public interface ControllerSetup
{
    /**
     * Pass Stage and BLLManager to controller.
     * @param thisStage
     * @param bllManager
     */
    public void setup(Stage thisStage, BLLManager bllManager);
}
