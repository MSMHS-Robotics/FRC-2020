package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.robot.commands.drivetrain.DriveOnHeading;
import frc.robot.commands.drivetrain.ResetGyroCommand;
import frc.robot.commands.intake.DeployIntake;

import frc.robot.commands.shooter.ShootBurstCommand;
import frc.robot.commands.shooter.WarmupCommand;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

/** A 3 ball autonomous program */
public class ThreeBallAuto extends SequentialCommandGroup {
    
    /** A 3 ball autonomous program 
     * @param drivetrain a Drivetrain subsystem
     * @param intake an intake subsystem
     * @param shooter a shooter subsystem
    */
    public ThreeBallAuto(Drivetrain drivetrain, Intake intake, Shooter shooter) {
        super(
            new ResetGyroCommand(drivetrain),
            new DeployIntake(intake, 1),
            new WarmupCommand(shooter, null, 90, true, drivetrain),
            new WaitCommand(1),
            new ShootBurstCommand(shooter, intake, null, 90, 5, true, drivetrain),
            new DriveOnHeading(drivetrain, 0, -0.3, 2)
            );
    }
}

