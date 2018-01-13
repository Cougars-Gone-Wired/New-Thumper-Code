package org.usfirst.frc.team2996.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;

public class Climber {

	// object declarations
	private Joystick mobilityStick;
	private WPI_TalonSRX climberMotor;

	public Climber(Robot robot) {
		// assigning local variable to variable in robot class
		this.mobilityStick = robot.getMobilityStick();
		this.climberMotor = robot.getClimberMotor();
	}

	public void climber() { // doesn't do anything if no triggers or both triggers are pressed, goes up if
							// right trigger, goes down if left trigger
		if ((mobilityStick.getRawAxis(Robot.CLIMB_UP_TRIGGER) > 0.15)
				&& (mobilityStick.getRawAxis(Robot.CLIMB_DOWN_TRIGGER) > 0.15)) {
			climberMotor.set(0);
		} else if (mobilityStick.getRawAxis(Robot.CLIMB_UP_TRIGGER) > 0.15) {
			climberMotor.set(mobilityStick.getRawAxis(Robot.CLIMB_UP_TRIGGER));
		} else if (mobilityStick.getRawAxis(Robot.CLIMB_DOWN_TRIGGER) > 0.15) {
			climberMotor.set(-mobilityStick.getRawAxis(Robot.CLIMB_DOWN_TRIGGER));
		} else {
			climberMotor.set(0);
		}
	}
}
