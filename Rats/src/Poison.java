import java.awt.Point;

/**
 * Poison.java
 * 
 * Responsible for the poisoning logic of rats.
 * 
 * @version 1.0
 * @since 19/11/2021
 * Last modified 03/12/2021
 * @author (REMOVED)
 */
public class Poison extends Item {
    private static final int TOTAL_DURATION = 1;
    
    private transient RatManager ratManager;

    /**
     * Constructor for a new Poison object to be placed within the player's
     * inventory.
     * @param curLevel The current Level instance being used.
     * @param ratManager The current RatManager instance being used.
     */
    public Poison(Level curLevel, RatManager ratManager) {
        super(TOTAL_DURATION, curLevel);
        this.ratManager = ratManager;
    }

    /**
     * Constructor for a new Poison object to be placed on the level.
     * @param coordinates The location of this bomb on the level.
     * @param curLevel The current Level instance being used.
     * @param ratManager The current RatManager instance being used.
     */
    public Poison(Point coordinates, Level curLevel, RatManager ratManager) {
        super(coordinates, TOTAL_DURATION, curLevel);
        this.ratManager = ratManager;
    }

    /**
     * Kills the first rat to touch this object.
     */
    public void tick() {
        if (getCurTile().getNumberOfRatsOnTile() > 0) {
            setDuration(getDuration() - 1);
            Rat contactRat = getCurTile().getRatOnTile(0);
            ratManager.killRat(contactRat);
            getCurTile().removeItemOnTile();
            getCurTile().removeRat(0);
        }
    }
    
    /**
     * Sets the reference to the RatManager instance after a game save has been
     * loaded.
     * @param rats The instance of RatManager currently being used.
     */
    public void setRats(RatManager rats) {
    	this.ratManager = rats;
    }
}
