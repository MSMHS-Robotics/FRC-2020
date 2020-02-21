package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Encoder;
import java.util.Map;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	// public CANSparkMax Climber;

	private Encoder encoder;
	private WPI_TalonSRX climberMotor;
	private Solenoid climberPistons1;
	private Solenoid climberPistons2;
	private Solenoid lock1;
	private Solenoid lock2;
	private Boolean isDeployed = false;
	private PIDController raisePID = new PIDController(0.0001, 0.00001, 0.00001);
	private PIDController climbPID = new PIDController(0.0001, 0.00001, 0.00001);
	private ShuffleboardTab ClimberTab = Shuffleboard.getTab("Climber Tab");
	private NetworkTableEntry climberMotorPwr = ClimberTab.addPersistent("Climber Motor Power", Constants.climberMotorPwr).getEntry();
	private NetworkTableEntry motorPosition = ClimberTab.addPersistent("Motor Position", 0).getEntry();
	private NetworkTableEntry fwdLimitSwitch = ClimberTab.addPersistent("Forward Limit Switch", 0).getEntry();
	private NetworkTableEntry revLimitSwitch = ClimberTab.addPersistent("Reverse Limit Switch", 0).getEntry();
	private NetworkTableEntry raiseKp = ClimberTab.addPersistent("raiseKp", Constants.raisePID[0]).getEntry();
	private NetworkTableEntry raiseKi = ClimberTab.addPersistent("raiseKi", Constants.raisePID[1]).getEntry();
	private NetworkTableEntry raiseKd = ClimberTab.addPersistent("raiseKd", Constants.raisePID[2]).getEntry();
	private NetworkTableEntry climbKp = ClimberTab.addPersistent("climbKp", Constants.climbPID[0]).getEntry();
	private NetworkTableEntry climbKi = ClimberTab.addPersistent("climbKi", Constants.climbPID[1]).getEntry();
	private NetworkTableEntry climbKd = ClimberTab.addPersistent("climbKd", Constants.climbPID[2]).getEntry();
	private NetworkTableEntry motorPosition = Climbertab.addPersistent("Motor Position", 0).getEntry();
  private NetworkTableEntry isitDeployed = Climbertab.addPersistent("is it Deployed", false).withWidget("Boolean Box").withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();

	public Climber() {
		// need to assign actual channel values
		// !==UPDATE==! --> values assigned now
		climberMotor = new WPI_TalonSRX(10);
		encoder = new Encoder(3, 4);
		raisePID.setSetpoint(5 * 2480);
		climbPID.setSetpoint(1000);
		climberPistons1 = new Solenoid(7);
		climberPistons2 = new Solenoid(6);
		lock1 = new Solenoid(1); //right channel?
		lock2 = new Solenoid(2); //right channel?
	}

	public boolean climberDeployed() {
		return isDeployed;
	}

	public void ClimberDeploy() {
		//if (climberPistons1 != null) {
			climberPistons1.set(false);
			isDeployed = true;
		//}
		//if (climberPistons2 != null) {
			climberPistons2.set(true);
			isDeployed = true;
		//}
		isitDeployed.setBoolean(true);
	}

	public void ClimberPistonsBackIn() {
		//if (climberPistons1 != null) {
			climberPistons1.set(true);
			isDeployed = false;
		//}
		//if (climberPistons2 != null) {
			climberPistons2.set(false);
			isDeployed = false;
		//}
		isitDeployed.setBoolean(false);
	}

	@Deprecated
	public void raiseClimber() {
		if (isDeployed) {
			if (climberMotor.isFwdLimitSwitchClosed() == 0) {
				if (climberMotor != null) {
					climberMotor.set(Constants.climberMotorPwr);
				}
			} else {
				if (climberMotor != null) {
					climberMotor.set(-0.01);
				}
			}
		}
	}

	public void raiseClimberPID() {
		if (isDeployed) {
			if (climberMotor.isFwdLimitSwitchClosed() == 0) {
				if (climberMotor != null) {
					climberMotor.set(raisePID.calculate(encoder.getDistance()));
				}
			} else {
				climberMotor.set(0);
			}
		}
	}
	
	public void climbWithPID() {
		if (isDeployed) {
			if (climberMotor.isRevLimitSwitchClosed() == 0) {
				if (climberMotor != null) {
					climberMotor.set(climbPID.calculate(encoder.getDistance()));
				}
			}
		}
	}

	public void lock() {
		lock1.set(true);
		lock2.set(true);
	}

	@Deprecated
	public void climbUp() {
		if (isDeployed) {
			if (climberMotor.isRevLimitSwitchClosed() == 0) {
				if (climberMotor != null) {
					climberMotor.set(-Constants.climberMotorPwr);
				}
			} else { //we are touching the rev
				if (climberMotor != null) { //motor exists
					climberMotor.set(0); //sfwd it
				}
			}
		}
	}

	public void climbUsingStick(double x) {
		if (climberMotor != null) {
			climberMotor.set(x);
		}
	}

	public void climb() {
		if (climberMotor != null) {
			climberMotor.set(0);
		}
		climberMotor.set(-Constants.climberMotorPwr);
	}
	
	public void climberStop() {
		climberMotor.set(0);
	}

	@Override
	public void periodic() {
		//off get ready
		double TempClimberSpeed = climberMotorPwr.getDouble(0.5);
		if (TempClimberSpeed != Constants.climberMotorPwr) {
			Constants.climberMotorPwr = TempClimberSpeed;
			climberMotorPwr.setDouble(Constants.climberMotorPwr);
		}

		double tempRP = raiseKp.getDouble(Constants.raisePID[0]);
		if (Constants.raisePID[0] != tempRP) {
			Constants.raisePID[0] = tempRP;
			raisePID.setP(Constants.raisePID[0]);
		}

		double tempRI = raiseKi.getDouble(Constants.raisePID[1]);
		if (Constants.raisePID[1] != tempRI) {
			Constants.raisePID[1] = tempRI;
			raisePID.setI(Constants.raisePID[1]);
		}

		double tempRD = raiseKd.getDouble(Constants.raisePID[2]);
		if (Constants.raisePID[2] != tempRD) {
			Constants.raisePID[2] = tempRD;
			raisePID.setD(Constants.raisePID[2]);
		}

		double tempCP = climbKp.getDouble(Constants.climbPID[0]);
		if (Constants.climbPID[0] != tempCP) {
			Constants.climbPID[0] = tempCP;
			climbPID.setP(Constants.climbPID[0]);
		}

		double tempCI = climbKi.getDouble(Constants.climbPID[1]);
		if (Constants.climbPID[1] != tempCI) {
			Constants.climbPID[1] = tempCI;
			climbPID.setI(Constants.climbPID[1]);
		}

		double tempCD = climbKd.getDouble(Constants.climbPID[2]);
		if (Constants.climbPID[2] != tempCD) {
			Constants.climbPID[2] = tempCD;
			climbPID.setD(Constants.climbPID[2]);
		}
		fwdLimitSwitch.setDouble(climberMotor.isFwdLimitSwitchClosed());
		revLimitSwitch.setDouble(climberMotor.isRevLimitSwitchClosed());
		motorPosition.setDouble(encoder.getDistance());
	}
}