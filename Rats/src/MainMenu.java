import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * MainMenu.java
 * 
 * MainMenu is the main menu gui which is used to load other menus.
 * It also displays the high scores and MOTD.
 * 
 * @version 1.8
 * @since 02/12/2021
 * Last modified 04/12/21
 * @author (REMOVED)
 */
public class MainMenu {
    private static final int PROFILE_INDEX_ZERO = 0;
    private static final int PROFILE_INDEX_ONE = 1;
    private static final int PROFILE_INDEX_TWO = 2;
    private static final int PROFILE_INDEX_THREE = 3;
    private static final int PROFILE_INDEX_FOUR = 4;
    private static final int PROFILE_INDEX_FIVE = 5;
    private static final int PROFILE_INDEX_SIX = 6;
    private static final int PROFILE_INDEX_SEVEN = 7;
    private static final int PROFILE_INDEX_EIGHT = 8;
    private static final int PROFILE_INDEX_NINE = 9;
    private static final String CROWN_IMAGE_NAME = "Crown";
    private static final String TWO = "2";
    private static final String THREE = "3";
    private static final String FOUR = "4";
    private static final String FIVE = "5";
    private static final String SIX = "6";
    private static final String SEVEN = "7";
    private static final String EIGHT = "8";
    private static final String NINE = "9";
    private static final String TEN = "10";
    private static final String BACKGROUND_MENU = "MenuBack";

	@FXML
    private ImageView backgroundImageView;
    @FXML
    private Text currentLevelHighScoreText;
    @FXML
    private ImageView goldCrownImageView;
    @FXML
    private Text motdText;
    @FXML
    private Text num10PlayerText;
    @FXML
    private Text num1PlayerText;
    @FXML
    private Text num2PlayerText;
    @FXML
    private Text num3PlayerText;
    @FXML
    private Text num4PlayerText;
    @FXML
    private Text num5PlayerText;
    @FXML
    private Text num6PlayerText;
    @FXML
    private Text num7PlayerText;
    @FXML
    private Text num8PlayerText;
    @FXML
    private Text num9PlayerText;
    @FXML
    private Text position10Text;
    @FXML
    private Text position2Text;
    @FXML
    private Text position3Text;
    @FXML
    private Text position4Text;
    @FXML
    private Text position4Text1;
    @FXML
    private Text position5Text;
    @FXML
    private Text position6Text;
    @FXML
    private Text position7Text;
    @FXML
    private Text position8Text;
    @FXML
    private Text position9Text;
    @FXML
    private Text score10Text;
    @FXML
    private Text score1Text;
    @FXML
    private Text score2Text;
    @FXML
    private Text score3Text;
    @FXML
    private Text score4Text;
    @FXML
    private Text score5Text;
    @FXML
    private Text score6Text;
    @FXML
    private Text score7Text;
    @FXML
    private Text score8Text;
    @FXML
    private Text score9Text;

    /**
     * Current level high scores being shown.
     */
    private int currentLevelHighScore = 1;
    private Game game;
    private Graphics graphics;

    /**
     * Stores the current game so data can be taken from it.
     */
    public void storeGame(Game game) {
    	this.game = game;
    }
    
    /**
     * Stores the graphics so images can be taken from it.
     */
    public void storeGraphics(Graphics graphics) {
    	this.graphics = graphics;
    }
    
    /**
     * Sets the main menu background image.
     * @param graphics The graphics needed to draw the background.
     */
    public void setMainMenuBackground(Graphics graphics) {
    	backgroundImageView.setImage(graphics.getImage(BACKGROUND_MENU));
    }

    /**
     * Switches to the save menu.
     * @param event The button to switch to the save menu is pressed.
     */
    @FXML
    void switchToSaves(ActionEvent event) {
    	game.getGraphics().getStage().setScene(game.getGraphics().getSavesMenuScene());
    }

    /**
     * Switches to the level menu.
     * @param event The button to switch to the level menu is pressed.
     */
    @FXML
    void switchToLevels(ActionEvent event) {
    	if (game.getProfiles().getCurrentProfile() == null) {
    		return;
    	}
    	game.getGraphics().getStage().setScene(game.getGraphics().getLevelsMenuScene());
    }

