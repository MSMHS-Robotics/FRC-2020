package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.RocketTimedCommand;

/**
 * A command to shoot
 */
public class ShootCommand extends RocketTimedCommand {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private Shooter shooter;
    private Intake intake;
    private Joystick joystick;
    private Drivetrain drivetrain;
    private Limelight vision;
    
    private int preset;
    private double timeout;
    private boolean isAuto;
    
    /**
     * A command to shoot.
     *
     * @param shooter a shooter subsystem
     * @param intake an intake subsystem
     * @param drivetrain a drivetrain subystem
     * @param joystick a joystick
     * @param preset the preset to shoot at
     * @param timout the timout for the command
     * @param isAuto a boolean representing if we are running in auton or not
     */
    public ShootCommand(Shooter shooter, Intake intake, Drivetrain drivetrain, Limelight vision, Joystick joystick, int preset, double timeout, boolean isAuto) {
        this.shooter = shooter;
        this.intake = intake;
        this.joystick = joystick;
        this.preset = preset;
        this.timeout = timeout;
        this.drivetrain = drivetrain;
        this.vision = vision;
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
        int val;

        if (isAuto) {
            val = preset;
        } else {
            val = joystick.getPOV();
        }

        shooter.setShootingFlag(true);

        switch (val) {
            case 0:
                shooter.trenchShot();
                break;
            case 90:
                shooter.tenFootShot();
                break;
            case 180:
                shooter.layupShot();
                break;
            case 270:
                shooter.customShot(vision.getNeededRPM());
                break;
            default:
                //warmup sets up the case
                break;
        }
        
        if (shooter.isShooterGood()) {
            intake.feed(1);
        } else {
            intake.feed(0);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        shooter.stop();
        shooter.setShootingFlag(false);
        intake.feed(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return super.isTimedOut();
    }
}