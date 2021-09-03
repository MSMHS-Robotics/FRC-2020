package frc.robot.commands.intake;

import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class FeedCommand extends CommandBase {
  private Intake intake;
  private double x;

  public FeedCommand(Intake intake, double power) {
    this.intake = intake;
    x = power;
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intake.feed(x);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.feed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}