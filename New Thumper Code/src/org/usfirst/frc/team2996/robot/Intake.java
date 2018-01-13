package org.usfirst.frc.team2996.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;

public class Intake {

	// object declarations
	private Joystick manipulatorStick;
	private Toggle gearPanButton;
	private WPI_TalonSRX intakeMotor;
	private Solenoid gearPanSolenoid;

	public Intake(Robot robot) {
		// assigning local variables to variables in the robot class
		this.manipulatorStick = robot.getManipulatorStick();
		this.gearPanButton = robot.getGearPanButton();
		this.intakeMotor = robot.getIntakeMotor();
		this.gearPanSolenoid = robot.getGearPanSolenoid();
	}

	public void intakeOuttake() { // doesn't do anything if no triggers or both triggers are pressed, intakes if
									// right trigger, outtakes if left trigger
		if ((manipulatorStick.getRawAxis(Robot.INTAKE_TRIGGER) > 0.15)
				&& (manipulatorStick.getRawAxis(Robot.OUTTAKE_TRIGGER) > 0.15)) {
			intakeMotor.set(0);
		} else if (manipulatorStick.getRawAxis(Robot.INTAKE_TRIGGER) > 0.15) {
			intakeMotor.set(1);
		} else if (manipulatorStick.getRawAxis(Robot.OUTTAKE_TRIGGER) > 0.15) {
			intakeMotor.set(-1);
		} else {
			intakeMotor.set(0);
		}
	}

	public void gearPanUpDown() { // one button to activate and deactivate the gear pan solenoid
		if (gearPanButton.toggle()) {
			gearPanSolenoid.set(true);
		} else {
			gearPanSolenoid.set(false);
		}
	}
}
