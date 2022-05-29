import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

/**
 * ProfilesMenu.java
 *
 * ProfilesMenu is the controller for the profile menu. It's used to
 * select a profile or delete a profile.
 *
 * @version 1.8
 * @since 02/12/2021
 * Last modified 05/12/21
 * @author (REMOVED)
 */
public class ProfilesMenu {
	private static final int NUMBER_PROFILES_PER_PAGE = 11;
	private static final int PROFILE_TWO_INDEX = 1;
	private static final int PROFILE_THREE_INDEX = 2;
	private static final int PROFILE_FOUR_INDEX = 3;
	private static final int PROFILE_FIVE_INDEX = 4;
	private static final int PROFILE_SIX_INDEX = 5;
	private static final int PROFILE_SEVEN_INDEX = 6;
	private static final int PROFILE_EIGHT_INDEX = 7;
	private static final int PROFILE_NINE_INDEX = 8;
	private static final int PROFILE_TEN_INDEX = 9;
	private static final int PROFILE_ELEVEN_INDEX = 10;
	@FXML
	private Text currentMaxLevelText;
	@FXML
	private Text currentProfileText;
    @FXML
    private Text currentPageNumberText;
	@FXML
	private Text maxLevel1;
	@FXML
	private Text maxLevel10;
	@FXML
	private Text maxLevel11;
	@FXML
	private Text maxLevel2;
	@FXML
	private Text maxLevel3;
	@FXML
	private Text maxLevel4;
	@FXML
	private Text maxLevel5;
	@FXML
	private Text maxLevel6;
	@FXML
	private Text maxLevel7;
	@FXML
	private Text maxLevel8;
	@FXML
	private Text maxLevel9;
	@FXML
	private TextArea newProfileNameTextArea;
	@FXML
	private Text profileName1;
	@FXML
	private Text profileName10;
	@FXML
	private Text profileName11;
	@FXML
	private Text profileName12;
	@FXML
	private Text profileName2;
	@FXML
	private Text profileName3;
	@FXML
	private Text profileName4;
	@FXML
	private Text profileName41;
	@FXML
	private Text profileName5;
	@FXML
	private Text profileName6;
	@FXML
	private Text profileName7;
	@FXML
	private Text profileName8;
	@FXML
	private Text profileName9;

	private Game game;
	private int profileMenuPageNumber = 0;

	/**
	 * Stores the current game so data can be taken from it.
	 */
	public void storeGame(Game game) {
		this.game = game;
	}

	/**
	 * Displays profile information.
	 */
	public void displayProfiles() {
		currentPageNumberText.setText("" + profileMenuPageNumber);
		resetProfileText();
		ArrayList<Profile> currentProfiles = game.getProfiles().getAllProfiles();

		if (game.getProfiles().getCurrentProfile() == null) {
			currentMaxLevelText.setText("");
			currentProfileText.setText("");
		} else {
			currentMaxLevelText.setText("" + game.getProfiles().getCurrentProfile().getMaxLevel());
			currentProfileText.setText("" + game.getProfiles().getCurrentProfile().getPlayerName());
		}

		int numberOfProfiles = currentProfiles.size();
		if (numberOfProfiles > 0) {
			profileName1.setText("" + currentProfiles.get(
					this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE).getPlayerName());
			maxLevel1.setText("" + currentProfiles.get(
					this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE).getMaxLevel());
		}
		if (numberOfProfiles > PROFILE_TWO_INDEX) {
			profileName2.setText("" + currentProfiles.get(
					this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE
							+ PROFILE_TWO_INDEX).getPlayerName());
			maxLevel2.setText("" + currentProfiles.get(
					this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE
							+ PROFILE_TWO_INDEX).getMaxLevel());
		}
		if (numberOfProfiles > PROFILE_THREE_INDEX) {
			profileName3.setText("" + currentProfiles.get(
					this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE
							+ PROFILE_THREE_INDEX).getPlayerName());
			maxLevel3.setText("" + currentProfiles.get(
					this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE
							+ PROFILE_THREE_INDEX).getMaxLevel());
		}
		if (numberOfProfiles > PROFILE_FOUR_INDEX) {
			profileName4.setText("" + currentProfiles.get(
					this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE
							+ PROFILE_FOUR_INDEX).getPlayerName());
			maxLevel4.setText("" + currentProfiles.get(
					this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE
							+ PROFILE_FOUR_INDEX).getMaxLevel());
		}
		if (numberOfProfiles > PROFILE_FIVE_INDEX) {
			profileName5.setText("" + currentProfiles.get(
					this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE
							+ PROFILE_FIVE_INDEX).getPlayerName());
			maxLevel5.setText("" + currentProfiles.get(
					this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE
							+ PROFILE_FIVE_INDEX).getMaxLevel());
		}
		if (numberOfProfiles > PROFILE_SIX_INDEX) {
			profileName6.setText("" + currentProfiles.get(
					this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE
							+ PROFILE_SIX_INDEX).getPlayerName());
			maxLevel6.setText("" + currentProfiles.get(
					this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE
							+ PROFILE_SIX_INDEX).getMaxLevel());
		}
		if (numberOfProfiles > PROFILE_SEVEN_INDEX) {
			profileName7.setText("" + currentProfiles.get(
					this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE
							+ PROFILE_SEVEN_INDEX).getPlayerName());
			maxLevel7.setText("" + currentProfiles.get(
					this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE
							+ PROFILE_SEVEN_INDEX).getMaxLevel());
		}
		if (numberOfProfiles > PROFILE_EIGHT_INDEX) {
			profileName8.setText("" + currentProfiles.get(
					this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE
							+ PROFILE_EIGHT_INDEX).getPlayerName());
			maxLevel8.setText("" + currentProfiles.get(
					this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE
							+ PROFILE_EIGHT_INDEX).getMaxLevel());
		}
		if (numberOfProfiles > PROFILE_NINE_INDEX) {
			profileName9.setText("" + currentProfiles.get(
					this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE
							+ PROFILE_NINE_INDEX).getPlayerName());
			maxLevel9.setText("" + currentProfiles.get(
					this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE
							+ PROFILE_NINE_INDEX).getMaxLevel());
		}
		if (numberOfProfiles > PROFILE_TEN_INDEX) {
			profileName10.setText("" + currentProfiles.get(
					this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE
							+ PROFILE_TEN_INDEX).getPlayerName());
			maxLevel10.setText("" + currentProfiles.get(
					this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE
							+ PROFILE_TEN_INDEX).getMaxLevel());
		}
		if (numberOfProfiles > PROFILE_ELEVEN_INDEX) {
			profileName11.setText("" + currentProfiles.get(
					this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE
							+ PROFILE_ELEVEN_INDEX).getPlayerName());
			maxLevel11.setText("" + currentProfiles.get(
					this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE
							+ PROFILE_ELEVEN_INDEX).getMaxLevel());
		}

	}

