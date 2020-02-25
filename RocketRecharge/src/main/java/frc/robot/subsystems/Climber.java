package frc.robot.subsystems;

import java.util.Map;
import java.util.function.BooleanSupplier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	// public CANSparkMax Climber;

	private DigitalInput bottomLimitSwitch, topLimitSwitch;
	private Encoder encoder;
	private WPI_TalonSRX climberMotor;
	private Solenoid climberPistons1;
	private Solenoid climberPistons2;
	private Solenoid Latch1, Latch2;
	private double distanceSetpoint;
	private boolean isDeployed = false;
	private boolean isRaised = false;
	private PIDController extendclimbPID = new PIDController(Constants.extendclimbkP, Constants.extendclimbkI,
			Constants.extendclimbkD);

	// shuffleboard
	private ShuffleboardTab Climbertab = Shuffleboard.getTab("Climber Tab");
	private NetworkTableEntry climbSpeed = Climbertab.addPersistent("climbSpeed", Constants.climbSpeed).getEntry();
	private NetworkTableEntry motorPosition = Climbertab.addPersistent("Motor Position", 0).getEntry();
	private NetworkTableEntry motorUp = Climbertab.addPersistent("motorUp", Constants.motorUp).getEntry();
	private NetworkTableEntry extendclimbkP = Climbertab.addPersistent("extendclimbkP", Constants.extendclimbkP)
			.getEntry();
	private NetworkTableEntry extendclimbkI = Climbertab.addPersistent("extendclimbkI", Constants.extendclimbkI)
			.getEntry();
	private NetworkTableEntry extendclimbkD = Climbertab.addPersistent("extendclimbkD", Constants.extendclimbkD)
			.getEntry();
	private NetworkTableEntry DistanceSetpoint = Climbertab
			.addPersistent("DistanceSetpoint", Constants.distancesetpoint).getEntry();
	private NetworkTableEntry ExtendError = Climbertab.addPersistent("ExtendError", 0).getEntry();
	private NetworkTableEntry isitDeployed = Climbertab.addPersistent("is it Deployed", false).withWidget("Boolean Box")
			.withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();

	public Climber() {
		bottomLimitSwitch = new DigitalInput(9);
		// topLimitSwitch = new DigitalInput(2);
		// need to assign actual channel values
		// !==UPDATE==! --> values assigned now
		climberMotor = new WPI_TalonSRX(10);
		climberMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,Constants.kPIDLoopIdx,Constants.kTimeoutMs);
		climberMotor.setSelectedSensorPosition(0);
		climberMotor.setSensorPhase(true);
		climberMotor.setInverted(true);
		// slidePID.setSetpoint(4);
		// climberPID.setSetpoint(0.2);
		Latch1 = new Solenoid(4);
		Latch2 = new Solenoid(5);
		climberPistons1 = new Solenoid(7);
		climberPistons2 = new Solenoid(6);

		ClimberUnlatch();

		if (climberMotor != null) {
			climberMotor.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
		}
		// Config the peak and nominal outputs
		if (climberMotor != null) {
			climberMotor.configNominalOutputForward(0, Constants.kTimeoutMs);
			climberMotor.configNominalOutputReverse(0, Constants.kTimeoutMs);
			climberMotor.configPeakOutputForward(1, Constants.kTimeoutMs);
			climberMotor.configPeakOutputReverse(-1, Constants.kTimeoutMs);
		}

		// Config the Velocity closed loop gains in slot0
		if (climberMotor != null) {
			climberMotor.config_kP(Constants.kPIDLoopIdx, Constants.extendclimbkP, Constants.kTimeoutMs);
			climberMotor.config_kI(Constants.kPIDLoopIdx, Constants.extendclimbkI, Constants.kTimeoutMs);
			climberMotor.config_kD(Constants.kPIDLoopIdx, Constants.extendclimbkD, Constants.kTimeoutMs);
			climberMotor.config_kF(Constants.kPIDLoopIdx, 0, Constants.kTimeoutMs);
		}
	}

	public void ClimberDeploy() {
		if (climberPistons1 != null) {
			climberPistons1.set(false);
			
		}
		if (climberPistons2 != null) {
			climberPistons2.set(true);
			

		}
	}

	public void ClimberLatch(){
		if (Latch1 != null){
			Latch1.set(true);
		}
		if (Latch2 != null){
			Latch2.set(false);
		}
	}

	public void ClimberUnlatch(){
		if (Latch1 != null){
			Latch1.set(false);
		}
		if (Latch2 != null){
			Latch2.set(true);
		}
	}

	public double GetExtendError(){
		return  climberMotor.getSelectedSensorPosition()-Constants.distancesetpoint;
	}

	public void ClimberPistonsBackIn() {
		if (climberPistons1 != null) {
			climberPistons1.set(true);
		}
		if (climberPistons2 != null) {
			climberPistons2.set(false);
		}
		
	}

	public void raiseClimberPID() {
		//if (isDeployed) {
			if (climberMotor != null) {
				climberMotor.set(ControlMode.Position, Constants.distancesetpoint);
				isRaised = true;
			}
		//}
	}

	public boolean climbUp() {
		//if (isDeployed) {
			if (climberMotor != null) {
				climberMotor.set(ControlMode.PercentOutput,-Constants.climbSpeed);
				isRaised = false;
			}
		//}
		return !bottomLimitSwitch.get();
	}

	public BooleanSupplier isRaised(){
		BooleanSupplier bSupplier = () -> isRaised;
		return bSupplier;
	}

	public void climbUsingStick(double x) {
		if (climberMotor != null) {
			climberMotor.set(ControlMode.PercentOutput, x);
		}
	}

	public void stop() {
		if (climberMotor != null) {
			climberMotor.set(0);
		}
	}
	
	public void stopRaise() {
		climberMotor.set(0);
	}

	@Override
	public void periodic() {

		ExtendError.setDouble(GetExtendError());

		isDeployed = !climberPistons1.get() && climberPistons2.get();
		isitDeployed.setBoolean(isDeployed);

		if (!bottomLimitSwitch.get()){
			climberMotor.setSelectedSensorPosition(0);
		}

		double TempClimberSpeed = climbSpeed.getDouble(0.5);
		if (TempClimberSpeed != Constants.climbSpeed) {
			Constants.climbSpeed = TempClimberSpeed;
			climbSpeed.setDouble(Constants.climbSpeed);
		}
		motorPosition.setDouble(climberMotor.getSelectedSensorPosition());

		double tempECP = extendclimbkP.getDouble(Constants.extendclimbkP);
		if (Constants.extendclimbkP != tempECP && climberMotor != null) {
			Constants.extendclimbkP = tempECP;
			climberMotor.config_kP(Constants.kPIDLoopIdx, Constants.extendclimbkP, Constants.kTimeoutMs);
		}

		double tempECI = extendclimbkI.getDouble(Constants.extendclimbkI);
		if (Constants.extendclimbkI != tempECI && climberMotor != null) {
			Constants.extendclimbkI = tempECI;
			climberMotor.config_kI(Constants.kPIDLoopIdx, Constants.extendclimbkI, Constants.kTimeoutMs);
		}

		double tempECD = extendclimbkD.getDouble(Constants.extendclimbkD);
		if (Constants.extendclimbkD != tempECD && climberMotor != null) {
			Constants.extendclimbkD = tempECD;
			climberMotor.config_kD(Constants.kPIDLoopIdx, Constants.extendclimbkD, Constants.kTimeoutMs);
		}

		double tempDistanceSetpoint = DistanceSetpoint.getDouble(Constants.distancesetpoint);
		if (Constants.distancesetpoint != tempDistanceSetpoint) {
			Constants.distancesetpoint = tempDistanceSetpoint;
		}
	}
}		