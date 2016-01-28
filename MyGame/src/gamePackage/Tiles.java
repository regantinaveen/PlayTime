package gamePackage;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;







public class Tiles {
	
	
	
	
	
	Game  game;
	Ball ball;
	
	
	Random random;
	
	public int tileWidth;
	public int tileHeight;
	public int noOfTypesOfTiles = 4;
	public int tileHorizontalGap = 10;
	public int tileVerticalGap = 5;
	public int horizontalOffset = 15;
	public int verticalOffset = 5;
	public int noOfColumns;
	public int noOfRows;
	
	
	int tiletype[][];
	boolean isTilePresent[][];
	int tilesLeft;
	BufferedImage tileImages[];
	
	
	
	public Tiles(Game gm,Ball bl){
		game = gm;
		ball = bl;
		
		random = new Random();
		
		tileImages = new BufferedImage[noOfTypesOfTiles];
		
		
		for(int i=0;i<noOfTypesOfTiles;i++){
			try {
				tileImages[i] =  ImageIO.read(this.getClass().getResource("/Resources/Images/tile" + (i+1) + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		tileWidth = tileImages[0].getWidth();
		tileHeight = tileImages[0].getHeight();
		
		noOfColumns = (Game.rightBorder-Game.leftBorder-tileHorizontalGap)/(tileWidth+tileHorizontalGap);
		noOfRows = (Game.downBorder-Game.topBorder - 400)/(tileHeight+tileVerticalGap);
		
		tilesLeft = noOfColumns * noOfRows;
		
		//System.out.println(noOfRows + " " + noOfColumns);
		loadTiles();
		
	}
	
	
	public int getXOfTile(int i){
		return Game.leftBorder + horizontalOffset + i*(tileWidth + tileHorizontalGap);
	}
	
	public int getYofTile(int j){
		return Game.topBorder + verticalOffset + j*(tileHeight + tileVerticalGap);
	}
	
	

	
	
	
	public void loadTiles(){
		tiletype = new int[noOfRows][noOfColumns];
		isTilePresent = new boolean[noOfRows][noOfColumns];
		
		for(int i=0;i<noOfRows;i++){
			for(int j=0;j<noOfColumns;j++){
				int r = random.nextInt(4);
				tiletype[i][j] = r;
				isTilePresent[i][j] = true;
			}
		}
	}
	
	
	public void draw(Graphics2D g){
		for(int i=0;i<noOfRows;i++){
			for(int j=0;j<noOfColumns;j++){
				if(isTilePresent[i][j])g.drawImage(tileImages[tiletype[i][j]], getXOfTile(j), getYofTile(i),null);
			}
		}
	}
	
	
	
	
	void update(){
		
		int bx = ball.posX;
		int by = ball.posY;
		
		int i = (by - Game.topBorder - verticalOffset + tileVerticalGap/2)/(tileHeight + tileVerticalGap);
		int j = (bx - Game.leftBorder - horizontalOffset + tileHorizontalGap/2)/(tileWidth + tileHorizontalGap);
		
		boolean found = false;
		
		int collisionWidth = 7;
		
		for(int x = Math.max(0,i-1);x<Math.min(noOfRows, i+1);x++){
			if(found)break;
			for(int y = Math.max(0, j-1);y<Math.min(noOfColumns, j+1);y++){
				if(!isTilePresent[x][y])continue;
				int xl = getXOfTile(y) - tileHorizontalGap/2;
				int xr = xl + tileWidth + tileHorizontalGap/2;
				int yt = getYofTile(x) - tileVerticalGap/2;
				int yb = yt + tileHeight + tileVerticalGap/2;
				
				if(ball.velX>0){
					if(by<yb&&by>yt){
						if(Math.abs(bx-xl)<collisionWidth){ball.velX = - Math.abs(ball.velX);found = true;}
					}
				}
				else{
					if(by<yb&&by>yt){
						if(Math.abs(bx-xr)<collisionWidth){ball.velX = Math.abs(ball.velX);found = true;}
					}
				}
				
				if(ball.velY>0){
					if(bx<xr&&bx>xl){
						if(Math.abs(by-yt)<collisionWidth){ball.velY = -Math.abs(ball.velY);found = true;}
					}
				}
				else{
					if(bx<xr&&bx>xl){
						if(Math.abs(by-yb)<collisionWidth){ball.velY = Math.abs(ball.velY);found = true;}
					}
				}
				
				
				
				if(found){
					tilesLeft--;
					game.Score += 1;
					isTilePresent[x][y] = false;
					if(tilesLeft==0){game.gameOver = true;}
					
					try {
						AudioInputStream stream;
					    AudioFormat format;
					    DataLine.Info info;
					    Clip clip;

					    stream = AudioSystem.getAudioInputStream(getClass().getResource("/Resources/Sounds/s2.wav"));
					    format = stream.getFormat();
					    info = new DataLine.Info(Clip.class, format);
					    clip = (Clip) AudioSystem.getLine(info);
					    clip.open(stream);
					    clip.start();
					}
					catch (Exception e) {
					  e.printStackTrace();
					}
					
					break;}
			}
		}
	}
	


	
	
	
	
	

}
