package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;;

public class ClimbUpCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Climber climber;

  public ClimbUpCommand(Climber climber) {
    this.climber = climber;
    addRequirements(climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    climber.climbWithPID();
    //climber.climbUsingStick(x); this is for testing purposes
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //climber.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}