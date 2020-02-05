
/*----------------------------------------------------------------------------*/

/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */

/* Open Source Software - may be modified and shared by FRC teams. The code   */

/* must be accompanied by the FIRST BSD license file in the root directory of */

/* the project.                                                               */

/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;;

/////HOLD UP I AIN"T DONE WITH EDITING PLEASE DO NOT WORRY!

/**

 * An example command that uses an example subsystem.

 */

public class RaiseClimber extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Climber climber;
  /**

   * Creates a new ExampleCommand.

   *

   
   */

  public RaiseClimber(Climber climber_) {
    climber = climber_;
    // Use addRequirements() here to declare subsystem dependencies.

    //addRequirements(angleadjust);

  }



  // Called when the command is initially scheduled.

  @Override

  public void initialize() {

  }



  // Called every time the scheduler runs while the command is scheduled.

  @Override

  public void execute() {
    climber.raiseClimber();
  }



  // Called once the command ends or is interrupted.

  @Override

  public void end(boolean interrupted) {
    climber.stop();
  }



  // Returns true when the command should end.

  @Override

  public boolean isFinished() {
    return false;
  }

}