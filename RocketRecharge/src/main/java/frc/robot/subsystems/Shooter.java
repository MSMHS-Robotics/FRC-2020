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

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Shooter extends SubsystemBase {
  CANSparkMax shooterMotor;
  WPI_TalonSRX angleMotor;
  CANPIDController pidController;
  CANEncoder encoder;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;
  /**
   * Creates a new ExampleSubsystem.
   */
  public Shooter() {
    shooterMotor = new CANSparkMax(deviceID, MotorType.kBrushless);
    shooterMotor.restoreFactoryDefaults();
    pidController = shooterMotor.getPIDController();
    encoder = shooterMotor.getEncoder();

    // PID coefficients
    kP = 5e-5; 
    kI = 1e-6;
    kD = 0; 
    kIz = 0; 
    kFF = 0; 
    kMaxOutput = 1; 
    kMinOutput = -1;
    maxRPM = 5700;
 
    // set PID coefficients
    pidController.setP(kP);
    pidController.setI(kI);
    pidController.setD(kD);
    pidController.setIZone(kIz);
    pidController.setFF(kFF);
    pidController.setOutputRange(kMinOutput, kMaxOutput);


    //angle motor config
    angleMotor.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);

    /* Config the peak and nominal outputs */
		angleMotor.configNominalOutputForward(0, Constants.kTimeoutMs);
		angleMotor.configNominalOutputReverse(0, Constants.kTimeoutMs);
		angleMotor.configPeakOutputForward(1, Constants.kTimeoutMs);
		angleMotor.configPeakOutputReverse(-1, Constants.kTimeoutMs);

		/* Config the Velocity closed loop gains in slot0 */
		angleMotor.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kF, Constants.kTimeoutMs);
		angleMotor.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kP, Constants.kTimeoutMs);
		angleMotor.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kI, Constants.kTimeoutMs);
    angleMotor.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kD, Constants.kTimeoutMs);
    
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
    pidController.setReference(RPM, ControlType.kVelocity);
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