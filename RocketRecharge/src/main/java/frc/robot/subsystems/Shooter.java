package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  private CANSparkMax shooterMotor1;
  private CANSparkMax shooterMotor2;

  private CANEncoder shooterEncoder;

  private CANPIDController shooterPID;
  
  public Shooter() {
    //TODO change these port numbers to be correct
    shooterMotor1 = new CANSparkMax(12, MotorType.kBrushless);
    shooterMotor2 = new CANSparkMax(12, MotorType.kBrushless);

    shooterEncoder = shooterMotor1.getEncoder();
    shooterPID = shooterMotor1.getPIDController();
    
    //TODO add pid values and config and stuff
    //shooterPID.setP();
    //shooterPID.setI();
    //shooterPID.setD();

    shooterMotor2.follow(shooterMotor1, true);
  }

  //TODO document this function or something
  public void shoot(double RPM) {
    shooterPID.setReference(RPM, ControlType.kVelocity);
  }

  //TODO documentation and verify that this is indeed necesssery
  public void stop() {
    shooterMotor1.setVoltage(0);
    shooterMotor2.setVoltage(0);
  }
}