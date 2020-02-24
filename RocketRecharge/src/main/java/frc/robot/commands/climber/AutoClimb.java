package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Climber;

public class AutoClimb extends SequentialCommandGroup {

    public AutoClimb(Climber climber) {
        super(
            new ClimbUpCommand(climber),
            new WaitCommand(.5),
            new LatchCommand(climber)
            );
    }
}