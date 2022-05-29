import java.awt.Point;
import java.util.ArrayList;

/**
 * Sterilisation.java
 * 
 * Responsible for the rat sterilisation logic.
 * 
 * @version 1.0
 * @since 19/11/2021
 * Last modified 03/12/2021
 * @author (REMOVED)
 */
public class Sterilisation extends Item {
    private static final int TOTAL_DURATION = 3;
    private static final int TICKS_PER_SECOND = 20;
    private static final int[][] AREA_TRANS = {{0, 1}, {0, 2}, {1, 1}, {1, 0},
    		{2, 0}, {1, -1}, {0, -1}, {0, -1}, {0, -2}, {-1, -1}, {-1, 0},
    		{-2, 0}, {-1, 1}};
    
    private ArrayList<Point> area;
    private int currentTick;

    /**
     * Constructor for a new Sterilisation object to be placed within the
     * player's inventory.
     * @param curLevel The current Level instance being used.
     */
    public Sterilisation(Level curLevel) {
        super(TOTAL_DURATION, curLevel);
    }

    /**
     * Constructor for a new Sterilisation object to be placed on the level.
     * @param coordinates The location of this bomb on the level.
     * @param curLevel The current Level instance being used.
     */
    public Sterilisation(Point coordinates, Level curLevel) {
        super(coordinates, TOTAL_DURATION, curLevel);
        area = new ArrayList<>();
        addTiles();
    } 

    /**
     * Deletes the object after a rat touches the object.
     */
    public void tick() {
        currentTick++;
        
        if (getDuration() <= 0) {
            getCurTile().removeItemOnTile();
            return;
        }
        
        if (currentTick % TICKS_PER_SECOND != 0) {
        	return;
        }
        
        setDuration(getDuration() - 1);
        
        area.forEach(tilePos -> {
            getCurLevel().getTile(tilePos).getRatsOnTile().forEach(rat -> {
            	rat.setSterile(true); 
            });	 
        });
    }

    /**
     * Adds this object to the "itemOnTile" parameter of all tile object which
     * are surrounding this object
     */
    private void addTiles() {
        for (int[] translate : AREA_TRANS) {
        	int x = (int) getCoordinates().getX() + translate[0];
        	int y = (int) getCoordinates().getY() + translate[1];
            Point curPoint = new Point(x, y);
            
            if (getCurLevel().isValidTile(curPoint)) {
                Tile newTile = getCurLevel().getTile(curPoint);
                
                if (newTile.getTileType() != Tile.TileType.GRASS) {
                    area.add(new Point(curPoint));
                }
            }
        }
    }
}
