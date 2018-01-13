package org.usfirst.frc.team2996.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousPrograms extends AutonomousMethods { // has access to variables from AutonomousMethods

	public AutonomousPrograms(Robot robot) {
		super(robot); // same constructor as AutonomousMethods
	}

	public void doNothing() { // default auto in case we don't have an auto that we can use
		differentialDrive.curvatureDrive(0, 0, false);
	}

	public void driveForward() { // just supposed to drive forward for a certain distance
//		if (encoderAverage() < (SmartDashboard.getNumber("Drive Forward Distance", 0) / distancePerEncoderTick)) {
//			robotDrive.drive(SmartDashboard.getNumber("Autonomous Drive Speed", 0.6), 0);
//		} else {
//			robotDrive.drive(0, 0);
//		}
	}

	public void centerGear() { // supposed to drive forward, go up on mecanums, and place a gear
//		if (encoderAverage() < (SmartDashboard.getNumber("Center Gear Forward Distance", 0) / distancePerEncoderTick)) {
//			robotDrive.drive(SmartDashboard.getNumber("Autonomous Drive Speed", 0.6), 0);
//		} else if (encoderAverage() < (SmartDashboard.getNumber("Center Gear Final Distance", 0)
//				/ distancePerEncoderTick)) {
//			drive.setSolenoids(false, false, false, false);
//			robotDrive.drive(SmartDashboard.getNumber("Autonomous Mecanum Speed", 0.5), 0);
//		} else {
//			gearPanSolenoid.set(true);
//			robotDrive.drive(0, 0);
//		}
	}

	public void leftGear() { // supposed to drive forward, turn right, drive forward again, go up on
								// mecanums, and place a gear
//		if (encoderAverage() < (SmartDashboard.getNumber("Left Gear First Distance", 0) / distancePerEncoderTick)) {
//			robotDrive.drive(SmartDashboard.getNumber("Autonomous Drive Speed", 0.6), 0);
//		} else if (gyro.getYaw() < SmartDashboard.getNumber("Left Gear Turn Angle", 0)) {
//			robotDrive.arcadeDrive(0, SmartDashboard.getNumber("Autonomous Turn Speed", 0.7));
//		} else if (encoderAverage() < (SmartDashboard.getNumber("Left Gear Second Distance", 0)
//				/ distancePerEncoderTick)) {
//			robotDrive.drive(SmartDashboard.getNumber("Autonomous Drive Speed", 0.6), 0);
//		} else if (encoderAverage() < (SmartDashboard.getNumber("Left Gear Final Distance", 0)
//				/ distancePerEncoderTick)) {
//			drive.setSolenoids(false, false, false, false);
//			robotDrive.drive(SmartDashboard.getNumber("Autonomous Mecanum Speed", 0.5), 0);
//		} else {
//			gearPanSolenoid.set(true);
//			robotDrive.drive(0, 0);
//		}
	}

	public void rightGear() { // supposed to drive forward, turn left, drive forward again, go up on mecanums,
								// and place a gear
//		if (encoderAverage() < (SmartDashboard.getNumber("Right Gear First Distance", 0) / distancePerEncoderTick)) {
//			robotDrive.drive(SmartDashboard.getNumber("Autonomous Drive Speed", 0.6), 0);
//		} else if (gyro.getYaw() < SmartDashboard.getNumber("Right Gear Turn Angle", 0)) {
//			robotDrive.arcadeDrive(0, SmartDashboard.getNumber("Autonomous Turn Speed", 0.7));
//		} else if (encoderAverage() < (SmartDashboard.getNumber("Right Gear Second Distance", 0)
//				/ distancePerEncoderTick)) {
//			robotDrive.drive(SmartDashboard.getNumber("Autonomous Drive Speed", 0.6), 0);
//		} else if (encoderAverage() < (SmartDashboard.getNumber("Right Gear Final Distance", 0)
//				/ distancePerEncoderTick)) {
//			drive.setSolenoids(false, false, false, false);
//			robotDrive.drive(SmartDashboard.getNumber("Autonomous Mecanum Speed", 0.5), 0);
//		} else {
//			gearPanSolenoid.set(true);
//			robotDrive.drive(0, 0);
//		}
	}
}
