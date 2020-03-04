package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class GKC extends SequentialCommandGroup {

    public GKC(Shooter shooter, Joystick joystick, Drivetrain drivetrain, Intake intake) {
        super(
            new WarmupCommand(shooter, joystick, -1, true, drivetrain),
            new WaitCommand(0.5),
            new ShootBurstCommand(shooter, intake, joystick, -1, -1, false, drivetrain)
            );
    }
}

