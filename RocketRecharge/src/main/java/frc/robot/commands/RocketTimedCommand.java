package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** A utility class or something for timed commands. IDK. */
public abstract class RocketTimedCommand extends CommandBase{
    private Timer timer = new Timer();
    private double timeout = -1;

    public void setTimeout(double timeout){
        timer.reset();
        timer.start();
        this.timeout = timeout;
    }

    /**
     * Returns if weare timed out or not
     * @return if timeout has been reached
     */
    public boolean isTimedOut(){
        if (timeout < 0 || timer.get() < timeout){
            return false;
        } 
        return true;
    }
}