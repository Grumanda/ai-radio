package de.gozilalp;

import java.util.ArrayList;
import java.util.List;

public class SQLCommands {

    public static final String CREATE_SCHEDULE_TABLE = """
            CREATE TABLE IF NOT EXISTS
              schedule (
                scheduleId number (3),
                scheduleHour number (2),
                isActivated varchar2 (1) 
                CONSTRAINT pkSchedule PRIMARY key (scheduleId)
              );
            """;

    public static final String CREATE_SETTINGS_TABLE = """
            CREATE TABLE IF NOT EXISTS
              settings (
                settingsId number (3),
                designName varchar2 (20),
                city varchar2 (30) 
                CONSTRAINT pkSettings PRIMARY key (settingsId)
              );
            """;

    public static final String INSERT_SCHEDULE_DATA = """
            INSERT INTO schedule (scheduleId, scheduleHour, isActivated)
            VALUES (?, ?, n);
            """;

    public static final String INSERT_SETTINGS_DATA = """
            INSERT INTO settings (settingsId, designName, city)
            VALUES (1, 'light', 'Berlin')
            """;

    public static final String SELECT_SETTINGS_DATA = """
            SELECT * FROM settings;
            """;

    public static final String SELECT_SCHEDULE_DATA = """
            SELECT * FROM schedule;
            """;
}
