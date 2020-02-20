package frc.robot.auto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import frc.robot.Robot;
import frc.robot.util.ComponentControlMode;
import frc.team5431.titan.core.robot.Command;
import frc.team5431.titan.core.robot.ConditionalCommand;
import frc.team5431.titan.core.robot.ConsumerCommand;
import frc.team5431.titan.core.robot.ParallelCommandGroup;
import frc.team5431.titan.mimic.*;

public enum Path{
    HAB1_TO_FROCKET_GEN((sequence, swapped)->{
        final ParallelCommandGroup<Robot> group = new ParallelCommandGroup<>();
        // final DriveToArcCommand arc = new DriveToArcCommand(-241, -0.8, 35 * swapped);
        // group.addQueue(List.of(arc, new TurnCommand(-(90-61.25) * swapped)));
        // group.addQueue(List.of(new ConditionalCommand<>((rob)->arc.getProgress(rob.getDrivebase()) >= 0.3),new ConsumerCommand<>((rob)->{
        //     rob.getAuton().runSequence(rob, SequenceType.HATCH, sequence);
        // })));
        return List.of(group);
    }),
    TEST(mimicGenerator("TEST"));

    private static BiFunction<Sequence, Double, List<Command<Robot>>> mimicGenerator(final String name){
        return (sequence, swapped)->{
            // final List<Command<Robot>> outCommands = new ArrayList<>();

            // int lastRunningSequence = -1;

            // //Collect the mimic file
            // //mimicChooser.getSelected()

            // Repeater.prepare(name);
            // final List<Stepper> steps = Repeater.getData();
            // for(final Stepper step : steps){
            //     final List<Command<Robot>> out = new ArrayList<>();
            //     if(step.getBoolean(MimicPropertyValue.HOME)){
            //         out.add(new Titan.ConsumerCommand<>((rob)->{
            //             rob.getDrivebase().reset();
            //         }));
            //     }

            //     final int stepSequence = step.getInteger(MimicPropertyValue.RUNNING_SEQUENCE);
            //     if(sequence != null && stepSequence >= 0 && stepSequence != lastRunningSequence){
            //         out.add(new ConsumerCommand<>((rob)->{
            //             //Sequence.values()[stepSequence];
            //             rob.getAuton().runSequence(rob, SequenceMode.values(), sequence);
            //         }));
            //     }
            //     lastRunningSequence = stepSequence;

            //     final double leftPower, leftDistance;
            //     final double rightPower, rightDistance;
            //     final double angle;

            //     if(swapped == -1){
            //         leftPower = step.getDouble(MimicPropertyValue.RIGHT_POWER);
            //         rightPower = step.getDouble(MimicPropertyValue.LEFT_POWER);

            //         leftDistance = step.getDouble(MimicPropertyValue.RIGHT_DISTANCE);
            //         rightDistance = step.getDouble(MimicPropertyValue.LEFT_DISTANCE);

            //         angle = -step.getDouble(MimicPropertyValue.ANGLE);
            //     }else{
            //         leftPower = step.getDouble(MimicPropertyValue.LEFT_POWER);
            //         rightPower = step.getDouble(MimicPropertyValue.RIGHT_POWER);

            //         leftDistance = step.getDouble(MimicPropertyValue.LEFT_DISTANCE);
            //         rightDistance = step.getDouble(MimicPropertyValue.RIGHT_DISTANCE);

            //         angle = step.getDouble(MimicPropertyValue.ANGLE);
            //     }

            //     out.add(new MimicDriveCommand(leftPower, rightPower, leftDistance, rightDistance, angle, step.getDouble(MimicPropertyValue.BATTERY)));

            //     if(out.size() == 1){
            //         outCommands.add(out.get(0));
            //     }else{
            //         final Titan.ParallelCommandGroup<Robot> group = new Titan.ParallelCommandGroup<>();
            //         for(final Titan.Command<Robot> com : out){
            //             group.addCommand(com);
            //         }
            //         outCommands.add(group);
            //     }
            // }
            // return outCommands;
            return null;
        };
    };

    private final BiFunction<Sequence, Double, List<Command<Robot>>> generator;

    private Path(final BiFunction<Sequence, Double, List<Command<Robot>>> gen){
        this.generator = gen;
    }

    public List<Command<Robot>> generate(final Sequence sequence, final boolean swapped){
        final List<Command<Robot>> out = new ArrayList<>();
        out.add(new ConsumerCommand<>((rob)->{
            rob.getDrivebase().resetSensors();

            rob.getDrivebase().setControlMode(ComponentControlMode.AUTO);
        }));
        out.addAll(generator.apply(sequence, swapped ? -1.0 : 1.0));
        out.add(new ConsumerCommand<>((rob)->{
            System.out.println("Finished path");
            rob.getDrivebase().resetSensors();
        }));
        return out;
    }
}