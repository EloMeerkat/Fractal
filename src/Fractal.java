
public abstract class Fractal{
	protected int max = 2500;
	
	public Fractal() {
		// TODO Auto-generated constructor stub
	}
	
	public abstract int dot(Complex z);
	
	public void setMax(int max){
		this.max = max;
	}
	
	public int getMax(){
		return max;
	}
}
