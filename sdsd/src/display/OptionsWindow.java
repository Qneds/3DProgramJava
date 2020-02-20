package display;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JWindow;
import javax.swing.SwingWorker;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import math.D3CoordinateMatrix;
import world.Mesh;
import world.World;

public class OptionsWindow extends JFrame implements ChangeListener, ActionListener, FocusListener, MouseListener{

	private static final long serialVersionUID = 1L;

	private Window window;
	private ObjFileReader reader;
	
	
	private String rangeInfo = "(Poza zakresem)";
	private boolean couplingBool = true;
	private static boolean vertexesBool = true;
	private static boolean linesBool = true;
	private static boolean referencePointBool = false;
	private JPanel tempPanel = new JPanel();
	private Mesh mesh;
	
	private String inst = 	"Obs³uga kamery\n" +
							"W, S - przód, ty³\n" +
							"A, D - lewo, prawo\n" +	
							"Spacja, Ctrl - góra,dó³\n" +
							"Q, E - obrót na bok (lewo, prawo)\n" +
							"Z, X - obrót kamery (lewo, prawo)\n" +
							"R, F - obrót kamery (góra, dó³)";
	
	private JScrollPane pane;
	private JPanel mainPanel;
	private JPanel camOp;
	private JPanel objOp;
	private JPanel ldOp;
	private JPanel insOp;
	
	private JSlider camMovSpd;
	private JSlider camRotSpd;
	private JSlider camDisViw;
	private JSlider camFovX;
	private JSlider camFovY;
	private JSlider objOrtX;
	private JSlider objOrtY;
	private JSlider objOrtZ;
	private JSlider objScale;
	
	private JLabel camMovLbl = new JLabel("Prêdkoœæ przemiesczania: [" + World.SPEED + "]");
	private JLabel camRotLbl = new JLabel("Prêdkoœæ obrotów: [" + Math.toDegrees(World.ROT_SPEED) + "°]");
	private JLabel camDisLbl = new JLabel("Zasiêg rysowania: [" + Window.DISTANCE_VIEW + "]");
	private JLabel camFovXLbl = new JLabel("Fov X: [" + (int)(Window.FOV_X*10)/10. + "°]");
	private JLabel camFovYLbl = new JLabel("Fov Y: [" + (int)(Window.FOV_Y*10)/10. + "°]");
	private JLabel objPosXLbl = new JLabel("Wspó³rzêdna X: ");
	private JLabel objPosYLbl = new JLabel("Wspó³rzedna Y: ");
	private JLabel objPosZLbl = new JLabel("Wspó³rzêdna Z: ");
	private JLabel objOrtXLbl = new JLabel("Orientacja X: [" + "°]");
	private JLabel objOrtYLbl = new JLabel("Orientacja Y: [" + "°]");
	private JLabel objOrtZLbl = new JLabel("Orientacja Z: [" + "°]");
	private JLabel objScaleLbl = new JLabel("Skala obiektu: [" + "]");
	
	private JTextField objPosX;
	private JTextField objPosY;
	private JTextField objPosZ;
	
	private JButton objUpdtPos;
	private JButton loadFile;
	
	private JCheckBox referencePoint;
	private JCheckBox coupling;
	private JCheckBox vertexes;
	private JCheckBox lines;
	
