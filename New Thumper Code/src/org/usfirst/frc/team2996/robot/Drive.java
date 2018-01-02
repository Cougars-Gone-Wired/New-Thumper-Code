package org.usfirst.frc.team2996.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive {

	// object declarations
	private Joystick mobilityStick;
	private Toggle driveSwitchButton;
	private RobotDrive robotDrive;
	private Solenoid frontLeftSolenoid;
	private Solenoid rearLeftSolenoid;
	private Solenoid frontRightSolenoid;
	private Solenoid rearRightSolenoid;

	// variable declarations
	double arcadeYaxisValue;
	double arcadeRotateAxisValue;
	double mecanumXaxisValue;
	double mecanumYaxisValue;
	double mecanumRotateAxisValue;

	public Drive(Robot robot) {
		// assigning local variables to variables in the robot class
		this.mobilityStick = robot.getMobilityStick();
		this.driveSwitchButton = robot.getDriveSwitchButton();
		this.robotDrive = robot.getRobotDrive();
		this.frontLeftSolenoid = robot.getFrontLeftSolenoid();
		this.rearLeftSolenoid = robot.getRearLeftSolenoid();
		this.frontRightSolenoid = robot.getFrontRightSolenoid();
		this.rearRightSolenoid = robot.getRearRightSolenoid();

		// assigning local variables to hold the values of stick axes
		arcadeYaxisValue = mobilityStick.getRawAxis(Robot.ARCADE_DRIVE_Y_AXIS);
		arcadeRotateAxisValue = mobilityStick.getRawAxis(Robot.ARCADE_DRIVE_ROTATE_AXIS);
		mecanumXaxisValue = mobilityStick.getRawAxis(Robot.MECANUM_DRIVE_X_AXIS);
		mecanumYaxisValue = mobilityStick.getRawAxis(Robot.MECANUM_DRIVE_Y_AXIS);
		mecanumRotateAxisValue = mobilityStick.getRawAxis(Robot.MECANUM_DRIVE_ROTATE_AXIS);
	}

	// method to set the drive solenoids - false is default position - true is
	// second position
	public void setSolenoids(boolean frontLeftSolenoidSet, boolean rearLeftSolenoidSet, boolean frontRightSolenoidSet,
			boolean rearRightSolenoidSet) {
		frontLeftSolenoid.set(frontLeftSolenoidSet);
		rearLeftSolenoid.set(rearLeftSolenoidSet);
		frontRightSolenoid.set(frontRightSolenoidSet);
		rearRightSolenoid.set(rearRightSolenoidSet);
	}

	public void setDriveSpeed() { // modifies the values returned from the drive axes to modify the drive speed
		arcadeYaxisValue *= SmartDashboard.getNumber("Drive Speed", 1);
		arcadeRotateAxisValue *= SmartDashboard.getNumber("Drive Speed", 1);
		mecanumXaxisValue *= SmartDashboard.getNumber("Drive Speed", 1);
		mecanumYaxisValue *= SmartDashboard.getNumber("Drive Speed", 1);
		mecanumRotateAxisValue *= SmartDashboard.getNumber("Drive Speed", 1);
	}

	public void arcadeDrive() { // to be used when on colsons
		setDriveSpeed();
		robotDrive.arcadeDrive(DeadZone.deadZone(arcadeYaxisValue), DeadZone.deadZone(arcadeRotateAxisValue));
		setSolenoids(true, true, true, true); // puts robot on colsons
	}

	public void mecanumDrive() { // to be used when on mecanums
		setDriveSpeed();
		robotDrive.mecanumDrive_Cartesian(DeadZone.deadZone(mecanumXaxisValue), DeadZone.deadZone(mecanumYaxisValue),
				DeadZone.deadZone(mecanumRotateAxisValue), 0);
		setSolenoids(false, false, false, false); // puts robot on mecanums
	}

	public void driveSwitch() { // uses a button to activate the solenoids and switch the drive methods
		if (driveSwitchButton.toggle()) {
			mecanumDrive();
		} else {
			arcadeDrive();
		}
	}
}