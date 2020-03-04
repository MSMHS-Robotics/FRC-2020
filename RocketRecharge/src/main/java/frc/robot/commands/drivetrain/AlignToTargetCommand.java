package frc.robot.commands.drivetrain;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Lights;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AlignToTargetCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain drivetrain;
  private final Lights blinkin;

  public AlignToTargetCommand(Drivetrain drivetrain, Lights blinkin) {
    this.drivetrain = drivetrain;
    this.blinkin = blinkin;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.visionPIDReset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(drivetrain.getVisionType()) { //true is snipa, false is normal
      drivetrain.visionAlignSnipa();
      if(drivetrain.isVisionAligned()) {
        blinkin.setGreen();
      }
      else {
        blinkin.setRedLarson();
      }
    }
    else {
      drivetrain.visionAlign();
      if(drivetrain.isVisionAligned()) {
        blinkin.setGreen();
      }
      else {
        blinkin.setRedLarson();
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //drivetrain.ledsOff();
    blinkin.setRedLarson();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return drivetrain.isVisionAligned();
  }
}
