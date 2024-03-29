/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
//CANSparkMax
//import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
//import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
//import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
  //private ShuffleboardTab tab = Shuffleboard.getTab("drivetrain");
  //private NetworkTableEntry testPIDthing = tab.add("testPIDthing", 1).getEntry();
  
  AHRS ahrs;
  
  PIDController visionPID = new PIDController(0.019, 0.08, 0.0085);
  PIDController headingPID = new PIDController(.15, 0, 0);
  PIDController drivingPID = new PIDController(1, 0, 0);
  
  private ShuffleboardTab tab = Shuffleboard.getTab("Drivetrain Tab");
  
  private NetworkTableEntry visionConstraintMax = tab.addPersistent("VisionPIDMax", Constants.visionPIDconstraints[1]).getEntry();
  private NetworkTableEntry visionConstraintMin = tab.addPersistent("VisionPIDMin", Constants.visionPIDconstraints[0]).getEntry();
  private NetworkTableEntry headingConstraintMin = tab.addPersistent("HeadingPIDMin", Constants.headingPIDconstraints[0]).getEntry();
  private NetworkTableEntry headingConstraintMax = tab.addPersistent("HeadingPIDmax", Constants.headingPIDconstraints[1]).getEntry();
  private NetworkTableEntry drivingConstraintMin = tab.addPersistent("DrivingPIDMin", Constants.drivingPIDconstraints[0]).getEntry();
  private NetworkTableEntry drivingConstraintMax = tab.addPersistent("DrivingPIDMax", Constants.drivingPIDconstraints[1]).getEntry();
  private NetworkTableEntry visionKp = tab.addPersistent("VisionKp", Constants.visionPID[0]).getEntry();
  private NetworkTableEntry visionKi = tab.addPersistent("VisionKi", Constants.visionPID[1]).getEntry();
  private NetworkTableEntry visionKd = tab.addPersistent("VisionKd", Constants.visionPID[2]).getEntry();
  private NetworkTableEntry visionError = tab.addPersistent("Vision Error", 0).getEntry();
  private NetworkTableEntry drivingKp = tab.addPersistent("DrivingKp", Constants.drivingPID[0]).getEntry();
  private NetworkTableEntry drivingKi = tab.addPersistent("DrivingKi", Constants.drivingPID[1]).getEntry();
  private NetworkTableEntry drivingKd = tab.addPersistent("DrivingKd", Constants.drivingPID[2]).getEntry();
  private NetworkTableEntry headingKp = tab.addPersistent("HeadingKp", Constants.headingPID[0]).getEntry();
  private NetworkTableEntry headingKi = tab.addPersistent("HeadingKi", Constants.headingPID[1]).getEntry();
  private NetworkTableEntry headingKd = tab.addPersistent("HeadingKd", Constants.headingPID[2]).getEntry();
  private NetworkTableEntry hdgTolerance = tab.addPersistent("hdgTolerance", Constants.headingTolerance[0]).getEntry();
  private NetworkTableEntry hdgVTolerance = tab.addPersistent("hdgVTolerance", Constants.headingTolerance[1]).getEntry();
  private NetworkTableEntry vsnTolerance = tab.addPersistent("vsnTolerance", Constants.visionTolerance[0]).getEntry();
  private NetworkTableEntry vsnVTolerance = tab.addPersistent("vsnVTolerance", Constants.visionTolerance[1]).getEntry();
  private NetworkTableEntry drvTolerance = tab.addPersistent("drvTolerance", Constants.drivingTolerance[0]).getEntry();
  private NetworkTableEntry drvVTolerance = tab.addPersistent("drvVTolerance", Constants.drivingTolerance[1]).getEntry();
  private NetworkTableEntry rightTickConstant = tab.addPersistent("RTickConstant", Constants.rightTickConstant).getEntry();
  private NetworkTableEntry leftTickConstant = tab.addPersistent("LTickConstant", Constants.leftTickConstant).getEntry();
  private NetworkTableEntry leftEncoderValue = tab.addPersistent("LeftEncoder", 0).getEntry();
  private NetworkTableEntry rightEncoderValue = tab.addPersistent("RightEncoder", 0).getEntry();
  private NetworkTableEntry encoderaverage = tab.addPersistent("encoderaverage", 0).getEntry();
  private NetworkTableEntry throughBoreRight = tab.addPersistent("Through Bore Right", 0).getEntry();
  private NetworkTableEntry throughBoreLeft = tab.addPersistent("Through Bore Left", 0).getEntry();
  private NetworkTableEntry rTickBoreConstant = tab.addPersistent("RTickBoreConstant", Constants.rTickBoreConstant).getEntry();
  private NetworkTableEntry lTickBoreConstant = tab.addPersistent("LTickBoreConstant", Constants.lTickBoreConstant).getEntry();

  private NetworkTableEntry resetGyroCommandEntry = tab.add("Reset Gyro", false).withWidget(BuiltInWidgets.kToggleButton).getEntry();
  
  private final CANSparkMax left1 = new CANSparkMax(1, MotorType.kBrushless);
  private final CANSparkMax left2 = new CANSparkMax(2, MotorType.kBrushless);
  private final CANSparkMax left3 = new CANSparkMax(3, MotorType.kBrushless);
  private final CANEncoder encoderLeft1 = new CANEncoder(left1);
  private final CANEncoder  encoderLeft2 = new CANEncoder(left2);
  private final CANEncoder  encoderLeft3 = new CANEncoder (left3);

  private final CANSparkMax right1 = new CANSparkMax(4, MotorType.kBrushless);
  private final CANSparkMax right2 = new CANSparkMax(5, MotorType.kBrushless);
  private final CANSparkMax right3 = new CANSparkMax(6, MotorType.kBrushless);
  private final CANEncoder encoderRight1 = new CANEncoder (right1);
  private final CANEncoder  encoderRight2 = new CANEncoder (right2);
  private final CANEncoder encoderRight3 = new CANEncoder(right3);
  
  SpeedControllerGroup leftSide = new SpeedControllerGroup(left1, left2, left3);
  SpeedControllerGroup rightSide = new SpeedControllerGroup(right1, right2, right3);
  
  private Encoder throughboreRight = new Encoder(6, 8);
  private Encoder throughboreLeft = new Encoder(2,3);

  private final DifferentialDrive drivetrain = new DifferentialDrive(leftSide, rightSide);
  private boolean aligned = false;
  private boolean alignZoom = true;

  public Drivetrain() {
    ledsOff();
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
  
    encoderLeft1.setPositionConversionFactor(1);
    encoderLeft2.setPositionConversionFactor(1);
    encoderLeft3.setPositionConversionFactor(1);
    encoderRight1.setPositionConversionFactor(1);
    encoderRight2.setPositionConversionFactor(1);
    encoderRight3.setPositionConversionFactor(1);
    
    //reset for shuffleboard
    encoderLeft1.setPosition(0);
    encoderLeft2.setPosition(0);
    encoderLeft3.setPosition(0);
    encoderRight1.setPosition(0);
    encoderRight2.setPosition(0);
    encoderRight3.setPosition(0);
  
    //through bore encoder
    throughboreRight.reset();
    throughboreLeft.reset();

    ahrs = new AHRS(SPI.Port.kMXP);
  } 

  @Override
  public void periodic() {
    leftEncoderValue.setDouble(leftEncoderAverage());
    rightEncoderValue.setDouble(rightEncoderAverage());
    encoderaverage.setDouble(encoderAverage());
    throughBoreLeft.setDouble(throughboreLeft.getDistance());
    throughBoreRight.setDouble(throughboreRight.getDistance());

    Constants.headingPIDconstraints[0] = headingConstraintMin.getDouble(Constants.headingPIDconstraints[0]);
    Constants.headingPIDconstraints[1] = headingConstraintMax.getDouble(Constants.headingPIDconstraints[1]);

    Constants.visionPIDconstraints[0] = visionConstraintMin.getDouble(Constants.visionPIDconstraints[0]);
    Constants.visionPIDconstraints[1] = visionConstraintMax.getDouble(Constants.visionPIDconstraints[1]);

    Constants.drivingPIDconstraints[0] = drivingConstraintMin.getDouble(Constants.drivingPIDconstraints[0]);
    Constants.drivingPIDconstraints[1] = drivingConstraintMax.getDouble(Constants.drivingPIDconstraints[1]);

    //now for changing the PID values on robot and in Constants.java. this is going to be _very_ long
    double tempVP = visionKp.getDouble(Constants.visionPID[0]);
    if(Constants.visionPID[0] != tempVP) {
      Constants.visionPID[0] = tempVP;
      visionPID.setP(Constants.visionPID[0]);
    }

    double tempVI = visionKi.getDouble(Constants.visionPID[1]);
    if(Constants.visionPID[1] != tempVI) {
      Constants.visionPID[1] = tempVI;
      visionPID.setI(Constants.visionPID[1]);
    }

    double tempVD = visionKd.getDouble(Constants.visionPID[2]);
    if(Constants.visionPID[2] != tempVD) {
      Constants.visionPID[2] = tempVD;
      visionPID.setD(Constants.visionPID[2]);
    }

    double tempVisionTolerance = vsnTolerance.getDouble(Constants.visionTolerance[0]);
    if(Constants.visionTolerance[0] != tempVisionTolerance) {
      Constants.visionTolerance[0] = tempVisionTolerance;
      visionPID.setTolerance(Constants.visionTolerance[0], Constants.visionTolerance[1]);
    }

    double tempVisionVTolerance = vsnVTolerance.getDouble(Constants.visionTolerance[1]);
    if(Constants.visionTolerance[1] != tempVisionVTolerance) {
      Constants.visionTolerance[1] = tempVisionVTolerance;
      visionPID.setTolerance(Constants.visionTolerance[0], Constants.visionTolerance[1]);
    }

    //end visionPID. now for driving

    double tempDP = drivingKp.getDouble(Constants.drivingPID[0]);
    if(Constants.drivingPID[0] != tempDP) {
      Constants.drivingPID[0] = tempDP;
      drivingPID.setP(Constants.drivingPID[0]);
    }

    double tempDI = drivingKi.getDouble(Constants.drivingPID[1]);
    if(Constants.drivingPID[1] != tempDI) {
      Constants.drivingPID[1] = tempDI;
      drivingPID.setI(Constants.drivingPID[1]);
    }

    double tempDD = drivingKd.getDouble(Constants.drivingPID[2]);
    if(Constants.drivingPID[2] != tempDD) {
      Constants.drivingPID[2] = tempDD;
      drivingPID.setD(Constants.drivingPID[2]);
    }

    double tempDrivingTolerance = drvTolerance.getDouble(Constants.drivingTolerance[0]);
    if(Constants.drivingTolerance[0] != tempDrivingTolerance) {
      Constants.drivingTolerance[0] = tempDrivingTolerance;
      drivingPID.setTolerance(Constants.drivingTolerance[0], Constants.drivingTolerance[1]);
    }

    double tempDrivingVTolerance = drvVTolerance.getDouble(Constants.drivingTolerance[1]);
    if(Constants.drivingTolerance[1] != tempDrivingVTolerance) {
      Constants.drivingTolerance[1] = tempDrivingVTolerance;
      drivingPID.setTolerance(Constants.drivingTolerance[0], Constants.drivingTolerance[1]);
    }

    //heading PID stuff
    double tempHP = headingKp.getDouble(Constants.headingPID[0]);
    if(Constants.headingPID[0] != tempHP) {
      Constants.headingPID[0] = tempHP;
      headingPID.setP(Constants.headingPID[0]);
    }

    double tempHI = headingKi.getDouble(Constants.headingPID[1]);
    if(Constants.headingPID[1] != tempHI) {
      Constants.headingPID[1] = tempHI;
      headingPID.setI(Constants.headingPID[1]);
    }

    double tempHD = headingKd.getDouble(Constants.headingPID[2]);
    if(Constants.headingPID[2] != tempHD) {
      Constants.headingPID[2] = tempHD;
      headingPID.setD(Constants.headingPID[2]);
    }

    double tempHeadingTolerance = hdgTolerance.getDouble(Constants.headingTolerance[0]);
    if(Constants.headingTolerance[0] != tempHeadingTolerance) {
      Constants.headingTolerance[0] = tempHeadingTolerance;
      headingPID.setTolerance(Constants.headingTolerance[0], Constants.headingTolerance[1]);
    }

    double tempHeadingVTolerance = hdgVTolerance.getDouble(Constants.headingTolerance[1]);
    if(Constants.headingTolerance[1] != tempHeadingVTolerance) {
      Constants.headingTolerance[1] = tempHeadingVTolerance;
      headingPID.setTolerance(Constants.headingTolerance[0], Constants.headingTolerance[1]);
    }

    //tick constants

    double tempRTickConstant = rightTickConstant.getDouble(Constants.rightTickConstant);
    if(Constants.rightTickConstant != tempRTickConstant) {
      Constants.rightTickConstant = tempRTickConstant;
      encoderRight1.setPositionConversionFactor(Constants.rightTickConstant);
      encoderRight2.setPositionConversionFactor(Constants.rightTickConstant);
      encoderRight3.setPositionConversionFactor(Constants.rightTickConstant);
    }

    double tempLTickConstant = leftTickConstant.getDouble(Constants.leftTickConstant);
    if(Constants.leftTickConstant != tempLTickConstant) {
      Constants.leftTickConstant = tempLTickConstant;
      encoderLeft1.setPositionConversionFactor(Constants.leftTickConstant);
      encoderLeft2.setPositionConversionFactor(Constants.leftTickConstant);
      encoderLeft3.setPositionConversionFactor(Constants.leftTickConstant);
    }

    double tempRTickBoreConstant = rTickBoreConstant.getDouble(Constants.rTickBoreConstant);
    if(Constants.rTickBoreConstant != tempRTickBoreConstant) {
      Constants.rTickBoreConstant = tempRTickBoreConstant;
      throughboreRight.setDistancePerPulse(Constants.rTickBoreConstant);
    }

    double tempLTickBoreConstant = lTickBoreConstant.getDouble(Constants.lTickBoreConstant);
    if(Constants.lTickBoreConstant != tempLTickBoreConstant) {
      Constants.lTickBoreConstant = tempLTickBoreConstant;
      throughboreLeft.setDistancePerPulse(Constants.lTickBoreConstant);
    }


    if(resetGyroCommandEntry.getBoolean(false)) { 
      this.resetGyro();
    }

  }

  public void headingPIDReset() {
    headingPID.reset();
  }

  // idk if we need to reset again?
  public void drivingPIDReset() {
    drivingPID.reset();
  }

  public void encoderReset(){
    encoderLeft1.setPosition(0);
    encoderLeft2.setPosition(0);
    encoderLeft3.setPosition(0);
    encoderRight1.setPosition(0);
    encoderRight2.setPosition(0);
    encoderRight3.setPosition(0);
  }
  public void tankDrive(double leftPow, double rightPow) {
    drivetrain.tankDrive(leftPow / 2, rightPow / 2, false); //actually drive
  }

  public void visionPIDReset() {
    visionPID.reset();
  }

  public void ledsOff() {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
  }

  public void ledsOn() {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
  }

  /**
   * Aligns us with the vision target. 
   * This function is called in the AlignToTargetCommand.java. 
   * You need to import NetworkTable and NetworkTableEntry
   */
  public void visionAlign() {
    ledsOn();
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tv = table.getEntry("tv");
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);
    if(tv.getDouble(0) == 1) {
      NetworkTableEntry tx = table.getEntry("tx");
  
      //start loop
      double x_offset = tx.getDouble(0);
      visionError.setDouble(x_offset);
      drivetrain.arcadeDrive(0, MathUtil.clamp(-visionPID.calculate(x_offset), Constants.visionPIDconstraints[0], Constants.visionPIDconstraints[1]));
      if(x_offset < Constants.visionTolerance[0]) {
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

  public void toggleVisionAlign() {
    alignZoom = !alignZoom;
    if(alignZoom) {
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1);
    }
    else {
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);
    }
  }

  public double encoderAverage() {
    double average = leftEncoderAverage();
    average += rightEncoderAverage();
    average /=2;
    return average;
  }

  public double leftEncoderAverage() {
    double average = encoderLeft1.getPosition();
     average += encoderLeft2.getPosition();
     average += encoderLeft3.getPosition();
     average /= 3;
    return average;
  }

  public double rightEncoderAverage() {
    double average = encoderRight1.getPosition();
     average += encoderRight2.getPosition();
     average += encoderRight3.getPosition();
     average /= 3;
    return average;
  }



  public boolean driveOnHeading(double power, double angle) {
    final double currentAngle = ahrs.getYaw(); // is this right?
    final double turnPower = MathUtil.clamp(headingPID.calculate(currentAngle, angle), Constants.headingPIDconstraints[0], Constants.headingPIDconstraints[1]);
    drivetrain.arcadeDrive(power, turnPower);
    return headingPID.atSetpoint();
  }

  public boolean driveDistance(double distance,double angle) {
    final double currentDistance = encoderAverage();// is this right?
    final double drivePower = MathUtil.clamp(drivingPID.calculate(currentDistance, angle), -0.5, 0.5);
    final boolean headingAligned = this.driveOnHeading(drivePower, angle);
    return drivingPID.atSetpoint() && headingAligned;
  }

  /**
   * Resets our gyro (AHRS) so we don't kill people in auto
   */
  public boolean resetGyro() {
    ahrs.reset();
    return true;
  }
}

