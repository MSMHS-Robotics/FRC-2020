package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;;

/** A command to stop the raising of the climber */
public class StopRaiseCommand extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private Climber climber;

  /** Constructor
   * @param climber a climber subsystem
   */
  public StopRaiseCommand(Climber climber) {
    this.climber = climber;
    addRequirements(climber);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    climber.stopRaise();
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}