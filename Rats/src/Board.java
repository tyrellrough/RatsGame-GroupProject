import java.awt.Point;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.text.Text;

/**
 * Board.java
 *
 * Board is the controller for the level canvas, progress canvas and progress bar canvas.
 *
 * @version 1.5
 * @since 25/11/2021
 * Last modified 05/12/21
 * @author (REMOVED)
 */
public class Board {
	private static final int TILES_IN_VIEW_EACH_AXIS = 10;
	private static final int BOARD_CANVAS_HEIGHT = 540;
	private static final double TILE_SIZE = BOARD_CANVAS_HEIGHT / TILES_IN_VIEW_EACH_AXIS;
	private static final int FOUR_ITEMS = 4;
	private static final int THREE_ITEMS = 3;
	private static final int AXIS_INCREASED = 10;

	private static final String BOMB_NAME = "Bomb";
	private static final String GAS_NAME = "Gas";
	private static final String STERILISATION_NAME = "Sterilisation";
	private static final String POISON_NAME = "Poison";
	private static final String MALE_SEX_CHANGE_NAME = "MaleSexChange";
	private static final String FEMALE_SEX_CHANGE_NAME = "FemaleSexChange";
	private static final String NO_ENTRY_NAME = "NoEntry";
	private static final String DEATH_RAT_NAME = "DeathRat";
	private static final String EAST_DEATH_RAT = "DeathRatEast";
	private static final String SEX_CHANGE_FEMALE = "SexChangeFemale";
	private static final String SEX_CHANGE_MALE = "SexChangeMale";


	@FXML
	private Canvas boardCanvas;
	@FXML
	private Canvas miniMapCanvas;
	@FXML
	private Button scrollUpButton;
	@FXML
	private Button scrollDownButton;
	@FXML
	private Button scrollLeftButton;
	@FXML
	private Button scrollRightButton;
	@FXML
	private ImageView bombImageView1;
	@FXML
	private ImageView bombImageView2;
	@FXML
	private ImageView bombImageView3;
	@FXML
	private ImageView bombImageView4;
	@FXML
	private ImageView deathRatImageView1;
	@FXML
	private ImageView deathRatImageView2;
	@FXML
	private ImageView deathRatImageView3;
	@FXML
	private ImageView deathRatImageView4;
	@FXML
	private ImageView gasImageView1;
	@FXML
	private ImageView gasImageView2;
	@FXML
	private ImageView gasImageView3;
	@FXML
	private ImageView gasImageView4;
	@FXML
	private ImageView noEntryImageView1;
	@FXML
	private ImageView noEntryImageView2;
	@FXML
	private ImageView noEntryImageView3;
	@FXML
	private ImageView noEntryImageView4;
	@FXML
	private ImageView poisonImageView1;
	@FXML
	private ImageView poisonImageView2;
	@FXML
	private ImageView poisonImageView3;
	@FXML
	private ImageView poisonImageView4;
	@FXML
	private ImageView sexChangeMaleImageView1;
	@FXML
	private ImageView sexChangeMaleImageView2;
	@FXML
	private ImageView sexChangeMaleImageView3;
	@FXML
	private ImageView sexChangeMaleImageView4;
	@FXML
	private ImageView sexChangeFemaleImageView1;
	@FXML
	private ImageView sexChangeFemaleImageView2;
	@FXML
	private ImageView sexChangeFemaleImageView3;
	@FXML
	private ImageView sexChangeFemaleImageView4;
	@FXML
	private ImageView sterilisationImageView1;
	@FXML
	private ImageView sterilisationImageView2;
	@FXML
	private ImageView sterilisationImageView3;
	@FXML
	private ImageView sterilisationImageView4;
	@FXML
	private Text scoreText;
	@FXML
	private Canvas progressBarCanvas;
	@FXML
	private Text currentLevelText;

	private int currentTileStartingX;
	private int currentTileStartingY;

	private Game game;

	private Tile[][] currentTilesView;
	private String currentItemDragged;

	/**
	 * Initialises local attributes within this board.
	 * @param game An instance of Game currently being used.
	 */
	public void initialise(Game game) {
		this.game = game;
		currentTileStartingX = 0;
		currentTileStartingY = 0;
		currentTilesView = new Tile[TILES_IN_VIEW_EACH_AXIS][TILES_IN_VIEW_EACH_AXIS];
	}

