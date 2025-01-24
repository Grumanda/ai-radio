package de.gozilalp.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDialog extends JDialog {

    private static ScheduleDialog instance;
    private List<JCheckBox> checkBoxList;
    private JButton saveButton;

    private ScheduleDialog(JFrame mainInstance) {
        setSize(400, 400);
        setLocationRelativeTo(mainInstance);
        setLayout(new BorderLayout());

        // Panel and content
        JPanel contentPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        contentPanel.setAutoscrolls(true);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        getCheckBoxList().forEach(contentPanel::add);

        // ButtonPanel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(getSaveButton());

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    public static ScheduleDialog getInstance(JFrame mainInstance) {
        if (instance == null) {
            instance = new ScheduleDialog(mainInstance);
        }
        return instance;
    }

    private List<JCheckBox> getCheckBoxList() {
        if (checkBoxList == null) {
            checkBoxList = new ArrayList<>();
            for (int i = 1; i <= 24; i++) {
                JCheckBox checkBox = new JCheckBox(i + ":00");
                checkBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
                checkBoxList.add(checkBox);
            }
        }
        return checkBoxList;
    }

    private List<String> getSelectedSchedule() {
        List<String> selectedScheduleList = new ArrayList<>();
        getCheckBoxList().forEach(checkBox -> {
            if (checkBox.isSelected()) {
                String text = checkBox.getText();
                String[] parts = text.split(":");
                selectedScheduleList.add(parts[0]);
            }
        });
        return selectedScheduleList;
    }

    private JButton getSaveButton() {
        if (saveButton == null) {
            saveButton = new JButton();
            saveButton.setText("Save");
            saveButton.addActionListener(this::saveButtonAction);
        }
        return saveButton;
    }

    private void saveButtonAction(ActionEvent event) {
        getSelectedSchedule().forEach(schedule -> System.out.println(schedule));
        //TODO datenbank access
        instance.dispose();
    }
}
