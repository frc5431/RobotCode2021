package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.*;
import frc.team5431.titan.core.misc.Logger;

/**
 * A command wrapper class for Logger
 * 
 * @author Colin Wong
 * @deprecated
 */

@Deprecated
public class LoggerCommand extends CommandBase {
    /**
     * Sends a log message to the RioLog.
     * 
     * @param str    The string to log
     * @param append The objects to append to the string
     */
    public LoggerCommand(String str, Object... append) {
        Logger.l(str, append);
    }

    /**
     * Sends an error message to the RioLog.
     * The value of error does not matter - only that it is present.
     * 
     * @param str    The string to log
     * @param append The objects to append to the string
     */
    public LoggerCommand(boolean error, String str, Object... append) {
        Logger.e(str, append);
    }

    /**
     * Sends an exception message to the RioLog.
     * 
     * @param namespace The namespace of the exception
     * @param e         The exception
     */
    public LoggerCommand(String namespace, Exception e) {
        Logger.ee(namespace, e);
    }
}