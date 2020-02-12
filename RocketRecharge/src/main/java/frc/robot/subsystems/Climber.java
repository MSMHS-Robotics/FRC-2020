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
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase; //adding shuffleboard commands
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	private final ShuffleboardTab Climbertab = Shuffleboard.getTab("Climber Tab");
	private Object arm;
	private final NetworkTableEntry CLIMBER_CLIMBER_SPEED = Climbertab.addPersistent("CLIMBER_CLIMBER_SPEED", Constants.CLIMBER_CLIMBER_SPEED).getEntry();
	private final NetworkTableEntry INTAKE_OUTTAKE_SPEED = Climbertab.addPersistent("INTAKE_OUTTAKE_SPEED", Constants.INTAKE_OUTTAKE_SPEED).getEntry();
	private final NetworkTableEntry motorposition = Climbertab.addPersistent("motorposition", Constants.motorPosition).getEntry();
    private final NetworkTableEntry motorUp = Climbertab.addPersistent("motorUp", Constants.motorUp).getEntry();

	
	public Climber() {
		bottomLimitSwitch = new DigitalInput(1);
		topLimitSwitch = new DigitalInput(2);
		//need to assign actual channel values
		//!==UPDATE==! --> values assigned now
		climberMotor = new WPI_TalonSRX(10);
		encoder = new AnalogInput(3);
		slidePID.setSetpoint(4);
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
			if(!topLimitSwitch.get()) {
				if(climberMotor != null) {climberMotor.set(Constants.CLIMBER_CLIMBER_SPEED);}
			}
			else {
				if(climberMotor != null) {climberMotor.set(-0.01);}
			}
		}
	}

	public void raiseClimberPID() {
		if(isDeployed) {
			if(!topLimitSwitch.get()) {
				climberMotor.set(slidePID.calculate(encoder.getVoltage()));
			}
			else {
				climberMotor.set(0);
			}
		}

	public Climber() {
		forwardLimitSwitch = new DigitalInput(1);
		reverseLimitSwitch = new DigitalInput(2);
		//need to assign act2ual channel values
		climberMotor = new Talon(5);
		climberPistons = new Solenoid(7);
	}

	public void ClimberDeploy() {
		climberPistons.set(true);
	}

	public void raiseClimber() {
		climberMotor.set(Constants.CLIMBER_CLIMBER_SPEED);
	}


	public void climbUp() {
		if(isDeployed) {
			if(!bottomLimitSwitch.get()) {
				if(climberMotor != null) {climberMotor.set(-Constants.CLIMBER_CLIMBER_SPEED);}
			}
			else {
				if(climberMotor != null) {climberMotor.set(0.001);}
			}
		}
	}

	public void climbUsingStick(double x) {
		if(climberMotor != null) {climberMotor.set(x);}
	}
	
	public void stop() {
		if(climberMotor != null) {climberMotor.set(0);}
		climberMotor.set(-Constants.CLIMBER_CLIMBER_SPEED);
	}

	@Override
	public void periodic() {

		private double TempClimberSpeed = CLIMBER_CLIMBER_SPEED.getDouble(0.5);
		if(TempClimberSpeed != Constants.CLIMBER_CLIMBER_SPEED){
			Constants.CLIMBER_CLIMBER_SPEED = TempClimberSpeed;
			CLIMBER_CLIMBER_SPEED.setDouble(Constants.CLIMBER_CLIMBER_SPEED);
		}
	}

	public boolean stopRaise() {
		climberMotor.set(0);
		return forwardLimitSwitch.get();
	}
}

