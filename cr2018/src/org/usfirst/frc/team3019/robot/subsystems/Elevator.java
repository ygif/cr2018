package org.usfirst.frc.team3019.robot.subsystems;

import org.usfirst.frc.team3019.robot.RobotMap;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevator extends Subsystem {

	VictorSP one;
	VictorSP two;
	
	public Elevator() {
		one = new VictorSP(RobotMap.elevatorMotorOne);
		two = new VictorSP(RobotMap.elevatorMotorTwo);
	}
	
	public void setMotors(double speed) {
		one.set(speed);
		two.set(speed);
	}
	
	public void stopMotors() {
		one.stopMotor();
		two.stopMotor();
	}
	
	@Override
	protected void initDefaultCommand() {
		
	}
}
