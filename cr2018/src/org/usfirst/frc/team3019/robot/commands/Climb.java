package org.usfirst.frc.team3019.robot.commands;

import org.usfirst.frc.team3019.robot.Robot;
import org.usfirst.frc.team3019.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class Climb extends Command {

	boolean backward;
	
	public Climb(boolean backward) {
		super();
		requires(Robot.climber);
		this.backward = backward;
	}
	
	public Climb() {
		super();
		requires(Robot.climber);
		backward = false;
	}
	
	@Override
	protected void initialize() {
		
	}
	
	@Override
	protected void execute() {
		Robot.climber.setMotors((backward ? -1.0 : 1.0) * RobotMap.CLIMB_SCALE_FACTOR);
	}
	
	@Override
	protected void end() {
		Robot.climber.stopMotors();
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
