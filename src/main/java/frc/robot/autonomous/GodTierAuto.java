package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.ResetGyroCommand;
import frc.robot.commands.drivetrain.SetDrive;
import frc.robot.commands.drivetrain.TurnOnHeading;
import frc.robot.commands.intake.RunIntakeCommand;
//import frc.robot.commands.shooter.ShootAngleWarmupCommand;
//import frc.robot.commands.drivetrain.TurnOnHeading;
import frc.robot.commands.shooter.ShootCommand;
import frc.robot.commands.shooter.WarmupCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class GodTierAuto extends SequentialCommandGroup {
    public GodTierAuto(Drivetrain drivetrain, Intake intake, Shooter shooter) {
        super(
            //start with intake facing away from goal
            new ResetGyroCommand(drivetrain),
            new TurnOnHeading(drivetrain, 90, 1.5),//turn right 
            new SetDrive(drivetrain, 90, .4, 96, 5),//drive toward opponent trench
            new SetDrive(drivetrain, 90, 0, 0, .1),//stop
            new TurnOnHeading(drivetrain, 0, 2),//turn torward trench
            new RunIntakeCommand(intake, .5),//start intake
            new SetDrive(drivetrain, 0, .4, 36, 2),//drive to steal power cells
            new SetDrive(drivetrain, 0, 0, 0, .1),//stop
            new RunIntakeCommand(intake, 0),//stop intake
            new SetDrive(drivetrain, 0, -.4, 36, 2),//drive out of trench
            new SetDrive(drivetrain, 0, 0, 0, .1),//stop
            new TurnOnHeading(drivetrain, -90, 1.5),//turn 90 degrees left
            new WarmupCommand(shooter, null, 90, true, drivetrain),//start warmup
            new SetDrive(drivetrain, -90, .5, 36, 2),//drive towards goal
            new SetDrive(drivetrain, -90, 0, 0, .1),//stop
            new TurnOnHeading(drivetrain, -180, 2),//turn to face goal
            new ShootCommand(shooter, intake, null, 90, 5, true, drivetrain),//shoot 5 power cells
            new TurnOnHeading(drivetrain, -90, 2),//turn right to -90
            new SetDrive(drivetrain, -90, .4, 60, 5),//drive toward alliance trench
            new SetDrive(drivetrain, -90, 0, 0, .1),//stop
            new TurnOnHeading(drivetrain, 0, 1.5),//turn to face trench
            new RunIntakeCommand(intake, .5),//run intake
            new SetDrive(drivetrain, 0, .4, 144, 10),//drive to pick up 5 power cells in trench
            new SetDrive(drivetrain, 0, 0, 0, .1),//stop
            new RunIntakeCommand(intake, 0),//stop intake
            new WarmupCommand(shooter, null, 0, true, drivetrain),//warmup
            new SetDrive(drivetrain, 0, -.4, 144, 10),//drive backwards toward goal
            new SetDrive(drivetrain, 0, 0, 0, .1),//stop
            new TurnOnHeading(drivetrain, 135, 1.5),//turn to face goal
            new ShootCommand(shooter, intake, null, 0, 5, true, drivetrain)//shoot 5 remaining power cells
        );
    }
}
