package frc.robot.devices;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;

/** A CANEncoder subclass with some presets. I _really_ don't know what it does. */
public class RocketEncoder extends CANEncoder {
    private double positionOffset = 0.0;
    private double kPosition = 1;

    public RocketEncoder(CANSparkMax device) {
        super(device, EncoderType.kHallSensor, 11);
    }

    /**
     * Gives the current position of the encoder
     * @return current position of encoder
     */
    @Override
    public double getPosition() {
        return (super.getPosition() - positionOffset) * kPosition;
    }

    /** Resets position offset or something */
    public void reset(){
        positionOffset = super.getPosition();
    }

    /**
     * Sets the position or something
     * @param kPosition the new position
     */
    public void setPositionConstant(double kPosition) {
        this.kPosition = kPosition;
    }
}