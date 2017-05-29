package br.com.johnatan.board;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import br.com.johnatan.sprites.Sprite2D;
import br.com.johnatan.sprites.utils.Vector;

public class ScoreBoard extends Sprite2D{

	private double score = 0;
	private int lasers = 0;
	private int health = 0;
	private int width;
	
	public ScoreBoard(int width) {
		super(new Vector());
		this.width = width;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics2D g) {
		
		g.setFont(new Font("verdana", Font.BOLD, 20 ));
		g.setColor(Color.GRAY);
		
		String score = "Score: " + this.score;
		g.drawString(score, width/2 - g.getFontMetrics().stringWidth(score) / 2, 20);
		
		String life = "Life: " + health;
		g.drawString(life, g.getFontMetrics().stringWidth(life) , 20);
		
		String laser = "Lasers: " + lasers;
		g.drawString(laser, width - 10 - g.getFontMetrics().stringWidth(laser), 20);
		
	}

	@Override
	public void update() {
		
	}

	public void giveScore(double score){
		this.score += score;
	}
	
	public double getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLasers() {
		return lasers;
	}

	public void setLasers(int lasers) {
		this.lasers = lasers;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	

}
