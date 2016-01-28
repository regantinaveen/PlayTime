package gamePackage;

import java.awt.Cursor;
import java.awt.Frame;

import javax.swing.JFrame;

public class GameFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	GamePanel gamepanel;
	boolean fullscreen = false;
	
	
	
	public void setFullScreen(){
		fullscreen  = true;
		setExtendedState(Frame.MAXIMIZED_BOTH);
		//repaint();
	}
	
	public void removeFullScreen(){
		fullscreen = false;
		setBounds(150, 50, 1000, 600);
		//repaint();
	}
	
	public void toggleFullScreen(){
		if(fullscreen)removeFullScreen();
		else setFullScreen();
	}
	

	@SuppressWarnings("deprecation")
	public GameFrame(){		
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setUndecorated(true);
		if(fullscreen)setFullScreen();
		
		setCursor(Cursor.CROSSHAIR_CURSOR);
		gamepanel = new GamePanel(this);
		setContentPane(gamepanel);
		
		
		setVisible(true);
	}
	
	
	
	

	
	
	
	
	
}
