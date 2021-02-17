package frc.robot;

import java.util.List;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.*;
import frc.team5431.titan.core.misc.Logger;

public class Systems {

    private final WPI_TalonSRX balancer_Talon;

    private final WPI_TalonFX drivebase_Falcon_Left_Back;
    private final WPI_TalonFX drivebase_Falcon_Left_Front;

    private final WPI_TalonFX drivebase_Falcon_Right_Back;
    private final WPI_TalonFX drivebase_Falcon_Right_Front;

    private final WPI_TalonFX elevator_Falcon;

    private final WPI_TalonFX flywheel_Falcon_Left;
    private final WPI_TalonFX flywheel_Falcon_Right;

    private final WPI_TalonSRX hopper_Talon_Left;
    private final WPI_TalonSRX hopper_Talon_Right;

    private final WPI_TalonFX intake_Falcon;

    private final WPI_TalonFX feeder_Falcon;

    private final WPI_TalonFX pivot_Falcon;

    private final PowerDistributionPanel pdp;

    private final Balancer balancer;
    private final Drivebase drivebase;
    private final Elevator elevator;
    private final Feeder feeder;
    private final Flywheel flywheel;
    private final Hopper hopper;
    private final Intake intake;
    private final Pivot pivot;

    private final List<SubsystemBase> subsystems;

    public Systems() {

        balancer_Talon = new WPI_TalonSRX(Constants.CLIMBER_BALANCER_ID);

        drivebase_Falcon_Left_Back = new WPI_TalonFX(Constants.DRIVEBASE_BACK_LEFT_ID);
        drivebase_Falcon_Left_Front = new WPI_TalonFX(Constants.DRIVEBASE_FRONT_LEFT_ID);
        drivebase_Falcon_Right_Back = new WPI_TalonFX(Constants.DRIVEBASE_BACK_RIGHT_ID);
        drivebase_Falcon_Right_Front = new WPI_TalonFX(Constants.DRIVEBASE_FRONT_RIGHT_ID);

        elevator_Falcon = new WPI_TalonFX(Constants.CLIMBER_ELEVATOR_ID);

        flywheel_Falcon_Left = new WPI_TalonFX(Constants.SHOOTER_FLYWHEEL_LEFT_ID);
        flywheel_Falcon_Right = new WPI_TalonFX(Constants.SHOOTER_FLYWHEEL_RIGHT_ID);

        hopper_Talon_Left = new WPI_TalonSRX(Constants.HOPPER_LEFT_ID);
        hopper_Talon_Right = new WPI_TalonSRX(Constants.HOPPER_RIGHT_ID);

        intake_Falcon = new WPI_TalonFX(Constants.INTAKE_ID);

        feeder_Falcon = new WPI_TalonFX(Constants.SHOOTER_FEEDER_ID);

        pivot_Falcon = new WPI_TalonFX(Constants.PIVOT_ID);

        final String constructingMsg = "Constructing %s Object!";

        Logger.l(constructingMsg, "PowerDistributionPanel");
        pdp = new PowerDistributionPanel();

        // Independent Subsystems
        Logger.l(constructingMsg, "Balancer");
        balancer = new Balancer(balancer_Talon);

        Logger.l(constructingMsg, "Drivebase");
        drivebase = new Drivebase(drivebase_Falcon_Left_Front, drivebase_Falcon_Right_Front, drivebase_Falcon_Left_Back,
                drivebase_Falcon_Right_Back);

        Logger.l(constructingMsg, "Elevator");
        elevator = new Elevator(elevator_Falcon);

        Logger.l(constructingMsg, "Flywheel");
        flywheel = new Flywheel(flywheel_Falcon_Left, flywheel_Falcon_Right);

        Logger.l(constructingMsg, "Hopper");
        hopper = new Hopper(hopper_Talon_Left, hopper_Talon_Right);

        Logger.l(constructingMsg, "Intake");
        intake = new Intake(intake_Falcon);

        // Dependent Subsystems
        Logger.l(constructingMsg, "Feeder");
        feeder = new Feeder(pdp, feeder_Falcon);

        Logger.l(constructingMsg, "Pivot");
        pivot = new Pivot(pdp, pivot_Falcon);

        subsystems = List.of(balancer, drivebase, elevator, flywheel, hopper, intake, feeder, pivot);
    }

    /**
     * Clears all commands from every subsystem
     */
    public void clearAllCommands() {
        Logger.l("Clearing Commands In All Subsystems");
        subsystems.forEach(subsystem -> {
            Command command = subsystem.getCurrentCommand();
            if (command != null)
                command.cancel();
        });
    }

    /**
     * @return the balancer
     */
    public Balancer getBalancer() {
        return balancer;
    }

    /**
     * @return the drivebase
     */
    public Drivebase getDrivebase() {
        return drivebase;
    }

    /**
     * @return the elevator
     */
    public Elevator getElevator() {
        return elevator;
    }

    /**
     * @return the flywheel
     */
    public Flywheel getFlywheel() {
        return flywheel;
    }

    /**
     * @return the hopper
     */
    public Hopper getHopper() {
        return hopper;
    }

    /**
     * @return the intake
     */
    public Intake getIntake() {
        return intake;
    }

    /**
     * @return the feeder
     */
    public Feeder getFeeder() {
        return feeder;
    }

    /**
     * @return the pivot
     */
    public Pivot getPivot() {
        return pivot;
    }

    /**
     * @return the pdp
     */
    public PowerDistributionPanel getPdp() {
        return pdp;
    }

    /**
     * 
     * @return a list of all Falcon 500s on the robot
     */
    public List<WPI_TalonFX> getAllFalcons() {
        return List.of(drivebase_Falcon_Left_Back, drivebase_Falcon_Left_Front, drivebase_Falcon_Right_Back,
                drivebase_Falcon_Right_Front, elevator_Falcon, flywheel_Falcon_Left, flywheel_Falcon_Right,
                intake_Falcon, feeder_Falcon, pivot_Falcon);
    }
}