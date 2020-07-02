package frc.robot.commands.shooter;

import frc.robot.commands.RocketTimedCommand;
import frc.robot.subsystems.Shooter;

/** A command to stop the shooter */
public class ShooterStopCommand extends RocketTimedCommand {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private Shooter shooter;
    
    /**
     * A command to stop the shooter.
     *
     * @param shooter a shooter subsystem
     */
    public ShooterStopCommand(Shooter shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        shooter.stopPercent();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }
}