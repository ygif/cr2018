package org.usfirst.frc.team3019.robot.commands;

import org.usfirst.frc.team3019.robot.Robot;
import org.usfirst.frc.team3019.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class RotateArm extends Command {

	boolean forward;

	public RotateArm(boolean forward) {
		super();
		requires(Robot.armRotator);
		this.forward = forward;
	}
	
	public RotateArm() {
		super();
		requires(Robot.armRotator);
		forward = false;
	}
	
	@Override
	protected void execute() {
		Robot.armRotator.rotateArm((forward ? 1.0: -1.0) * RobotMap.ARM_ROTATE_SCALE_FACTOR);
	}
	
	@Override
	protected void end() {
		Robot.armRotator.stopArmRotation();
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void interrupted() {
		end();
	}
}
