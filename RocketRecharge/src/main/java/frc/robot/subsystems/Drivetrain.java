package frc.robot.subsystems;

// Gyro sensor
import com.kauailabs.navx.frc.AHRS;

// Encoders
import edu.wpi.first.wpilibj.Encoder;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// Shuffleboard
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

// Drivetrain-y stuff
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

// Other
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants;
import frc.robot.subsystems.Limelight;

/** A Drivetrain subsystem class */
public class Drivetrain extends SubsystemBase {
  //motors
  private CANSparkMax left1;
  private CANSparkMax left2;
  private CANSparkMax left3;
  private CANSparkMax right1;
  private CANSparkMax right2;
  private CANSparkMax right3;

  //encoders
  private CANEncoder encoderLeft1;
  private CANEncoder encoderLeft2;
  private CANEncoder encoderLeft3;
  private CANEncoder encoderRight1;
  private CANEncoder encoderRight2;
  private CANEncoder encoderRight3;
  
  // Gyro
  private AHRS ahrs;

  // PIDz
  private PIDController visionPID = new PIDController(0.019, 0.08, 0.0085);
  private PIDController headingPID = new PIDController(.15, 0, 0);
  private PIDController drivingPID = new PIDController(1, 0, 0);

  // Shuffleboard
  private ShuffleboardTab tab = Shuffleboard.getTab("Drivetrain Tab");
  
  private NetworkTableEntry visionConstraintMax = tab.addPersistent("VisionPIDMax", Constants.visionPIDconstraints[1]).getEntry();
  private NetworkTableEntry visionConstraintMin = tab.addPersistent("VisionPIDMin", Constants.visionPIDconstraints[0]).getEntry();
  private NetworkTableEntry headingConstraintMin = tab.addPersistent("HeadingPIDMin", Constants.headingPIDconstraints[0]).getEntry();
  private NetworkTableEntry headingConstraintMax = tab.addPersistent("HeadingPIDmax", Constants.headingPIDconstraints[1]).getEntry();
  private NetworkTableEntry drivingConstraintMin = tab.addPersistent("DrivingPIDMin", Constants.drivingPIDconstraints[0]).getEntry();
  private NetworkTableEntry drivingConstraintMax = tab.addPersistent("DrivingPIDMax", Constants.drivingPIDconstraints[1]).getEntry();
  private NetworkTableEntry visionKp = tab.addPersistent("VisionKp", Constants.visionPID[0]).getEntry();
  private NetworkTableEntry visionKi = tab.addPersistent("VisionKi", Constants.visionPID[1]).getEntry();
  private NetworkTableEntry visionKd = tab.addPersistent("VisionKd", Constants.visionPID[2]).getEntry();
  private NetworkTableEntry visionError = tab.addPersistent("Vision Error", 0).getEntry();
  private NetworkTableEntry drivingKp = tab.addPersistent("DrivingKp", Constants.drivingPID[0]).getEntry();
  private NetworkTableEntry drivingKi = tab.addPersistent("DrivingKi", Constants.drivingPID[1]).getEntry();
  private NetworkTableEntry drivingKd = tab.addPersistent("DrivingKd", Constants.drivingPID[2]).getEntry();
  private NetworkTableEntry headingKp = tab.addPersistent("HeadingKp", Constants.headingPID[0]).getEntry();
  private NetworkTableEntry headingKi = tab.addPersistent("HeadingKi", Constants.headingPID[1]).getEntry();
  private NetworkTableEntry headingKd = tab.addPersistent("HeadingKd", Constants.headingPID[2]).getEntry();
  private NetworkTableEntry hdgTolerance = tab.addPersistent("hdgTolerance", Constants.headingTolerance[0]).getEntry();
  private NetworkTableEntry hdgVTolerance = tab.addPersistent("hdgVTolerance", Constants.headingTolerance[1]).getEntry();
  private NetworkTableEntry vsnTolerance = tab.addPersistent("vsnTolerance", Constants.visionTolerance[0]).getEntry();
  private NetworkTableEntry vsnVTolerance = tab.addPersistent("vsnVTolerance", Constants.visionTolerance[1]).getEntry();
  private NetworkTableEntry drvTolerance = tab.addPersistent("drvTolerance", Constants.drivingTolerance[0]).getEntry();
  private NetworkTableEntry drvVTolerance = tab.addPersistent("drvVTolerance", Constants.drivingTolerance[1]).getEntry();
  private NetworkTableEntry rightTickConstant = tab.addPersistent("RTickConstant", Constants.rightTickConstant).getEntry();
  private NetworkTableEntry leftTickConstant = tab.addPersistent("LTickConstant", Constants.leftTickConstant).getEntry();
  private NetworkTableEntry leftEncoderValue = tab.addPersistent("LeftEncoder", 0).getEntry();
  private NetworkTableEntry rightEncoderValue = tab.addPersistent("RightEncoder", 0).getEntry();
  private NetworkTableEntry encoderaverage = tab.addPersistent("encoderaverage", 0).getEntry();
  private NetworkTableEntry throughBoreRight = tab.addPersistent("Through Bore Right", 0).getEntry();
  private NetworkTableEntry throughBoreLeft = tab.addPersistent("Through Bore Left", 0).getEntry();
  private NetworkTableEntry rTickBoreConstant = tab.addPersistent("RTickBoreConstant", Constants.rTickBoreConstant).getEntry();
  private NetworkTableEntry lTickBoreConstant = tab.addPersistent("LTickBoreConstant", Constants.lTickBoreConstant).getEntry();

