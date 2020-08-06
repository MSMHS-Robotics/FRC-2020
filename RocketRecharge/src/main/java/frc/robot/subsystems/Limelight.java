package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants;

/** A Limelight class to separate vision stuff from Drivetrain.java to make it smaller */
public class Limelight extends SubsystemBase {
    private boolean zoom = false;
    private boolean aligned = false;
    private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    
    /** Turns the Limelight LEDs off */
    private void ledsOff() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    }

    /** Turns the Limelight LEDs on */
    private void ledsOn() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
    }
    
    /**
     * Returns if we are aligned with the vision target or not
     * @return if we are aligned or not
     */
    public boolean isAligned() {
        return aligned;
    }
    
    /** Toggles whether we are using 1x or 2x zoom on the Limelight. Changes both the flag and the vision pipeline */
    public void toggleVisionZoom() {
        zoom = !zoom;
        if(zoom) {
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1);
        }
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);
    }

    /** Changes Limelight to use 2x hardware zoom, aka "snipa" */
    private void zoom() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1);
        zoom = true;
    }
    
    /** The opposite of zoom(), changes to normal 1x (i.e. none) zoom */
    private void unZoom() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);
        zoom = false;
    }
    
    /**
     * Returns if we are zooming or not
     * @return if we are using 2x or 1x hardware zoom on limelight
     */
    public boolean isZoomed() {
        return zoom;
    }
    
    /**
     * Returns the x-offset from the crosshair to the target. Used in the {@link Drivetrain} subsystem to align to the target.
     * @return the x offset
     */
    public double getXOffset() {  
        ledsOn();
        
        if (table.getEntry("tv").getDouble(0) == 1) { // if there are any targets in view
            double x_offset = table.getEntry("tx").getDouble(0); // x offset
            if (x_offset < Constants.visionThreshold) { // if we are close enough to be called aligned
                aligned = true; // so as to break out of loop
            }
            return x_offset; // return x offset
        }
    }

    /**
     * Uses the Limelight to get distance to goal
     * Does this using an equation taken from the limelight docs
     * @return the distance to the target in inches. If distance is -1 then something went wrong
     */
    private double getDist() {
        unZoom(); // so zoom won't affect results
        ledsOn(); // so we can see
        
        // taken from limelight docs (equation is d = (h2-h1) / tan(a1+a2))
        return 70.25 / Math.tan(10 + table.getEntry("ty").getDouble(-1)); 
        
        // the 70.25 is height of center of the circle (in the hexagon frame) in inches minus how high lens is off the groun (20 inches) (h2 - h1)
        // 10 is angle limelight lens is at (a1)
        // ty.getDouble gives the y value of the target from LL's perspective
        // -1 is a default value to return, being unsuccesful
    }
    
    /**
     * Uses some arcane magical equation to return the RPM we need based on the distance to the target
     * Found it on SE somewhere (can't remember now where I found it, so...), but not really sure if it's correct
     * @return the RPM needed to shoot the PCs to the goal
    */
    public double getNeededRPM() {
        double d = this.getDist(); //distance to target
        double angle = Contstants.shooterAngle; //angle we are shooting at
        double g = Contstants.accel_due_to_gravity; //acceleration due to gravity
        double h = Constants.limelight_height; //height above ground we are shooting at
        
        // the 60 is to convert RPM into seconds to get m/s for velocity. RPM / 60 * wheel radius = tangential velocity
        // wheel radius is 2 because we are using the blue 4" wheels
        // the ball rotates to match the tangential velocity so center of mass rotates to have .5 the velocity. so the * 2 of tangent_v equation cancels the /2 of this so you don't see it in the below actual code equation
        // units are in inches and degrees and seconds and stuff
        return 60 * ((1 / Math.cos(angle) * Math.sqrt((0.5 * (d * d) * g) / (d * Math.tan(angle) + h))));
    }
}