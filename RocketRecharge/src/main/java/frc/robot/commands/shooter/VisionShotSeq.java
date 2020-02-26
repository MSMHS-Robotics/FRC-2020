package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.AlignToTargetCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Lights;
import frc.robot.subsystems.Shooter;

public class VisionShotSeq extends SequentialCommandGroup {
    public VisionShotSeq(Drivetrain drivetrain, Lights blinkin, Joystick joystick, Shooter shooter, Intake intake) {
        super(
            new AlignToTargetCommand(drivetrain, blinkin),
            new WarmupCommand(shooter, joystick, -1, false, drivetrain),
            new ShootBurstVisionCommand(shooter, intake, joystick, -1, -1, false, drivetrain)
            );
    }
}

