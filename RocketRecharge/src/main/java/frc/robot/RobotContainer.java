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
import frc.robot.autonomous.DriveOffLine;
import frc.robot.autonomous.EightBallAuto;
import frc.robot.commands.AlignToTargetCommand;
import frc.robot.commands.AlignToTargetCommandSnipa;
//import frc.robot.commands.drivetrain.TurnOnHeading;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  //Joystick
  private final Joystick gamepad1 = new Joystick(0);
  JoystickButton aButton = new JoystickButton(gamepad1, 1);
  JoystickButton bButton = new JoystickButton(gamepad1, 2);

  //subsystems go here:
  private final Drivetrain drivetrain = new Drivetrain();
 
 //auto commands
 // private final TurnOnHeading m_autoCommand = new TurnOnHeading(drivetrain, 90, -1);
 private final EightBallAuto eightBallAuto = new EightBallAuto(drivetrain);
 private final DriveOffLine driveAuto = new DriveOffLine(drivetrain);


 //Drivetrain Commands
  //this works for some reason and is the only way we can work with joysticks (x + y) apparently
  private final RunCommand runDrivetrain = new RunCommand(() -> drivetrain.tankDrive(
    gamepad1.getRawAxis(1),
    gamepad1.getRawAxis(5)),
    drivetrain);
 
  private final AlignToTargetCommand align = new AlignToTargetCommand(drivetrain);
  private final AlignToTargetCommandSnipa alignSnipa = new AlignToTargetCommandSnipa(drivetrain);
  

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
    aButton.whenPressed(align);
    bButton.whenPressed(alignSnipa);
    bButton.whenReleased(runDrivetrain);
    aButton.whenReleased(runDrivetrain);
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return driveAuto;
  }

  public RunCommand getDriveCommand(){
    return runDrivetrain;
  }
}