	/**
	 * Switches to the main menu.
	 * @param event The back button is pressed.
	 */
	@FXML
	private void backButton(ActionEvent event) {
		game.getGraphics().getStage().setScene(game.getGraphics().getMenuScene());
	}

	/**
	 * Deletes profile 1 where profile 1 is the first profile displayed.
	 * @param event The delete button for the 1st Profile is pressed.
	 */
	@FXML
	private void delProfile1(ActionEvent event) {
		ArrayList<Profile> currentProfiles = game.getProfiles().getAllProfiles();
		this.game.getProfiles().removeProfile(currentProfiles.get(
				this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE));
		displayProfiles();
	}

	/**
	 * Deletes profile 2 where profile 2 is the second profile displayed.
	 * @param event The delete button for the 2nd Profile is pressed.
	 */
	@FXML
	private void delProfile2(ActionEvent event) {
		ArrayList<Profile> currentProfiles = game.getProfiles().getAllProfiles();
		this.game.getProfiles().removeProfile(currentProfiles.get(
				this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE + PROFILE_TWO_INDEX));
		displayProfiles();
	}

	/**
	 * Deletes profile 3 where profile 3 is the third profile displayed.
	 * @param event The delete button for the 3rd Profile is pressed.
	 */
	@FXML
	private void delProfile3(ActionEvent event) {
		ArrayList<Profile> currentProfiles = game.getProfiles().getAllProfiles();
		this.game.getProfiles().removeProfile(currentProfiles.get(
				this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE + PROFILE_THREE_INDEX));
		displayProfiles();
	}

	/**
	 * Deletes profile 4 where profile 4 is the fourth profile displayed.
	 * @param event The delete button for the 4th Profile is pressed.
	 */
	@FXML
	private void delProfile4(ActionEvent event) {
		ArrayList<Profile> currentProfiles = game.getProfiles().getAllProfiles();
		this.game.getProfiles().removeProfile(currentProfiles.get(
				this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE + PROFILE_FOUR_INDEX));
		displayProfiles();
	}

	/**
	 * Deletes profile 5 where profile 5 is the fifth profile displayed.
	 * @param event The delete button for the 5th Profile is pressed.
	 */
	@FXML
	private void delProfile5(ActionEvent event) {
		ArrayList<Profile> currentProfiles = game.getProfiles().getAllProfiles();
		this.game.getProfiles().removeProfile(currentProfiles.get(
				this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE + PROFILE_FIVE_INDEX));
		displayProfiles();
	}

