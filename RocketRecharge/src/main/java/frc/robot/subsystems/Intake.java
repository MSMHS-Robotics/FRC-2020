package frc.robot.subsystems;

import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  private Talon intakeMotor;
  private Talon beltMotor;
  private Talon bigWheelMotor;
  private Solenoid armPistons;

  private ShuffleboardTab Intaketab = Shuffleboard.getTab("Intake");
  private NetworkTableEntry intakeMotorSpeed = Intaketab.addPersistent("Intake Motor Speed", 0.5).getEntry();
  private NetworkTableEntry intakePosition = Intaketab.addPersistent("Intake Position", false).getEntry();
  private NetworkTableEntry shotPrepped = Intaketab.addPersistent("Shot Prepped", false).withWidget("Boolean Box").withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();
  //now for sensors
  //private DigitalInput intakeSensor;
  //private DigitalInput firstSensor;
  private DigitalInput lastSensor;
  private DigitalInput bigWheelSensor;
  /**
   * Creates a new ExampleSubsystem.
   */
  public Intake() {
	//the "1" is a port number. change
	armPistons = new Solenoid(1);
	intakeMotor = new Talon(0); //our motors
	beltMotor = new Talon(1);
	//intakeSensor = new DigitalInput(0); //some sensors
	//firstSensor = new DigitalInput(1);
	lastSensor = new DigitalInput(2);
	bigWheelSensor = new DigitalInput(3);
	bigWheelMotor = new Talon(2);
  }
  public void runIntake(double power) {
	  intakeMotor.set(power);
  }

  public void feed() {
	beltMotor.set(1);
	bigWheelMotor.set(1);
  }

  public void stop() {
	beltMotor.set(0);
	bigWheelMotor.set(0);
  }

  public boolean prepShot() {
	  if(lastSensor.get()) {
		beltMotor.set(0);
		//pretty sure that works, might take some tuning
		if(bigWheelSensor.get()) {
			bigWheelMotor.set(1);
			shotPrepped.setBoolean(this.prepShot());
			return true;
		}
		else {
			bigWheelMotor.set(0);
			shotPrepped.setBoolean(this.prepShot());
		}
	  }
	  else {
		  beltMotor.set(1);
		  shotPrepped.setBoolean(this.prepShot());
		  return false;
	  }
	  shotPrepped.setBoolean(this.prepShot());
	  return false;
  }

  /*
  public boolean setIdle() {
	  if(firstSensor.get()) {
		beltMotor.set(0);
		return true;
	  }
	  else {
		beltMotor.set(-1);  
		return false;
	  }
  }
  */

  public void raiseIntake() {
	armPistons.set(true);
  }

  public void lowerIntake() {
	  armPistons.set(false);
  }

  public boolean isRaised() {
	  return armPistons.get();
  }

  @Override
	public void periodic() {
		double tempMotorSpeed = intakeMotorSpeed.getDouble(0.5);
		if(tempMotorSpeed != Constants.intakeMotorSpeed) {
			Constants.intakeMotorSpeed = tempMotorSpeed;
			intakeMotorSpeed.setDouble(Constants.intakeMotorSpeed);
		}
		intakePosition.setBoolean(armPistons.get());
	}
}