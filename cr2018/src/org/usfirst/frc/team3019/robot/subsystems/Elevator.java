package org.usfirst.frc.team3019.robot.subsystems;

import org.usfirst.frc.team3019.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends Subsystem {

	VictorSP one;
	DigitalInput top;
	DigitalInput bottom;
	
	double speed;
	
	public Elevator() {
		one = new VictorSP(RobotMap.elevatorMotorOne);
		top = new DigitalInput(RobotMap.topElevatorSwitch);
		bottom = new DigitalInput(RobotMap.bottomElevatorSwitch);
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
		
	}
	
	@Override
	public void periodic() {
		SmartDashboard.putNumber("elevator speed", speed);
		if((top.get() && speed > 0.0) || (bottom.get() && speed < 0.0)) {
			stopMotors();
			getCurrentCommand().cancel();
		}
	}
}
