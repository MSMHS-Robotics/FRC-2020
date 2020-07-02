package frc.robot.subsystems;

// Motors
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;

// Shuffleboard
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

// Other
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Timer;
import java.util.Map;

/** A Shooter subsystem class */
public class Shooter extends SubsystemBase {
  // Motors
  private CANSparkMax shooterMotor;
  private CANSparkMax shooterMotor2;
  private WPI_TalonSRX angleMotor;

  // Control
  private CANPIDController shooterPID;
  private CANPIDController anglePID;
  private CANEncoder encoder;
  
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
  private double RPMSetpoint;
  private double angleSetpoint;
  private Boolean shootingFlag;

  private double pastTime = 0;
  private double pastVelocity = 0;
  private double shotAcceleration = 0;

  // Shuffleboard
  private ShuffleboardTab tab1 = Shuffleboard.getTab("Shooter");
  private NetworkTableEntry ShooterkP = tab1.addPersistent("ShooterkP", Constants.ShooterkP).getEntry();
  private NetworkTableEntry kPdivide = tab1.addPersistent("kPdivide", Constants.ShooterkPdivide).getEntry();
  private NetworkTableEntry ShooterkI = tab1.addPersistent("ShooterkI", Constants.ShooterkI).getEntry();
  private NetworkTableEntry kIdivide = tab1.addPersistent("kIdivide", Constants.ShooterkIdivide).getEntry();
  private NetworkTableEntry ShooterkD = tab1.addPersistent("ShooterkD", Constants.ShooterkD).getEntry();
  private NetworkTableEntry kDdivide = tab1.addPersistent("kDdivide", Constants.ShooterkDdivide).getEntry();
  private NetworkTableEntry ShooterkIz = tab1.addPersistent("ShooterkIz", Constants.ShooterkIz).getEntry();
  private NetworkTableEntry ShooterkFF = tab1.addPersistent("ShooterkFF", Constants.ShooterkFF).getEntry();
  private NetworkTableEntry kFFdivide = tab1.addPersistent("kFFdivide", Constants.ShooterkFFdivide).getEntry();
  private NetworkTableEntry ShooterkMaxOutput = tab1.addPersistent("ShooterkMaxOutput", Constants.ShooterkMaxOutput).getEntry();
  private NetworkTableEntry ShooterkMinOutput = tab1.addPersistent("ShooterkMinOutput", Constants.ShooterkMinOutput).getEntry();
  private NetworkTableEntry RPMTolerance = tab1.addPersistent("RPMTolerance", Constants.RPMTolerance).getEntry();
  private NetworkTableEntry ShooterRPM = tab1.addPersistent("ShooterRPM", 0).getEntry();
  private NetworkTableEntry neededRPM = tab1.addPersistent("Vision Needed RPM", 0).getEntry();
  private NetworkTableEntry ShotAcceleration = tab1.addPersistent("Shot Acceleration", 0).getEntry();
  private NetworkTableEntry AccelerationTolerance = tab1.addPersistent("Acceleration Tolerance",Constants.accelerationTolerance).getEntry();
  private NetworkTableEntry isShooterGood = tab1.addPersistent("is Shooter Good", false).withWidget("Boolean Box").withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();
  private NetworkTableEntry isShooting = tab1.addPersistent("Shooter is Shooting", false).withWidget("Boolean Box").withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red")).getEntry();
  
  private NetworkTableEntry AnglekP = tab1.addPersistent("AnglekP", Constants.AnglekP).getEntry();
  private NetworkTableEntry AnglekI = tab1.addPersistent("AnglekI", Constants.AnglekI).getEntry();
  private NetworkTableEntry AnglekD = tab1.addPersistent("AnglekD", Constants.AnglekD).getEntry();

  private ShuffleboardTab tab2 = Shuffleboard.getTab("Shooter Presets");
  private NetworkTableEntry TrenchAngle = tab2.addPersistent("TrenchAngle", Constants.TrenchAngle).getEntry();
  private NetworkTableEntry TrenchRPM = tab2.addPersistent("TrenchRPM", Constants.TrenchRPM).getEntry();
  private NetworkTableEntry TenFootAngle = tab2.addPersistent("TenFootAngle", Constants.TenFootAngle).getEntry();
  private NetworkTableEntry TenFootRPM = tab2.addPersistent("TenFootRPM", Constants.TenFootRPM).getEntry();
  private NetworkTableEntry LayupAngle = tab2.addPersistent("LayupAngle", Constants.LayupAngle).getEntry();
  private NetworkTableEntry LayupRPM = tab2.addPersistent("LayupRPM", Constants.LayupRPM).getEntry();

