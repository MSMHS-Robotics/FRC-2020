package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

/** A command to climb up */
public class ClimbUpCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Climber climber;

  /** Constructor
   * @param climber a climber subsystem
   */
  public ClimbUpCommand(Climber climber) {
    this.climber = climber;
    addRequirements(climber);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    climber.climbUp();
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}