import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * DrawLevel.java
 * 
 * DrawLevel is responsible for drawing the tiles, items, item effects, and rats.
 * 
 * @version 1.8
 * @since 25/11/2021
 * Last modified 04/12/21
 * @author (REMOVED)
 */
public class DrawLevel {
	private static final int BOARD_CANVAS_HEIGHT = 540;
	// This would be used if the board canvas isn't square.
	// private static final int BOARD_CANVAS_WIDTH = 540;
	private static final double TILE_SIZE = BOARD_CANVAS_HEIGHT / Board.getWidthOfTileViewInTiles();
    private static final char CHAR_NORTH = 'N';  
    private static final char CHAR_SOUTH = 'S'; 
    private static final char CHAR_EAST = 'E'; 
    private static final char CHAR_WEST = 'W'; 
	private static final int TILES_IN_VIEW_EACH_AXIS = Board.getWidthOfTileViewInTiles();
	private static final int BABY_RAT_OFFSET = 5;
	private static final int STERILISATION_CIRCLE_DIAMETER = 3;
	private static final int PLAYER_VIEW_NUM_TILES_SHOWN = 10;
	private static final double STERILISATION_TRANSPARENCY_VALUE = 0.5;
	private static final int ITEM_DURATION_FIVE = 5;
	private static final int ITEM_DURATION_FOUR = 4;
	private static final int ITEM_DURATION_THREE = 3;
	private static final int ITEM_DURATION_TWO = 2;
	private static final int ITEM_DURATION_ONE = 1;
	
	/**
	 * Draws everything.
	 */
  	public static void drawAll(Game game, GraphicsContext gc,
  			int currentTileStartingX, int currentTileStartingY, Tile[][] currentTilesView) {
  		drawTiles(game, currentTilesView, gc);
  		drawRats(game.getGraphics(), currentTileStartingY, currentTileStartingY, currentTilesView, gc);
  		drawItems(game.getGraphics(), currentTilesView, gc);
  	}
	  
