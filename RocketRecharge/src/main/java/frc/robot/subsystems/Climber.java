package frc.robot.subsystems;

import frc.robot.Constants;
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
	//public CANSparkMax Climber;

	DigitalInput forwardLimitSwitch, reverseLimitSwitch;
	Talon climberMotor;
	Solenoid climberPistons;
	Object arm;
	private final ShuffleboardTab Climbertab = Shuffleboard.getTab("Climber Tab");

	private final NetworkTableEntry CLIMBER_CLIMBER_SPEED = Climbertab.addPersistent("CLIMBER_CLIMBER_SPEED", Constants.CLIMBER_CLIMBER_SPEED).getEntry();
	private final NetworkTableEntry INTAKE_OUTTAKE_SPEED = Climbertab.addPersistent("INTAKE_OUTTAKE_SPEED", Constants.INTAKE_OUTTAKE_SPEED).getEntry();
	private final NetworkTableEntry motorposition = Climbertab.addPersistent("motorposition", Constants.motorPosition).getEntry();
    private final NetworkTableEntry motorUp = Climbertab.addPersistent("motorUp", Constants.motorUp).getEntry();

    



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
		climberMotor.set(-Constants.CLIMBER_CLIMBER_SPEED);
		
	}

	@Override
	public void periodic() {

		final double TempClimberSpeed = CLIMBER_CLIMBER_SPEED.getDouble(0.5);

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

