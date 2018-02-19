package org.usfirst.frc.team3019.robot.subsystems;

import org.usfirst.frc.team3019.robot.RobotMap;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ArmRotator extends Subsystem {

	VictorSP rotateMotor;
	
	public ArmRotator() {
		rotateMotor = new VictorSP(RobotMap.rotateClawMotor);
	}
	
	public void rotateArm(double speed) {
		rotateMotor.setSpeed(speed);
	}
	
	public void stopArmRotation() {
		rotateMotor.stopMotor();
	}
	
	@Override
	protected void initDefaultCommand() {
		
	}
}
