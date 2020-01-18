/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class TurnOnHeading extends RocketTimedCommand {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final Drivetrain driveheading;
  private double angle;
  private boolean onHeading;
  private double timeout;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public TurnOnHeading(Drivetrain headingdrive, double angle, double timeout) {
    driveheading = headingdrive;
   this.angle = angle;
   this.timeout = timeout; 
   
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(headingdrive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    super.setTimeout(timeout);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    onHeading = driveheading.driveOnHeading(0, angle);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return onHeading || super.isTimedOut();
  }
}
