package de.gozilalp.gui;

import javax.swing.*;

public class ChangeDesignDialog extends JDialog {

    private static ChangeDesignDialog instance;

    private ChangeDesignDialog(JFrame mainInstance) {
        setLocationRelativeTo(mainInstance);
    }

    public static ChangeDesignDialog getInstance(JFrame mainInstance) {
        if (instance == null) {
            instance = new ChangeDesignDialog(mainInstance);
        }
        return instance;
    }
}
