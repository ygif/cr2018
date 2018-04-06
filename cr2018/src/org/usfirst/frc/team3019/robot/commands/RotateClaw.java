package org.usfirst.frc.team3019.robot.commands;

import org.usfirst.frc.team3019.robot.Robot;
import org.usfirst.frc.team3019.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class RotateClaw extends Command {
	
	public boolean up;
	
	public RotateClaw(boolean down) {
		super();
		requires(Robot.clawRotator);
		this.up = down;
	}
	
	public RotateClaw() {
		super();
		requires(Robot.clawRotator);
		up = false;
	}

	@Override
	protected void execute() {
		Robot.clawRotator.setMotors((up ? -1.0 : 1.0) * RobotMap.CLAW_ROTATE_SCALE_FACTOR);
	}

	@Override
	protected void end() {
		Robot.clawRotator.stopMotors();
	}

	@Override
	protected boolean isFinished() {
		return false/*(!Robot.elevator.top.get() && Robot.elevator.speed > 0.0) ||
				(!Robot.elevator.bottom.get() && Robot.elevator.speed < 0.0)*/;
	}
	
	@Override
	protected void interrupted() {
		end();
	}
}
