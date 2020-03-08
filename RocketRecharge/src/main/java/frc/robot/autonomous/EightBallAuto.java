package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.drivetrain.DriveOnHeading;
import frc.robot.commands.drivetrain.ResetGyroCommand;
import frc.robot.commands.intake.DeployIntake;
import frc.robot.commands.intake.RunIntakeCommand;
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
            new DeployIntake(intake, 1),
            new WaitCommand(1),
            new WarmupCommand(shooter, null, 90, true, drivetrain),
            new WaitCommand(1),
            new ShootBurstCommand(shooter, intake, null, 90, 5, true, drivetrain), //changed for testing
            new DriveOnHeading(drivetrain, 180, -.3, 5), //flip
            new RunIntakeCommand(intake, 1), //suck balls
            new DriveOnHeading(drivetrain, 0, 0.5, 5), //drive back
            new DriveOnHeading(drivetrain, 180, 0.3, 5), //flip again
            new WarmupCommand(shooter, null, 90, true, drivetrain), //warmup
            new WaitCommand(1),
            new ShootBurstCommand(shooter, intake, null, 90, 5, true, drivetrain) //shoot 5 balls
            );
    }
}