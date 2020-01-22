package com.uwaterloo.cs349.gradle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Line extends Shape{
	
public Line(Color c, int s, int x, int y, int w, int h ){
		
		// width is x2 and height is y2 for line
		super(c, s, x, y, w, h );
		
	}
	
	
	public void draw(Graphics2D g2){
		
		if(isSelected){
			g2.setColor(selectColor);
			g2.setStroke(new BasicStroke(stroke+5));
			g2.drawLine(xpos+1, ypos+1, width, height);
		}
		g2.setColor(color);
		g2.setStroke(new BasicStroke(stroke));
		g2.drawLine(xpos, ypos, xpos+width, ypos+height);
		
	}
	
	public boolean amITheShape(int ex, int ey){
		return (ex >= xpos && ex <= (xpos+width) && ey >= ypos && ey <= (ypos+height));
	}

}
