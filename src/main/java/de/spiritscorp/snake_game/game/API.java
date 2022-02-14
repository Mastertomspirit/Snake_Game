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

import de.spiritscorp.snake_game.game.util.Action;
import de.spiritscorp.snake_game.game.util.GameState;
import de.spiritscorp.snake_game.game.util.GameStateUtil;

public final class API {

	private final Controller controller;
	private boolean gameRun = true;

	public API(final Controller controller) {
		this.controller = controller;
	}
	
	/**
	 * Initialize the game
	 * @return <b>GameState</b> </br>The current game state
	 */
	public GameState initializeGame() {
		controller.initGame();
		gameRun = true;
		return buildStateObservation();
	}
	
	/**
	 * Move the snake for one Step at the given direction 
	 * @param direction The direction where the snake should go
	 */
	public void move(final int action) {
		controller.setDirection(Action.toDirection(controller.getSnake().getFirst().getPosition(), controller.getSnake().get(1).getPosition(), Action.getAction(action)));
		gameRun = controller.runStep();
	}

	/**
	 * Build the game state for the next possible steps
	 * @return <b>GameState</b> </br>The game state
	 */
	public GameState buildStateObservation() {
		Point head = controller.getSnake().getFirst().getPosition();
		Point neck = controller.getSnake().get(1).getPosition();
		return new GameState(new double[] {
				GameStateUtil.getStateForDirection(controller.getSnake(), controller.getFood(), Action.toDirection(head, neck, Action.LEFT)),
				GameStateUtil.getStateForDirection(controller.getSnake(), controller.getFood(), Action.toDirection(head, neck, Action.FORWARD)),
				GameStateUtil.getStateForDirection(controller.getSnake(), controller.getFood(), Action.toDirection(head, neck, Action.RIGHT))
		});
	}
	
	/**
	 * Check if the game run, or if it´s over
	 * @return <b>boolean</b> </br>true if game going on 
	 */
	public boolean getGameRun() {
		return gameRun;
	}
	
	public void updateFrame() {
		controller.updateFrame();
	}
	
	public void dispose() {
		controller.disposeView();
	}

	public int getScore() {
		return controller.getScore();
	}
}






