

/*----------------------------------------------------------------------------*/

/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */

/* Open Source Software - may be modified and shared by FRC teams. The code   */

/* must be accompanied by the FIRST BSD license file in the root directory of */

/* the project.                                                               */

/*----------------------------------------------------------------------------*/



package frc.robot.commands;



import frc.robot.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;

/////HOLD UP I AIN"T DONE WITH EDITING PLEASE DO NOT WORRY!

/**

 * An example command that uses an example subsystem.

 */

public class StopClimber extends CommandBase {

  /**

   * Creates a new ExampleCommand.

   *

   
   */

  public StopClimber {

    

    // Use addRequirements() here to declare subsystem dependencies.


  }



  // Called when the command is initially scheduled.

  @Override
  public void initialize() {

  }



  // Called every time the scheduler runs while the command is scheduled.

  @Override
  public void execute() {

      //add execute command here

      Climber.set(0);
      //need more to add
  

  }



  // Called once the command ends or is interrupted.

  @Override
public void end {

  }



  // Returns true when the command should end.

  @Override

  public boolean isFinished() {
      Climber.set(0);

  }

}