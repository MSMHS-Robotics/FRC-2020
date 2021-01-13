package frc.robot.commands.diagnostics;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Diagnostics;

/** A command to update the diagnostics subsystem and shuffleboard */
public class Update extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    
  private Diagnostics diagnostics;

  public Update(Diagnostics diagnostics) {
    this.diagnostics = diagnostics;
    addRequirements(diagnostics);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    diagnostics.update();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}