/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3019.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.IOException;

import org.usfirst.frc.team3019.robot.subsystems.Climber;
import org.usfirst.frc.team3019.robot.subsystems.Drivetrain;
import org.usfirst.frc.team3019.robot.subsystems.Elevator;
import org.usfirst.frc.team3019.robot.subsystems.IntakeSystem;
import org.usfirst.frc.team3019.util.Playback;
import org.usfirst.frc.team3019.util.Recorder;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	public static Drivetrain driveTrain;
	public static Climber climber;
	public static Elevator elevator;
	public static IntakeSystem intakeSystem;
	public static OI oi;

	SendableChooser<String> station = new SendableChooser<String>();
	SendableChooser<Boolean> shouldRecord = new SendableChooser<Boolean>();
	SendableChooser<String> switchSide = new SendableChooser<String>();
	Recorder recorder;
	Playback auto;

	public void robot() {
		driveTrain = new Drivetrain();
		climber = new Climber();
		elevator = new Elevator();
		intakeSystem = new IntakeSystem();
		
		recorder = new Recorder(RobotMap.xbox1port);

		station.addDefault("Left station", "left");
		station.addObject("Center station", "center");
		station.addObject("Right station", "right");
		SmartDashboard.putData("Alliance station", station);

		shouldRecord.addDefault("Don't record", Boolean.FALSE);
		shouldRecord.addObject("record", Boolean.TRUE);
		SmartDashboard.putData("Toggle Recorder", shouldRecord);

		switchSide.addDefault("Left", "L");
		switchSide.addObject("Right", "R");
		SmartDashboard.putData("Switch Side", switchSide);
	}
	
	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();

		/*
		 * new Thread(() -> {
		 * 
		 * UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		 * camera.setResolution(640, 480);
		 * 
		 * CvSink cvSink = CameraServer.getInstance().getVideo(); CvSource outputStream
		 * = CameraServer.getInstance().putVideo("Blur", 640, 480);
		 * 
		 * Mat source = new Mat(); Mat output = new Mat();
		 * 
		 * while (!Thread.interrupted()) { cvSink.grabFrame(source);
		 * Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
		 * outputStream.putFrame(output); }
		 * 
		 * }).start();
		 */
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {
		recorder.stop();
		if (auto != null) {
			auto.stop();
			auto = null;
		}
		oi.xbox.setPlaybackMode(false);
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		if (recorder.isRunning) {
			recorder.stop();
		}
		if (auto == null) {
			String name = DriverStation.getInstance().getGameSpecificMessage().substring(0, 1);
			name += station.getSelected();
			auto = new Playback(name);
		}
		auto.start();
		oi.xbox.setPlaybackMode(true);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		auto.playback();
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		if (auto != null) {
			auto.stop();
			auto = null;
		}
		oi.xbox.setPlaybackMode(false);
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {

		if (shouldRecord.getSelected().booleanValue() && !recorder.isRunning) {
			recorder.start(switchSide.getSelected() + station.getSelected());
		} else if (shouldRecord.getSelected().booleanValue() == false && recorder.isRunning) {
			recorder.stop();
		}
		if (recorder.isRunning) {
			try {
				recorder.record();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
