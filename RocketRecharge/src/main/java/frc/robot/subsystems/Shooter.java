/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

//talon
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

//spark
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Shooter extends SubsystemBase {
  CANSparkMax shooterMotor;
  WPI_TalonSRX angleMotor;
  CANPIDController shooterPID;
  CANEncoder encoder;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

  private ShuffleboardTab tab = Shuffleboard.getTab("Shooter");
  private NetworkTableEntry ShooterkP = tab.addPersistent("ShooterkP", Constants.ShooterkP).getEntry();
  private NetworkTableEntry ShooterkI = tab.addPersistent("ShooterkI", Constants.ShooterkI).getEntry();
  private NetworkTableEntry ShooterkD = tab.addPersistent("ShooterkD", Constants.ShooterkD).getEntry();
  private NetworkTableEntry ShooterkIz = tab.addPersistent("ShooterkIz", Constants.ShooterkIz).getEntry();
  private NetworkTableEntry ShooterkFF = tab.addPersistent("ShooterkFF", Constants.ShooterkFF).getEntry();
  private NetworkTableEntry ShooterkMaxOutput = tab.addPersistent("ShooterkMaxOutput", Constants.ShooterkMaxOutput).getEntry();
  private NetworkTableEntry ShooterkMinOutput = tab.addPersistent("ShooterkMinOutput", Constants.ShooterkMinOutput).getEntry();
  //RIP MaxRPM you will be missed D:

  private NetworkTableEntry kTimeoutMs = tab.addPersistent("ShooterkMaxOutput", Constants.kTimeoutMs).getEntry();
  private NetworkTableEntry kPIDLoopIdx = tab.addPersistent("ShooterkMaxOutput", Constants.kPIDLoopIdx).getEntry();
  private NetworkTableEntry AnglekF = tab.addPersistent("ShooterkMaxOutput", Constants.AnglekF).getEntry();
  private NetworkTableEntry AnglekP = tab.addPersistent("ShooterkMaxOutput", Constants.AnglekP).getEntry();
  private NetworkTableEntry AnglekI = tab.addPersistent("ShooterkMaxOutput", Constants.AnglekI).getEntry();
  private NetworkTableEntry AnglekD = tab.addPersistent("ShooterkMaxOutput", Constants.AnglekD).getEntry();
  


  /**
   * Creates a new ExampleSubsystem.
   */
  public Shooter() {
    shooterMotor = new CANSparkMax(7, MotorType.kBrushless);
    shooterMotor.restoreFactoryDefaults();
    shooterPID = shooterMotor.getPIDController();
    encoder = shooterMotor.getEncoder();

    // PID coefficients
    kP = 5e-5; 
    kI = 1e-6;
    kD = 0; 
    kIz = 0; 
    kFF = 0; 
    kMaxOutput = 1; 
    kMinOutput = -1;
    
 
    // set PID coefficients
    shooterPID.setP(Constants.ShooterkP);
    shooterPID.setI(Constants.ShooterkI);
    shooterPID.setD(Constants.ShooterkD);
    shooterPID.setIZone(Constants.ShooterkIz);
    shooterPID.setFF(Constants.ShooterkFF);
    shooterPID.setOutputRange(Constants.ShooterkMinOutput, Constants.ShooterkMaxOutput);


    //angle motor config
    angleMotor.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);

    /* Config the peak and nominal outputs */
		angleMotor.configNominalOutputForward(0, Constants.kTimeoutMs);
		angleMotor.configNominalOutputReverse(0, Constants.kTimeoutMs);
		angleMotor.configPeakOutputForward(1, Constants.kTimeoutMs);
		angleMotor.configPeakOutputReverse(-1, Constants.kTimeoutMs);

		/* Config the Velocity closed loop gains in slot0 */
		angleMotor.config_kF(Constants.kPIDLoopIdx, Constants.AnglekF, Constants.kTimeoutMs);
		angleMotor.config_kP(Constants.kPIDLoopIdx, Constants.AnglekP, Constants.kTimeoutMs);
		angleMotor.config_kI(Constants.kPIDLoopIdx, Constants.AnglekI, Constants.kTimeoutMs);
    angleMotor.config_kD(Constants.kPIDLoopIdx, Constants.AnglekD, Constants.kTimeoutMs);
    
    /* Shooter with talon
    //shooter motor config

      shooterMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,Constants.kPIDLoopIdx,Constants.kTimeoutMs);

    // Config the peak and nominal outputs 
		shooterMotor.configNominalOutputForward(0, Constants.kTimeoutMs);
		shooterMotor.configNominalOutputReverse(0, Constants.kTimeoutMs);
		shooterMotor.configPeakOutputForward(1, Constants.kTimeoutMs);
		shooterMotor.configPeakOutputReverse(-1, Constants.kTimeoutMs);

		// Config the Velocity closed loop gains in slot0 
		shooterMotor.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kF, Constants.kTimeoutMs);
		shooterMotor.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kP, Constants.kTimeoutMs);
		shooterMotor.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kI, Constants.kTimeoutMs);
    shooterMotor.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kD, Constants.kTimeoutMs);
    */
    
  }

  public boolean warmUp(double RPM) {
    /* Shooter with talon
			 //Convert 500 RPM to units / 100ms.
			 //4096 Units/Rev * 500 RPM / 600 100ms/min in either direction:
			 // velocity setpoint is in units/100ms
			 
			double targetVelocity = RPM * 4096 / 600; 
    shooterMotor.set(ControlMode.Velocity,targetVelocity);
    return true;
    */

    //Shooter with Neo
    shooterPID.setReference(RPM, ControlType.kVelocity);
    return true;
  }

  public boolean shooterAngle(double angle) {
    angleMotor.set(ControlMode.Position, angle);
    //return Math.abs(angleMotor.getClosedLoopError()) < 1;
    return true;
  }

  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    //now for changing the PID values on robot and in Constants.java. this is going to be _very_ long
    double tempSP = ShooterkP.getDouble(Constants.ShooterkP);
    if(Constants.ShooterkP != tempSP) {
      Constants.ShooterkP = tempSP;
      shooterPID.setP(Constants.ShooterkP);
    }

    double tempSI = ShooterkI.getDouble(Constants.ShooterkI);
    if(Constants.ShooterkI != tempSI) {
      Constants.ShooterkI = tempSI;
      shooterPID.setI(Constants.ShooterkI);
    }

    double tempSD = ShooterkD.getDouble(Constants.ShooterkD);
    if(Constants.ShooterkD != tempSD) {
      Constants.ShooterkD = tempSD;
      shooterPID.setD(Constants.ShooterkD);
    }

    double tempSIz = ShooterkIz.getDouble(Constants.ShooterkIz);
    if(Constants.ShooterkIz != tempSIz) {
      Constants.ShooterkIz = tempSIz;
      shooterPID.setIZone(Constants.ShooterkIz);
    }

    double tempSFF = ShooterkFF.getDouble(Constants.ShooterkFF);
    if(Constants.ShooterkFF != tempSFF) {
      Constants.ShooterkFF = tempSFF;
      shooterPID.setFF(Constants.ShooterkFF);
    }

    double tempSMax = ShooterkMaxOutput.getDouble(Constants.ShooterkMaxOutput);
    if(Constants.ShooterkMaxOutput != tempSMax) {
      Constants.ShooterkMaxOutput = tempSMax;
      shooterPID.setOutputRange(Constants.ShooterkMinOutput,Constants.ShooterkMaxOutput);
    }
  
    double tempSMin = ShooterkMinOutput.getDouble(Constants.ShooterkMinOutput);
    if(Constants.ShooterkMinOutput != tempSMin) {
      Constants.ShooterkMaxOutput = tempSMin;
      shooterPID.setOutputRange(Constants.ShooterkMinOutput, Constants.ShooterkMaxOutput);
    }

    //angle motor

    



  }
}

/*command list for shooter

Warm up:
- connect with shooter subsystem
- speed up motor
- apply the current RPM to the shooter
ENDS WHEN- button is released
WHEN INTERUPTED- do nothing

Adjust power per position:
-connect with shooter subsystem
- read the current RPM of the motor
- have a preset shooter command that the shooter motor can stick with
   - detect vision alinement
   - run warmup
- once ready, then shoot

:-D

*/