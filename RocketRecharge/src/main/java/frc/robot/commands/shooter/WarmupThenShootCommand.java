package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

/** An auto command sequence that does something */
public class WarmupThenShootCommand extends SequentialCommandGroup {
    /** An auto command sequence that does something
     * @param shooter a Shooter subsystem
     * @param drivetrain a Drivetrain subsystem
     * @param intake an intake subsystem
     * @param joystick a Joystick
    */
    public WarmupThenShootCommand(Shooter shooter, Drivetrain drivetrain, Intake intake, Joystick joystick) {
        super(
            new WarmupCommand(shooter, joystick, -1, true, drivetrain),
            new WaitCommand(0.5),
            new ShootBurstCommand(shooter, intake, joystick, -1, -1, false, drivetrain)
            );
    }
}

