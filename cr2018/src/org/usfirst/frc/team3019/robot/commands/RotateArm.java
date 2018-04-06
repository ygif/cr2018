package org.usfirst.frc.team3019.robot.commands;

import org.usfirst.frc.team3019.robot.Robot;
import org.usfirst.frc.team3019.robot.RobotMap;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

public class RotateArm extends Command {
 
	public RotateArm() {
		super();
		requires(Robot.armRotator);
	}
	
	@Override
	protected void execute() {
		double stickValue = Robot.oi.xbox.getY(Hand.kRight);
		Robot.armRotator.rotateArm((stickValue) * RobotMap.ARM_ROTATE_SCALE_FACTOR);
	}
	
	@Override
	protected void end() {
		Robot.armRotator.stopArmRotation();
	}
	
	@Override
	protected boolean isFinished() {
		return !Robot.armRotator.stopper.get();
	}
	
	@Override
	protected void interrupted() {
		end();
	}
}
