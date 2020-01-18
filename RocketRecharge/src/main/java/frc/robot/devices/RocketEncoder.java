package frc.robot.devices;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

public class RocketEncoder extends CANEncoder {

    private double positionOffset = 0.0;
    private double kPosition = 1;

    public RocketEncoder(CANSparkMax device) {
        super(device);
    }

    @Override
    public double getPosition(){
        return (super.getPosition() - positionOffset) * kPosition;
    }

    public void reset(){
        positionOffset = super.getPosition();
    }

    public void setPositionConstant(double kPosition){
        this.kPosition = kPosition;
    }


}