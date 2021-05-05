package frc.robot.subsystems;

import java.util.ArrayList;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.TalonSRXSimCollection;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMUSimCollection;
import com.ctre.phoenix.sensors.WPI_PigeonIMU;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.system.LinearSystem;
import edu.wpi.first.wpilibj.system.plant.DCMotor;
import edu.wpi.first.wpilibj.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpiutil.math.Matrix;
import edu.wpi.first.wpiutil.math.VecBuilder;
import edu.wpi.first.wpiutil.math.numbers.*;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.util.FalconUtils;
import frc.team5431.titan.core.subsystem.TitanDifferentalDrivebase;
/*
 * a lot of asserts were added as there are many things that can go wrong in this code
*/

/**
 * @author Ryan Hirasaki
 * @author Colin Wong
 */
public class Drivebase extends SubsystemBase {

    private static class ProcessError {
        public interface Function {
            ErrorCode run();
        }

        public static void test(Function lambda) {
            ErrorCode code = lambda.run();
            // Weird syntax but valid
            // https://docs.oracle.com/javase/8/docs/technotes/guides/language/assert.html
            assert (code == ErrorCode.OK) : code.toString();
        }

        public static BaseTalon test_motor(BaseTalon talon) {
            assert talon instanceof SpeedController;
            if (Robot.isReal())
                assert talon instanceof WPI_TalonFX;
            else
                assert talon instanceof WPI_TalonSRX;
            return talon;
        }
    }

    private final WPI_PigeonIMU pigeon;

    private final BaseTalon leftMain;
    private final BaseTalon rightMain;

    private final BaseTalon leftFollow;
    private final BaseTalon rightFollow;

    private final DifferentialDriveOdometry odometry;
    private final DifferentialDriveKinematics driveKinematics = new DifferentialDriveKinematics(
            Constants.ROBOT_DRIVEBASE_TRACK_WIDTH);
    private final SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(//
            Constants.ROBOT_TRAJECTORY_kS, //
            Constants.ROBOT_TRAJECTORY_kV, //
            Constants.ROBOT_TRAJECTORY_kA);

    private final Field2d field_2d = new Field2d();
    private final DifferentialDrivetrainSim drivetrainSim;
    private final TalonSRXSimCollection leftDriveSim;
    private final TalonSRXSimCollection rightDriveSim;
    private final PigeonIMUSimCollection pigeonSim;

