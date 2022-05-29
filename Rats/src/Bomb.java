import java.awt.Point;
import java.util.ArrayList;

/**
 * Bomb.java
 *
 * Responsible for the countdown/explosion logic of a bomb (and killing of other rats/items).
 *
 * @version 1.0
 * @since 19/11/2021
 * Last modified 03/12/2021
 * @author (REMOVED)
 */
public class Bomb extends Item {
    private static final int TOTAL_DURATION = 5;
    private static final int TICKS_PER_SECOND = 20;

    private transient RatManager ratManager;
    private ArrayList<Point> area;
    private int currentTick = 0;
    private boolean dBounce;

    /**
     * Constructor for a new Bomb object to be placed within the player's inventory.
     * @param curLevel The current Level instance being used.
     * @param ratManager The current RatManager instance being used.
     */
    public Bomb(Level curLevel, RatManager ratManager) {
        super(TOTAL_DURATION, curLevel);
        this.ratManager = ratManager;
    }

    /**
     * Constructor for a new Bomb object to be placed on the level.
     * @param coordinates The location of this bomb on the level.
     * @param curLevel The current Level instance being used.
     * @param ratManager The current RatManager instance being used.
     */
    public Bomb(Point coordinates, Level curLevel, RatManager ratManager) {
        super(coordinates, TOTAL_DURATION, curLevel);
        this.ratManager = ratManager;
        area = new ArrayList<Point>();
        dBounce = true;
    }

    /**
     * Every 20 times this is called duration will decrease until the Tiles in the area ArrayList.
     */
    public void tick() {
        currentTick++;
        if (currentTick % TICKS_PER_SECOND == 0) {
            setDuration(getDuration() - 1);
        }

        if (getDuration() == 0 && dBounce) {
            dBounce = false;
            getTiles(1, 0);
            getTiles(-1, 0);
            getTiles(0, 1);
            getTiles(0, -1);
            area.forEach(tilePos -> {
                Tile tile = getCurLevel().getTile(tilePos);
                tile.setExploded(true);
                tile.getRatsOnTile().forEach(rat ->  ratManager.killRat(rat));
                tile.clearTile();
            });
            getCurTile().removeAllRats();
            getCurTile().setExploded(true);
        }

        if (getDuration() == -1) {
            area.forEach(tilePos -> {
                getCurLevel().getTile(tilePos).setExploded(false);
            });
            getCurTile().setExploded(false);
            getCurTile().clearTile();
        }
    }

    /**
     * Sets the reference to the RatManager instance after a game save has been loaded.
     * @param rats The instance of RatManager currently being used.
     */
    public void setRats(RatManager rats) {
        ratManager = rats;
    }

    /**
     * Adds a Tile object to the list of tiles the bomb will explode, if not a grass tile.
     * @param changeX The difference between the original X position and tile.
     * @param changeY The difference between the original Y position and tile.
     */
    private void getTiles(int changeX, int changeY) {
        Point curPoint = new Point((int) getCoordinates().getX() + changeX, (int) getCoordinates().getY() + changeY);
        Tile curTile = getCurLevel().getTile(curPoint);
        while (getCurLevel().isValidTile(curPoint) && curTile.getTileType() != Tile.TileType.GRASS) {
            if (!area.contains(new Point(curPoint))) {
                area.add(new Point(curPoint));
            }
            curPoint.setLocation(curPoint.getX() + changeX, curPoint.getY() + changeY);
            curTile = getCurLevel().getTile(curPoint);
        }
    }
}
