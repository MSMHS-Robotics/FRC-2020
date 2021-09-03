package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
    private AHRS ahrs;
    private ShuffleboardTab tab = Shuffleboard.getTab("Drivetrain Tab");
    private NetworkTableEntry resetGyroCommandEntry = tab.add("Reset Gyro", false).withWidget(BuiltInWidgets.kToggleButton).getEntry();

    private final CANSparkMax left1 = new CANSparkMax(1, MotorType.kBrushless);
    private final CANSparkMax left2 = new CANSparkMax(2, MotorType.kBrushless);
    private final CANSparkMax left3 = new CANSparkMax(3, MotorType.kBrushless);
    private final CANSparkMax right1 = new CANSparkMax(4, MotorType.kBrushless);
    private final CANSparkMax right2 = new CANSparkMax(5, MotorType.kBrushless);
    private final CANSparkMax right3 = new CANSparkMax(6, MotorType.kBrushless);

    private final CANEncoder encoderLeft1 = new CANEncoder(left1);
    private final CANEncoder encoderLeft2 = new CANEncoder(left2);
    private final CANEncoder encoderLeft3 = new CANEncoder(left3);
    private final CANEncoder encoderRight1 = new CANEncoder(right1);
    private final CANEncoder encoderRight2 = new CANEncoder(right2);
    private final CANEncoder encoderRight3 = new CANEncoder(right3);

    private double leftPow = 0;
    private double rightPow = 0;

    private SpeedControllerGroup leftSide = new SpeedControllerGroup(left1, left2, left3);
    private SpeedControllerGroup rightSide = new SpeedControllerGroup(right1, right2, right3);

    private Encoder throughboreRight = new Encoder(6, 8);
    private Encoder throughboreLeft = new Encoder(2, 3);

    private DifferentialDrive drivetrain = new DifferentialDrive(leftSide, rightSide);

    public Drivetrain() {
        encoderLeft1.setPositionConversionFactor(1);
        encoderLeft2.setPositionConversionFactor(1);
        encoderLeft3.setPositionConversionFactor(1);
        encoderRight1.setPositionConversionFactor(1);
        encoderRight2.setPositionConversionFactor(1);
        encoderRight3.setPositionConversionFactor(1);

        // through bore encoder
        throughboreRight.reset();
        throughboreLeft.reset();

        ahrs = new AHRS(SPI.Port.kMXP);
    }

    @Override
    public void periodic() {
        if (resetGyroCommandEntry.getBoolean(false)) {
            this.resetGyro();
            resetGyroCommandEntry.setBoolean(true);
        }
    }

    public void encoderReset() {
        encoderLeft1.setPosition(0);
        encoderLeft2.setPosition(0);
        encoderLeft3.setPosition(0);
        encoderRight1.setPosition(0);
        encoderRight2.setPosition(0);
        encoderRight3.setPosition(0);
    }

    public void tankDrive(double leftStick, double rightStick) {
        drivetrain.tankDrive(leftPow, rightPow, false); // don't auto-square inputs
    }

    /**
     * Gets the encoder average
     * @return the average of the left and right encoders
     */
    public double encoderAverage() {
        return (leftEncoderAverage() + rightEncoderAverage()) / 2;
    }

    /**
     * Gets the left encoder average
     * @return the average of the left encoders
     */

    public double leftEncoderAverage() {
        return (encoderLeft1.getPosition() + encoderLeft2.getPosition() + encoderLeft3.getPosition()) / 3;
    }

    /**
     * Gets the right side encoder average
     * @return the average of the right encoders
     */
    public double rightEncoderAverage() {
        double average = encoderRight1.getPosition();
        average += encoderRight2.getPosition();
        average += encoderRight3.getPosition();
        average /= 3;
        return average;
    }

    /**
     * Resets our gyro (AHRS) so we don't kill people in auto
     */
    public boolean resetGyro() {
        ahrs.reset();
        return true;
    }
}
