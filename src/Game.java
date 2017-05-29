import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import br.com.johnatan.board.ScoreBoard;
import br.com.johnatan.entities.Asteroid;
import br.com.johnatan.entities.Laser;
import br.com.johnatan.entities.Ship;
import br.com.johnatan.gamescomponents.Scene;
import br.com.johnatan.sprites.Sprite2D;
import br.com.johnatan.sprites.utils.Vector;

public class Game {
	private static final Random RANDOM = new Random();

	public static void main(String[] args) {
		new Game().init();
	}

	private JFrame janela;
	private Scene cena;
	private Ship player;
	private boolean running = false;;
	private boolean left;
	private boolean right;
	private boolean moving;
	private long time;
	private long startTime;
	private ScoreBoard scoreBoard;
	private JPanel menu;
	private JButton btnStart;


	private void init() {
		preparaJanela();
		preparaCena();
		preparaMenu();
		preparaControles();
		iniciaGameLoop();
		mostraJanela();
	}

	private void preparaMenu() {
		menu = new JPanel();
		menu.setLayout(new GridLayout(1, 1));
		btnStart = new JButton("START");
		btnStart.setFocusable(false);
		btnStart.addActionListener(click -> start());
		menu.add(btnStart);
		janela.add(menu, BorderLayout.SOUTH);
	}

	private void start() {
		preparaObjetos();
		time = System.currentTimeMillis();
		if(!running){				
			btnStart.setText("RESTART");
			running = true;
		}else{
			btnStart.setText("START");
		}		
	}

	private void preparaObjetos() {	
		cena.getObjetcs().clear();
		scoreBoard = new ScoreBoard(cena.getWidth());
		cena.addScoreBoard(scoreBoard);

		moving = false;
		left = false;
		right = false;

		player = new Ship(new Vector(
				cena.getWidth()/2, 
				cena.getHeight()/2));
		cena.addSprite(player);

	}

	private void preparaJanela() {
		janela = new JFrame("Game");
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setLayout(new BorderLayout());
	}

	private void preparaCena() {
		cena = new Scene();
		janela.add(cena, BorderLayout.CENTER);
	}

	private void preparaControles() {
		cena.setFocusable(true);
		cena.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e){}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_A:
					left = false;
					break;

				case KeyEvent.VK_D:
					right = false;
					break;

				case KeyEvent.VK_W:
					moving = false;
					break;

				default:
					break;
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_A:
					left = true;
					break;

				case KeyEvent.VK_D:
					right = true;
					break;

				case KeyEvent.VK_W:
					moving = true;
					break;

				case KeyEvent.VK_SPACE:
					if(player.hasLaser()){
						Laser laser = player.shot();
						cena.addSprite(laser);
					}
					break;
				default:
					break;
				}

			}
		});
	}

	private void iniciaGameLoop() {
		Thread t = new Thread(() -> gameLoop());
		t.start();
	}

	private void gameLoop() {
		while(true){
			while(running){
				menu.setVisible(false);
				time = System.currentTimeMillis() - startTime;
								
				if(time % 30 == 0 ) {
					player.recharge();
					scoreBoard.setLasers(player.getCartouche());
				}				
				
				if(time % 60 == 0){
					createAsteroid();
				}

				doCollision();
				controllers();
				updateScoreBoard();
				cena.repaint();
				
				try {
					Thread.sleep(1000/30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
			if(!menu.isVisible()){
				menu.setVisible(true);
			}
			
			try {
				Thread.sleep(1000/30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void updateScoreBoard() {
		scoreBoard.setHealth(player.getHealth());
		scoreBoard.setLasers(player.getCartouche());
	}

	private void createAsteroid() {
		boolean xbound = RANDOM.nextBoolean();
		boolean ybound = RANDOM.nextBoolean();
		
		Asteroid asteroid = new Asteroid(
				new Vector(
						xbound ? cena.getWidth() : 0, 
						ybound ? cena.getHeight() : 0));
		cena.addSprite(asteroid);
	}

	private void doCollision() {
		BlockingQueue<Sprite2D> objetcs = cena.getObjetcs();

		for(Sprite2D objeto : objetcs){
			
			if(objeto instanceof Asteroid){
				if(objeto.getBounds().getWidth() == 0 ||
						objeto.getBounds().getHeight() == 0){
					objeto.setVisible(false);
					continue;
				}
				if(player.onCollide(objeto)){
					player.hit( ( (Asteroid) objeto ).getRaio());
					if(!player.isAlive()){
						running = false;
						JOptionPane.showMessageDialog(null, "GAME OVER!!!");
						break;
					}
				}
				
				for(Sprite2D objeto2 : objetcs){
					if((objeto2 instanceof Laser) 
							&& objeto.onCollide(objeto2)){
						objeto.setVisible(false);
						objeto2.setVisible(false);
						destroyAsteroid((Asteroid) objeto);
						scoreBoard.giveScore(100 / objeto.getBounds().width/2);
					}
						
				}				
			}			
			
		}
	}

	private void controllers() {
		if(moving)player.boost(); 
		if(left) player.rotate(-Math.toRadians(10));
		if(right) player.rotate(Math.toRadians(10));
	}


	private void destroyAsteroid(Asteroid asteroid) {
		Vector direction = Vector.create(5, Math.toRadians(RANDOM.nextInt(360)));
		Vector direction2 = Vector.create(5, Math.toRadians(RANDOM.nextInt(360)));
		Asteroid half = new Asteroid(asteroid.getPosition().add(direction), asteroid.getRaio()/2, direction);
		Asteroid half2 = new Asteroid(asteroid.getPosition().add(direction2), asteroid.getRaio()/2, direction2);
		cena.addSprite(half, half2);
	}

	private void mostraJanela() {
		janela.setExtendedState(JFrame.MAXIMIZED_BOTH);
		janela.setUndecorated(true);
		janela.setLocationRelativeTo(null);
		janela.setVisible(true);
	}

}
