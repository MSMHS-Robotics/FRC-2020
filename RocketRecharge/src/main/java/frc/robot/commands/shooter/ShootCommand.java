package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.RocketTimedCommand;

public class ShootCommand extends CommandBase {
    private Shooter shooter;
    private double RPM;
    
    /**
     * Creates a new ExampleCommand.
     *
     * @param shooter The subsystem used by this command.
     */
    public ShootCommand(Shooter shooter, double RPM) {
        this.shooter = shooter;
        this.RPM = RPM;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(shooter);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        shooter.shoot(RPM);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        shooter.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}