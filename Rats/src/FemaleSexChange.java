import java.awt.Point;

/**
 * FemaleSexChange.java
 * 
 * Responsible for the rat sex change to female logic.
 * 
 * @version 1.0
 * @since 26/11/2021
 * Last modified 03/12/2021
 * @author (REMOVED)
 */
public class FemaleSexChange extends Item {
    private static final int TOTAL_DURATION = 1;

    /**
     * Constructor for a new FemaleSexChange object to be placed within the player's inventory.
     * @param curLevel The current Level instance being used.
     */
    public FemaleSexChange(Level curLevel) {
        super(TOTAL_DURATION, curLevel);
    }

    /**
     * Constructor for a new FemaleSexChange object to be placed on the level.
     * @param coordinates The location of this bomb on the level.
     * @param curLevel The current Level instance being used.
     */
    public FemaleSexChange(Point coordinates, Level curLevel) {
        super(coordinates, TOTAL_DURATION, curLevel);
    }

    /**
     * Changes the sex of the first rat to female when a rat touches this object.
     */
    public void tick() {
        if (getCurTile().getNumberOfRatsOnTile() > 0) {
            setDuration(getDuration() - 1);
            Rat contactRat = getCurTile().getRatOnTile(0);
            contactRat.setSex('f');
            getCurTile().removeItemOnTile();
        }
    }
}
