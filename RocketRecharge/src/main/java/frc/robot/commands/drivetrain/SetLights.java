package frc.robot.commands.drivetrain;

import frc.robot.subsystems.Lights;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetLights extends CommandBase {
  private Lights lights;
  private double value = 0;

  public SetLights(Lights lights, double value) {
    this.lights = lights;
    this.value = value;
    addRequirements(lights);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    lights.set(value);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
