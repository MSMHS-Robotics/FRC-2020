package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.ResetGyroCommand;
import frc.robot.commands.drivetrain.SetDrive;
//import frc.robot.commands.drivetrain.TurnOnHeading;
import frc.robot.subsystems.Drivetrain;

public class DriveOffLine extends SequentialCommandGroup {
    public DriveOffLine(Drivetrain drivetrain) {
        super(
            new ResetGyroCommand(drivetrain),
            new SetDrive(drivetrain, 0, 0.3, 12, 4), //drive us 5 seconds
            new SetDrive(drivetrain, 0, 0, 0, 10) //stop us
            );
    }
}

