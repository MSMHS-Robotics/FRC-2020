package frc.robot.commands.drivetrain;

import frc.robot.subsystems.Lights;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AlertHumanPlayerCommand extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final Lights blinkin;

  public AlertHumanPlayerCommand(Lights light) {
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
