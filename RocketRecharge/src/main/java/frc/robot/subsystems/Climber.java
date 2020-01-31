package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANDigitalInput.LimitSwitch;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Climber extends SubsystemBase {
	
	private final CANSparkMax climberMotor = new CANSparkMax(8, MotorType.kBrushless);



    // Put methods for controlling this subsystem

    // here. Call these from Commands.

	//public CANSparkMax Climber;
	
	
	DigitalInput forwardLimitSwitch, reverseLimitSwitch;

	

	public Climber() {

	    
		final DigitalInput forwardLimitSwitch = new DigitalInput(1);


	}

	public boolean DeployClimber() {

		climberMotor.set(Constants.motorPosition);
		if(forwardLimitSwitch.get()){
			climberMotor.set(0);
			return true;	
		}
			return false;

		//climberMotor.set(Constants.motorPosition);
		//if (forwardLimitSwitch.get()){
			//return true;
		//}
		//return false;
	}

	public void ClimbUpCommand() {

		climberMotor.set(Constants.motorUp);

	}

	public void stop() {

		climberMotor.set(0);

	}

	public static void set(int i) {
	}



    //public void initDefaultCommand() {

        // Set the default command for a subsystem here.

      //  setDefaultCommand(new MySpecialCommand());

    

}

