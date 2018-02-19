package org.usfirst.frc.team3019.robot.subsystems;

import org.usfirst.frc.team3019.robot.RobotMap;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeSystem extends Subsystem {

	VictorSP left;
	VictorSP right;
	
	public IntakeSystem() {
		left = new VictorSP(RobotMap.leftIntakeMotor);
		right = new VictorSP(RobotMap.rightIntakeMotor);
	}
	
	public void setMotors(double speed) {
		left.set(speed);
		right.set(-speed);
	}
	
	public void stopMotors() {
		left.stopMotor();
		right.stopMotor();
	}
	
	@Override
	protected void initDefaultCommand() {
		
	}
}
