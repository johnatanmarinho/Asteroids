package br.com.johnatan.entities;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

import br.com.johnatan.sprites.Sprite2D;
import br.com.johnatan.sprites.utils.Vector;

public class Laser extends Sprite2D {

	public Laser(Vector position, Vector direction) {
		super(position);
		this.velocity = direction;
		this.setBounceable(false);
		shape = new Line2D.Double(0, 0, 30, 0);
		
	}

	private void transform() {
		transform = new AffineTransform();
		transform.translate(position.getX(), position.getY());
		transform.rotate(velocity.getAngle(), getBounds().getCenterX(), getBounds().getCenterY());
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