	/**
	 * Draws the board and minimap and everything on them.
	 * @param graphics The graphics needed to be drawn.
	 */
	public void drawBoard(Graphics graphics) {
		hideInventory(game.getPlayer(), graphics);
		setCurrentViewableTiles();
		showScore();
		showCurrentLevel();

		GraphicsContext levelGC = boardCanvas.getGraphicsContext2D();
		GraphicsContext minimapGC = miniMapCanvas.getGraphicsContext2D();
		GraphicsContext progressBarGC = progressBarCanvas.getGraphicsContext2D();

		DrawLevel.drawAll(game, levelGC, currentTileStartingX, currentTileStartingY, currentTilesView);
		DrawMiniMap.drawAll(game.getLevel(), minimapGC, currentTileStartingX, currentTileStartingY);
		DrawProgressBar.drawProgressBar(progressBarGC, game.getRats(), game.getLevel());
	}

	/**
	 * Gets width of current view in tiles.
	 */
	public static int getWidthOfTileViewInTiles() {
		return TILES_IN_VIEW_EACH_AXIS;
	}

	/**
	 * Stores the currently viewable Tiles in an array.
	 */
	public void setCurrentViewableTiles() {
		for (int y = 0; y < TILES_IN_VIEW_EACH_AXIS; y++) {
			for (int x = 0; x < TILES_IN_VIEW_EACH_AXIS; x++) {
				Point p = new Point(currentTileStartingX + x, currentTileStartingY + y);
				currentTilesView[y][x] = game.getLevel().getTile(p);
			}
		}
	}

	/**
	 * Allows the Player to use the scroll up to move up on the Level.
	 * @param e The event of when the Players press the up button on screen.
	 */
	public void scrollUp(ActionEvent e) {
		if ((currentTileStartingY) > 0) {
			currentTileStartingY--;
		}
	}

	/**
	 * Allows the Player to use the scroll down to move up on the Level.
	 * @param e The event of when the Players press the down button on screen.
	 */
	public void scrollDown(ActionEvent e) {
		if ((currentTileStartingY + AXIS_INCREASED) < game.getLevel().getHeight()) {
			currentTileStartingY++;
		}
	}

	/**
	 * Allows the Player to use the scroll left to move up on the Level.
	 * @param e The event of when the Players press the left button on screen.
	 */
	public void scrollLeft(ActionEvent e) {
		if (currentTileStartingX > 0) {
			currentTileStartingX--;
		}
	}

	/**
	 * Allows the Player to use the scroll up to move right on the Level.
	 * @param e The event of when the Players press the right button on screen.
	 */
	public void scrollRight(ActionEvent e) {
		if ((currentTileStartingX + AXIS_INCREASED) < game.getLevel().getWidth()) {
			currentTileStartingX++;
		}
	}

	/**
	 * Displays the current Level.
	 */
	private void showCurrentLevel() {
		currentLevelText.setText("" + game.getLevel().getLevelNumber());
	}

	/**
	 * Displays the Player's score.
	 */
	private void showScore() {
		scoreText.setText("" + game.getPlayer().getScore());
	}

	/**
	 * Shows the correct number of draggable Bomb Items on the screen.
	 * @param currentPlayer The current Player.
	 * @param graphics The graphics needed to display the Bomb Item.
	 */
	private void hideBombInventory(Player currentPlayer, Graphics graphics) {
		int numOfBombInventory = currentPlayer.getNumOfItem(BOMB_NAME);
		switch (numOfBombInventory) {
			case FOUR_ITEMS:
				bombImageView1.setImage(graphics.getImage(BOMB_NAME));
				bombImageView2.setImage(graphics.getImage(BOMB_NAME));
				bombImageView3.setImage(graphics.getImage(BOMB_NAME));
				bombImageView4.setImage(graphics.getImage(BOMB_NAME));
				break;
			case THREE_ITEMS:
				bombImageView1.setImage(graphics.getImage(BOMB_NAME));
				bombImageView2.setImage(graphics.getImage(BOMB_NAME));
				bombImageView3.setImage(graphics.getImage(BOMB_NAME));
				bombImageView4.setImage(null);
				break;
			case 2:
				bombImageView1.setImage(graphics.getImage(BOMB_NAME));
				bombImageView2.setImage(graphics.getImage(BOMB_NAME));
				bombImageView3.setImage(null);
				bombImageView4.setImage(null);
				break;
			case 1:
				bombImageView1.setImage(graphics.getImage(BOMB_NAME));
				bombImageView2.setImage(null);
				bombImageView3.setImage(null);
				bombImageView4.setImage(null);
				break;
			case 0:
				bombImageView4.setImage(null);
				bombImageView3.setImage(null);
				bombImageView2.setImage(null);
				bombImageView1.setImage(null);
			default:
				break;
		}
	}

