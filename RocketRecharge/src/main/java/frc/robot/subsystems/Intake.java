package frc.robot.subsystems;

import java.util.Map; //need for boolean box widget on ShuffleBoard

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX; //hardware components
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.networktables.NetworkTableEntry; //shuffleboard
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class Intake extends SubsystemBase {
  private WPI_TalonSRX intakeMotor;
  private WPI_TalonSRX beltMotor;
  private WPI_TalonSRX triggerMotor;
  private DigitalInput triggerSensor;
  private DigitalInput detector1;
  private DigitalInput detector2;
  private DigitalInput detector3;
  private DigitalInput detector4;
  private DigitalInput detector5;
  private DigitalInput[] detectors;

  private ShuffleboardTab Intaketab = Shuffleboard.getTab("Intake Tab");
  private NetworkTableEntry intakePosition = Intaketab.addPersistent("Intake Position", false).getEntry();
  
  private NetworkTableEntry irAll = Intaketab.addPersistent("Has Any Ball", false).withWidget("Boolean Box").withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();
  private NetworkTableEntry irTrigger = Intaketab.addPersistent("Chamber", false).withWidget("Boolean Box").withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry(); 
  private NetworkTableEntry ir1 = Intaketab.addPersistent("Ball 1", false).withWidget("Boolean Box").withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();
  private NetworkTableEntry ir2 = Intaketab.addPersistent("Ball 2", false).withWidget("Boolean Box").withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();
  private NetworkTableEntry ir3 = Intaketab.addPersistent("Ball 3", false).withWidget("Boolean Box").withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();
  private NetworkTableEntry ir4 = Intaketab.addPersistent("Ball 4", false).withWidget("Boolean Box").withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();
  private NetworkTableEntry ir5 = Intaketab.addPersistent("Ball 5", false).withWidget("Boolean Box").withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();
  private NetworkTableEntry[] irs;

  	public Intake() {
		// uncomment once pneumatics attatched
		irs = new NetworkTableEntry[] {ir1, ir2, ir3, ir4, ir5, irTrigger};
		intakeMotor = new WPI_TalonSRX(15); // our motors
		beltMotor = new WPI_TalonSRX(11);
		triggerMotor = new WPI_TalonSRX(12); // change?

		triggerSensor = new DigitalInput(1);

		detector1 = new DigitalInput(4);
		detector2 = new DigitalInput(5);
		detector3 = new DigitalInput(6);
		detector4 = new DigitalInput(7);
		detector5 = new DigitalInput(8);
		detectors = new DigitalInput[] {detector1, detector2, detector3, detector4, detector5, triggerSensor};
	}

	public void runIntake(double power) {
		if (intakeMotor != null) {
			intakeMotor.set(power);
		}
	}

	public void runIndexer(double pow) {
		if (beltMotor != null) {
			beltMotor.set(pow);
		}
	}

	public void runTrigger(double power) {
		if (triggerMotor != null) {
			triggerMotor.set(power);
		}
	}

	public void feed(double power) {
		if (beltMotor != null) {
			beltMotor.set(power * 0.5);
		}
		if (triggerMotor != null) {
			triggerMotor.set(-power);
		}
	}

	public boolean prepShot() {
		return false;
	}

	public void setIdle() {
		if(!triggerSensor.get()) {
			if (beltMotor != null) {
				beltMotor.set(0.5);
			}
			if (triggerMotor != null) {
				triggerMotor.set(-1);
			}
		}
		else {
			this.feed(0);
		}
	}

  public boolean hasBall() {
	for(int x = 0; x < detectors.length; x++) {
		irs[x].setBoolean(detectors[x].get()); //complete jank
		if(detectors[x].get()) {
			irAll.setBoolean(true);
			return true;
		}
	}
	return false;
  }

  @Override
	public void periodic() {
		intakePosition.setBoolean(false);
		this.setIdle();
	}
}