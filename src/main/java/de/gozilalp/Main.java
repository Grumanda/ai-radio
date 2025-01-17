package de.gozilalp;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatMaterialDesignDarkIJTheme;
import de.gozilalp.gui.MainWindow;
import org.h2.tools.Server;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            DatabaseConnector databaseConnector = DatabaseConnector.getInstance();
            Server.main();
            System.out.println("H2-Console: http://localhost:8082");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        loadDesign(Design.FLATLIGHTLAF);
        MainWindow.getInstance();
    }

    private static void loadDesign(Design design) {
        switch (design) {
            case FLATMATERIALDESIGNDARKIJTHEME -> FlatMaterialDesignDarkIJTheme.setup();
            default -> FlatLightLaf.setup();
        }
    }
}