	/**
	 * Shows the correct number of draggable DeathRat Items on the screen.
	 * @param currentPlayer The current Player.
	 * @param graphics The graphics to draw the DeathRat Item.
	 */
	private void hideDeathRatInventory(Player currentPlayer, Graphics graphics) {
		int numOfDeathRatInventory = currentPlayer.getNumOfItem(DEATH_RAT_NAME);
		switch (numOfDeathRatInventory) {
			case FOUR_ITEMS:
				deathRatImageView1.setImage(graphics.getImage(EAST_DEATH_RAT));
				deathRatImageView2.setImage(graphics.getImage(EAST_DEATH_RAT));
				deathRatImageView3.setImage(graphics.getImage(EAST_DEATH_RAT));
				deathRatImageView4.setImage(graphics.getImage(EAST_DEATH_RAT));
				break;
			case THREE_ITEMS:
				deathRatImageView1.setImage(graphics.getImage(EAST_DEATH_RAT));
				deathRatImageView2.setImage(graphics.getImage(EAST_DEATH_RAT));
				deathRatImageView3.setImage(graphics.getImage(EAST_DEATH_RAT));
				deathRatImageView4.setImage(null);
				break;
			case 2:
				deathRatImageView1.setImage(graphics.getImage(EAST_DEATH_RAT));
				deathRatImageView2.setImage(graphics.getImage(EAST_DEATH_RAT));
				deathRatImageView3.setImage(null);
				deathRatImageView4.setImage(null);
				break;
			case 1:
				deathRatImageView1.setImage(graphics.getImage(EAST_DEATH_RAT));
				deathRatImageView2.setImage(null);
				deathRatImageView3.setImage(null);
				deathRatImageView4.setImage(null);
				break;
			case 0:
				deathRatImageView4.setImage(null);
				deathRatImageView3.setImage(null);
				deathRatImageView2.setImage(null);
				deathRatImageView1.setImage(null);
				break;
			default:
				break;
		}
	}

	/**
	 * Shows the correct number of draggable Gas Items on the screen.
	 * @param currentPlayer The current Player.
	 * @param graphics The graphics needed to draw the Gas Items.
	 */
	private void hideGasInventory(Player currentPlayer, Graphics graphics) {
		int numOfGasInventory = currentPlayer.getNumOfItem(GAS_NAME);
		switch (numOfGasInventory) {
			case FOUR_ITEMS:
				gasImageView1.setImage(graphics.getImage(GAS_NAME));
				gasImageView2.setImage(graphics.getImage(GAS_NAME));
				gasImageView3.setImage(graphics.getImage(GAS_NAME));
				gasImageView4.setImage(graphics.getImage(GAS_NAME));
				break;
			case THREE_ITEMS:
				gasImageView1.setImage(graphics.getImage(GAS_NAME));
				gasImageView2.setImage(graphics.getImage(GAS_NAME));
				gasImageView3.setImage(graphics.getImage(GAS_NAME));
				gasImageView4.setImage(null);
				break;
			case 2:
				gasImageView1.setImage(graphics.getImage(GAS_NAME));
				gasImageView2.setImage(graphics.getImage(GAS_NAME));
				gasImageView3.setImage(null);
				gasImageView4.setImage(null);
				break;
			case 1:
				gasImageView1.setImage(graphics.getImage(GAS_NAME));
				gasImageView2.setImage(null);
				gasImageView3.setImage(null);
				gasImageView4.setImage(null);
				break;
			case 0:
				gasImageView4.setImage(null);
				gasImageView3.setImage(null);
				gasImageView2.setImage(null);
				gasImageView1.setImage(null);
				break;
			default:
				break;
		}
	}

