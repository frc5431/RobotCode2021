package frc.robot.util;

import frc.robot.Constants;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import frc.team5431.titan.core.misc.Logger;
import frc.team5431.titan.pathfinder.PathLoader;
import frc.team5431.titan.pathfinder.PathLoader.Status;

public class GeneratePaths {

    private static PathLoader build(String name) throws IOException {
        PathLoader ldr = new PathLoader(//
                Constants.DRIVEBASE_PATHWEAVER_CONFIG, name);
        if (ldr.getStatus() == Status.ERROR_PARSE)
            throw new IOException("File not Parsable");
        if (ldr.getStatus() == Status.ERROR_NON_REAL)
            throw new IOException("File not Opened");
        return ldr;
    }

    public static Map<String, PathLoader> generate() {
        var data = new HashMap<String, PathLoader>();
        for (String kName : Constants.DRIVEBASE_PATHWEAVER_PATHS) {
            kName = "paths/" + kName;
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
