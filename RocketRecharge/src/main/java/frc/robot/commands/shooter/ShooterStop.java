//Shooter passed into constructor
//Joystick passed into constructor (teleop)
//integer passed into constructor (auto)
//boolean passed into constuctor (decision)
//plug and chug
//intialize as normal
//not interupted stop
// interupted keep going
// is finished return false


package frc.robot.commands.shooter;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class ShooterStop extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final Shooter shooter;
    private final Joystick joystick;
    private int preset;
    private boolean isAuto;

    /**
     * Creates a new ExampleCommand.
     *
     * @param shooter The subsystem used by this command.
     */
    public ShooterStop(Shooter shooter) {
    this.shooter = shooter;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      shooter.stopPlease();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}