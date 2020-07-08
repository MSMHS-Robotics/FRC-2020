package frc.robot.subsystems;

// Motors stuff
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

// Shuffleboard stuff
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import java.util.Map;

// Other
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/** An Intake subsystem class */
public class Intake extends SubsystemBase {
	private WPI_TalonSRX positionMotor;
	private WPI_TalonSRX intakeMotor;
	private WPI_TalonSRX beltMotor;
	private CANSparkMax triggerMotor;
	
	private boolean intakeExtended = false;

	/*
	// Sensor stuff
	private DigitalInput triggerSensor;
	private DigitalInput detector1;
	private DigitalInput detector2;
	private DigitalInput detector3;
	private DigitalInput detector4;
	private DigitalInput detector5;
	private DigitalInput[] detectors;
	*/

	// Shuffleboard
	private ShuffleboardTab Intaketab = Shuffleboard.getTab("Intake Tab");
	private NetworkTableEntry intakePosition = Intaketab.addPersistent("Intake Position", false).getEntry();

	/*
	private NetworkTableEntry irAll = Intaketab.addPersistent("Has Any Ball", false).withWidget("Boolean Box").withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();
	private NetworkTableEntry irTrigger = Intaketab.addPersistent("Chamber", false).withWidget("Boolean Box").withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();
	private NetworkTableEntry ir1 = Intaketab.addPersistent("Ball 1", false).withWidget("Boolean Box").withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();
	private NetworkTableEntry ir2 = Intaketab.addPersistent("Ball 2", false).withWidget("Boolean Box").withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();
	private NetworkTableEntry ir3 = Intaketab.addPersistent("Ball 3", false).withWidget("Boolean Box").withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();
	private NetworkTableEntry ir4 = Intaketab.addPersistent("Ball 4", false).withWidget("Boolean Box").withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();
	private NetworkTableEntry ir5 = Intaketab.addPersistent("Ball 5", false).withWidget("Boolean Box").withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();
	
	private NetworkTableEntry[] irs;
	*/

	private NetworkTableEntry intakekP = Intaketab.addPersistent("IntakekP", Constants.intakekP).getEntry();
	private NetworkTableEntry intakekI = Intaketab.addPersistent("IntakekI", Constants.intakekI).getEntry();
	private NetworkTableEntry intakekD = Intaketab.addPersistent("IntakekD", Constants.intakekD).getEntry();
	private NetworkTableEntry intakeSetpoint = Intaketab.addPersistent("Intake Setpoint", Constants.intakesetpoint).getEntry();
	private NetworkTableEntry intakeError = Intaketab.addPersistent("Intake Error", 0).getEntry();
	private NetworkTableEntry intakeEncoderValue = Intaketab.addPersistent("IntakeEncoder", 0).getEntry();

	private ShuffleboardTab toggleTab = Shuffleboard.getTab("Toggle Tab");
	private NetworkTableEntry toggleDiag = toggleTab.add("Comp Mode?", false).withWidget(BuiltInWidgets.kToggleButton).getEntry();


    /** An Intake subsystem class */
	public Intake() {
		// Motors
		intakeMotor = new WPI_TalonSRX(15);
		beltMotor = new WPI_TalonSRX(11);
		triggerMotor = new CANSparkMax(12, MotorType.kBrushless);
		positionMotor = new WPI_TalonSRX(10);

		//triggerSensor = new DigitalInput(0);
		
		/*
		// Sensors
		irs = new NetworkTableEntry[] { ir1, ir2, ir3, ir4, ir5, irTrigger };
		detector1 = new DigitalInput(4);
		detector2 = new DigitalInput(5);
		detector3 = new DigitalInput(6);
		detector4 = new DigitalInput(7);
		detector5 = new DigitalInput(8);
		detectors = new DigitalInput[] { detector1, detector2, detector3, detector4, detector5, triggerSensor };
		*/
		
		positionMotor.set(ControlMode.PercentOutput, 0);
		positionMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx, Constants.kTimeoutMsin);
		positionMotor.setSelectedSensorPosition(0);
		positionMotor.setSensorPhase(true);
		positionMotor.setInverted(true);

		if (positionMotor != null) {
			positionMotor.configAllowableClosedloopError(0, Constants.kPIDLoopIdxin, Constants.kTimeoutMsin);
		}
		
		// Config the peak and nominal outputs
		if (positionMotor != null) {
			positionMotor.configNominalOutputForward(0, Constants.kTimeoutMsin);
			positionMotor.configNominalOutputReverse(0, Constants.kTimeoutMsin);
			positionMotor.configPeakOutputForward(1, Constants.kTimeoutMsin);
			positionMotor.configPeakOutputReverse(-1, Constants.kTimeoutMsin);
		}

