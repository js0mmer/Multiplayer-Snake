package net.jgsstudios.snake;

import java.awt.Point;

public class Snake2 {
	
	public Point[] tail = new Point[100];
	
	public boolean dead;
	private boolean grow;
	
	/**
	 * 0=Right, 1=Down, 2=Left, 3=Up
	 */
	
	public int moveDirection;
	public int tailSize;
	public int length;
	
	public int maxMoveSpeed;
	private int moveSpeed;
	
	private Food[] food;
	private Snake snake;
	
	public Snake2(Food[] food){
		this.tail[0] = new Point(20, 10);
		this.length = 1;
		
		this.food = food;
		this.maxMoveSpeed = 10;
		
		this.tailSize = 40;
}
	
	public void update(){
		if(moveSpeed >= maxMoveSpeed){
		if(moveDirection == 0)updateSnakeTail(1, 0);
		if(moveDirection == 1)updateSnakeTail(0, 1);
		if(moveDirection == 2)updateSnakeTail(-1, 0);
		if(moveDirection == 3)updateSnakeTail(0, -1);
		moveSpeed = 0;
		
		checkDeath();
		eatFood();
		}else{
			moveSpeed++;
		}
	}
	
	private void eatFood(){
		for(int i = 0; i < food.length; i++){
			if(food[i] != null){
				if(tail[0].x == food[i].x && tail[0].y == food[i].y){
					food[i] = null;
					grow=true;
					break;
				}
			}
		}
	}
	
	private void checkDeath(){
		if(tail[0].x < 0||tail[0].x > Frame.WIDTH/tailSize-1) dead=true;
		if(tail[0].y < 0||tail[0].y > Frame.HEIGHT/tailSize-1) dead=true;
		
		for(int i = 0; i < tail.length; i++){
			if(tail[i] != null){
			for(int j = 0; j < tail.length; j++){
				if(i != j){
					if(tail[j] != null){
					if(tail[i].x == tail[j].x && tail[i].y == tail[j].y){
						dead = true;
						snake.dead = true;
							}
						}
					}
				}
			}
		}
	}
	
	private void updateSnakeTail(int x, int y){
		Point lastTail = (Point) tail[0].clone();
		
		tail[0].x+=x;
		tail[0].y+=y;
		for(int i = 1; i < tail.length; i++){
			if(tail[i] != null){
				Point point = (Point) tail[i].clone();
				
				
				
				tail[i]=lastTail;
				
				lastTail = point;
				
				
			}
			
			if(grow && i == length){
				tail[i+1]=(Point) lastTail.clone();
				length++;
				grow=false;
		}
	}
	
}
}
