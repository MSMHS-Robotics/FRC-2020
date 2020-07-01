package frc.robot.commands.intake;

import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * A command to run the indexer
 */
public class RunIndexerCommand extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

  private Intake intake;
  private double power;

  /**
   * A command to run the indexer.
   * @param intake an intake subsystem
   * @param power the speed at which to run the indexer
  */
  public RunIndexerCommand(Intake intake, double power) {
    this.intake = intake;
    this.power = power;
    
    addRequirements(intake);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intake.runIndexer(power);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}