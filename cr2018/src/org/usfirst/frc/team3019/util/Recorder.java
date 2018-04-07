package org.usfirst.frc.team3019.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

import org.usfirst.frc.team3019.robot.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Timer;

public class Recorder {

	private DriverStation ds;
	private int joystick;
	private int numOfButtons;
	private int numOfAxes;
	private int numOfPOV;
	private double startTime;
	private double timeStamp;
	private double loopTime;
	private final String PATH = "/home/lvuser";
	private File file;
	private FileWriter fw;
	public boolean isRunning;

	/**
	 * Create a new recorder with the given joystick
	 * 
	 * @param joystick joystick port number
	 */
	public Recorder(int joystick) {
		ds = DriverStation.getInstance();
		this.joystick = joystick;
		numOfButtons = ds.getStickButtonCount(this.joystick);
		numOfAxes = ds.getStickAxisCount(this.joystick);
		numOfPOV = ds.getStickPOVCount(this.joystick);
		isRunning = false;
	}

	/**
	 * Start recording input data from a joystick into the file with the specified
	 * name
	 * 
	 * @param name The name of the file to write to.
	 */
	public void start(String name) {
		file = new File(PATH + "/" + name + ".txt");
		file.setWritable(true);
		file.setReadable(true);

		if (file.exists()) {
			file.delete();
		}

		try {
			file.createNewFile();
			fw = new FileWriter(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		writeRobotConstants();

		startTime = Timer.getFPGATimestamp();
		timeStamp = 0.0;
		loopTime = 0.02;
		
		isRunning = true;
	}

	/**
	 * Start recording input data from a joystick into a file called temp
	 */
	public void start() {
		start("temp");
	}

	/**
	 * Takes the state of every button and axis and writes those to a file.
	 * 
	 * @throws IOException
	 */
	public void record() throws IOException {
		if (file == null || !file.exists()) {
			throw new FileNotFoundException("start() should be called before record.");
		}

		StringBuilder sb = new StringBuilder();
		double currentTime = Timer.getFPGATimestamp() - startTime;
		loopTime = currentTime - timeStamp;
		timeStamp = currentTime;
		sb.append(currentTime + " " + loopTime + " " + RobotController.getBatteryVoltage() + " ;");
		// record button states to a string
		for (int i = 1; i <= numOfButtons; i++) {
			sb.append(ds.getStickButton(joystick, i) + " ");
		}

		sb.append(';');
		// record axis values
		for (int i = 0; i < numOfAxes; i++) {
			sb.append(ds.getStickAxis(joystick, i) + " ");
		}

		sb.append(';');
		// record POV button states
		for (int i = 0; i < numOfPOV; i++) {
			sb.append(ds.getStickPOV(joystick, i) + " ");
		}

		fw.append(sb.toString() + "\n");
	}

	/**
	 * Records the value of some constants in the RobotMap class
	 */
	private void writeRobotConstants() {
		StringBuilder temp = new StringBuilder("Constants ");
		Field[] cons = RobotMap.class.getDeclaredFields();
		for (int i = 0; i < cons.length; i++) {
			String s = cons[i].getName();
			try {
				if (s.contains("SCALE_FACTOR")) {
					temp.append(s + " " + cons[i].getDouble(RobotMap.class) + " ");
				} else if (s.contains("orientForward")) {
					temp.append(s + " " + cons[i].getBoolean(RobotMap.class) + " ");
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}

		}
		
		temp.append("\n");
		try {
			fw.append(temp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		file = null;
		try {
			if (fw != null) {
				fw.close();
				fw = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		isRunning = false;
	}
}
