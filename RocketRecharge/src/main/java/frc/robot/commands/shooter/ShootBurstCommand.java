package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.RocketTimedCommand;

/**
 * A command to shoot very fastily
 */
public class ShootBurstCommand extends RocketTimedCommand {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    
    private Shooter shooter;
    private Intake intake;
    private Drivetrain drivetrain;
    
    private Joystick joystick;
    
    private int preset;
    private int lastval;
    private double timeout;
    private boolean isAuto;
    private boolean hasShooterBeenGood;

    /**
     * A command to shoot very fastily
     * @param shooter a shooter subsystem
     * @param intake an intake subsystem
     * @param drivetrain a drivetrain subsystem
     * @param joystick a joystick
     * @param preset the preset RPM to shoot at
     * @param timeout the timeout for the command
     * @param auto a boolean. True if in autonomous mode, false otherwise
    */
    public ShootBurstCommand(Shooter shooter, Intake intake, Drivetrain drivetrain, Joystick joystick, int preset, double timeout, boolean isAuto) {
        this.shooter = shooter;
        this.intake = intake;
        this.drivetrain = drivetrain;
        this.joystick = joystick;
        
        this.preset = preset;
        this.lastval = -1;
        this.timeout = timeout;
        this.isAuto = isAuto;
        this.hasShooterBeenGood = false;
        
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

        switch (val) {
            case 0:
                shooter.layupShot();
                break;
            case 90:
                shooter.tenFootShot();
                break;
            case 180:
                shooter.trenchShot();
                break;
            case 270:
                shooter.customShot(drivetrain.getNeededRPM());
                break;
            default:
                //warmup sets up case
                break;
        }

        if (shooter.isShooterGood() || (hasShooterBeenGood && lastval == val)) {
            hasShooterBeenGood = true;
            intake.feed(1);
        } else {
            hasShooterBeenGood = false;
            if (!intake.hasBall()) {
                intake.feed(0);
            }
        }
        lastval = val;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        shooter.stop();
        intake.feed(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return super.isTimedOut();
    }
}