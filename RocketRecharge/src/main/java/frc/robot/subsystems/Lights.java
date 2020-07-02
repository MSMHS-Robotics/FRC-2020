package frc.robot.subsystems;

// Imports
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** For use with the REV Blinkin */
public class Lights extends SubsystemBase {
    // Easiest to configure it as a Spark
    private Spark blinkin;

    /** For use with the REV Blinkin */
    public Lights() {
        blinkin = new Spark(0);
    }

    /** Sets the lights to Red Larson Scanner */
    public void setRedLarson() {
        blinkin.set(-0.25);
    }

    /** Sets the lights to Green */
    public void setGreen() {
        blinkin.set(0.73);
    }

    /** Sets the lights to Fire */
    public void setFire() {
        blinkin.set(-0.57);
    }

    /** Sets the lights to Gold */
    public void setGold() {
        blinkin.set(0.67);
    }

    /** Sets the lights to Pink */
    public void setPink() {
        blinkin.set(0.57);
    }

    /** Sets the lights to Rainbow */
    public void setRainbow() {
        blinkin.set(-0.45);
    }
}