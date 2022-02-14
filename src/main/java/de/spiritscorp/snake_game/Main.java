/*
 		Snake Game
 		
 		A little test game for neuronal nets 
 		
 	 	@author Tom Spirit
 	 	@date 2022
 		@version	V1.2

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
package de.spiritscorp.snake_game;

import javax.swing.JOptionPane;

import de.spiritscorp.snake_game.game.API;
import de.spiritscorp.snake_game.game.Controller;

public class Main {
	
	public static boolean GAME_ON = true;				
	public static boolean kiPlay = false;
	private static API api;
	private static RunKI runKI;

	public static void main(String[] args) {
		Main.startGame();
	}
	
    public static void waitMs(long ms) {
    	try {
    		Thread.sleep(ms);
    	}catch(InterruptedException e) {e.printStackTrace();}
    }
		
	/**
	 * Ask which modus would you play. KI or human. And would you replay or not. 
	 */
	private static void startGame() {
		do {
			int res = JOptionPane.showOptionDialog(null, "Willst du selber spielen?", "Snake Game", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if(res == 0) {
				api = new API(new Controller(false));
				kiPlay = false;
			}
			else {
				runKI = new RunKI();
				kiPlay = true;
			}
			while(Main.GAME_ON) {
				waitMs(200);
			}
			waitMs(5000);
			Main.GAME_ON = true;
			if(JOptionPane.showOptionDialog(null, "Nochmal spielen?", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null) != 0)	System.exit(0);

			if(kiPlay)	runKI.dispose();
			else		api.dispose();
		}while(true);
	}
}
