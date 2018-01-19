package pmc.dal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import pmc.be.Settings;

/**
 * En Gruppe
 * @author Alex, Asbjørn & Jan
 */
public class SettingsData
{
    private final String SETTINGS_FILE = "settings.pmc";

    private static Settings settings;

    /**
     * Saves the given settings to a file
     *
     * @param settings the settings to save
     * @throws DALException
     */
    public void saveSettings(Settings settings) throws DALException
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SETTINGS_FILE)))
        {
            oos.writeObject(settings);
            SettingsData.settings = settings;
        }
        catch (IOException ex)
        {
            throw new DALException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Loads the settings from the file.
     *
     * @return the settings from the file
     * @throws DALException
     */
    public Settings loadSettings() throws DALException
    {
        if (settings == null)
        {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SETTINGS_FILE)))
            {
                settings = (Settings) ois.readObject();
            }
            catch (FileNotFoundException ex)
            {
                saveSettings(new Settings());
                return settings;
            }
            catch (IOException | ClassNotFoundException ex)
            {
                throw new DALException(ex.getMessage(), ex.getCause());
            }
        }
        return settings;
    }
}
