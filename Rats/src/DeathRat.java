import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * DeathRat.java
 * 
 * Responsible for the movement logic of a single death rat item (and killing of other rats).
 * 
 * @version 1.0
 * @since 19/11/2021
 * Last modified 03/12/2021
 * @author (REMOVED)
 */
public class DeathRat extends Item {
	private static final int RAND_DIRECTION_NUM = 4;
	private static final int RAND_DIRECTION_3 = 3;
	private static final int RAND_DIRECTION_4 = 4;
    private static final int TOTAL_DURATION = 5;
    private static final int TICKS_PER_SECOND = 20;

    private transient RatManager ratManager;    
    private int stationary = 2;
    private int currentTick = 0;
    private int direction;

    /**
     * Constructor for a new DeathRat object to be placed within the player's inventory.
     * @param curLevel The current Level instance being used.
     * @param ratManager The current RatManager instance being used.
     */
    public DeathRat(Level curLevel, RatManager ratManager) {
        super(TOTAL_DURATION, curLevel);
        this.ratManager = ratManager;
    }

    /**
     * Constructor for a new DeathRat object to be placed on the level.
     * @param coordinates The location of this bomb on the level.
     * @param curLevel The current Level instance being used.
     * @param ratManager The current RatManager instance being used.
     */
    public DeathRat(Point coordinates, Level curLevel, RatManager ratManager) {
        super(coordinates, TOTAL_DURATION, curLevel);
        this.direction = randomNumber(RAND_DIRECTION_NUM);
        this.ratManager = ratManager;
    }
    
    /**
     * Converts the direction value to an orientation (North, East, South, West) and returns a character.
     * @return orientation In the form of a character.
     */
    public char getOrientation() {
        char orientation;
        switch (direction) {
            case 1:
                orientation = 'N';
                break;
            case 2:
                orientation = 'E';
                break;
            case RAND_DIRECTION_3:
                orientation = 'S';
                break;
            default:
                orientation = 'W';
        }
        return orientation;
    }

    /**
     * Each call, checks if the rat is in the stationary period and if not, moves the rat 1 tile. Kills any rat that
     * is on the same tile as the object.
     */
    public void tick() {
        currentTick++;
        if (currentTick % TICKS_PER_SECOND == 0) {
            stationary--;
        }
        if (stationary <= 0 && currentTick % (TICKS_PER_SECOND * 2) == 0) {
            getCurTile().removeItemOnTile();
            move();
            setCurTile(getCurLevel().getTile(getCoordinates()));
            getCurTile().setItemOnTile(this);
        }
        if (getCurTile().getNumberOfRatsOnTile() > 0) {
            ratManager.killRat(getCurTile().getRatOnTile(0));
            getCurTile().removeRat(0);
            setDuration(getDuration() - 1);
            if (getDuration() == 0) {
                getCurTile().removeItemOnTile();
            }
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
     * Generates a random integer between 1 and a maximum.
     * @param max The largest number to generate.
     * @return A random integer.
     */
    private int randomNumber(int max) {
        Random random = new Random();
        int randomNum = random.nextInt(max) + 1;
        return randomNum;
    }

    /**
     * Converts the direction of the rat into a new position.
     * @param newDir The direction the rat is moving in.
     * @return The point after moving a direction.
     */
    private Point newPos(int newDir) {
        Point pos = new Point(getCoordinates());
        if (newDir > RAND_DIRECTION_NUM) {
            newDir = newDir - RAND_DIRECTION_NUM;
        } else if (newDir < 1) {
            newDir = RAND_DIRECTION_NUM;
        }
        switch (newDir) {
            case 1:
                pos.translate(0, -1);
                break;
            case 2:
                pos.translate(1, 0);
                break;
            case RAND_DIRECTION_3:
                pos.translate(0, 1);
                break;
            case RAND_DIRECTION_4:
                pos.translate(-1, 0);
            default:
        }
        return pos;
    }

    /**
     * Calculates the next Tile Point that the rat can move to.
     */
    private void move() {
        ArrayList<Integer> newDirections = new ArrayList<>();
        Point newPos = newPos(direction);
        Tile newTile = getCurLevel().getTile(newPos);
        if (newTile.getItemOnTile() == null && newTile.getTileType() != Tile.TileType.GRASS) {
            newDirections.add(direction);
        } else if (newTile.getItemOnTile() instanceof NoEntry) {
            ((NoEntry) newTile.getItemOnTile()).contactMade();
        }
        newPos = newPos(direction - 1);
        newTile = getCurLevel().getTile(newPos);
        if (newTile.getItemOnTile() == null && newTile.getTileType() != Tile.TileType.GRASS) {
            newDirections.add(direction - 1);
        } else if (newTile.getItemOnTile() instanceof NoEntry) {
            ((NoEntry) newTile.getItemOnTile()).contactMade();
        }
        newPos = newPos(direction + 1);
        newTile = getCurLevel().getTile(newPos);
        if (newTile.getItemOnTile() == null && newTile.getTileType() != Tile.TileType.GRASS) {
            newDirections.add(direction + 1);
        } else if (newTile.getItemOnTile() instanceof NoEntry) {
            ((NoEntry) newTile.getItemOnTile()).contactMade();
        }
        if (newDirections.size() == 0) {
            newDirections.add(direction + 2);
        }
        int newDirection = newDirections.get(randomNumber(newDirections.size()) - 1);
        newPos = newPos(newDirection);
        setCoordinates(newPos);
        direction = newDirection;
        if (direction > RAND_DIRECTION_NUM) {
            direction = direction - RAND_DIRECTION_NUM;
        } else if (direction < 1) {
            direction = RAND_DIRECTION_NUM;
        }
    }
}