	/**
	 * Deletes profile 6 where profile 6 is the sixth profile displayed.
	 * @param event The delete button for the 6th Profile is pressed.
	 */
	@FXML
	private void delProfile6(ActionEvent event) {
		ArrayList<Profile> currentProfiles = game.getProfiles().getAllProfiles();
		this.game.getProfiles().removeProfile(currentProfiles.get(
				this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE + PROFILE_SIX_INDEX));
		displayProfiles();
	}

	/**
	 * Deletes profile 7 where profile 7 is the seventh profile displayed.
	 * @param event The delete button for the 7th Profile is pressed.
	 */
	@FXML
	private void delProfile7(ActionEvent event) {
		ArrayList<Profile> currentProfiles = game.getProfiles().getAllProfiles();
		this.game.getProfiles().removeProfile(currentProfiles.get(
				this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE + PROFILE_SEVEN_INDEX));
		displayProfiles();
	}

	/**
	 * Deletes profile 8 where profile 8 is the eighth profile displayed.
	 * @param event The delete button for the 8th Profile is pressed.
	 */
	@FXML
	private void delProfile8(ActionEvent event) {
		ArrayList<Profile> currentProfiles = game.getProfiles().getAllProfiles();
		this.game.getProfiles().removeProfile(currentProfiles.get(
				this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE + PROFILE_EIGHT_INDEX));
		displayProfiles();
	}

	/**
	 * Deletes profile 9 where profile 9 is the ninth profile displayed.
	 * @param event The delete button for the 9th Profile is pressed.
	 */
	@FXML
	private void delProfile9(ActionEvent event) {
		ArrayList<Profile> currentProfiles = game.getProfiles().getAllProfiles();
		this.game.getProfiles().removeProfile(currentProfiles.get(
				this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE + PROFILE_NINE_INDEX));
		displayProfiles();
	}

	/**
	 * Deletes profile 10 where profile 10 is the second profile displayed.
	 * @param event The delete button for the 11th Profile is pressed.
	 */
	@FXML
	private void delProfile10(ActionEvent event) {
		ArrayList<Profile> currentProfiles = game.getProfiles().getAllProfiles();
		this.game.getProfiles().removeProfile(currentProfiles.get(
				this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE + PROFILE_TEN_INDEX));
		displayProfiles();
	}

	/**
	 * Deletes profile 11 where profile 11 is the elventh profile displayed.
	 * @param event The delete button for the 11th Profile is pressed.
	 */
	@FXML
	private void delProfile11(ActionEvent event) {
		ArrayList<Profile> currentProfiles = game.getProfiles().getAllProfiles();
		this.game.getProfiles().removeProfile(currentProfiles.get(
				this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE + PROFILE_ELEVEN_INDEX));
		displayProfiles();
	}

	/**
	 * Sets profile 1 as the current profile.
	 * @param event The load button for the 1st Profile is pressed.
	 */
	@FXML
	private void loadProfile1(ActionEvent event) {
		ArrayList<Profile> currentProfiles = game.getProfiles().getAllProfiles();
		this.game.getProfiles().setCurrentProfile(currentProfiles.get(
				this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE));
		displayProfiles();
	}

	/**
	 * Sets profile 2 as the current profile.
	 * @param event The load button for the 2nd Profile is pressed.
	 */
	@FXML
	private void loadProfile2(ActionEvent event) {
		ArrayList<Profile> currentProfiles = game.getProfiles().getAllProfiles();
		this.game.getProfiles().setCurrentProfile(currentProfiles.get(
				this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE + 1));
		displayProfiles();
	}

	/**
	 * Sets profile 3 as the current profile.
	 * @param event The load button for the 3rd Profile is pressed.
	 */
	@FXML
	private void loadProfile3(ActionEvent event) {
		ArrayList<Profile> currentProfiles = game.getProfiles().getAllProfiles();
		this.game.getProfiles().setCurrentProfile(currentProfiles.get(
				this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE + PROFILE_TWO_INDEX));
		displayProfiles();
	}

	/**
	 * Sets profile 4 as the current profile.
	 * @param event The load button for the 4th Profile is pressed.
	 */
	@FXML
	private void loadProfile4(ActionEvent event) {
		ArrayList<Profile> currentProfiles = game.getProfiles().getAllProfiles();
		this.game.getProfiles().setCurrentProfile(currentProfiles.get(
				this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE + PROFILE_FOUR_INDEX));
		displayProfiles();
	}

	/**
	 * Sets profile 5 as the current profile.
	 * @param event The load button for the 5th Profile is pressed.
	 */
	@FXML
	private void loadProfile5(ActionEvent event) {
		ArrayList<Profile> currentProfiles = game.getProfiles().getAllProfiles();
		this.game.getProfiles().setCurrentProfile(currentProfiles.get(
				this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE + PROFILE_FIVE_INDEX));
		displayProfiles();
	}

