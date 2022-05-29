import java.awt.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * DrawMiniMap.java
 *
 * DrawMiniMap is responsible for drawing the minimap tiles, minimap rats, and the current tile view
 * box.
 *
 * @version 1.8
 * @since 25/11/2021
 * Last modified 05/12/21
 * @author (REMOVED)
 */
public class DrawMiniMap {
	private static final int MINI_MAP_SIZE = 250;
	private static final float MINI_MAP_CURRENT_VIEW_BOX_LINE_WIDTH = 0.4f;
	private static final int TILES_IN_VIEW_EACH_AXIS = Board.getWidthOfTileViewInTiles();
	/**
	 * Pixels to offset rat drawing by.
	 */
	private static final int RAT_OFFSET = 4;
	
	/**
	 * Draw everything.
	 */
  	public static void drawAll(Level level, GraphicsContext gc, int currentTileStartingX, 
  			int currentTileStartingY) {
  		drawTiles(gc, level);
  		drawRats(gc, level);
  		drawCurrentViewBox(currentTileStartingX, currentTileStartingY, level, gc);
  	}
	  
	/**
	 * Draws minimap tiles.
	 */
	private static void drawTiles(GraphicsContext gc, Level level) {
		double miniMapTileHeight = MINI_MAP_SIZE / level.getHeight();
		double miniMapTileWidth = MINI_MAP_SIZE / level.getWidth();
		
		// Loop height.
		for (int y = 0; y < level.getHeight(); y++) {
			// Loop width.
			for (int x = 0; x <  level.getWidth(); x++) {
				Point currentTilePos = new Point(x, y);
				if (level.getTile(currentTilePos).getTileType() == Tile.TileType.GRASS) {
					gc.setFill(Color.GREEN);
            		gc.fillRect(x * miniMapTileHeight, y * miniMapTileWidth, miniMapTileHeight, miniMapTileWidth);
            		
				} else if (level.getTile(currentTilePos).getTileType() == Tile.TileType.PATH) {
            		gc.setFill(Color.GRAY);
            		gc.fillRect(x * miniMapTileHeight, y * miniMapTileWidth, miniMapTileHeight, miniMapTileWidth);
					
            	} else if (level.getTile(currentTilePos).getTileType() == Tile.TileType.TUNNEL) {
            		gc.setFill(Color.SANDYBROWN);
            		gc.fillRect(x * miniMapTileHeight, y * miniMapTileWidth, miniMapTileHeight, miniMapTileWidth);
            		
            	}
			}
		}
	}
	
	/**
	 * Draws rats as squares on the minimap.
	 */
	private static void drawRats(GraphicsContext gc, Level level) {
		double miniMapTileHeight = MINI_MAP_SIZE / level.getHeight();
		double miniMapTileWidth = MINI_MAP_SIZE / level.getWidth();
		/*
		 Off set rats on the tile, so they are centered.
		 */
		double heightOffSet = miniMapTileHeight / RAT_OFFSET;
		double widthOffSet = miniMapTileWidth / RAT_OFFSET;
		
		// Loops height.
		for (int y = 0; y < level.getHeight(); y++) {
			// Loops width.
			for (int x = 0; x <  level.getWidth(); x++) {
				Point p = new Point(x, y);
				// Number of rats on current tile.
				int numberOfRatsOnTile = level.getTile(p).getNumberOfRatsOnTile();
				// Check if there's more than 0 rats on the tile.
				if (numberOfRatsOnTile > 0) {
					// Loop through rats on tile.
					for (int i = 0; i < numberOfRatsOnTile; i++) {
						// Check if on tunnel then if rat is baby, if it isn't then check gender.
						if (level.getTile(p).getTileType() == Tile.TileType.TUNNEL) {
							// Do not draw the rat.
						} else if (level.getTile(p).getRatOnTile(i).isBaby()) {
							gc.setFill(Color.WHITE);
							gc.fillRect(x * miniMapTileHeight + heightOffSet, y 
									* miniMapTileWidth + widthOffSet,
									miniMapTileHeight/2, miniMapTileWidth/2);
						} else if (level.getTile(p).getRatOnTile(i).getSex() == 'm') {
							gc.setFill(Color.BLUE);
							gc.fillRect(x * miniMapTileHeight + heightOffSet, y 
									* miniMapTileWidth + widthOffSet,
									miniMapTileHeight/2, miniMapTileWidth/2);
							
						} else if (level.getTile(p).getRatOnTile(i).getSex() == 'f') {
							gc.setFill(Color.HOTPINK);
							gc.fillRect(x * miniMapTileHeight + heightOffSet, y 
									* miniMapTileWidth + widthOffSet,
									miniMapTileHeight/2, miniMapTileWidth/2);
						}
						
					}
					
				}
				
			}
		}
		
	}
	
	/**
	 * Draws a box showing the tiles in current view on the minimap.
	 */
	private static void drawCurrentViewBox(int currentTileStartingX, int currentTileStartingY,
			Level level, GraphicsContext gc) {
		double miniMapTileHeight = MINI_MAP_SIZE / level.getHeight();
		double miniMapTileWidth = MINI_MAP_SIZE / level.getWidth();
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(MINI_MAP_CURRENT_VIEW_BOX_LINE_WIDTH);
		// Left y line.
		gc.strokeLine(currentTileStartingX * miniMapTileWidth, currentTileStartingY * miniMapTileHeight, 
				currentTileStartingX * miniMapTileWidth, (currentTileStartingY 
						+ TILES_IN_VIEW_EACH_AXIS) * miniMapTileHeight);
		// Top x line.
		gc.strokeLine(currentTileStartingX * miniMapTileWidth, currentTileStartingY * miniMapTileHeight, 
				(currentTileStartingX + TILES_IN_VIEW_EACH_AXIS) 
				* miniMapTileWidth, currentTileStartingY * miniMapTileHeight);
		// Right y line.
		gc.strokeLine((currentTileStartingX + TILES_IN_VIEW_EACH_AXIS) 
				* miniMapTileWidth, currentTileStartingY * miniMapTileHeight, 
				(currentTileStartingX + TILES_IN_VIEW_EACH_AXIS) 
				* miniMapTileWidth, ((currentTileStartingY + TILES_IN_VIEW_EACH_AXIS) 
				* miniMapTileHeight));
		// Bottom x line.
		gc.strokeLine(currentTileStartingX * miniMapTileWidth, (currentTileStartingY 
				+ TILES_IN_VIEW_EACH_AXIS) * miniMapTileHeight, 
				(currentTileStartingX + TILES_IN_VIEW_EACH_AXIS) * miniMapTileWidth,
				(currentTileStartingY + TILES_IN_VIEW_EACH_AXIS) * miniMapTileHeight);
	}
}
