/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

/**
 * An example command that uses an example subsystem.
 */
public class ShootCommand extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final Shooter shooter;
    private final Intake intake;
    private final Joystick joystick;
    private int preset;
    private boolean autoOrTeleop;


    /**
     * Creates a new ExampleCommand.
     *
     * @param shooter The subsystem used by this command.
     */
    public ShootCommand(Shooter shooter, Intake intake, Joystick joystick, int preset, boolean auto) {
        this.shooter = shooter;
        this.intake = intake;
        this.joystick = joystick;
        this.preset = preset;
        autoOrTeleop = auto;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(shooter, intake);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        int val;

        if (autoOrTeleop) {
            val = preset;
        } else {
            val = joystick.getPOV();
        }

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
        default:
            shooter.tenFootShot(); // maybe change this?
            break;
        }

        if (shooter.isShooterGood()) {
            intake.feed();
        } else {
            intake.stop();
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        shooter.stopPlease();
        intake.stop();
    }

    // Returns true when the command should end.
  @Override
  public boolean isFinished() {
      return false;
  }
}