	/**
	 * Shows the correct number of draggable NoEntry Items on the screen.
	 * @param currentPlayer The current Player.
	 * @param graphics The graphics needed to draw the NoEntry Items.
	 */
	private void hideNoEntryInventory(Player currentPlayer, Graphics graphics) {
		int numOfNoEntryInventory = currentPlayer.getNumOfItem(NO_ENTRY_NAME);
		switch (numOfNoEntryInventory) {
			case FOUR_ITEMS:
				noEntryImageView1.setImage(graphics.getImage(NO_ENTRY_NAME));
				noEntryImageView2.setImage(graphics.getImage(NO_ENTRY_NAME));
				noEntryImageView3.setImage(graphics.getImage(NO_ENTRY_NAME));
				noEntryImageView4.setImage(graphics.getImage(NO_ENTRY_NAME));
				break;
			case THREE_ITEMS:
				noEntryImageView1.setImage(graphics.getImage(NO_ENTRY_NAME));
				noEntryImageView2.setImage(graphics.getImage(NO_ENTRY_NAME));
				noEntryImageView3.setImage(graphics.getImage(NO_ENTRY_NAME));
				noEntryImageView4.setImage(null);
				break;
			case 2:
				noEntryImageView1.setImage(graphics.getImage(NO_ENTRY_NAME));
				noEntryImageView2.setImage(graphics.getImage(NO_ENTRY_NAME));
				noEntryImageView3.setImage(null);
				noEntryImageView4.setImage(null);
				break;
			case 1:
				noEntryImageView1.setImage(graphics.getImage(NO_ENTRY_NAME));
				noEntryImageView2.setImage(null);
				noEntryImageView3.setImage(null);
				noEntryImageView4.setImage(null);
				break;
			case 0:
				noEntryImageView4.setImage(null);
				noEntryImageView3.setImage(null);
				noEntryImageView2.setImage(null);
				noEntryImageView1.setImage(null);
				break;
			default:
				break;
		}
	}

	/**
	 * Shows the correct number of draggable Poison Items on the screen.
	 * @param currentPlayer The current Player.
	 * @param graphics The graphics needed to draw the Poison Items.
	 */
	private void hidePoisonInventory(Player currentPlayer, Graphics graphics) {
		int numOfPoisonInventory = currentPlayer.getNumOfItem(POISON_NAME);
		switch (numOfPoisonInventory) {
			case FOUR_ITEMS:
				poisonImageView1.setImage(graphics.getImage(POISON_NAME));
				poisonImageView2.setImage(graphics.getImage(POISON_NAME));
				poisonImageView3.setImage(graphics.getImage(POISON_NAME));
				poisonImageView4.setImage(graphics.getImage(POISON_NAME));
				break;
			case THREE_ITEMS:
				poisonImageView1.setImage(graphics.getImage(POISON_NAME));
				poisonImageView2.setImage(graphics.getImage(POISON_NAME));
				poisonImageView3.setImage(graphics.getImage(POISON_NAME));
				poisonImageView4.setImage(null);
				break;
			case 2:
				poisonImageView1.setImage(graphics.getImage(POISON_NAME));
				poisonImageView2.setImage(graphics.getImage(POISON_NAME));
				poisonImageView3.setImage(null);
				poisonImageView4.setImage(null);
				break;
			case 1:
				poisonImageView1.setImage(graphics.getImage(POISON_NAME));
				poisonImageView2.setImage(null);
				poisonImageView3.setImage(null);
				poisonImageView4.setImage(null);
				break;
			case 0:
				poisonImageView4.setImage(null);
				poisonImageView3.setImage(null);
				poisonImageView2.setImage(null);
				poisonImageView1.setImage(null);
				break;
			default:
				break;
		}
	}

