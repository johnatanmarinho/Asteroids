package br.com.johnatan.sprites;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import br.com.johnatan.sprites.utils.Vector;

public abstract class Sprite2D {

	protected Vector position;
	protected Vector velocity;
	protected Shape shape;
	protected AffineTransform transform;
	private boolean visible;
	private boolean bounceable;
	private boolean obstacle;
	
	public boolean isObstacle() {
		return obstacle;
	}


	public void setObstacle(boolean obstacle) {
		this.obstacle = obstacle;
	}


	public Sprite2D ( Vector position){
		this.position = position;
		this.velocity = new Vector();
		this.transform = new AffineTransform();
		this.visible = true;
		this.bounceable = true;
		this.obstacle = true;
	}

	
	public abstract void draw(Graphics2D g);
	public abstract void update();
	
	
	public Rectangle getBounds(){
		return shape.getBounds();
	}

	public Vector getPosition() {
		return position;
	}

	public void setPosition(Vector position) {
		this.position = position;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible){
		this.visible = visible;
	} 
	
	public Shape getTransformedShape(){
		return transform.createTransformedShape(shape);
	}
	
	public boolean onCollide(Sprite2D other){
		Shape a = convert(this);
		Shape b = convert(other);
		
		if(a.intersects(b.getBounds()) &&
				b.intersects(a.getBounds())){
			return true;
		}
		
		return false;
	}
	
	protected Shape convert(Sprite2D other) {
		AffineTransform temp = new AffineTransform();
		temp.translate(other.position.getX(), other.position.getY());
		temp.rotate(other.velocity.getAngle(), other.getBounds().getCenterX(), other.getBounds().getCenterY());
		return temp.createTransformedShape(other.shape);
	}

	public void setBounceable(boolean bounceable){
		this.bounceable = bounceable;
	}

	public boolean isBounceable() {
		return bounceable;
	}


	public void rotate(double d) {
		this.velocity.rotate(d);
	}
	
}
