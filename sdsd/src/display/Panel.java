package display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import math.D3CoordinateMatrix;
import math.DefinedMatrixs;
import math.Matrix;
import math.MatrixException;
import math.SquareMatrix;
import world.Cube;
import world.Mesh;
import world.World;

public class Panel extends JPanel implements Runnable, MouseMotionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	World world;
	
	public Panel() {
		super();
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		addMouseMotionListener(this);
		world = new World();
		Thread t = new Thread(this);
		t.start();
		addKeyListener(world);
		
		
		
		setFocusable(true);
		
	}
	

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		
		
		g2.translate(0, Window.HEIGHT);
		g2.scale(1, -1);
		setSize(Window.WIDTH, Window.HEIGHT);
		
		if(Window.MULTI_THR == true) {
			for(Mesh mesh : world.getMeshList()) {
				synchronized (mesh.getDisplayCords()) {
					mesh.draw(g2);
				}
			}
		} else {
			world.draw(g2);
		}
	}


	ArrayList<D3CoordinateMatrix> storage;
	D3CoordinateMatrix cons;
	int i = 0;
	int keyGiven;
	Mesh meshStr = null;
	HashMap<Integer, D3CoordinateMatrix> bufor1 = new HashMap<Integer, D3CoordinateMatrix>();
	HashMap<Integer, D3CoordinateMatrix> bufor2 = new HashMap<Integer, D3CoordinateMatrix>();
	HashMap<Integer, D3CoordinateMatrix> actualBufor = bufor1;
	SquareMatrix lookAtCopy;
	
	@Override
	public void run() {
		
		if (Window.MULTI_THR == true) {
			int threadAmount = 4;
			HashMap<Integer, D3CoordinateMatrix> toCalculate;
			storage = new ArrayList<D3CoordinateMatrix>();
			lookAtCopy = world.camera.lookAt.clone();
			
			
			new Thread(new Runnable() {

				@Override
				public void run() {
					while(true) {
						repaint();
						try {
							Thread.sleep(33);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
				}
				
			}).start();
			
			
			
			for (int k = 0; k < threadAmount; k++) {
				new Thread(new Runnable() {

					@Override
					public void run() {

						int li;
						D3CoordinateMatrix cord, ret;
						Mesh mesh;

						while (true) {

							synchronized (storage) {

								while (storage.isEmpty()) {
									try {
										storage.wait();
									} catch (InterruptedException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}

								cord = storage.get(0);
								li = i;
								mesh = meshStr;
								storage.clear();
							}
							try {
								ret = (D3CoordinateMatrix) Matrix.multiplyMatrixs(lookAtCopy, cord);
								ret = (D3CoordinateMatrix) Matrix.multiplyMatrixs(
										DefinedMatrixs.perspectiveProjection(Math.toRadians(Window.FOV_X),
												Math.toRadians(Window.FOV_Y), 1., Window.DISTANCE_VIEW),
										ret);
								ret.toNDC();

								synchronized (actualBufor) {
									actualBufor.put(li, ret);
								}

							} catch (MatrixException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

					}

				}).start();
			}
			while (true) {
				lookAtCopy = world.camera.lookAt.clone();

				for (Mesh mesh : world.getMeshList()) {

					meshStr = mesh;
					toCalculate = mesh.worldCords();

					while (true) {

						if (storage.isEmpty() && (i < toCalculate.size())) {

							synchronized (storage) {
								i++;
								storage.add(0, toCalculate.get(i));
								//System.out.println(i);
								storage.notifyAll();
							}

						}

						//System.out.println(toCalculate.size());
						if (actualBufor.size() >= toCalculate.size()) {
							synchronized (actualBufor) {
								mesh.setDisplayCords(actualBufor);
								if (actualBufor == bufor1) {
									actualBufor = bufor2;
								} else {
									actualBufor = bufor1;
								}
								actualBufor.clear();
								i = 0;
								break;
							}
						}
					}

				}

				//repaint();
				//System.out.println(i);
				//try {
				//	Thread.sleep(33);
				//} catch (InterruptedException e) {
				//	e.printStackTrace();
				//}
			} 
		} else {
			while(true) {
				repaint();
				try {
					Thread.sleep(33);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
	int x = 0;
	int y = 0;
	


	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

	@Override
	public void mouseMoved(MouseEvent event) {
		x = event.getX();
		y = event.getY();
	}
	
	public static Double calculateFovYRad(Double fovX, Double aspectRatio) {
		
		return 2*Math.atan(Math.tan(fovX/2) / aspectRatio);
		
	}
	
	public static Double calculateFovXRad(Double fovY, Double aspectRatio) {
		return 2*Math.atan(Math.tan(fovY/2) * aspectRatio);
	}
	
}
