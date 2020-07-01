package frc.robot.commands.intake;

import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * A command to run the intake.
 */
public class RunIntakeCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private Intake intake;
  private double power;
  
  /**
   * A command to run the intake.
   * @param intake an intake subsystem
   * @param power the speed at which to intake
  */
  public RunIntakeCommand(Intake intake, double power) {
    this.intake = intake;
    this.power = power;
    addRequirements(intake);
  }
  
  @Override
  public void execute() {
    intake.runIntake(power);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}