  private NetworkTableEntry resetGyroCommandEntry = tab.add("Reset Gyro", false).withWidget(BuiltInWidgets.kToggleButton).getEntry();
  
  private ShuffleboardTab toggleTab = Shuffleboard.getTab("Toggle Tab");
  private NetworkTableEntry toggleDiag = toggleTab.add("Comp Mode?", false).withWidget(BuiltInWidgets.kToggleButton).getEntry();

  // Powers for scaling inputs
  private double leftPow = 0;
  private double rightPow = 0;
  
  // Encoder test
  private Encoder throughboreRight = new Encoder(6, 8);
  private Encoder throughboreLeft = new Encoder(2,3);

  // VisIoN!
  private Limelight limelight = new Limelight();

  /** A Drivetrain subsystem class */
  public Drivetrain(int left1Port, int left2Port, int left3Port, int right1Port, int right2Port, int right3Port) {
    // Drivetrain motors
    CANSparkMax left1 = new CANSparkMax(left1Port, MotorType.kBrushless);
    CANSparkMax left2 = new CANSparkMax(left2Port, MotorType.kBrushless);
    CANSparkMax left3 = new CANSparkMax(left3Port, MotorType.kBrushless);
    CANSparkMax right1 = new CANSparkMax(right1Port, MotorType.kBrushless);
    CANSparkMax right2 = new CANSparkMax(right2Port, MotorType.kBrushless);
    CANSparkMax right3 = new CANSparkMax(right3Port, MotorType.kBrushless);
    
    // Drivetrain motor encoders
    CANEncoder encoderLeft1 = new CANEncoder(left1);
    CANEncoder encoderLeft2 = new CANEncoder(left2);
    CANEncoder encoderLeft3 = new CANEncoder(left3);
    CANEncoder encoderRight1 = new CANEncoder(right1);
    CANEncoder encoderRight2 = new CANEncoder(right2);
    CANEncoder encoderRight3 = new CANEncoder(right3);
    
    // Actual drivetrain stuff
    private SpeedControllerGroup leftSide = new SpeedControllerGroup(left1, left2, left3);
    private SpeedControllerGroup rightSide = new SpeedControllerGroup(right1, right2, right3);
    private DifferentialDrive drivetrain = new DifferentialDrive(leftSide, rightSide);

    // Sets the error tolerance to 5, and the error derivative tolerance to 10 per second
    headingPID.setTolerance(2, 5);
    headingPID.setIntegratorRange(-0.5, 0.5);

    // Enables continuous input on a range from -180 to 180
    headingPID.enableContinuousInput(-180, 180);
    
    // Sets the error tolerance to 5, and the error derivative tolerance to 10 per second
    drivingPID.setTolerance(2, 5);
    drivingPID.setIntegratorRange(-0.5, 0.5);
    // Enables continuous input on a range from -180 to 180
    //drivingPID.enableContinuousInput(-180, 180);

    // Encoder initialize stuff
    encoderLeft1.setPositionConversionFactor(1);
    encoderLeft2.setPositionConversionFactor(1);
    encoderLeft3.setPositionConversionFactor(1);
    encoderRight1.setPositionConversionFactor(1);
    encoderRight2.setPositionConversionFactor(1);
    encoderRight3.setPositionConversionFactor(1);
    
    //reset for shuffleboard
    encoderLeft1.setPosition(0);
    encoderLeft2.setPosition(0);
    encoderLeft3.setPosition(0);
    encoderRight1.setPosition(0);
    encoderRight2.setPosition(0);
    encoderRight3.setPosition(0);
    
    //through bore encoder
    throughboreRight.reset();
    throughboreLeft.reset();

    // Initialize gyro
    ahrs = new AHRS(SPI.Port.kMXP);
  }