	/**
	 * Shows the correct number of draggable Sterilisation Items on the screen.
	 * @param currentPlayer The current Player.
	 * @param graphics The graphics needed to draw the Sterilisation Items.
	 */
	private void hideSterilisationInventory(Player currentPlayer, Graphics graphics) {
		int numOfSterilisationInventory = currentPlayer.getNumOfItem(STERILISATION_NAME);
		switch (numOfSterilisationInventory) {
			case FOUR_ITEMS:
				sterilisationImageView1.setImage(graphics.getImage(STERILISATION_NAME));
				sterilisationImageView2.setImage(graphics.getImage(STERILISATION_NAME));
				sterilisationImageView3.setImage(graphics.getImage(STERILISATION_NAME));
				sterilisationImageView4.setImage(graphics.getImage(STERILISATION_NAME));
				break;
			case THREE_ITEMS:
				sterilisationImageView1.setImage(graphics.getImage(STERILISATION_NAME));
				sterilisationImageView2.setImage(graphics.getImage(STERILISATION_NAME));
				sterilisationImageView3.setImage(graphics.getImage(STERILISATION_NAME));
				sterilisationImageView4.setImage(null);
				break;
			case 2:
				sterilisationImageView1.setImage(graphics.getImage(STERILISATION_NAME));
				sterilisationImageView2.setImage(graphics.getImage(STERILISATION_NAME));
				sterilisationImageView3.setImage(null);
				sterilisationImageView4.setImage(null);
				break;
			case 1:
				sterilisationImageView1.setImage(graphics.getImage(STERILISATION_NAME));
				sterilisationImageView2.setImage(null);
				sterilisationImageView3.setImage(null);
				sterilisationImageView4.setImage(null);
				break;
			case 0:
				sterilisationImageView4.setImage(null);
				sterilisationImageView3.setImage(null);
				sterilisationImageView2.setImage(null);
				sterilisationImageView1.setImage(null);
				break;
			default:
				break;
		}
	}

	/**
	 * Shows the correct number of draggable MaleSexChange Items on the screen.
	 * @param currentPlayer The current Player.
	 * @param graphics The graphics needed to draw the MaleSexChange Items.
	 */
	private void hideSexChangeMaleInventory(Player currentPlayer, Graphics graphics) {
		int numOfSterilisationInventory = currentPlayer.getNumOfItem(MALE_SEX_CHANGE_NAME);
		switch (numOfSterilisationInventory) {
			case FOUR_ITEMS:
				sexChangeMaleImageView1.setImage(graphics.getImage(SEX_CHANGE_MALE));
				sexChangeMaleImageView2.setImage(graphics.getImage(SEX_CHANGE_MALE));
				sexChangeMaleImageView3.setImage(graphics.getImage(SEX_CHANGE_MALE));
				sexChangeMaleImageView4.setImage(graphics.getImage(SEX_CHANGE_MALE));
				break;
			case THREE_ITEMS:
				sexChangeMaleImageView1.setImage(graphics.getImage(SEX_CHANGE_MALE));
				sexChangeMaleImageView2.setImage(graphics.getImage(SEX_CHANGE_MALE));
				sexChangeMaleImageView3.setImage(graphics.getImage(SEX_CHANGE_MALE));
				sexChangeMaleImageView4.setImage(null);
				break;
			case 2:
				sexChangeMaleImageView1.setImage(graphics.getImage(SEX_CHANGE_MALE));
				sexChangeMaleImageView2.setImage(graphics.getImage(SEX_CHANGE_MALE));
				sexChangeMaleImageView3.setImage(null);
				sexChangeMaleImageView4.setImage(null);
				break;
			case 1:
				sexChangeMaleImageView1.setImage(graphics.getImage(SEX_CHANGE_MALE));
				sexChangeMaleImageView2.setImage(null);
				sexChangeMaleImageView3.setImage(null);
				sexChangeMaleImageView4.setImage(null);
				break;
			case 0:
				sexChangeMaleImageView4.setImage(null);
				sexChangeMaleImageView3.setImage(null);
				sexChangeMaleImageView2.setImage(null);
				sexChangeMaleImageView1.setImage(null);
				break;
			default:
				break;
		}
	}

