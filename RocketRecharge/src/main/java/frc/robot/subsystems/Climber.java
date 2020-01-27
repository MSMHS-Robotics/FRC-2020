package org.usfirst.frc.team2723.robot.subsystems;

package org.usfirst.frc.team2723.robot.subsystems;



import org.usfirst.frc.team2723.robot.Robot;

import org.usfirst.frc.team2723.util.Constants;



import edu.wpi.first.wpilibj.Talon;

import edu.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.command.Subsystem;



/**

 *

 */

public class Climber extends SubsystemBase {



    // Put methods for controlling this subsystem

    // here. Call these from Commands.

	//public CANSparkMax Climber;
	
	climberMotor = new CANSparkMax(CAN_ID);

	DigitalInput forwardLimitSwitch, reverseLimitSwitch;

	

	public Climber() {

	    climberMotor = Robot.hardware.climberMotor;
		DigitalInput forwardLimitSwitch = new DigitalInput(1);


	}

	public ClimberDeploy() {

		climberMotor.set(Constants.CLIMBER_CLIMBER_SPEED);

	}

	public ClimberS() {

		climberMotor.set(Constants.INTAKE_OUTTAKE_SPEED);

	}

	public void stop() {

		climberMotor.set(0);

	}



    //public void initDefaultCommand() {

        // Set the default command for a subsystem here.

      //  setDefaultCommand(new MySpecialCommand());

    

}

