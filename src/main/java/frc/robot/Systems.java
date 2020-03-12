package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.*;
import frc.team5431.titan.core.misc.Logger;

public final class Systems {

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
		final String constucting = "Constructing %s Object!";

		Logger.l(constucting, "PowerDistributionPanel");
		pdp = new PowerDistributionPanel();

		// Independent Subsystems
		Logger.l(constucting, "Balancer");
		balancer = new Balancer();
		
		Logger.l(constucting, "Drivebase");
		drivebase = new Drivebase();
		
		Logger.l(constucting, "Elevator");
		elevator = new Elevator();
		
		Logger.l(constucting, "Flywheel");
		flywheel = new Flywheel();

		Logger.l(constucting, "Hopper");
		hopper = new Hopper();

		Logger.l(constucting, "Intake");
		intake = new Intake();

		// Dependent Subsystems
		Logger.l(constucting, "Feeder");
		feeder = new Feeder(pdp);

		Logger.l(constucting, "Pivot");
		pivot = new Pivot(pdp);

		subsystems = List.of(
			balancer, drivebase, elevator, flywheel, hopper, intake, feeder, pivot
		);
	}

	/**
	 * This Function Will Clear All Commands From Every Subsystem
	 */
	public void clearAllCommands() {
		Logger.l("Clearing Commands In All Subsystems");
		subsystems.forEach((subsystem) -> subsystem.getCurrentCommand().cancel());
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
}