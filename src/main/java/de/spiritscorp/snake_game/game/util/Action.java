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
package de.spiritscorp.snake_game.game.util;

import java.awt.Point;


public enum Action {

	LEFT,
	FORWARD,
	RIGHT;
	
	private static final Action[] VALUES = values();

	public static final Action getAction(Integer action) {
		return VALUES[action];
	}	
	
	public static final Direction toDirection(Point head, Point neck, Action action) {
		if(head.x == neck.x) {
			if((head.y - neck.y) < 0) {
				switch (action) {
				case LEFT: return Direction.LEFT;
				case FORWARD: return Direction.UP;
				case RIGHT: return Direction.RIGHT;
				}
			}else {
				switch (action){
				case LEFT: return Direction.RIGHT;
				case FORWARD: return Direction.DOWN;
				case RIGHT: return Direction.LEFT;
				}
			}
		}else {
			if((head.x - neck.x) < 0) {
				switch (action) {
				case LEFT: return Direction.DOWN;
				case FORWARD: return Direction.LEFT;
				case RIGHT:	return Direction.UP;
				}
			}else {
				switch (action) {
				case LEFT: return Direction.UP;
				case FORWARD: return Direction.RIGHT;
				case RIGHT: return Direction.DOWN;
				}
			}
		}
		return null;
	}
}
