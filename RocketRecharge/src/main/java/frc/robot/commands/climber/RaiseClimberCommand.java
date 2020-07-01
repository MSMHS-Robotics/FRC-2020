package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

/** A command to raise the climber in preparation for climbing */
public class RaiseClimberCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Climber climber;
  
  /** Constructor
   * @param climber a climber subsystem
   */
  public RaiseClimberCommand(Climber climber) {
    this.climber = climber;
    addRequirements(climber);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    climber.raiseClimber();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}