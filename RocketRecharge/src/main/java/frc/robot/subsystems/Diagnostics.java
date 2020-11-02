package frc.robot.subsystems;

import edu.wpi.first.hal.can.CANJNI;
import edu.wpi.first.hal.can.CANStatus;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Diagnostics extends SubsystemBase {
    private PowerDistributionPanel PDP = new PowerDistributionPanel(0);
    private CANStatus canStatus = new CANStatus();

    private ShuffleboardTab tab = Shuffleboard.getTab("PDP");
    private NetworkTableEntry port0 = tab.addPersistent("Port 0", 0).getEntry();
    private NetworkTableEntry port1 = tab.addPersistent("Port 1", 0).getEntry();
    private NetworkTableEntry port2 = tab.addPersistent("Port 2", 0).getEntry();
    private NetworkTableEntry port3 = tab.addPersistent("Port 3", 0).getEntry();
    private NetworkTableEntry port4 = tab.addPersistent("Port 4", 0).getEntry();
    private NetworkTableEntry port5 = tab.addPersistent("Port 5", 0).getEntry();
    private NetworkTableEntry port6 = tab.addPersistent("Port 6", 0).getEntry();
    private NetworkTableEntry port7 = tab.addPersistent("Port 7", 0).getEntry();
    private NetworkTableEntry port8 = tab.addPersistent("Port 8", 0).getEntry();
    private NetworkTableEntry port9 = tab.addPersistent("Port 9", 0).getEntry();
    private NetworkTableEntry port10 = tab.addPersistent("Port 10", 0).getEntry();
    private NetworkTableEntry port11 = tab.addPersistent("Port 11", 0).getEntry();
    private NetworkTableEntry port12 = tab.addPersistent("Port 12", 0).getEntry();
    private NetworkTableEntry port13 = tab.addPersistent("Port 13", 0).getEntry();
    private NetworkTableEntry port14 = tab.addPersistent("Port 14", 0).getEntry();
    private NetworkTableEntry port15 = tab.addPersistent("Port 15", 0).getEntry();

    private NetworkTableEntry totalEnergy = tab.addPersistent("Total Energy", 0).getEntry();
    private NetworkTableEntry inputVoltage = tab.addPersistent("Input Voltage", 0).getEntry();
    private NetworkTableEntry totalCurrent = tab.addPersistent("Total Current", 0).getEntry();
    private NetworkTableEntry totalPower = tab.addPersistent("Total Power", 0).getEntry();
    private NetworkTableEntry temperature = tab.addPersistent("Temperature", 0).getEntry();

    private NetworkTableEntry percentBus = tab.addPersistent("Percent Bus Utilization", 0).getEntry();
    private NetworkTableEntry busOff = tab.addPersistent("Bus Off Count", 0).getEntry();
    private NetworkTableEntry recieveError = tab.addPersistent("Recieve Error Count", 0).getEntry();
    private NetworkTableEntry transmitError = tab.addPersistent("Transmit Error Count", 0).getEntry();
    private NetworkTableEntry fullCount = tab.addPersistent("TX Full Count", 0).getEntry();
  
    public Diagnostics() {
    }
    
    @Override
    public void periodic() {
        port0.setDouble(PDP.getCurrent(0));
        port1.setDouble(PDP.getCurrent(1));
        port2.setDouble(PDP.getCurrent(2));
        port3.setDouble(PDP.getCurrent(3));
        port4.setDouble(PDP.getCurrent(4));
        port5.setDouble(PDP.getCurrent(5));
        port6.setDouble(PDP.getCurrent(6));
        port7.setDouble(PDP.getCurrent(7));
        port8.setDouble(PDP.getCurrent(8));
        port9.setDouble(PDP.getCurrent(9));
        port10.setDouble(PDP.getCurrent(10));
        port11.setDouble(PDP.getCurrent(11));
        port12.setDouble(PDP.getCurrent(12));
        port13.setDouble(PDP.getCurrent(13));
        port14.setDouble(PDP.getCurrent(14));
        port15.setDouble(PDP.getCurrent(15));
        
        temperature.setDouble(PDP.getTemperature());
        totalEnergy.setDouble(PDP.getTotalEnergy());
        inputVoltage.setDouble(PDP.getVoltage());
        totalCurrent.setDouble(PDP.getTotalCurrent());
        totalPower.setDouble(PDP.getTotalPower());
        
        CANJNI.GetCANStatus(canStatus);
        percentBus.setDouble(canStatus.percentBusUtilization);
        busOff.setDouble(canStatus.busOffCount);
        transmitError.setDouble(canStatus.transmitErrorCount);
        recieveError.setDouble(canStatus.receiveErrorCount);
        fullCount.setDouble(canStatus.txFullCount);
    }
}