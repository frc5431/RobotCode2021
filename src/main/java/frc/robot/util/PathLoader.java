package frc.robot.util;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;

public class PathLoader {
    public static Trajectory FromFile(String file) {
        final Path filepath = Filesystem.getDeployDirectory().toPath().resolve(file);
        Trajectory trajectory;
        try {
            trajectory = TrajectoryUtil.fromPathweaverJson(filepath);
        } catch (IOException e) {
            trajectory = new Trajectory();
        }
        return trajectory;
    }

    public static Trajectory FromSample() {
        return TrajectoryGenerator.generateTrajectory(//
                new Pose2d(2, 2, new Rotation2d()), //
                List.of(), //
                new Pose2d(6, 4, new Rotation2d()), //
                new TrajectoryConfig(2, 2));
    }
}