  @Override
  public void periodic() {
    //ooooooooof get ready
    
    leftEncoderValue.setDouble(leftEncoderAverage());
    rightEncoderValue.setDouble(rightEncoderAverage());
    encoderaverage.setDouble(encoderAverage());
    throughBoreLeft.setDouble(throughboreLeft.getDistance());
    throughBoreRight.setDouble(throughboreRight.getDistance());

    // Constants.headingIntegrator[0] = hIntegratorMin.getDouble(Constants.headingIntegrator[0]);
    // Constants.headingIntegrator[1] = hIntegratorMax.getDouble(Constants.headingIntegrator[1]);
    //no more constraints (what we clamp to in the PID stuff using Math.clamp())

    Constants.headingPIDconstraints[0] = headingConstraintMin.getDouble(Constants.headingPIDconstraints[0]);
    Constants.headingPIDconstraints[1] = headingConstraintMax.getDouble(Constants.headingPIDconstraints[1]);

    Constants.visionPIDconstraints[0] = visionConstraintMin.getDouble(Constants.visionPIDconstraints[0]);
    Constants.visionPIDconstraints[1] = visionConstraintMax.getDouble(Constants.visionPIDconstraints[1]);

    Constants.drivingPIDconstraints[0] = drivingConstraintMin.getDouble(Constants.drivingPIDconstraints[0]);
    Constants.drivingPIDconstraints[1] = drivingConstraintMax.getDouble(Constants.drivingPIDconstraints[1]);

    // If comp mode is true
    if(toggleDiag.getBoolean(false)) { 
      continue; // skip the rest for speeeed
    }

    // now for changing the PID values on robot and in Constants.java. this is going to be _very_ long
    
    double tempVP = visionKp.getDouble(Constants.visionPID[0]);
    if(Constants.visionPID[0] != tempVP) {
      Constants.visionPID[0] = tempVP;
      visionPID.setP(Constants.visionPID[0]);
    }

    double tempVI = visionKi.getDouble(Constants.visionPID[1]);
    if(Constants.visionPID[1] != tempVI) {
      Constants.visionPID[1] = tempVI;
      visionPID.setI(Constants.visionPID[1]);
    }

    double tempVD = visionKd.getDouble(Constants.visionPID[2]);
    if(Constants.visionPID[2] != tempVD) {
      Constants.visionPID[2] = tempVD;
      visionPID.setD(Constants.visionPID[2]);
    }

    double tempVisionTolerance = vsnTolerance.getDouble(Constants.visionTolerance[0]);
    if(Constants.visionTolerance[0] != tempVisionTolerance) {
      Constants.visionTolerance[0] = tempVisionTolerance;
      visionPID.setTolerance(Constants.visionTolerance[0], Constants.visionTolerance[1]);
    }

    double tempVisionVTolerance = vsnVTolerance.getDouble(Constants.visionTolerance[1]);
    if(Constants.visionTolerance[1] != tempVisionVTolerance) {
      Constants.visionTolerance[1] = tempVisionVTolerance;
      visionPID.setTolerance(Constants.visionTolerance[0], Constants.visionTolerance[1]);
    }

    //end visionPID. now for driving

    double tempDP = drivingKp.getDouble(Constants.drivingPID[0]);
    if(Constants.drivingPID[0] != tempDP) {
      Constants.drivingPID[0] = tempDP;
      drivingPID.setP(Constants.drivingPID[0]);
    }

    double tempDI = drivingKi.getDouble(Constants.drivingPID[1]);
    if(Constants.drivingPID[1] != tempDI) {
      Constants.drivingPID[1] = tempDI;
      drivingPID.setI(Constants.drivingPID[1]);
    }

    double tempDD = drivingKd.getDouble(Constants.drivingPID[2]);
    if(Constants.drivingPID[2] != tempDD) {
      Constants.drivingPID[2] = tempDD;
      drivingPID.setD(Constants.drivingPID[2]);
    }

    double tempDrivingTolerance = drvTolerance.getDouble(Constants.drivingTolerance[0]);
    if(Constants.drivingTolerance[0] != tempDrivingTolerance) {
      Constants.drivingTolerance[0] = tempDrivingTolerance;
      drivingPID.setTolerance(Constants.drivingTolerance[0], Constants.drivingTolerance[1]);
    }

    double tempDrivingVTolerance = drvVTolerance.getDouble(Constants.drivingTolerance[1]);
    if(Constants.drivingTolerance[1] != tempDrivingVTolerance) {
      Constants.drivingTolerance[1] = tempDrivingVTolerance;
      drivingPID.setTolerance(Constants.drivingTolerance[0], Constants.drivingTolerance[1]);
    }

    //heading PID stuff
    double tempHP = headingKp.getDouble(Constants.headingPID[0]);
    if(Constants.headingPID[0] != tempHP) {
      Constants.headingPID[0] = tempHP;
      headingPID.setP(Constants.headingPID[0]);
    }

    double tempHI = headingKi.getDouble(Constants.headingPID[1]);
    if(Constants.headingPID[1] != tempHI) {
      Constants.headingPID[1] = tempHI;
      headingPID.setI(Constants.headingPID[1]);
    }

    double tempHD = headingKd.getDouble(Constants.headingPID[2]);
    if(Constants.headingPID[2] != tempHD) {
      Constants.headingPID[2] = tempHD;
      headingPID.setD(Constants.headingPID[2]);
    }

    double tempHeadingTolerance = hdgTolerance.getDouble(Constants.headingTolerance[0]);
    if(Constants.headingTolerance[0] != tempHeadingTolerance) {
      Constants.headingTolerance[0] = tempHeadingTolerance;
      headingPID.setTolerance(Constants.headingTolerance[0], Constants.headingTolerance[1]);
    }

    double tempHeadingVTolerance = hdgVTolerance.getDouble(Constants.headingTolerance[1]);
    if(Constants.headingTolerance[1] != tempHeadingVTolerance) {
      Constants.headingTolerance[1] = tempHeadingVTolerance;
      headingPID.setTolerance(Constants.headingTolerance[0], Constants.headingTolerance[1]);
    }

    //tick constants

    double tempRTickConstant = rightTickConstant.getDouble(Constants.rightTickConstant);
    if(Constants.rightTickConstant != tempRTickConstant) {
      Constants.rightTickConstant = tempRTickConstant;
      encoderRight1.setPositionConversionFactor(Constants.rightTickConstant);
      encoderRight2.setPositionConversionFactor(Constants.rightTickConstant);
      encoderRight3.setPositionConversionFactor(Constants.rightTickConstant);
    }

    double tempLTickConstant = leftTickConstant.getDouble(Constants.leftTickConstant);
    if(Constants.leftTickConstant != tempLTickConstant) {
      Constants.leftTickConstant = tempLTickConstant;
      encoderLeft1.setPositionConversionFactor(Constants.leftTickConstant);
      encoderLeft2.setPositionConversionFactor(Constants.leftTickConstant);
      encoderLeft3.setPositionConversionFactor(Constants.leftTickConstant);
    }

    double tempRTickBoreConstant = rTickBoreConstant.getDouble(Constants.rTickBoreConstant);
    if(Constants.rTickBoreConstant != tempRTickBoreConstant) {
      Constants.rTickBoreConstant = tempRTickBoreConstant;
      throughboreRight.setDistancePerPulse(Constants.rTickBoreConstant);
    }

    double tempLTickBoreConstant = lTickBoreConstant.getDouble(Constants.lTickBoreConstant);
    if(Constants.lTickBoreConstant != tempLTickBoreConstant) {
      Constants.lTickBoreConstant = tempLTickBoreConstant;
      throughboreLeft.setDistancePerPulse(Constants.lTickBoreConstant);
    }


    if(resetGyroCommandEntry.getBoolean(false)) { 
      this.resetGyro();
      resetGyroCommandEntry.setBoolean(true);
    }

    //dang that is some messy code
  }

