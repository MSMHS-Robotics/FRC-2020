package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.robot.commands.drivetrain.DriveOnHeading;
import frc.robot.commands.drivetrain.ResetGyroCommand;
import frc.robot.commands.drivetrain.TurnOnHeading;

import frc.robot.commands.intake.DeployIntake;
import frc.robot.commands.intake.RunIntakeCommand;
import frc.robot.commands.intake.SetIntakeStatusCommand;

import frc.robot.commands.shooter.ShootBurstCommand;
import frc.robot.commands.shooter.WarmupCommand;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

/** A theoretical auton that would score 8 Power Cells */
public class EightBallAuto extends SequentialCommandGroup {
    /** A theoretical auton that would score 8 Power Cells
     * @param drivetrain a Drivetrain subsystem
     * @param intake an Intake subsystem
     * @param shooter a Shooter subsystem
    */
    public EightBallAuto(Drivetrain drivetrain, Intake intake, Shooter shooter) {
        super(
            new ResetGyroCommand(drivetrain),
            new SetIntakeStatusCommand(intake, false),
            new DeployIntake(intake, 0.25),
            new WarmupCommand(shooter, null, 90, true, drivetrain),
            new ShootBurstCommand(shooter, intake, null, 90, 3.5, true, drivetrain), //changed for testing
            new TurnOnHeading(drivetrain, -157, 1.75), // flip
            new RunIntakeCommand(intake, -1), // suck balls
            new DriveOnHeading(drivetrain, -157, 0.5, 0.75),
            new DriveOnHeading(drivetrain, -157, 0.35, 4.5), // drive back
            new WarmupCommand(shooter, null, 0, true, drivetrain), // warmup
            new TurnOnHeading(drivetrain, -5, 2),
            new ShootBurstCommand(shooter, intake, null, 0, 5, true, drivetrain) //shoot 5 balls
            );
    }
}