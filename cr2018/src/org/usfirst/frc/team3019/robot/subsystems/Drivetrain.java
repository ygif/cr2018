package org.usfirst.frc.team3019.robot.subsystems;

import org.usfirst.frc.team3019.robot.RobotMap;
import org.usfirst.frc.team3019.robot.commands.Drive;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
/**
 *
 */
public class Drivetrain extends Subsystem {
	
	VictorSP leftFrontMotor;
	VictorSP leftRearMotor;
	VictorSP rightFrontMotor;
	VictorSP rightRearMotor;
	DifferentialDrive dd;
	
	public Drivetrain() {
		super();
		
		leftFrontMotor = new VictorSP(2);
		leftRearMotor = new VictorSP(3);
		SpeedControllerGroup left = new SpeedControllerGroup(leftFrontMotor, leftRearMotor);
		left.setInverted(true);
		
		rightFrontMotor = new VictorSP(0);
		rightRearMotor = new VictorSP(1);
		SpeedControllerGroup right = new SpeedControllerGroup(rightFrontMotor, rightRearMotor);
		right.setInverted(true);
		
		dd = new DifferentialDrive(left, right);
		dd.setSafetyEnabled(false);
		dd.setDeadband(0.1);
	}
	
	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new Drive());
	}
	
	public void arcadeDrive(double moveValue, double rotateValue){
		dd.arcadeDrive(moveValue * RobotMap.DRIVE_SCALE_FACTOR, rotateValue * RobotMap.DRIVE_SCALE_FACTOR -0.05);
	}
	
	public void curvatureDrive(double moveSpeed, double turn) {
		dd.curvatureDrive(moveSpeed * RobotMap.DRIVE_SCALE_FACTOR, turn * RobotMap.DRIVE_SCALE_FACTOR, true);
	}
}