  /**
   * Resets our gyro (AHRS) so we don't kill people in auto
   */
  public boolean resetGyro() {
    ahrs.reset();
    return true;
  }

  /**
   * Reset the headingPID
   */
  public void headingPIDReset() {
    headingPID.reset();
  }

  /**
   * Reset the driving PID
   */
  public void drivingPIDReset() {
    drivingPID.reset();
  }
  
  /**
   * Resets the vision PID
   */
  public void visionPIDReset() {
    visionPID.reset();
  }
  
  /**
   * Reset the encoders
   */
  public void encoderReset(){
    encoderLeft1.setPosition(0);
    encoderLeft2.setPosition(0);
    encoderLeft3.setPosition(0);
    encoderRight1.setPosition(0);
    encoderRight2.setPosition(0);
    encoderRight3.setPosition(0);
  }

  /**
   * Does the actual driving part
   * @param leftStick value of the left stick Y axis
   * @param rightStick value of the right stick Y axis
   */
  public void tankDrive(double leftStick, double rightStick) {
    //scale inputs (needs to be non-even number, otherwise we can't drive backwards because -1 ** 2 == 1 != -1)
    drivetrain.tankDrive(Math.pow(leftStick, 3), Math.pow(rightStick, 3)); //actually drive
  }

  /** Aligns us to the target using the LL subsystem
   * The command that runs this is responsible for deciding if we should be zoomed or unzoomed, and when to stop
   * Stopping should be based on the return value of this function
   * @return if we are aligned or not
   */
  public boolean alignToTarget() {
    double offset = limelight.getXOffset();
    drivetrain.arcadeDrive(0, MathUtil.clamp(visionPID.calculate(offset), Constants.visionConstraints[0], Constants.visionConstraints[1]));
    return limelight.isAligned();
  }

