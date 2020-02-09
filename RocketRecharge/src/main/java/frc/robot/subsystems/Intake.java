package frc.robot.subsystems;

import java.util.Map;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  private WPI_TalonSRX intakeMotor;
  private WPI_TalonSRX beltMotor;
  private WPI_TalonSRX bigWheelMotor;
  private Solenoid armPistons1;
  private Solenoid armPistons2;

  private ShuffleboardTab Intaketab = Shuffleboard.getTab("Intake Tab");
  private NetworkTableEntry intakeMotorSpeed = Intaketab.addPersistent("Intake Motor Speed", 0.5).getEntry();
  private NetworkTableEntry intakePosition = Intaketab.addPersistent("Intake Position", false).getEntry();
  private NetworkTableEntry shotPrepped = Intaketab.addPersistent("Shot Prepped", false).withWidget("Boolean Box").withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();
  //now for sensors
  private DigitalInput lastSensor;
  private DigitalInput bigWheelSensor;

  private boolean intakeRaised = true;
  /**
   * Creates a new ExampleSubsystem.
   */
  public Intake() {
	//the "1" is a port number. change
	//armPistons1 = new Solenoid(0);
	//armPistons2 = new Solenoid(1);
	intakeMotor = new WPI_TalonSRX(12); //our motors
	beltMotor = new WPI_TalonSRX(13);
	lastSensor = new DigitalInput(4); //are we still using sensors?
	bigWheelSensor = new DigitalInput(3);
	bigWheelMotor = new WPI_TalonSRX(9); //change?
  }

  public void runIntake(double power) {
	if(intakeMotor != null) {intakeMotor.set(power);}
  }

  public void feed() {
	if(beltMotor != null) {beltMotor.set(1);}
	if(bigWheelMotor != null) {bigWheelMotor.set(1);}
  }

  public void feedReverse() {
	if(beltMotor!= null) {beltMotor.set(-1);}
  }

  public void stop() {
	if(beltMotor != null) {beltMotor.set(0);}
	if(bigWheelMotor != null) {bigWheelMotor.set(0);}
  }

  public boolean prepShot() {
	if(lastSensor.get()) {
		if(beltMotor != null) {beltMotor.set(0);}
		//pretty sure that works, might take some tuning
		if(bigWheelSensor.get()) {
			if(bigWheelMotor != null) {bigWheelMotor.set(1);}
			shotPrepped.setBoolean(this.prepShot());
			return true;
		}
		else {
			if(bigWheelMotor != null) {bigWheelMotor.set(0);}
			shotPrepped.setBoolean(this.prepShot());
		}
		}
	else {
		if(beltMotor != null) {beltMotor.set(1);}
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

  public void toggleIntake() {
	intakeRaised = !intakeRaised;
	//if(armPistons1 != null) {armPistons1.set(intakeRaised);}
	//if(armPistons2 != null) {armPistons2.set(intakeRaised);}
  }

  public boolean isRaised() {
	return armPistons1.get();
  }

  public void triggerForward() {
	if(bigWheelMotor != null) {bigWheelMotor.set(1);}
  }

  public void triggerBackward() {
	if(bigWheelMotor != null) {bigWheelMotor.set(-1);}
  }

  public void triggerStop() {
	if(bigWheelMotor != null) {bigWheelMotor.set(0);}
  }

  @Override
	public void periodic() {
		double tempMotorSpeed = intakeMotorSpeed.getDouble(0.5);
		if(tempMotorSpeed != Constants.intakeMotorSpeed) {
			Constants.intakeMotorSpeed = tempMotorSpeed;
			intakeMotorSpeed.setDouble(Constants.intakeMotorSpeed);
		}
		/*try{
			intakePosition.setBoolean(armPistons1.get());
		}
		catch(NullPointerException exception){
			intakePosition.setBoolean(false);
		}*/
	}
}