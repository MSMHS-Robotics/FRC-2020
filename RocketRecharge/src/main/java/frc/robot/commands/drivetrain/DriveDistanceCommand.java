package frc.robot.commands.drivetrain;

import frc.robot.commands.RocketTimedCommand;
import frc.robot.subsystems.Drivetrain;

/**
 * A command to drive a distance, for use in auton
 */
public class DriveDistanceCommand extends RocketTimedCommand {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  
  private Drivetrain drivetrain;
  private double distance;
  private double angle;
  private boolean atDistance;
  private double timeout;

  /**
   * Constructor
   * @param drivetrain a drivetrain subsystem
   * @param distance how far to drive in inches
   * @param angle the angle to drive on/to
   * @param timeout timeout of the command
   */
  public DriveDistanceCommand(Drivetrain drivetrain, double distance, double angle,double timeout) {
    this.drivetrain= drivetrain;
    this.distance = distance;
    this.angle = angle;
    this.timeout = timeout;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.encoderReset();
    drivetrain.drivingPIDReset();
    drivetrain.headingPIDReset();
    super.setTimeout(timeout);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    atDistance = drivetrain.driveDistance(distance, angle);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return atDistance || super.isTimedOut();
  }
}
