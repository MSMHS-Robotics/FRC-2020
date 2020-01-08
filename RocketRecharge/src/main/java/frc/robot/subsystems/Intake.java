package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  private Victor intakeMotor;
  private Victor beltMotor;

  //now for sensors
  private DigitalInput intakeSensor;
  private DigitalInput firstSensor;
  private DigitalInput lastSensor;
  /**
   * Creates a new ExampleSubsystem.
   */
  public Intake() {
		intakeMotor = new Victor(0); //our motors
		beltMotor = new Victor(1);
		intakeSensor = new DigitalInput(0); //some sensors
		firstSensor = new DigitalInput(1);
		lastSensor = new DigitalInput(2);
  }
  public void runIntake(double power) {
	  intakeMotor.set(power);
  }

  public void feed() {
	beltMotor.set(1);
  }

  public void stop() {
	  beltMotor.set(0);
  }


  public boolean prepShot() {
	  if(lastSensor.get()) {
		beltMotor.set(0);
		return true;
	  }
	  else {
		  beltMotor.set(1);
		  return false;
	  }
  }

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

  @Override
	public void periodic() {
		// This method will be called once per scheduler run
	}
}