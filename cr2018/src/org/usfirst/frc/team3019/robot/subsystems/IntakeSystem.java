package org.usfirst.frc.team3019.robot.subsystems;

import org.usfirst.frc.team3019.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeSystem extends Subsystem {

	VictorSP left;
	VictorSP right;
	
	DigitalInput stop;
	
	double speed;
	
	public IntakeSystem() {
		left = new VictorSP(RobotMap.leftIntakeMotor);
		right = new VictorSP(RobotMap.rightIntakeMotor);
		stop = new DigitalInput(3);
		speed = 0.0;
	}
	
	public void setMotors(double speed) {
		left.set(speed);
		right.set(-speed);
		this.speed = speed;
	}
	
	public void stopMotors() {
		left.stopMotor();
		right.stopMotor();
		speed = 0.0;
	}
	
	@Override
	public void periodic() {
		if(stop.get() && speed < 0.0) {
			stopMotors();
			getCurrentCommand().cancel();
		}
	}
	
	@Override
	protected void initDefaultCommand() {
		
	}
}
