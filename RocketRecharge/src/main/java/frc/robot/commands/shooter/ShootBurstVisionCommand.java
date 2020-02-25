package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.RocketTimedCommand;

public class ShootBurstVisionCommand extends RocketTimedCommand {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private Shooter shooter;
    private Intake intake;
    private final Drivetrain drivetrain;
    private double timeout;
    private boolean isAuto;
    
    public ShootBurstVisionCommand(Shooter shooter, Intake intake, Joystick joystick, int preset, double timeout,
            boolean auto, Drivetrain drivetrain) {
        this.shooter = shooter;
        this.intake = intake;
        this.timeout = timeout;
        this.drivetrain = drivetrain;
        isAuto = auto;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(shooter, intake, drivetrain);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        if (isAuto) {
            super.setTimeout(timeout);
        }
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        shooter.customShot(drivetrain.getNeededRPM());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        shooter.stopPlease();
        intake.feed(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return super.isTimedOut();
    }
}