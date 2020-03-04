package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {

	private WPI_TalonSRX climberMotor;
	
	// shuffleboard
	private ShuffleboardTab climberTab = Shuffleboard.getTab("Climber Tab");
	private NetworkTableEntry climbSpeed = climberTab.addPersistent("climbSpeed", Constants.climbSpeed).getEntry();
	private NetworkTableEntry motorPosition = climberTab.addPersistent("Motor Position", 0).getEntry();
	private NetworkTableEntry extendclimbkP = climberTab.addPersistent("extendclimbkP", Constants.extendclimbkP).getEntry();
	private NetworkTableEntry extendclimbkI = climberTab.addPersistent("extendclimbkI", Constants.extendclimbkI).getEntry();
	private NetworkTableEntry extendclimbkD = climberTab.addPersistent("extendclimbkD", Constants.extendclimbkD).getEntry();
	private NetworkTableEntry DistanceSetpoint = climberTab.addPersistent("DistanceSetpoint", Constants.distancesetpoint).getEntry();
	private NetworkTableEntry ExtendError = climberTab.addPersistent("ExtendError", 0).getEntry();
	
	public Climber() {
		climberMotor = new WPI_TalonSRX(10);
		climberMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,Constants.kPIDLoopIdx,Constants.kTimeoutMs);
		climberMotor.setSelectedSensorPosition(0);
		climberMotor.setSensorPhase(true);
		climberMotor.setInverted(true);

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
	
	public double GetExtendError(){
		return climberMotor.getSelectedSensorPosition() - Constants.distancesetpoint;
	}

	public void raiseClimberPID() {
		if (climberMotor != null) {
			climberMotor.set(ControlMode.Position, Constants.distancesetpoint);
		}
	}

	public void climbUp() {
		if (climberMotor != null) {
			climberMotor.set(ControlMode.PercentOutput,-Constants.climbSpeed);
		}
	}

	public void stop() {
		if (climberMotor != null) {
			climberMotor.set(0);
		}
	}
	
	@Override
	public void periodic() {
		ExtendError.setDouble(GetExtendError());
		motorPosition.setDouble(climberMotor.getSelectedSensorPosition());
	
		double TempClimberSpeed = climbSpeed.getDouble(0.5);
		if (TempClimberSpeed != Constants.climbSpeed) {
			Constants.climbSpeed = TempClimberSpeed;
			climbSpeed.setDouble(Constants.climbSpeed);
		}

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