		// Config the Velocity closed loop gains in slot0
		if (positionMotor != null) {
			positionMotor.config_kP(Constants.kPIDLoopIdxin, Constants.intakekP, Constants.kTimeoutMsin);
			positionMotor.config_kI(Constants.kPIDLoopIdxin, Constants.intakekI, Constants.kTimeoutMsin);
			positionMotor.config_kD(Constants.kPIDLoopIdxin, Constants.intakekD, Constants.kTimeoutMsin);
			positionMotor.config_kF(Constants.kPIDLoopIdxin, 0, Constants.kTimeoutMsin);
		}
	}

	/**
	 * Gets error
	 * @return the diff between encoder and setpoint
	 */
	public double GetExtendError() {
		return positionMotor.getSelectedSensorPosition() - Constants.intakesetpoint;
	}

	/** Extends intake if it isn't already extended, otherwise does nothing */
	public void extendIntake() {
		if (!intakeExtended){
			positionMotor.set(ControlMode.PercentOutput, -0.5);
			intakeExtended = true;
		}
	}

	/** Raises intake */
	public void retractIntake() {
		positionMotor.set(ControlMode.PercentOutput, 0.5);
		intakeExtended = false;
	}

	/** 
	 * Sets the intakeExtended flag
	 * For use in autons
	 * @param status the value to set the status of the intake to
	 */
	public void setIntakeStatus(boolean status){
		intakeExtended = status;
	}

	/** Stops the intake position motor */
	public void stopRaising() {
		positionMotor.set(ControlMode.PercentOutput, 0);
	}

	/**
	 * Runs the intake motor at the specified speed. Can be negative for reverse direction
	 * @param power the speed at which to run the motor
	 */
	public void runIntake(double power) {
		if (intakeMotor != null) {
			intakeMotor.set(power);
		}
	}

	/**
	 * Runs the indexer motor at the specified speed. Can be negative for reverse direction
	 * @param power the speed at which to run the motor
	 */
	public void runIndexer(double power) {
		if (beltMotor != null) {
			beltMotor.set(power);
		}
	}

	/**
	 * Runs the trigger motor at the specified speed
	 * @param power the speed at which to run the motor
	 */
	public void runTrigger(double power) {
		if (triggerMotor != null) {
			triggerMotor.set(power);
		}
	}

	/**
	 * Runs the intake and indexer motors at the specified speed. Can be negative for reverse direction
	 * @param power the speed at which to run the motor
	 */
	public void feed(double power) {
		this.runIntake(power);
		this.runTrigger(power);
	}

	/**
	 * Leftover broken dreams of using sensors
	 */
	public void setIdle() {
		if (!triggerSensor.get()) {
			this.feed(0);
		} else {
			this.feed(0);
		}
	}

	/**
	 * Returns if we have a ball somewhere in the robot using the IR sensors
	 * Currently does nothing because we don't have IR sensors
	 * @return true if we have a ball, false otherwise
	 */
	public boolean hasBall() {
		/*
		for (int x = 0; x < detectors.length; x++) {
			irs[x].setBoolean(detectors[x].get()); // complete jank
			if (detectors[x].get()) {
				irAll.setBoolean(true);
				return true;
			}
		}
		return false;
		*/
		return true;
	}
	
	// Shuffleboard
	@Override
	public void periodic() {
		intakeEncoderValue.setDouble(positionMotor.getSelectedSensorPosition());
		
		// ALSO WHAT IS THIS
		intakePosition.setBoolean(false);

		// Wait why do we set to 0 constantly?
		//TODO
		this.setIdle();

		// If comp mode is true
		if(toggleDiag.getBoolean(false)) { 
			continue;
		}

		double tempINP = intakekP.getDouble(Constants.intakekP);
		if (Constants.intakekP != tempINP && positionMotor != null) {
			Constants.intakekP = tempINP;
			positionMotor.config_kP(Constants.kPIDLoopIdxin, Constants.intakekP, Constants.kTimeoutMsin);
		}

		double tempINI = intakekI.getDouble(Constants.intakekI);
		if (Constants.intakekI != tempINI && positionMotor != null) {
			Constants.intakekI = tempINI;
			positionMotor.config_kI(Constants.kPIDLoopIdxin, Constants.intakekI, Constants.kTimeoutMsin);
		}

		double tempIND = intakekD.getDouble(Constants.intakekD);
		if (Constants.intakekD != tempIND && positionMotor != null) {
			Constants.intakekD = tempIND;
			positionMotor.config_kD(Constants.kPIDLoopIdxin, Constants.intakekD, Constants.kTimeoutMsin);
		}

		double tempintakeSetpoint = intakeSetpoint.getDouble(Constants.intakesetpoint);
		if (Constants.intakesetpoint != tempintakeSetpoint) {
			Constants.intakesetpoint = tempintakeSetpoint;
		}
	}
}