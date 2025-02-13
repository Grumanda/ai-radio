package de.gozilalp;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatDarkFlatIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatMaterialDesignDarkIJTheme;
import de.gozilalp.gui.main.MainWindow;
import de.gozilalp.gui.settings.city.ChangeCityDialog;
import de.gozilalp.gui.settings.design.ChangeDesignDialog;
import de.gozilalp.gui.settings.schedule.ScheduleDialog;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static List<Schedule> scheduleList = new ArrayList<>();
    public static String design; //TODO Datenbankabfrage
    public static String city; //TODO Datenbankabfrage

    public static void main(String[] args) {
        startSession();
    }

    public static void startSession() {
        disposeAll();

        DatabaseConnector database = DatabaseConnector.getInstance();
        database.setupSchedule();
        loadDesign();
        MainWindow.getInstance();
    }

    private static void disposeAll() {
        ScheduleDialog.tryFinalize();
        ChangeDesignDialog.tryFinalize();
        ChangeCityDialog.tryFinalize();
        MainWindow.tryFinalize();
    }

    private static void loadDesign() {
        if (design == "white") {
            FlatLightLaf.setup();
        } else {
            FlatMaterialDesignDarkIJTheme.setup();
        }
    }
}