	/**
	 * Sets profile 6 as the current profile.
	 * @param event The load button for the 6th Profile is pressed.
	 */
	@FXML
	private void loadProfile6(ActionEvent event) {
		ArrayList<Profile> currentProfiles = game.getProfiles().getAllProfiles();
		this.game.getProfiles().setCurrentProfile(currentProfiles.get(
				this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE + PROFILE_SIX_INDEX));
		displayProfiles();
	}

	/**
	 * Sets profile 7 as the current profile.
	 * @param event The load button for the 7th Profile is pressed.
	 */
	@FXML
	private void loadProfile7(ActionEvent event) {
		ArrayList<Profile> currentProfiles = game.getProfiles().getAllProfiles();
		this.game.getProfiles().setCurrentProfile(currentProfiles.get(
				this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE + PROFILE_SEVEN_INDEX));
		displayProfiles();
	}

	/**
	 * Sets profile 8 as the current profile.
	 * @param event The load button for the 8th Profile is pressed.
	 */
	@FXML
	private void loadProfile8(ActionEvent event) {
		ArrayList<Profile> currentProfiles = game.getProfiles().getAllProfiles();
		this.game.getProfiles().setCurrentProfile(currentProfiles.get(
				this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE + PROFILE_EIGHT_INDEX));
		displayProfiles();
	}

	/**
	 * Sets profile 9 as the current profile.
	 * @param event The load button for the 9th Profile is pressed.
	 */
	@FXML
	private void loadProfile9(ActionEvent event) {
		ArrayList<Profile> currentProfiles = game.getProfiles().getAllProfiles();
		this.game.getProfiles().setCurrentProfile(currentProfiles.get(
				this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE + PROFILE_NINE_INDEX));
		displayProfiles();
	}

	/**
	 * Sets profile 10 as the current profile.
	 * @param event The load button for the 10th Profile is pressed.
	 */
	@FXML
	private void loadProfile10(ActionEvent event) {
		ArrayList<Profile> currentProfiles = game.getProfiles().getAllProfiles();
		this.game.getProfiles().setCurrentProfile(currentProfiles.get(
				this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE + PROFILE_TEN_INDEX));
		displayProfiles();
	}

	/**
	 * Sets profile 11 as the current profile.
	 * @param event The load button for the 11th Profile is pressed.
	 */
	@FXML
	private void loadProfile11(ActionEvent event) {
		ArrayList<Profile> currentProfiles = game.getProfiles().getAllProfiles();
		this.game.getProfiles().setCurrentProfile(currentProfiles.get(
				this.profileMenuPageNumber * NUMBER_PROFILES_PER_PAGE + PROFILE_ELEVEN_INDEX));
		displayProfiles();
	}

	/**
	 * Creates a new profile.
	 * @param event The create profile button is pressed.
	 */
	@FXML
	private void createProfile(ActionEvent event) {
		String profileName = newProfileNameTextArea.getText();
		if (!profileName.isBlank()) {
			profileName = profileName.replaceAll("\\s", "");
			newProfileNameTextArea.setText("");
			game.getProfiles().newProfile(profileName);
			displayProfiles();
		}
	}

	/**
	 * Switches to the next page of profiles.
	 * @param event The next page button is pressed.
	 */
	@FXML
	private void nextProfilePage(ActionEvent event) {
		this.profileMenuPageNumber++;
		currentPageNumberText.setText("" + profileMenuPageNumber);
		displayProfiles();
	}

	/**
	 * Switches to the previous page of profiles.
	 * @param event The previous button is pressed.
	 */
	@FXML
	private void previousProfilePage(ActionEvent event) {
		if (this.profileMenuPageNumber > 0) {
			this.profileMenuPageNumber--;
		}

		currentPageNumberText.setText("" + profileMenuPageNumber);
		displayProfiles();
	}

	/**
	 * Clears profile information text.
	 */
	private void resetProfileText() {
		profileName1.setText("");
		maxLevel1.setText("");
		profileName2.setText("");
		maxLevel2.setText("");
		profileName3.setText("");
		maxLevel3.setText("");
		profileName4.setText("");
		maxLevel4.setText("");
		profileName5.setText("");
		maxLevel5.setText("");
		profileName6.setText("");
		maxLevel6.setText("");
		profileName7.setText("");
		maxLevel7.setText("");
		profileName8.setText("");
		maxLevel8.setText("");
		profileName9.setText("");
		maxLevel9.setText("");
		profileName10.setText("");
		maxLevel10.setText("");
		profileName11.setText("");
		maxLevel11.setText("");
	}
}
