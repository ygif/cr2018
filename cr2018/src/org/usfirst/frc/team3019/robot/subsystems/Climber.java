package org.usfirst.frc.team3019.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem{

	VictorSP left;
	VictorSP right;
	
	public Climber(int leftDevice, int rightDevice) {
		left = new VictorSP(leftDevice);
		right = new VictorSP(rightDevice);
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
