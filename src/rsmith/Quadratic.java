package rsmith;

public class Quadratic {
	
	private double a;
	private double b;
	private double c;
	
	public Quadratic(double a, double b, double c) {
		this.a=a;
		this.b=b;
		this.c=c;
	}
	
	public double eval(double x)  {
		return Quadratic.evalQuadratic(x,a,b,c);
	}
	
	public double  [] solve() {
		return Quadratic.solveQuadratic(a,b,c);
	}
	
	public double [] intersect(Quadratic q) {
		double A = q.a-this.a;
		double B = q.b-this.b;
		double C = q.c-this.c;
		return solveQuadratic(A,B,C);
	}
	
	public static double []  solveQuadratic(double a, double b, double c) {
		double [] result = null;
		double d = b*b-4*a*c;
		if(d>0) {
			double s  = Math.sqrt(d);
			result = new double[2];
			result[0] = (b+s)/(2*a);
			result[1] = (b-s)/(2*a);
		} 
		return result;
	}
	
	public static double evalQuadratic(double  x, double a, double b, double c) {
		return a*x*x+b*x+c;
	}
	

}
