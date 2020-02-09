package frc.robot.subsystems;

import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	//public CANSparkMax Climber;

	private DigitalInput forwardLimitSwitch, reverseLimitSwitch;
	private WPI_TalonSRX climberMotor;
	private Solenoid climberPistons1;
	private Solenoid climberPistons2;
	private Boolean isDeployed = false;
	

	public Climber() {
		forwardLimitSwitch = new DigitalInput(1);
		reverseLimitSwitch = new DigitalInput(2);
		//need to assign actual channel values
		climberMotor = new WPI_TalonSRX(5);
		//climberPistons1 = new Solenoid(7);
		//climberPistons2 = new Solenoid (6);
	}

	public void ClimberDeploy() {
		//if(climberPistons1 != null) {climberPistons1.set(true);isDeployed = true;}
		//if(climberPistons2 != null) {climberPistons2.set(true);isDeployed = true;}
	}

	public void ClimberPistonsBackIn() {
		//if(climberPistons1 != null) {climberPistons1.set(false);isDeployed = false;}
		//if(climberPistons2 != null) {climberPistons2.set(false);isDeployed = false;}
	}

	public void raiseClimber() {
		if(isDeployed) {
			if(!forwardLimitSwitch.get()) {
				if(climberMotor != null) {climberMotor.set(Constants.CLIMBER_CLIMBER_SPEED);}
			}
		}
	}

	public void climbUp() {
		if(climberMotor != null) {climberMotor.set(-Constants.CLIMBER_CLIMBER_SPEED);}
	}

	public void climbUsingStick(double x) {
		if(climberMotor != null) {climberMotor.set(x);}
	}
	
	public void stop() {
		if(climberMotor != null) {climberMotor.set(0);}
	}
}

