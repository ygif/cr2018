package org.usfirst.frc.team3019.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.usfirst.frc.team3019.robot.Robot;
import org.usfirst.frc.team3019.robot.RobotMap;
import org.usfirst.frc.team3019.robot.commands.Drive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Playback {

	private final String PATH = "/home/lvuser";
	private File file;
	private BufferedReader br;
	public boolean isRunning;
	private ArrayList<String> values;
	private ArrayList<String> fieldValues;
	private int loc;
	private double startTime;

	/**
	 * Creates an instance of Playback using the file with the specified name
	 * 
	 * @param name file to load
	 */
	public Playback(String name) {
		file = new File(PATH + "/" + name + ".txt");
		values = new ArrayList<String>();
		loc = 1;
		fieldValues = new ArrayList<String>();
		storeCurrentConstants();
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Scheduler.getInstance().add(new Drive(-0.5, 0, 4.1));
		}
		isRunning = false;
	}

	/**
	 * Creates an instance of Playback using a file named temp
	 */
	public Playback() {
		this("temp");
	}

	public void start() {
		isRunning = true;
		System.out.println("Started playing back recording.");
		try {
			String in = br.readLine();
			while (in != null) {
				values.add(in);
				in = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		setTempConstants();
		startTime = Timer.getFPGATimestamp();
	}

	/**
	 * Set RobotMap constant values to the values recorded on the file
	 */
	private void setTempConstants() {
		if (values.get(0).contains("Constants")) {
			// 0 is Constants
			// i the variable's name, i + 1 is its value
			String[] cons = values.get(0).split(" ");
			for (int i = 1; i < cons.length; i += 2) {
				Field[] temp = RobotMap.class.getDeclaredFields();
				for (int j = 0; j < temp.length; j++) {
					try {
						if (temp[j].getName().equals("orientForward")) {
							temp[j].setBoolean(RobotMap.class, Boolean.parseBoolean(cons[i + 1]));
						} else if (temp[j].getName().equals(cons[i])) {
							temp[j].setDouble(RobotMap.class, Double.parseDouble(cons[i + 1]));
						}
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void playback() {
		if (loc < values.size()) {
			String[] input = values.get(loc).split(";", 4);
			// At the moment input[0] contains a timestamp and the battery voltage
			// if the information is necessary to use in the future.
			
			// set buttons
			{
				String[] tokens = input[1].split(" ");
				boolean[] buttonStates = new boolean[tokens.length];
				for (int i = 0; i < tokens.length; i++) {
					buttonStates[i] = tokens[i].equalsIgnoreCase("true");
				}
				Robot.oi.xbox.setButtonValues(buttonStates);
			}
			// set axes
			{
				String[] tokens = input[2].split(" ");
				double[] axisValues = new double[tokens.length];
				for (int i = 0; i < tokens.length; i++) {
					axisValues[i] = Double.parseDouble(tokens[i]);
				}
				Robot.oi.xbox.setAxisValues(axisValues);
			}
			// set POV buttons
			{
				String[] tokens = input[3].split(" ");
				int[] povValues = new int[tokens.length];
				for (int i = 0; i < tokens.length; i++) {
					povValues[i] = Integer.parseInt(tokens[i]);
				}
				Robot.oi.xbox.setPOVValues(povValues);
			} 
		} else if (loc == values.size()) {
			System.out.println("Reached the end of the recording");
		}
		loc++;
	}

	public void stop() {
		Robot.oi.xbox.setButtonValues(new boolean[Robot.oi.xbox.getButtonCount()]);
		Robot.oi.xbox.setAxisValues(new double[Robot.oi.xbox.getAxisCount()]);
		Robot.oi.xbox.setPOVValues(new int[Robot.oi.xbox.getPOVCount()]);
		setCurrentConstants();
		isRunning = false;
	}

	/**
	 * Stores current RobotMap values in an ArrayList
	 */
	public void storeCurrentConstants() {
		Field[] temp = RobotMap.class.getDeclaredFields();
		for (int i = 0; i < temp.length; i++) {
			String s = temp[i].getName();
			if (s.contains("SCALE_FACTOR") || s.equals("orientForward")) {
				fieldValues.add(temp[i].getName());
				try {
					if (temp[i].getType().equals(double.class)) {
						fieldValues.add(Double.toString(temp[i].getDouble(RobotMap.class)));
					} else if (temp[i].getType().equals(boolean.class)) {
						fieldValues.add(Boolean.toString(temp[i].getBoolean(RobotMap.class)));
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Set RobotMap constants back to their values before playback
	 */
	public void setCurrentConstants() {
		// i the variable's name, i + 1 is its value
		for (int i = 0; i < fieldValues.size(); i += 2) {

			Field[] temp = RobotMap.class.getDeclaredFields();
			for (int j = 0; j < temp.length; j++) {
				try {
					if (temp[j].getName().equals("orientForward")) {
						temp[j].setBoolean(RobotMap.class, Boolean.parseBoolean(fieldValues.get(i + 1)));
					} else if (temp[j].getName().equals(fieldValues.get(i))) {
						temp[j].setDouble(RobotMap.class, Double.parseDouble(fieldValues.get(i + 1)));
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public double getTimingDifference() {
		double currentTime = Timer.getFPGATimestamp() - startTime;
		return currentTime - Double.parseDouble(values.get(loc)
				.split(";", 4)[0]
				.split(" ")[0]);
	}
	
	public double getRecordedPeriod() {
		return Double.parseDouble(values.get(loc)
				.split(";")[0]
				.split(" ")[1]);
	}
}