	private JTextPane ins = new JTextPane();
	
	
	
	
	public OptionsWindow(Window window) {
		
		super("Opcje programu");
		
		this.window = window;
		reader = new ObjFileReader(window);
		mesh = window.panel.world.getMeshList().get(0);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//setLocationRelativeTo(window);
		addMouseListener(this);
		setSize(450, 600);
		
		int gapSize = 50; 
		
		
		//    Init panels
		mainPanel = new JPanel();
		
		
		camOp = new JPanel();
		objOp = new JPanel();
		ldOp = new JPanel();
		insOp = new JPanel();
		//
		
		//   Init components
		{
			
			int min = 0;
			int max = 1000;
			
			camMovSpd = new JSlider(min, max, (int)(World.SPEED*10));
			camMovSpd.addChangeListener(this); 
			Hashtable<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
			
			for(int i = min; i < max + 1 ; i += 200) {
				labels.put(new Integer(i), new JLabel((double)(i/10)+""));
			}
			camMovSpd.setLabelTable(labels);
			camMovSpd.setPaintLabels(true);
			camMovSpd.setMajorTickSpacing(200);
			camMovSpd.setMinorTickSpacing(40);
			camMovSpd.setPaintTicks(true);
			
		}
		
		{
			int min = 0;
			int max = 180;
			
			camRotSpd = new JSlider(min, max, (int)Math.toDegrees(World.ROT_SPEED));
			camRotSpd.addChangeListener(this);
			camRotSpd.setLabelTable(camRotSpd.createStandardLabels(30));
			camRotSpd.setMajorTickSpacing(30);
			camRotSpd.setMinorTickSpacing(6);
			camRotSpd.setPaintTicks(true);
			camRotSpd.setPaintLabels(true);
		}
		
		{
			
			int min = 100;
			int max = 10100;
			camDisViw = new JSlider(min, max, (int)(Window.DISTANCE_VIEW*10));
			camDisViw.addChangeListener(this);
			
			Hashtable<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
			
			for(int i = min; i < max + 1 ; i += 1000) {
				labels.put(new Integer(i), new JLabel(i/10+""));
			}
			camDisViw.setLabelTable(labels);
			camDisViw.setMajorTickSpacing(1000);
			camDisViw.setMinorTickSpacing(200);
			camDisViw.setPaintLabels(true);
			camDisViw.setPaintTicks(true);
			
		}
		
		{
			referencePoint = new JCheckBox("Zaznacz (0, 0, 0)", false);
			referencePoint.addChangeListener(this);
			referencePointBool = referencePoint.isSelected();
		}
		
		{
			int min = 60;
			int max = 90;
			
			camFovX = new JSlider(min, max, (int)(Window.FOV_X/1));
			camFovX.addChangeListener(this);
			camFovX.setLabelTable(camFovX.createStandardLabels(5, min));
			camFovX.setMajorTickSpacing(5);
			camFovX.setMinorTickSpacing(1);
			camFovX.setPaintTicks(true);
			camFovX.setPaintLabels(true);
		}
		
		{
			int min = 60;
			int max = 90;
			int temp = (int)(Window.FOV_Y/1);
			if(temp < min) {
				temp = min;
				camFovYLbl.setText("Fov Y: [" + (int)(Window.FOV_Y*10)/10. + "°]" + rangeInfo);
			}
			
			camFovY = new JSlider(min, max, temp);
			camFovY.addChangeListener(this);
			camFovY.setLabelTable(camFovX.createStandardLabels(5, min));
			camFovY.setMajorTickSpacing(5);
			camFovY.setMinorTickSpacing(1);
			camFovY.setPaintTicks(true);
			camFovY.setPaintLabels(true);
		}
		
		{
			coupling = new JCheckBox("Sprzê¿enie FovX z FovY", true);
			coupling.addChangeListener(this);
			couplingBool = coupling.isSelected();
		}
		
		{
			objPosX = new JTextField(mesh.getCords().x() + "", 5);
			objPosX.addFocusListener(this);
		}
		
		{
			objPosY = new JTextField(mesh.getCords().y() + "", 5);
			objPosY.addFocusListener(this);
		}
		
		{
			objPosZ = new JTextField(mesh.getCords().z() + "", 5);
			objPosZ.addFocusListener(this);
		}
		
		{
			objUpdtPos = new JButton("Aktualizuj");
			objUpdtPos.addActionListener(this);
		}
		
		{
			int min = -180;
			int max = 180;
			objOrtX = new JSlider(min, max, (int)(mesh.getOrientationCords().x()/1));
			objOrtX.addChangeListener(this);	
			objOrtX.setMajorTickSpacing(60);
			objOrtX.setMinorTickSpacing(12);
			objOrtX.setLabelTable(objOrtX.createStandardLabels(60));
			objOrtX.setPaintTicks(true);
			objOrtX.setPaintLabels(true);
			objOrtXLbl.setText("Orientacja X: [" + objOrtX.getValue() +"°]");
		}
		
		{
			int min = -180;
			int max= 180;
			objOrtY = new JSlider(min, max, (int)(mesh.getOrientationCords().y()/1));
			objOrtY.addChangeListener(this);
			objOrtY.setMajorTickSpacing(60);
			objOrtY.setMinorTickSpacing(12);
			objOrtY.setLabelTable(objOrtY.createStandardLabels(60));
			objOrtY.setPaintTicks(true);
			objOrtY.setPaintLabels(true);
			objOrtYLbl.setText("Orientacja Y: [" + objOrtY.getValue() +"°]");
		}
			
		{
			int min = -180;
			int max= 180;
			objOrtZ = new JSlider(min, max, (int)(mesh.getOrientationCords().z()/1));
			objOrtZ.addChangeListener(this);
			objOrtZ.setMajorTickSpacing(60);
			objOrtZ.setMinorTickSpacing(12);
			objOrtZ.setLabelTable(objOrtZ.createStandardLabels(60));
			objOrtZ.setPaintTicks(true);
			objOrtZ.setPaintLabels(true);
			objOrtZLbl.setText("Orientacja Z: [" + objOrtZ.getValue() +"°]");
		}
		
		{
			int min = 0;
			int max = 100;
			objScale = new JSlider(min, max, (int)(mesh.getScale()*10));
			objScale.addChangeListener(this);
			
			Hashtable<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
			for(int i = min ; i < max+1 ; i = i+10) {
				labels.put(i, new JLabel(i/10. + ""));
			}
			
			objScale.setLabelTable(labels);
			objScale.setMajorTickSpacing(10);
			objScale.setMinorTickSpacing(2);
			objScale.setPaintLabels(true);
			objScale.setPaintTicks(true);
			objScaleLbl = new JLabel("Skala obiektu: [" + mesh.getScale() + "]");
		}
		
		{
			vertexes = new JCheckBox("Rysuj wierzcho³ki", true);
			vertexes.addChangeListener(this);
			vertexesBool = vertexes.isSelected();
		}
		
		{
			lines = new JCheckBox("Rysuj krawêdzie", true);
			lines.addChangeListener(this);
			linesBool = lines.isSelected();
		}
		
		{
			loadFile = new JButton("Za³aduj plik");
			loadFile.addActionListener(this);
		}
		
		{
			
			StyledDocument doc = ins.getStyledDocument();
			StyleContext cs = new StyleContext();
			Style style = cs.getStyle(StyleContext.DEFAULT_STYLE);
			
			StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER);
			
			try {
				doc.insertString(0, inst, style);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ins.setStyledDocument(doc);
			ins.setEditable(false);
			ins.setAlignmentX(CENTER_ALIGNMENT);
			ins.setText(inst);
			ins.setBackground(getBackground());
			ins.setFont(new Font("Instr", Font.BOLD, 12));
		}
		//
		
		//   setting panels look
		
		camOp.setBorder(BorderFactory.createTitledBorder("Opcje kamery"));
		camOp.setLayout(new BoxLayout(camOp,BoxLayout.Y_AXIS));;
		
		objOp.setBorder(BorderFactory.createTitledBorder("W³aœciwoœci obiektu"));
		objOp.setLayout(new BoxLayout(objOp, BoxLayout.Y_AXIS));
		
		ldOp.setBorder(BorderFactory.createTitledBorder("Wczytywanie obiektu"));
		insOp.setBorder(BorderFactory.createTitledBorder("Instrukcja"));
		
		mainPanel.setLayout(new BoxLayout(mainPanel ,BoxLayout.Y_AXIS));
		//
		
		
		
		camOp.add(camMovLbl);
		camOp.add(camMovSpd);
		camOp.add(camRotLbl);
		camOp.add(camRotSpd);
		camOp.add(camDisLbl);
		camOp.add(camDisViw);
		camOp.add(referencePoint);
		camOp.add(Box.createRigidArea(new Dimension(0, gapSize)));
		camOp.add(camFovXLbl);
		camOp.add(camFovX);
		camOp.add(camFovYLbl);
		camOp.add(camFovY);
		camOp.add(coupling);
		
		
		
		
		tempPanel = new JPanel();
		tempPanel.add(vertexes);
		tempPanel.add(lines);
		objOp.add(tempPanel);
		
		tempPanel = new JPanel();
		tempPanel.add(objPosXLbl);
		tempPanel.add(objPosX);
		objOp.add(tempPanel);
		
		tempPanel = new JPanel();
		tempPanel.add(objPosYLbl);
		tempPanel.add(objPosY);
		objOp.add(tempPanel);
		
		tempPanel = new JPanel();
		tempPanel.add(objPosZLbl);
		tempPanel.add(objPosZ);
		objOp.add(tempPanel);
		
		objOp.add(objUpdtPos);
		
		objOp.add(Box.createRigidArea(new Dimension(0, gapSize)));
		objOp.add(objOrtXLbl);
		objOp.add(objOrtX);
		objOp.add(objOrtYLbl);
		objOp.add(objOrtY);
		objOp.add(objOrtZLbl);
		objOp.add(objOrtZ);
		objOp.add(objScaleLbl);
		objOp.add(objScale);
		
		ldOp.add(loadFile);
		
		insOp.add(ins);
		
		
		mainPanel.add(camOp);
		mainPanel.add(objOp);
		mainPanel.add(ldOp);
		mainPanel.add(insOp);
		
		
		pane = new JScrollPane(mainPanel);
		setContentPane(pane);
		setVisible(true);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		Object source = e.getSource();
		
		if(source == camMovSpd) {
			
			World.SPEED = camMovSpd.getValue()/10.;
			camMovLbl.setText("Prêdkoœæ przemiesczania: [" + World.SPEED+ "]");
		}
		
		if(source == camRotSpd) {
			
			int temp = camRotSpd.getValue();
			camRotLbl.setText("Prêdkoœæ obrotów: [" + temp +"°]");
			World.ROT_SPEED = Math.toRadians(temp);
		}
		
		if(source == camDisViw) {
			Window.DISTANCE_VIEW = camDisViw.getValue()/10.;
			camDisLbl.setText("Zasiêg rysowania: [" + Window.DISTANCE_VIEW + "]");
		}
		
		if(source == referencePoint) {
			referencePointBool = referencePoint.isSelected();
		}
		
		if(source == camFovX) {
			
			Window.FOV_X = camFovX.getValue()/1.;
			camFovXLbl.setText("Fov X: [" + (int)(Window.FOV_X*10)/10. + "°]");
			
			setUpCamFovYBarOnFovX(Window.FOV_X);
			
		}
		
		if(source == camFovY) {
			
			Window.FOV_Y = camFovY.getValue()/1.;
			camFovYLbl.setText("Fov Y: [" + (int)(Window.FOV_Y*10)/10. + "°]");
			
			setUpCamFovXBarOnFovY(Window.FOV_Y);
			
		}
		
		if(source == coupling) {
			couplingBool = coupling.isSelected();
		}
		
		if(source == vertexes) {
			vertexesBool = vertexes.isSelected();
		}
		
		if(source == lines) {
			linesBool = lines.isSelected();
		}
		
		if(source == objOrtX) {
			
			mesh.setOrientationX(objOrtX.getValue()/1.);
			objOrtXLbl.setText("Orientacja X: [" + objOrtX.getValue() +"°]");
			
		}
		
		if(source == objOrtY) {
			mesh.setOrientationY(objOrtY.getValue()/1.);
			objOrtYLbl.setText("Orientacja Y: [" + objOrtY.getValue() +"°]");
		}
		
		if(source == objOrtZ) {
			mesh.setOrientationZ(objOrtZ.getValue()/1.);
			objOrtZLbl.setText("Orientacja Z: [" + objOrtZ.getValue() +"°]");
		}
		
		if(source == objScale) {
			mesh.setScale(objScale.getValue()/10.);
			objScaleLbl.setText("Skala obiektu: [" + mesh.getScale() + "]");
		}
		
		
	}
	
	
	public void setUpCamFovYBarOnFovX(Double fovX) {
		
		if(couplingBool) {
			
			Window.FOV_Y = Math.toDegrees(Panel.calculateFovYRad(Math.toRadians(fovX), Window.ASPECT_RATIO));
			
			
			rangeInfo = "";
			if(Window.FOV_Y < camFovY.getMinimum()) {
				camFovY.removeChangeListener(this);
				camFovY.setValue(camFovY.getMinimum());
				camFovY.addChangeListener(this);
				rangeInfo = "(Poza zakresem)";
			}
			else if(Window.FOV_Y > camFovY.getMaximum()) {
				camFovY.removeChangeListener(this);
				camFovY.setValue(camFovY.getMaximum());
				camFovY.addChangeListener(this);
				rangeInfo = "(Poza zakresem)";
			}
			else {
				camFovY.removeChangeListener(this);
				camFovY.setValue((int)(Window.FOV_Y/1));
				camFovY.addChangeListener(this);
			}
			
			camFovYLbl.setText("Fov Y: [" + (int)(Window.FOV_Y*10)/10. + "°]" + rangeInfo);
			
		}
	}
	
