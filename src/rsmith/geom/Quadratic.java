package rsmith.geom;

/**
 * @author agrippa
 * 
 */
public class Quadratic implements Comparable<Quadratic> {

	private double a;
	private double b;
	private double c;

	/**
	 * @param a
	 * @param b
	 * @param c
	 */
	public Quadratic(double a, double b, double c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	/**
	 * @param x
	 * @return the y value of this quadratic at x
	 */
	public double eval(double x) {
		return Quadratic.evalQuadratic(x, a, b, c);
	}

	/**
	 * @return solutions, if any, to this quadratic
	 */
	public double[] solve() {
		return Quadratic.solveQuadratic(a, b, c);
	}

	/**
	 * @param q
	 * @return the points, if any, where this quadratic interesects with q
	 *         sorted by x-value
	 */
	public double[] intersect(Quadratic q) {
		double A = q.a - this.a;
		double B = q.b - this.b;
		double C = q.c - this.c;
		return solveQuadratic(A, B, C);
	}

	/**
	 * @param a
	 * @param b
	 * @param c
	 * @return solution to a*x^2+bx+c in sorted order
	 */
	public static double[] solveQuadratic(double a, double b, double c) {
		double[] result = null;
		double d = b * b - 4 * a * c;
		if (d > 0) {
			double s = Math.sqrt(d);
			result = new double[2];
			double x1 = (-b + s) / (2 * a);
			double x2 = (-b - s) / (2 * a);
			result[0] = Math.min(x1, x2);
			result[1] = Math.max(x1, x2);
		}
		return result;
	}

	/**
	 * @param x
	 * @param a
	 * @param b
	 * @param c
	 * @return evaluate a*x^2+bx+c
	 */
	public static double evalQuadratic(double x, double a, double b, double c) {
		return a * x * x + b * x + c;
	}

	/**
	 * @return get a
	 */
	public double getA() {
		return a;
	}

	/**
	 * @param a
	 */
	public void setA(double a) {
		this.a = a;
	}

	/**
	 * @return get b
	 */
	public double getB() {
		return b;
	}

	/**
	 * @param b
	 */
	public void setB(double b) {
		this.b = b;
	}

	/**
	 * @return get c
	 */
	public double getC() {
		return c;
	}

	/**
	 * @param c
	 */
	public void setC(double c) {
		this.c = c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(a);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(b);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(c);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Quadratic other = (Quadratic) obj;
		if (Double.doubleToLongBits(a) != Double.doubleToLongBits(other.a))
			return false;
		if (Double.doubleToLongBits(b) != Double.doubleToLongBits(other.b))
			return false;
		if (Double.doubleToLongBits(c) != Double.doubleToLongBits(other.c))
			return false;
		return true;
	}

	public int compareTo(Quadratic o) {
		// TODO Auto-generated method stub
		return 0;
	}
 
	public String toString() {
		return "[a="+a+",b="+b+",c="+c+"]";
	}
}
