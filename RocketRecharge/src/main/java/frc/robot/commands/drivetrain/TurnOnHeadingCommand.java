package frc.robot.commands.drivetrain;

import frc.robot.commands.RocketTimedCommand;
import frc.robot.subsystems.Drivetrain;

/**
 * Turns the drivetrain, for use in auton
 */
public class TurnOnHeadingCommand extends RocketTimedCommand {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private Drivetrain drivetrain;
  private double angle;
  private boolean onHeading;
  private double timeout;

  /**
   * Constructor
   * @param drivetrain a drivetrain subsystem
   * @param angle the angle to turn to
   * @param timeout timeout for the command
   */
  public TurnOnHeadingCommand(Drivetrain drivetrain, double angle, double timeout) {
   this.drivetrain = drivetrain;
   this.angle = angle;
   this.timeout = timeout; 
   
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    super.setTimeout(timeout);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    onHeading = drivetrain.driveOnHeading(0, angle);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return onHeading || super.isTimedOut();
  }
}