	public void setUpCamFovXBarOnFovY(Double fovY) {
		
		if(couplingBool) {
			
			Window.FOV_X = Math.toDegrees(Panel.calculateFovXRad(Math.toRadians(fovY), Window.ASPECT_RATIO));
			
			
			rangeInfo = "";
			if(Window.FOV_X < camFovX.getMinimum()) {
				camFovX.removeChangeListener(this);
				camFovX.setValue(camFovX.getMinimum());
				camFovX.addChangeListener(this);
				rangeInfo = "(Poza zakresem)";
			}
			else if(Window.FOV_X > camFovX.getMaximum()) {
				camFovX.removeChangeListener(this);
				camFovX.setValue(camFovX.getMaximum());
				camFovX.addChangeListener(this);
				rangeInfo = "(Poza zakresem)";
			}
			else
			{
				camFovX.removeChangeListener(this);
				camFovX.setValue((int)(Window.FOV_X/1));
				camFovX.addChangeListener(this);
			}
			camFovXLbl.setText("Fov X: [" + (int)(Window.FOV_X*10)/10. + "°]" + rangeInfo);
			
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();
		
		if(source == objUpdtPos) {
			
			//
		
		}
		
		if(source == loadFile) {
			mesh = reader.createMeshAsync(reader.loadFile());
			if(mesh != null)
				window.panel.world.getMeshList().set(0, mesh);
			
		}
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		 Object source = e.getSource();
		 
		 if(source == objPosX) {
			 objPosX.setText("");
		 }
		 
		 if(source == objPosY) {
			 objPosY.setText("");
		 }
		
		 if(source == objPosZ) {
			 objPosZ.setText("");
		 }
	}

	@Override
	public void focusLost(FocusEvent e) {
		
		Object source = e.getSource();
		String temp;
		
		
		if(source == objPosX) {
			
			temp = objPosX.getText();
			
			if(temp.equals("")) {
				objPosX.setText(mesh.getCords().x() + "");
			} else {
				
				temp = temp.replaceAll(",", ".");
				try {
					mesh.setCords(new D3CoordinateMatrix(Double.parseDouble(temp), mesh.getCords().y(), mesh.getCords().z()));
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "Wartoœæ musi byæ liczb¹ rzeczywist¹");
					objPosX.setText(mesh.getCords().x() + "");
				}
			}
		}
		
		if(source == objPosY) {
			
			temp = objPosY.getText();
			
			if(temp.equals("")) {
				objPosY.setText(mesh.getCords().y() + "");
			} else {
				
				temp = temp.replaceAll(",", ".");
				try {
					mesh.setCords(new D3CoordinateMatrix(0., Double.parseDouble(temp), mesh.getCords().z()));
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "Wartoœæ musi byæ liczb¹ rzeczywist¹");
					objPosX.setText(mesh.getCords().y() + "");
				}
			}
		}
		
