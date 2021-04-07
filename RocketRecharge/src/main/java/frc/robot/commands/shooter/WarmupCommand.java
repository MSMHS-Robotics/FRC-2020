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
 * A command to warmup the shooter before shooting
 */
public class WarmupCommand extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private Shooter shooter;
    private Joystick joystick;
    private int preset;
    private boolean isAuto;
    private Drivetrain drivetrain;

    /**
     * A command to warmup the shooter before shooting.
     *
     * @param shooter a shooter subsystem
     * @param drivetrain a drivetrain subystem
     * @param joystick a joystick
     * @param preset the preset to shoot at
     * @param isAuto a boolean representing if we are running in auton or not
     */
    public WarmupCommand(Shooter shooter, Drivetrain drivetrain, Joystick joystick, int preset, boolean isAuto) {
    this.shooter = shooter;
    this.joystick = joystick;
    this.preset = preset;
    this.isAuto = isAuto;
    this.drivetrain = drivetrain;
    
    addRequirements(shooter, drivetrain);
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
        default:
            shooter.tenFootShot(); //maybe change this?
            break;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
      if(!shooter.isShooting() && !interrupted && !isAuto) {
        shooter.stop();
      }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return shooter.isShooterGood() && isAuto;
  }
}