package de.gozilalp.gui.main;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DarkLightSwitchButton extends JButton {

    private Icon darkIcon = new FlatSVGIcon(DarkLightSwitchButton.class.getResource(
            "/assets/dark.svg"));
    private Icon lightIcon = new FlatSVGIcon(DarkLightSwitchButton.class.getResource(
            "/assets/light.svg"));
    private String appliedMode;

    public DarkLightSwitchButton(String startMode) {
        appliedMode = startMode;
        if (startMode == "dark") {
            setIcon(darkIcon);
        } else {
            setIcon(lightIcon);
        }
        addActionListener(this::switchMode);

        setBounds(100, 50, 50, 50);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
    }

    private void switchMode(ActionEvent event) {
        if (appliedMode == "dark") {
            setIcon(lightIcon);
            appliedMode = "light";
        } else {
            setIcon(darkIcon);
            appliedMode = "dark";
        }
        updateUI();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Antialiasing aktivieren

        if (getModel().isArmed()) {
            g2.setColor(Color.LIGHT_GRAY); // Farbe wenn geklickt
        } else {
            g2.setColor(getBackground());
        }
        g2.fillOval(0, 0, getWidth(), getHeight()); // Kreis zeichnen

        super.paintComponent(g2);
    }


    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Antialiasing aktivieren

        g2.setColor(getForeground()); // Farbe der Border
        g2.drawOval(0, 0, getWidth() - 1, getHeight() - 1); // Kreis-Border zeichnen
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(50, 50); // Standardgröße als Kreis
    }
}
