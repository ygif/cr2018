/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3019.robot;

import org.usfirst.frc.team3019.robot.commands.Climb;
import org.usfirst.frc.team3019.robot.commands.Intake;
import org.usfirst.frc.team3019.robot.commands.MoveElevator;
import org.usfirst.frc.team3019.util.PlaybackXboxController;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	
	public PlaybackXboxController xbox;
	
	Button intake; 
	Button outtake;
	Button elevatorUp;
	Button elevatorDown;
	Button toggleDriveOrientation; 
	Button climb;
	
	public OI() {
		xbox = new PlaybackXboxController(RobotMap.xbox1port);
		intake = new JoystickButton(xbox, 1);
		outtake = new JoystickButton(xbox, 3);
		elevatorUp = new JoystickButton(xbox, 2);
		elevatorDown = new JoystickButton(xbox, 4);
		toggleDriveOrientation = new JoystickButton(xbox, 5);
		climb = new JoystickButton(xbox, 6);
		
		intake.whileHeld(new Intake(false));
		outtake.whileHeld(new Intake(true));
		elevatorUp.whileHeld(new MoveElevator(false));
		elevatorDown.whileHeld(new MoveElevator(true));
		climb.whileHeld(new Climb(false));
		toggleDriveOrientation.whenPressed(new Command() {
			@Override
			protected void initialize() {
				RobotMap.orientForward = !RobotMap.orientForward;
			}

			@Override
			protected boolean isFinished() {
				return true;
			}
		});
	}
}
