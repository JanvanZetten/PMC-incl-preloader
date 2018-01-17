/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.dal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import pmc.be.Settings;

/**
 *
 * @author janvanzetten
 */
public class SettingsData {

    Settings settings;

    /**
     * Saves the given settings to a file
     *
     * @param settings the settings to save
     * @throws DALException
     */
    public void saveSettings(Settings settings) throws DALException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("settings.pmc"))) {
            oos.writeObject(settings);
            this.settings = settings;
        } catch (IOException ex) {
            throw new DALException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Loads the settings from the file.
     *
     * @return the settings from the file
     * @throws DALException
     */
    public Settings loadSettings() throws DALException {
        if (settings == null) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("settings.pmc"))) {
                settings = (Settings) ois.readObject();
            } catch (FileNotFoundException ex) {
                saveSettings(new Settings());
                return settings;
            } catch (IOException | ClassNotFoundException ex) {
                throw new DALException(ex.getMessage(), ex.getCause());
            }
        }
        
        return settings;
    }

}
