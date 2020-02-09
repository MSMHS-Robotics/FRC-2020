/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivetrain;

import frc.robot.subsystems.Lights;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Aligns us to the target. Uses drivetrain subsystem
 */
public class SetRedCommand extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final Lights blinkin;

  /**
   * Creates a new AlignToTargetCommand.
   *
   * @param drivetrain The subsystem used by this command.
   */
  public SetRedCommand(Lights light) {
    blinkin = light;
    //addRequirements(blinkin);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    blinkin.setRed();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