		if(source == objPosZ) {
			
			temp = objPosZ.getText();
			
			if(temp.equals("")) {
				objPosZ.setText(mesh.getCords().z() + "");
			} else {
				
				temp = temp.replaceAll(",", ".");
				try {
					mesh.setCords(new D3CoordinateMatrix(mesh.getCords().x(), mesh.getCords().y(), Double.parseDouble(temp)));
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "Wartoœæ musi byæ liczb¹ rzeczywist¹");
					objPosX.setText(mesh.getCords().z() + "");
				}
			}
			
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object source =  e.getSource();
		
		if(source == this) {
			this.requestFocus();
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static boolean getVertexDraw() {
		return vertexesBool;
	}
	
	public static boolean getLineDraw() {
		return linesBool;
	}
	
	public static boolean getReferencePointDraw() {
		return referencePointBool;
	}
}

class ObjFileReader {
	
	JFileChooser chooser;
	Component parent;
	
	public ObjFileReader(Component parent) {
		this.parent = parent;
		chooser = new JFileChooser(".");
		chooser.setFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				
				if(f.isDirectory())
					return true;
				else {
					String name = f.getName().toLowerCase();
					return name.endsWith(".obj");
					
				}
			}

			@Override
			public String getDescription() {
				return "3D object (*.obj)";
			}
			
		});
	}
	
	public File loadFile() {
		int i = chooser.showOpenDialog(parent);
		File file;
		
		if(i == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
			
			String name = file.getName();
			
			if(!name.endsWith(".obj")) {
				JOptionPane.showMessageDialog(parent, "Plik powinien mieæ rozszerzenie *.obj");
				return null;
			}
			return file;
		}
		return null;
	}
	
	public Mesh createMesh(File file) {
		
		if(file != null) {
			try (BufferedReader reader = new BufferedReader(new FileReader(file))){
				Mesh mesh = null;
				
				
				HashMap<Integer, D3CoordinateMatrix> cords = new HashMap<Integer, D3CoordinateMatrix>();
				String line;
				String[] values;
				Double x;
				Double y;
				Double z;
				int i = 1;
				while((line = reader.readLine())!= null) {
							
					System.out.println(line);
					//values = line.split("  ");
							
					if(line.startsWith("v ")) {
							
						line = line.substring(2);
							
						while(true) {
							
							if(line.startsWith(" ")) {
								line = line.substring(1);
							} else
								break;
											
						}
								
						values = line.split(" ");
								
						x = Double.parseDouble(values[0]);
						y = Double.parseDouble(values[1]);
						z = Double.parseDouble(values[2]);
								
						cords.put(i, new D3CoordinateMatrix(x, y, z));
						i++;
								
								
					}
							
							
							
				}
				Mesh m = new Mesh(cords);
				return m;
				
				
			} catch (IOException e) { }
			
		}
		
		return null;
		
	}
	
	
	public Mesh createMeshAsync(File file) {
		
		Mesh me;
		
		class Task extends SwingWorker<Mesh, Void> {
			
			Mesh mesh;
			
			@Override
			protected Mesh doInBackground() throws Exception {
				return createMesh(file);
			}
			
			@Override
			public void done() {
				
				
				
			}
		}
		
		Task task = new Task();
		task.execute();
		
		try {
			return task.get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
		
	}
	
}
