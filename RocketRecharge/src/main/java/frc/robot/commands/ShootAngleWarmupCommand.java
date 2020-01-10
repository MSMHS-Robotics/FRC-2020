/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class ShootAngleWarmupCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter angleAdjust;
  private double Angle;
  private boolean isLinedUp;
  /**
   * Creates a new ExampleCommand.
   *
   * @param angleadjust The subsystem used by this command.
   */
  public ShootAngleWarmupCommand(Shooter angleadjust,double angle) {
    angleAdjust = angleadjust;
    Angle = angle;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(angleadjust);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    isLinedUp = angleAdjust.shooterAngle(Angle);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isLinedUp;
  }
}