  private ShuffleboardTab toggleTab = Shuffleboard.getTab("Toggle Tab");
	private NetworkTableEntry toggleDiag = toggleTab.add("Comp Mode?", false).withWidget(BuiltInWidgets.kToggleButton).getEntry();

  /** A Shooter subsystem class */
  public Shooter() {
    shootingFlag = false;
    shooterMotor = new CANSparkMax(7, MotorType.kBrushless);
    
    if (shooterMotor != null) { // Init motor
      shooterMotor.restoreFactoryDefaults();
      shooterMotor.setInverted(true); // Connected to other motor opposite it so this is good
      shooterPID = shooterMotor.getPIDController();
      encoder = shooterMotor.getEncoder();
    }

    // second motor
    shooterMotor2 = new CANSparkMax(8, MotorType.kBrushless);
    if (shooterMotor2 != null){
      shooterMotor2.restoreFactoryDefaults();
      shooterMotor2.follow(shooterMotor, true); // Set it to follow the first motor
    }

    // keep this here for now
    //angleMotor = new WPI_TalonSRX(16);

    // set PID coefficients
    if (shooterPID != null){
      shooterPID.setP(Constants.ShooterkP);
      shooterPID.setI(Constants.ShooterkI);
      shooterPID.setD(Constants.ShooterkD);
      shooterPID.setIZone(Constants.ShooterkIz);
      shooterPID.setFF(Constants.ShooterkFF);
      shooterPID.setOutputRange(Constants.ShooterkMinOutput, Constants.ShooterkMaxOutput);
    }
    
    /* 
    //angle motor config
    if (angleMotor != null){
      angleMotor.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    }
    
    //Config the peak and nominal outputs 
    if (angleMotor != null){
		  angleMotor.configNominalOutputForward(0, Constants.kTimeoutMs);
		  angleMotor.configNominalOutputReverse(0, Constants.kTimeoutMs);
		  angleMotor.configPeakOutputForward(1, Constants.kTimeoutMs);
		  angleMotor.configPeakOutputReverse(-1, Constants.kTimeoutMs);
    }
  
    //Config the Velocity closed loop gains in slot0
    if (angleMotor != null){ 
		  angleMotor.config_kF(Constants.kPIDLoopIdx, Constants.AnglekF, Constants.kTimeoutMs);
		  angleMotor.config_kP(Constants.kPIDLoopIdx, Constants.AnglekP, Constants.kTimeoutMs);
		  angleMotor.config_kI(Constants.kPIDLoopIdx, Constants.AnglekI, Constants.kTimeoutMs);
      angleMotor.config_kD(Constants.kPIDLoopIdx, Constants.AnglekD, Constants.kTimeoutMs);
    }
    */
  }
  
  /**
   * Warms up shooter to desired RPM
   * @param RPM how fast you want the wheels turning
   */
  public void warmUp(double RPM) { 
    if (shooterPID != null){
      shooterPID.setReference(RPM, ControlType.kVelocity);
    }
  }

  /*
  public boolean shooterAngle(double angle) {
    if (angleMotor != null){
      angleMotor.set(ControlMode.Position, angle);
      return Math.abs(angleMotor.getClosedLoopError()) < 1;
    }
    return false;
  }
  */

  /**
   * Stops the shooter motor by setting volts to it to 0
   */
  public void stopPlease() {
    if (shooterMotor != null) {
      shooterMotor.setVoltage(0);
    }
  }

  /** IDK what this does. Backup? */
  public void stopPercent() {
    if(shooterMotor != null) {
      shooterMotor.set(0);
    }
  }

  /**
   * Sets whether we are shooting or not
   * @param stillShooting if we are still shooting or not
   */
  public void setShootingFlag(Boolean stillShooting) {
    shootingFlag = stillShooting;
  }

  /**
   * Returns whether we be shootin or not
   * @return if we are shooting or not
   */
  public boolean isShooting() {
    return shootingFlag;
  }

