# General
 - a shuffleboard 'change' button that has to be pressed to update any of the values? idk. only if we get loop overruns i guess

# Drivetrain
## Hardware
 - 6 NEOs (3 per side)
 - 6 CAN encoders for the NEOs (3 per side)
 - 1 NavX IMU board
 - 2 REV through-bore encoders (1 per side)
## Shuffleboard
 - heading
 - reset gyro button
 - heading PID

 - distance
 - left distance
 - right distance
 - distancePID
 - speed
 - reset encoders
 - speedPID
 - set setpoint for speed
 - set max speed
 - set stick input multiplier
 ## Functions
 - double getLeftEncAvg
 - double getRightEncAvg
 - double getEncAvg
 - void driveTeleOp(leftstick, rightstick)
 - void resetGyro
 - void resetEncoders
 - void resetDistancePID
 - void resetHeadingPID
 - double getHeading
 - double getSpeed???
## Commands
 - DriveSteady
 - DriveTeleOp? or just the random stick thingies we do?

# Pass through
## Hardware
 - 3 NEO 550s (2 for belts, 1 for trigger wheel)
 - 3 CAN encoders
## Shuffleboard
 - speed
 - set speed
## Functions
 - set(double)
 - get()
## Commands
 - Set(double)

# Shooter
## Hardware
 - 2 NEOs
 - 2 CAN encoders
## Shuffleboard
 - speed
 - shooterPID
 - RPM setpoint
## Functions
 - shoot(double)
 - get()?
## Commands
 - Set(double)

# Vision
## Hardware
 - 1 Limelight
 - 1 other camera
## Shuffleboard
 - camera feeds
 - target location?
 - visionPID?
## Functions
 - getXDist()
 - getStickOutputs ??? or make in drivetrain???
## Commands
 - TrackTarget?

# Lights?
## Hardware
 - 1 Spark motor controller
## Shuffleboard
 - preset values (4)
## Functions
 - void set(double)
## Commands
 - Set