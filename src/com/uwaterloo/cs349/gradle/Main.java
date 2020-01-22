package com.uwaterloo.cs349.gradle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;



public class Main extends JPanel {
	
	private Shape currentShape;
	private ArrayList<Shape> shapes;
	
	private Color currentColor = Color.BLACK;
	private int currentThickness = 1;
	private mode currentMode = mode.CIRCLE;
	private colorMode currentColorMode = colorMode.C1;
	
	private Shape selectedShape;
	private int lastx;
	private int lasty;
	
	private int clickX; 
	private int clickY; 
	
	
	private Color c1 = Color.black;
	private Color c2 = Color.red;
	private Color c3 = Color.blue;
	private Color c4 = Color.green;
	private Color c5 = Color.yellow;
	private Color c6 = Color.orange;
	
	
	enum mode{
		CIRCLE, LINE, RECTANGLE, FILL, ERASE, SELECT
	};
	
	enum colorMode{
		C1, C2, C3, C4, C5, C6
	}
	
	
	
	
	public static void main(String[] args) {
		Main m = new Main();
		m.run();
	}
	
	
	
	
	
	public Main(){
		shapes = new ArrayList<>();
	}
	
	
	private void unSelectAll(){
		for (Shape s : shapes) {
			
			s.unSelectMe();
		}
		selectedShape = null;
	}
	
	
	public void run(){
		JFrame frame = new JFrame("Jsketch (20617522)");
		JMenuBar menuBar = new JMenuBar();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1600, 1200);
		frame.setResizable(false);
		
		
		
		frame.setJMenuBar(menuBar);
		
