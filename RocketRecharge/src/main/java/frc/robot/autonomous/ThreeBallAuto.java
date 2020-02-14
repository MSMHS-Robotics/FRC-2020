package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.ResetGyroCommand;
import frc.robot.commands.drivetrain.SetDrive;
//import frc.robot.commands.shooter.ShootAngleWarmupCommand;
//import frc.robot.commands.drivetrain.TurnOnHeading;
import frc.robot.commands.shooter.ShootCommand;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class ThreeBallAuto extends SequentialCommandGroup {
    public ThreeBallAuto(Drivetrain drivetrain, Intake intake, Shooter shooter) {
        super(
            new ResetGyroCommand(drivetrain),
            new ShootCommand(shooter, intake, null, 90, 5, true),
            new SetDrive(drivetrain, 0, 0.3, 12, 4), //drive us 5 seconds
            new SetDrive(drivetrain, 0, 0, 0, 10) //stop us
            );
    }
}

