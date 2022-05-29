import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * Graphics.java
 *
 * A helper class for graphics related operations within the game (e.g. loading assets).
 *
 * @version 1.0
 * @since 25/11/21
 * Last modified 05/12/21
 * @author (REMOVED)
*/
public class Graphics {
	private static final String TITLE_NAME = "Rats!";
	private static final String WINDOW_ICON = "MaleRatEast";
	private static final String CSS_PATH = "./fxml/application.css";
	private static final String BOARD_FXML = "./fxml/board.fxml";
	private static final String MENU_FXML = "./fxml/menu.fxml";
	private static final String LEVEL_FXML = "./fxml/menu-levels.fxml";
	private static final String PROFILES_FXML = "./fxml/menu-profiles.fxml";
	private static final String SAVES_FXML = "./fxml/menu-saves.fxml";
	private static final String LOSE_FXML = "./fxml/menu-lose.fxml";
	private static final String WIN_FXML = "./fxml/menu-win.fxml";
	private static final String CROWN_IMAGE_NAME = "Crown";
	private static final String MENU_IMAGE_NAME = "MenuBack";
	private static final String EXPLOSION_IMAGE_NAME = "Explosion";
	private static final String BOMB_IMAGE_NAME ="Bomb";
	private static final String BOMB_1_IMAGE_NAME ="Bomb1";
	private static final String BOMB_2_IMAGE_NAME ="Bomb2";
	private static final String BOMB_3_IMAGE_NAME ="Bomb3";
	private static final String BOMB_4_IMAGE_NAME ="Bomb4";
	private static final String GAS_IMAGE_NAME ="Gas";
	private static final String NO_ENTRY_IMAGE_NAME ="NoEntry";
	private static final String NO_ENTRY_1_IMAGE_NAME ="NoEntry1";
	private static final String NO_ENTRY_2_IMAGE_NAME ="NoEntry2";
	private static final String NO_ENTRY_3_IMAGE_NAME ="NoEntry3";
	private static final String POISON_IMAGE_NAME ="Poison";
	private static final String MALE_IMAGE_NAME ="SexChangeMale";
	private static final String FEMALE_IMAGE_NAME ="SexChangeFemale";
	private static final String STERILISE_IMAGE_NAME ="Sterilisation";
	private static final String DEATH_NORTH_IMAGE_NAME ="DeathRatNorth";
	private static final String DEATH_EAST_IMAGE_NAME ="DeathRatEast";
	private static final String DEATH_SOUTH_IMAGE_NAME ="DeathRatSouth";
	private static final String DEATH_WEST_IMAGE_NAME ="DeathRatWest";
	private static final String GRASS_IMAGE_NAME ="GrassTile";
	private static final String PATH_IMAGE_NAME ="PathTile";
	private static final String TUNNEL_IMAGE_NAME ="TunnelTile";
	private static final String MINI_GRASS_IMAGE_NAME ="MiniMapGrassTile";
	private static final String MINI_PATH_IMAGE_NAME ="MiniMapPathTile";
	private static final String MINI_TUNNEL_IMAGE_NAME ="MiniMapTunnelTile";
	private static final String MALE_NORTH_IMAGE_NAME ="MaleRatNorth";
	private static final String MALE_EAST_IMAGE_NAME ="MaleRatEast";
	private static final String MALE_SOUTH_IMAGE_NAME ="MaleRatSouth";
	private static final String MALE_WEST_IMAGE_NAME ="MaleRatWest";
	private static final String FEMALE_NORTH_IMAGE_NAME ="FemaleRatNorth";
	private static final String FEMALE_EAST_IMAGE_NAME ="FemaleRatEast";
	private static final String FEMALE_SOUTH_IMAGE_NAME ="FemaleRatSouth";
	private static final String FEMALE_WEST_IMAGE_NAME ="FemaleRatWest";
	private static final String BABY_NORTH_IMAGE_NAME ="BabyRatNorth";
	private static final String BABY_EAST_IMAGE_NAME ="BabyRatEast";
	private static final String BABY_SOUTH_IMAGE_NAME ="BabyRatSouth";
	private static final String BABY_WEST_IMAGE_NAME ="BabyRatWest";
	private static final String IMAGE_PATH = "./images/";
	private static final String PNG_END = ".png";
	private Game game;
	private Stage stage;
	
	private HashMap<String, Image> images;
	
	private Board board;
	private MainMenu menu;
	
	private Scene levelScene;
	private Scene menuScene;
	private Scene levelsMenuScene;
	private Scene profilesMenuScene;
	private Scene savesMenuScene;
	private Scene loseMenuScene;
	private Scene winMenuScene;
	
