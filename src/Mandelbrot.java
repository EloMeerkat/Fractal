
public class Mandelbrot extends Fractal {
	public Mandelbrot() {
		// TODO Auto-generated constructor stub
	}
	
	public int dot(Complex z){
		Complex Zi = new Complex(0,0);
		int i = 0;
		
		for(; i<max; i++){
			Zi = Zi.sqr().add(z);
			if(Zi.sqrModulus() > 4){
				return i;
			}
		}
		return 0;
	}
}
