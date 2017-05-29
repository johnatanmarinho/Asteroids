package br.com.johnatan.gamescomponents;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JPanel;

import br.com.johnatan.board.ScoreBoard;
import br.com.johnatan.sprites.Sprite2D;

@SuppressWarnings("serial")
public class Scene extends JPanel{

	private BlockingQueue<Sprite2D> objetos;
	private Color background;
	private ScoreBoard scoreBoard;
	
	public Scene(){
		objetos = new LinkedBlockingQueue<>();
		this.background = Color.BLACK;
		
	}

	public void addSprite(Sprite2D... objetos){
		try {
			for (Sprite2D objeto : objetos) {
				this.objetos.put(objeto);
			}
		} catch (InterruptedException e) {
			System.out.println("aaa");
			e.printStackTrace();
		}
	}	

	public BlockingQueue<Sprite2D> getObjetcs(){
		return objetos;
	}
	
	public void setBackgroundColor(Color c){
		this.background = c;
	}

	private boolean clean(Sprite2D objeto) {
		if(objeto.getPosition().getX() < - getWidth() ||
				objeto.getPosition().getX() > getWidth() * 2 ||
				objeto.getPosition().getY() < - getHeight() ||
				objeto.getPosition().getY() > getHeight() * 2 ||
				!isVisible()){
			objeto.setVisible(false);
			return true;
			//i.remove();
		}
		return false;
	}
	
	private void verifyBounds(Sprite2D objeto) {
		double x = objeto.getPosition().getX();
		double y = objeto.getPosition().getY();
		double width2 = objeto.getBounds().getWidth();		
		double height2 = objeto.getBounds().getHeight();

		if(x < -width2){
			objeto.getPosition().setX(getWidth());
		}else if (x > getWidth()){
			objeto.getPosition().setX(-width2);
		}

		if(y < -height2){
			objeto.getPosition().setY(getHeight());
		}else if (y > getHeight()){
			objeto.getPosition().setY(-height2);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		g.setColor(background);
		g.fillRect(0, 0, getWidth(), getHeight());


		Iterator<Sprite2D> sprites = objetos.iterator();
		while(sprites.hasNext()){
			Sprite2D objeto = sprites.next();
			
			if(objeto.isVisible()){
				
				if(objeto.isBounceable())
					verifyBounds(objeto);		

				if(clean(objeto)) 
					continue;					

				objeto.update();
				objeto.draw((Graphics2D) g );
				
			}else{
				sprites.remove();
			}
		}
		
		if(scoreBoard != null)
			scoreBoard.draw((Graphics2D) g);
	}

	public void addScoreBoard(ScoreBoard scoreBoard) {
		this.scoreBoard = scoreBoard;
	}

	

}
