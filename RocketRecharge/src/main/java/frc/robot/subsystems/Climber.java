package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;
import com.revrobotics.CANSparkMax;

/**
 * Do NOT add any static variables to this class, or any initialization at all. Unless you know what
 * you are doing, do not modify this file except to change the parameter class to the startRobot
 * call.
 */
public class Climber extends SubsystemBase {

    private CANSparkMax Climber = CANSparkMax(7);

    public Climber(){
        
        Climber.set(1);

    }

  

  /**
   * Main initialization function. Do not perform any initialization here.
   *
   * <p>If you change your main robot class, change the parameter type.
   */
  
  }
}
