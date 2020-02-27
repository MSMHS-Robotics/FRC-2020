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

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class WarmupCommand extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final Shooter shooter;
    private final Joystick joystick;
    private int preset;
    private boolean isAuto;
    private Drivetrain drivetrain;

    /**
     * Creates a new ExampleCommand.
     *
     * @param shooter The subsystem used by this command.
     */
    public WarmupCommand(Shooter shooter, Joystick joystick, int preset, boolean auto, Drivetrain drivetrain) {
    this.shooter = shooter;
    this.joystick = joystick;
    this.preset = preset;
    isAuto = auto;
    this.drivetrain = drivetrain;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter, drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      int val;

    if(isAuto){
        val = preset;
    }
    else{
        val = joystick.getPOV();
    }

    switch(val){
        case 0:
            shooter.layupShot();
            break;
        case 90:
            shooter.tenFootShot();
            break;
        case 180:
            shooter.trenchShot();
            break;
        case 270:
            shooter.customShot(drivetrain.getNeededRPM());
        default:
            shooter.tenFootShot(); //maybe change this?
            break;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
      if(!shooter.shooting() && !isAuto) {
        shooter.stopPlease();
      }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return shooter.isShooterGood() && isAuto;
  }
}