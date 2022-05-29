import java.util.ArrayList;

/**
 * Tile.java
 *
 * A tile is a single unit of a level.
 *
 * @version 1.4
 * @since 12/11/21
 * Last modified 04/12/21
 * @author (REMOVED)
*/
public class Tile {
    /**
     * Types of tiles.
     */
    enum TileType {
        GRASS, PATH, TUNNEL
    }

    private static final char CHAR_TILE_TYPE_GRASS = 'G';
    private static final char CHAR_TILE_TYPE_PATH = 'P';
    private static final char CHAR_TILE_TYPE_TUNNEL = 'T';
    
    private Item itemOnTile;
    private ArrayList<Rat> ratsOnTile;
    private TileType type;
    private boolean isTraversable;
    private boolean isInteractable;
    private boolean isVisible;
    private boolean isExploded;
    private boolean isGassed;
    
    /**
     * Constructs a single tile.
     * @param tileType The character representation of the type of tile.
     */
    public Tile(char tileType) {
    	ratsOnTile = new ArrayList<Rat>();

        switch (tileType) {
            case CHAR_TILE_TYPE_GRASS:
                type = TileType.GRASS;
                isVisible = true;
                break;
            case CHAR_TILE_TYPE_PATH:
                type = TileType.PATH;
                isTraversable = true;
                isInteractable = true;
                isVisible = true;
                break;
            case CHAR_TILE_TYPE_TUNNEL:
            	type = TileType.TUNNEL;
                isTraversable = true;
                break;
            default:
                break;
        }
    }
    
    /**
     * Clears the tile of Rats and the Item is present.
     */
    public void clearTile() {
        ratsOnTile.clear();
        itemOnTile = null;
    }

    /**
     * Returns the Item on this Tile.
     * @return The Item instance currently on this Tile.
     */
    public Item getItemOnTile() {
        return itemOnTile;
    }

    /**
     * Sets the given Item on this Tile.
     * @param item The Item to put on this Tile.
     */
    public void setItemOnTile(Item item) {
        itemOnTile = item;
    }
    
    /**
     * Removes the Item on this Tile.
     */
    public void removeItemOnTile() {
        itemOnTile = null;
    }
    
    /**
     * Gets the Rat on this Tile at a specific index.
     * @return The Rat instance if it exists.
     */
    public Rat getRatOnTile(int ratIndex) {
        return ratsOnTile.get(ratIndex);
    }
    
    /**
     * Gets all Rats on this Tile.
     * @return A list of Rats on this Tile.
     */
    public ArrayList<Rat> getRatsOnTile() {
        return ratsOnTile;
    }

    /**
     * Adds a Rat to this Tile.
     * @param rat The Rat instance to be added.
     */
    public void addRat(Rat rat) {
        ratsOnTile.add(rat);
    }

    /**
     * Remove a Rat from this Tile.
     * @param index The index of Rat to remove.
     */
    public void removeRat(int index) {
        ratsOnTile.remove(index);
    }

    /**
     * Removes all Rats on this Tile.
     */
    public void removeAllRats() {
        ratsOnTile.clear();
    }

    /**
     * Gets the number of Rats on this Tile.
     * @return The number of Rats.
     */
    public int getNumberOfRatsOnTile() {
        return ratsOnTile.size();
    }
    
    /**
     * Gets the type of this Tile.
     * @return The Tile type.
     */
    public Tile.TileType getTileType() {
        return type;
    }
    
    /**
     * Checks if this Tile is traversable by a Rat.
     * @return True or False on whether this Tile can be traversed.
     */
    public boolean isTraversable() {
        return isTraversable;
    }

    /**
     * Checks if this Tile is interactable with an Item.
     * @return True or false on whether this Tile can be interacted with.
     */
    public boolean isInteractable() {
        return isInteractable;
    }

    /**
     * Checks whether Rats on this Tile are visible.
     * @return Whether Rats are visible on this Tile.
     */
    public boolean isVisible() {
        return isVisible;
    }
    
    /**
     * Checks if an explosion has happened on this Tile.
     * @return True or false on whether this Tile is currently exploded.
     */
    public boolean isExploded() {
        return isExploded;
    }

    /**
     * Sets the exploded status of this Tile.
     * @param exploded True or false on whether this Tile should be exploded.
     */
    public void setExploded(boolean exploded) {
        this.isExploded = exploded;
    }

    /**
     * Checks if this Tile has been gassed.
     * @return True or false on whether this Tile is currently gassed.
     */
    public boolean isGassed() {
        return isGassed;
    }

    /**
     * Sets the gassed status of this Tile.
     * @param gassed True or false on whether this Tile should be gassed.
     */
    public void setGassed(boolean gassed) {
        this.isGassed = gassed;
    }
}
