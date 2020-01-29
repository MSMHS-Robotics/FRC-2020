/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.FeedToShooterCommand;
import frc.robot.commands.PrepLoadCommand;
import frc.robot.commands.PrepShotCommand;
import frc.robot.commands.RunIntakeCommand;
import frc.robot.commands.StopFeedToShooterCommand;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final Intake intake = new Intake();
  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  private final Joystick gamepad1 = new Joystick(0);
  private final Joystick gamepad2 = new Joystick(1);
  JoystickButton aButton = new JoystickButton(gamepad1, 0);
  JoystickButton bButton = new JoystickButton(gamepad1, 1);
  JoystickButton xButton = new JoystickButton(gamepad1, 2);
  JoystickButton yButton = new JoystickButton(gamepad2, 3);
  Joystick leftStick = new Joystick(0);

  RunIntakeCommand runIntake = new RunIntakeCommand(intake, (int) leftStick.getX());
  PrepLoadCommand prepLoad = new PrepLoadCommand(intake);
  FeedToShooterCommand feed = new FeedToShooterCommand(intake);
  PrepShotCommand prepShot = new PrepShotCommand(intake);
  StopFeedToShooterCommand stopFeed = new StopFeedToShooterCommand(intake);


  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    aButton.whenPressed(prepShot);
    bButton.whenPressed(feed);
    xButton.whenPressed(prepLoad);
    yButton.whenPressed(stopFeed);
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
