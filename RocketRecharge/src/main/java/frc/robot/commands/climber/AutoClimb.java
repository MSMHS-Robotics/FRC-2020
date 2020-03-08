package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Climber;

public class AutoClimb extends SequentialCommandGroup {
    public AutoClimb(Climber climber, int timeout) {
        super(
            new ClimbUpCommand(climber),
            new WaitCommand(timeout),
            new StopClimb(climber)
            );
    }
}