		myPanel paintPanel = new myPanel();
		paintPanel.setPreferredSize(new Dimension(1500, 1200));
		
		
		JMenu file = new JMenu("File");
		menuBar.add(file);
		JMenuItem item1 = new JMenuItem("New");
		item1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shapes.clear();
				currentShape = null;
				paintPanel.repaint();
			}
		});
		
		
		file.add(item1);
		JMenuItem item2 = new JMenuItem("Load");
		file.add(item2);
		JMenuItem item3 = new JMenuItem("Save");
		file.add(item3);
		
		
		//Border for when clicked on
		Border blackLine = BorderFactory.createLineBorder(Color.pink, 3);
		Border emptyBorder = BorderFactory.createEmptyBorder();
		
		
		
		
		//Tool buttons
		JButton selectTool = new JButton(new ImageIcon(((new ImageIcon("src/resources/selection.jpg")).getImage()).getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)));
		JButton lineTool = new JButton(new ImageIcon(((new ImageIcon("src/resources/line.png")).getImage()).getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)));
		JButton circleTool = new JButton(new ImageIcon(((new ImageIcon("src/resources/circle.png")).getImage()).getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)));
		JButton rectangleTool = new JButton(new ImageIcon(((new ImageIcon("src/resources/rectangle.png")).getImage()).getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)));
		JButton fillTool = new JButton(new ImageIcon(((new ImageIcon("src/resources/fill.jpg")).getImage()).getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)));
		JButton eraseTool = new JButton(new ImageIcon(((new ImageIcon("src/resources/erase.png")).getImage()).getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)));
		
		//Color buttons
		JButton color1 = new JButton();
		JButton color2 = new JButton();
		JButton color3 = new JButton();
		JButton color4 = new JButton();
		JButton color5 = new JButton();
		JButton color6 = new JButton();
		
		//Thickness buttons
		JButton thick1 = new JButton();
		JButton thick2 = new JButton();
		JButton thick3 = new JButton();
		
		
		
		//Tool for selecting a shape
		selectTool.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					currentMode = mode.SELECT;
					selectTool.setBorder(blackLine);
					lineTool.setBorder(emptyBorder);
					circleTool.setBorder(emptyBorder);
					rectangleTool.setBorder(emptyBorder);
					fillTool.setBorder(emptyBorder);
					eraseTool.setBorder(emptyBorder);
			}
		});
		selectTool.setBackground(Color.WHITE);
		selectTool.setPreferredSize(new Dimension(30, 30));	
		
		
		//Tool for creating a line
		
		lineTool.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentMode = mode.LINE;
				selectTool.setBorder(emptyBorder);
				lineTool.setBorder(blackLine);
				circleTool.setBorder(emptyBorder);
				rectangleTool.setBorder(emptyBorder);
				fillTool.setBorder(emptyBorder);
				eraseTool.setBorder(emptyBorder);
			}
		});
		lineTool.setBackground(Color.WHITE);
		lineTool.setPreferredSize(new Dimension(30, 30));
		
		
		
		//Tool for creating a circle
		circleTool.setBorder(blackLine);
		circleTool.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					currentMode = mode.CIRCLE;
					selectTool.setBorder(emptyBorder);
					lineTool.setBorder(emptyBorder);
					circleTool.setBorder(blackLine);
					rectangleTool.setBorder(emptyBorder);
					fillTool.setBorder(emptyBorder);
					eraseTool.setBorder(emptyBorder);
			}
		});
		circleTool.setBackground(Color.WHITE);
		circleTool.setPreferredSize(new Dimension(30, 30));
	
		
		
		//Tool for creating a rectangle
		
		rectangleTool.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentMode = mode.RECTANGLE;
				selectTool.setBorder(emptyBorder);
				lineTool.setBorder(emptyBorder);
				circleTool.setBorder(emptyBorder);
				rectangleTool.setBorder(blackLine);
				fillTool.setBorder(emptyBorder);
				eraseTool.setBorder(emptyBorder);
			}
		});
		rectangleTool.setBackground(Color.WHITE);
		rectangleTool.setPreferredSize(new Dimension(30, 30));
		
		
		
		
		//Tool to fill the color of a shape using current color
		
		fillTool.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					currentMode = mode.FILL;
					selectTool.setBorder(emptyBorder);
					lineTool.setBorder(emptyBorder);
					circleTool.setBorder(emptyBorder);
					rectangleTool.setBorder(emptyBorder);
					fillTool.setBorder(blackLine);
					eraseTool.setBorder(emptyBorder);
			}
		});
		fillTool.setBackground(Color.WHITE);
		fillTool.setPreferredSize(new Dimension(30, 30));
		
		
		//to to erase the shape you click on
		
		eraseTool.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					currentMode = mode.ERASE;
					selectTool.setBorder(emptyBorder);
					lineTool.setBorder(emptyBorder);
					circleTool.setBorder(emptyBorder);
					rectangleTool.setBorder(emptyBorder);
					fillTool.setBorder(emptyBorder);
					eraseTool.setBorder(blackLine);
			}
		});
		eraseTool.setBackground(Color.WHITE);
		eraseTool.setPreferredSize(new Dimension(30, 30));
		
		
		
		//Color tools
		
		color1.setBackground(Color.black);
		color1.setBorder(blackLine);
		color1.setPreferredSize(new Dimension(30,30));
		color1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentColor = c1;
				currentColorMode = colorMode.C1;
				color5.setBorder(emptyBorder);
				color2.setBorder(emptyBorder);
				color3.setBorder(emptyBorder);
				color4.setBorder(emptyBorder);
				color1.setBorder(blackLine);
				color6.setBorder(emptyBorder);
				
			}
		});
		
		
		
		color2.setBackground(Color.red);
		color2.setPreferredSize(new Dimension(30,30));
		color2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentColor = c2;
				currentColorMode = colorMode.C2;
				color1.setBorder(emptyBorder);
				color5.setBorder(emptyBorder);
				color3.setBorder(emptyBorder);
				color4.setBorder(emptyBorder);
				color2.setBorder(blackLine);
				color6.setBorder(emptyBorder);
			}
		});
		
		
		
		color3.setBackground(Color.blue);
		color3.setPreferredSize(new Dimension(30,30));
		color3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentColor = c3;
				currentColorMode = colorMode.C3;
				color1.setBorder(emptyBorder);
				color2.setBorder(emptyBorder);
				color5.setBorder(emptyBorder);
				color4.setBorder(emptyBorder);
				color3.setBorder(blackLine);
				color6.setBorder(emptyBorder);
			}
		});
		
		
		
		color4.setBackground(Color.green);
		color4.setPreferredSize(new Dimension(30,30));
		color4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentColor = c4;
				currentColorMode = colorMode.C4;
				color1.setBorder(emptyBorder);
				color2.setBorder(emptyBorder);
				color3.setBorder(emptyBorder);
				color5.setBorder(emptyBorder);
				color4.setBorder(blackLine);
				color6.setBorder(emptyBorder);
			}
		});
		
		
		color5.setBackground(Color.yellow);
		color5.setPreferredSize(new Dimension(30,30));
		color5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentColor = c5;
				currentColorMode = colorMode.C5;
				color1.setBorder(emptyBorder);
				color2.setBorder(emptyBorder);
				color3.setBorder(emptyBorder);
				color4.setBorder(emptyBorder);
				color5.setBorder(blackLine);
				color6.setBorder(emptyBorder);
			}
		});
		
		
		
		color6.setBackground(Color.orange);
		color6.setPreferredSize(new Dimension(30,30));
		color6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentColor = c6;
				currentColorMode = colorMode.C6;
				color1.setBorder(emptyBorder);
				color2.setBorder(emptyBorder);
				color3.setBorder(emptyBorder);
				color4.setBorder(emptyBorder);
				color5.setBorder(emptyBorder);
				color6.setBorder(blackLine);
			}
		});
		
		//Button For allowing users to choose more colors than displayed in toolPanel and the previously selected color in toolPanel gets replaced by the new Color
		JButton colorChooser = new JButton();
		colorChooser.setBackground(Color.white);
		colorChooser.setPreferredSize(new Dimension(78,30));
		JLabel chooseLabel = new JLabel("Choose");
		colorChooser.add(chooseLabel);
		colorChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Color newColor = JColorChooser.showDialog(null, "Choose Color", currentColor);
				paintPanel.validate();
				paintPanel.repaint();
				if(newColor != null){
					currentColor = newColor;
				
					if (currentColorMode == colorMode.C1){
						color1.setBackground(currentColor);
						c1 = currentColor;
					} else if(currentColorMode == colorMode.C2){
						color2.setBackground(currentColor);
						c2 = currentColor;
					} else if(currentColorMode == colorMode.C3){
						color3.setBackground(currentColor);
						c3 = currentColor;
					} else if(currentColorMode == colorMode.C4){
						color4.setBackground(currentColor);
						c4 = currentColor;
					} else if(currentColorMode == colorMode.C5){
						color5.setBackground(currentColor);
						c5 = currentColor;
					} else {
						color6.setBackground(currentColor);
						c6 = currentColor;
					}
					
				}
			}
		});
		
		
		
		//Thickness of borders
		thick1.setBackground(Color.red);
		thick1.setPreferredSize(new Dimension(78,4));
		thick1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentThickness = 1;
				thick1.setBackground(Color.red);
				thick2.setBackground(Color.black);
				thick3.setBackground(Color.black);
				
			}
		});
		
		
		thick2.setBackground(Color.black);
		thick2.setPreferredSize(new Dimension(78,6));
		thick2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentThickness = 5;
				thick2.setBackground(Color.red);
				thick1.setBackground(Color.black);
				thick3.setBackground(Color.black);
				
			}
		});
		
		
		thick3.setBackground(Color.black);
		thick3.setPreferredSize(new Dimension(78,8));
		thick3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentThickness = 15;
				thick3.setBackground(Color.red);
				thick1.setBackground(Color.black);
				thick2.setBackground(Color.black);
				
			}
		});
		
		
		
		
		
		
		
		
		JPanel toolPanel = new JPanel();
		toolPanel.setPreferredSize(new Dimension(80, 1200));
		toolPanel.setLocation(0, 0);
		
		toolPanel.add(eraseTool);
		toolPanel.add(selectTool);
		toolPanel.add(rectangleTool);
		toolPanel.add(lineTool);
		toolPanel.add(circleTool);
		toolPanel.add(fillTool);
		toolPanel.add(color1);
		toolPanel.add(color2);
		toolPanel.add(color3);
		toolPanel.add(color4);
		toolPanel.add(color5);
		toolPanel.add(color6);
		toolPanel.add(colorChooser);
		toolPanel.add(thick1);
		toolPanel.add(thick2);
		toolPanel.add(thick3);
		
		
		toolPanel.setBackground(Color.GRAY);
		paintPanel.setBackground(Color.WHITE);
		
		
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(toolPanel);
		this.add(paintPanel);
		frame.add(this);
		frame.setVisible(true);
		
		
		
		//Function taken from one of the example code with some changes
		paintPanel.addMouseListener(new MouseAdapter() {
			

		    @Override
		    public void mouseReleased(MouseEvent e) {
		    	if(currentMode == mode.CIRCLE || currentMode == mode.RECTANGLE || currentMode == mode.LINE){
		    		shapes.add(currentShape);
		    	}
		    	currentShape = null;
		    	
		    }

		    @Override
		    public void mousePressed(MouseEvent e) {
		    	
		    	unSelectAll();
		    	
		    	
		    	if(currentMode == mode.CIRCLE){
		    		currentShape = new Circle(currentColor, currentThickness, e.getX(), e.getY(), 0, 0);
		    	}
		    	else if(currentMode == mode.RECTANGLE){
		    		currentShape = new Rectangle(currentColor, currentThickness, e.getX(), e.getY(), 0, 0);
		    	}
		    	else if(currentMode == mode.LINE){
		    		currentShape = new Line(currentColor, currentThickness, e.getX(), e.getY(), e.getX(), e.getY());
		    		
		    	} else if(currentMode == mode.ERASE){
		    		
		    		Shape tempShape = null;
		    		
		    		for (Shape s : shapes) {
						boolean hit = s.amITheShape(e.getX(), e.getY());
						if(hit){
							tempShape = s;
							break;
						}
					}
		    		
		    		shapes.remove(tempShape);
		    		
		    		repaint();
		    		
		    	} else if(currentMode == mode.FILL){
		    		
		    		
		    		
		    		for (Shape s : shapes) {
						boolean hit = s.amITheShape(e.getX(), e.getY());
						if(hit){
							s.fillMe(true);
							s.setFillColor(currentColor);
							repaint();
							break;
						}
					}
		    		
		    	} else if(currentMode == mode.SELECT){
		    		
		    		
		    		for (Shape s : shapes) {
						boolean hit = s.amITheShape(e.getX(), e.getY());
						if(hit){
							selectedShape = s;
							selectedShape.selectMe();
							break;
						}
					}
		    		if(selectedShape == null)
		    			return;
		    		//Change currently selected color button to display color of selected shape;
		    		if (currentColorMode == colorMode.C1){
						color1.setBackground(selectedShape.getColor());
						c1 = selectedShape.getColor();
					} else if(currentColorMode == colorMode.C2){
						color2.setBackground(selectedShape.getColor());
						c2 = selectedShape.getColor();
					} else if(currentColorMode == colorMode.C3){
						color3.setBackground(selectedShape.getColor());
						c3 = selectedShape.getColor();
					} else if(currentColorMode == colorMode.C4){
						color4.setBackground(selectedShape.getColor());
						c4 = selectedShape.getColor();
					} else if(currentColorMode == colorMode.C5){
						color5.setBackground(selectedShape.getColor());
						c5 = selectedShape.getColor();
					} else {
						color6.setBackground(selectedShape.getColor());
						c6 = selectedShape.getColor();
					}
		    		
		    		
		    		currentColor = selectedShape.getColor();
		    		//Change thickness button to display thickness of currently select 
		    		if(selectedShape.getThickness() == 1){
		    			thick1.setBackground(Color.red);
		    			thick2.setBackground(Color.black);
		    			thick3.setBackground(Color.black);
		    		} else if(selectedShape.getThickness() == 5){
		    			thick1.setBackground(Color.black);
		    			thick2.setBackground(Color.red);
		    			thick3.setBackground(Color.black);
		    		} else {
		    			thick3.setBackground(Color.red);
		    			thick2.setBackground(Color.black);
		    			thick1.setBackground(Color.black);
		    		}
		    	
		    		
		    		
		    	}
		    	
		    	lastx = e.getX();
		    	lasty = e.getY();	
		    	clickX = e.getX();
		    	clickY = e.getY();
		    	
		    	repaint();
		    
		    }
			
			
			
		});
		
		paintPanel.addMouseMotionListener(new MouseMotionAdapter() {
			//Takes care of creating the preview when dragging and covers dragging into all directions
			@Override
			public void mouseDragged(MouseEvent e) {
				
				int mousex = e.getX();
				int mousey = e.getY();
				
				
				if(currentMode == mode.CIRCLE || currentMode == mode.RECTANGLE || currentMode == mode.LINE){
					if(mousex < clickX  && mousey  < clickY){
						
					
						currentShape.setX(mousex);
						currentShape.setY(mousey);
						
						currentShape.setWidth(clickX - mousex);
						currentShape.setHeight(clickY - mousey);
					}
					
					
					else if(mousey < clickY){
						currentShape.setY(mousey);
						
						currentShape.setWidth(mousex -  clickX);
						currentShape.setHeight(clickY - mousey);
						
					}
					
					else if(mousex < clickX){
						currentShape.setX(mousex);
						
						currentShape.setWidth(clickX - mousex);
						currentShape.setHeight(mousey - clickY);
						
					}
					
					else{
					
						currentShape.setWidth(e.getX() - currentShape.getX());
						currentShape.setHeight(e.getY() - currentShape.getY());
						
					}
				} else if(currentMode == mode.SELECT && selectedShape != null){
					
					selectedShape.setX(selectedShape.getX() + (e.getX() - lastx));
					selectedShape.setY(selectedShape.getY() + (e.getY() - lasty));
					
					lastx = e.getX();
					lasty = e.getY();
				}
				
				
				
				paintPanel.repaint();
	    	}
		});
	}
	
	
	
	
	//Method taken from one of the example code with some changes
	
	public class myPanel extends JPanel {
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g; 
			
			for (Shape s : shapes) {
				s.draw(g2);
			}
			
			//If new is clicked then don't draw current shape that is not in the array yet
	
			if (currentShape != null)
				currentShape.draw(g2);
        	
		}
	}
	
	
	public void paintComponent(Graphics g){
		
	}
	
}


