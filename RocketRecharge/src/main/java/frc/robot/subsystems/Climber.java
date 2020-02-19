package frc.robot.subsystems;

import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class Climber extends SubsystemBase {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	// public CANSparkMax Climber;

	private DigitalInput bottomLimitSwitch, topLimitSwitch;
	private AnalogInput encoder;
	private WPI_TalonSRX climberMotor;
	private Solenoid climberPistons1;
	private Solenoid climberPistons2;
	private Boolean isDeployed = false;
	private PIDController slidePID = new PIDController(0.0001, 0.00001, 0.00001);
	private PIDController climberPID = new PIDController(0.0001, 0.00001, 0.00001);
	private ShuffleboardTab Climbertab = Shuffleboard.getTab("Climber Tab");
	private NetworkTableEntry climberMotorSpeed = Climbertab.addPersistent("climberMotorSpeed", Constants.climberMotorSpeed).getEntry();
	private NetworkTableEntry motorPosition = Climbertab.addPersistent("Motor Position", 0).getEntry();
	private NetworkTableEntry motorUp = Climbertab.addPersistent("motorUp", Constants.motorUp).getEntry();

	public Climber() {
		bottomLimitSwitch = new DigitalInput(1);
		topLimitSwitch = new DigitalInput(2);
		// need to assign actual channel values
		// !==UPDATE==! --> values assigned now
		climberMotor = new WPI_TalonSRX(10);
		encoder = new AnalogInput(3);
		//slidePID.setSetpoint(4);
		//climberPID.setSetpoint(0.2);
		climberPistons1 = new Solenoid(0);
		climberPistons2 = new Solenoid(1);
	}

	public void ClimberDeploy() {
		//if (climberPistons1 != null) {
			climberPistons1.set(true);
			isDeployed = true;
		//}
		//if (climberPistons2 != null) {
			climberPistons2.set(false);
			isDeployed = true;
		
		//}
	}

	public void ClimberPistonsBackIn() {
		if (climberPistons1 != null) {
			climberPistons1.set(false);
			isDeployed = false;
		}
		if (climberPistons2 != null) {
			climberPistons2.set(true);
			isDeployed = false;
		}
	}

	@Deprecated
	public void raiseClimber() {
		if (isDeployed) {
			if (!topLimitSwitch.get()) {
				if (climberMotor != null) {
					climberMotor.set(Constants.climberMotorSpeed);
				}
			} else {
				if (climberMotor != null) {
					climberMotor.set(-0.01);
				}
			}
		}
	}

	public void raiseClimberPID() {
		if (isDeployed) {
			if (!topLimitSwitch.get()) {
				if (climberMotor != null) {
					climberMotor.set(slidePID.calculate(encoder.getVoltage()));
				}
			} else {
				climberMotor.set(0);
			}
		}
	}

	public void climbWithPID() {
		if (isDeployed) { //if pistons are out
			if (!bottomLimitSwitch.get()) { //and we aren't touching the bottom
				if (climberMotor != null) { //and motor exists
					climberMotor.set(climberPID.calculate(encoder.getVoltage())); //pid to it
				}
			} else { //we are touching the bottom
				if(climberMotor != null) { //motor exists
					climberMotor.set(0); //stop it
				}
			}
		}
	}

	@Deprecated
	public void climbUp() {
		if (isDeployed) { //if pistons are out
			if (!bottomLimitSwitch.get()) { //and we aren't touching the bottom
				if (climberMotor != null) { //and our motor is plugged in
					climberMotor.set(-Constants.climberMotorSpeed); //climb
				}
			} else { //we are touching the bottom
				if (climberMotor != null) { //motor exists
					climberMotor.set(0); //stop it
				}
			}
		}
	}

	public void climbUsingStick(double x) {
		if (climberMotor != null) {
			climberMotor.set(x);
		}
	}

	public void stop() {
		if (climberMotor != null) {
			climberMotor.set(0);
		}
		//climberMotor.set(-Constants.climberMotorSpeed);
	}

	@Override
	public void periodic() {
		double TempClimberSpeed = climberMotorSpeed.getDouble(0.5);
		if (TempClimberSpeed != Constants.climberMotorSpeed) {
			Constants.climberMotorSpeed = TempClimberSpeed;
			climberMotorSpeed.setDouble(Constants.climberMotorSpeed);
		}
		motorPosition.setDouble((double) encoder.getAccumulatorCount());
	}

	public void stopRaise() {
		climberMotor.set(0);
	}
}
