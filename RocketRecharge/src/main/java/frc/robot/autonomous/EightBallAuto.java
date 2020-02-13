package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.SetDrive;
import frc.robot.commands.drivetrain.TurnOnHeading;
import frc.robot.commands.intake.RunIntakeCommand;
import frc.robot.commands.shooter.ShootCommand;
import frc.robot.commands.shooter.WarmupCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class EightBallAuto extends SequentialCommandGroup{
    public EightBallAuto(Drivetrain drivetrain, Intake intake, Shooter shooter){
        super(
            new ShootCommand(shooter, intake, null, 90, 5, true),//launches preloads
            new TurnOnHeading(drivetrain, 115, 2), //turn 135 degrees
            new SetDrive(drivetrain, 115, -0.4, 75, 5),// drive at 115 degrees for 75 inches
            new SetDrive(drivetrain, 115, 0, 0, 0.1),//stop
            new TurnOnHeading(drivetrain, 180, 1.5),//turn at 180 degrees
            new RunIntakeCommand(intake, .5),//run intake to pick up power cells
            new SetDrive(drivetrain, 180, -0.4, 120, 10), //drive through trench run
            new RunIntakeCommand(intake, 0),//stop intake
            new WarmupCommand(shooter, null, 0, true),//start warmup
            new SetDrive(drivetrain, 180, 0.4, 119, 5),//drive back
            new TurnOnHeading(drivetrain, -115, 1.5),//turn 115 degrees right
            new ShootCommand(shooter, intake, null, 0, 5, true) //shot power cells we have
            //this is also rough but it'll work :D
            );
    }
}

