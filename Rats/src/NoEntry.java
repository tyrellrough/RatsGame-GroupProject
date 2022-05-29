import java.awt.Point;

/**
 * NoEntry.java
 * 
 * Responsible for contact logic with rats.
 * 
 * @version 1.0
 * @since 19/11/2021
 * Last modified 03/12/2021
 * @author (REMOVED)
 */
public class NoEntry extends Item {
    private static final int TOTAL_DURATION = 5;

    /**
     * Constructor for a new NoEntry object to be placed within the player's
     * inventory.
     * @param curLevel The current Level instance being used.
     */
    public NoEntry(Level curLevel) {
        super(TOTAL_DURATION, curLevel);
    }

    /**
     * Constructor for a new NoEntry object to be placed on the level.
     * @param coordinates The location of this bomb on the level.
     * @param curLevel The current Level instance being used.
     */
    public NoEntry(Point coordinates, Level curLevel) {
        super(coordinates, TOTAL_DURATION, curLevel);
    }

    /**
     * Decreases the duration of the NoEntry object after contact is made from
     * a rat.
     */
    public void contactMade() {
        setDuration(getDuration() - 1);
    }

    /**
     * Deletes the object after the duration reaches 0.
     */
    public void tick() {
        if (getDuration() <= 0) {
            setDuration(getDuration() - 1);
            getCurTile().removeItemOnTile();
        }
    }
}