	/**
	 * Shows the correct number of draggable FemaleSexChange Items on the screen.
	 * @param currentPlayer The current Player.
	 * @param graphics The graphics needed to draw the FemaleSexChange Items.
	 */
	private void hideSexChangeFemaleInventory(Player currentPlayer, Graphics graphics) {
		int numOfSterilisationInventory = currentPlayer.getNumOfItem(FEMALE_SEX_CHANGE_NAME);
		switch (numOfSterilisationInventory) {
			case FOUR_ITEMS:
				sexChangeFemaleImageView1.setImage(graphics.getImage(SEX_CHANGE_FEMALE));
				sexChangeFemaleImageView2.setImage(graphics.getImage(SEX_CHANGE_FEMALE));
				sexChangeFemaleImageView3.setImage(graphics.getImage(SEX_CHANGE_FEMALE));
				sexChangeFemaleImageView4.setImage(graphics.getImage(SEX_CHANGE_FEMALE));
				break;
			case THREE_ITEMS:
				sexChangeFemaleImageView1.setImage(graphics.getImage(SEX_CHANGE_FEMALE));
				sexChangeFemaleImageView2.setImage(graphics.getImage(SEX_CHANGE_FEMALE));
				sexChangeFemaleImageView3.setImage(graphics.getImage(SEX_CHANGE_FEMALE));
				sexChangeFemaleImageView4.setImage(null);
				break;
			case 2:
				sexChangeFemaleImageView1.setImage(graphics.getImage(SEX_CHANGE_FEMALE));
				sexChangeFemaleImageView2.setImage(graphics.getImage(SEX_CHANGE_FEMALE));
				sexChangeFemaleImageView3.setImage(null);
				sexChangeFemaleImageView4.setImage(null);
				break;
			case 1:
				sexChangeFemaleImageView1.setImage(graphics.getImage(SEX_CHANGE_FEMALE));
				sexChangeFemaleImageView2.setImage(null);
				sexChangeFemaleImageView3.setImage(null);
				sexChangeFemaleImageView4.setImage(null);
				break;
			case 0:
				sexChangeFemaleImageView4.setImage(null);
				sexChangeFemaleImageView3.setImage(null);
				sexChangeFemaleImageView2.setImage(null);
				sexChangeFemaleImageView1.setImage(null);
				break;
			default:
				break;
		}
	}

	/**
	 * Shows the correct number of draggable Items on the screen.
	 * @param currentPlayer The current Player.
	 * @param graphics The graphics needed to draw the Items on to the screen.
	 */
	private void hideInventory(Player currentPlayer, Graphics graphics) {
		hideSterilisationInventory(currentPlayer, graphics);
		hidePoisonInventory(currentPlayer, graphics);
		hideNoEntryInventory(currentPlayer, graphics);
		hideGasInventory(currentPlayer, graphics);
		hideDeathRatInventory(currentPlayer, graphics);
		hideBombInventory(currentPlayer, graphics);
		hideSexChangeMaleInventory(currentPlayer, graphics);
		hideSexChangeFemaleInventory(currentPlayer, graphics);
	}

	/**
	 * Allows for Bomb Items in the inventory to start being dragged.
	 * @param event The mouse clicking and dragging the Bomb item.
	 */
	@FXML
	private void startBombDrag(MouseEvent event) {
		Dragboard db = bombImageView1.startDragAndDrop(TransferMode.ANY);
		currentItemDragged = BOMB_NAME;
		ClipboardContent content = new ClipboardContent();
		content.putString(BOMB_NAME);
		db.setContent(content);
		event.consume();
	}

	/**
	 * Allows for Bomb Items in the inventory to start being dragged.
	 * @param event The mouse clicking and dragging the Bomb item.
	 */
	@FXML
	private void startDeathRatDrag(MouseEvent event) {
		Dragboard db = deathRatImageView1.startDragAndDrop(TransferMode.ANY);
		currentItemDragged = DEATH_RAT_NAME;
		ClipboardContent content = new ClipboardContent();
		content.putString(DEATH_RAT_NAME);
		db.setContent(content);
		event.consume();

	}

	/**
	 * Allows for Gas Items in the inventory to start being dragged.
	 * @param event The mouse clicking and dragging the Gas Item.
	 */
	@FXML
	private void startGasDrag(MouseEvent event) {
		Dragboard db = gasImageView1.startDragAndDrop(TransferMode.ANY);
		currentItemDragged = GAS_NAME;
		ClipboardContent content = new ClipboardContent();
		content.putString(GAS_NAME);
		db.setContent(content);
		event.consume();

	}

