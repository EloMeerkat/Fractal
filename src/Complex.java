public class Complex implements Cloneable {

	private double re=0;
	private double im=0;
	
	public Complex() {}

	public Complex(double r, double i) {
		re=r; im=i;
	}

	public double sqrModulus() {
		return re*re+im*im;
	}

	public Complex sqr() {
		return new Complex(re*re-im*im, 2*re*im);
	}

	public Complex add(Complex z) {
		return new Complex(re+z.re, im+z.im);
	}

	public double getReal() {
		return re;
	}

	public double getImg() {
		return im;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}