package frc.robot.commands.intake;

import frc.robot.commands.RocketTimedCommand;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** A command to retract the intake */
public class RetractIntakeCommand extends RocketTimedCommand {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private Intake intake;
  private double timeout;

  /** A command to retract the intake
   * @param intake an intake subsystem
   * @param timeout timeout for the command
   */
  public RetractIntakeCommand(Intake intake, double timeout) {
    this.intake = intake;
    this.timeout = timeout;
    addRequirements(intake);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intake.retractIntake();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.stopRaising();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return super.isTimedOut();
  }
}