/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3019.robot;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.IOException;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team3019.robot.commands.Drive;
import org.usfirst.frc.team3019.robot.subsystems.ArmRotator;
import org.usfirst.frc.team3019.robot.subsystems.Climber;
import org.usfirst.frc.team3019.robot.subsystems.Drivetrain;
import org.usfirst.frc.team3019.robot.subsystems.ClawRotator;
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
	public static ClawRotator clawRotator;
	public static IntakeSystem intakeSystem;
	public static ArmRotator armRotator;
	public static OI oi;

	SendableChooser<String> station = new SendableChooser<String>();
	SendableChooser<Boolean> shouldRecord = new SendableChooser<Boolean>();
	SendableChooser<String> switchSide = new SendableChooser<String>();
	// SendableChooser<String> scaleSide = new SendableChooser<String>();
	Recorder recorder;
	Playback auto;

	public Robot() {
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
		 * outputStream.putFrame(output); } }).start();
		 */
	}

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		driveTrain = new Drivetrain();
		climber = new Climber();
		clawRotator = new ClawRotator();
		intakeSystem = new IntakeSystem();
		armRotator = new ArmRotator();

		recorder = new Recorder(RobotMap.xbox1port);

		station.addDefault("Left station", "left");
		station.addObject("Center station", "center");
		station.addObject("Right station", "right");
		SmartDashboard.putData("Alliance station", station);

		shouldRecord.addDefault("Don't record", Boolean.FALSE);
		shouldRecord.addObject("record", Boolean.TRUE);
		SmartDashboard.putData("Toggle Recorder", shouldRecord);

		switchSide.addDefault("Left Switch", "L");
		switchSide.addObject("Right Switch", "R");
		SmartDashboard.putData("Switch Side", switchSide);

		oi = new OI();
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
		// setPeriod(0.02);
		oi.xbox.setPlaybackMode(false);
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
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

		// Scheduler.getInstance().add(new Drive(-0.5, 0, 4.0));
		if (auto == null) {
			String name = DriverStation.getInstance().getGameSpecificMessage().substring(0, 2);
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
		
		// setPeriod(auto.getRecordedPeriod());
	}

	@Override
	public void teleopInit() {
		if (auto != null) {
			auto.stop();
			auto = null;
		}
		// setPeriod(0.02);
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

	@Override
	public void robotPeriodic() {
		SmartDashboard.putString("left stick", oi.xbox.getX(Hand.kLeft) + " " + oi.xbox.getY(Hand.kLeft));
		SmartDashboard.putNumber("time", Timer.getFPGATimestamp());
		SmartDashboard.putBoolean("Recorder on?", recorder.isRunning);
		driveTrain.postEncoderValues();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
