package br.com.johnatan.sprites.utils;

import static java.lang.Math.*;

import java.awt.geom.Point2D;

public class Vector {
	private double x;
	private double y;
	
	public Vector (){
		x = y = 0;
	}
	
	public Vector (double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public static Vector create(double magnitude, double direction){
		return new Vector(cos(direction) * magnitude, 
							sin(direction) * magnitude);
	} 
	
	public Vector add(Vector vector){
		this.x += vector.getX();
		this.y += vector.getY();
		return this;
	}
	
	public Vector scalar(double scale){
		this.x *= scale;
		this.y *= scale;
		return this;
	}
	
	public Vector normalize(){
		this.x /= getSize();
		this.y /= getSize();
		return this;
	}
	
	public Vector rotate (double angle){
		double dx = x * cos(angle) - y * sin(angle);
		double dy = x * sin(angle) + y * cos(angle);
		
		this.x = dx;
		this.y = dy;
		
		return this;
	}
	
	public double getAngle(){
		return atan2(y, x);
	}

	public double getSize(){
		return hypot(x, y);
	}
		
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}

	public Point2D toPoint() {
		return new Point2D.Double(x, y);
	}
	
	

}
