package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//TODO documentation
public class Drivetrain extends SubsystemBase {
  private CANSparkMax left1;
  private CANSparkMax left2;
  private CANSparkMax left3;
  private CANSparkMax right1;
  private CANSparkMax right2;
  private CANSparkMax right3;

  private CANEncoder left1Encoder;
  private CANEncoder left2Encoder;
  private CANEncoder left3Encoder;
  private CANEncoder right1Encoder;
  private CANEncoder right2Encoder;
  private CANEncoder right3Encoder;

  private SpeedControllerGroup leftSide;
  private SpeedControllerGroup rightSide;
  private DifferentialDrive drive;

  private double speedMod;

  private AHRS gyro;

  private PIDController speedPID;
  private PIDController headingPID;

  private double angle;
  private double setSpeed; //TODO make shuffleboard

  //TODO Shuffleboard
  
  public Drivetrain() {
    left1 = new CANSparkMax(1, MotorType.kBrushless);
    left2 = new CANSparkMax(2, MotorType.kBrushless);
    left3 = new CANSparkMax(3, MotorType.kBrushless);
    right1 = new CANSparkMax(4, MotorType.kBrushless);
    right2 = new CANSparkMax(5, MotorType.kBrushless);
    right3 = new CANSparkMax(6, MotorType.kBrushless);

    left1Encoder = new CANEncoder(left1);
    left2Encoder = new CANEncoder(left2);
    left3Encoder = new CANEncoder(left3);
    right1Encoder = new CANEncoder(right1);
    right2Encoder = new CANEncoder(right2);
    right3Encoder = new CANEncoder(right3);

    //TODO set encoder position conversion factors

    gyro = new AHRS();

    leftSide = new SpeedControllerGroup(left1, left2, left3);
    rightSide = new SpeedControllerGroup(right1, right2, right3);
    drive = new DifferentialDrive(leftSide, rightSide);
  }

  //TODO documentation
  public double getLeftEncAvg() {
    return (left1Encoder.getPosition() + left2Encoder.getPosition() + left3Encoder.getPosition()) / 3;
  }

  //TODO documentation
  public double getRightEncAvg() {
    return (right1Encoder.getPosition() + right2Encoder.getPosition() + right3Encoder.getPosition()) / 3;
  }

    //TODO documentation
  public double getEncAvg() {
    return (getLeftEncAvg() + getRightEncAvg()) / 2;
  }

    //TODO documentation
  public void driveTeleOp(double left, double right) {
    drive.tankDrive(left * speedMod, right * speedMod, false);
  }

    //TODO documentation
  public void resetGyro() {
    gyro.reset();
  }

    //TODO documentation
  public void resetEncoders() {
    left1Encoder.setPosition(0);
    left2Encoder.setPosition(0);
    left3Encoder.setPosition(0);
    right1Encoder.setPosition(0);
    right2Encoder.setPosition(0);
    right3Encoder.setPosition(0);
  }

    //TODO documentation
  public void resetSpeedPID() {
    speedPID.reset();
  }

    //TODO documentation
  public void resetHeadingPID() {
    headingPID.reset();
  }

    //TODO documentation
  public double getHeading() {
    return gyro.getFusedHeading();
  }

  public void setAngle() {
    angle = getHeading();
  }

  //TODO make getSpeed work
  public double getSpeed() {
    return 0;
  }

  //TODO documenetation and DEFINATELY FREAKIN TEST THIS BIT
  public void driveOnHeading() {
    drive.arcadeDrive(speedPID.calculate(setSpeed - getSpeed()), headingPID.calculate(angle - getHeading()), false);
  }
}