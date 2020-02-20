/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    //shooter PID constants
    public static double ShooterkP = 5e-5;
    public static double ShooterkI = 1e-6;
    public static double ShooterkD = 0;
    public static double ShooterkIz = 0;
    public static double ShooterkFF = 0;
    public static double ShooterkMaxOutput = 1;
    public static double ShooterkMinOutput = -1;
  

    //angle PID Constants
    public static final int kTimeoutMs = 30;
    public static final int kPIDLoopIdx = 0;
    public static double AnglekP = 0.15;
    public static double AnglekI = 0.0;
    public static double AnglekD = 1.0;
    public static double AnglekF = 0;

    //Preset Constants
    public static double TrenchAngle = 35;
    public static double TrenchRPM = 6;
    public static double TenFootAngle = 45; 
    public static double TenFootRPM = 10;
    public static double LayupAngle = 10;
    public static double LayupRPM = 5;

    public static double RPMTolerance = 100;
    public static double AngleTolerance = 10;
    public static double[] visionPID = {0.03, 0, 0};
    public static double[] visionPIDconstraints = {-0.5, 0.5};
    public static double[] headingPID = {0.15,0,0};
    public static double[] headingPIDconstraints = {-0.25, 0.25};
    public static double[] drivingPID = {1, 0, 0};
    public static double[] drivingPIDconstraints = {-0.5, 0.5};
    public static double[] encoderConstants = {0,0,0};
    public static double[] speed = {1};
    public static double alignAllowedError = 0.1;

    public static double[] drivingTolerance = {2, 5};
    public static double[] visionTolerance = {2, 5};
    public static double[] headingTolerance = {2, 5};

    //how to calculate: push the robot a specific distance and divide 120 by the encoder count given
    public static double rightTickConstant = 2.1925;
    public static double leftTickConstant = -2.2005;
    //public static double[] headingIntegrator = {-0.5, 0.5};

    
    

    public void setVisionPID(double kp, double ksomething, double ksomethingelse) {
        visionPID[0] = kp;
        visionPID[1] = ksomething;
        visionPID[2] = ksomethingelse;
    }
    public static double climberMotorPwr = 0.03;
	public static double motorPosition = 0;//fix later
}
