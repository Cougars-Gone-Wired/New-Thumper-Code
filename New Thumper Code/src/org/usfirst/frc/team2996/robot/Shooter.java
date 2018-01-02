package org.usfirst.frc.team2996.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {

	// object declarations
	private Joystick manipulatorStick;
	private CANTalon shooterMotor;
	private CANTalon augerMotor;

	public Shooter(Robot robot) {
		// assigning local variables to variables in the robot class
		this.manipulatorStick = robot.getManipulatorStick();
		this.shooterMotor = robot.getShooterMotor();
		this.augerMotor = robot.getAugerMotor();
	}

	public void shooter() { // spins shooter if button held, does nothing if otherwise
		if (manipulatorStick.getRawButton(Robot.SHOOTER_BUTTON)) {
			shooterMotor.set(SmartDashboard.getNumber("Shooter Speed", 0.5));
		} else {
			shooterMotor.set(0);
		}
	}

	public void auger() { // doesn't do anything if no bumpers or both bumpers are pressed, intakes if
							// right bumper, outtakes if left bumper
		if (manipulatorStick.getRawButton(Robot.AUGER_IN_BUMPER)
				&& manipulatorStick.getRawButton(Robot.AUGER_OUT_BUMPER)) {
			augerMotor.set(0);
		} else if (manipulatorStick.getRawButton(Robot.AUGER_IN_BUMPER)) {
			augerMotor.set(0.8);
		} else if (manipulatorStick.getRawButton(Robot.AUGER_OUT_BUMPER)) {
			augerMotor.set(-0.8);
		} else {
			augerMotor.set(0);
		}
	}
}
