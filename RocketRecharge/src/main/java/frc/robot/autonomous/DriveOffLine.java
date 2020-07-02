package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.ResetGyroCommand;
import frc.robot.commands.drivetrain.SetDrive;

import frc.robot.subsystems.Drivetrain;

/** A simple auto program that drives off the line */
public class DriveOffLine extends SequentialCommandGroup {

    /** A simple auto program that drives off the line
     * @param drivetrain a Drivetrain subsystem
     */
    public DriveOffLine(Drivetrain drivetrain) {
        super(
            new ResetGyroCommand(drivetrain), // reset
            new SetDrive(drivetrain, 0, 0.3, 12, 4), // drive us 5 seconds
            new SetDrive(drivetrain, 0, 0, 0, 10) // stop us
            );
    }
}

