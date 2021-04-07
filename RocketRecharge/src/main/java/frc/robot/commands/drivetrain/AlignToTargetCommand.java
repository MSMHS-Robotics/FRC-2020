package frc.robot.commands.drivetrain;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Lights;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** A command to align the robot with the vision target */
public class AlignToTargetCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  private final Drivetrain drivetrain;
  private Limelight vision;
  private final Lights blinkin;
  private boolean aligned = false;

  /** 
   * Constructor
   * @param drivetrain a drivetrain subsystem
   * @param blinkin a lights subsystem
   */
  public AlignToTargetCommand(Drivetrain drivetrain, Limelight vision, Lights blinkin) {
    this.drivetrain = drivetrain;
    this.vision = vision;
    this.blinkin = blinkin;
    addRequirements(drivetrain, blinkin);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.visionPIDReset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    boolean aligned = drivetrain.alignToTarget();
    if (aligned) {
      blinkin.setGreen();
    }
    else {
      blinkin.setRedLarson();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    blinkin.setRedLarson();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return aligned;
  }
}
