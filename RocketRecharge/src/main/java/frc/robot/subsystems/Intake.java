package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  private Motor intakeMotor;
  private Motor motor1;
  private Motor motor2;
  private Motor motor3;
  private Motor motor4;
  private Motor motor5;

  boolean autoMode;
  //now for sensors
  private IRSensor sensor1;
  private IRSensor sensor2;
  private IRSensor sensor3;
  private IRSensor sensor4;
  private IRSensor sensor5;
  /**
   * Creates a new ExampleSubsystem.
   */
  public Intake() {

  }

  public void indexCells() {
	if(!sensor1) {
		intakeMotor.power(1);
	}
	if(sensor1) {
		if(!sensor2)
		  motor1.power(1);
	}
	if(sensor2) {
	  if(sensor3) {
		  motor2.power(1);
	  }
	}
	if(sensor3) {
		if(!sensor4) {
			motor3.power(1);
	  }
  }
  if(sensor4) {
	  if(!sensor5) {
		  motor4.power(1);
	  }
  }
  if(sensor5) {
	  motor5.power(1);
  }
  wait(1000);
  motor1.power(0);
  motor2.power(0);
  motor3.power(0);
  motor4.power(0);
  motor5.power(0);
  }

  @Override
	public void periodic() {
		// This method will be called once per scheduler run
		if(autoMode) {
			this.indexCells();
		}
	}
}
