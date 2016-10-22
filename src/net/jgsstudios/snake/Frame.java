package net.jgsstudios.snake;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Frame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	public static int WIDTH;
	public static int HEIGHT;
	
	public static void main(String[]args){
		new Frame();
	}
	
	public Frame(){
		
		this.setTitle("Multiplayer Snake - JGS Studios");
		this.setResizable(true);
		this.setUndecorated(false);
		this.setExtendedState(NORMAL);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setMaximumSize(new Dimension (1366, 720));
		this.setMinimumSize(new Dimension (1366, 720));
		this.setPreferredSize(new Dimension (1366, 720)); 
		
		
		this.setVisible(true);
		
		WIDTH = getWidth();
		HEIGHT = getHeight();
		
		this.add(new Screen(this));
		
	}

}
