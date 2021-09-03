package frc.robot.commands.drivetrain;

import frc.robot.subsystems.Lights;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetRedHeartbeatCommand extends CommandBase {
  private final Lights blinkin;

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
    return false;
  }
}
