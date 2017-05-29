package br.com.johnatan.entities;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.Random;

import br.com.johnatan.sprites.Sprite2D;
import br.com.johnatan.sprites.utils.Vector;

public class Asteroid extends Sprite2D{

	private double raio;
	private int[] xpoints;
	private int[] ypoints;
	private Random r = new Random();
	
	public Asteroid(Vector position) {
		super(position);
		this.raio = 40;
		
		drawAsteroid();
		velocity = Vector.create(2, Math.toRadians(r.nextInt(360)));
		
	}
	
	public Asteroid (Vector position, double raio, Vector direction){
		super(new Vector(position.getX(), position.getY()));
		this.raio = raio;
		this.velocity = direction;
		drawAsteroid();
		
		
	}
	
	private void drawAsteroid() {
		xpoints = new int[(int)raio];
		ypoints = new int[(int)raio];
		int lenght = (int)(raio * 1.4);
		
		for(int i = 0; i < (int)this.raio; i++){
			double angle = (i + 2) * Math.PI/(this.raio/2);
			Vector aux = Vector.create(this.raio + r.nextInt(lenght), angle);
			xpoints[i] = (int) aux.getX();
			ypoints[i] = (int) aux.getY();
		}
		
		this.shape = new Polygon(xpoints, ypoints, (int)raio);
		
	}

	private void transform() {
		transform = new AffineTransform();
		transform.translate(position.getX(), position.getY());
	}
	
	public double getRaio(){
		return this.raio;
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
	public void update() {
		position.add(velocity);
	}
	
}
