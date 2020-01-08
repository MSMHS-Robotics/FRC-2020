/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Shooter extends SubsystemBase {
  WPI_TalonSRX shooterMotor;
  WPI_TalonSRX angleMotor;
  /**
   * Creates a new ExampleSubsystem.
   */
  public Shooter() {
    angleMotor.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
  }

  public boolean warmUp(double RPM) {
    shooterMotor.pidWrite(RPM);
    return true;
  }

  public boolean shooterAngle(double angle) {
    angleMotor.set(ControlMode.Position, angle);
    return Math.abs(angleMotor.getClosedLoopError()) < 1;
  }

  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}

/*command list for shooter

Warm up:
- connect with shooter subsystem
- speed up motor
- apply the current RPM to the shooter
ENDS WHEN- button is released
WHEN INTERUPTED- do nothing

Adjust power per position:
-connect with shooter subsystem
- read the current RPM of the motor
- have a preset shooter command that the shooter motor can stick with
   - detect vision alinement
   - run warmup
- once ready, then shoot

:-D

*/