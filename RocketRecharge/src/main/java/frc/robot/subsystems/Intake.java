package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
	private CANSparkMax beltMotor1;
	private CANSparkMax beltMotor2;
	private CANSparkMax triggerMotor;

	//TODO make sure this runs at a constant encoder-based speed and pids and stuff
	public Intake() {
		beltMotor1 = new CANSparkMax(15, MotorType.kBrushless);
		beltMotor2 = new CANSparkMax(15, MotorType.kBrushless);
		triggerMotor = new CANSparkMax(30, MotorType.kBrushless);
	}

	//TODO documentation
	public void feed(double power) {
		beltMotor1.set(power);
		beltMotor2.set(power);
		triggerMotor.set(power);
	}
}