	/**
	 * Allows for NoEntry Items in the inventory to start being dragged.
	 * @param event The mouse clicking and dragging the NoEntry Item.
	 */
	@FXML
	private void startNoEntryDrag(MouseEvent event) {
		Dragboard db = noEntryImageView1.startDragAndDrop(TransferMode.ANY);
		currentItemDragged = NO_ENTRY_NAME;
		ClipboardContent content = new ClipboardContent();
		content.putString(NO_ENTRY_NAME);
		db.setContent(content);
		event.consume();

	}

	/**
	 * Allows for Poison Items in the inventory to start being dragged.
	 * @param event The mouse clicking and dragging the Poison Item.
	 */
	@FXML
	private void startPoisonDrag(MouseEvent event) {
		Dragboard db = poisonImageView1.startDragAndDrop(TransferMode.ANY);
		currentItemDragged = POISON_NAME;
		ClipboardContent content = new ClipboardContent();
		content.putString(POISON_NAME);
		db.setContent(content);
		event.consume();

	}

	/**
	 * Allows for MaleSexChange Items in the inventory to start being dragged.
	 * @param event The mouse clicking and dragging the MaleSexChange Item.
	 */
	@FXML
	private void startMaleSexChangeDrag(MouseEvent event) {
		Dragboard db = sexChangeMaleImageView1.startDragAndDrop(TransferMode.ANY);
		currentItemDragged = MALE_SEX_CHANGE_NAME;
		ClipboardContent content = new ClipboardContent();
		content.putString(MALE_SEX_CHANGE_NAME);
		db.setContent(content);
		event.consume();

	}

	/**
	 * Allows for FemaleSexChange Items in the inventory to start being dragged.
	 * @param event The mouse clicking and dragging the FemaleSexChange Item.
	 */
	@FXML
	private void startFemaleSexChangeDrag(MouseEvent event) {
		Dragboard db = sexChangeFemaleImageView1.startDragAndDrop(TransferMode.ANY);
		currentItemDragged = FEMALE_SEX_CHANGE_NAME;
		ClipboardContent content = new ClipboardContent();
		content.putString(FEMALE_SEX_CHANGE_NAME);
		db.setContent(content);
		event.consume();

	}

	/**
	 * Allows for Sterilisation Items in the inventory to start being dragged.
	 * @param event The mouse clicking and dragging the Sterilisation Item.
	 */
	@FXML
	private void startSterilisationDrag(MouseEvent event) {
		Dragboard db = sterilisationImageView1.startDragAndDrop(TransferMode.ANY);
		currentItemDragged = STERILISATION_NAME;
		ClipboardContent content = new ClipboardContent();
		content.putString(STERILISATION_NAME);
		db.setContent(content);
		event.consume();

	}

	/**
	 * Drags the Item's images on to the board.
	 * @param event The event of dragging the Item with the mouse.
	 */
	@FXML
	private void boardDragOver(DragEvent event) {
		if (event.getGestureSource() == bombImageView1
				|| event.getGestureSource() == deathRatImageView1
				|| event.getGestureSource() == gasImageView1
				|| event.getGestureSource() == noEntryImageView1
				|| event.getGestureSource() == poisonImageView1
				|| event.getGestureSource() == sexChangeMaleImageView1
				|| event.getGestureSource() == sexChangeFemaleImageView1
				|| event.getGestureSource() == sterilisationImageView1) {
			event.acceptTransferModes(TransferMode.ANY);
			event.consume();
		}

	}

