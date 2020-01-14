/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

//import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//CANSparkMax

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Drivetrain extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   
   */

   private CANSparkMax left1 = new CANSparkMax(1, MotorType.kBrushless);
   private CANSparkMax left2 = new CANSparkMax(2, MotorType.kBrushless);
   private CANSparkMax left3 = new CANSparkMax(3, MotorType.kBrushless);
   private CANSparkMax right1 = new CANSparkMax(4, MotorType.kBrushless);
   private CANSparkMax right2 = new CANSparkMax(5, MotorType.kBrushless);
   private CANSparkMax right3 = new CANSparkMax(6, MotorType.kBrushless);
   SpeedControllerGroup leftSide = new SpeedControllerGroup(left1, left2, left3);
   SpeedControllerGroup rightSide = new SpeedControllerGroup(right1, right2, right3);
   private DifferentialDrive drivetrain = new DifferentialDrive(leftSide, rightSide);
   public double speed;
   //private double left;
   
   public Drivetrain() {

     //CANSparkMax left1 = new CANSparkMax();
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
