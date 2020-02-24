package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.intake.ExtendIntakeCommand;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Intake;

public class AutoDeployClimber extends SequentialCommandGroup {

    public AutoDeployClimber(Intake intake, Climber climber) {
        super(
            new ExtendIntakeCommand(intake),
            new DeployClimber(climber),
            new RaiseClimber(climber)
            );
    }
}