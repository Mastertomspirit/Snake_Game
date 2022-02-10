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

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

import javax.swing.SwingUtilities;

import de.spiritscorp.snake_game.game.util.Direction;

public final class Controller implements KeyListener {
	
	public static final int GAME_WIDTH = 500;
	public static final int GAME_HEIGHT = 500;
	private final Controller controller;
	private View view;
	private final Model model;
	
	public Controller(final boolean kI){
		controller = this;
		model = new Model(controller);
		try {
				SwingUtilities.invokeAndWait(
					() -> {
						view = new View(controller);
					});
		} catch (InvocationTargetException | InterruptedException e) {e.printStackTrace();}
		if(!kI) new Thread( () -> model.runGame()).start();
	}
	 
	
	void goUp() {
		if(!(model.getDirection() == Direction.DOWN)) {
			model.setDirection(Direction.UP);
		}
	}
	void goDown() {
		if(!(model.getDirection() == Direction.UP)) {
			model.setDirection(Direction.DOWN);
		}
	}
	void goLeft() {
		if(!(model.getDirection() == Direction.RIGHT)) {
			model.setDirection(Direction.LEFT);
		}
	}
	void goRight() {
		if(!(model.getDirection() == Direction.LEFT)) {
			model.setDirection(Direction.RIGHT);
		}
	}
	
	int getScore() {
		return model.getSnake().size() - 3;
	}
	
	void updateFrame() {
		view.update();
	}
	
	void disposeView() {
		view.dispose();
	}
	void setDirection(Direction direction) {
		model.setDirection(direction);
	}
	
	void initGame() {
		model.initGame();
	}
	
	boolean runStep() {
		return model.runStep();
	}

	LinkedList<Snake> getSnake(){
		return model.getSnake();
	}
	
	Food getFood() {
		return model.getFood();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			goDown();
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			goUp();
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			goRight();
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			goLeft();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
}
