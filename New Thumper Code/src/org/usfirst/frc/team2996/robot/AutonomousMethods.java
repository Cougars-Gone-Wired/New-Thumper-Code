package org.usfirst.frc.team2996.robot;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;

public class AutonomousMethods {
	// objects declarations
	private CANTalon frontLeftMotor;
	private CANTalon rearLeftMotor;
	private CANTalon frontRightMotor;
	private CANTalon rearRightMotor;

	// not private because they are used in AutonomousPrograms
	Solenoid gearPanSolenoid;
	RobotDrive robotDrive;
	Drive drive;
	AHRS gyro;

	// variable declarations
	double distancePerEncoderTick = (Robot.WHEEL_DIAMETER * Math.PI) / Robot.ENCODER_CODES_PER_REV;

	public AutonomousMethods(Robot robot) {
		// assignments of local variables to robot class variables
		this.frontLeftMotor = robot.getFrontLeftMotor();
		this.rearLeftMotor = robot.getRearLeftMotor();
		this.frontRightMotor = robot.getFrontRightMotor();
		this.rearRightMotor = robot.getRearRightMotor();
		this.gearPanSolenoid = robot.getGearPanSolenoid();
		this.robotDrive = robot.getRobotDrive();
		this.drive = robot.getDrive();
		this.gyro = robot.getGyro();

		// settings number of encoder ticks per revolution for each drive motor encoder
		frontLeftMotor.configEncoderCodesPerRev(Robot.ENCODER_CODES_PER_REV);
		rearLeftMotor.configEncoderCodesPerRev(Robot.ENCODER_CODES_PER_REV);
		frontRightMotor.configEncoderCodesPerRev(Robot.ENCODER_CODES_PER_REV);
		rearRightMotor.configEncoderCodesPerRev(Robot.ENCODER_CODES_PER_REV);
	}

	public void encoderReset() { // to reset encoder ticks
		frontLeftMotor.setEncPosition(0);
		rearLeftMotor.setEncPosition(0);
		frontRightMotor.setEncPosition(0);
		rearRightMotor.setEncPosition(0);
	}

	public int encoderAverage() { // used to find roughly where the robot really is by averaging the four drive
									// motor controllers
		// assumes all four encoders work
		int average = (Math.abs(frontLeftMotor.getEncPosition()) + Math.abs(rearLeftMotor.getEncPosition())
				+ Math.abs(frontRightMotor.getEncPosition()) + Math.abs(rearRightMotor.getEncPosition()) / 4);
		return average;
	}

}
