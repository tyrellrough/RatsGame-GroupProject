import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * LevelsMenu.java
 * 
 * LevelsMenu is the controller for the level menu gui. It's used to 
 * select a level.
 * 
 * @version 1.8
 * @since 02/12/2021
 * Last modified 05/12/21
 * @author (REMOVED)
 */
public class LevelsMenu {
	private static final int LEVEL_ONE = 1;
	private static final int LEVEL_TWO = 2;
	private static final int LEVEL_THREE = 3;
	private static final int LEVEL_FOUR = 4;
	private static final int LEVEL_FIVE = 5;
	private Game game;
	
    /**
     * Used to pass profile menu a profile manager.
     */
    public void storeGame(Game game) {
    	this.game = game;
    }
	
    /**
     * Switches to the main menu.
     * @param event The back button is pressed.
     */
    @FXML
    void backButton(ActionEvent event) {
    	game.getGraphics().getStage().setScene(game.getGraphics().getMenuScene());
    }

    /**
     * Loads level 1.
     * @param event The 'Level 1' button is pressed.
     */
    @FXML
    void levelOneButtonPress(ActionEvent event) {
    	if (game.getProfiles().getCurrentProfile().getMaxLevel() >= LEVEL_ONE) {
	    	game.onLevelStart(LEVEL_ONE);
    	}
    }

    /**
     * Loads level 2.
     * @param event The 'Level 2' button is pressed.
     */
    @FXML
    void levelTwoButtonPress(ActionEvent event) {
        if (game.getProfiles().getCurrentProfile().getMaxLevel() >= LEVEL_TWO) {
            game.onLevelStart(LEVEL_TWO);
        }
    }
    
    /**
     * Loads level 3.
     * @param event The 'Level 3' button is pressed.
     */
    @FXML
    void levelThreeButtonPress(ActionEvent event) {
    	if (game.getProfiles().getCurrentProfile().getMaxLevel() >= LEVEL_THREE) {
	    	game.onLevelStart(LEVEL_THREE);
    	}
    }

    /**
     * Loads level 4.
     * @param event The 'Level 4' button is pressed.
     */
    @FXML
    void levelFourButtonPress(ActionEvent event) {
        if (game.getProfiles().getCurrentProfile().getMaxLevel() >= LEVEL_FOUR) {
            game.onLevelStart(LEVEL_FOUR);
        }
    }

    /**
     * Loads level 5.
     * @param event The 'Level 5' button is pressed.
     */
    @FXML
    void levelFiveButtonPress(ActionEvent event) {
        if (game.getProfiles().getCurrentProfile().getMaxLevel() >= LEVEL_FIVE) {
            game.onLevelStart(LEVEL_FIVE);
        }
    }
}
