package gamePackage;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;

public class Bar {
	
	
	GameFrame mainFrame;
	
	public int posX;
	public int posY;
	public int velX;
	public int accX;
	
	
	public int barWidth;
	public int barHeight;
	
	
	public AudioClip sound1;
	
	private boolean leftKeyPressed;
	private boolean rightKeyPressed;
	
	
	public BufferedImage barImage;
	private long lastTime;
	
	
	public Bar(GameFrame gf){	
		try {
			barImage = ImageIO.read(this.getClass().getResource("/Resources/Images/playbar.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}
		mainFrame = gf;
		
		
		barWidth = barImage.getWidth();
		barHeight = barImage.getHeight();
		
		
		posX = (Game.rightBorder - Game.leftBorder - barWidth)/2 + Game.leftBorder;
		posY = Game.downBorder - barHeight-3;
		velX = accX = 0;
		
		lastTime = -1;
	}
	
	
	
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_LEFT){leftKeyPressed = true;}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT){rightKeyPressed = true;}
	}
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_LEFT){leftKeyPressed = false;}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT){rightKeyPressed = false;}
		
	}
	
	public void update(){
		long timeElapsed = System.nanoTime() - lastTime;
		if(lastTime==-1){timeElapsed = 0;}
		lastTime = System.nanoTime();
			
		Point pp = mainFrame.gamepanel.getMousePosition();
		if(pp!=null)pp = new Point((pp.x * GamePanel.gameImageWidth)/mainFrame.getWidth()-barWidth/2,(pp.y*GamePanel.gameImageHeight)/mainFrame.getHeight());
		else pp = new Point(posX,0);
		
		velX = (pp.x - posX);
		
		posX = pp.x;
		if(posX<Game.leftBorder){posX = Game.leftBorder;}
		if(posX>Game.rightBorder-barWidth){posX = Game.rightBorder-barWidth;}
		
		Ball ball = mainFrame.gamepanel.game.ball;
		
		Rectangle r1  = new Rectangle(posX, posY, barWidth, barHeight);
		Rectangle r2 = new Rectangle(ball.posX,ball.posY,ball.ballWidth,ball.ballHeight);
		
		if(r1.intersects(r2)){
			ball.velY = - Math.abs(ball.velY);
			ball.velX += velX;
			if(ball.velX<-5){ball.velX = -5;}
			else if(ball.velX>5){ball.velX = 5;}
			try {
				AudioInputStream stream;
			    AudioFormat format;
			    DataLine.Info info;
			    Clip clip;

			    stream = AudioSystem.getAudioInputStream(getClass().getResource("/Resources/Sounds/s1.wav"));
			    format = stream.getFormat();
			    info = new DataLine.Info(Clip.class, format);
			    clip = (Clip) AudioSystem.getLine(info);
			    clip.open(stream);
			    clip.start();
			}
			catch (Exception e) {
			  e.printStackTrace();
			}
		}

		
		
	}
	
	
	
	public void draw(Graphics2D g){
		g.drawImage(barImage,posX,posY,null);
	}
	

}
