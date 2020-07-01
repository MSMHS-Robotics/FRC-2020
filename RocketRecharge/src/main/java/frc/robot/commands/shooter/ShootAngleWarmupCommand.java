package frc.robot.commands.shooter;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * A command to warmup the shooter
 */
public class ShootAngleWarmupCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  private Shooter shooter;
  private double angle;
  private boolean isLinedUp;
  
  /**
   * A command to warmup the shooter
   *
   * @param shooter a Shooter subsystem
   * @param angle the angle to warm-up to
   */
  public ShootAngleWarmupCommand(Shooter shooter, double angle) {
    this.shooter= shooter;
    this.angle = angle;
    addRequirements(shooter);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    isLinedUp = shooter.shooterAngle(Angle);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isLinedUp;
  }
}
