package frc.robot.commands.drivetrain;

import frc.robot.commands.RocketTimedCommand;
import frc.robot.subsystems.Drivetrain;

/**
 * IDK what this is
 */
public class SetDriveCommand extends RocketTimedCommand {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private Drivetrain drivetrain;
  private double angle;
  private double power;
  private double distance;
  private boolean onHeading;
  private double timeout;
  
  /**
   * Constructor
   * @param drivetrain a drivetrain subsystem
   * @param angle the angle to drive to/on
   * @param power speed to drive at
   * @param timeout timeout of the command
   */
  public SetDriveCommand(Drivetrain drivetrain, double angle, double power, double distance, double timeout) {
    this.drivetrain = drivetrain;
    this.angle = angle;
    this.power = power;
    this.timeout = timeout;
    this.distance = distance;

    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    super.setTimeout(timeout);
    drivetrain.encoderReset();
    drivetrain.headingPIDReset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    onHeading = drivetrain.driveOnHeading(power, angle);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (onHeading && Math.abs(drivetrain.encoderAverage()) >= Math.abs(distance)) || super.isTimedOut();
  }
}