  /**
   * Gives average of left and right encoders
   * Calls leftEncoderAverage() and rightEncoderAverage() and averages them
   * @return L and R encoder average
   */
  public double encoderAverage() {
    return (this.leftEncoderAverage() + this.rightEncoderAverage()) / 2;
  }

  /**
   * Gives average of left encoders
   * @return average of left encoders
   */
  public double leftEncoderAverage() {
    return (encoderLeft1.getPosition() + encoderLeft2.getPosition() + encoderLeft3.getPosition()) / 3;
  }

  /**
   * Gives average of right encoders
   * @return average of right encoders
   */
  public double rightEncoderAverage() {
    return (encoderRight1.getPosition() + encoderRight2.getPosition() + encoderRight3.getPosition()) / 3;
  }

  /**
   * Drives the robot on a heading
   * @param power the speed to drive at
   * @param angle the angle to drive on/turn to
   * @return if the robot is on the heading or is off by a few degs
   */
  public boolean driveOnHeading(double power, double angle) {
    double turnPower = MathUtil.clamp(headingPID.calculate(ahrs.getYaw(), angle), Constants.headingPIDconstraints[0], Constants.headingPIDconstraints[1]);
    drivetrain.arcadeDrive(power, turnPower);
    return headingPID.atSetpoint();
  }

  /**
   * Drives the robot a distance on an angle
   * @param distance how far to drive in inches
   * @param angle the angle to rive on
   * @return if we are aligned and have reached the distance
   */
  public boolean driveDistance(double distance, double angle) {
    double drivePower = MathUtil.clamp(drivingPID.calculate(this.encoderAverage(), angle), -0.5, 0.5);
    boolean headingAligned = this.driveOnHeading(drivePower, angle);
    return drivingPID.atSetpoint() && headingAligned;
  }
}