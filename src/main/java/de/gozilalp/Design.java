package de.gozilalp;

/**
 * This enum contains all designs (LookAndFeels).
 *
 * @author grumanda
 */
public enum Design {
    FLATLIGHTLAF("light"),
    FLATMATERIALDESIGNDARKIJTHEME("dark");

    private String name;

    private Design(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
