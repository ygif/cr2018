package org.usfirst.frc.team3019.util;

import edu.wpi.first.wpilibj.XboxController;

public class PlaybackXboxController extends XboxController{
	
	private boolean[] macroButtonValues;
	private double[] macroAxisValues;
	private int[] macroPOVValues;
	private boolean playback = false;

	public PlaybackXboxController(int port) {
		super(port);
		macroButtonValues = new boolean[super.getButtonCount()];
		macroAxisValues = new double[super.getAxisCount()];
		macroPOVValues = new int[super.getPOVCount()];
	}
	
	public void setButtonValues(boolean[] values) {
		for (int i = 0; i < values.length; i++) {
			macroButtonValues[i] = values[i];
		}
	}
	
	public void setAxisValues(double[] values) {
		for (int i = 0; i < values.length; i++) {
			macroAxisValues[i] = values[i];
		}
	}
	
	public void setPOVValues(int[] values) {
		for (int i = 0; i < values.length; i++) {
			macroPOVValues[i] = values[i];
		}
	}
	
	public void setPlaybackMode(boolean mode) {
		playback = mode;
	}
	
	@Override
	public boolean getRawButton(int button) {
		return (playback) ? macroButtonValues[button - 1] : super.getRawButton(button);
	}
	
	@Override
	public double getRawAxis(int axis) {
		return (playback) ? macroAxisValues[axis] : super.getRawAxis(axis);
	}
	
	@Override
	public int getPOV(int pov) {
		return (playback) ? macroPOVValues[pov] : super.getPOV(pov);
	}
}
