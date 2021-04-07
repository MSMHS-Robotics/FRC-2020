package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.robot.commands.drivetrain.DriveOnHeadingCommand;
import frc.robot.commands.drivetrain.ResetGyroCommand;
import frc.robot.commands.drivetrain.TurnOnHeadingCommand;

import frc.robot.commands.intake.DeployIntakeCommand;
import frc.robot.commands.intake.RunIntakeCommand;
import frc.robot.commands.intake.SetIntakeStatusCommand;

import frc.robot.commands.shooter.ShootBurstCommand;
import frc.robot.commands.shooter.WarmupCommand;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

/** A theoretical auton that would score 8 Power Cells */
public class EightBallAuto extends SequentialCommandGroup {
    /** A theoretical auton that would score 8 Power Cells
     * @param drivetrain a Drivetrain subsystem
     * @param intake an Intake subsystem
     * @param shooter a Shooter subsystem
    */
    public EightBallAuto(Drivetrain drivetrain, Intake intake, Shooter shooter, Limelight limelight) {
        super(
            new ResetGyroCommand(drivetrain),
            new SetIntakeStatusCommand(intake, false),
            new DeployIntakeCommand(intake, 0.25),
            new WarmupCommand(shooter, drivetrain, null, 90, true),
            new ShootBurstCommand(shooter, intake, drivetrain, limelight, null, 90, 3.5, true), //changed for testing
            new TurnOnHeadingCommand(drivetrain, -157, 1.75), // flip
            new RunIntakeCommand(intake, -1), // suck balls
            new DriveOnHeadingCommand(drivetrain, -157, 0.5, 0.75),
            new DriveOnHeadingCommand(drivetrain, -157, 0.35, 4.5), // drive back
            new WarmupCommand(shooter, drivetrain, null, 0, true), // warmup
            new TurnOnHeadingCommand(drivetrain, -5, 2),
            new ShootBurstCommand(shooter, intake, drivetrain, limelight, null, 0, 5, true) //shoot 5 balls
            );
    }
}