	/**
	 * Draws items.
	 * @param currentTilesView The 2d array of all tiles in view.
	 * @param gc The graphics context to draw the Items.
	 */
	private static void drawItems(Graphics graphics, Tile[][] currentTilesView, GraphicsContext gc) {
		// Loops the height of the current tiles.
		for (int y = 0; y < TILES_IN_VIEW_EACH_AXIS; y++) {
			// Loops the width of the current tiles.
			for (int x = 0; x <  TILES_IN_VIEW_EACH_AXIS; x++) {
				// Checks if a bomb has exploded on any tiles and draw effect.
				if (currentTilesView[y][x].isExploded()) {
					gc.drawImage(graphics.getImage("Explosion"),  x * TILE_SIZE, y * TILE_SIZE);
				}
				// Checks if a gas item has gassed any tiles and draw effect.
				if (currentTilesView[y][x].isGassed()) {
					gc.drawImage(graphics.getImage("Gas"),  x * TILE_SIZE, y * TILE_SIZE);
				}
				// Checks if there's an item on the tile.
				if (currentTilesView[y][x].getItemOnTile() != null) {
					Item itemOnTile = currentTilesView[y][x].getItemOnTile();
					if (itemOnTile instanceof Bomb) {
						int bombCountDown = ((Bomb) itemOnTile).getDuration();
						switch (bombCountDown) {
							case ITEM_DURATION_FIVE:
								gc.drawImage(graphics.getImage("Bomb"),  
										x * TILE_SIZE, y * TILE_SIZE);
								break;
							case ITEM_DURATION_FOUR:
								gc.drawImage(graphics.getImage("Bomb4"),  
										x * TILE_SIZE, y * TILE_SIZE);
								break;
							case ITEM_DURATION_THREE:
								gc.drawImage(graphics.getImage("Bomb3"), 
										x * TILE_SIZE, y * TILE_SIZE);
								break;
							case ITEM_DURATION_TWO :
								gc.drawImage(graphics.getImage("Bomb2"), 
										x * TILE_SIZE, y * TILE_SIZE);
								break;
							case ITEM_DURATION_ONE:
								gc.drawImage(graphics.getImage("Bomb1"),  
										x * TILE_SIZE, y * TILE_SIZE);
								break;
							default:
								break;
						}
					}
					if (itemOnTile instanceof DeathRat) {
						char orientation = ((DeathRat) currentTilesView[y][x].getItemOnTile())
								.getOrientation();
						if (currentTilesView[y][x].getTileType() == Tile.TileType.TUNNEL) {
							// Do nothing.
						} else {
							switch (orientation) {
							case CHAR_NORTH:
								gc.drawImage(graphics.getImage("DeathRatNorth"),
										x * TILE_SIZE, y * TILE_SIZE);
								break;
							case CHAR_SOUTH:
								gc.drawImage(graphics.getImage("DeathRatSouth"),
										x * TILE_SIZE, y * TILE_SIZE);
								break;
							case CHAR_EAST:
								gc.drawImage(graphics.getImage("DeathRatEast"),
										x * TILE_SIZE, y * TILE_SIZE);
								break;
							case CHAR_WEST:
								gc.drawImage(graphics.getImage("DeathRatWest"),
										x * TILE_SIZE, y * TILE_SIZE);
								break;
							default:
								break;
							} 
						}
					} 
					if (itemOnTile instanceof Gas) {
						gc.drawImage(graphics.getImage("Gas"), x * TILE_SIZE, y * TILE_SIZE);
					} 
					if (itemOnTile instanceof NoEntry) {
						int duration = currentTilesView[y][x].getItemOnTile().getDuration();
						switch (duration) {
						case ITEM_DURATION_FIVE: 
							gc.drawImage(graphics.getImage(
									"NoEntry"), x * TILE_SIZE, y * TILE_SIZE);
							break;
						case ITEM_DURATION_FOUR:
							gc.drawImage(graphics.getImage(
									"NoEntry"), x * TILE_SIZE, y * TILE_SIZE);
							break;
						case ITEM_DURATION_THREE:
							gc.drawImage(graphics.getImage(
									"NoEntry3"), x * TILE_SIZE, y * TILE_SIZE);
							break;
						case ITEM_DURATION_TWO:
							gc.drawImage(graphics.getImage(
									"NoEntry2"), x * TILE_SIZE, y * TILE_SIZE);
							break;
						case ITEM_DURATION_ONE:
							gc.drawImage(graphics.getImage(
									"NoEntry1"), x * TILE_SIZE, y * TILE_SIZE);
							break;
						default:
							break;
						}
					} 
					if (itemOnTile instanceof Poison) {
						gc.drawImage(graphics.getImage("Poison"), x * TILE_SIZE, y * TILE_SIZE);
					}
					if (itemOnTile instanceof MaleSexChange) {
						gc.drawImage(graphics.getImage(
								"SexChangeMale"), x * TILE_SIZE, y * TILE_SIZE);
					}
					if (itemOnTile instanceof FemaleSexChange) {
						gc.drawImage(graphics.getImage(
								"SexChangeFemale"), x * TILE_SIZE, y * TILE_SIZE);
					} 
					if (currentTilesView[y][x].getItemOnTile() instanceof Sterilisation) {
						gc.drawImage(graphics.getImage(
								"Sterilisation"), x * TILE_SIZE, y * TILE_SIZE);
						gc.setFill(Color.YELLOW);
						//transparency.
						gc.setGlobalAlpha(STERILISATION_TRANSPARENCY_VALUE);
						gc.fillOval((x - 1) * TILE_SIZE, (y - 1) * TILE_SIZE, TILE_SIZE 
								* STERILISATION_CIRCLE_DIAMETER, TILE_SIZE 
								* STERILISATION_CIRCLE_DIAMETER);
						//transparency.
						gc.setGlobalAlpha(1.0);
					}
					
			
				}
		
			}
		}
	}

