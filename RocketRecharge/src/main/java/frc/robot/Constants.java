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
    public static double[] visionPID = {0.03, 0, 0};
    public static double[] visionPIDconstraints = {-0.5, 0.5};
    public static double[] headingPID = {0.15,0,0};
    public static double[] headingPIDconstraints = {-0.25, 0.25};
    public static double[] drivingPID = {1, 0, 0};
    public static double[] drivingPIDconstraints = {-0.5, 0.5};
    public static double[] encoderConstants = {0,0,0};
    public static double[] speed = {1};

    public static double[] headingTolerance = {2, 5};
    public static double[] headingIntegrator = {-0.5, 0.5};

    
    

    public void setVisionPID(double kp, double ksomething, double ksomethingelse) {
        visionPID[0] = kp;
        visionPID[1] = ksomething;
        visionPID[2] = ksomethingelse;
    }
}
