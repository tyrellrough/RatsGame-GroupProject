import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * DrawProgressBar.java
 *
 * DrawProgressBar draws the progress bar on the screen, the visual indicator shows
 * how many rats are on a level and how close the player is to winning/losing.
 *
 * @version 1.2
 * @since 25/11/2021
 * Last modified 05/12/21
 * @author (REMOVED)
 */
public class DrawProgressBar {
	private static final double PROGRESS_BAR_HEIGHT = 300;
	private static final double PROGRESS_BAR_WIDTH = 25;
	
	/**
	 * Draws the progress bar, its shows the number of each rat gender and how close
	 * the player is to winning/losing.
	 * @param gc The graphics for the progress bar.
	 * @param ratManager The RatManager for the Level.
	 * @param level The current Level.
	 */
	public static void drawProgressBar(GraphicsContext gc, RatManager ratManager, Level level) {
		int maxRatPopulation =  level.getRatMaxPop();
		// How many pixels tall a Rat should use on the bar.
		double ratSegmentHeight = PROGRESS_BAR_HEIGHT  / maxRatPopulation;
		// Horizontal line properties.
		gc.setLineWidth(1f);
		gc.setStroke(Color.WHITE);
		// Creating the female Rat bar first at the top.
		gc.setFill(Color.HOTPINK);
		double currentEndOfRatBar = 0;
		int numberOfFemaleRats = ratManager.getFemaleRats();
		for (int i = 0; i < numberOfFemaleRats; i++) {
			gc.fillRect(0, currentEndOfRatBar, PROGRESS_BAR_WIDTH, currentEndOfRatBar + ratSegmentHeight);
			// Draws horizontal lines splitting the number of Rats.
			gc.strokeLine(0, currentEndOfRatBar, PROGRESS_BAR_WIDTH, currentEndOfRatBar);
			currentEndOfRatBar = currentEndOfRatBar + ratSegmentHeight;
		}
		// Drawing the male Rat bar.
		gc.setFill(Color.BLUE);
		int numberOfMaleRats = ratManager.getMaleRats();
		for (int i = 0; i < numberOfMaleRats; i++) {
			gc.fillRect(0, currentEndOfRatBar, PROGRESS_BAR_WIDTH, currentEndOfRatBar + ratSegmentHeight);
			// Draws horizontal lines splitting the number of Rats.
			gc.strokeLine(0, currentEndOfRatBar, PROGRESS_BAR_WIDTH, currentEndOfRatBar);
			currentEndOfRatBar = currentEndOfRatBar + ratSegmentHeight;
		}
		
		// Fill the rest in as green for the Player's progress
		gc.strokeLine(0, currentEndOfRatBar, PROGRESS_BAR_WIDTH, currentEndOfRatBar);
		gc.setFill(Color.GREEN);
		gc.fillRect(0, currentEndOfRatBar, PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT);
	}
}
