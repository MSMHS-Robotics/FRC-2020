/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import frc.robot.Constants;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.DriverStation;
//import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.PIDOutput;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.devices.RocketEncoder;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//CANSparkMax

//import com.revrobotics.CANEncoder;
//import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
//import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Drivetrain extends SubsystemBase {
  //private ShuffleboardTab tab = Shuffleboard.getTab("drivetrain");
  //private NetworkTableEntry testPIDthing = tab.add("testPIDthing", 1).getEntry();
  
  AHRS ahrs;
  double visionPIDconstant1 = Constants.visionPID[0];
  double visionPIDconstant2 = Constants.visionPID[1];
  double visionPIDconstant3 = Constants.visionPID[2];
  
  PIDController visionPID = new PIDController(visionPIDconstant1, visionPIDconstant2, visionPIDconstant3);
  PIDController headingPID = new PIDController(.15, 0, 0);
  PIDController drivingPID = new PIDController(1, 0, 0);
  
  private ShuffleboardTab tab = Shuffleboard.getTab("Drivetrain Tab");
  private NetworkTableEntry visionConstraintMax = tab.addPersistent("Vision Constraint - Max", Constants.visionPIDconstraints[1]).getEntry();
  private NetworkTableEntry visionConstraintMin = tab.addPersistent("Vision Constraint - Min", Constants.visionPIDconstraints[0]).getEntry();
  private NetworkTableEntry headingConstraintMin = tab.addPersistent("Heading Constraint - Min", Constants.headingPIDconstraints[0]).getEntry();
  private NetworkTableEntry headingConstraintMax = tab.addPersistent("Heading Constraint - max", Constants.headingPIDconstraints[1]).getEntry();
  private NetworkTableEntry drivingConstraintMin = tab.addPersistent("Driving Constraint - Min", Constants.drivingPIDconstraints[0]).getEntry();
  private NetworkTableEntry drivingConstraintMax = tab.addPersistent("Driving Constraint - Max", Constants.drivingPIDconstraints[1]).getEntry();
  //private NetworkTableEntry drivingConstraintMax = tab.addPersistent("Driving Constraint - Max", Constants.drivingPIDconstraints[1]).getEntry();

  private final CANSparkMax left1 = new CANSparkMax(1, MotorType.kBrushless);
  private final RocketEncoder encoderLeft1 = new RocketEncoder (left1);
  private final CANSparkMax left2 = new CANSparkMax(2, MotorType.kBrushless);
  private final RocketEncoder  encoderLeft2 = new RocketEncoder(left2);
  private final CANSparkMax left3 = new CANSparkMax(3, MotorType.kBrushless);
  private final RocketEncoder  encoderLeft3 = new RocketEncoder (left3);
  private final CANSparkMax right1 = new CANSparkMax(4, MotorType.kBrushless);
  private final RocketEncoder encoderRight1 = new RocketEncoder (right1);
  private final CANSparkMax right2 = new CANSparkMax(5, MotorType.kBrushless);
  private final RocketEncoder  encoderRight2 = new RocketEncoder (right2);
  private final CANSparkMax right3 = new CANSparkMax(6, MotorType.kBrushless);
  private final RocketEncoder encoderRight3 = new RocketEncoder(right3);
  
  private double leftPow = 0;
  private double rightPow = 0;
  
  SpeedControllerGroup leftSide = new SpeedControllerGroup(left1, left2, left3);
  SpeedControllerGroup rightSide = new SpeedControllerGroup(right1, right2, right3);
  
  private final DifferentialDrive drivetrain = new DifferentialDrive(leftSide, rightSide);
  public double speed;
  private boolean aligned = false;
  // private double left;

  public Drivetrain() {
    // Sets the error tolerance to 5, and the error derivative tolerance to 10 per
    // second
    headingPID.setTolerance(2, 5);
    headingPID.setIntegratorRange(-0.5, 0.5);
    // Enables continuous input on a range from -180 to 180
    headingPID.enableContinuousInput(-180, 180);

    // Sets the error tolerance to 5, and the error derivative tolerance to 10 per
    // second
    drivingPID.setTolerance(2, 5);
    drivingPID.setIntegratorRange(-0.5, 0.5);
    // Enables continuous input on a range from -180 to 180
    //drivingPID.enableContinuousInput(-180, 180);

    encoderLeft1.setPositionConstant(1);//change this later
    encoderLeft2.setPositionConstant(1);//change this later
    encoderLeft3.setPositionConstant(1);//change this later
    encoderRight1.setPositionConstant(1);//change this later
    encoderRight2.setPositionConstant(1);//change this later
    encoderRight3.setPositionConstant(1);//change this later
    
    try {
      /***********************************************************************
       * navX-MXP: - Communication via RoboRIO MXP (SPI, I2C) and USB. - See
       * http://navx-mxp.kauailabs.com/guidance/selecting-an-interface.
       * 
       * navX-Micro: - Communication via I2C (RoboRIO MXP or Onboard) and USB. - See
       * http://navx-micro.kauailabs.com/guidance/selecting-an-interface.
       * 
       * VMX-pi: - Communication via USB. - See
       * https://vmx-pi.kauailabs.com/installation/roborio-installation/
       * 
       * Multiple navX-model devices on a single robot are supported.
       ************************************************************************/
      ahrs = new AHRS(SPI.Port.kMXP);
    } catch (RuntimeException ex) {
      DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
    }
  } 
  //=================================================================================
  @Override
  public void periodic() {
    //ooooooooof get ready
    Constants.visionPIDconstraints[0] = visionConstraintMin.getDouble(Constants.visionPIDconstraints[0]);
    Constants.visionPIDconstraints[1] = visionConstraintMax.getDouble(Constants.visionPIDconstraints[1]);
    Constants.drivingPIDconstraints[0] = drivingConstraintMin.getDouble(Constants.drivingPIDconstraints[0]);
    Constants.drivingPIDconstraints[1] = drivingConstraintMax.getDouble(Constants.drivingPIDconstraints[1]);
    Constants.headingPIDconstraints[0] = headingConstraintMin.getDouble(Constants.headingPIDconstraints[0]);
    Constants.headingPIDconstraints[1] = headingConstraintMax.getDouble(Constants.headingPIDconstraints[1]);

  }
  //==================================================================================
  public void headingPIDReset() {
    headingPID.reset();
  }

  // idk if we need to reset again?
  public void drivingPIDReset() {
    drivingPID.reset();
  }

  public void encoderReset(){
    encoderLeft1.reset();
    encoderLeft2.reset();
    encoderLeft3.reset();
    encoderRight1.reset();
    encoderRight2.reset();
    encoderRight3.reset();
  }
  public void tankDrive(final double leftStick, final double rightStick) {
    // don't mess with this, drivetrain is a member of a different class, this
    // function is not recursive - Daniel's last message to creation
    leftPow = Math.pow(-leftStick, 3);
    rightPow = Math.pow(-rightStick, 3);
    drivetrain.tankDrive(leftPow * 0.5, rightPow * 0.5);
  }

  public void visionPIDReset() {
    visionPID.reset();
  }

  /**
   * Aligns us with the vision target. 
   * This function is called in the AlignToTargetCommand.java. 
   * You need to import NetworkTable and NetworkTableEntry
   */
  public void visionAlign() {
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tv = table.getEntry("tv");
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
    if(tv.getDouble(0) == 1) {
      NetworkTableEntry tx = table.getEntry("tx");
  
      //start loop
      double x_offset = tx.getDouble(0);
      drivetrain.arcadeDrive(0, MathUtil.clamp(-visionPID.calculate(x_offset), Constants.visionPIDconstraints[0], Constants.visionPIDconstraints[1]));
      if(x_offset < 0.1) {
        aligned = true;
      }
      else {
        aligned = false;
      }
    }
    else {
      drivetrain.arcadeDrive(0, 0);
    }
    //end loop
  }

  /**
   * this function returns if we are aligned with the vision target or not
   */
  public boolean isVisionAligned() {
    //might be helpful sometime
    return aligned;
  }

  public double encoderAverage() {
    double average = encoderLeft1.getPosition();
     average += encoderLeft2.getPosition();
     average += encoderLeft3.getPosition();
     average += encoderRight1.getPosition();
     average += encoderRight2.getPosition();
     average += encoderRight3.getPosition();
     average /= 6;
    return average;
  }

  public boolean driveOnHeading(double power, double angle) {
    final double currentAngle = ahrs.getYaw(); // is this right?
    final double turnPower = MathUtil.clamp(headingPID.calculate(currentAngle, angle), -0.25, 0.25);
    drivetrain.arcadeDrive(power, turnPower);
    return headingPID.atSetpoint();
  }

  public boolean driveDistance(double distance,double angle) {
    final double currentDistance = encoderAverage();// is this right?
    final double drivePower = MathUtil.clamp(drivingPID.calculate(currentDistance, angle), -0.5, 0.5);
    final boolean headingAligned = this.driveOnHeading(drivePower, angle);
    return drivingPID.atSetpoint() && headingAligned;
  }

}

