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
import java.awt.Rectangle;

public final class Snake {

	private  Rectangle bounds;
	
	Snake(int x, int y){

		bounds = new Rectangle(x, y, 10,10);
	}
	
	public final Point getPosition() {
		return bounds.getLocation();
	}
		
	public final Rectangle getBounds() {
		return bounds;		
	}
	
	final void updatePosition(int x, int y) {
		bounds.x = x;
		bounds.y = y;
	}
}
