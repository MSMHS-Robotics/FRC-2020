package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.AlignToTargetCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Lights;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

/** An auto command sequence to shoot with the Limelight. I guess. */
public class VisionShotSeq extends SequentialCommandGroup {
    public VisionShotSeq(Drivetrain drivetrain, Lights blinkin, Limelight vision, Joystick joystick, Shooter shooter, Intake intake) {
        super(
            new AlignToTargetCommand(drivetrain, vision, blinkin),
            new WarmupCommand(shooter, drivetrain, joystick, -1, false),
            new ShootBurstVisionCommand(shooter, intake, drivetrain, vision, joystick, -1, false)
            );
    }
}

