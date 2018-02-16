package org.usfirst.frc.team3019.robot.commands;

import org.usfirst.frc.team3019.robot.Robot;
import org.usfirst.frc.team3019.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class Intake extends Command {
	
	boolean forward;

	public Intake(boolean forward) {
		super();
		requires(Robot.intakeSystem);
		this.forward = forward;
	}
	
	public Intake() {
		super();
		requires(Robot.intakeSystem);
		forward = false;
	}
	
	@Override
	protected void execute() {
		Robot.intakeSystem.setMotors((forward ? 1.0: -1.0) * RobotMap.ELEVATOR_MOVE_SCALE_FACTOR);
	}
	
	@Override
	protected void end() {
		Robot.intakeSystem.stopMotors();
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

}
