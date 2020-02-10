/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivetrain;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Lights;
//import edu.wpi.first.networktables.NetworkTable;
//import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Aligns us to the target. Uses drivetrain subsystem
 */
public class AlignToTargetCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain drivetrain;
  private final Lights blinkin;

  /**
   * Creates a new AlignToTargetCommand.
   *
   * @param drivetrain The subsystem used by this command.
   */
  public AlignToTargetCommand(Drivetrain drivetrain_, Lights x) {
    drivetrain = drivetrain_;
    blinkin = x;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.visionPIDReset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(drivetrain.getVisionType()) { //true is snipa, false is normal
      drivetrain.visionAlignSnipa();
      if(drivetrain.isVisionAligned()) {
        blinkin.setGreen();
      }
      else {
        blinkin.setRedLarson();
      }
    }
    else {
      drivetrain.visionAlign();
      if(drivetrain.isVisionAligned()) {
        blinkin.setGreen();
      }
      else {
        blinkin.setRedLarson();
      }
    }
    //SmartDashboard.putBool(drivetrain.isVisionAligned());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    blinkin.setRedLarson();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
