package display;

import java.awt.EventQueue;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class Window extends JFrame implements ComponentListener{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Panel panel;
	OptionsWindow op;
	public static boolean MULTI_THR = false;
	public static int WIDTH = 720*16/9;
	public static int HEIGHT = 720;
	public static Double ASPECT_RATIO = (Double)(WIDTH/1.)/(Double)(HEIGHT/1.);
	public static Double FOV_X = 90.;
	public static Double FOV_Y = Math.toDegrees(Panel.calculateFovYRad(Math.toRadians(FOV_X), ASPECT_RATIO));
	public static Double DISTANCE_VIEW = 50.;
	
	
	public static void main(String[] args) {
		
		int reply = JOptionPane.showConfirmDialog(null, "Wielow¹tkowoœæ?", "Tytu³", JOptionPane.YES_NO_OPTION);
		if(reply == JOptionPane.YES_OPTION) {
			MULTI_THR = true;
		}
		
		
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				new Window();
			}
			
		});
		
	}
	
	public Window() {
		super("3D program");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setLocationRelativeTo(null);
		setSize(WIDTH,HEIGHT);
		addComponentListener(this);
		
		
		panel = new Panel();
		//panel.setSize(WIDTH, HEIGHT);
		setContentPane(panel);
		op = new OptionsWindow(this);
		setVisible(true);
		op.toFront();
		
	}

	@Override
	public void componentResized(ComponentEvent e) {
		WIDTH = this.getWidth();
		HEIGHT = this.getHeight();
		ASPECT_RATIO = (Double)(WIDTH/1.)/(Double)(HEIGHT/1.);
		op.setUpCamFovYBarOnFovX(FOV_X);
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
}
