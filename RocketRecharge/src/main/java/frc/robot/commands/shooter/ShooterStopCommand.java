package frc.robot.commands.shooter;

import frc.robot.commands.RocketTimedCommand;
import frc.robot.subsystems.Shooter;

public class ShooterStopCommand extends RocketTimedCommand {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final Shooter shooter;
    private Boolean stopSucceded;
    
    /**
     * Creates a new StopShooterCommand.
     *
     * @param shooter The subsystem used by this command.
     */
    public ShooterStopCommand(Shooter shooter) {
        this.shooter = shooter;
        stopSucceded = false;
        addRequirements(shooter);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        stopSucceded = shooter.stopPercent();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return stopSucceded;
    }
}