/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3019.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;
	public static int xbox1port = 1;  
	
	public static boolean orientForward = false;
	
	public static double DRIVE_SCALE_FACTOR = 1.0;
	public static double ELEVATOR_MOVE_SCALE_FACTOR = 1.0;
	public static double CLIMB_SCALE_FACTOR = 1.0;
	public static double ARM_ROTATE_SCALE_FACTOR = 1.0;
	
	public static int leftClimbMotor = 4;
	public static int rightClimbMotor = 5;
	public static int elevatorMotorOne = 6;
	public static int elevatorMotorTwo = 7;
	public static int leftIntakeMotor = 8;
	public static int rightIntakeMotor = 9;
	public static int rotateClawMotor = 10;
	
	public static int topElevatorSwitch = 0;
	public static int bottomElevatorSwitch = 1;
}
