package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.drivetrain.DriveOnHeading;
import frc.robot.commands.drivetrain.ResetGyroCommand;
import frc.robot.commands.drivetrain.TurnOnHeading;
import frc.robot.commands.intake.DeployIntake;
import frc.robot.commands.intake.RunIntakeCommand;
import frc.robot.commands.intake.SetIntakeStatus;
//import frc.robot.commands.drivetrain.SetDrive;
import frc.robot.commands.shooter.ShootBurstCommand;
//import frc.robot.commands.shooter.ShootAngleWarmupCommand;
//import frc.robot.commands.drivetrain.TurnOnHeading;
//import frc.robot.commands.shooter.ShootCommand;
import frc.robot.commands.shooter.WarmupCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class EightBallAuto extends SequentialCommandGroup {
    public EightBallAuto(Drivetrain drivetrain, Intake intake, Shooter shooter) {
        super(
            new ResetGyroCommand(drivetrain),
            new SetIntakeStatus(intake, false),
            new DeployIntake(intake, 0.25),
            new WarmupCommand(shooter, null, 90, true, drivetrain),
            new ShootBurstCommand(shooter, intake, null, 90, 3.5, true, drivetrain), //changed for testing
            new TurnOnHeading(drivetrain, -157, 1.75), //flip
            new RunIntakeCommand(intake, -1), //suck balls
            new DriveOnHeading(drivetrain, -157, .5, .75),
            new DriveOnHeading(drivetrain, -157, 0.35, 4.5), //drive back
            new WarmupCommand(shooter, null, 0, true, drivetrain), //warmup
           // new DriveOnHeading(drivetrain, -157, -0.4, 1), //flip again
           // new DriveOnHeading(drivetrain,-157,0,0.01),
            new TurnOnHeading(drivetrain, -5, 2),
            new ShootBurstCommand(shooter, intake, null, 0, 5, true, drivetrain) //shoot 5 balls
            );
    }
}