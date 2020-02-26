package frc.robot.subsystems;

import java.util.Map; //need for boolean box widget on ShuffleBoard

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX; //hardware components
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.networktables.NetworkTableEntry; //shuffleboard
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class Intake extends SubsystemBase {
  private WPI_TalonSRX intakeMotor;
  private WPI_TalonSRX beltMotor;
  private WPI_TalonSRX triggerMotor;
  private Solenoid armPistons1;
  private Solenoid armPistons2;
  private DigitalInput lastSensor;
  private DigitalInput triggerSensor;
  private DigitalInput detector1;
  private DigitalInput detector2;
  private DigitalInput detector3;
  private DigitalInput detector4;
  private DigitalInput detector5;
  private DigitalInput[] detectors;

  private ShuffleboardTab Intaketab = Shuffleboard.getTab("Intake Tab");
  private NetworkTableEntry intakePosition = Intaketab.addPersistent("Intake Position", false).getEntry();
  private NetworkTableEntry shotPrepped = Intaketab.addPersistent("Shot Prepped", false).withWidget("Boolean Box").withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();

  	public Intake() {
		// uncomment once pneumatics attatched
		armPistons1 = new Solenoid(2);
		armPistons2 = new Solenoid(3);

		intakeMotor = new WPI_TalonSRX(15); // our motors
		beltMotor = new WPI_TalonSRX(11);
		triggerMotor = new WPI_TalonSRX(12); // change?

		lastSensor = new DigitalInput(0); // are we still using sensors?
		triggerSensor = new DigitalInput(1);

		detector1 = new DigitalInput(4);
		detector2 = new DigitalInput(5);
		detector3 = new DigitalInput(6);
		detector4 = new DigitalInput(7);
		detector5 = new DigitalInput(8);
		detectors = new DigitalInput[]{detector1, detector2, detector3, detector4, detector5};
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
		if (lastSensor.get()) {
			if (beltMotor != null) {
				beltMotor.set(0);
			}
			// pretty sure that works, might take some tuning
			if (triggerSensor.get()) {
				if (triggerMotor != null) {
					triggerMotor.set(1);
				}
				shotPrepped.setBoolean(this.prepShot());
				return true;
			} else {
				if (triggerMotor != null) {
					triggerMotor.set(0);
				}
				shotPrepped.setBoolean(this.prepShot());
			}
		} else {
			if (beltMotor != null) {
				beltMotor.set(1);
			}
			shotPrepped.setBoolean(this.prepShot());
			return false;
		}
		shotPrepped.setBoolean(this.prepShot());
		return false;
	}

	public void setIdle() {
		if (beltMotor != null) {
			beltMotor.set(0);
		}
	}

	public void intakeExtend() {
		if (armPistons1 != null) {
			armPistons1.set(true);
		}
		if (armPistons2 != null) {
			armPistons2.set(false);
		}
	}

	public void intakeRetract() {
		if (armPistons1 != null) {
			armPistons1.set(false);
		}
		if(armPistons2 != null) {
			armPistons2.set(true);
		}
  }

  public boolean isRaised() {
	return armPistons1.get() && armPistons2.get();
  }

  /*public boolean hasBall() {
	for(int x = 0; x < detectors.length; x++) {
		if(detectors[x].get()) {
			return true;
		}
	}
	return false;
  }*/

  @Override
	public void periodic() {
		intakePosition.setBoolean(this.isRaised());
	}
}