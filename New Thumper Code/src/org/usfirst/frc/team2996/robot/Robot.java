
package org.usfirst.frc.team2996.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
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
	double arcadeDriveYAxis;
	double arcadeDriveRotateAxis;
	double mecanumDriveYAxis;
	double mecanumDriveXAxis;
	double mecanumDriveRotateAxis;
	Toggle driveSwitchButton;
	
	//motor controller object declarations
	WPI_TalonSRX intakeMotor;
	WPI_TalonSRX shooterMotor;
	WPI_TalonSRX augerMotor;
	WPI_TalonSRX climberMotor;
	
	WPI_TalonSRX frontLeftMotor;
	WPI_TalonSRX rearLeftMotor;
	SpeedControllerGroup leftMotors;
	
	WPI_TalonSRX frontRightMotor;
	WPI_TalonSRX rearRightMotor;
	SpeedControllerGroup rightMotors;
	
	//motor controller object declarations
	Solenoid gearPanSolenoid;
	Solenoid frontLeftSolenoid;
	Solenoid rearLeftSolenoid;
	Solenoid frontRightSolenoid;
	Solenoid rearRightSolenoid;
	
	//other object declarations
	DifferentialDrive differentialDrive;
	MecanumDrive mecanumDrive;
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
		
		
		//manipulator stick instantiations
		manipulatorStick = new Joystick(MANIPULATOR_STICK_PORT);
		gearPanButton = new Toggle(manipulatorStick, GEAR_PAN_BUTTON);
		
		//mobility stick instantiations
		mobilityStick = new Joystick(MOBILITY_STICK_PORT);
		arcadeDriveYAxis = DeadZone.deadZone(mobilityStick.getRawAxis(ARCADE_DRIVE_Y_AXIS));
		arcadeDriveRotateAxis = DeadZone.deadZone(mobilityStick.getRawAxis(ARCADE_DRIVE_ROTATE_AXIS));
		mecanumDriveYAxis = DeadZone.deadZone(mobilityStick.getRawAxis(MECANUM_DRIVE_Y_AXIS));
		mecanumDriveXAxis = DeadZone.deadZone(mobilityStick.getRawAxis(MECANUM_DRIVE_X_AXIS));
		mecanumDriveRotateAxis = DeadZone.deadZone(mobilityStick.getRawAxis(MECANUM_DRIVE_ROTATE_AXIS));
		driveSwitchButton = new Toggle(mobilityStick, DRIVE_SWITCH_BUTTON);
		
		//motor controller instantiations
		intakeMotor = new WPI_TalonSRX(INTAKE_MOTOR_PORT);
		shooterMotor = new WPI_TalonSRX(SHOOTER_MOTOR_PORT);
		augerMotor = new WPI_TalonSRX(AUGER_MOTOR_PORT);
		climberMotor = new WPI_TalonSRX(CLIMBER_MOTOR_PORT);
		
		frontLeftMotor = new WPI_TalonSRX(FRONT_LEFT_MOTOR_PORT);
		rearLeftMotor = new WPI_TalonSRX(REAR_LEFT_MOTOR_PORT);
		leftMotors = new SpeedControllerGroup(frontLeftMotor, rearLeftMotor);
		
		frontRightMotor = new WPI_TalonSRX(FRONT_RIGHT_MOTOR_PORT);
		rearRightMotor = new WPI_TalonSRX(REAR_LEFT_MOTOR_PORT);
		rightMotors = new SpeedControllerGroup(frontRightMotor, rearLeftMotor);
		
		//solenoid instantiations
		gearPanSolenoid = new Solenoid(GEAR_PAN_SOLENOID_PORT);
		frontLeftSolenoid = new Solenoid(FRONT_LEFT_SOLENOID_PORT);
		rearLeftSolenoid = new Solenoid(REAR_LEFT_SOLENOID_PORT);
		frontRightSolenoid = new Solenoid(FRONT_RIGHT_SOLENOID_PORT);
		rearRightSolenoid = new Solenoid(REAR_RIGHT_SOLENOID_PORT);
		
		//other instantiations
		differentialDrive = new DifferentialDrive(leftMotors, rightMotors);
		mecanumDrive = new MecanumDrive(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
		gyro = new AHRS(SPI.Port.kMXP);
		
		//project class instantiations
		shooter = new Shooter(this);
		intake = new Intake(this);
		climber = new Climber(this);
		drive = new Drive(this);
		autonomousMethods = new AutonomousMethods(this);
		autonomousPrograms = new AutonomousPrograms(this);
		
		setInverts();
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
//		autonomousMethods.encoderReset();
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
		arcadeDriveYAxis = arcadeDriveYAxis * -1;
		arcadeDriveRotateAxis = arcadeDriveRotateAxis * -1;
		mecanumDriveYAxis = mecanumDriveYAxis * -1;
		mecanumDriveXAxis = mecanumDriveXAxis * 1;
		mecanumDriveRotateAxis = mecanumDriveRotateAxis * -1;
		
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

	public double getArcadeDriveYAxis() {
		return arcadeDriveYAxis;
	}

	public double getArcadeDriveRotateAxis() {
		return arcadeDriveRotateAxis;
	}

	public double getMecanumDriveYAxis() {
		return mecanumDriveYAxis;
	}

	public double getMecanumDriveXAxis() {
		return mecanumDriveXAxis;
	}

	public double getMecanumDriveRotateAxis() {
		return mecanumDriveRotateAxis;
	}

	public Toggle getDriveSwitchButton() {
		return driveSwitchButton;
	}

	public WPI_TalonSRX getIntakeMotor() {
		return intakeMotor;
	}

	public WPI_TalonSRX getShooterMotor() {
		return shooterMotor;
	}

	public WPI_TalonSRX getAugerMotor() {
		return augerMotor;
	}

	public WPI_TalonSRX getClimberMotor() {
		return climberMotor;
	}

	public WPI_TalonSRX getFrontLeftMotor() {
		return frontLeftMotor;
	}

	public WPI_TalonSRX getRearLeftMotor() {
		return rearLeftMotor;
	}

	public WPI_TalonSRX getFrontRightMotor() {
		return frontRightMotor;
	}

	public WPI_TalonSRX getRearRightMotor() {
		return rearRightMotor;
	}

	public DifferentialDrive getDifferentialDrive() {
		return differentialDrive;
	}

	public MecanumDrive getMecanumDrive() {
		return mecanumDrive;
	}

	public Solenoid getGearPanSolenoid() {
		return gearPanSolenoid;
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
