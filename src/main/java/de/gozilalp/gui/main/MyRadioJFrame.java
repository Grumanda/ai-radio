package de.gozilalp.gui.main;

import javax.swing.*;

/**
 * This class extends the JFrame. Here are some pre-modified things like
 * title, close operation or icon.
 *
 * @author grumanda
 */
public class MyRadioJFrame extends JFrame {

    /**
     * Icon for the program.
     */
    private final static ImageIcon ICON = new ImageIcon(
            MyRadioJFrame.class.getResource("/assets/my_radio.png"));

    /**
     * Constructor of the class.
     */
    public MyRadioJFrame() {
        setTitle("My Radio");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(ICON.getImage());
    }
}