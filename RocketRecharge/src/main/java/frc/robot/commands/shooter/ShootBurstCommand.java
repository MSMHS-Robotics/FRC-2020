/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.RocketTimedCommand;

/**
 * An example command that uses an example subsystem.
 */
public class ShootBurstCommand extends RocketTimedCommand {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private Shooter shooter;
    private Intake intake;
    private Joystick joystick;
    private final Drivetrain drivetrain;
    private int preset;
    private int lastval;
    private double timeout;
    private boolean isAuto;
    private boolean hasShooterBeenGood;

    /**
     * Creates a new ExampleCommand.
     *
     * @param shooter The subsystem used by this command.
     */
    public ShootBurstCommand(Shooter shooter, Intake intake, Joystick joystick, int preset, double timeout, boolean auto, Drivetrain drivetrain) {
        this.shooter = shooter;
        this.intake = intake;
        this.joystick = joystick;
        this.preset = preset;
        this.timeout = timeout;
        this.hasShooterBeenGood = false;
        this.lastval = -1;
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
        default:
            //warmup picks case
            break;
        }

        if (shooter.isShooterGood() || (hasShooterBeenGood && lastval == val)) {
            hasShooterBeenGood = true;
            intake.feed(1);
        } else {
            hasShooterBeenGood = false;
            intake.feed(0);
        }
        lastval = val;
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