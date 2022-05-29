import java.awt.Point;

/**
 * Item.java
 * 
 * Represents an item which can be placed within the player's inventory or on
 * a level tile.
 * 
 * @version 1.0
 * @since 19/11/2021
 * Last modified 01/12/2021
 * @author (REMOVED)
 */
public abstract class Item {
	private final String type = getClass().getName();
	
    private Point coordinates;
    private int duration;
    private transient Level curLevel;
    private transient Tile curTile;
    
    /**
     * Constructor for an item object to be placed within the player's
     * inventory.
     * @param duration The duration/durability of the item.
     * @param curLevel The Level instance currently being used.
     */
    public Item(int duration, Level curLevel) {
        this.duration = duration;
        this.curLevel = curLevel;
        this.coordinates = null;
        this.curTile = null;
    }

    /**
     * Constructor for an item object to be placed within the level.
     * @param coordinates The location at which the item is placed on the level.
     * @param duration The duration/durability of the item.
     * @param curLevel The Level instance currently being used.
     */
    public Item(Point coordinates, int duration, Level curLevel) {
        this.duration = duration;
        this.curLevel = curLevel;
        this.coordinates = coordinates;
        this.curTile = curLevel.getTile(coordinates);
    }

    /**
     * Returns the current duration/durability value.
     * @return The integer duration/durability value.
     */
    public int getDuration() {
        return duration;
    }
    
    /**
     * Returns the current coordinates value.
     * @return The current coordinates value.
     */
    public Point getCoordinates() {
        return coordinates;
    }
    
    /**
     * Sets the reference to the Level instance after a game save has been
     * loaded. Also sets the current tile by
     * calling 'getTile' on the Level instance.
     * @param curLevel The instance of Level currently being used.
     */
    public void setTransientReferences(Level curLevel) {
    	this.curLevel = curLevel;
    	curTile = curLevel.getTile(coordinates);
    }

    /**
     * Returns the type of this item (ie. the name of the subclass).
     * @return The type of this item as a string.
     */
	public String getType() {
		return type;
	}

    /**
     * A method for subclasses to implement which is called on every tick.
     */
    public abstract void tick();

    /**
     * Returns the current level this item is on.
     * @return The current Level instance.
     */
    protected Level getCurLevel() {
        return curLevel;
    }

    /**
     * Sets the current duration/durability of this item.
     * @param duration The integer duration/durability value.
     */
    protected void setDuration(int duration) {
    	this.duration = duration;
    }
    
    /**
     * Returns the current tile this item is on.
     * @return The current Tile instance.
     */
	protected Tile getCurTile() {
		return curTile;
	} 
    
    /**
     * Sets the current tile of this item.
     * @param curTile The new current tile.
     */
    protected void setCurTile(Tile curTile) {
    	this.curTile = curTile;
    }
    
    /**
     * Sets the current coordinates of this item.
     * @param coordinates The new coordinates.
     */
    protected void setCoordinates(Point coordinates) {
    	this.coordinates = coordinates;
    }
}
