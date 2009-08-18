package rsmith.util;

public class NumberUtils {
	
	public static double EPSILON = 10E-9;
	
	public static double randomNumber(double min, double max) {
		double result = Math.random() * (max - min) + min;
		// System.out.println("max="+max+"min="+min+",result="+result);
		return result;
	}
	
	public static boolean lt(double a, double b) {
		return (a + NumberUtils.EPSILON < b);
	}
	
	public static boolean gt(double a, double b) {
		return (a - NumberUtils.EPSILON > b);
	}
	
	public static boolean eq(double a, double b) {
		return (Math.abs(b-a) < NumberUtils.EPSILON);
	}
	
	public static boolean geq(double a, double b) {
		return eq(a,b) || gt(a,b); 
	}
	
	public static boolean  leq(double a, double b) {
		return eq(a,b) || lt(a,b);
	}
	
	public static boolean neq(double a, double b) {
		return !eq(a,b);
	}
	
}
