package org.usfirst.frc.team3019.robot.commands;

import org.usfirst.frc.team3019.robot.Robot;
import org.usfirst.frc.team3019.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class MoveElevator extends Command {
	
	public boolean down;
	
	public MoveElevator(boolean down) {
		super();
		requires(Robot.elevator);
		this.down = down;
	}
	
	public MoveElevator() {
		super();
		requires(Robot.elevator);
		down = false;
	}

	@Override
	protected void execute() {
		Robot.elevator.setMotors((down ? -1.0 : 1.0) * RobotMap.ELEVATOR_MOVE_SCALE_FACTOR);
	}

	@Override
	protected void end() {
		Robot.elevator.stopMotors();
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