  /**
   * Shoots at desired RPM
   * @param RPM how fast you want to shoot
   */
  public void customShot(double RPM) {
    warmUp(RPM);
    RPMSetpoint = RPM;
    neededRPM.setDouble(RPM);
  }

  /** Shoots with presets for the trench */
  public void trenchShot() {
    //shooterAngle(Constants.TrenchAngle);
    warmUp(Constants.TrenchRPM);
    RPMSetpoint = Constants.TrenchRPM;
    //angleSetpoint = Constants.TrenchAngle;
  }

  /** Shoots with presets for 10 feet */
  public void tenFootShot() {
    //shooterAngle(Constants.TenFootAngle);
    warmUp(Constants.TenFootRPM);
    RPMSetpoint = Constants.TenFootRPM;
    //angleSetpoint = Constants.TenFootAngle;
  }

  /** Shoots with presets for right in front of the goal */
  public void layupShot() {
    //shooterAngle(Constants.LayupAngle);
    warmUp(Constants.LayupRPM);
    RPMSetpoint = Constants.LayupRPM;
    //angleSetpoint = Constants.LayupAngle;
  }

  /**
   * Returns whether we have reached the needed RPM for shooting or not
   * @return whether we can shoot yet or not
   */
  public boolean isShooterGood() {
    if (encoder == null) {
      return false;
    }

    if (Math.abs(encoder.getVelocity() - RPMSetpoint) < Constants.RPMTolerance && Math.abs(shotAcceleration) < Constants.accelerationTolerance) {
      return true;
    }
    return false;   
  }

