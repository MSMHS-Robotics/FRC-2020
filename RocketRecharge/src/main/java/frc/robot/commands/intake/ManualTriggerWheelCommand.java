package frc.robot.commands.intake;

import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * A command to run the trigger wheel manually
 */
public class ManualTriggerWheelCommand extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

  private Intake intake;
  private double power;
  
  /**
   * A command to run the trigger wheel manually
   * @param intake an intake subsystem
   * @param power the speed at which to run the trigger wheel
  */
  public ManualTriggerWheelCommand(Intake intake, double power) {
    this.intake = intake;
    this.power = power;

    addRequirements(intake);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      intake.runTrigger(x);
  }
  
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.runTrigger(0);
  }

  /** Never ends? Huh. Should probs check that out*/
  @Override
  public boolean isFinished() {
    return false;
  }
}
