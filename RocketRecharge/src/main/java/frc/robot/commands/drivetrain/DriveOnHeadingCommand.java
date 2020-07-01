package frc.robot.commands.drivetrain;

import frc.robot.commands.RocketTimedCommand;
import frc.robot.subsystems.Drivetrain;

/**
 * A command to drive on a heading, for use in auton
 */
public class DriveOnHeadingCommand extends RocketTimedCommand {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private Drivetrain drivetrain;
  private double angle;
  private double power;
  private boolean onHeading;
  private double timeout;

  /**
   * Constructor
   *
   * @param drivetrain a drivetrain subsystem
   * @param angle the angle to drive on
   * @param power how fast to drive
   * @param timeout how long
   */
  public DriveOnHeadingCommand(Drivetrain drivetrain, double angle, double power, double timeout) {
    this.drivetrain = drivetrain;
    this.angle = angle;
    this.power = power;
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
    onHeading = drivetrain.driveOnHeading(power, angle);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return onHeading && super.isTimedOut();
  }
}