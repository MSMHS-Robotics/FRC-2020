package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	// public CANSparkMax Climber;

	private Encoder encoder;
	private WPI_TalonSRX climberMotor;
	private Solenoid climberPistons1;
	private Solenoid climberPistons2;
	private Boolean isDeployed = false;
	private PIDController slidePID = new PIDController(0.0001, 0.00001, 0.00001);
	private PIDController climberPID = new PIDController(0.0001, 0.00001, 0.00001);
	private ShuffleboardTab Climbertab = Shuffleboard.getTab("Climber Tab");
	private NetworkTableEntry climberMotorPwr = Climbertab.addPersistent("Climber Motor Power", Constants.climberMotorPwr).getEntry();
	private NetworkTableEntry motorPosition = Climbertab.addPersistent("Motor Position", 0).getEntry();
	
	public Climber() {
		// need to assign actual channel values
		// !==UPDATE==! --> values assigned now
		climberMotor = new WPI_TalonSRX(10);
		encoder = new Encoder(3, 4);
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
		//if (climberPistons1 != null) {
			climberPistons1.set(false);
			isDeployed = false;
		//}
		//if (climberPistons2 != null) {
			climberPistons2.set(true);
			isDeployed = false;
		//}
	}

	@Deprecated
	public void raiseClimber() {
		if (isDeployed) {
			if (climberMotor.isFwdLimitSwitchClosed() == 0) {
				if (climberMotor != null) {
					climberMotor.set(Constants.climberMotorPwr);
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
			if (climberMotor.isFwdLimitSwitchClosed() == 0) {
				if (climberMotor != null) {
					climberMotor.set(slidePID.calculate(encoder.getDistance()));
				}
			} else {
				climberMotor.set(0);
			}
		}
	}
	
	public void climbWithPID() {
		if (isDeployed) {
			if (climberMotor.isRevLimitSwitchClosed() == 0) {
				if (climberMotor != null) {
					climberMotor.set(climberPID.calculate(encoder.getDistance()));
				}
			}
		}
	}

	@Deprecated
	public void climbUp() {
		if (isDeployed) {
			if (climberMotor.isRevLimitSwitchClosed() == 0) {
				if (climberMotor != null) {
					climberMotor.set(-Constants.climberMotorPwr);
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
		climberMotor.set(-Constants.climberMotorPwr);
	}
	
	public void stopRaise() {
		climberMotor.set(0);
	}

	@Override
	public void periodic() {
		double TempClimberSpeed = climberMotorPwr.getDouble(0.5);
		if (TempClimberSpeed != Constants.climberMotorPwr) {
			Constants.climberMotorPwr = TempClimberSpeed;
			climberMotorPwr.setDouble(Constants.climberMotorPwr);
		}
		motorPosition.setDouble(encoder.getDistance());
	}
}