	/**
	 * Draws the viewable Tiles for the Level.
	 * @param game The current Game.
	 * @param currentTilesView The Tiles in view for the scrolling and such.
	 * @param gc The graphics needed to draw the tiles.
	 */
	private static void drawTiles(Game game, Tile[][] currentTilesView, GraphicsContext gc) {
		// Displays the currently viewable tiles.
		for (int y = 0; y < TILES_IN_VIEW_EACH_AXIS; y++) {
			for (int x = 0; x < TILES_IN_VIEW_EACH_AXIS; x++) {
				if (currentTilesView[y][x].getTileType() == Tile.TileType.GRASS) {
					gc.drawImage(game.getGraphics().getImage("GrassTile"), x * TILE_SIZE,
							y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
				} else if (currentTilesView[y][x].getTileType() == Tile.TileType.PATH) {
					gc.drawImage(game.getGraphics().getImage("PathTile"), x * TILE_SIZE,
							y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            	} else if (currentTilesView[y][x].getTileType() == Tile.TileType.TUNNEL) {
            		gc.drawImage(game.getGraphics().getImage("TunnelTile"), x * TILE_SIZE,
            				y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            	}
			}
		}
		
	}
	
	/**
	 * Draws rats on the currently viewable tiles.
	 */
	private static void drawRats(Graphics graphics, int currentTileStartingX,
			int currentTileStartingY, Tile[][] currentTilesView, GraphicsContext gc) {
		// Loop through the currently viewable tiles.
		for (int y = 0; y < PLAYER_VIEW_NUM_TILES_SHOWN; y++) {
			for (int x = 0; x <  PLAYER_VIEW_NUM_TILES_SHOWN; x++) {
				// Number of rats on current tile.
				int numberOfRatsOnTile = currentTilesView[y][x].getNumberOfRatsOnTile();
				// Checks if there's more than 0 rats on the tile.
				if (numberOfRatsOnTile > 0) {
					// Loop through rats on tile.
					for (int i = 0; i < numberOfRatsOnTile; i++) {
						// Checks if rats are in tunnels then rat is baby, else check gender.
						if (currentTilesView[y][x].getTileType() == Tile.TileType.TUNNEL) {
							// Do nothing.
						} else if (currentTilesView[y][x].getRatOnTile(i).isBaby()) {
							// Draws baby rats.
							char orientation = currentTilesView[y][x]
									.getRatOnTile(i).getOrientation();
							switch (orientation) {
							case CHAR_NORTH:
								gc.drawImage(graphics.getImage("BabyRatNorth"), x * TILE_SIZE
										+ BABY_RAT_OFFSET, y * TILE_SIZE + BABY_RAT_OFFSET);
								break;
							case CHAR_SOUTH:
								gc.drawImage(graphics.getImage("BabyRatSouth"), x * TILE_SIZE
										+ BABY_RAT_OFFSET, y * TILE_SIZE + BABY_RAT_OFFSET);
								break;
							case CHAR_EAST:
								gc.drawImage(graphics.getImage("BabyRatEast"), x * TILE_SIZE
										+ BABY_RAT_OFFSET, y * TILE_SIZE + BABY_RAT_OFFSET);
								break;
							case CHAR_WEST:
								gc.drawImage(graphics.getImage("BabyRatWest"), x * TILE_SIZE
										+ BABY_RAT_OFFSET, y * TILE_SIZE + BABY_RAT_OFFSET);
								break;
							} 
						} else if (currentTilesView[y][x].getRatOnTile(i).getSex() == 'm') {
							// Draws male rats.
							char orientation = currentTilesView[y][x].getRatOnTile(i).getOrientation();
							switch (orientation) {
								case CHAR_NORTH:
									gc.drawImage(graphics.getImage("MaleRatNorth"), x * TILE_SIZE,
											y * TILE_SIZE);
									break;
								case CHAR_SOUTH:
									gc.drawImage(graphics.getImage("MaleRatSouth"), 
											x * TILE_SIZE, y * TILE_SIZE);
									break;
								case CHAR_EAST:
									gc.drawImage(graphics.getImage("MaleRatEast"),
											x * TILE_SIZE, y * TILE_SIZE);
									break;
								case CHAR_WEST:
									gc.drawImage(graphics.getImage("MaleRatWest"),
											x * TILE_SIZE, y * TILE_SIZE);
									break;
								default:
									break;
							} 
						} else if (currentTilesView[y][x].getRatOnTile(i).getSex() == 'f') {
							// Draws female rats.
							char orientation = currentTilesView[y][x].getRatOnTile(i).getOrientation();
							switch (orientation) {
								case CHAR_NORTH:
									gc.drawImage(graphics.getImage("FemaleRatNorth"),
											x * TILE_SIZE, y * TILE_SIZE);
									break;
								case CHAR_SOUTH:
									gc.drawImage(graphics.getImage("FemaleRatSouth"),
											x * TILE_SIZE, y * TILE_SIZE);
									break;
								case CHAR_EAST:
									gc.drawImage(graphics.getImage("FemaleRatEast"),
											x * TILE_SIZE, y * TILE_SIZE);
									break;
								case CHAR_WEST:
									gc.drawImage(graphics.getImage("FemaleRatWest"),
											x * TILE_SIZE, y * TILE_SIZE);
									break;
								default:
									break;
							}
						} 
					}
					
				}
			}
		}
				
	}
	
	
}
