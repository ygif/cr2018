package org.usfirst.frc.team3019.robot.subsystems;

import org.usfirst.frc.team3019.robot.RobotMap;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {

	VictorSP motor;
	
	public Climber() {
		motor = new VictorSP(RobotMap.climbMotor);
	}
	
	public void setMotors(double speed) {
		motor.set(speed);
	}
	
	public void stopMotors(){
		motor.stopMotor();
	}
	
	@Override
	protected void initDefaultCommand() {
		
	}
}
