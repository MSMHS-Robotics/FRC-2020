package frc.robot.subsystems;

import java.util.Map; //need for boolean box widget on ShuffleBoard

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX; //hardware components
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.networktables.NetworkTableEntry; //shuffleboard
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class Intake extends SubsystemBase {
	private WPI_TalonSRX beltMotor;
	private CANSparkMax triggerMotor;

	private ShuffleboardTab Intaketab = Shuffleboard.getTab("Intake Tab");
	private NetworkTableEntry intakePosition = Intaketab.addPersistent("Intake Position", false).getEntry();
	private NetworkTableEntry intakeEncoderValue = Intaketab.addPersistent("IntakeEncoder", 0).getEntry();

	// intake PID

	public Intake() {
		beltMotor = new WPI_TalonSRX(11);
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
			beltMotor.set(power);
		}
		if (triggerMotor != null) {
			triggerMotor.set(power);
		}
	}
		
	@Override
	public void periodic() {
		intakePosition.setBoolean(false);
	}
}
