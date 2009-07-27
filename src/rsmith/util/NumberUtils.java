package rsmith.util;

public class NumberUtils {
	public static double randomNumber(double min, double max) {
		double result = Math.random() * (max - min) + min;
		// System.out.println("max="+max+"min="+min+",result="+result);
		return result;
	}
}
