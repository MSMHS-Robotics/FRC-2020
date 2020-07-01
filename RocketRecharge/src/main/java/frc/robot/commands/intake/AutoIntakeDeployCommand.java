package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Intake;

/** A command to automatically deploy intake and run it + indexer */
public class AutoIntakeDeployCommand extends SequentialCommandGroup {

    /** A command to automatically deploy intake and run it + indexer
     * @param intake an intake subsystem
    */
    public AutoIntakeDeployCommand(Intake intake) {
        super(
            new DeployIntake(intake, 1),
            new RunIntakeCommand(intake, -1),
            new RunIndexerCommand(intake, 0)
            );
    }
}
