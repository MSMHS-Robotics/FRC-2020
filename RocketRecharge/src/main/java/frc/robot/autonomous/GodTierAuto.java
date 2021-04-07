package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.ResetGyroCommand;
import frc.robot.commands.drivetrain.SetDriveCommand;

import frc.robot.commands.drivetrain.TurnOnHeadingCommand;
import frc.robot.commands.intake.RunIntakeCommand;

import frc.robot.commands.shooter.ShootCommand;
import frc.robot.commands.shooter.WarmupCommand;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

/** A theoretical auto that would score 10 Power Cells */
public class GodTierAuto extends SequentialCommandGroup {
    /** A theoretical auto that would score 10 Power Cells
     * @param drivetrain a Drivetrain subsystem
     * @param intake an Intake subsystem
     * @param shooter a Shooter subsystem
    */
    public GodTierAuto(Drivetrain drivetrain, Intake intake, Shooter shooter, Limelight limelight) {
        super(
            // start with intake facing away from goal
            new ResetGyroCommand(drivetrain),
            new TurnOnHeadingCommand(drivetrain, 90, 10.5), // turn right 
            new SetDriveCommand(drivetrain, 90, 0.4, 96, 5), // drive toward opponent trench
            new SetDriveCommand(drivetrain, 90, 0, 0, 0.1), // stop
            new TurnOnHeadingCommand(drivetrain, 0, 2), // turn torward trench
            new RunIntakeCommand(intake, 0.5), // start intake
            new SetDriveCommand(drivetrain, 0, 0.4, 36, 2), // drive to steal power cells
            new SetDriveCommand(drivetrain, 0, 0, 0, 0.1), // stop
            new RunIntakeCommand(intake, 0), // stop intake
            new SetDriveCommand(drivetrain, 0, -0.4, 36, 2), // drive out of trench
            new SetDriveCommand(drivetrain, 0, 0, 0, 0.1), // stop
            new TurnOnHeadingCommand(drivetrain, -90, 10.5), // turn 90 degrees left
            new WarmupCommand(shooter, drivetrain, null, 90, true), // start warmup
            new SetDriveCommand(drivetrain, -90, 0.5, 36, 2), // drive towards goal
            new SetDriveCommand(drivetrain, -90, 0, 0, 0.1), // stop
            new TurnOnHeadingCommand(drivetrain, -180, 2), // turn to face goal
            new ShootCommand(shooter, intake, drivetrain, limelight, null, 90, 5, true), // shoot 5 power cells
            new TurnOnHeadingCommand(drivetrain, -90, 2), // turn right to -90
            new SetDriveCommand(drivetrain, -90, 0.4, 60, 5), // drive toward alliance trench
            new SetDriveCommand(drivetrain, -90, 0, 0, 0.1), // stop
            new TurnOnHeadingCommand(drivetrain, 0, 10.5), // turn to face trench
            new RunIntakeCommand(intake, 0.5), // run intake
            new SetDriveCommand(drivetrain, 0, 0.4, 144, 10), // drive to pick up 5 power cells in trench
            new SetDriveCommand(drivetrain, 0, 0, 0, 0.1), // stop
            new RunIntakeCommand(intake, 0), // stop intake
            new WarmupCommand(shooter, drivetrain, null, 0, true), // warmup
            new SetDriveCommand(drivetrain, 0, -0.4, 144, 10), // drive backwards toward goal
            new SetDriveCommand(drivetrain, 0, 0, 0, 0.1), // stop
            new TurnOnHeadingCommand(drivetrain, 135, 10.5), // turn to face goal
            new ShootCommand(shooter, intake, drivetrain, limelight, null, 0, 5, true) // shoot 5 remaining power cells
        );
    }
}
