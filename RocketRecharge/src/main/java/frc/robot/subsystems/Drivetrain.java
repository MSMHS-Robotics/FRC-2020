/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;

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
   PIDController pid = new PIDController(1, 0, 0);
   private CANSparkMax left1 = new CANSparkMax(1, MotorType.kBrushless);
   private CANSparkMax left2 = new CANSparkMax(2, MotorType.kBrushless);
   private CANSparkMax left3 = new CANSparkMax(3, MotorType.kBrushless);
   private CANSparkMax right1 = new CANSparkMax(4, MotorType.kBrushless);
   private CANSparkMax right2 = new CANSparkMax(5, MotorType.kBrushless);
   private CANSparkMax right3 = new CANSparkMax(6, MotorType.kBrushless);
   private double leftPow = 0;
   private double rightPow = 0;
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
      /*
  @Override //why no work
  public void periodic() {
    // This method will be called once per scheduler run
    drivetrain.tankDrive(leftPow, rightPow);
  }
*/

  public void tankDrive(double leftStick, double rightStick) {
    //don't mess with this, drivetrain is a member of a different class, this function is not recursive
    leftPow = Math.pow(-leftStick, 3);
    rightPow = Math.pow(-rightStick, 3);
    drivetrain.tankDrive(leftPow, rightPow);
  }

  public void visionPIDReset() {
    pid.reset();
  }

  public void visionAlign() {
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tx = table.getEntry("tx");

    //start loop
    double x_offset = tx.getDouble(0);
    /*NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");
    */
    
    //read values periodically
    double x = tx.getDouble(0.0);
    drivetrain.arcadeDrive(0.5, MathUtil.clamp(pid.calculate(x_offset), -0.5, 0.5));
    //double y = ty.getDouble(0.0);
    //double area = ta.getDouble(0.0);

    //end loop
  }
}
