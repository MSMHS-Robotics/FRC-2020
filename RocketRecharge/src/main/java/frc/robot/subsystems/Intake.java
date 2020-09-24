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
	private WPI_TalonSRX intakePositionMotor;
	private WPI_TalonSRX intakeMotor;
	private WPI_TalonSRX beltMotor;
	private CANSparkMax triggerMotor;

	private boolean intakeExtended = false;

	// intake PID

	public Intake() {
		// uncomment once pneumatics attatched
		intakeMotor = new WPI_TalonSRX(15); // our motors
		beltMotor = new WPI_TalonSRX(11);
		triggerMotor = new CANSparkMax(12, MotorType.kBrushless); // change?
		intakePositionMotor = new WPI_TalonSRX(10);
		
		intakePositionMotor.set(ControlMode.PercentOutput, 0);
		intakePositionMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx, Constants.kTimeoutMsin);
		intakePositionMotor.setSelectedSensorPosition(0);
		intakePositionMotor.setSensorPhase(true);
		intakePositionMotor.setInverted(true);

		if (intakePositionMotor != null) {
			intakePositionMotor.configAllowableClosedloopError(0, Constants.kPIDLoopIdxin, Constants.kTimeoutMsin);
		}
		
		// Config the peak and nominal outputs
		if (intakePositionMotor != null) {
			intakePositionMotor.configNominalOutputForward(0, Constants.kTimeoutMsin);
			intakePositionMotor.configNominalOutputReverse(0, Constants.kTimeoutMsin);
			intakePositionMotor.configPeakOutputForward(1, Constants.kTimeoutMsin);
			intakePositionMotor.configPeakOutputReverse(-1, Constants.kTimeoutMsin);
		}

		// Config the Velocity closed loop gains in slot0
		if (intakePositionMotor != null) {
			intakePositionMotor.config_kP(Constants.kPIDLoopIdxin, Constants.intakekP, Constants.kTimeoutMsin);
			intakePositionMotor.config_kI(Constants.kPIDLoopIdxin, Constants.intakekI, Constants.kTimeoutMsin);
			intakePositionMotor.config_kD(Constants.kPIDLoopIdxin, Constants.intakekD, Constants.kTimeoutMsin);
			intakePositionMotor.config_kF(Constants.kPIDLoopIdxin, 0, Constants.kTimeoutMsin);
		}
	}

	public double GetExtendError() {
		return intakePositionMotor.getSelectedSensorPosition() - Constants.intakesetpoint;
	}

	public void extendIntake() {
		if (!intakeExtended){
			intakePositionMotor.set(ControlMode.PercentOutput, -.5);
		}
		intakeExtended = true;
	}

	public void retractIntake() {
		// intakePositionMotor.set(-0.5);
		intakePositionMotor.set(ControlMode.PercentOutput, .5);
		intakeExtended = false;
	}

	public void setIntakeStatus(boolean status){
		intakeExtended = status;
	}

	public void stopRaising() {
		intakePositionMotor.set(ControlMode.PercentOutput, 0);
	}

	// public boolean isRaised() {
	// return upLimit.get();
	// }

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
			beltMotor.set(power);
		}
		if (triggerMotor != null) {
			triggerMotor.set(power);
		}
	}

	public boolean prepShot() {
		return true;
	}

	public void setIdle() {
		beltMotor.set(0);
	}

	
	public boolean hasBall() {
		return true;
	}
	
}