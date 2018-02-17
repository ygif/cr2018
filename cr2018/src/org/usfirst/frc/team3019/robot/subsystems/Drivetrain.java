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
		
		leftFrontMotor = new VictorSP(0);
		leftRearMotor = new VictorSP(1);
		SpeedControllerGroup left = new SpeedControllerGroup(leftFrontMotor, leftRearMotor);
		
		rightFrontMotor = new VictorSP(2);
		rightRearMotor = new VictorSP(3);
		SpeedControllerGroup right = new SpeedControllerGroup(rightFrontMotor);
		
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
		dd.curvatureDrive(moveSpeed, turn, true);
	}
}
