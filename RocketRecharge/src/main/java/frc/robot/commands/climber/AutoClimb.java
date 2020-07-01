package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Climber;

/** A command to automatically climb up and stop */
public class AutoClimb extends SequentialCommandGroup {
    /** Constructor
     * @param climber a climber subsystem
     * @param timeout how long to climb up
     */
    public AutoClimb(Climber climber, int timeout) {
        super(
            new ClimbUpCommand(climber),
            new WaitCommand(timeout),
            new StopClimb(climber)
            );
    }
}