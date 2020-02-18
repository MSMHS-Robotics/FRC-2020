package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Intake;

public class AutoIntakeRetractCommand extends SequentialCommandGroup {

    public AutoIntakeRetractCommand(Intake intake) {
        super(
            new RetractIntakeCommand(intake),
            new RunIntakeCommand(intake, 0),
            new RunIndexerCommand(intake, 0),
            new ManualTriggerWheel(intake, 0)
            );
    }
}

