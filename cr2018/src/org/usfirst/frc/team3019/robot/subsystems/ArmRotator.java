package org.usfirst.frc.team3019.robot.subsystems;

import org.usfirst.frc.team3019.robot.RobotMap;
import org.usfirst.frc.team3019.robot.commands.RotateArm;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ArmRotator extends Subsystem {

	WPI_TalonSRX rotateMotorOne;
	VictorSP rotateMotorTwo;
	
	public DigitalInput stopper;
	
	public ArmRotator() {
		rotateMotorOne = new WPI_TalonSRX(RobotMap.rotateClawMotorOne);
		rotateMotorTwo = new VictorSP(8);
		
		stopper = new DigitalInput(4);
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
