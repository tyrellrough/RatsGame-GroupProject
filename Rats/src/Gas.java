import java.awt.Point;
import java.util.ArrayList;

/**
 * Gas.java
 * 
 * Responsible for gas expansion/dissipation logic (and killing of other rats).
 * 
 * @version 1.0
 * @since 19/11/2021
 * Last modified 03/12/2021
 * @author (REMOVED)
 */
public class Gas extends Item {
	private static final int RAND_CONST_3 = 3;
	private static final int RAND_CONST_4 = 4;
	private static final int RAND_CONST_5 = 5;
	private static final int RAND_CONST_6 = 6;
	private static final int RAT_EXPOSURE_TIME = 40;
    private static final int TOTAL_DURATION = 6;
    private static final int[][] AREA_TRANS1 = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    private static final int[][] AREA_TRANS2 = {{0, 2}, {1, 1}, {2, 0}, {1, -1}, {0, -2}, {-1, -1}, {-2, 0}, {-1, 1}};
    private static final int[][] AREA_TRANS3 = {{0, 3}, {1, 2}, {2, 1}, {3, 0}, {2, -1}, {1, -2},
    		{0, -3}, {-1, -2}, {-2, -1}, {-3, 0}, {-2, 1}, {-1, 2}};
    
    private transient ArrayList<Rat> ratsInGas;
    private transient RatManager ratManager;
    private ArrayList<Point> area;
    private int currentTick;

    /**
     * Constructor for a new Gas object to be placed within the player's inventory.
     * @param curLevel The current Level instance being used.
     * @param ratManager The current RatManager instance being used.
     */
    public Gas(Level curLevel, RatManager ratManager) {
        super(TOTAL_DURATION, curLevel);
        this.ratManager = ratManager;
    }

    /**
     * Constructor for a new Gas object to be placed on the level.
     * @param coordinates The location of this bomb on the level.
     * @param curLevel The current Level instance being used.
     * @param ratManager The current RatManager instance being used.
     */
    public Gas(Point coordinates, Level curLevel, RatManager ratManager) {
        super(coordinates, TOTAL_DURATION, curLevel);
        this.ratManager = ratManager;
        ratsInGas = new ArrayList<>();
        area = new ArrayList<>();
    }

    /**
     * Each time called the gas may expand, disperse or be completely removed. Kills any rat that is in the gas for too
     * long.
     */
    public void tick() {
        currentTick++;
        if (currentTick % Game.TPS == 0) {
            // NOTE: Checkstyle considered these to be a magic numbers in this switch so had to be a constant, but
        	// none 1 or 2 wasn't flagged.
            switch (getDuration()) {
                case RAND_CONST_6:
                    addTiles(AREA_TRANS1);
                    break;
                case RAND_CONST_5:
                    addTiles(AREA_TRANS2);
                    break;
                case RAND_CONST_4:
                    addTiles(AREA_TRANS3);
                    break;
                case RAND_CONST_3:
                    remTiles(AREA_TRANS3);
                    break;
                case 2:
                    remTiles(AREA_TRANS2);
                    break;
                case 1:
                    remTiles(AREA_TRANS1);
                    getCurTile().removeItemOnTile();
                default:
            }
            setDuration(getDuration() - 1);
        }
        
        ratsInGas.clear();
        area.forEach(tilePos -> {
        	Tile tile = getCurLevel().getTile(tilePos);
        	ratsInGas.addAll(tile.getRatsOnTile());
        });
        
        ratsInGas.forEach(rat -> {
            rat.setGasExposureTime(rat.getGasExposureTime() + 1);
            if (rat.getGasExposureTime() >= RAT_EXPOSURE_TIME) {
            	Tile ratLoc = getCurLevel().getTile(rat.getLocation());
                ratLoc.removeRat(ratLoc.getRatsOnTile().indexOf(rat));
                ratManager.killRat(rat);
            }
        });
    }
    
    /**
     * Sets the reference to the RatManager instance after a game save has been loaded.
     * @param rats The instance of RatManager currently being used.
     */
    public void setRats(RatManager rats) {
    	this.ratManager = rats;
    }
    
    /**
     * This will add tiles to the "area" Arraylist where each tile is translated from the main object and adds this
     * object to those tiles.
     * @param areaTrans A 2d array of integers representing the Change in X and Y from the original point.
     */
    private void addTiles(int[][] areaTrans) {
        for (int[] translate : areaTrans) {
        	int x = (int) getCoordinates().getX() + translate[0];
        	int y = (int) getCoordinates().getY() + translate[1];
        	Point curPoint = new Point(x, y);
        	
            if (getCurLevel().isValidTile(curPoint)) {
                Tile newTile = getCurLevel().getTile(curPoint);
                if (newTile.getTileType() != Tile.TileType.GRASS && newTile.getItemOnTile() == null) {
                    if (newTile.getTileType() != Tile.TileType.TUNNEL) {
                        newTile.setGassed(true);
                    }
                    area.add(new Point(curPoint));
                }
            }
        }
    }

    /**
     * This will remove tiles to the "area" Arraylist where each tile is translated from the main object and removes
     * this object to those tiles.
     * @param areaTrans A 2d array of integers representing the Change in X and Y from the original point.
     */
    private void remTiles(int[][] areaTrans) {
        for (int[] translate : areaTrans) {
        	int x = (int) getCoordinates().getX() + translate[0];
        	int y = (int) getCoordinates().getY() + translate[1];
        	Point curPoint = new Point(x, y);
            
            if (!getCurLevel().isValidTile(curPoint)) {
            	continue;
            }
            
            Tile newTile = getCurLevel().getTile(curPoint);
            
            if (newTile.getTileType() != Tile.TileType.GRASS && newTile.getItemOnTile() == null) {
                if (newTile.getTileType() != Tile.TileType.TUNNEL) {
                    newTile.setGassed(false);
                }
                
                area.remove(curPoint);
            }
        }
    }
}
