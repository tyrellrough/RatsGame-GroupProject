import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * LevelsMenu.java
 * 
 * SavesMenu is the controller for the profile menu. It's used to 
 * load a save or save.
 * 
 * @version 1.8
 * @since 02/12/2021
 * Last modified 04/12/21
 * @author (REMOVED)
 */
public class SavesMenu {
    private static final int NUMBER_SAVES_PER_PAGE = 11;
    private static final int SAVE_TWO_INDEX = 1;
    private static final int SAVE_THREE_INDEX = 2;
    private static final int SAVE_FOUR_INDEX = 3;
    private static final int SAVE_FIVE_INDEX = 4;
    private static final int SAVE_SIX_INDEX = 5;
    private static final int SAVE_SEVEN_INDEX = 6;
    private static final int SAVE_EIGHT_INDEX = 7;
    private static final int SAVE_NINE_INDEX = 8;
    private static final int SAVE_TEN_INDEX = 9;
    private static final int SAVE_ELVEN_INDEX = 10;
    private static final int SAVE_ELEVEN_INDEX = 11;
	@FXML
    private Text currentSavesPageNumberText;
    @FXML
    private Text profileName12;
    @FXML
    private Text profileName41;
    @FXML
    private Text saveName1;
    @FXML
    private Text saveName10;
    @FXML
    private Text saveName11;
    @FXML
    private Text saveName2;
    @FXML
    private Text saveName3;
    @FXML
    private Text saveName4;
    @FXML
    private Text saveName5;
    @FXML
    private Text saveName6;
    @FXML
    private Text saveName7;
    @FXML
    private Text saveName8;
    @FXML
    private Text saveName9;
   
    private int savesMenuPageNumber = 0;
    private Game game;

    /**
     * Stores the current game so data can be taken from it.
     */
    public void storeGame(Game game) {
    	this.game = game;
    }

    /**
     * Displays information for each save and allows for
     * multiple pages of saves.
     */
    public void displaySaves() {
        currentSavesPageNumberText.setText("" + savesMenuPageNumber);
        resetSavesText();
        ArrayList<String> currentSaves = game.getDataManager().getAllSaveFiles();

        int numberOfSaves = game.getDataManager().getAllSaveFiles().size();
        if (numberOfSaves > 0) {
            saveName1.setText("" + currentSaves.get(this.savesMenuPageNumber * NUMBER_SAVES_PER_PAGE));
        }
        if (numberOfSaves > SAVE_TWO_INDEX) {
            saveName2.setText("" + currentSaves.get(
                    this.savesMenuPageNumber * NUMBER_SAVES_PER_PAGE + SAVE_TWO_INDEX));
        }
        if (numberOfSaves > SAVE_THREE_INDEX) {
            saveName3.setText("" + currentSaves.get(
                    this.savesMenuPageNumber * NUMBER_SAVES_PER_PAGE + SAVE_THREE_INDEX));
        }
        if (numberOfSaves > SAVE_FOUR_INDEX) {
            saveName4.setText("" + currentSaves.get(
                    this.savesMenuPageNumber * NUMBER_SAVES_PER_PAGE + SAVE_FOUR_INDEX));
        }
        if (numberOfSaves > SAVE_FIVE_INDEX) {
            saveName5.setText("" + currentSaves.get(
                    this.savesMenuPageNumber * NUMBER_SAVES_PER_PAGE + SAVE_FIVE_INDEX));
        }
        if (numberOfSaves > SAVE_SIX_INDEX) {
            saveName6.setText("" + currentSaves.get(
                    this.savesMenuPageNumber * NUMBER_SAVES_PER_PAGE + SAVE_SIX_INDEX));
        }
        if (numberOfSaves > SAVE_SEVEN_INDEX) {
            saveName7.setText("" + currentSaves.get(
                    this.savesMenuPageNumber * NUMBER_SAVES_PER_PAGE + SAVE_SEVEN_INDEX));
        }
        if (numberOfSaves >  SAVE_EIGHT_INDEX) {
            saveName8.setText("" + currentSaves.get(
                    this.savesMenuPageNumber * NUMBER_SAVES_PER_PAGE + SAVE_EIGHT_INDEX));
        }
        if (numberOfSaves > SAVE_NINE_INDEX) {
            saveName9.setText("" + currentSaves.get(
                    this.savesMenuPageNumber * NUMBER_SAVES_PER_PAGE + SAVE_NINE_INDEX));
        }
        if (numberOfSaves > SAVE_TEN_INDEX) {
            saveName10.setText("" + currentSaves.get(
                    this.savesMenuPageNumber * NUMBER_SAVES_PER_PAGE + SAVE_TEN_INDEX));
        }
        if (numberOfSaves > SAVE_ELEVEN_INDEX) {
            saveName11.setText("" + currentSaves.get(
                    this.savesMenuPageNumber * NUMBER_SAVES_PER_PAGE + SAVE_ELEVEN_INDEX));
        }

    }

    /**
     * Switches to the main menu.
     * @param event button press.
     */
    @FXML
    private void backButton(ActionEvent event) {
    	game.getGraphics().getStage().setScene(game.getGraphics().getMenuScene());
    }
    
    /**
     * Loads save 1.
     * @param event The load button for the 1st save on the page.
     */
    @FXML
    private void loadSave1(ActionEvent event) {
    	String save1 = game.getDataManager().getAllSaveFiles().get(this.savesMenuPageNumber * NUMBER_SAVES_PER_PAGE);
    	game.onLevelStart(save1);
    }
    
