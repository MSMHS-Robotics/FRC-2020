package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  private Victor intakeVictor;
  private Victor Victor1;
  private Victor Victor2;
  private Victor Victor3;
  private Victor Victor4;
  private Victor Victor5;
  private final int hasBall = 40; //the number that we get from IR sensor with a ball in front of it, change as needed to calibrate
  //private int cells[]; leave this alone

  public boolean autoMode = true; //what do i use this for again

  //now for sensors
  private AnalogInput sensor1;
  private AnalogInput sensor2;
  private AnalogInput sensor3;
  private AnalogInput sensor4;
  private AnalogInput sensor5;
  /**
   * Creates a new ExampleSubsystem.
   */
  public Intake() {
		intakeVictor = new Victor(0); //some motors
		Victor1 = new Victor(1);
		Victor2 = new Victor(2);
		Victor3 = new Victor(3);
		Victor4 = new Victor(4);
		Victor5 = new Victor(5);
  }

  public void IntakeAll() {
	Victor motors[] = {intakeVictor, Victor1, Victor2, Victor3, Victor4, Victor5};
	for(int i = 1; i < 5; i++) {
		motors[i].set(1);
	}
  }

  public void OutakeAll() {
	Victor motors[] = {intakeVictor, Victor1, Victor2, Victor3, Victor4, Victor5};
		for(int i = 0; i < 5; i++) {
			motors[i].set(-1);
		}
  }

  public void StopAll() {
	Victor motors[] = {intakeVictor, Victor1, Victor2, Victor3, Victor4, Victor5};
	for(int i = 0; i < 5; i++) {
		motors[i].set(0);
	}
  }

  public void IntakeIn() {
	  intakeVictor.set(1);
  }

  public void OutakeOut() {
	  intakeVictor.set(-1);
  }

  public void IntakeStop() {
	  intakeVictor.set(0);
  }

  public void runIndexMotor(int motor, int power) {
	Victor motors[] = {intakeVictor, Victor1, Victor2, Victor3, Victor4, Victor5};
	motors[motor].set(power);
  }

  public void indexCells() {
	boolean send[] = {false, false, false, false, false};
	boolean recieve[] = {false, false, false, false, false};
	Victor motors[] = {intakeVictor, Victor1, Victor2, Victor3, Victor4, Victor5};
	int FirstCell = sensor1.getValue(); //let's get some sensor values
	int SecondCell = sensor2.getValue();
	int ThirdCell = sensor3.getValue();
	int FourthCell = sensor4.getValue();
	int FifthCell = sensor5.getValue();
	int cells[] = {FirstCell, SecondCell, ThirdCell, FourthCell, FifthCell}; //just roll with it. an array of sensor values
	for(int i = 0; i < 4; i++) {
		if(cells[i] < hasBall && cells[++i] > hasBall) { //if cells[i] has a ball and the next one doesn't
			send[i] = true;
			recieve[++i] = true;
		}
	}
	for(int x = 0; x < 4; x++) {
		if(send[x] || recieve[x]) {
			motors[x].set(1);
		}
	}
  }

  @Override
	public void periodic() {
		// This method will be called once per scheduler run
		if(autoMode) {
			this.indexCells();
		}
	}
}