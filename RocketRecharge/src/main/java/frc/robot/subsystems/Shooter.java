package frc.robot.subsystems;

import java.util.Map;

//talon
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
//spark
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  private CANSparkMax shooterMotor;
  private CANSparkMax shooterMotor2;
  private CANEncoder encoder;
  private double RPMSetpoint;

  private ShuffleboardTab tab1 = Shuffleboard.getTab("Shooter");
  private NetworkTableEntry ShooterRPM = tab1.addPersistent("ShooterRPM", 0).getEntry();
  private NetworkTableEntry isShooterGood = tab1.addPersistent("is Shooter Good", false).withWidget("Boolean Box").withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();
  private NetworkTableEntry isShooting = tab1.addPersistent("Shooter is Shooting", false).withWidget("Boolean Box").withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();

  /**
   * Creates a new Shooter.
   */
  public Shooter() {
    //first motor
    shooterMotor = new CANSparkMax(7, MotorType.kBrushless);
    shooterMotor.restoreFactoryDefaults();
    shooterMotor.setInverted(true);
   
    //second motor
    shooterMotor2 = new CANSparkMax(8, MotorType.kBrushless);
    shooterMotor2.restoreFactoryDefaults();
    shooterMotor2.follow(shooterMotor, true);
  }
  
 
  public void warmUp(double RPM) { 
    shooterPID.setReference(RPM, ControlType.kVelocity);
  }

  public void stop() {
    shooterMotor.set(0);
  }

  public void customShot(double rpm) {
    //shooterAngle(Constants.TrenchAngle); //need to change. this is temp. we don't have an articulating hood yet, so should be fine for now
    warmUp(rpm);
    RPMSetpoint = rpm;
    angleSetpoint = Constants.TrenchAngle; //also temp. <!-- !!!CHANGE!!! -->
    neededRPM.setDouble(rpm);
  }

  public boolean isShooterGood() {
    if (encoder == null){
      isShooterGood.setBoolean(false);
      return false;
    }

    if(Math.abs(encoder.getVelocity() - RPMSetpoint) < Constants.RPMTolerance && Math.abs(shotAcceleration) < Constants.accelerationTolerance) {
      isShooterGood.setBoolean(true);
      return true;
    }

    isShooterGood.setBoolean(false);
    return false;   
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    ShooterRPM.setDouble(shooterMotor.getEncoder().getVelocity());
    
    double tempTrenchRPM = TrenchRPM.getDouble(Constants.TrenchRPM);
    if(Constants.TrenchRPM != tempTrenchRPM){
      Constants.TrenchRPM = tempTrenchRPM;
    }
  }
}