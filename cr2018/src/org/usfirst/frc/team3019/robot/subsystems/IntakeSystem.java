package org.usfirst.frc.team3019.robot.subsystems;

import org.usfirst.frc.team3019.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeSystem extends Subsystem {

	VictorSP left;
	VictorSP right;
	
	public DigitalInput stop;
	
	public double speed;
	
	public IntakeSystem() {
		left = new VictorSP(RobotMap.leftIntakeMotor);
		right = new VictorSP(RobotMap.rightIntakeMotor);
		stop = new DigitalInput(RobotMap.intakeStopSwitch);
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
	
	// Look in Drivetrain.java. We need to have the same type of function for the VictorSPs in this file
	/*private void setTalonCurrLimit(WPI_TalonSRX tal,  int amps)
	{
		tal.configContinuousCurrentLimit(amps, 1000);
		tal.configPeakCurrentLimit(amps, 1000);
		tal.configPeakCurrentDuration(0, 1000);
		tal.enableCurrentLimit(true);
	}*/
	
	@Override
	protected void initDefaultCommand() {
		
	}
	
	@Override
	public void periodic() {
		SmartDashboard.putBoolean("Intake Switch", !stop.get());
		SmartDashboard.putNumber("Intake Speed", speed);
	}
}
