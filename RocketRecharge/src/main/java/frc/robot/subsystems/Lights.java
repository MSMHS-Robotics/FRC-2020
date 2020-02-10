package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;

public class Lights {
    private Spark blinkin;

    public Lights() {
        blinkin = new Spark(0);
    }

    public void setRedLarson() {
        blinkin.set(-0.25);
    }

    public void setGreen() {
        blinkin.set(0.73);
    }

    public void setFire() {
        blinkin.set(-0.57);
    }

    public void setGold() {
        blinkin.set(0.67);
    }

    public void setPink() {
        blinkin.set(0.57);
    }
}