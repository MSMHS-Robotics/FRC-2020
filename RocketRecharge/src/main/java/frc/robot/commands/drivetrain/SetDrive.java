package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.RocketTimedCommand;
import frc.robot.subsystems.Drivetrain;

public class SetDrive extends CommandBase {
  private Drivetrain drivetrain;

  public SetDrive(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;

    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    //Reset everything
    drivetrain.resetGyro();
    drivetrain.resetSpeedPID();
    drivetrain.resetHeadingPID();
    drivetrain.resetEncoders();
  }

  @Override
  public void execute() {
    drivetrain.driveOnHeading();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
