
/*----------------------------------------------------------------------------*/

/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */

/* Open Source Software - may be modified and shared by FRC teams. The code   */

/* must be accompanied by the FIRST BSD license file in the root directory of */

/* the project.                                                               */

/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;;

/////HOLD UP I AIN"T DONE WITH EDITING PLEASE DO NOT WORRY!

/**

 * An example command that uses an example subsystem.

 */

public class ClimbUpCommand extends CommandBase {
      private static final String Angle = null;
  DigitalInput forwardLimitSwitch, reverseLimitSwitch;

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  /**

   * Creates a new ExampleCommand.

   *

   
   */

  public ClimbUpCommand(double angleadjust, double angle) { //CHANGE THIS LATER!

    DigitalInput forwardLimitSwitch = new DigitalInput(1);

    double angleAdjust = angleadjust; //FIX LATER

    double Angle = angle;

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

    Object angleAdjust;
    Object isLinedUp = angleAdjust.shooterAngle(Angle);

    Object output;
    if (forwardLimitSwitch.get()) // If the forward limit switch is pressed, we want to keep the values between -1
                                  // and 0
            output = Math.min((int) output, 0);

        else if(reverseLimitSwitch.get()) // If the reversed limit switch is pressed, we want to keep the values between 0 and 1
            output = Math.max((int) output, 0);
      //I ADDED THE BOOLEAN STATEMENT FOR THE LIMIT SWITCH. 
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