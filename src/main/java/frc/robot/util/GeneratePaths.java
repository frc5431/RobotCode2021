package frc.robot.util;

import frc.robot.Constants;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.Filesystem;
import frc.team5431.titan.core.misc.Logger;
import frc.team5431.titan.pathfinder.PathLoader;
import frc.team5431.titan.pathfinder.PathLoader.Status;

public class GeneratePaths {
    private static String getPath(String name) throws IOException {
        File full_dir = new File(//
                Filesystem.getDeployDirectory(), //
                Constants.DRIVEBASE_PATHWEAVER_PATH_DIRECTORY);
        File trajectory = new File(full_dir, name);
        if (trajectory.exists()) {
            return trajectory.getAbsolutePath();
        }
        throw new IOException("File not Found");
    }

    private static PathLoader build(String name) throws IOException {
        String path = getPath(name);
        PathLoader ldr = new PathLoader(//
                Constants.DRIVEBASE_PATHWEAVER_CONFIG, path);
        if (ldr.getStatus() != Status.LOADED)
            throw new IOException("File not Parsable");
        return ldr;
    }

    public static Map<String, PathLoader> generate() {
        var data = new HashMap<String, PathLoader>();
        for (final String kName : Constants.DRIVEBASE_PATHWEAVER_PATHS) {
            PathLoader ldr = null;
            try {
                ldr = build(kName);
            } catch (IOException e) {
                Logger.l("%s cannot load due to: %s", kName, e.getMessage());
            }
            data.put(kName, ldr);
        }
        return data;
    }
}
