package frc.robot.commands.lights;

import frc.robot.subsystems.Lights;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * A command to set the color of the lights back to red
 */
public class SetRedHeartbeatCommand extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final Lights blinkin;

  /**
   * A command to set the color of the lights back to red.
   * @param light a Lights subsystem
  */
  public SetRedHeartbeatCommand(Lights light) {
    blinkin = light;
    addRequirements(light);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    blinkin.setRedLarson();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