	/**
	 * Allows the Item to be dropped and places the Item onto the Tile if it is a path.
	 * @param event The event of dropping the dragged image.
	 */
	@FXML
	private void dragDropped(DragEvent event) {
		double x = event.getX();
		double y = event.getY();
		// Get the position of the Tile dropped on, then covert from current view to actual position in the Level.
		int xPos =  (int) (currentTileStartingX + Math.floor(x / TILE_SIZE));
		int yPos =  (int) (currentTileStartingY + Math.floor(y / TILE_SIZE));
		Point tilePosToAddItem = new Point(xPos, yPos);

		if (game.getLevel().getTile(tilePosToAddItem).getItemOnTile() != null) {
			return;
		}

		// Checks which Item was dropped.
		// Gets the current Tile type.
		Tile.TileType currentTileType = game.getLevel().getTile(tilePosToAddItem).getTileType();
		switch(currentItemDragged) {
			case BOMB_NAME:
				if (game.getPlayer().getNumOfItem(BOMB_NAME) > 0) {
					Bomb bomb = new Bomb(tilePosToAddItem, game.getLevel(), game.getRats());

					if (currentTileType == Tile.TileType.PATH) {
						game.getLevel().getTile(tilePosToAddItem).setItemOnTile(bomb);
						game.getPlayer().removeItemFromInventory(bomb);
					}
				}
				break;
			case DEATH_RAT_NAME:
				if (game.getPlayer().getNumOfItem(DEATH_RAT_NAME) > 0) {
					DeathRat deathRat = new DeathRat(tilePosToAddItem, game.getLevel(), game.getRats());
					if (currentTileType == Tile.TileType.PATH) {
						game.getLevel().getTile(tilePosToAddItem).setItemOnTile(deathRat);
						game.getPlayer().removeItemFromInventory(deathRat);
					}
				}
				break;
			case GAS_NAME:
				if (game.getPlayer().getNumOfItem(GAS_NAME) > 0) {
					Gas gas = new Gas(tilePosToAddItem, game.getLevel(), game.getRats());
					if (currentTileType == Tile.TileType.PATH) {
						game.getLevel().getTile(tilePosToAddItem).setItemOnTile(gas);
						game.getPlayer().removeItemFromInventory(gas);
					}
				}
				break;
			case NO_ENTRY_NAME:
				if (game.getPlayer().getNumOfItem(NO_ENTRY_NAME) > 0) {
					NoEntry noEntry = new NoEntry(tilePosToAddItem, game.getLevel());
					if (currentTileType == Tile.TileType.PATH) {
						game.getLevel().getTile(tilePosToAddItem).setItemOnTile(noEntry);
						game.getPlayer().removeItemFromInventory(noEntry);
					}
				}
				break;
			case POISON_NAME:
				if (game.getPlayer().getNumOfItem(POISON_NAME) > 0) {
					Poison poison = new Poison(tilePosToAddItem, game.getLevel(), game.getRats());
					if (currentTileType == Tile.TileType.PATH) {
						game.getLevel().getTile(tilePosToAddItem).setItemOnTile(poison);
						game.getPlayer().removeItemFromInventory(poison);
					}
				}
				break;
			case MALE_SEX_CHANGE_NAME:
				if (game.getPlayer().getNumOfItem(MALE_SEX_CHANGE_NAME) > 0) {
					MaleSexChange maleSexChange = new MaleSexChange(tilePosToAddItem, game.getLevel());
					if (currentTileType == Tile.TileType.PATH) {
						game.getLevel().getTile(tilePosToAddItem).setItemOnTile(maleSexChange);
						game.getPlayer().removeItemFromInventory(maleSexChange);
					}
				}
				break;
			case FEMALE_SEX_CHANGE_NAME:
				if (game.getPlayer().getNumOfItem(FEMALE_SEX_CHANGE_NAME) > 0) {
					FemaleSexChange femaleSChange = new FemaleSexChange(tilePosToAddItem, game.getLevel());
					if (currentTileType == Tile.TileType.PATH) {
						game.getLevel().getTile(tilePosToAddItem).setItemOnTile(femaleSChange);
						game.getPlayer().removeItemFromInventory(femaleSChange);
					}
				}
				break;
			case STERILISATION_NAME:
				if (game.getPlayer().getNumOfItem(STERILISATION_NAME) > 0) {
					Sterilisation sterilisation = new Sterilisation(tilePosToAddItem, game.getLevel());
					if (currentTileType == Tile.TileType.PATH) {
						game.getLevel().getTile(tilePosToAddItem).setItemOnTile(sterilisation);
						game.getPlayer().removeItemFromInventory(sterilisation);
					}
				}
				break;
			default:
				break;
		}

		event.consume();
	}
}
