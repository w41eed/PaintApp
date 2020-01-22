package com.uwaterloo.cs349.gradle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Rectangle extends Shape {

public Rectangle(Color c, int s, int x, int y, int w, int h ){
		
		super(c, s, x, y, w, h );
		
	}
	
	
	public void draw(Graphics2D g2){
		g2.setColor(color);
		g2.setStroke(new BasicStroke(stroke));
		g2.setColor(fillColor);
		if(fillShape){
			g2.fillRect(xpos, ypos, width, height);
		}
		g2.setColor(color);
		g2.drawRect(xpos, ypos, width, height);
		if(isSelected){
			g2.setColor(selectColor);
			g2.drawRect(xpos, ypos, width, height);
		}
		
	}
	
	public boolean amITheShape(int ex, int ey){
		
		return (ex >= xpos && ex <= (xpos+width) && ey >= ypos && ey <= (ypos+height));
	}
	
}
