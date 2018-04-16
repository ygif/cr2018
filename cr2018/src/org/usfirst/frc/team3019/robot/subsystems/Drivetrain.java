package org.usfirst.frc.team3019.robot.subsystems;

import org.usfirst.frc.team3019.robot.RobotMap;
import org.usfirst.frc.team3019.robot.commands.Drive;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
/**
 *
 */
public class Drivetrain extends Subsystem {
	
	WPI_TalonSRX leftFrontMotor;
	WPI_TalonSRX leftRearMotor;
	WPI_TalonSRX rightFrontMotor;
	WPI_TalonSRX rightRearMotor;
	DifferentialDrive dd;
	
	public Drivetrain() {
		super();
		
		leftFrontMotor = new WPI_TalonSRX(RobotMap.leftFront);
		leftRearMotor = new WPI_TalonSRX(RobotMap.leftBack);
		
		/*leftFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 20);
		leftRearMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 20);
		leftFrontMotor.setSensorPhase(false);
		leftRearMotor.setSensorPhase(false);*/
		
		SpeedControllerGroup left = new SpeedControllerGroup(leftFrontMotor, leftRearMotor);
		left.setInverted(true);
		
		rightFrontMotor = new WPI_TalonSRX(RobotMap.rightFront);
		rightRearMotor = new WPI_TalonSRX(RobotMap.rightBack);
		
		//Sets max current for talon srxs
		int maxCurr = 25;
		setTalonCurrLimit(leftFrontMotor, maxCurr);
		setTalonCurrLimit(leftRearMotor, maxCurr);

		setTalonCurrLimit(rightFrontMotor, maxCurr);
		setTalonCurrLimit(rightRearMotor, maxCurr);

		//rightFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 20);
		//rightRearMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 20);
		//rightFrontMotor.setSensorPhase(false);
		//rightRearMotor.setSensorPhase(false);
		
		SpeedControllerGroup right = new SpeedControllerGroup(rightFrontMotor, rightRearMotor);
		right.setInverted(true);
		
		dd = new DifferentialDrive(left, right);
		dd.setSafetyEnabled(false);
		dd.setDeadband(0.1);
	}
	
	//Sets max current/amps for talon srxs
	private void setTalonCurrLimit(WPI_TalonSRX tal,  int amps) {
		tal.configContinuousCurrentLimit(amps, 1000);
		tal.configPeakCurrentLimit(amps, 1000);
		tal.configPeakCurrentDuration(0, 1000);
		tal.enableCurrentLimit(true);
	}
	
	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new Drive());
	}
	
	public void arcadeDrive(double moveValue, double rotateValue){
		dd.arcadeDrive(moveValue * RobotMap.DRIVE_SCALE_FACTOR, rotateValue * RobotMap.TURN_SCALE_FACTOR);
	}
	
	public void curvatureDrive(double moveSpeed, double turn) {
		dd.curvatureDrive(moveSpeed * RobotMap.DRIVE_SCALE_FACTOR, turn * RobotMap.TURN_SCALE_FACTOR, true);
	}
	
	public void postEncoderValues() {
		//SmartDashboard.putNumber("left position", leftFrontMotor.getSelectedSensorPosition(0));
		//SmartDashboard.putNumber("right position", rightFrontMotor.getSelectedSensorPosition(0));
		//SmartDashboard.putNumber("left velocity", leftFrontMotor.getSelectedSensorVelocity(0));
		//SmartDashboard.putNumber("right velocity", rightFrontMotor.getSelectedSensorVelocity(0));
	}
}
