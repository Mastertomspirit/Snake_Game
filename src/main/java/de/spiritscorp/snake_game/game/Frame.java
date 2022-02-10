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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import de.spiritscorp.snake_game.Main;
import de.spiritscorp.snake_game.RunKI;

final class Frame extends JPanel {

	private static final long serialVersionUID = 5002820872573834385L;
	private final Controller controller;
	private static Image head = null;
	private static Image tail = null;
	private static Image food = null;
	private static final Image headBg;
	private static final Image tailBg;
	private static final Image foodBg;

	static {
		try {
			head = ImageIO.read(Snake.class.getClassLoader().getResource("SnakeHead_10.png"));
			tail = ImageIO.read(Snake.class.getClassLoader().getResource("block-bum-snaketail_10.png"));
			food = ImageIO.read(Snake.class.getClassLoader().getResource("KillMe_10.png"));
		} catch (IOException e) {e.printStackTrace();}
		headBg = head;
		tailBg = tail;
		foodBg = food;
	}
	
	Frame(final Controller controller){
		this.controller = controller;
		
		setLayout(null);
		setDoubleBuffered(true);
		setBackground(Color.BLACK);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i = 0; i < controller.getSnake().size(); i++) {
			if(i == 0)		{
				g.drawImage(headBg, controller.getSnake().get(i).getPosition().x, controller.getSnake().get(i).getPosition().y, null);
			}
			else			{
				g.drawImage(tailBg, controller.getSnake().get(i).getPosition().x, controller.getSnake().get(i).getPosition().y, null);
			}
		}
		g.drawImage(foodBg, controller.getFood().getPosition().x, controller.getFood().getPosition().y, null);
		g.setColor(Color.WHITE);
		
		g.drawString(String.format("Punkte: %d", controller.getScore() ), 20, 20);
		if(!Main.GAME_ON) {
			g.setColor(Color.RED);
			g.setFont(new Font("Comic Sans MS", 3, 14));
			if(Main.kiPlay)		g.drawString("Max: " + RunKI.getHighscore() + " Avg: " + RunKI.getAverage() , Controller.GAME_WIDTH / 2 - 50, 50);
			else				g.drawString("Max: " + Model.getHighscore() + " Avg: " + Model.getAverage() , Controller.GAME_WIDTH / 2 - 50, 50);
		}
	}
}
