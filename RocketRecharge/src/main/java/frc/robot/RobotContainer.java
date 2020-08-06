package frc.robot;

// Utils for Auto selector
import java.util.ArrayList;
import java.util.TreeMap;

// Needed for binding commands
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

// All of our autons
import frc.robot.autonomous.DriveOffLine;
import frc.robot.autonomous.DriveOffLineReverse;
import frc.robot.autonomous.EightBallAuto;
import frc.robot.autonomous.ThreeBallAuto;
import frc.robot.autonomous.DelayedThreeBallAuto;
import frc.robot.autonomous.UnchargedThreeBallAuto;

// Import the commands
import frc.robot.commands.intake.*;
import frc.robot.commands.climber.*;
import frc.robot.commands.drivetrain.*;
import frc.robot.commands.shooter.*;
import frc.robot.diagnostics.Update;

// import some stuff
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.commands.drivetrain.TurnOnHeading;

// import our subsystems
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Lights;
import frc.robot.subsystems.Diagnostics;;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  //subsytems
  private final Climber climber = new Climber(13, 14);
  private final Diagnostics diagnostics = new Diagnostics();
  private final Drivetrain drivetrain = new Drivetrain(1, 2, 3, 4, 5, 6);
  private final Intake intake = new Intake(10, 11, 15, 12);
  private final Lights blinkin = new Lights(0);
  private final Limelight limelight = new Limelight();
  private final Shooter shooter = new Shooter(7, 8);
  
  //joysticks
  //raw axis 2 and 5 are the Y axis for the left and right joysticks.
  private final Joystick gamepad1 = new Joystick(0);
  private final Joystick gamepad2 = new Joystick(1);
  
  //get ready for some buttons and stuff
  private JoystickButton aButton = new JoystickButton(gamepad1, 1);
  private JoystickButton bButton = new JoystickButton(gamepad1, 2);
  private JoystickButton xButton = new JoystickButton(gamepad1, 3);
  private JoystickButton yButton = new JoystickButton(gamepad1, 4);

  private JoystickButton aButton2 = new JoystickButton(gamepad2, 1);
  private JoystickButton bButton2 = new JoystickButton(gamepad2, 2);
  private JoystickButton xButton2 = new JoystickButton(gamepad2, 3);
  private JoystickButton yButton2 = new JoystickButton(gamepad2, 4);
  
  private JoystickButton leftBumper = new JoystickButton(gamepad1, 5);
  private JoystickButton rightBumper = new JoystickButton(gamepad1, 6);
  private JoystickButton leftBumper2 = new JoystickButton(gamepad2, 5);
  private JoystickButton rightBumper2 = new JoystickButton(gamepad2, 6);
  
  private double rightTrigger = gamepad1.getRawAxis(3);

  private JoystickButton back = new JoystickButton(gamepad1, 7);
  private JoystickButton back2 = new JoystickButton(gamepad2, 7);

  private JoystickButton start = new JoystickButton(gamepad1, 8);
  private JoystickButton start2 = new JoystickButton(gamepad2, 8);
  
  //now for some commands
  //climber
  private final RaiseClimber raiseClimber = new RaiseClimber(climber);
  private final ClimbUpCommand climbUp = new ClimbUpCommand(climber);
  private final StopClimb stopClimb = new StopClimb(climber);
  private final StopRaise stopRaise = new StopRaise(climber);
  
  //drivetrain
  private final AlignToTargetCommand align = new AlignToTargetCommand(drivetrain, blinkin);
  private final ToggleZoomCommand toggleVision = new ToggleZoomCommand(limelight);
  private final SetRedHeartbeatCommand setFire = new SetRedHeartbeatCommand(blinkin);
  private final AlertHumanPlayerCommand setRainbow = new AlertHumanPlayerCommand(blinkin);
  
  //intake + indexer
  private final DeployIntake deployIntake = new DeployIntake(intake, 1);
  private final RetractIntakeCommand retractIntake = new RetractIntakeCommand(intake, -1);
  private final RunIntakeCommand intakeIn = new RunIntakeCommand(intake, -1);
  private final RunIntakeCommand intakeOut = new RunIntakeCommand(intake, 1);
  private final RunIntakeCommand stopIntake = new RunIntakeCommand(intake, 0);
  private final AutoIntakeDeployCommand autoDeploy = new AutoIntakeDeployCommand(intake);
  
  private final RunIndexerCommand unjam = new RunIndexerCommand(intake, -1);
  private final FeedCommand feedForward = new FeedCommand(intake, 1);
  private final FeedCommand feedReverse = new FeedCommand(intake, -1);
  private final FeedCommand stopFeed = new FeedCommand(intake, 0);
  private final RunIntakeCommand setIdle = new RunIntakeCommand(intake, 0);
  
  //shooter
  private final ShooterStopCommand stopShooter = new ShooterStopCommand(shooter);
  private final WarmupCommand shooterWarmup = new WarmupCommand(shooter, gamepad2, 1, false, drivetrain);
  private final ShootBurstCommand shootTeleop = new ShootBurstCommand(shooter, intake, gamepad1, 1, 1, false, drivetrain); //this timeout right?
  private final ShooterStopCommand stopWarmupPlease = new ShooterStopCommand(shooter);
  
  //diagnostics
  private final Update diagnosticsCommand = new Update(diagnostics);

  //Stupid axis stuff
  //this works for some reason and is the only way we can work with joysticks (x + y) apparently
  private final RunCommand runDrivetrain = new RunCommand(() -> drivetrain.tankDrive(
    gamepad1.getRawAxis(1),
    gamepad1.getRawAxis(5)),
    drivetrain);
  //this is jank and I have no idea if it will work
  
  //auto selector stuff
  private TreeMap<String, Command> autos = new TreeMap<String, Command>();
  private ArrayList<String> autoNames;
  private int curr_auto = 0;
  private int lengthOfList;

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    
    //auto selector stuff
    autos.put("Drive Off Line", new DriveOffLine(drivetrain));
    autos.put("Three Ball Auto", new ThreeBallAuto(drivetrain, intake, shooter));
    autos.put("Delay Three Ball Auto", new DelayedThreeBallAuto(drivetrain, intake, shooter));
    autos.put("Uncharged Three Ball Auto", new UnchargedThreeBallAuto(drivetrain, intake, shooter));
    autos.put("Drive Off Line Reverse", new DriveOffLineReverse(drivetrain));
    autos.put("Eight Ball Auto", new EightBallAuto(drivetrain, intake, shooter));
    autoNames = new ArrayList<>(autos.keySet());
    lengthOfList = autoNames.size();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //drivetrain stuff
    aButton.whenPressed(align);
    aButton.whenReleased(runDrivetrain);
    aButton.whenReleased(setFire);
    start.whenPressed(toggleVision);
    start.whenReleased(runDrivetrain);
    back.whenPressed(runDrivetrain);

    //intake stuff. intake automagically sets power to 0 after command ends
    rightBumper.whenHeld(autoDeploy); //extends, runs intake + belt
    rightBumper.whenReleased(stopIntake);
    yButton.whileHeld(retractIntake);
    leftBumper.whenHeld(intakeOut);
    leftBumper.whenReleased(stopIntake);
    xButton.whenPressed(feedForward);
    xButton.whenReleased(stopFeed);
    
    //climber
    yButton2.whenPressed(climbUp);
    yButton2.whenReleased(stopClimb);
    //xButton2.whenPressed(LowerClimber);//added just in case
    rightBumper2.whenPressed(raiseClimber);
    rightBumper2.whenReleased(stopRaise);


    //shooter
    bButton2.whileHeld(shooterWarmup);
    bButton.whileHeld(shootTeleop);
    bButton.whenReleased(stopShooter);
    bButton.whenReleased(runDrivetrain);
    aButton2.whenPressed(stopShooter);
    xButton2.whenPressed(stopWarmupPlease);
    xButton2.whenReleased(runDrivetrain);
  }

  /**
   * Gets all of the names for the auto selector
   * 
   * @return a String of all the names joined together
   */
  public String getNames() {
    String tempAutoNames = "";
    for(int i = 0; i < this.getLength(); i++) {
      tempAutoNames += (autoNames.get(i) + ", ");
    }
    return tempAutoNames;
  }

  /**
   * Gets the name of desired auto
   *
   * @param y index of name you want to get
   * @return returns a String of the name of the auto
   */
  public String getName(int y) {
    return "\n>>" + autoNames.get(y);
  }

  /**
   * Sets the current auto (i.e. the one that will be returned to Robot and run)
   *
   * @param x index of the auto you want to be run
   */
  public void setAutoNum(int x) {
    curr_auto = x;
  }

  /**
   * Gets the length of the list of autons
   *
   * @return length of the list of autons
   */
  public int getLength() {
    return lengthOfList;
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autos.get(autoNames.get(curr_auto));
  }

  /**
   * Returns the command to run the drivetrain
   * @return the runDrivetrain command
   */
  public RunCommand getDriveCommand() {
    return runDrivetrain;
  }

  /* Backup shoot code
  public WarmupThenShoot getShootCommand() {
    return shootTeleop;
  }
  */

  /**
   * Returns the command to warm up the shooter
   * @return the shooterWarmup command
   */
  public WarmupCommand getWarmupCommand(){
    return shooterWarmup; 
  }

  /**
   * Returns gamepad 1
   * @return gamepad 1
   */
  public Joystick getJoystick1() {
    return gamepad1;
  }

  /**
   * Returns gamepad 1
   * @return gamepad 1
   */
  public Joystick getJoystick2(){
    return gamepad2;
  }

  /**
   * Returns the "A" button on gamepad 1
   * @return JoystickButton a
   */
  public JoystickButton getButtonA1() {
    return aButton; 
  }

  /**
   * Returns the fancy LEDs
   * @return blinkin
   */
  public Lights getBlinkin() {
    return blinkin;
  }

  /*
  public Command getDiagnosticsCommand() {
    return diagnosticsCommand;
  }
  */
}