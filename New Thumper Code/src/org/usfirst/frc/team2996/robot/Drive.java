package org.usfirst.frc.team2996.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive {

	// object declarations
	private Joystick mobilityStick;
	private Toggle driveSwitchButton;
	private DifferentialDrive differentialDrive;
	private MecanumDrive mecanumDrive;
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
		this.differentialDrive = robot.getDifferentialDrive();
		this.mecanumDrive = robot.getMecanumDrive();
		this.frontLeftSolenoid = robot.getFrontLeftSolenoid();
		this.rearLeftSolenoid = robot.getRearLeftSolenoid();
		this.frontRightSolenoid = robot.getFrontRightSolenoid();
		this.rearRightSolenoid = robot.getRearRightSolenoid();

		// assigning local variables to hold the values of stick axes
		arcadeYaxisValue = robot.getArcadeDriveYAxis();
		arcadeRotateAxisValue = robot.getArcadeDriveRotateAxis();
		mecanumXaxisValue = robot.getMecanumDriveXAxis();
		mecanumYaxisValue = robot.getMecanumDriveYAxis();
		mecanumRotateAxisValue = robot.getMecanumDriveRotateAxis();
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
		differentialDrive.arcadeDrive(DeadZone.deadZone(arcadeYaxisValue), DeadZone.deadZone(arcadeRotateAxisValue));
		setSolenoids(true, true, true, true); // puts robot on colsons
	}

	public void mecanumDrive() { // to be used when on mecanums
		setDriveSpeed();
		mecanumDrive.driveCartesian(DeadZone.deadZone(mecanumXaxisValue), DeadZone.deadZone(mecanumYaxisValue),
				DeadZone.deadZone(mecanumRotateAxisValue));
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
