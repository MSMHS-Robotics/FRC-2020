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
    public static double ShooterkP = 7e-5;
    public static double ShooterkI = 6e-7;
    public static double ShooterkD = 0.001;
    public static double ShooterkIz = 0;
    public static double ShooterkFF = 6e-6;
    public static double ShooterkMaxOutput = 1;
    public static double ShooterkMinOutput = -1;

    //shooter PID division
    public static double ShooterkPdivide = 1;
    public static double ShooterkIdivide = 1;
    public static double ShooterkDdivide = 1;
    public static double ShooterkFFdivide = 10;

    //angle PID Constants
   
    public static double AnglekP = 0.15;
    public static double AnglekI = 0.0;
    public static double AnglekD = 1.0;
    public static double AnglekF = 0;

    //Preset Constants
    public static double TrenchAngle = 3300;
    public static double TrenchRPM = 3300;
    public static double TenFootAngle = 45; 
    public static double TenFootRPM = 3150;
    public static double LayupAngle = 10;
    public static double LayupRPM = 3200;

    public static double accelerationTolerance = 10;
    public static double RPMTolerance = 100;
    public static double AngleTolerance = 10;
    public static double[] visionPID = {0.029, 0.08, 0.0085};
    public static double[] visionPIDconstraints = {-0.5, 0.5};
    public static double[] headingPID = {0.15,0,0};
    public static double[] headingPIDconstraints = {-0.25, 0.25};
    public static double[] drivingPID = {1, 0, 0};
    public static double[] drivingPIDconstraints = {-0.5, 0.5};
    public static double[] encoderConstants = {0,0,0};
    public static double[] speed = {1};
    public static double alignAllowedError = 0.1;

    public static double[] drivingTolerance = {2, 5};
    public static double[] visionTolerance = {0, 5};
    public static double[] headingTolerance = {2, 5};
    public static double highDeadband = 0.95;
    public static double lowDeadband = 0.05;

    //how to calculate: push the robot a specific distance and divide 120 by the encoder count given
    public static double rightTickConstant = 2.1925;
    public static double leftTickConstant = -2.2005;
    //public static double[] headingIntegrator = {-0.5, 0.5};

    
    

    public void setVisionPID(double kp, double ksomething, double ksomethingelse) {
        visionPID[0] = kp;
        visionPID[1] = ksomething;
        visionPID[2] = ksomethingelse;
    }

    //climber (may need to look at this)
    public static  double climbSpeed = 0.3;
	public static double motorPosition = 0;//fix later
    public static double motorUp = 10;//fix later
    
    //climberPID
    public static double extendclimbkP = 0.0001;
    public static double extendclimbkI = 0.0001;
    public static double extendclimbkD = 0.0001;
    public static double distancesetpoint = 14000;
    public static final int kTimeoutMs = 30;
    public static final int kPIDLoopIdx = 0;

    //intake
    public static double intakesetpoint = 1000;
    public static double intakekP = 0.0001;
    public static double intakekI = 0.0001;
    public static double intakekD = 0.0001;
    public static final int kTimeoutMsin = 30;
    public static final int kPIDLoopIdxin = 0;


}
