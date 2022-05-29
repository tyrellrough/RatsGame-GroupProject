import java.io.File;
import java.util.HashMap;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Game.java
 *
 * Initialises the game and stores all major components needed.
 *
 * @version 1.0
 * @since 25/11/21
 * Last modified 05/12/21
 * @author (REMOVED)
*/
public class Game extends Application {
	public static final int TPS = 20;
	public static final int FPS = 30;
	private static final int TICK_RATE = 1000/TPS;
	private static final int DRAW_RATE = 1000/FPS;
	private static final String PROFILE_ERROR = "Unable to load profiles from the saved file.";
	private static final String HIGHSCORES_ERROR = "Unable to load the high scores table from the saved file.";
	private static final String GRAPHICS_ERROR = "Unable to initialise graphics/load in game assets.";
	private static final String MUSIC_ERROR = "Unable to load music.";
	private static final String DATE_FOLDER = "data";
	private static final String SAVE_FOLDER = "saves";
	private static final String LEVEL_MUSIC = "./music/level.wav";
	private static final String MENU_MUSIC = "./music/menu.wav";
	private static final String BOMB = "Bomb";
	private static final String GAS = "Gas";
	private static final String POISON = "Poison";
	private static final String STERILISATION = "Sterilisation";
	private static final String NO_ENTRY = "NoEntry";
	private static final String FEMALE_CHANGE = "FemaleSexChange";
	private static final String MALE_CHANGE = "MaleSexChange";
	private static final String DEATH_RAT = "DeathRat";
	
	private Graphics graphics;
	private HighScoreTable highScores;
	private ProfileManager profiles;
	private GameDataManager data;
	private Level level;
	private RatManager rats;
	private Player player;
	
	private Timeline tickTimeline;
	private Timeline drawTimeline;
	
	private Clip levelMusic;
	private Clip menuMusic;
	
	private boolean levelOngoing;
	private HashMap<String, Integer> itemDropTimers;
	private int gameTimeTimer;
	
	/**
	 * The application entry point which starts JavaFX.
	 * @param args The supplied command line arguments.
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Start the application, load required data, and launch the game menu.
	 * @param stage The stage provided by JavaFX.
	 */
	public void start(Stage stage) {
		mkdirsIfAbsent(DATE_FOLDER);
		mkdirsIfAbsent(SAVE_FOLDER);
		
		profiles = new ProfileManager();
		if (!profiles.readFile()) {
			exit(PROFILE_ERROR);
		}
		
		highScores = new HighScoreTable();
		profiles.setHighScores(highScores);
		if (!highScores.loadFromFile(profiles)) {
			exit(HIGHSCORES_ERROR);
		}
		
		data = new GameDataManager();
		
		graphics = new Graphics(this, stage);
		if (!graphics.initialise()) {
			exit(GRAPHICS_ERROR);
		}
		
		tickTimeline = new Timeline(new KeyFrame(Duration.millis(TICK_RATE), event -> tick()));
		drawTimeline = new Timeline(new KeyFrame(Duration.millis(DRAW_RATE), event -> draw()));
		
		tickTimeline.setCycleCount(Animation.INDEFINITE);
		drawTimeline.setCycleCount(Animation.INDEFINITE);
		
		initialiseMusic();
		switchToMenu();
	}
	
	/**
	 * Start a new level and switch focus to it.
	 * @param levelNum The level number to
	 */
	public void onLevelStart(int levelNum) {
		level = new Level("Level" + levelNum + ".txt");
		rats = new RatManager(this.level);
		player = new Player();
		
		graphics.getBoard().initialise(this);
		initialiseItemDropTimers();
		gameTimeTimer = 0;
		
		levelOngoing = true;
		switchToLevel();
	}
	
	/**
	 * Resume a level from a save file and switch focus to it.
	 * @param saveFile The name of the save file to resume from.
	 */
	public void onLevelStart(String saveFile) {
		data.loadSaveFile(saveFile);
		
		level = data.getLevelLoaded();
		rats = data.getRatMgLoaded();
		player = data.getPlayerLoaded();
		
		// Ensure that transient references are re-initialised.
		level.getAllRats().forEach(rat -> rat.setLevel(level));
		level.getAllItems().forEach(item -> initialiseItemReferences(item));
		rats.setAllRats(level.getAllRats());
		rats.setCurrentLevel(level);
		
		graphics.getBoard().initialise(this);
		initialiseItemDropTimers();
		gameTimeTimer = 0;
		
		levelOngoing = true;
		switchToLevel();
	}
	
