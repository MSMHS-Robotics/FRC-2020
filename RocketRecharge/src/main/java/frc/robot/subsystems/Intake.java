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
	private WPI_TalonSRX beltMotor;
	private WPI_TalonSRX intakeMotor;
	private CANSparkMax triggerMotor;
	
	private boolean intakeExtended = false;

	// Shuffleboard
	private ShuffleboardTab Intaketab = Shuffleboard.getTab("Intake Tab");
	private NetworkTableEntry intakePosition = Intaketab.addPersistent("Intake Position", false).getEntry();

    /** An Intake subsystem class
	 * @param positionPort the port for the position motor
	 * @param beltPort the port for the belt motors
	 * @param intakePort the port for the intake (wheel shaft) motor
	 * @param triggerPort the port for the trigger motor
	 */
	public Intake(int positionPort, int beltPort, int intakePort, int triggerPort) {
		// Motors
		positionMotor = new WPI_TalonSRX(10);
		beltMotor = new WPI_TalonSRX(11);
		intakeMotor = new WPI_TalonSRX(15);
		triggerMotor = new CANSparkMax(12, MotorType.kBrushless);

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
	 * Returns if we have a ball somewhere in the robot using the IR sensors
	 * Currently does nothing because we don't have IR sensors
	 * @return true if we have a ball, false otherwise
	 */
	public boolean hasBall() {
		return true;
	}
	
	// Shuffleboard
	@Override
	public void periodic() {
		//intakeEncoderValue.setDouble(positionMotor.getSelectedSensorPosition());
		
		// ALSO WHAT IS THIS
		intakePosition.setBoolean(false);
	}
}