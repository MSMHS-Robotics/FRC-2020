/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

//import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   
   */

   private Spark left1 = new Spark(0);
   private Spark left2 = new Spark(1);
   private Spark left3 = new Spark( 2);
   private Spark right1 = new Spark(3);
   private Spark right2 = new Spark(4);
   private Spark right3 = new Spark(5);
   SpeedControllerGroup leftSide = new SpeedControllerGroup(left1, left2, left3);
   SpeedControllerGroup rightSide = new SpeedControllerGroup(right1, right2, right3);
   private DifferentialDrive drivetrain = new DifferentialDrive(leftSide, rightSide);
   public double speed;
   //private double left;
   
   public Drivetrain() {

     //Spark left1 = new Spark();
     /*PIDOutput driveOutput = new PIDOutput(){
      @Override
      public void pidWrite(double output) {
      */}

  /*@Override
  public void teleopPeriodic() {
    // This method will be called once per scheduler run
    drivetrain.tankDrive(, rightSpeed);
  }*/
  public void tankdrive(double left, double right ) {
    drivetrain.tankDrive(Math.pow(-left, 3),Math.pow(-right, 3));
  }
}
