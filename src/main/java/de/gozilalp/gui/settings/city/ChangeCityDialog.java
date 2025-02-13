package de.gozilalp.gui.settings.city;

import de.gozilalp.DatabaseConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ChangeCityDialog extends JDialog {

    private static ChangeCityDialog instance;
    private JButton saveButton;
    private CitySearchPanel citySearchPanel;

    private ChangeCityDialog(JFrame mainFrame) {
        setTitle("Set city");
        setLocationRelativeTo(mainFrame);
        setSize(400, 400);
        setLayout(new BorderLayout());

        // add city suggestions (CitySearchPanel)
        citySearchPanel = new CitySearchPanel();
        add(citySearchPanel, BorderLayout.CENTER);

        // add save button
        add(getSaveButton(), BorderLayout.SOUTH);
    }

    public static ChangeCityDialog getInstance(JFrame mainFrame) {
        if (instance == null) {
            instance = new ChangeCityDialog(mainFrame);
        }
        instance.setVisible(true);
        return instance;
    }

    private JButton getSaveButton() {
        if (saveButton == null) {
            saveButton = new JButton("SAVE");
            saveButton.addActionListener(this::saveButtonAction);
        }
        return saveButton;
    }

    private void saveButtonAction(ActionEvent event) {
        // save city in database
        if (citySearchPanel.getSelectedCity() == null) {
            DatabaseConnector.getInstance().saveCity("null");
        } else {
            DatabaseConnector.getInstance().saveCity(citySearchPanel.getSelectedCity());
        }
        instance.dispose();
    }

    public static void tryFinalize() {
        if (instance != null) {
            instance.dispose();
            try {
                instance.finalize();
            } catch (Throwable e) {
                // do nothing
            }
        }
    }
}
