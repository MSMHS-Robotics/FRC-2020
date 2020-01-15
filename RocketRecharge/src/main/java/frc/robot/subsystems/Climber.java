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

	public CANSparkMax Climber;

	

	public Climber() {

	    Climber = Robot.hardware.Climber;

	}

	public ClimberDeploy() {

		Climber.set(Constants.CLIMBER_CLIMBER_SPEED);

	}

	public ClimberS() {

		Climber.set(Constants.INTAKE_OUTTAKE_SPEED);

	}

	public void stop() {

		Climber.set(0);

	}



    public void initDefaultCommand() {

        // Set the default command for a subsystem here.

      //  setDefaultCommand(new MySpecialCommand());

    }

}