	/**
	 * Construct a new graphics helper object.
	 * @param game The game object which constructs this helper.
	 * @param stage The stage provided by JavaFX.
	 */
	public Graphics(Game game, Stage stage) {
		this.game = game;
		this.stage = stage;
		this.images = new HashMap<String, Image>();
	}
	
	/**
	 * Initialise this helper by loading in all needed game assets.
	 * @return A boolean representing whether the initialisation was successful.
	 */
	public boolean initialise() {
		try {			
			initialiseImages();
			initialiseMenu();
			initialiseBoard();
			initialiseEscKeyListeners();
			
			stage.setTitle(TITLE_NAME);
			stage.setResizable(false);
			stage.getIcons().add(this.getImage(WINDOW_ICON));
			stage.show();
			
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Fetches an image loaded within the local store.
	 * @param name The name of the image file (excluding any file extension).
	 * @return An Image, or null if the image hasn't been loaded during initialisation.
	 */
	public Image getImage(String name) {
		return images.get(name);
	}

	/**
	 * Returns the board FXML controller.
	 * @return The Board instance currently being used.
	 */
	public Board getBoard() {
		return board;
	}
	
	/**
	 * Returns the menu FXML controller.
	 * @return The MainMenu instance currently being used.
	 */
	public MainMenu getMenu() {
		return menu;
	}
	
	/**
	 * Returns the application's stage provided by JavaFX.
	 * @return The JavaFX Stage being used.
	 */
	public Stage getStage() {
		return stage;
	}

	/**
	 * Returns the Scene instance being used for the level.
	 * @return The Scene instance currently in use.
	 */
	public Scene getLevelScene() {
		return levelScene;
	}

	/**
	 * Returns the Scene instance being used for the menu.
	 * @return The Scene instance currently in use.
	 */
	public Scene getMenuScene() {
		return menuScene;
	}

	/**
	 * Returns the Scene instance being used for the levels sub-menu.
	 * @return The Scene instance currently in use.
	 */
	public Scene getLevelsMenuScene() {
		return levelsMenuScene;
	}

	/**
	 * Returns the Scene instance being used for the profiles sub-menu.
	 * @return The Scene instance currently in use.
	 */
	public Scene getProfilesMenuScene() {
		return profilesMenuScene;
	}

	/**
	 * Returns the Scene instance being used for the saves sub-menu.
	 * @return The Scene instance currently in use.
	 */
	public Scene getSavesMenuScene() {
		return savesMenuScene;
	}
	
	/**
	 * Returns the Scene instance being used for the win transition menu.
	 * @return The Scene instance currently in use.
	 */
	public Scene getWinMenuScene() {
		return winMenuScene;
	}
	
	/**
	 * Returns the Scene instance being used for the lose transition menu.
	 * @return The Scene instance currently in use.
	 */
	public Scene getLoseMenuScene() {
		return loseMenuScene;
	}
	
	/**
	 * Initialise this helper by loading in all needed game assets.
	 */
	private void initialiseEscKeyListeners() {
		menuScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				game.switchToLevel();
			}
		});
		
		levelScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				game.switchToMenu();
			}
		});
		
		profilesMenuScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				stage.setScene(menuScene);
			}
		});
		
		levelsMenuScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				stage.setScene(menuScene);
			}
		});
		
		savesMenuScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				stage.setScene(menuScene);
			}
		});
	}
	
	/**
	 * Initialise the board GUI/FXML, controller and scene.
	 * @throws Exception Any exception that may be thrown when reading class resources/loading FXML.
	 */
	private void initialiseBoard() throws Exception {
		String css = this.getClass().getResource(CSS_PATH).toExternalForm();
		FXMLLoader loader = new FXMLLoader(getClass().getResource(BOARD_FXML));
		levelScene = new Scene(loader.load());	
		board = loader.<Board>getController();
		levelScene.getStylesheets().add(css);
	}
	
	/**
	 * Initialise the individual menu GUI/FXML, controller and scenes.
	 * @throws Exception Any exception that may be thrown when reading class resources/loading FXML.
	 */
	private void initialiseMenu() throws Exception {
		String css = this.getClass().getResource(CSS_PATH).toExternalForm();
		FXMLLoader loader = new FXMLLoader(getClass().getResource(MENU_FXML));
		menuScene = new Scene(loader.load());
		menuScene.getStylesheets().add(css);
		menu = loader.<MainMenu>getController();
		menu.storeGame(game);
		menu.storeGraphics(this);
		menu.displayHighScores();
		menu.setMotd();
		
		loader = new FXMLLoader(getClass().getResource(LEVEL_FXML));
		levelsMenuScene = new Scene(loader.load());
		loader.<LevelsMenu>getController().storeGame(game);
		levelsMenuScene.getStylesheets().add(css);
		
		loader = new FXMLLoader(getClass().getResource(PROFILES_FXML));
		profilesMenuScene = new Scene(loader.load());
		loader.<ProfilesMenu>getController().storeGame(game);
		loader.<ProfilesMenu>getController().displayProfiles();
		profilesMenuScene.getStylesheets().add(css);
		
		loader = new FXMLLoader(getClass().getResource(SAVES_FXML));
		savesMenuScene = new Scene(loader.load());
		loader.<SavesMenu>getController().storeGame(game);
		loader.<SavesMenu>getController().displaySaves();
		savesMenuScene.getStylesheets().add(css);
		
		loader = new FXMLLoader(getClass().getResource(LOSE_FXML));
		loseMenuScene = new Scene(loader.load());
		loader.<WinLoseMenu>getController().storeGame(game);
		loseMenuScene.getStylesheets().add(css);
		
		loader = new FXMLLoader(getClass().getResource(WIN_FXML));
		winMenuScene = new Scene(loader.load());
		loader.<WinLoseMenu>getController().storeGame(game);
		winMenuScene.getStylesheets().add(css);
	}
	
	/**
	 * Initialise all image assets and store them within a HashMap.
	 * @throws Exception Any exception that may be thrown when reading class resources.
	 */
	private void initialiseImages() throws Exception {
		loadImage(CROWN_IMAGE_NAME);
		loadImage(MENU_IMAGE_NAME);
		
		loadImage(EXPLOSION_IMAGE_NAME);
		loadImage(BOMB_IMAGE_NAME);
		loadImage(BOMB_1_IMAGE_NAME);
		loadImage(BOMB_2_IMAGE_NAME);
		loadImage(BOMB_3_IMAGE_NAME);
		loadImage(BOMB_4_IMAGE_NAME);
		loadImage(GAS_IMAGE_NAME);
		loadImage(NO_ENTRY_IMAGE_NAME);
		loadImage(NO_ENTRY_1_IMAGE_NAME);
		loadImage(NO_ENTRY_2_IMAGE_NAME);
		loadImage(NO_ENTRY_3_IMAGE_NAME);
		loadImage(POISON_IMAGE_NAME);
		loadImage(MALE_IMAGE_NAME);
		loadImage(FEMALE_IMAGE_NAME);
		loadImage(STERILISE_IMAGE_NAME);
		
		loadImage(DEATH_NORTH_IMAGE_NAME);
		loadImage(DEATH_EAST_IMAGE_NAME);
		loadImage(DEATH_SOUTH_IMAGE_NAME);
		loadImage(DEATH_WEST_IMAGE_NAME);
		
		loadImage(GRASS_IMAGE_NAME);
		loadImage(PATH_IMAGE_NAME);
		loadImage(TUNNEL_IMAGE_NAME);
		
		loadImage(MINI_GRASS_IMAGE_NAME);
		loadImage(MINI_PATH_IMAGE_NAME);
		loadImage(MINI_TUNNEL_IMAGE_NAME);
		
		loadImage(MALE_NORTH_IMAGE_NAME);
		loadImage(MALE_EAST_IMAGE_NAME);
		loadImage(MALE_SOUTH_IMAGE_NAME);
		loadImage(MALE_WEST_IMAGE_NAME);
		
		loadImage(FEMALE_NORTH_IMAGE_NAME);
		loadImage(FEMALE_EAST_IMAGE_NAME);
		loadImage(FEMALE_SOUTH_IMAGE_NAME);
		loadImage(FEMALE_WEST_IMAGE_NAME);

		loadImage(BABY_NORTH_IMAGE_NAME);
		loadImage(BABY_EAST_IMAGE_NAME);
		loadImage(BABY_SOUTH_IMAGE_NAME);
		loadImage(BABY_WEST_IMAGE_NAME);
	}
	
	/**
	 * Loads an image from the assets' folder (packed within the JAR) and adds it to the local store.
	 * @param name The name of the image file (excluding any file extension).
	 * @throws Exception A generic exception thrown if loading an invalid image or IO error.
	 */
	private void loadImage(String name) throws Exception {
		Image image = new Image(getClass().getResourceAsStream(IMAGE_PATH + name + PNG_END));
		images.put(name, image);
	}
}
