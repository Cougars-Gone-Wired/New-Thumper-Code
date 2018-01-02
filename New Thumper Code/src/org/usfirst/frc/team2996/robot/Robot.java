
package org.usfirst.frc.team2996.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	//Autonomous selector setup
	final String doNothingAuto = "Do Nothing";
	final String driveForwardAuto = "Drive Forward";
	final String centerGearAuto = "Center Gear";
	final String leftGearAuto = "Left Gear";
	final String rightGearAuto = "Right Gear";
	String autoSelected;
	SendableChooser<String> autonomousChooser = new SendableChooser<>();
	
	
	//Manipulator Stick constants
	static int MANIPULATOR_STICK_PORT;
	static int INTAKE_TRIGGER;
	static int OUTTAKE_TRIGGER;
	static int GEAR_PAN_BUTTON;
	static int SHOOTER_BUTTON;
	static int AUGER_IN_BUMPER;
	static int AUGER_OUT_BUMPER;
	
	//Mobility Stick constants
	static int MOBILITY_STICK_PORT;
	static int CLIMB_UP_TRIGGER;
	static int CLIMB_DOWN_TRIGGER;
	static int DRIVE_SWITCH_BUTTON;
	static int ARCADE_DRIVE_Y_AXIS;
	static int ARCADE_DRIVE_ROTATE_AXIS;
	static int MECANUM_DRIVE_Y_AXIS;
	static int MECANUM_DRIVE_X_AXIS;
	static int MECANUM_DRIVE_ROTATE_AXIS;
	
	
	//motor controller port constants
	static int INTAKE_MOTOR_PORT;
	static int SHOOTER_MOTOR_PORT;
	static int AUGER_MOTOR_PORT;
	static int CLIMBER_MOTOR_PORT;
	static int FRONT_LEFT_MOTOR_PORT;
	static int REAR_LEFT_MOTOR_PORT;
	static int FRONT_RIGHT_MOTOR_PORT;
	static int REAR_RIGHT_MOTOR_PORT;
	
	//solenoid port constants
	static int GEAR_PAN_SOLENOID_PORT;
	static int FRONT_LEFT_SOLENOID_PORT;
	static int REAR_LEFT_SOLENOID_PORT;
	static int FRONT_RIGHT_SOLENOID_PORT;
	static int REAR_RIGHT_SOLENOID_PORT;
	
	//autonomous constants
	static int WHEEL_DIAMETER;
	static int ENCODER_CODES_PER_REV;
	
	
	//manipulator stick object declarations
	Joystick manipulatorStick;	
	Toggle gearPanButton;
	
	//mobility stick object declarations
	Joystick mobilityStick;
	Toggle driveSwitchButton;
	
	//motor controller object declarations
	CANTalon intakeMotor;
	CANTalon shooterMotor;
	CANTalon augerMotor;
	CANTalon climberMotor;
	CANTalon frontLeftMotor;
	CANTalon rearLeftMotor;
	CANTalon frontRightMotor;
	CANTalon rearRightMotor;
	
	//motor controller object declarations
	Solenoid gearPanSolenoid;
	Solenoid frontLeftSolenoid;
	Solenoid rearLeftSolenoid;
	Solenoid frontRightSolenoid;
	Solenoid rearRightSolenoid;
	
	//other object declarations
	RobotDrive robotDrive;
	AHRS gyro;
	
	//project class declarations
	Shooter shooter;
	Intake intake;
	Climber climber;
	Drive drive;
	AutonomousMethods autonomousMethods;
	AutonomousPrograms autonomousPrograms;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//Autonomous selector setup onto the SmartDashboard
		autonomousChooser.addDefault("Do Nothing", doNothingAuto);
		autonomousChooser.addObject("Drive Forward", driveForwardAuto);
		autonomousChooser.addObject("Center Gear", centerGearAuto);
		autonomousChooser.addObject("Left Gear", leftGearAuto);
		autonomousChooser.addObject("Right Gear", rightGearAuto);
		SmartDashboard.putData("Auto choices", autonomousChooser);
		
		
		//Robot class method calls
		displaySettings();
		setConstants();
		setInverts();
		
		
		//manipulator stick instantiations
		manipulatorStick = new Joystick(MANIPULATOR_STICK_PORT);
		gearPanButton = new Toggle(manipulatorStick, GEAR_PAN_BUTTON);
		
		//mobility stick instantiations
		mobilityStick = new Joystick(MOBILITY_STICK_PORT);
		driveSwitchButton = new Toggle(mobilityStick, DRIVE_SWITCH_BUTTON);
		
		//motor controller instantiations
		intakeMotor = new CANTalon(INTAKE_MOTOR_PORT);
		shooterMotor = new CANTalon(SHOOTER_MOTOR_PORT);
		augerMotor = new CANTalon(AUGER_MOTOR_PORT);
		climberMotor = new CANTalon(CLIMBER_MOTOR_PORT);
		frontLeftMotor = new CANTalon(FRONT_LEFT_MOTOR_PORT);
		rearLeftMotor = new CANTalon(REAR_LEFT_MOTOR_PORT);
		frontRightMotor = new CANTalon(FRONT_RIGHT_MOTOR_PORT);
		rearRightMotor = new CANTalon(REAR_LEFT_MOTOR_PORT);
		
		//solenoid instantiations
		gearPanSolenoid = new Solenoid(GEAR_PAN_SOLENOID_PORT);
		frontLeftSolenoid = new Solenoid(FRONT_LEFT_SOLENOID_PORT);
		rearLeftSolenoid = new Solenoid(REAR_LEFT_SOLENOID_PORT);
		frontRightSolenoid = new Solenoid(FRONT_RIGHT_SOLENOID_PORT);
		rearRightSolenoid = new Solenoid(REAR_RIGHT_SOLENOID_PORT);
		
		//other instantiations
		robotDrive = new RobotDrive(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
		gyro = new AHRS(SPI.Port.kMXP);
		
		//project class instantiations
		shooter = new Shooter(this);
		intake = new Intake(this);
		climber = new Climber(this);
		drive = new Drive(this);
		autonomousMethods = new AutonomousMethods(this);
		autonomousPrograms = new AutonomousPrograms(this);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {

	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autoSelected = autonomousChooser.getSelected();
		autoSelected = SmartDashboard.getString("Auto Selector", doNothingAuto);
		
		//reseting things for auto
		gyro.zeroYaw();
		autonomousMethods.encoderReset();
		drive.arcadeDrive(); //so that we start on colsons
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		
		//switch to run the auto program selected on the SmartDashboard
		switch (autoSelected) {
		case doNothingAuto:
			autonomousPrograms.doNothing();
			break;
		case driveForwardAuto:
			autonomousPrograms.driveForward();
			break;
		case centerGearAuto:
			autonomousPrograms.centerGear();
			break;
		case leftGearAuto:
			autonomousPrograms.leftGear();
			break;
		case rightGearAuto:
			autonomousPrograms.rightGear();
		default:
			autonomousPrograms.doNothing();
			break;
		}
	}

	@Override
	public void teleopInit() {
		drive.arcadeDrive(); //so that we start on colsons
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		//calling mobility classes to be run
		drive.driveSwitch();
		climber.climber();
		
		//calling manipulator classes to be run
		shooter.shooter();
		shooter.auger();
		intake.intakeOuttake();
		intake.gearPanUpDown();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {

	}
	
	public void displaySettings() {
		//values for teleop
		SmartDashboard.putNumber("Shooter Speed", 0.5);
		SmartDashboard.putNumber("Drive Speed", 1.0);
		
		//values for autonomous
		//speeds
		SmartDashboard.putNumber("Autonomous Drive Speed",0.6);
		SmartDashboard.putNumber("Autonomous Mecanum Speed", 0.5);
		SmartDashboard.putNumber("Autonomous Turn Speed", 0.7);
		
		//for drive forward auto
		SmartDashboard.putNumber("Drive Forward Distance", 0);
		
		//for center gear auto
		SmartDashboard.putNumber("Center Gear Forward Distance", 0);
		SmartDashboard.putNumber("Center Gear Final Distance", 0);
		
		//for left gear auto
		SmartDashboard.putNumber("Left Gear First Distance", 0);
		SmartDashboard.putNumber("Left Gear Turn Angle", 0);
		SmartDashboard.putNumber("Left Gear Second Distance", 0);
		SmartDashboard.putNumber("Left Gear Final Distance", 0);
		
		//for right gear auto
		SmartDashboard.putNumber("Right Gear First Distance", 0);
		SmartDashboard.putNumber("Right Gear Turn Angle", 0);
		SmartDashboard.putNumber("Right Gear Second Distance", 0);
		SmartDashboard.putNumber("Right Gear Final Distance", 0);
		
		//speeds are from 0 to 1
		//distances in inches
		//angles in degrees
	}
	
	public static void setConstants(){
		MANIPULATOR_STICK_PORT = 0; //manipulator stick USB port assignment
		//manipulator axis assignments 
		INTAKE_TRIGGER = 3;
		OUTTAKE_TRIGGER = 2;
		//manipulator button assignments
		GEAR_PAN_BUTTON = 2;
		SHOOTER_BUTTON = 1;
		AUGER_IN_BUMPER = 6;
		AUGER_OUT_BUMPER = 5;
		
		MOBILITY_STICK_PORT = 1; //mobility stick USB port assignments
		//mobility axis assignments
		CLIMB_UP_TRIGGER = 3;
		CLIMB_DOWN_TRIGGER = 2;
		ARCADE_DRIVE_Y_AXIS = 1;
		ARCADE_DRIVE_ROTATE_AXIS = 4;
		MECANUM_DRIVE_Y_AXIS = 1;
		MECANUM_DRIVE_X_AXIS = 0;
		MECANUM_DRIVE_ROTATE_AXIS = 4;
		//mobility button assignments
		DRIVE_SWITCH_BUTTON = 1;
		
		//motor controller port assignments
		INTAKE_MOTOR_PORT = 4;
		SHOOTER_MOTOR_PORT = 7;
		AUGER_MOTOR_PORT = 2;
		CLIMBER_MOTOR_PORT = 1;
		FRONT_LEFT_MOTOR_PORT = 5;
		REAR_LEFT_MOTOR_PORT = 0;
		FRONT_RIGHT_MOTOR_PORT = 8;
		REAR_RIGHT_MOTOR_PORT = 3;
		
		//solenoid port assignments
		GEAR_PAN_SOLENOID_PORT = 2;
		FRONT_LEFT_SOLENOID_PORT = 4;
		REAR_LEFT_SOLENOID_PORT = 5;
		FRONT_RIGHT_SOLENOID_PORT = 0;
		REAR_RIGHT_SOLENOID_PORT = 1;
		
		//auto constant assignments
		ENCODER_CODES_PER_REV = 20;
		WHEEL_DIAMETER = 5; //in inches
	}

	public void setInverts() {
		//stick axis inverts (- means inverted)
		ARCADE_DRIVE_Y_AXIS = -ARCADE_DRIVE_Y_AXIS;
		ARCADE_DRIVE_ROTATE_AXIS = -ARCADE_DRIVE_ROTATE_AXIS;
		MECANUM_DRIVE_Y_AXIS = -MECANUM_DRIVE_Y_AXIS;
		MECANUM_DRIVE_X_AXIS = MECANUM_DRIVE_X_AXIS; //here for completion purposes
		MECANUM_DRIVE_ROTATE_AXIS = -MECANUM_DRIVE_ROTATE_AXIS;
		
		//motor controller voltage inverts
		intakeMotor.setInverted(true);
		shooterMotor.setInverted(true);
		augerMotor.setInverted(false);
		climberMotor.setInverted(false);
		frontLeftMotor.setInverted(false);
		rearLeftMotor.setInverted(false);
		frontRightMotor.setInverted(false);
		rearRightMotor.setInverted(false);
	}
	
	//all the getters
	public Joystick getManipulatorStick() {
		return manipulatorStick;
	}

	public Toggle getGearPanButton() {
		return gearPanButton;
	}

	public Joystick getMobilityStick() {
		return mobilityStick;
	}

	public Toggle getDriveSwitchButton() {
		return driveSwitchButton;
	}

	public CANTalon getIntakeMotor() {
		return intakeMotor;
	}

	public Solenoid getGearPanSolenoid() {
		return gearPanSolenoid;
	}

	public CANTalon getShooterMotor() {
		return shooterMotor;
	}

	public CANTalon getAugerMotor() {
		return augerMotor;
	}

	public CANTalon getClimberMotor() {
		return climberMotor;
	}

	public RobotDrive getRobotDrive() {
		return robotDrive;
	}

	public CANTalon getFrontLeftMotor() {
		return frontLeftMotor;
	}

	public CANTalon getRearLeftMotor() {
		return rearLeftMotor;
	}

	public CANTalon getFrontRightMotor() {
		return frontRightMotor;
	}

	public CANTalon getRearRightMotor() {
		return rearRightMotor;
	}

	public Solenoid getFrontLeftSolenoid() {
		return frontLeftSolenoid;
	}

	public Solenoid getRearLeftSolenoid() {
		return rearLeftSolenoid;
	}

	public Solenoid getFrontRightSolenoid() {
		return frontRightSolenoid;
	}

	public Solenoid getRearRightSolenoid() {
		return rearRightSolenoid;
	}

	public Drive getDrive() {
		return drive;
	}

	public AHRS getGyro() {
		return gyro;
	}
	
}
