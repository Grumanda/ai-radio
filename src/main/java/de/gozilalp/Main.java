package de.gozilalp;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatMaterialDesignDarkIJTheme;
import de.gozilalp.gui.MainWindow;

public class Main {

    public static void main(String[] args) {
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