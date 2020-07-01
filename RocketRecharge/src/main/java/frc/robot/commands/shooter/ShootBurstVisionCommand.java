package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.RocketTimedCommand;

/** A command to shoot very fastily using the Limelight to get the distance */
public class ShootBurstVisionCommand extends RocketTimedCommand {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private Shooter shooter;
    private Intake intake;
    private Drivetrain drivetrain;
    
    private double timeout;
    private boolean isAuto;
    
    /** A command to shoot very fastily using the Limelight to get the distance.
     * @param shooter a shooter subsystem
     * @param intake an intake subsystem
     * @param drivetrain a drivetrain subsystem
     * @param joystick a Joystick
     * @param timeout the timout for the command
     * @param isAuto true if in an auton, false otherwise
    */
    public ShootBurstVisionCommand(Shooter shooter, Intake intake, Drivetrain drivetrain, Joystick joystick, double timeout, boolean isAuto) {
        this.shooter = shooter;
        this.intake = intake;
        this.timeout = timeout;
        this.drivetrain = drivetrain;
        this.isAuto = isAuto;
        
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