/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3019.robot;

import org.usfirst.frc.team3019.robot.commands.Climb;
import org.usfirst.frc.team3019.robot.commands.Intake;
import org.usfirst.frc.team3019.robot.commands.RotateClaw;
import org.usfirst.frc.team3019.util.PlaybackXboxController;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	public PlaybackXboxController xbox;
	
	Button intake; 
	Button outtake;
	Button clawUp;
	Button clawDown;
	Button toggleDriveOrientation; 
	Button climb;
	Button climbDown;
	
	public OI() {
		xbox = new PlaybackXboxController(RobotMap.xbox1port);
		intake = new JoystickButton(xbox, 5);
		outtake = new JoystickButton(xbox, 6);
		clawUp = new JoystickButton(xbox, 1);
		clawDown = new JoystickButton(xbox, 2);
		toggleDriveOrientation = new JoystickButton(xbox, 8);
		climb = new JoystickButton(xbox, 4);
		climbDown = new JoystickButton(xbox, 3);
		
		intake.whileHeld(new Intake(false));
		outtake.whileHeld(new Intake(true));
		clawUp.whileHeld(new RotateClaw(false));
		clawDown.whileHeld(new RotateClaw(true));
		climb.whileHeld(new Climb (false));
		climbDown.whileHeld(new Climb(true));
		toggleDriveOrientation.whenPressed(new InstantCommand() {
			@Override
			protected void initialize() {
				RobotMap.orientForward = !RobotMap.orientForward;
			}
		});
	}
}
