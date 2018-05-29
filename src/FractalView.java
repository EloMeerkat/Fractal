import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class FractalView extends Thread {
	protected double xmin = 0;
	protected double ymin = 0;
	protected double xmax = 0;
	protected double ymax = 0;
	protected int width = 0;
	protected int height = 0;
	protected FractalPanel panel;
	protected Fractal fractal;
	protected BufferedImage image;
	protected int[] data;
	private boolean reStart = false;
	
	private FractalListener listener;
	public void setFractalListener(FractalListener l) {
		listener= l;
	}
	
	public FractalView() {
		// TODO Auto-generated constructor stub
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public Fractal getFractal(){
		return fractal;
	}
	
	public void setMinMax(double xMin, double xMax, double yMin, double yMax){
		this.xmin = xMin;
		this.xmax = xMax;
		this.ymin = yMin;
		this.ymax = yMax;
		reDo();
	}
	
	public void setViewSize(Dimension sz){
		height = sz.height;
		width = sz.width;
		reDo();
	}
	
	public void setFractal(Fractal fractal){
		this.fractal = fractal;
	}
	
	public void setPanel(FractalPanel panel){
		this.panel = panel;
	}
	
	public Complex getPosFractalFromPanel(int x, int y){
		double X, Y;
		X = xmin + x*(xmax-xmin)/width;
		Y = ymin + y*(ymax-ymin)/height;
		
		return new Complex(X,Y);
	}
	
	public Point getPosViewFromFractal(Complex Z){
		int x,y;
		x = (int) ((Z.getReal()-xmin)*width/(xmax-xmin));
		y = (int) ((Z.getImg()-ymin)*height/(ymax-ymin));
		
		return new Point(x,y);
	}
	
	public void calculate(){
		long startTime= System.currentTimeMillis();
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int [] data= new int[height*width];
		for(int y=0; y<height; y++)
			if(reStart) return;
			else for(int x=0; x<width; x++)
				data[x+y*width]= 1000*fractal.dot(getPosFractalFromPanel(x,y));
		image.getRaster().setDataElements(0, 0, width, height, data);
		if(listener!= null)
			System.out.println(String.format("time %d", System.currentTimeMillis() - startTime));
			listener.imageDone();
		synchronized (this){
			try{
				wait();
			} catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	public BufferedImage getImage(){
		return image;
	}
	
	@Override
	public void run() {
		while (true){
			calculate();
			reStart = false;
		}
	}
	
	public synchronized void reDo() {
		if(isAlive()){
			reStart = true;
			notify();
		}
	}
	
}
