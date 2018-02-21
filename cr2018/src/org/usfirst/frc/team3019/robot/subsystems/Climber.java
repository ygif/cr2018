package org.usfirst.frc.team3019.robot.subsystems;

import org.usfirst.frc.team3019.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {

	WPI_TalonSRX left;
	WPI_TalonSRX right;
	
	public Climber() {
		left = new WPI_TalonSRX(RobotMap.leftClimbMotor);
		right = new WPI_TalonSRX(RobotMap.rightClimbMotor);
	}
	
	public void setMotors(double speed) {
		left.set(speed);
		right.set(speed);
	}
	
	public void stopMotors(){
		left.stopMotor();
		right.stopMotor();
	}
	
	@Override
	protected void initDefaultCommand() {
		
	}
}
