package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.ResetGyroCommand;
import frc.robot.commands.drivetrain.SetDriveCommand;

import frc.robot.subsystems.Drivetrain;

/** A simple auto program that drives off the line, but in the opposite direction */
public class DriveOffLineReverse extends SequentialCommandGroup {

    /** A simple auto program that drives off the line, but in the opposite direction
     * @param drivetrain a Drivetrain subsystem
    */
    public DriveOffLineReverse(Drivetrain drivetrain) {
        super(
            new ResetGyroCommand(drivetrain), // so we always start with the gyro aligned with the direction the robot is pointing
            new SetDriveCommand(drivetrain, 0, -0.3, 12, 4), //d rive us 4 seconds or about 10 inches
            new SetDriveCommand(drivetrain, 0, 0, 0, 10) // stop us
            );
    }
}