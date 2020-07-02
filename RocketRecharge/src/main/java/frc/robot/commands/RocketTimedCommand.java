package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** A utility class or something for timed commands. IDK. */
public abstract class RocketTimedCommand extends CommandBase {
    private Timer timer = new Timer();
    private double timeout = -1;

    /** Sets the timeout
     * @param timeout a double representing the timeout
     */
    public void setTimeout(double timeout){
        timer.reset();
        timer.start();
        this.timeout = timeout;
    }

    /**
     * Returns if we are timed out or not
     * @return a boolean representing if the timeout has been reached
     */
    public boolean isTimedOut(){
        if (timeout < 0 || timer.get() < timeout){
            return false;
        } 
        return true;
    }
}