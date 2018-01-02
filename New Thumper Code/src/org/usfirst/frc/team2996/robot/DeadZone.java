package org.usfirst.frc.team2996.robot;

public class DeadZone {

	public static double deadZone(double x) { // creates threshold on a specified stick axis so that no variable is
												// returned unless it is pushed past a certain distance
		if (Math.abs(x) > 0.15) {
			return x;
		} else {
			return 0;
		}
	}
}
