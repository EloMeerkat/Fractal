import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.ImageObserver;

import javax.swing.JPanel;


public class FractalPanel extends JPanel implements FractalListener {
	private FractalView [] view = new FractalView[8];
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Dimension sz = getSize();
		for(int i=0; i<view.length; i++){
			g.drawImage(view[i].getImage(), 0, i*(sz.height/view.length),sz.width, sz.height/view.length, new ImageObserver() {
				
				@Override
				public boolean imageUpdate(Image img, int infoflags, int x, int y,
						int width, int height) {
					// TODO Auto-generated method stub
					return false;
				}
			});
		}
	}

	/**
	 * Create the panel.
	 */
	public FractalPanel() {
		double j;
		for(int i=0; i<view.length; i++){
			view[i] = new FractalView();

			view[i].setPanel(this);
			view[i].setFractal(new Mandelbrot());
			
			view[i].setFractalListener(this);
			
			view[i].setMinMax(-2, 1, -1.5+i*3.0/(view.length), -1.5+(i+1)*3.0/(view.length));
		}
		
		addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentResized(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				Dimension sz = getSize();
				Dimension sizeView = new Dimension(sz.width, sz.height/view.length);
				
				for(int i=0; i<view.length; i++){
					view[i].setViewSize(sizeView);
					if(!view[i].isAlive()) {
						System.out.println("Thread is not alive.");
						view[i].start();
					}
				}
			}
			
			@Override
			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void imageDone() {
		// TODO Auto-generated method stub
		this.repaint();
		
	}
}