    /**
     * Loads save 2.
     * @param event The load button for the 2nd save on the page.
     */
    @FXML
    private void loadSave2(ActionEvent event) {
     	String save2 = game.getDataManager().getAllSaveFiles().get(
     			this.savesMenuPageNumber * NUMBER_SAVES_PER_PAGE + SAVE_TWO_INDEX);
     	game.onLevelStart(save2);
    }
    
    /**
     * Loads save 3.
     * @param event The load button for the 3rd save on the page.
     */
    @FXML
    private void loadSave3(ActionEvent event) {
      	String save3 = game.getDataManager().getAllSaveFiles().get(
      			this.savesMenuPageNumber * NUMBER_SAVES_PER_PAGE + SAVE_THREE_INDEX);
    	game.onLevelStart(save3);
    }

    /**
     * Loads save 4.
     * @param event The load button for the 4th save on the page.
     */
    @FXML
    private void loadSave4(ActionEvent event) {
      	String save4 = game.getDataManager().getAllSaveFiles().get(
      			this.savesMenuPageNumber * NUMBER_SAVES_PER_PAGE + SAVE_FOUR_INDEX);
      	game.onLevelStart(save4);
    }

    /**
     * Loads save 5.
     * @param event The load button for the 5th save on the page.
     */
    @FXML
    private void loadSave5(ActionEvent event) {
      	String save5 = game.getDataManager().getAllSaveFiles().get(
      			this.savesMenuPageNumber * NUMBER_SAVES_PER_PAGE + SAVE_FIVE_INDEX);
      	game.onLevelStart(save5);
    }

    /**
     * Loads save 6.
     * @param event The load button for the 6th save on the page.
     */
    @FXML
    private void loadSave6(ActionEvent event) {
      	String save6 = game.getDataManager().getAllSaveFiles().get(
      			this.savesMenuPageNumber * NUMBER_SAVES_PER_PAGE + SAVE_SIX_INDEX);
      	game.onLevelStart(save6);
    }

    /**
     * Loads save 7.
     * @param event The load button for the 7th save on the page.
     */
    @FXML
    private void loadSave7(ActionEvent event) {
      	String save7 = game.getDataManager().getAllSaveFiles().get(
      			this.savesMenuPageNumber * NUMBER_SAVES_PER_PAGE + SAVE_SEVEN_INDEX);
      	game.onLevelStart(save7);
    }

    /**
     * Loads save 8.
     * @param event The load button for the 8th save on the page.
     */
    @FXML
    private void loadSave8(ActionEvent event) {
      	String save8 = game.getDataManager().getAllSaveFiles().get(
      			this.savesMenuPageNumber * NUMBER_SAVES_PER_PAGE + SAVE_EIGHT_INDEX);
      	game.onLevelStart(save8);
    }

    /**
     * Loads save 9.
     * @param event The load button for the 9th save on the page.
     */
    @FXML
    private void loadSave9(ActionEvent event) {
      	String save9 = game.getDataManager().getAllSaveFiles().get(
      			this.savesMenuPageNumber * NUMBER_SAVES_PER_PAGE + SAVE_NINE_INDEX);
      	game.onLevelStart(save9);
    }

    /**
     * Loads save 10.
     * @param event The load button for the 10th save on the page.
     */
    @FXML
    private void loadSave10(ActionEvent event) {
        String save10 = game.getDataManager().getAllSaveFiles()
                .get(this.savesMenuPageNumber * NUMBER_SAVES_PER_PAGE + SAVE_TEN_INDEX);
        game.onLevelStart(save10);
    }


    /**
     * Loads save 11.
     * @param event The load button for the 11th save on the page.
     */
    @FXML
    private void loadSave11(ActionEvent event) {
        String save11 = game.getDataManager().getAllSaveFiles().get(
                this.savesMenuPageNumber * NUMBER_SAVES_PER_PAGE + SAVE_ELVEN_INDEX);
        game.onLevelStart(save11);
    }
    
    /**
     * Clear all the text that shows save data.
     */
    private void resetSavesText() {
	   saveName1.setText("");
	   saveName2.setText("");
	   saveName3.setText("");
	   saveName4.setText("");
	   saveName5.setText("");
	   saveName6.setText("");
	   saveName7.setText("");
	   saveName8.setText("");
	   saveName9.setText("");
	   saveName10.setText("");
	   saveName11.setText("");
    }

    /**
     * Switches the saves page to the next page.
     * @param event The next page button being pressed.
     */
    @FXML
    private void nextSavesPage(ActionEvent event) {
    	this.savesMenuPageNumber++;
    	currentSavesPageNumberText.setText("" + savesMenuPageNumber);
    	displaySaves();
    }

    /**
     * Switches the saves page to the previous page.
     * @param event The previous button being pressed.
     */
    @FXML
    private void previousSavesPage(ActionEvent event) {
    	if (this.savesMenuPageNumber > 0) {
    		this.savesMenuPageNumber--;
    	}
    	
    	currentSavesPageNumberText.setText("" + savesMenuPageNumber);
    	displaySaves();
    }
    
    /**
     * Creates a new save.
     * @param event The save button being pressed.
     */
    @FXML
    private void createNewSave(ActionEvent event) {
    	game.saveCurrentLevel();
        displaySaves();
    }
}
