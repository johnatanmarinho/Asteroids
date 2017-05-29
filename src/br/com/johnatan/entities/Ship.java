package br.com.johnatan.entities;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

import br.com.johnatan.sprites.Sprite2D;
import br.com.johnatan.sprites.utils.Vector;

public class Ship extends Sprite2D{

	private int health = 100;
	private int cartouche;
	private final int maxLaser = 10;
	
	public Ship(Vector position) {
		
		super(position);
		drawShip();
		this.cartouche = this.maxLaser;
		
	}

	private void drawShip() {
		
		int[] xpoints = {0, 50, 0};
		int[] ypoints = {0, 20, 40};	
		this.shape = new Polygon(xpoints, ypoints, xpoints.length);
		
	}

	private void transform() {
		transform = new AffineTransform();
		transform.translate(position.getX(), position.getY());
		transform.rotate(velocity.getAngle(), getBounds().getCenterX(), getBounds().getCenterY());
	}
	
	public void hit(double raio){
		this.health -= raio;
	}
	
	public boolean isAlive(){
		if(health > 0) return true;
		return false;
	}
	
	public void boost(){
		velocity.add(Vector.create(2, velocity.getAngle()));
	}
	
	public Laser shot(){
		cartouche--;
		return new Laser(
				new Vector(getBounds().getCenterX() + position.getX() , 
						getBounds().getCenterY() + position.getY()),
				Vector.create(15, velocity.getAngle()));
	}

	public void recharge() {
		if(cartouche < 10) cartouche += 1;
	}
	
	public int getCartouche(){
		return cartouche;
	}
	
	public boolean hasLaser() {
		if(cartouche > 0) return true;
		return false;
	}
	
	public void rotate(double angle){
		velocity.rotate(angle);
	}
		
	@Override
	public void draw(Graphics2D g) {

		transform();
		g.setColor(Color.WHITE);
		g.draw(getTransformedShape());
		
	}

	@Override
	public boolean onCollide(Sprite2D other) {
			
		Area areaA = new Area(convert(this));
		Area areaB = new Area(convert(other));
			
		areaA.intersect(areaB);
		return !areaA.isEmpty();
		
	}
	
	@Override
	public void update() {
		position.add(velocity.scalar(0.9));
	}

	public int getHealth() {
		// TODO Auto-generated method stub
		return health;
	}
	
}
