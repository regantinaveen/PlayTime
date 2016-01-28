package gamePackage;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Ball {
	
	
	public GameFrame mainFrame;
	
	public int posX;
	public int posY;
	public int velX;
	public int velY;
	
	
	public int ballWidth;
	public int ballHeight;
	
	public long lastTime;

	
	BufferedImage ballImage;
	
	Ball(GameFrame gf,int posx,int posy,String s){
		this.posX = posx;
		this.posY = posy;
		
		mainFrame = gf;
		
		URL path = this.getClass().getResource("/Resources/Images/playball.png");
		if(s!=null)path = this.getClass().getResource("/Resources/Images/"+s);
		loadResources(path);
		
		ballWidth = ballImage.getWidth();
		ballHeight = ballImage.getHeight();
		lastTime = -1;
		
		
		velX = 5;//pixel/nanosec
		velY =  -6;
		
	}

	private void loadResources(URL path){
		try {
			ballImage = ImageIO.read(path);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void draw(Graphics2D g){
		g.drawImage(ballImage, posX, posY, null);
	}
	
	
	public void update(){
		long timeElapsed = System.nanoTime() - lastTime;
		if(lastTime==-1){timeElapsed = 0;}
		lastTime = System.nanoTime();
		
		
		
		
		posX += velX;
		posY += velY;
		
		

		
		if(posX>=Game.rightBorder-(ballWidth)){velX = -Math.abs(velX);}
		if(posX<Game.leftBorder){velX = Math.abs(velX);}
		if(posY<Game.topBorder){velY = Math.abs(velY);}
		//if(posY>=Game.downBorder - (ballWidth)){velY = -Math.abs(velY);}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
