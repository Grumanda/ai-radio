package de.gozilalp.gui.settings.design;

import javax.swing.*;

public class ChangeDesignDialog extends JDialog {

    private static ChangeDesignDialog instance;

    private ChangeDesignDialog(JFrame mainInstance) {
        setLocationRelativeTo(mainInstance);
        //TODO
    }

    public static ChangeDesignDialog getInstance(JFrame mainInstance) {
        if (instance == null) {
            instance = new ChangeDesignDialog(mainInstance);
        }
        instance.setVisible(true);
        return instance;
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
