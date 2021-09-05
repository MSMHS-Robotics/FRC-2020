package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//TODO documentation
public class Lights extends SubsystemBase {
    private Spark blinkin;
    private double val = 0; // TODO presets or something

    //TODO shuffleboard

    public Lights() {
        blinkin = new Spark(0);
    }

    //TODO documentation
    public void set(double val) {
        blinkin.set(val);
    }
}