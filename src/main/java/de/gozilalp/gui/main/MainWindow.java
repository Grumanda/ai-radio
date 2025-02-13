package de.gozilalp.gui.main;

import de.gozilalp.gui.settings.city.ChangeCityDialog;
import de.gozilalp.gui.settings.schedule.ScheduleDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;

/**
 * This class defines the main window of the program.
 *
 * @author grumanda
 */
public class MainWindow extends MyRadioJFrame {

    private static MainWindow instance;
    private JMenuBar windowMenuBar;

    /**
     * Constructor of this frame.
     */
    private MainWindow() {
        setSize(700, 700);
        setLocation(600, 200);
        setJMenuBar(getWindowMenuBar());
        setLayout(new BorderLayout());

        // Components
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel rightPadding = new JPanel();
        rightPadding.setPreferredSize(new Dimension(10, 10));
        DarkLightSwitchButton modeButton = new DarkLightSwitchButton("dark");
        topPanel.add(modeButton, BorderLayout.EAST);
        topPanel.add(rightPadding, BorderLayout.CENTER);

        // Add components
        add(new JLabel("MUSIC"), BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
    }

    /**
     * Returns the instance of this class.
     *
     * @return MainWindow
     */
    public static MainWindow getInstance() {
        if (instance == null) {
            instance = new MainWindow();
        }
        instance.setVisible(true);
        return instance;
    }

    /**
     * This method creates the JMenuBar and returns it.
     *
     * @return JMenuBar
     */
    private JMenuBar getWindowMenuBar() {
        if (windowMenuBar == null) {
            windowMenuBar = new JMenuBar();

            JMenu settingsMenu = new JMenu("Settings");
            JMenu helpMenu = new JMenu("Help");

            // Menu Items for "Settings"
            JMenuItem openMusicDirItem = new JMenuItem("Open music directory");
            openMusicDirItem.addActionListener(this::openMusicDirAction);
            JMenuItem configScheduleItem = new JMenuItem("Configurate news schedule");
            configScheduleItem.addActionListener(this::configScheduleAction);
            JMenuItem configCityItem = new JMenuItem("Configurate city");
            configCityItem.addActionListener(this::configCityAction);
            JMenuItem changeDesignItem = new JMenuItem("Change design");
            changeDesignItem.addActionListener(this::changeDesignAction);
            // Menu Items for "Help"
            JMenuItem getHelpItem = new JMenuItem("Get help");
            getHelpItem.addActionListener(this::getHelpAction);
            JMenuItem aboutItem = new JMenuItem("About");
            aboutItem.addActionListener(this::aboutAction);

            // Add all components
            settingsMenu.add(openMusicDirItem);
            settingsMenu.add(configScheduleItem);
            settingsMenu.add(configCityItem);
            settingsMenu.add(changeDesignItem);
            helpMenu.add(getHelpItem);
            helpMenu.add(aboutItem);
            windowMenuBar.add(settingsMenu);
            windowMenuBar.add(helpMenu);
        }
        return windowMenuBar;
    }

    /**
     * This method describes the actionEvent for the openMusicDirItem.
     * The method opens the directory for music in the explorer.
     *
     * @param event event
     */
    private void openMusicDirAction(ActionEvent event) {
        URL url = MainWindow.class.getResource("/music");
        System.out.println(String.valueOf(url));
        try {
            Runtime.getRuntime().exec("explorer " + url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method opens a dialog {@link ScheduleDialog}.
     * In this dialog the schedule for news can be edited.
     *
     * @param event
     */
    private void configScheduleAction(ActionEvent event) {
        ScheduleDialog.getInstance(instance);
    }

    /**
     * This method opens a dialog {@link ChangeCityDialog}.
     * In this dialog the city can be edited (used for weather).
     *
     * @param event
     */
    private void configCityAction(ActionEvent event) {
        ChangeCityDialog.getInstance(instance);
    }

    /**
     * This method opens a dialog {@link TODO}.
     * In this dialog the design of the look and feel can be changed.
     *
     * @param event
     */
    private void changeDesignAction(ActionEvent event) {
        //TODO
    }

    /**
     * This method opens a website in order to get help.
     *
     * @param event
     */
    private void getHelpAction(ActionEvent event) {
        //TODO
    }

    /**
     * This method opens a dialog {@link TODO}.
     * In this dialog some information are shown like version.
     *
     * @param event
     */
    private void aboutAction(ActionEvent event) {
        //TODO
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