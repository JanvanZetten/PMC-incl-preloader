/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.dal;

/**
 *
 * @author janvanzetten
 */
public class DalExeption extends Exception {

    public DalExeption() {
    }

    public DalExeption(String message) {
        super(message);
    }

    public DalExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public DalExeption(Throwable cause) {
        super(cause);
    }
    
    
}
