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
package de.spiritscorp.snake_game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;

import de.spiritscorp.snake_game.game.API;
import de.spiritscorp.snake_game.game.Controller;
import de.spiritscorp.snake_game.game.util.GameState;
import de.spiritscorp.snake_game.game.util.GameStateUtil;
import de.spiritscorp.snake_game.game.util.Vars;

public class RunKI {

	private final API game;
	private static int average = 0;
	private static int highscore = 0;

	RunKI(){
		game = new API(new Controller(true));
		this.runGame("SnakeNet___Durch_90__Höchst_145__Zeit_1644609027469_167737200223500.zip");
	}
	
	/**
	 * Starting the game for the neuronal net
	 * @param networkName The name of the network which is to use
	 */
	private void runGame(String networkName) {
		final MultiLayerNetwork multiLayerNetwork = loadNetwork(networkName);
		int score = 0;
		highscore = 0;
		average = 0;
		for(int i = 0; i < Vars.ITERATIONS; i++) {
			score = 0;
			game.initializeGame();
			while(game.getGameRun()) {
				final GameState gameState = game.buildStateObservation();
				final INDArray output = multiLayerNetwork.output(gameState.getMatrix(), false);
				double[] data = output.data().asDouble();
				int maxValueIndex = GameStateUtil.getMaxValueIndex(data);
				game.move(maxValueIndex);
				if(game.getGameRun()) score = game.getScore();
				
				Main.waitMs(25);
			}
			System.out.format("Durchgang %d erreichte Punktzahl %d%n", i, score);
			if(highscore < score) highscore = score;
			average += score;
		}
		average /= Vars.ITERATIONS;
		System.out.println("Höchstpunktzahl:  " + highscore);
		System.out.println("Durchschnitt:     " + average);
		Main.GAME_ON = false;
		game.updateFrame();
	}
	
	/**
	 * Create the file with the network from the resources and load it from the harddisk
	 * @param networkName The name of the network which is to use
	 * @return <b>MultiLayerNetwork</b> The multilayer network
	 */
    private MultiLayerNetwork loadNetwork(final String networkName) {
        try {
        	Path ndPath = Paths.get(System.getProperty("user.home"), "Snake_Game");
        	Path netPath = ndPath.resolve(networkName);
        	if(!Files.exists(netPath)) {
        		if(!Files.exists(ndPath))		Files.createDirectory(ndPath);
        		Files.copy(getClass().getClassLoader().getResourceAsStream(networkName), netPath, StandardCopyOption.REPLACE_EXISTING);
        	}
            return MultiLayerNetwork.load(netPath.toFile(), false);
        } catch (IOException e) {
            e.printStackTrace();
            game.dispose();
        }
        return null;
    }
    
    void dispose() {
    	game.dispose();
    }

	public static int getAverage() {
		return average;
	}

	public static int getHighscore() {
		return highscore;
	}
}
