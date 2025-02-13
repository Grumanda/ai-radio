package de.gozilalp.gui.settings.schedule;

import de.gozilalp.DatabaseConnector;
import de.gozilalp.Main;
import de.gozilalp.Schedule;

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
        setTitle("Schedule");
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
    }

    public static ScheduleDialog getInstance(JFrame mainInstance) {
        if (instance == null) {
            instance = new ScheduleDialog(mainInstance);
        }
        instance.setVisible(true);
        return instance;
    }

    private List<JCheckBox> getCheckBoxList() {
        if (checkBoxList == null) {
            checkBoxList = new ArrayList<>();
            for (int i = 1; i <= 24; i++) {
                JCheckBox checkBox = new JCheckBox(i + ":00");
                checkBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
                if (Main.scheduleList.get(i - 1).isActivated()) {
                    checkBox.setSelected(true);
                }
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
        // update list
        List<Schedule> scheduleList = Main.scheduleList;
        for (Schedule schedule : scheduleList) {
            String scheduleHour = String.valueOf(schedule.getHour());
            if (getSelectedSchedule().contains(scheduleHour)) {
                schedule.setActivated(true);
            } else {
                schedule.setActivated(false);
            }
        }

        // update schedule in database and restart
        DatabaseConnector.getInstance().saveNewSchedule(Main.scheduleList);
        instance.dispose();
        //TODO neustarten
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
