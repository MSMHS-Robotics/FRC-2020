package frc.robot.commands.intake;

import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * A command to set the status of the intake, for use in auton
 */
public class SetIntakeStatusCommand extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

  private Intake intake;
  private boolean status;
  
  /**
   * A command to set the status of the intake, for use in auton
   * @param intake an intake subsystem
   * @param status a boolean of whether the intake is raised or not
  */
  public SetIntakeStatusCommand(Intake intake,boolean status) {
    this.intake = intake;
    this.status = status;
    addRequirements(intake);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intake.setIntakeStatus(status);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
