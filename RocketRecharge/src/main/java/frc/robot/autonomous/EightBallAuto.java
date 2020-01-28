package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.SetDrive;
import frc.robot.commands.drivetrain.TurnOnHeading;
import frc.robot.subsystems.Drivetrain;

public class EightBallAuto extends SequentialCommandGroup{
    public EightBallAuto(Drivetrain drivetrain){
        super(
            new TurnOnHeading(drivetrain, 115, 2), //turn 135 degrees
            new SetDrive(drivetrain, 115, -0.4, 75, 5),
            new SetDrive(drivetrain, 115, 0, 0, 0.1),
            new TurnOnHeading(drivetrain, 180, 1.5)
            );
    }
}

