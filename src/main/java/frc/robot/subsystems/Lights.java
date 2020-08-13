package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Lights extends SubsystemBase {
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

    public void setRainbow() {
        blinkin.set(-0.45);
    }
}