package rsmith.util;

public class NumberUtils {
	public static double randomNumber(double min, double max) {
		return Math.random() * (max - min) + min;
	}
}
