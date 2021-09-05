package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.drivetrain.SetLights;
import frc.robot.commands.intake.FeedCommand;
import frc.robot.commands.shooter.ShootCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Lights;
import frc.robot.subsystems.Shooter;;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  //subsytems
  private final Intake intake = new Intake();
  private final Shooter shooter = new Shooter();
  private final Drivetrain drivetrain = new Drivetrain();
  private final Lights blinkin = new Lights();
  
  //joysticks
  //raw axis 2 and 5 are the Y axis for the left and right joysticks.
  private final Joystick gamepad1 = new Joystick(0);
  
  //get ready for some buttons and stuff
  private JoystickButton aButton = new JoystickButton(gamepad1, 1);
  private JoystickButton bButton = new JoystickButton(gamepad1, 2);
  private JoystickButton xButton = new JoystickButton(gamepad1, 3);
  private JoystickButton yButton = new JoystickButton(gamepad1, 4);
  
  private JoystickButton leftBumper = new JoystickButton(gamepad1, 5);
  private JoystickButton rightBumper = new JoystickButton(gamepad1, 6);
  
  private double rightTrigger = gamepad1.getRawAxis(3);

  private JoystickButton back = new JoystickButton(gamepad1, 7);

  private JoystickButton start = new JoystickButton(gamepad1, 8);
  
  //now for some commands  

  //drivetrain
  //private final AlignToTargetCommand align = new AlignToTargetCommand(drivetrain, blinkin);

  // Lights
  private final SetLights toggleVision = new SetLights(blinkin, 0.5);
  private final SetLights setFire = new SetLights(blinkin, 0.5);
  private final SetLights setRainbow = new SetLights(blinkin, 0.5);
  
  //intake + indexer
  private final FeedCommand feedForward = new FeedCommand(intake, 1);
  private final FeedCommand feedReverse = new FeedCommand(intake, -1);
  private final FeedCommand stopFeed = new FeedCommand(intake, 0);
  
  //shooter
  private final ShootCommand stopShooter = new ShootCommand(shooter, 0); // do we even need this if it stops automatically?
  private final ShootCommand shoot = new ShootCommand(shooter, 100); // Constants and other speed-logic in here or other files?

  //Stupid axis stuff
  //this works for some reason and is the only way we can work with joysticks (x + y) apparently
  private final RunCommand runDrivetrain = new RunCommand(() -> drivetrain.driveTeleOp(
    gamepad1.getRawAxis(1),
    gamepad1.getRawAxis(5)),
    drivetrain);
 
  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
      configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //TODO update bindings
    //drivetrain
    back.whenPressed(runDrivetrain);
    
    //intake
    xButton.whenPressed(feedForward);
    xButton.whenReleased(stopFeed); // do even need this?
    
    //shooter
    bButton.whenReleased(stopShooter);
    aButton.whenPressed(shoot);
    
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    //this might automagically work. no touchy.
    return null;
  }

  public RunCommand getDriveCommand() {
    return runDrivetrain;
  }

  public Joystick getJoystick1() {
    return gamepad1;
  }


  public Lights getBlinkin() {
    return blinkin;
  }
}