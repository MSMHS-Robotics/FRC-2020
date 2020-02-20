/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;
import java.util.TreeMap;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.autonomous.DriveOffLine;
import frc.robot.autonomous.DriveOffLineReverse;
import frc.robot.autonomous.EightBallAuto;
import frc.robot.autonomous.ThreeBallAuto;

import frc.robot.commands.intake.*; //a lot easier than importing them one by one
import frc.robot.commands.climber.*;
import frc.robot.commands.drivetrain.*;
import frc.robot.commands.shooter.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.commands.drivetrain.TurnOnHeading;
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
  // The robot's subsystems and commands are defined here...

  //subsystems go here:
  //auto commands
  // private final TurnOnHeading m_autoCommand = new TurnOnHeading(drivetrain, 90, -1);
  //private final EightBallAuto eightBallAuto = new EightBallAuto(drivetrain);
  //private final DriveOffLine driveAuto = new DriveOffLine(drivetrain);
  
  //subsytems
  private final Climber climber = new Climber();
  private final Intake intake = new Intake();
  private final Shooter shooter = new Shooter();
  private final Drivetrain drivetrain = new Drivetrain();
  private final Lights blinkin = new Lights();
  private final Diagnostics diagnostics = new Diagnostics();
  
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

  private JoystickButton start = new JoystickButton(gamepad1, 8);
  private JoystickButton start2 = new JoystickButton(gamepad2, 8);
  
  //now for some commands
  //climber
  private final ClimbUpCommand climbUp = new ClimbUpCommand(climber);
  private final UnDeployClimber unDeployClimber = new UnDeployClimber(climber);
 // private final AutoDeployClimber climberDeploy = new AutoDeployClimber(intake, climber);
 private final DeployClimber climberDeploy = new DeployClimber(climber);

  //drivetrain
  private final AlignToTargetCommand align = new AlignToTargetCommand(drivetrain, blinkin);
  private final ToggleVisionTypeCommand toggleVision = new ToggleVisionTypeCommand(drivetrain);
  private final SetRedHeartbeatCommand setFire = new SetRedHeartbeatCommand(blinkin);
  private final AlertHumanPlayerCommand setRainbow = new AlertHumanPlayerCommand(blinkin);
  
  //intake + indexer
  private final RunIntakeCommand intakeIn = new RunIntakeCommand(intake, 1);
  private final RunIntakeCommand intakeOut = new RunIntakeCommand(intake, -1);

  private final FeedCommand feedForward = new FeedCommand(intake, 1);
  private final FeedCommand feedReverse = new FeedCommand(intake, -1);
  private final RunIndexerCommand setIdle = new RunIndexerCommand(intake, 0);
  
  private final PrepShotCommand prepShot = new PrepShotCommand(intake);

  private final RetractIntakeCommand retractIntake = new RetractIntakeCommand(intake);
  private final AutoIntakeDeployCommand autoDeployIntake = new AutoIntakeDeployCommand(intake);

  //auto. maybe delete
  private final TurnOnHeading turnOffLine = new TurnOnHeading(drivetrain, 90, -1);

  //shooter
  private final WarmupCommand shooterWarmup = new WarmupCommand(shooter, gamepad1, 1, false);
  private final ShootCommand shootTeleop = new ShootCommand(shooter, intake, gamepad1, 1, 1, false); //this timeout right?

  //Stupid axis stuff
  //this works for some reason and is the only way we can work with joysticks (x + y) apparently
  private final RunCommand runDrivetrain = new RunCommand(() -> drivetrain.tankDrive(
    gamepad1.getRawAxis(1),
    gamepad1.getRawAxis(5)),
    drivetrain);
  private final RunCommand climbUsingTehStick = new RunCommand(() -> climber.climbUsingStick(gamepad2.getRawAxis(5)));
  //this is jank and I have no idea if it will work
  //private final RunCommand shoot = new RunCommand(() -> {if(gamepad1.getRawAxis(3) < 0) {shooterWarmup.execute();} else{shooterWarmup.end(false);}});
 
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
    autos.put("Drive Off Line Reverse", new DriveOffLineReverse(drivetrain));
    autos.put("Eight Ball Auto", new EightBallAuto(drivetrain));
    autos.put("Three Ball Auto", new ThreeBallAuto(drivetrain, intake, shooter));
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
    //drivetrain stuff. working on toggle hardware zoom with 1 button
    //update: TOGGLING DONE! untested though
    aButton.whenPressed(align);
    aButton.whenReleased(runDrivetrain);
    aButton.whenPressed(setFire);
    start.whenPressed(toggleVision); //so we can use less buttons
    xButton.whenPressed(setRainbow);
    xButton.whenReleased(setFire);
    
    aButton2.whenPressed(new ResetGyroCommand(drivetrain));
   


    //intake stuff. intake automagically sets power to 0 after command ends
    //rightBumper.whenPressed(intakeOut); //don't need this now
    leftBumper.whenHeld(autoDeployIntake); //extends, runs intake + belt
    leftBumper.whenReleased(retractIntake); //retracts, intake and indexer motor stop automatically
    leftBumper.whenReleased(setIdle); //to sort the stuff out

    //climber
    //this might work don't trust it
    //leftBumper.whenPressed(climbUsingTehStick); //for testing purposes
    xButton2.whenHeld(climberDeploy);
    yButton2.whenPressed(climbUp);
    start2.whenPressed(unDeployClimber);
    
    //shooter
    bButton2.whenHeld(new WarmupCommand(shooter, gamepad2, -1, false));
    bButton.whenHeld(new ShootCommand(shooter, intake, gamepad1, -1, -1, false));

  }

  //stuff for auto selector
  public String getNames() {
    String tempAutoNames = "";
    for(int i = 0; i < this.getLength(); i++) {
      tempAutoNames += (autoNames.get(i) + ", ");
    }
    return tempAutoNames;
  }

  public String getName(int y) {
    return "\n>>" + autoNames.get(y);
  }

  public void setAutoNum(int x) {
    curr_auto = x;
  }

  public int getLength() {
    return lengthOfList;
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    //this might automagically work. no touchy.
    return autos.get(autoNames.get(curr_auto));
    //return turnOffLine;
  }

  public RunCommand getDriveCommand() {
    return runDrivetrain;
  }

  public ShootCommand getShootCommand(){
    return shootTeleop;
  }

  public WarmupCommand getWarmupCommand(){
    return shooterWarmup; 
  }

  public Joystick getJoystick1() {
    return gamepad1;
  }

  public Joystick getJoystick2(){
    return gamepad2;
  }

  public JoystickButton getButtonA1() {
    return aButton; 
  }

  public Lights getBlinkin() {
    return blinkin;
  }
}