    /**
     * Switches to the profiles' menu.
     * @param event The button to switch to the profile menu is pressed.
     */
    @FXML
    void switchToProfiles(ActionEvent event) {
    	game.getGraphics().getStage().setScene(game.getGraphics().getProfilesMenuScene());
    }

    /**
     * Displays the MOTD.
     */
    public void setMotd() {
    	motdText.setText(new MOTD().get());
    }

    /**
     * Switches which level high scores are being displayed.
     * @param event The button to see the next page of high scores is pressed.
     */
    @FXML
    void nextHighScoreLevel(ActionEvent event) {
    	if (this.currentLevelHighScore < PROFILE_INDEX_FIVE) {
    		this.currentLevelHighScore++;
    		currentLevelHighScoreText.setText("" + this.currentLevelHighScore);
    	}
    	displayHighScores();
    }

    /**
     * Switches which level high scores are being displayed.
     * @param event The button to see the previous page of high scores is pressed.
     */
    @FXML
    void previousHighScoreLevel(ActionEvent event) {
    	if (this.currentLevelHighScore > PROFILE_INDEX_ONE) {
    		this.currentLevelHighScore--;
    		currentLevelHighScoreText.setText("" + this.currentLevelHighScore);
    	}
    	displayHighScores();
    }
   
    /**
     * Displays the high scores of the current level selected.
     */
    public void displayHighScores() {
    	resetScoreText();
    	currentLevelHighScoreText.setText("" + this.currentLevelHighScore);

    	if (game.getHighScores().getLevelPlacements(currentLevelHighScore).size() > PROFILE_INDEX_ZERO) {
    		this.goldCrownImageView.setImage(graphics.getImage(CROWN_IMAGE_NAME));
    		this.num1PlayerText.setText("" + game.getHighScores().getLevelPlacements(currentLevelHighScore).get(
    				PROFILE_INDEX_ZERO).getProfile().getPlayerName());
    		this.score1Text.setText("" + game.getHighScores().getLevelPlacements(currentLevelHighScore).get(
    				PROFILE_INDEX_ZERO).getScore());
    	}
    	if (game.getHighScores().getLevelPlacements(currentLevelHighScore).size() > PROFILE_INDEX_ONE) {
    		this.position2Text.setText(TWO);
    		this.num2PlayerText.setText("" + game.getHighScores().getLevelPlacements(currentLevelHighScore).get(
    				PROFILE_INDEX_ONE).getProfile().getPlayerName());
    		this.score2Text.setText("" + game.getHighScores().getLevelPlacements(currentLevelHighScore).get(
    				PROFILE_INDEX_ONE).getScore());
    	}
      	if (game.getHighScores().getLevelPlacements(currentLevelHighScore).size() > PROFILE_INDEX_TWO) {
      		this.position3Text.setText(THREE);
    		this.num3PlayerText.setText("" +game.getHighScores().getLevelPlacements(currentLevelHighScore).get(
    				PROFILE_INDEX_TWO).getProfile().getPlayerName());
    		this.score3Text.setText("" + game.getHighScores().getLevelPlacements(currentLevelHighScore).get(
    				PROFILE_INDEX_TWO).getScore());
    	}
      	if (game.getHighScores().getLevelPlacements(currentLevelHighScore).size() > PROFILE_INDEX_THREE) {
    		this.position4Text.setText(FOUR);
    		this.num4PlayerText.setText("" + game.getHighScores().getLevelPlacements(currentLevelHighScore).get(
    				PROFILE_INDEX_THREE).getProfile().getPlayerName());
    		this.score4Text.setText("" + game.getHighScores().getLevelPlacements(currentLevelHighScore).get(
    				PROFILE_INDEX_THREE).getScore());
    	}
      	if (game.getHighScores().getLevelPlacements(currentLevelHighScore).size() > PROFILE_INDEX_FOUR) {
      		this.position5Text.setText(FIVE);
    		this.num5PlayerText.setText("" + game.getHighScores().getLevelPlacements(currentLevelHighScore).get(
    				PROFILE_INDEX_FOUR).getProfile().getPlayerName());
    		this.score5Text.setText("" + game.getHighScores().getLevelPlacements(currentLevelHighScore).get(
    				PROFILE_INDEX_FOUR).getScore());
    	}
      	if (game.getHighScores().getLevelPlacements(currentLevelHighScore).size() > PROFILE_INDEX_FIVE) {
      		this.position6Text.setText(SIX);
    		this.num6PlayerText.setText("" + game.getHighScores().getLevelPlacements(currentLevelHighScore).get(
    				PROFILE_INDEX_FIVE).getProfile().getPlayerName());
    		this.score6Text.setText("" + game.getHighScores().getLevelPlacements(currentLevelHighScore).get(
    				PROFILE_INDEX_FIVE).getScore());
    	}
      	if (game.getHighScores().getLevelPlacements(currentLevelHighScore).size() > PROFILE_INDEX_SIX) {
      		this.position7Text.setText(SEVEN);
    		this.num7PlayerText.setText("" + game.getHighScores().getLevelPlacements(currentLevelHighScore).get(
    				PROFILE_INDEX_SIX).getProfile().getPlayerName());
    		this.score7Text.setText("" + game.getHighScores().getLevelPlacements(currentLevelHighScore).get(
    				PROFILE_INDEX_SIX).getScore());
    	}
      	if (game.getHighScores().getLevelPlacements(currentLevelHighScore).size() > PROFILE_INDEX_SEVEN) {
      		this.position8Text.setText(EIGHT);
    		this.num8PlayerText.setText("" + game.getHighScores().getLevelPlacements(currentLevelHighScore).get(
    				PROFILE_INDEX_SEVEN).getProfile().getPlayerName());
    		this.score8Text.setText("" + game.getHighScores().getLevelPlacements(currentLevelHighScore).get(
    				PROFILE_INDEX_SEVEN).getScore());
    	}
      	if (game.getHighScores().getLevelPlacements(currentLevelHighScore).size() > PROFILE_INDEX_EIGHT) {
      		this.position9Text.setText(NINE);
    		this.num9PlayerText.setText("" + game.getHighScores().getLevelPlacements(currentLevelHighScore).get(
    				PROFILE_INDEX_EIGHT).getProfile().getPlayerName());
    		this.score9Text.setText("" + game.getHighScores().getLevelPlacements(currentLevelHighScore).get(
    				PROFILE_INDEX_EIGHT).getScore());
    	}
      	if (game.getHighScores().getLevelPlacements(currentLevelHighScore).size() > PROFILE_INDEX_NINE) {
      		this.position10Text.setText(TEN);
    		this.num10PlayerText.setText("" + game.getHighScores().getLevelPlacements(currentLevelHighScore).get(
    				PROFILE_INDEX_NINE).getProfile().getPlayerName());
    		this.score10Text.setText("" + game.getHighScores().getLevelPlacements(currentLevelHighScore).get(
    				PROFILE_INDEX_NINE).getScore());
    	}
    }
    
    /**
     * Clears the high score text.
     */
    private void resetScoreText() {
       this.goldCrownImageView.setImage(null);
       num1PlayerText.setText("");
       score1Text.setText("");
       
	   position2Text.setText("");
	   score2Text.setText("");
	   num2PlayerText.setText("");
	   
	   position3Text.setText("");
	   score3Text.setText("");
	   num3PlayerText.setText("");
	   
	   position4Text.setText("");
	   score4Text.setText("");
	   num4PlayerText.setText("");
	   
	   position5Text.setText("");
	   score5Text.setText("");
	   num5PlayerText.setText("");
	   
	   position6Text.setText("");
	   score6Text.setText("");
	   num6PlayerText.setText("");
	   
	   position7Text.setText("");
	   score7Text.setText("");
	   num7PlayerText.setText("");
	   
	   position8Text.setText("");
	   score8Text.setText("");
	   num8PlayerText.setText("");
	   
	   position9Text.setText("");
	   score9Text.setText("");
	   num9PlayerText.setText("");
	   
	   position10Text.setText("");
	   score10Text.setText("");
	   num10PlayerText.setText("");
    }
}
