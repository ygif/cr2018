package org.usfirst.frc.team3019.robot.subsystems;

import org.usfirst.frc.team3019.robot.RobotMap;
import org.usfirst.frc.team3019.robot.commands.RotateArm;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ArmRotator extends Subsystem {

	VictorSP rotateMotorOne;
	VictorSP rotateMotorTwo;
	
	public DigitalInput stopper;
	
	public ArmRotator() {
		rotateMotorOne = new VictorSP(RobotMap.rotateArmMotorOne);
		rotateMotorTwo = new VictorSP(RobotMap.rotateArmMotorTwo);
		
		stopper = new DigitalInput(RobotMap.armRotateSwitch);
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
