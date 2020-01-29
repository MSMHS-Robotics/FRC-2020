package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ResetGyroCommand;
import frc.robot.commands.drivetrain.SetDrive;
//import frc.robot.commands.drivetrain.TurnOnHeading;
import frc.robot.subsystems.Drivetrain;

public class DriveOffLineReverse extends SequentialCommandGroup {
    public DriveOffLineReverse(Drivetrain drivetrain) {
        super(
            new ResetGyroCommand(drivetrain), //so we always start with the gyro aligned with the direction the robot is pointing
            new SetDrive(drivetrain, 0, -0.3, 12, 4), //drive us 4 seconds or about 10 inches
            new SetDrive(drivetrain, 0, 0, 0, 10) //stop us
            );
    }
}