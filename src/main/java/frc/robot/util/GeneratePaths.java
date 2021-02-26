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

    private static PathLoader build(String name) throws IOException {
        PathLoader ldr = new PathLoader(//
                Constants.DRIVEBASE_PATHWEAVER_CONFIG, name);
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
