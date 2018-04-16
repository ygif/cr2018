package org.usfirst.frc.team3019.robot.subsystems;

import org.usfirst.frc.team3019.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ClawRotator extends Subsystem {

	VictorSP one;
	public DigitalInput stopper;
	
	public double speed;
	
	public ClawRotator() {
		one = new VictorSP(RobotMap.clawRotateMotorOne);
		stopper = new DigitalInput(RobotMap.clawSwitch);
		speed = 0.0;
	}
	
	public void setMotors(double speed) {
		one.set(speed);
		this.speed = speed;
	}
	
	public void stopMotors() {
		one.stopMotor();
		speed = 0.0;
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(null);
	}
}
