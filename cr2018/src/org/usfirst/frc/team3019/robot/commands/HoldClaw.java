package org.usfirst.frc.team3019.robot.commands;

import org.usfirst.frc.team3019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class HoldClaw extends Command {

	public HoldClaw() {
		super.requires(Robot.clawRotator);
	}
	
	@Override
	protected void execute() {
		Robot.clawRotator.setMotors(-0.25);
	}

	@Override
	protected void end() {
		Robot.clawRotator.stopMotors();
	}

	@Override
	protected boolean isFinished() {
		return !Robot.clawRotator.stopper.get();
	}
	
	@Override
	protected void interrupted() {
		end();
	}
}
