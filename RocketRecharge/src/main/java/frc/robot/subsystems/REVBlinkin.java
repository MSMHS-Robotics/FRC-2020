package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class REVBlinkin extends SubsystemBase {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	// public CANSparkMax Climber;

	private Spark blinkin;

	public REVBlinkin() {
		blinkin = new Spark(0);
	}

	public void set1() {
		blinkin.set(1);
	}

	public void setNegative1() {
		blinkin.set(1);
	}
}

