package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.robot.commands.drivetrain.DriveOnHeadingCommand;
import frc.robot.commands.drivetrain.ResetGyroCommand;

import frc.robot.commands.shooter.ShootBurstCommand;
import frc.robot.commands.shooter.WarmupCommand;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

/** An uncharged (?) 3 ball auton */
public class UnchargedThreeBallAuto extends SequentialCommandGroup {
    /** A uncharged (?) 3 ball autonomous program 
     * @param drivetrain a Drivetrain subsystem
     * @param intake an intake subsystem
     * @param shooter a shooter subsystem
    */
    public UnchargedThreeBallAuto(Drivetrain drivetrain, Intake intake, Shooter shooter, Limelight limelight) {
        super(
            new ResetGyroCommand(drivetrain),
            new DriveOnHeadingCommand(drivetrain, 0, 0.5, 0.2),
            new DriveOnHeadingCommand(drivetrain, 0, -0.5, 0.3),
            new WarmupCommand(shooter, drivetrain, null, 90, true),
            new WaitCommand(1),
            new ShootBurstCommand(shooter, intake, drivetrain, limelight, null, 90, 5, true), //changed for testing
            new DriveOnHeadingCommand(drivetrain, 0, -0.3, 2)
            );
    }
}

