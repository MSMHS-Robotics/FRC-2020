/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

//import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpiutil.math.MathUtil;


//CANSparkMax

//import com.revrobotics.CANPIDController;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
//import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Drivetrain extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   
   */
  CANEncoder encoder;
   AHRS ahrs;
   PIDController headingPID = new PIDController(1, 0, 0);
   PIDController drivingPID = new PIDController(1, 0, 0);
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
     // Sets the error tolerance to 5, and the error derivative tolerance to 10 per second
     headingPID.setTolerance(5, 10);
     headingPID.setIntegratorRange(-0.5, 0.5);
     // Enables continuous input on a range from -180 to 180
     headingPID.enableContinuousInput(-180, 180);

     // Sets the error tolerance to 5, and the error derivative tolerance to 10 per second
     drivingPID.setTolerance(5, 10);
     drivingPID.setIntegratorRange(-0.5, 0.5);
     // Enables continuous input on a range from -180 to 180
     drivingPID.enableContinuousInput(-180, 180);
   }
  
    public void headingPIDReset() {
    headingPID.reset();
  }
    // idk if we need to reset again?
    //public void drivingPIDReset() {
    //drivingPID.reset();
    //}

  public void tankDrive(double leftStick, double rightStick) {
    //don't mess with this, drivetrain is a member of a different class, this function is not recursive
    leftPow = Math.pow(-leftStick, 3);
    rightPow = Math.pow(-rightStick, 3);
    drivetrain.tankDrive(leftPow, rightPow);
  }

  public boolean driveOnHeading(double power,double angle){
    double currentAngle = ahrs.getYaw(); //is this right?
    double turnPower = MathUtil.clamp(headingPID.calculate(currentAngle, angle), -0.5, 0.5);
    drivetrain.arcadeDrive(power,turnPower);
    return headingPID.atSetpoint();
  }

  public boolean driveDistance(double distance, double angle){
    double currentDistance = encoder.getPosition();//is this right?
    double drivePower = MathUtil.clamp(drivingPID.calculate(currentDistance, angle), -0.5, 0.5);
    this.driveOnHeading(drivePower,angle);
    return drivingPID.atSetpoint();
  }
}