	/**
	 * Switch focus of the application to an ongoing game.
	 */
	public void switchToLevel() {
		if (!levelOngoing) {
			return;
		}
		
		levelMusic.loop(Clip.LOOP_CONTINUOUSLY);
		menuMusic.stop();
		levelMusic.start();
		
		graphics.getStage().setScene(graphics.getLevelScene());
		
		tickTimeline.play();
		drawTimeline.play();
	}
	
	/**
	 * Switch focus of the application to the lose menu.
	 */
	public void switchToLoseMenu() {
		graphics.getStage().setScene(graphics.getLoseMenuScene());
	}
	
	/**
	 * Switch focus of the application to the win menu.
	 */
	public void switchToWinMenu() {
		graphics.getStage().setScene(graphics.getWinMenuScene());
	}
	
	/**
	 * Switch focus of the application to the menu.
	 */
	public void switchToMenu() {
		menuMusic.loop(Clip.LOOP_CONTINUOUSLY);
		levelMusic.stop();
		menuMusic.start();
		tickTimeline.stop();
		drawTimeline.stop();
		
		graphics.getMenu().displayHighScores();
		graphics.getStage().setScene(graphics.getMenuScene());
	}
	
	/**
	 * Saves the current ongoing level.
	 */
	public void saveCurrentLevel() {
		if (!levelOngoing) {
			return;
		}
		
		data.saveGame(level, rats, player);
	}
	
	/**
	 * Returns the ProfileManager instance being used.
	 * @return An instance of ProfileManager.
	 */
	public ProfileManager getProfiles() {
		return profiles;
	}
	
	/**
	 * Returns the HighScoreTable instance being used.
	 * @return An instance of HighScoreTable.
	 */
	public HighScoreTable getHighScores() {
		return highScores;
	}
	
	/**
	 * Returns the Graphics instance being used.
	 * @return An instance of Graphics.
	 */
	public Graphics getGraphics() {
		return graphics;
	}
	
	/**
	 * Returns the Level instance being used.
	 * @return An instance of Level.
	 */
	public Level getLevel() {
		return level;
	}
	
	/**
	 * Returns the RatManager instance being used.
	 * @return An instance of RatManager.
	 */
	public RatManager getRats() {
		return rats;
	}
	
	/**
	 * Returns the Player instance being used.
	 * @return An instance of Player.
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Returns the GameDataManager instance being used.
	 * @return An instance of GameDataManager.
	 */
	public GameDataManager getDataManager() {
		return data;
	}
	
	/**
	 * Initialise the Clip instances which are used to play the menu and level music.
	 */
	private void initialiseMusic() {
		try {
			levelMusic = AudioSystem.getClip();
			menuMusic = AudioSystem.getClip();
			
			levelMusic.open(AudioSystem.getAudioInputStream(getClass().getResource(LEVEL_MUSIC)));
			menuMusic.open(AudioSystem.getAudioInputStream(getClass().getResource(MENU_MUSIC)));
		} catch(Exception ex) {
			ex.printStackTrace();
			exit(MUSIC_ERROR);
		}
	}
	
	/**
	 * Update the game state while a game is in progress.
	 */
	private void tick() {
		// Check the win/loss conditions.
		if (level.getAllRats().size() == 0) {
			onGameEnd(true);
		} else if (rats.hasLostGame()) {
			onGameEnd(false);
		}
		
		if (gameTimeTimer++ == TPS) {
			gameTimeTimer = 0;
			level.incrementTimeCounter();
		}
		
		onGameDrop();
		
		rats.breedRatsOnTiles();
		rats.ratsGivingBirth();
		
		level.getAllRats().forEach(rat -> rat.tick());
		level.getAllItems().forEach(item -> item.tick());
		
		player.calculateScore(this, false);
	}
	
	/**
	 * Draw a single frame to the screen while a game is in progress.
	 */
	private void draw() {
		graphics.getBoard().drawBoard(graphics);
	}
	
	/**
	 * Set references needed for items loaded from a gave save file.
	 * @param item The item to set references for.
	 */
	private void initialiseItemReferences(Item item) {
		item.setTransientReferences(level);
		
		if (item instanceof Bomb) {
			((Bomb) item).setRats(rats);
		}
		if (item instanceof DeathRat) {
			((DeathRat) item).setRats(rats);
		}
		if (item instanceof Gas) {
			((Gas) item).setRats(rats);
		}
		if (item instanceof Poison) {
			((Poison) item).setRats(rats);
		}
	}
	
