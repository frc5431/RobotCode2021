package frc.robot;

public final class Constants {
    public static enum RobotType {
        PRACTICE, COMPETITION;
    }

    // I am defining colors this way so there is less in the namespace and is easier
    // for the code to choose which values to use.
    private static enum ColorTypes {
        // TODO: Calibrate Sensor for competition as Competition Values from rulebook
        // Practice RGB, Competition RGB
        YELLOW(rgb(0, 0, 0), rgb(1, 1, 0)), RED(rgb(0, 0, 0), rgb(1, 0, 0)), GREEN(rgb(0, 0, 0), rgb(0, 1, 0)),
        BLUE(rgb(0, 0, 0), rgb(0, 1, 1));

        ColorTypes(double[] practice, double[] competition) {
            value = (ROBOT_TYPE == RobotType.PRACTICE) ? practice : competition;
        }

        private double[] value;

        private static double[] rgb(double r, double g, double b) {
            return new double[] { r, g, b };
        }

        public double[] getValue() {
            return value;
        }
    }

    public static final RobotType ROBOT_TYPE = RobotType.PRACTICE;

    public static final double[] YELLOW = ColorTypes.YELLOW.getValue();
    public static final double[] RED = ColorTypes.RED.getValue();
    public static final double[] GREEN = ColorTypes.GREEN.getValue();
    public static final double[] BLUE = ColorTypes.BLUE.getValue();

    public static final double CONTROLPANEL_CONFIDENCE = 0.75;

}