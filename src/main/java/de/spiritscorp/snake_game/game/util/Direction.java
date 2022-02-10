/*
 * Snake Game 
 *  
 * @author Tom Spirit
 */

package de.spiritscorp.snake_game.game.util;

public enum Direction{
	
	UP,
	DOWN,
	LEFT,
	RIGHT;

	private static final Direction[] VALUES = values();

	public static Direction getDirection(Integer action) {
		return VALUES[action];
	}	
}
