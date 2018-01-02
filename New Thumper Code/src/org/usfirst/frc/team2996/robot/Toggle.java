package org.usfirst.frc.team2996.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Toggle {
	
	// variable declarations
	private boolean buttonState;
	private boolean output = false;

	// object declarations
	private Joystick stick;
	private int buttonNumber;

	public Toggle(Joystick stick, int buttonNumber) {
		// assigns the local objects to the objects from the constructor parameters
		this.stick = stick;
		this.buttonNumber = buttonNumber;
	}

	public boolean toggle() { // outputs one value if button pressed once, outputs other value if button
								// pressed again
		if (stick.getRawButton(buttonNumber)) {
			if (!buttonState) {
				output = !output;
			}
			buttonState = true;
		} else {
			buttonState = false;
		}
		return output;
	}
}