  // Shuffleboard
  @Override
  public void periodic() {
    double currentTime = Timer.getFPGATimestamp();
    double currentVelocity = shooterMotor.getEncoder().getVelocity();

    shotAcceleration = (currentVelocity - pastVelocity) / ((currentTime - pastTime) * 60);
    pastTime = currentTime;
    pastVelocity = currentVelocity;

    ShooterRPM.setDouble(currentVelocity);
    ShotAcceleration.setDouble(shotAcceleration);
    isShooterGood.setBoolean(isShooterGood());
    isShooting.setBoolean(shootingFlag);

		// If comp mode is true
		if(toggleDiag.getBoolean(false)) { 
			continue;
		}
  
    //now for changing the PID values on robot and in Constants.java. this is going to be _very_ long
    double tempSP = ShooterkP.getDouble(Constants.ShooterkP);
    if(Constants.ShooterkP != tempSP && shooterPID != null) {
      Constants.ShooterkP = tempSP;
      shooterPID.setP(Constants.ShooterkP/Constants.ShooterkPdivide);
    }

    double tempSPdivide = kPdivide.getDouble(Constants.ShooterkPdivide);
    if(Constants.ShooterkPdivide != tempSPdivide && shooterPID != null) {
      Constants.ShooterkPdivide = tempSPdivide;
      shooterPID.setP(Constants.ShooterkP/Constants.ShooterkPdivide);
    }

    double tempSFFdivide = kFFdivide.getDouble(Constants.ShooterkFFdivide);
    if(Constants.ShooterkFFdivide != tempSFFdivide && shooterPID != null) {
      Constants.ShooterkFFdivide = tempSFFdivide;
      shooterPID.setP(Constants.ShooterkP/Constants.ShooterkFFdivide);
    }

    double tempSI = ShooterkI.getDouble(Constants.ShooterkI);
    if(Constants.ShooterkI != tempSI && shooterPID != null) {
      Constants.ShooterkI = tempSI;
      shooterPID.setI(Constants.ShooterkI/Constants.ShooterkIdivide);
    }

    double tempSIdivide = kIdivide.getDouble(Constants.ShooterkIdivide);
    if(Constants.ShooterkIdivide != tempSIdivide && shooterPID != null) {
      Constants.ShooterkIdivide = tempSIdivide;
      shooterPID.setI(Constants.ShooterkI/Constants.ShooterkIdivide);
    }

    double tempSD = ShooterkD.getDouble(Constants.ShooterkD);
    if(Constants.ShooterkD != tempSD && shooterPID != null) {
      Constants.ShooterkD = tempSD;
      shooterPID.setD(Constants.ShooterkD/Constants.ShooterkDdivide);
    }

    double tempSDdivide = kDdivide.getDouble(Constants.ShooterkDdivide);
    if(Constants.ShooterkDdivide != tempSDdivide && shooterPID != null) {
      Constants.ShooterkDdivide = tempSDdivide;
      shooterPID.setD(Constants.ShooterkD/Constants.ShooterkDdivide);
    }

    double tempSIz = ShooterkIz.getDouble(Constants.ShooterkIz);
    if(Constants.ShooterkIz != tempSIz && shooterPID != null) {
      Constants.ShooterkIz = tempSIz;
      shooterPID.setIZone(Constants.ShooterkIz);
    }

    double tempSFF = ShooterkFF.getDouble(Constants.ShooterkFF);
    if(Constants.ShooterkFF != tempSFF && shooterPID != null) {
      Constants.ShooterkFF = tempSFF;
      shooterPID.setFF(Constants.ShooterkFF);
    }

    double tempSMax = ShooterkMaxOutput.getDouble(Constants.ShooterkMaxOutput);
    if(Constants.ShooterkMaxOutput != tempSMax && shooterPID != null) {
      Constants.ShooterkMaxOutput = tempSMax;
      shooterPID.setOutputRange(Constants.ShooterkMinOutput,Constants.ShooterkMaxOutput);
    }
  
    double tempSMin = ShooterkMinOutput.getDouble(Constants.ShooterkMinOutput);
    if(Constants.ShooterkMinOutput != tempSMin && shooterPID != null) {
      Constants.ShooterkMaxOutput = tempSMin;
      shooterPID.setOutputRange(Constants.ShooterkMinOutput, Constants.ShooterkMaxOutput);
    }

    double tempTolerance = RPMTolerance.getDouble(Constants.RPMTolerance);
    if(Constants.RPMTolerance != tempTolerance){
      Constants.RPMTolerance = tempTolerance;
    }

    double tempATolerance = AccelerationTolerance.getDouble(Constants.accelerationTolerance);
    if(Constants.accelerationTolerance != tempATolerance){
      Constants.accelerationTolerance = tempATolerance;
    }

    //angle motor
    double tempAkp = AnglekP.getDouble(Constants.AnglekP);
    if(Constants.AnglekP != tempAkp && anglePID != null) {
      Constants.AnglekP = tempAkp;
      anglePID.setP(Constants.AnglekP);
    }
    
    double tempAki = AnglekI.getDouble(Constants.AnglekI);
    if(Constants.AnglekI != tempAki && anglePID != null) {
      Constants.AnglekI = tempAki;
      anglePID.setI(Constants.AnglekI);
    }

    double tempAkd = AnglekD.getDouble(Constants.AnglekD);
    if(Constants.AnglekD != tempAkd && anglePID != null) {
      Constants.AnglekD = tempAkd;
      anglePID.setD(Constants.AnglekD);
    }
    
    
    //Preset
    double tempTrenchAngle = TrenchAngle.getDouble(Constants.TrenchAngle);
    if(Constants.TrenchAngle != tempTrenchAngle) {
      Constants.TrenchAngle = tempTrenchAngle;
    }

    double tempTrenchRPM = TrenchRPM.getDouble(Constants.TrenchRPM);
    if(Constants.TrenchRPM != tempTrenchRPM){
      Constants.TrenchRPM = tempTrenchRPM;
    }

    double tempTenFootAngle = TenFootAngle.getDouble(Constants.TenFootAngle);
    if(Constants.TenFootAngle != tempTenFootAngle) {
      Constants.TenFootAngle = tempTenFootAngle;
    }

    double tempTenFootRPM = TenFootRPM.getDouble(Constants.TenFootRPM);
    if(Constants.TenFootRPM != tempTenFootRPM){
      Constants.TenFootRPM = tempTenFootRPM;
    }

    double tempLayupAngle = LayupAngle.getDouble(Constants.LayupAngle);
    if(Constants.LayupAngle != tempLayupAngle) {
      Constants.LayupAngle = tempLayupAngle;
    }

    double tempLayupRPM = LayupRPM.getDouble(Constants.LayupRPM);
    if(Constants.LayupRPM != tempLayupRPM){
      Constants.LayupRPM = tempLayupRPM;
    }
  }
}