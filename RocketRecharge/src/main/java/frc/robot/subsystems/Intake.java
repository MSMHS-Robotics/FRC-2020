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
	//private WPI_TalonSRX triggerMotor;
	private CANSparkMax triggerMotor;

	private DigitalInput triggerSensor;
	// private DigitalInput upLimit;
	// private DigitalInput bottomLimit;
	private DigitalInput detector1;
	private DigitalInput detector2;
	private DigitalInput detector3;
	private DigitalInput detector4;
	private DigitalInput detector5;
	private DigitalInput[] detectors;

	private boolean intakeExtended = false;

	double intakeUp = 0;

	private ShuffleboardTab Intaketab = Shuffleboard.getTab("Intake Tab");
	private NetworkTableEntry intakePosition = Intaketab.addPersistent("Intake Position", false).getEntry();

	private NetworkTableEntry irAll = Intaketab.addPersistent("Has Any Ball", false).withWidget("Boolean Box")
			.withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();
	private NetworkTableEntry irTrigger = Intaketab.addPersistent("Chamber", false).withWidget("Boolean Box")
			.withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();
	private NetworkTableEntry ir1 = Intaketab.addPersistent("Ball 1", false).withWidget("Boolean Box")
			.withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();
	private NetworkTableEntry ir2 = Intaketab.addPersistent("Ball 2", false).withWidget("Boolean Box")
			.withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();
	private NetworkTableEntry ir3 = Intaketab.addPersistent("Ball 3", false).withWidget("Boolean Box")
			.withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();
	private NetworkTableEntry ir4 = Intaketab.addPersistent("Ball 4", false).withWidget("Boolean Box")
			.withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();
	private NetworkTableEntry ir5 = Intaketab.addPersistent("Ball 5", false).withWidget("Boolean Box")
			.withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();
	private NetworkTableEntry[] irs;

	private NetworkTableEntry intakekP = Intaketab.addPersistent("IntakekP", Constants.intakekP).getEntry();
	private NetworkTableEntry intakekI = Intaketab.addPersistent("IntakekI", Constants.intakekI).getEntry();
	private NetworkTableEntry intakekD = Intaketab.addPersistent("IntakekD", Constants.intakekD).getEntry();
	private NetworkTableEntry intakeSetpoint = Intaketab.addPersistent("Intake Setpoint", Constants.intakesetpoint)
			.getEntry();
	private NetworkTableEntry intakeError = Intaketab.addPersistent("Intake Error", 0).getEntry();
	private NetworkTableEntry intakeEncoderValue = Intaketab.addPersistent("IntakeEncoder", 0).getEntry();

	// intake PID

	public Intake() {
		// uncomment once pneumatics attatched
		irs = new NetworkTableEntry[] { ir1, ir2, ir3, ir4, ir5, irTrigger };
		intakeMotor = new WPI_TalonSRX(15); // our motors
		beltMotor = new WPI_TalonSRX(11);
		triggerMotor = new CANSparkMax(12, MotorType.kBrushless); // change?
		intakePositionMotor = new WPI_TalonSRX(10);

		triggerSensor = new DigitalInput(0);
		// upLimit = new DigitalInput(2);
		// bottomLimit = new DigitalInput(3);

		detector1 = new DigitalInput(4);
		detector2 = new DigitalInput(5);
		// detector3 = new DigitalInput(6);
		detector4 = new DigitalInput(7);
		// detector5 = new DigitalInput(8);
		detectors = new DigitalInput[] { detector1, detector2, detector3, detector4, detector5, triggerSensor };

		
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
		return false;
	}

	public void setIdle() {
		if (!triggerSensor.get()) {
			if (beltMotor != null) {
				beltMotor.set(0);
			}
			if (triggerMotor != null) {
				triggerMotor.set(0);
			}
		} else {
			this.feed(0);
		}
	}

	
	public boolean hasBall() {
		/*for (int x = 0; x < detectors.length; x++) {
			irs[x].setBoolean(detectors[x].get()); // complete jank
			if (detectors[x].get()) {
				irAll.setBoolean(true);
				return true;
			}
		}*/
		return true;
	}
	
	@Override
	public void periodic() {
		intakeEncoderValue.setDouble(intakePositionMotor.getSelectedSensorPosition());
		intakePosition.setBoolean(false);
		this.setIdle();

		double tempINP = intakekP.getDouble(Constants.intakekP);
		if (Constants.intakekP != tempINP && intakePositionMotor != null) {
			Constants.intakekP = tempINP;
			intakePositionMotor.config_kP(Constants.kPIDLoopIdxin, Constants.intakekP, Constants.kTimeoutMsin);
		}

		double tempINI = intakekI.getDouble(Constants.intakekI);
		if (Constants.intakekI != tempINI && intakePositionMotor != null) {
			Constants.intakekI = tempINI;
			intakePositionMotor.config_kI(Constants.kPIDLoopIdxin, Constants.intakekI, Constants.kTimeoutMsin);
		}

		double tempIND = intakekD.getDouble(Constants.intakekD);
		if (Constants.intakekD != tempIND && intakePositionMotor != null) {
			Constants.intakekD = tempIND;
			intakePositionMotor.config_kD(Constants.kPIDLoopIdxin, Constants.intakekD, Constants.kTimeoutMsin);
		}

		double tempintakeSetpoint = intakeSetpoint.getDouble(Constants.intakesetpoint);
		if (Constants.intakesetpoint != tempintakeSetpoint) {
			Constants.intakesetpoint = tempintakeSetpoint;
		}
	}
}