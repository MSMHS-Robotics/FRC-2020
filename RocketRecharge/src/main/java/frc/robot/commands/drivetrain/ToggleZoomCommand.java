package frc.robot.commands.drivetrain;

import frc.robot.subsystems.Limelight;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Toggles limelight hardware zoom
 */
public class ToggleZoomCommand extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private Limelight limelight;

  /**
   * Constructor
   * @param limelight a limelight subsystem
   */
  public ToggleZoomCommand(Limelight limelight) {
    this.limelight = limelight;
    addRequirements(limelight);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    limelight.toggleZoom();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
