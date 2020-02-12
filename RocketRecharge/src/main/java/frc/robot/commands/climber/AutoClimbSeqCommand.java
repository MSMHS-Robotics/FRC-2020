package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.*;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Intake;

public class AutoClimbSeqCommand extends SequentialCommandGroup {
    public AutoClimbSeqCommand(Climber climber, Intake intake) {
        super(
            new DeployClimber(climber, intake),
            new RaiseClimber(climber),
            new ClimbUpCommand(climber)
            );
    }
}

