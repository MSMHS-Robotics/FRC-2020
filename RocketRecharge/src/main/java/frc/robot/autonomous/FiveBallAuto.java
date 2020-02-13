package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.ResetGyroCommand;
import frc.robot.commands.drivetrain.SetDrive;
import frc.robot.commands.drivetrain.TurnOnHeading;
import frc.robot.commands.intake.FeedToShooterCommand;
import frc.robot.commands.intake.RunIntakeCommand;
import frc.robot.commands.shooter.ShootCommand;
import frc.robot.commands.shooter.WarmupCommand;
//import frc.robot.commands.drivetrain.TurnOnHeading;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class FiveBallAuto extends SequentialCommandGroup {
    public FiveBallAuto(Drivetrain drivetrain, Intake intake, Shooter shooter) {
        super(
            new ResetGyroCommand(drivetrain),
            new SetDrive(drivetrain, 0, 0.3, 24, 4), //drive us 5 seconds
            new RunIntakeCommand(intake, .5), //run intake
            new SetDrive(drivetrain, 0, 0, 0, 2), //stop us
            new RunIntakeCommand(intake, 0),//stop intake
            new SetDrive(drivetrain, 0, -.3, 13, 4), //leave trench
            new WarmupCommand(shooter, null, 90, true),//start warmup
            new SetDrive(drivetrain, 0, 0, 0, 2), //stop driving
            new TurnOnHeading(drivetrain, -90, 3), //turn -90 degrees
            new SetDrive(drivetrain, -90, .5, 10, 6), //grive forward
            new TurnOnHeading(drivetrain, -135, 2), //turn -135 degrees
            new FeedToShooterCommand(intake), //feed power cells
            new ShootCommand(shooter, intake, null, 90, 5, true) //shoot
            //rough attempt but okay :D
            );
    }
}

