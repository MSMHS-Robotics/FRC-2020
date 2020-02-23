package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Diagnostics extends SubsystemBase {

    
    private PowerDistributionPanel PDP = new PowerDistributionPanel(0);

    public Diagnostics(){
        
    }

    @Override
    public void periodic() {
        SmartDashboard.putData(PDP);
        //channelZeroCurrent.getDouble();
    }
}