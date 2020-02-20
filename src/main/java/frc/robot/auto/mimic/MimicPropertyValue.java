package frc.robot.auto.mimic;

import java.util.function.Function;

import edu.wpi.first.wpilibj.RobotController;
import frc.robot.Robot;
import frc.robot.auto.Sequence;
import frc.team5431.titan.core.mimic.*;
import frc.team5431.titan.core.joysticks.*;

public enum MimicPropertyValue implements PropertyValue<Robot>{
        LEFT_DISTANCE(PropertyType.DOUBLE, (robot)->robot.getDrivebase().getLeftDistance()),
		RIGHT_DISTANCE(PropertyType.DOUBLE, (robot)->robot.getDrivebase().getRightDistance()),
		ANGLE(PropertyType.DOUBLE, (robot)->robot.getDrivebase().getHeading()),
		LEFT_POWER(PropertyType.DOUBLE, (robot)->robot.getDrivebase().getLeftSpeed()),
		RIGHT_POWER(PropertyType.DOUBLE, (robot)->robot.getDrivebase().getRightSpeed()),
		// SEQUENCE_TYPE(PropertyType.INTEGER, (robot)->robot.getAuton().getCurrentSequenceType().ordinal()),
		// RUNNING_SEQUENCE(PropertyType.INTEGER, (robot)->{
		// 	final Sequence runningSequence = robot.getAuton().getRunningSequence();
		// 	if(runningSequence == null){
		// 		return -1;
		// 	}else{
		// 		return runningSequence.ordinal();
		// 	}
		// });
        ;

		private final PropertyType type;
		private final Function<Robot, Object> getter;

		private MimicPropertyValue(final PropertyType type, final Function<Robot, Object> getter){
			this.type = type;
			this.getter = getter;
			
		}

		public PropertyType getType(){
			return type;
		}

		public Object get(final Robot robot){
			return getter.apply(robot);
		}


}