    public Drivebase(BaseTalon frontLeft, BaseTalon frontRight, BaseTalon rearLeft, BaseTalon rearRight) {
        pigeon = new WPI_PigeonIMU(Constants.DRIVEBASE_PIGEON_IMU_ID);

        leftMain = ProcessError.test_motor(frontLeft);
        rightMain = ProcessError.test_motor(frontRight);
        leftFollow = ProcessError.test_motor(rearLeft);
        rightFollow = ProcessError.test_motor(rearRight);

        leftMain.configFactoryDefault();
        rightMain.configFactoryDefault();

        leftFollow.follow(leftMain);
        rightFollow.follow(rightMain);

        leftFollow.setInverted(InvertType.FollowMaster);
        rightFollow.setInverted(InvertType.FollowMaster);
        leftMain.setInverted(InvertType.None);
        leftMain.setSensorPhase(false);
        // Right set later

        // Reset encoders
        leftMain.setSelectedSensorPosition(0);
        rightMain.setSelectedSensorPosition(0);

        odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));

        drivetrainSim = new DifferentialDrivetrainSim(//
                Constants.ROBOT_DRIVEBASE_LINEAR_SYSTEM, Constants.ROBOT_DRIVEBASE_GEAR_BOX, //
                Constants.ROBOT_DRIVEBASE_GEAR_RATIO, //
                Constants.ROBOT_DRIVEBASE_TRACK_WIDTH, //
                Constants.ROBOT_DRIVEBASE_WHEEL_RADIUS, // Get radius from circumfrence
                null);
        pigeonSim = pigeon.getSimCollection();

        // Only needs to be published once
        SmartDashboard.putData("Field", field_2d);
        if (Robot.isSimulation()) {
            // invert right side only on real robot, as simulation expects both side
            // positive
            rightMain.setInverted(InvertType.None);
            rightMain.setSensorPhase(false);
            // Will only work as on simulation left and right are actually WPI_TalonSRX
            leftDriveSim = ((WPI_TalonSRX) leftMain).getSimCollection();
            rightDriveSim = ((WPI_TalonSRX) rightMain).getSimCollection();
        } else {
            rightMain.setInverted(InvertType.InvertMotorOutput);
            rightMain.setSensorPhase(true);
            leftDriveSim = null;
            rightDriveSim = null;
        }
    }

    @Override
    public void periodic() {
        odometry.update(pigeon.getRotation2d(),
                FalconUtils.nativeUnitsToDistanceMeters(leftMain.getSelectedSensorPosition()),
                FalconUtils.nativeUnitsToDistanceMeters(rightMain.getSelectedSensorPosition()));
        field_2d.setRobotPose(odometry.getPoseMeters());

        SmartDashboard.putNumber("Left Main Voltage", leftMain.getMotorOutputVoltage());
        SmartDashboard.putNumber("Right Main Voltage", rightMain.getMotorOutputVoltage());
        SmartDashboard.putNumber("Left Follow Voltage", leftFollow.getMotorOutputVoltage());
        SmartDashboard.putNumber("Right Follow Voltage", rightFollow.getMotorOutputVoltage());
        SmartDashboard.putNumber("Left Velocity",
                FalconUtils.velocityToNativeUnits(leftMain.getSelectedSensorVelocity()));
        SmartDashboard.putNumber("Right Velocity",
                FalconUtils.velocityToNativeUnits(rightMain.getSelectedSensorVelocity()));
    }

    @Override
    public void simulationPeriodic() {
        // go to https://bit.ly/3puz4bm for more info
        drivetrainSim.setInputs(leftMain.getMotorOutputVoltage(), rightMain.getMotorOutputVoltage());
        drivetrainSim.update(Robot.kDefaultPeriod);

        var leftPosition = leftMain.getSelectedSensorPosition();
        var rightPosition = rightMain.getSelectedSensorPosition();
        var leftVelocity = leftMain.getSelectedSensorVelocity();
        var rightVelocity = rightMain.getSelectedSensorVelocity();
        leftDriveSim.setQuadratureRawPosition(FalconUtils.distanceToNativeUnits(leftPosition));
        leftDriveSim.setQuadratureVelocity(FalconUtils.velocityToNativeUnits(leftVelocity));
        rightDriveSim.setQuadratureRawPosition(FalconUtils.distanceToNativeUnits(rightPosition));
        rightDriveSim.setQuadratureVelocity(FalconUtils.velocityToNativeUnits(rightVelocity));

        double bus_voltage = RobotController.getBatteryVoltage();
        leftDriveSim.setBusVoltage(bus_voltage);
        rightDriveSim.setBusVoltage(bus_voltage);

        pigeonSim.setRawHeading(-drivetrainSim.getHeading().getDegrees());
    }

    public void resetOdometry(Pose2d pose) {
        leftMain.setSelectedSensorPosition(0);
        rightMain.setSelectedSensorPosition(0);
        odometry.resetPosition(pose, pigeon.getRotation2d());
    }

    public void tankDriveVolts(double leftV, double rightV) {
        ((SpeedController) leftMain).setVoltage(leftV);
        ((SpeedController) rightMain).setVoltage(-rightV);
    }

    public void tankDrive(double leftP, double rightP) {
        leftMain.set(ControlMode.PercentOutput, leftP);
        rightMain.set(ControlMode.PercentOutput, rightP);
    }

    public void arcadeDrive(double pow, double turn) {
        leftMain.set(ControlMode.PercentOutput, pow, DemandType.ArbitraryFeedForward,
                -turn * Constants.DRIVEBASE_TURN_MAX_SPEED);
        rightMain.set(ControlMode.PercentOutput, pow, DemandType.ArbitraryFeedForward,
                +turn * Constants.DRIVEBASE_TURN_MAX_SPEED);
    }

    public void drawTrajectory(Trajectory trajectory) {
        ArrayList<Pose2d> poses = new ArrayList<>();

        for (Trajectory.State pose : trajectory.getStates()) {
            poses.add(pose.poseMeters);
        }
    }

    public DifferentialDriveKinematics getKinematics() {
        return driveKinematics;
    }

    public SimpleMotorFeedforward getFeedforward() {
        return feedforward;
    }

    public DifferentialDriveOdometry getOdometry() {
        return odometry;
    }

    public double getHeading() {
        return pigeon.getRotation2d().getDegrees();
    }

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(//
                FalconUtils.velocityToNativeUnits(leftMain.getSelectedSensorVelocity()),
                FalconUtils.velocityToNativeUnits(rightMain.getSelectedSensorVelocity()));
    }
}