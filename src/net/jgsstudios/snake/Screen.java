package net.jgsstudios.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.Random;

import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable {
	
	private static final long serialVersionUID = 1L;

	private Thread thread = new Thread(this);
	
	private KeyHandler keyHandler;
	
	private Food[] food;
	
	private Frame frame;
	private Snake snake;
	private Snake2 snake2;
	
	private Random random;
	
	private Image menuImage = null;
	private boolean isInMenu = true;
	private int endgameonce = 0;
	

	public Screen(Frame frame){
		this.random = new Random();
		this.food = new Food[10];
		
		this.frame = frame;
		this.snake = new Snake(this.food);
		this.snake2 = new Snake2(this.food);
		this.keyHandler = new KeyHandler();
		this.frame.addKeyListener(this.keyHandler);
		
		
		this.thread.start();
	}
	
	public void paintComponent(Graphics g){
		g.clearRect(0, 0, Frame.WIDTH, Frame.HEIGHT);
		
		if(isInMenu){
			//draw menu
			drawMenu(g);
		}else{
			//draw everything else
			for(int i = 0; i < snake.tail.length; i++){
				if(snake.tail[i] != null){
					g.setColor(Color.BLACK);
					g.drawRect(snake.tail[i].x*snake.tailSize, snake.tail[i].y*snake.tailSize, snake.tailSize, snake.tailSize);
					
					g.setColor(new Color(0, 136+((i*5)<(255-136)?(i*5):(255-136)), 25));
					g.fillRect(snake.tail[i].x*snake.tailSize+1, snake.tail[i].y*snake.tailSize+1, snake.tailSize-1, snake.tailSize-1);
				}
			}
			
			for(int i = 0; i < snake2.tail.length; i++){
				if(snake2.tail[i] != null){
					g.setColor(Color.BLACK);
					g.drawRect(snake2.tail[i].x*snake2.tailSize, snake2.tail[i].y*snake2.tailSize, snake2.tailSize, snake2.tailSize);
					
					g.setColor(new Color(0, 38+((i*5)<(255-136)?(i*5):(255-136)), 255));
					g.fillRect(snake2.tail[i].x*snake.tailSize+1, snake2.tail[i].y*snake2.tailSize+1, snake2.tailSize-1, snake2.tailSize-1);
				}
			}
			
			for(int i = 0; i < food.length; i++){
				if(food[i] != null){
					g.setColor(Color.BLACK);
					g.drawRect(food[i].x*snake.tailSize+(snake.tailSize-food[i].foodSize)/2, food[i].y*snake.tailSize+(snake.tailSize-food[i].foodSize)/2, food[i].foodSize, food[i].foodSize);
					
					g.setColor(new Color(255, 0, 0));
					g.fillRect(food[i].x*snake.tailSize+(snake.tailSize-food[i].foodSize)/2+1, food[i].y*snake.tailSize+(snake.tailSize-food[i].foodSize)/2+1, food[i].foodSize-1, food[i].foodSize-1);
				}
				
			}
			
			g.setColor(Color.BLACK);
			g.drawString("Press Esc to Pause", 1250, 25);
			g.drawString("Controls:", 1250, 40);
			
			g.setColor(new Color(0, 136,  25));
			g.drawString("Player 1: WASD", 1250, 55);
			g.drawString("Player 1 Length: " + snake.length, 10 , 40);
			g.setColor(new Color(0, 38,  255));
			g.drawString("Player 2: Arrow Keys", 1250, 70);
			g.drawString("Player 2 Length: " + snake2.length, 10, 55);
			
			g.setColor(Color.BLACK);
			g.drawString("FPS: " + fps, 10, 25);
			g.drawString("Created by JGS Studios", 10, 70);
			
			
			if(this.snake.dead||this.snake2.length == 99){
				if(endgameonce == 0||endgameonce == 1){
				g.setColor(Color.RED);
				g.drawString("Game Over", 650, 375);
				g.setColor(new Color(0, 38,  255));
				g.drawString("Player 2 Wins!", 645, 400);
				endgameonce=1;
				snake2.dead = true;
			}
		}	
			if(this.snake2.dead||this.snake.length == 99){
				if(endgameonce == 0||endgameonce == 2){
				g.setColor(Color.RED);
				g.drawString("Game Over", 650, 375);
				g.setColor(new Color(0, 136,  25));
				g.drawString("Player 1 Wins!", 645, 400);
				endgameonce=2;
				snake.dead = true;
			}
		}			
	}
}	
	private int maxFoodSpawn = 200;
	private int foodSpawn;
	
	public void drawMenu(Graphics g){
		if(this.menuImage == null){
		try{
			URL imagePath = Screen.class.getResource("snakeMultiMenu.png");
			menuImage = Toolkit.getDefaultToolkit().getImage(imagePath);
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
		g.drawImage(menuImage, 0, 0, 1366, 728, this);
}	
	
	
	
	public void update(){
		if(!isInMenu){
		if(!this.snake.dead)this.snake.update();
		if(!this.snake2.dead)this.snake2.update();
		
		if(foodSpawn >= maxFoodSpawn){
			for(int i = 0; i < food.length; i++){
				if(food[i] == null){
					boolean spawned = false;
					
					int x = 0;
					int y = 0;
					
					while(!spawned){
					x = random.nextInt(Frame.WIDTH/snake.tailSize-1);
					y = random.nextInt(Frame.HEIGHT/snake.tailSize-1);
					
					spawned=true;
					
					for(int j = 0; j < food.length; j++){
						if(food[j] != null){
							if(food[j].x == x && food[j].y == y){
								spawned=false;
							}
						}
					}
					}
					for(int j = 0; j < snake.tail.length; j++){
						if(snake.tail[j] != null){
							if(snake.tail[j].x == x && snake.tail[j].y == y){
								spawned=false;
							}
						}
					}
					for(int j = 0; j < snake2.tail.length; j++){
						if(snake2.tail[j] != null){
							if(snake2.tail[j].x == x && snake2.tail[j].y == y){
								spawned=false;
							}
						}
					}
					food[i] = new Food (x, y);
					
					break;
				}
			}
			foodSpawn=0;
		}else{
			foodSpawn++;
		}
	}
	}	
	private long maxFrameRate = 50;
	private int fps = 0;
	
	public void run() {
		int frames = 0;
		long secondStart = System.currentTimeMillis();
		
		while(true){
			update();
			
			try {
				Thread.sleep(1000/this.maxFrameRate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			repaint();

			if(secondStart + 1000 <= System.currentTimeMillis()){
				secondStart = System.currentTimeMillis();
				fps=frames;
				frames=0;
			}
			
			frames++;
		}
	}
	private class KeyHandler implements KeyListener{
		public void keyPressed(KeyEvent e) {
			//Start Game
			if(e.getKeyCode() == 10){
				isInMenu = false;
			}
			//Pause Game
			if(e.getKeyCode() == 27){
				isInMenu = true;
			}
			//Up
			if(e.getKeyCode() == 87){
				snake.moveDirection=3;
			}

		    //Right
			if(e.getKeyCode() == 68){
				snake.moveDirection=0;
			}
			
			//Down
			if(e.getKeyCode() == 83){
				snake.moveDirection=1;
			}
			
			//Left
			if(e.getKeyCode() == 65){
				snake.moveDirection=2;
			}
			
			//Up2
			if(e.getKeyCode() == 38){
				snake2.moveDirection=3;
			}
			
			//Right 2
			if(e.getKeyCode() == 39){
				snake2.moveDirection=0;
			}
			
			//Down2
			if(e.getKeyCode() == 40){
				snake2.moveDirection=1;
			}
			
			//Left
			if(e.getKeyCode() == 37){
				snake2.moveDirection=2;
			}
		}
		public void keyReleased(KeyEvent e) {
			
		}

		public void keyTyped(KeyEvent e) {
			
		}
		
	}

}
