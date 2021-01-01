# FRC-2020
FRC 2020: Infinite Recharge Robot Software
This repo contains the code used for #2723 Team Rocket's 2019-2020 FRC season.
The code is written in Java using the new (not old) WPILib command-based framework. To use this code simply download the repo and build the code using the WPILib VSCode extension. This code depends on the NavX (Kauai Labs), Talon (CTRE), and SparkMAX (REV) libraries. Make sure you've installed them if you want to build and use the code. The most recent branch (with untested post-season fixes) should be feat/refactoring.

## Documentation
Most of the documentation was written post-season without access to the robot, and because some other changes were made, and these changes were not tested, these changes were not merged into the main branch. You can view most of the documentation on the feat/docs branch by either reading the comments or looking at the auto-generated html files in the `docs` folder.

## Subsystem Overview
There are 6 subsystems, corresponding to 6 different parts of the roobt.
 - Drivetrain: controls our drivetrain motors, reads sensor inputs from 8 different encoders, a NavX, and a Limelight for lining up with the vision target.
 - Intake: controls the intake, pass-through, and 'trigger' motors. The trigger motor is the wheel right before the shooter that controls whether we are shooting or not. The intake has one encoder.
 - Shooter: controls the two shooter motors, one leading and one following.
 - Climber: controls the two
