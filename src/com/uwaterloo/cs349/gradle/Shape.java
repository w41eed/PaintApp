package com.uwaterloo.cs349.gradle;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Shape {

	protected int xpos;
	protected int ypos;
	protected int width;
	protected int height;
	protected int stroke;
	protected Color color;
	protected Color fillColor;
	protected boolean fillShape;
	protected boolean isSelected;
	protected Color selectColor;
	
	public Shape(Color c, int s, int x, int y, int w, int h ){
		xpos = x;
		ypos = y;
		width = w;
		height = h;
		stroke = s;
		color = c;
		isSelected = false;
		
		selectColor = Color.magenta;
		
	}
	
	
	
	public abstract void draw(Graphics2D g2);
	public abstract boolean amITheShape(int ex, int ey);
	
	
	public int getX() {
		return xpos;
	}
	
	public int getY() {
		return ypos;
	}
	
	public void setX(int x){
		xpos = x;
	}
	
	public void setY(int y){
		ypos = y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setWidth(int w) {
		width = w;
	}
	
	public void setHeight(int h) {
		height = h;
	}
	
	public boolean amISelected(){
		return isSelected;
	}
	
	public void selectMe(){
		isSelected = true;
	}
	
	public void unSelectMe(){
		isSelected = false;
	}
	
	public void setFillColor(Color c){
		fillColor = c;
	}
	
	public void fillMe(boolean b){
		fillShape = b;
	}
	
	public Color getColor(){
		return color;
	}
	
	public int getThickness(){
		return stroke;
	}
	
}
