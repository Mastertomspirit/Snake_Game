/*
  		Snake Game
 		 		
 	 	@author Tom Spirit

		Copyright (c) 2022 Tom Spirit
		
		This program is free software; you can redistribute it and/or modify
		it under the terms of the GNU General Public License as published by
		the Free Software Foundation; either version 3 of the License, or
		(at your option) any later version.
		
		This program is distributed in the hope that it will be useful,
		but WITHOUT ANY WARRANTY; without even the implied warranty of
		MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
		GNU General Public License for more details.
		
		You should have received a copy of the GNU General Public License
		along with this program; if not, write to the Free Software Foundation,
		Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
package de.spiritscorp.snake_game.game;

import java.awt.Point;
import java.util.LinkedList;

import de.spiritscorp.snake_game.Main;
import de.spiritscorp.snake_game.game.util.Direction;
import de.spiritscorp.snake_game.game.util.Vars;

final class Model {

	private static int highscore = 0;
	private static int average = 0;
	private Direction direction = Direction.UP;
	private LinkedList<Snake> snake;
	private Food food;
	private final Controller controller;

	/**
	 * 
	 * @param The controller
	 */
	Model(final Controller controller) {
		this.controller = controller;
		initGame();
	}
	
	/**
	 * Start the game for human playing
	 */
	void runGame() {
		boolean running = true;
		int score = 0;
		for(int i = 0; i < Vars.ITERATIONS; i++) {
			score = 0;
			while(running) {
				running = runStep();
				if(running)		score = controller.getScore();
				Main.waitMs(Vars.VELOCITY);
			}
			average += score;
			if(highscore < score) highscore = score;
			Main.waitMs(1000);
			running = true;
			initGame();
		}
		average /= Vars.ITERATIONS;
		Main.GAME_ON = false;
		controller.updateFrame();
	}
	
	/**
	 * Initialize the game
	 */
	void initGame() {
		initSnake();
		food = createFood();
		direction = Direction.UP;
	}
	
	/**
	 * Move the snake one step forward and check about a collision
	 * @return <b>boolean</b> </br>true if no collision detected
	 */
	boolean runStep() {
		int x = snake.getFirst().getPosition().x;
		int y = snake.getFirst().getPosition().y;
		int xL = snake.getLast().getPosition().x;
		int yL = snake.getLast().getPosition().y;
		
		if(direction == Direction.DOWN)		{
			snake.addFirst(snake.getLast());
			snake.getFirst().updatePosition(x, y + 10); 
		}
		else if(direction == Direction.UP)		{
			snake.addFirst(snake.getLast());
			snake.getFirst().updatePosition(x, y - 10); 
		}
		else if(direction == Direction.LEFT)		{
			snake.addFirst(snake.getLast());
			snake.getFirst().updatePosition(x - 10, y);
		}
		else if(direction == Direction.RIGHT)	{
			snake.addFirst(snake.getLast());
			snake.getFirst().updatePosition(x + 10, y); 
		}
		
		snake.removeLast();
		
		if(snake.getFirst().getBounds().intersects(food.getBounds()))		{
			food = createFood();
			snake.addLast(new Snake(xL, yL));
		}
		
		controller.updateFrame();

		for(int i = 1; i < snake.size(); i++) {
			if(snake.getFirst().getBounds().intersects(snake.get(i).getBounds()))		return false;
		}
		
		if(snake.getFirst().getPosition().x < 0 || snake.getFirst().getPosition().x > Controller.GAME_WIDTH
		   || snake.getFirst().getPosition().y < 0 || snake.getFirst().getPosition().y > Controller.GAME_HEIGHT)		return false;
		
		return true;
	}
		
	/**
	 * Create new food
	 * @return <b>Food</b> </br>The new food with new positions
	 */
	private Food createFood() {
		Point p = null;
		boolean go = true;
		do{
			Point p1 = new Point(normalize((int)((Math.random()* (Controller.GAME_WIDTH - 40)) + 10 )), normalize((int)((Math.random()* (Controller.GAME_HEIGHT - 70)) + 10)));
			if(!snake.stream().anyMatch((s) -> s.getPosition().equals(p1)))		{
				go = false;
				p = p1;
			}
		}
		while(go);
		return new Food(p.x, p.y);
	}
	
	/**
	 * Create the snake with 3 elements
	 */
	private void initSnake() {
		snake = new LinkedList<>();
		snake.add(new Snake((Controller.GAME_WIDTH/2) - 5, (Controller.GAME_HEIGHT / 2) - 5));
		snake.add(new Snake((Controller.GAME_WIDTH/2) - 5, (Controller.GAME_HEIGHT / 2) - 5 + 10));
		snake.add(new Snake((Controller.GAME_WIDTH/2) - 5, (Controller.GAME_HEIGHT / 2) - 5 + 20));
	}
	
	/**
	 * Set the coordinates of the new food between 0 and 5
	 * @param int The random coordinates
	 * @return	<b>int</b> </br>The normalized coordinates
	 */
	private int normalize(int coordinate) {
		int x = coordinate % 5;
		int y = coordinate / 5;
		
		int res;
		
		if((y % 2) == 0) res = coordinate + 5 - x;
		else 	res = coordinate - x;
		return res;
	}

	static int getHighscore() {
		return highscore;
	}

	static int getAverage() {
		return average;
	}
	
	void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	LinkedList<Snake> getSnake(){
		return snake;
	}
	Food getFood() {
		return food;
	}
	
	Direction getDirection() {
		return direction;
	}
}
