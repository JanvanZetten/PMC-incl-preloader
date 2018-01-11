/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.bll;

import pmc.dal.IMDbRip;

/**
 *
 * @author janvanzetten
 */
public class ripManager {
    IMDbRip ripper;

    public ripManager(String Url) {
        ripper = new IMDbRip(Url);
    }
    
    
    
    
}
