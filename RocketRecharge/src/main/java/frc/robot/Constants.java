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
    public static double ShootermaxRPM = 5700;

    //angle PID Constants
    public static final int kTimeoutMs = 30;
    public static final int kPIDLoopIdx = 0;
    public static double AnglekF = 0;
    public static double AnglekP = 0.15;
    public static double AnglekI = 0.0;
    public static double AnglekD = 1.0;
}
