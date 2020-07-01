package frc.robot.commands.drivetrain;

import frc.robot.subsystems.Lights;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** A command to alert the human player to some event, using the LEDs */
public class AlertHumanPlayerCommand extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  
  private Lights blinkin;

  /** A command to alert the human player to some event, using the LEDs
   * @param lights a Lights subsystem
   */
  public AlertHumanPlayerCommand(Lights lights) {
    blinkin = light;
    addRequirements(light);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    blinkin.setRainbow();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