	/**
	 * End the game and switch back to the game menu.
	 */
	private void onGameEnd(boolean win) {
		Profile current = profiles.getCurrentProfile();
		
		if (win && current.getMaxLevel() != Level.getLevelCount()) {
			current.setMaxLevel(level.getLevelNumber());	
			profiles.overWriteFile();
		}
		
		if (win) {
			player.calculateScore(this, true);
			highScores.profileGotScore(current, level.getLevelNumber(), player.getScore());
		}
		
		levelOngoing = false;
		if (win) {
			switchToWinMenu();
		} else if (!win) {
			switchToLoseMenu();
		}
		
	}
	
	/**
	 * Update all item drop timers and add any item types to the player's inventory which have expired.
	 */
	private void onGameDrop() {
		itemDropTimers.put(BOMB, itemDropTimers.get(BOMB) - 1);
		itemDropTimers.put(DEATH_RAT, itemDropTimers.get(DEATH_RAT) - 1);
		itemDropTimers.put(MALE_CHANGE, itemDropTimers.get(MALE_CHANGE) - 1);
		itemDropTimers.put(FEMALE_CHANGE, itemDropTimers.get(FEMALE_CHANGE) - 1);
		itemDropTimers.put(GAS, itemDropTimers.get(GAS) - 1);
		itemDropTimers.put(NO_ENTRY, itemDropTimers.get(NO_ENTRY) - 1);
		itemDropTimers.put(POISON, itemDropTimers.get(POISON) - 1);
		itemDropTimers.put(STERILISATION, itemDropTimers.get(STERILISATION) - 1);
		
		if (itemDropTimers.get(BOMB) == 0) {
			itemDropTimers.put(BOMB, level.getBombDropRate() * TPS);
			player.addItemToInventory(new Bomb(level, rats));
		}
		if (itemDropTimers.get(DEATH_RAT) == 0) {
			itemDropTimers.put(DEATH_RAT, level.getDeathRatDropRate() * TPS);
			player.addItemToInventory(new DeathRat(level, rats));
		}
		if (itemDropTimers.get(MALE_CHANGE) == 0) {
			itemDropTimers.put(MALE_CHANGE, level.getMaleSexChangeDropRate() * TPS);
			player.addItemToInventory(new MaleSexChange(level));
		}
		if (itemDropTimers.get(FEMALE_CHANGE) == 0) {
			itemDropTimers.put(FEMALE_CHANGE, level.getFemaleSexChangeDropRate() * TPS);
			player.addItemToInventory(new FemaleSexChange(level));
		}
		if (itemDropTimers.get(GAS) == 0) {
			itemDropTimers.put(GAS, level.getGasDropRate() * TPS);
			player.addItemToInventory(new Gas(level, rats));
		}
		if (itemDropTimers.get(NO_ENTRY) == 0) {
			itemDropTimers.put(NO_ENTRY, level.getNoEntryDropRate() * TPS);
			player.addItemToInventory(new NoEntry(level));
		}
		if (itemDropTimers.get(POISON) == 0) {
			itemDropTimers.put(POISON, level.getPoisonDropRate() * TPS);
			player.addItemToInventory(new Poison(level, rats));
		}
		if (itemDropTimers.get(STERILISATION) == 0) {
			itemDropTimers.put(STERILISATION, level.getSterilisationDropRate() * TPS);
			player.addItemToInventory(new Sterilisation(level));
		}
	}
	
	/**
	 * Reset all item drop timers to their initial values for the current level.
	 */
	private void initialiseItemDropTimers() {
		itemDropTimers = new HashMap<String, Integer>();
		
		itemDropTimers.put(BOMB, level.getBombDropRate() * TPS);
		itemDropTimers.put(DEATH_RAT, level.getDeathRatDropRate() * TPS);
		itemDropTimers.put(MALE_CHANGE, level.getMaleSexChangeDropRate() * TPS);
		itemDropTimers.put(FEMALE_CHANGE, level.getFemaleSexChangeDropRate() * TPS);
		itemDropTimers.put(GAS, level.getGasDropRate() * TPS);
		itemDropTimers.put(NO_ENTRY, level.getNoEntryDropRate() * TPS);
		itemDropTimers.put(POISON, level.getPoisonDropRate() * TPS);
		itemDropTimers.put(STERILISATION, level.getSterilisationDropRate() * TPS);
	}
	
	/**
	 * Creates a child directory in the local working directory if absent.
	 */
	private void mkdirsIfAbsent(String name) {
		File folder = new File(name);
		
		if (!folder.exists() && !folder.mkdirs()) {
			exit("Unable to create the './" + name + "' folder during startup.");
		}
	}
	
	/**
	 * Exit from the program and print an error message to the console.
	 * @param message The cause of the error.
	 */
	private void exit(String message) {
		System.out.println("[ERROR] " + message);
		System.exit(-1);
	}
}
