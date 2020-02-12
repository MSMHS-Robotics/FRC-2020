package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.intake.*;
import frc.robot.subsystems.Intake;

public class AutoIntakeDeployCommand extends SequentialCommandGroup {

    public AutoIntakeDeployCommand(Intake intake) {
        super(
            new ExtendIntakeCommand(intake),
            new RunIntakeCommand(intake, 1),
            new IndexerForwardCommand(intake, 1)
            );
    }
}

