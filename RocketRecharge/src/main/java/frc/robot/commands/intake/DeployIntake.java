package frc.robot.commands.intake;

import frc.robot.commands.RocketTimedCommand;
import frc.robot.subsystems.Intake;

public class DeployIntake extends RocketTimedCommand {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private Intake intake;
  private double timeout;

  public DeployIntake(Intake intake, double timeout) {
    this.intake = intake;
    this.timeout = timeout;
    addRequirements(intake);
  }

  @Override
  public void initialize() {
    super.setTimeout(timeout);
  }

  @Override
  public void execute() {
    intake.extendIntake();
    intake.runIntake(-1);
  }

  @Override
  public void end(boolean interrupted) {
    intake.stopRaising();
  }

  @Override
  public boolean isFinished() {
    return super.isTimedOut();
  }
}