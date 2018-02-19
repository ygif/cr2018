package org.usfirst.frc.team3019.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3019.robot.Robot;
import org.usfirst.frc.team3019.robot.RobotMap;

/**
 *
 */
public class Drive extends Command {

	double move;
	double turn;

	public Drive(int timeout) {
		super(timeout);
		requires(Robot.driveTrain);
	}

	public Drive() {
		super();
		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		SmartDashboard.putNumber("move", move);
		SmartDashboard.putNumber("turn", turn);

		if (RobotMap.orientForward) {
			move = -Robot.oi.xbox.getY(Hand.kLeft);
			turn = Robot.oi.xbox.getX(Hand.kLeft);
		} else {
			move = Robot.oi.xbox.getY(Hand.kLeft);
			turn = Robot.oi.xbox.getX(Hand.kLeft);
		}
		
		/*double axis = Robot.oi.xbox.getTriggerAxis(Hand.kRight) * 0.9;
		double throttle = RobotMap.orientForward ? axis : -axis;
		double t = Robot.oi.xbox.getX(Hand.kLeft);*/
		
		//stick only
		Robot.driveTrain.arcadeDrive(move * RobotMap.DRIVE_SCALE_FACTOR, -turn * RobotMap.DRIVE_SCALE_FACTOR);
		
		//trigger and stick
		//Robot.driveTrain.arcadeDrive(throttle, t);
		
		//Robot.driveTrain.curvatureDrive(move * RobotMap.DRIVE_SCALE_FACTOR, turn * RobotMap.DRIVE_SCALE_FACTOR);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
