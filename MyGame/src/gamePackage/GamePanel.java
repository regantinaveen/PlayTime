package gamePackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;





public class GamePanel extends JPanel implements KeyListener,MouseListener{


	private static final long serialVersionUID = 1L;
	
	final long secInNanosec = 1000000000;
	final long milliInNano = 1000000;
	final long milliInSec = 1000;
	
	private BufferedImage gameScreenImage;
	public GameFrame mainFrame;
	public Game game;
	private int FPS  = 60;
	private long updateTime = milliInSec/60;
	
	
	public static int gameImageWidth = 1300;
	public static int gameImageHeight = 800;
	
	

	public GamePanel(GameFrame gf){
		super();
		mainFrame = gf;	
		setFocusable(true);
		requestFocus();
		game = new Game(gf);
		
		
		
		
		setDoubleBuffered(true);
		this.addKeyListener(this);
		
		Thread t = new Thread(new Runnable() {
			
			
			@Override
			public void run() {
				gameLoop();
			}
		});
		
		t.start();
		
	}
	
	
	public void draw(Graphics2D g){
		game.draw(g);
	}
	
	

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		gameScreenImage = new BufferedImage(gameImageWidth, gameImageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D)gameScreenImage.getGraphics();
		g2.setColor(new Color(50,50,50));
		g2.fillRect(0, 0, gameScreenImage.getWidth(), gameScreenImage.getHeight());
		draw(g2);
		g.drawImage(gameScreenImage,0,0,mainFrame.getWidth(),mainFrame.getHeight(),null);
	}
	
	
	public void gameLoop(){
		
		long lastTime = System.nanoTime();
		long loopcount = 0;
		long paintcountRate = (milliInSec)/(FPS*updateTime);
		if(paintcountRate<1)paintcountRate = 1;
		
		while(true){
			lastTime = System.nanoTime();
			game.update();
			if(loopcount%paintcountRate==0){repaint();}
			long timeLeft = updateTime - (System.nanoTime() - lastTime)/milliInNano;
			if(timeLeft<1){timeLeft = 1;}
			
			try {
				Thread.sleep(timeLeft);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			loopcount++;
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_F){mainFrame.toggleFullScreen();}
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE){System.exit(ABORT);}
		game.keyPressed(e);
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		game.keyReleased(e);
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		
	}	
}
