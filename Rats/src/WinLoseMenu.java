import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * WinLoseMenu.java
 *
 * WinLoseMenu is the controller for the win/lose fxml GUI.
 *
 * @version 1
 * @since 06/12/2021
 * Last modified 06/12/21
 * @author (REMOVED)
 */
public class WinLoseMenu {
	 private Game game;
	    
    /**
     * Stores the current game so data can be taken from it.
     */
    public void storeGame(Game game) {
    	this.game = game;
    }
	
    /**
     * Switches to the main menu.
     * @param event button event.
     */
    @FXML
    private void backButton(ActionEvent event) {
    	game.switchToMenu();
    }
    
}
