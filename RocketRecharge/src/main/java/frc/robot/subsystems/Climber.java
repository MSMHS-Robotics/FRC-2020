package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	//public CANSparkMax Climber;

	private DigitalInput forwardLimitSwitch, reverseLimitSwitch;
	private Talon climberMotor;
	//private Solenoid climberPistons;
	private Boolean isDeployed = false;
	

	public Climber() {
		forwardLimitSwitch = new DigitalInput(1);
		reverseLimitSwitch = new DigitalInput(2);
		//need to assign actual channel values
		climberMotor = new Talon(5);
		//climberPistons = new Solenoid(7);
	}

	public void ClimberDeploy() {
	//	climberPistons.set(true);
		isDeployed = true;
	}

	public void ClimberPistonsBackIn() {
	//	climberPistons.set(false);
		isDeployed = false;
	}

	public void raiseClimber() {
		if(isDeployed) {
			if(!forwardLimitSwitch.get()) {
				climberMotor.set(Constants.CLIMBER_CLIMBER_SPEED);
			}
		}
	}

	public void climbUp() {
		climberMotor.set(-Constants.CLIMBER_CLIMBER_SPEED);
	}

	public void climbUsingStick(double x) {
		climberMotor.set(x);
	}
	
	public void stop() {
		climberMotor.set(0);
	}
}

