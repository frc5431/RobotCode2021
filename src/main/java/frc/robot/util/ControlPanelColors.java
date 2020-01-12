package frc.robot.util;

public enum ControlPanelColors {
    YELLOW("Yellow"), RED("Red"), GREEN("Green"), BLUE("Blue");

    private String name;

    private ControlPanelColors(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}