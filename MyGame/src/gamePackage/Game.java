package gamePackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.NumberFormat;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Game {
	
	
	public GameFrame mainFrame;
	
	//private long lastTime;
	public long gameStartTime;
	public long Score;
	public static int topBorder = 100;
	public static int rightBorder = 1210;
	public static int downBorder  = 730;
	public static int leftBorder = 92;
	public boolean gameOver;
	
	public String time;
	
	//Game Components
	Ball ball;
	Bar bar;
	Tiles tiles;
	
	//Images
	BufferedImage bgImage;
	
	
	
	//Game States
	public enum GameStates{
		SPLASHSCREEN,MENU,LEVELSELECT,GAMERUNNING,PAUSE,GAMEOVER,DIED
	}
		
	
	
	
	public void restartGame(){
		try {
			bgImage = ImageIO.read(this.getClass().getResource("/Resources/Images/backgroundBorder.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		gameOver = false;
		bar = new Bar(mainFrame);
		ball = new Ball(mainFrame,bar.posX + bar.barWidth/2,bar.posY-50,null);
		tiles = new Tiles(this,ball);
		gameStartTime = System.nanoTime();
		Score = 0;
		
	}
	
	
		
	
	public Game(GameFrame gf){
		mainFrame = gf;
		restartGame();
	}
	
	public void drawMeta(Graphics2D g){
		g.setFont(new Font("TimesRoman",Font.BOLD + Font.ITALIC,100));
		g.setRenderingHint(
		        RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		if(gameOver){
			String score = "Score : " + Score;
			g.setColor(new Color(200,200,200));
			g.drawString(score,460,500);
			g.drawString(tiles.tilesLeft==0 ? "Good Job :)" :"GameOver :(", 400, 300);
			g.setFont(new Font("TimesRoman",Font.BOLD + Font.ITALIC,20));
			g.drawString("Press Enter To Play Again", 530, 600);
		}
		else{
			time = "Time : " + (System.nanoTime() - gameStartTime)/1000000000;
			String score = "Score : " + Score;
			g.setColor(new Color(100,100,100));
			g.drawString(time, 160, 500);
			g.drawString(score,700,500);
			
		}
	}
	
	
	public void draw(Graphics2D g){
		g.drawImage(bgImage, 10, 20, GamePanel.gameImageWidth, GamePanel.gameImageHeight, null);
		bar.draw(g);
		if(!gameOver){
			ball.draw(g);
			tiles.draw(g);
		}
		drawMeta(g);
	}
	
	public void update(){
		if(!gameOver){
			ball.update();
			bar.update();
			tiles.update();
			if(ball.posY>Game.downBorder){
				try {
					AudioInputStream stream;
				    AudioFormat format;
				    DataLine.Info info;
				    Clip clip;

				    stream = AudioSystem.getAudioInputStream(getClass().getResource("/Resources/Sounds/s3.wav"));
				    format = stream.getFormat();
				    info = new DataLine.Info(Clip.class, format);
				    clip = (Clip) AudioSystem.getLine(info);
				    clip.open(stream);
				    clip.start();
				}
				catch (Exception e) {
				  e.printStackTrace();
				}
				gameOver = true;
				}
		}
		

	}
	
	
	public void keyPressed(KeyEvent e){
		bar.keyPressed(e);
		if(gameOver&&e.getKeyCode()==KeyEvent.VK_ENTER){restartGame();}
	}
	
	public void keyReleased(KeyEvent e){
		bar.keyReleased(e);
	}
	
	
}
