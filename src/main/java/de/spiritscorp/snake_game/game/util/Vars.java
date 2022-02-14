package de.spiritscorp.snake_game.game.util;

public class Vars {

	private Vars() {}
	
		/**
		 * Number of games in a series
		 */
		public final static int ITERATIONS = 10;
		
		/**
		 * The time in milliseconds between the steps
		 */
		public static final int VELOCITY = 100;
		
		/**
		 * Length of the snakes view
		 */
		public final static int NEXT_STEP_LENGTH = 10;

		//		All values for the game state calculation 
		public final static double SNAKE_DIE_STATE = -15.0;
		public final static double SNAKE_EAT_STATE = 15.0;
		public final static double SNAKE_NEAR_TO_FOOD = 2.5;
		public final static double SNAKE_NOT_NEAR_TO_FOOD = -2.5;
		public final static double NEXT_STEP_POSITIV = 2.37;
		public final static double NEXT_STEP_NEGATIV = 8.76;
		public final static double NEXT_STEP_FOOD = 11.99;
}
