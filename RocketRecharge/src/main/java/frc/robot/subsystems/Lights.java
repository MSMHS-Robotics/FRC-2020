package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;

public class Lights {
    private Spark blinkin;

    public Lights(int port) {
        blinkin = new Spark(port);
    }

    public void setRed() {
        blinkin.set(1);
    }

    public void setBlue() {
        blinkin.set(-1);
    }
}