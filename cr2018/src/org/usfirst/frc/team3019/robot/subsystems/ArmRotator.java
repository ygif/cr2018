package org.usfirst.frc.team3019.robot.subsystems;

import org.usfirst.frc.team3019.robot.RobotMap;
import org.usfirst.frc.team3019.robot.commands.RotateArm;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ArmRotator extends Subsystem {

	WPI_TalonSRX rotateMotorOne;
	WPI_TalonSRX rotateMotorTwo;
	
	public ArmRotator() {
		rotateMotorOne = new WPI_TalonSRX(RobotMap.rotateClawMotorOne);
		rotateMotorTwo = new WPI_TalonSRX(RobotMap.rotateClawMotorTwo);
	}
	
	public void rotateArm(double speed) {
		rotateMotorOne.set(-speed);
		rotateMotorTwo.set(speed);
	}
	
	public void stopArmRotation() {
		rotateMotorOne.stopMotor();
		rotateMotorTwo.stopMotor();
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new RotateArm());
	}
}
