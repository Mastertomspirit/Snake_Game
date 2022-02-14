/*
 * Snake Game 
 *  
 * @author Tom Spirit
 */

package de.spiritscorp.snake_game.game.util;

import java.awt.Point;
import java.util.LinkedList;

import de.spiritscorp.snake_game.game.Controller;
import de.spiritscorp.snake_game.game.Food;
import de.spiritscorp.snake_game.game.Snake;

public class GameStateUtil {

	private GameStateUtil() {}

	/**
	 * Get the "view" for the snake 
	 * 
	 * 
	 * @param The snake 
	 * @param The food
	 * @param The direction to check
	 * @return A double value, which represent the radar distance 
	 */
	public static double getStateForDirection(LinkedList<Snake> snake, Food food, Direction direction) {
		Point nextMove[] = new Point[Vars.NEXT_STEP_LENGTH];
		int bounds = 0;
		
		switch(direction) {
		case UP:	
			for(int i = 0; i < nextMove.length; i++) {
				bounds += snake.getFirst().getBounds().height;
					nextMove[i] = new Point(snake.getFirst().getPosition().x, snake.getFirst().getPosition().y - bounds); 
			}
			break;
		case DOWN:				
			for(int i = 0; i < nextMove.length; i++) {
				bounds += snake.getFirst().getBounds().height;
				nextMove[i] = new Point(snake.getFirst().getPosition().x, snake.getFirst().getPosition().y + bounds); 
			}
			break;
		case LEFT:	
			for(int i = 0; i < nextMove.length; i++) {
				bounds += snake.getFirst().getBounds().width;
				nextMove[i] = new Point(snake.getFirst().getPosition().x - bounds, snake.getFirst().getPosition().y); 
			}
			break;
		case RIGHT:	
			for(int i = 0; i < nextMove.length; i++) {
				bounds += snake.getFirst().getBounds().width;
				nextMove[i] = new Point(snake.getFirst().getPosition().x + bounds, snake.getFirst().getPosition().y); 
			}
			break;
		}

		if(!nextMoveAble(snake, nextMove[0]))	return Vars.SNAKE_DIE_STATE;

		double nextFiveSteps = nextFiveSteps(nextMove, food.getPosition(), snake);
		
		if(nextMove[0].equals(food.getPosition())) return Vars.SNAKE_EAT_STATE + nextFiveSteps;
				
		if(direction == Direction.LEFT || direction == Direction.RIGHT) {
			if(Math.abs(nextMove[0].x - food.getPosition().x) < Math.abs(snake.getFirst().getPosition().x - food.getPosition().x))	return Vars.SNAKE_NEAR_TO_FOOD + nextFiveSteps;
			else if(Math.abs(nextMove[0].x - food.getPosition().x) == Math.abs(snake.getFirst().getPosition().x - food.getPosition().x))	return 0 + nextFiveSteps;
			else	return Vars.SNAKE_NOT_NEAR_TO_FOOD + nextFiveSteps;
		}else {
			if(Math.abs(nextMove[0].y - food.getPosition().y) < Math.abs(snake.getFirst().getPosition().y - food.getPosition().y))	return Vars.SNAKE_NEAR_TO_FOOD + nextFiveSteps;
			else if(Math.abs(nextMove[0].y - food.getPosition().y) == Math.abs(snake.getFirst().getPosition().y - food.getPosition().y))	return 0 + nextFiveSteps;
			else	return Vars.SNAKE_NOT_NEAR_TO_FOOD + nextFiveSteps;
		}
	}

	/**
	 * 
	 * @param A double array with the data
	 * @return The maximum value over all datas
	 */
	public static int getMaxValueIndex(double[] data) {
		double maxValue = -1000.0;
		int max = 0;
		for(int i = 0; i< data.length; i++) {
			if(data[i] > maxValue) {
				maxValue = data[i];
				max = i;
			}
		}
		return max;
	}
	
	/**
	 * Check the next steps and evaluate the distance
	 * @param nextMove
	 * @param food
	 * @param snake
	 * @return
	 */
	private static double nextFiveSteps(Point[] nextMove, Point food, LinkedList<Snake> snake) {
		double ret = 0;
		boolean hit = false;

		for(int i = 1; i < nextMove.length; i++) {
			if(nextMove[i].x < 0 || nextMove[i].x >= Controller.GAME_WIDTH)			return ret;
			else if( nextMove[i].y < 0 || nextMove[i].y >= Controller.GAME_HEIGHT) 	return ret - 1;
			else if(nextMove[i].equals(food))	{
				ret += (Vars.NEXT_STEP_FOOD / i);
				hit = true;
			}
			
			if(!hit) {
				for(Snake boa : snake) {
					if(boa.getPosition().equals(nextMove[i])) {
						return ret += -Vars.NEXT_STEP_NEGATIV;
					}
				}
			}
			
			if(!hit) {
				ret += (Vars.NEXT_STEP_POSITIV / i);
			}
			hit = false;
		}
	return ret;
	}

	/**
	 * Check if the next move is able
	 * 
	 * @param The snake
	 * @param The point of the next move
	 * @return true if no collision detected
	 */
	private static boolean nextMoveAble(LinkedList<Snake> snake, Point nextMove) {
			for(Snake s : snake) {
				if(s.getPosition().equals(nextMove)) return false;
			}
			if(snake.getFirst().getPosition().x < 0 || snake.getFirst().getPosition().x > Controller.GAME_WIDTH)	return false;
			if(snake.getFirst().getPosition().y < 0 || snake.getFirst().getPosition().y > Controller.GAME_HEIGHT)	return false;
		return true;
	}
}
