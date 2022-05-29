import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Player.java
 *
 * Handles the score from the current game and inventory management.
 *
 * @version 1.2
 * @since 19/11/21
 * Last modified 28/11/21
 * @author (REMOVED)
 */
public class Player {
    private static final int MAX_ITEMS = 4;
    private static  final int POINTS_PER_RAT = 10;

    private ArrayList<Item> inventory = new ArrayList<>();
    private int score;

    /**
     * Adds another Item to the player's inventory, if there is space.
     * @param newItem The item to add to the inventory.
     */
    public void addItemToInventory(Item newItem) {
        if (!isItemFull(newItem)) {
            this.inventory.add(newItem);
        }
    }

    /**
     * Removes an Item from the player's inventory.
     * @param itemToRemove The Item to be removed from the player's inventory.
     */
    public void removeItemFromInventory(Item itemToRemove) {
        String nameOfRemovableItem = itemToRemove.getClass().getName();
        if (doesItemExist(nameOfRemovableItem)) {
            Item itemDelete = itemToRemove;
            for (Item eachItem : this.inventory) {
                String eachItemName = eachItem.getClass().getName();
                if (eachItemName.equals(nameOfRemovableItem)) {
                    itemDelete = eachItem;
                }
            }

            this.inventory.remove(itemDelete);
        }

    }

    /**
     * Checks if an Item exist inside the inventory.
     * @param itemToCheck The given item to see if it exists in the inventory.
     * @return The true or false on whether the item exists in the
     * inventory.
     */
    public boolean doesItemExist(String itemToCheck) {
        return getNumOfItem(itemToCheck) != 0;
    }

    /**
     * Gets the number of that specific item from the inventory.
     * @param itemToCheck The specific item to see how many there are in the
     * inventory.
     * @return The number of times the item is inside the inventory.
     */
    public int getNumOfItem(String itemToCheck) {
        Stream<Item> items = inventory.stream().filter(item -> {
        	return item.getClass().getName().equals(itemToCheck);
        });
        
        return items.collect(Collectors.toList()).size();
    }

    /**
     * Calculates the player's current score and their bonus points are
     * included.
     * @param game The entire game.
     * @param completed Whether the Player has completed the Level.
     */
    public void calculateScore(Game game, boolean completed) {
    	int totalDeadRats = game.getRats().getTotalDeadRats() * POINTS_PER_RAT;
    	int bonus = game.getLevel().getExpectedCompletionTime();
    	bonus -= game.getLevel().getTimePlayed();
    	
    	score = totalDeadRats + (completed ? (bonus >= 0 ? bonus : 0) : 0);
    }
    
    /**
     * Gets the current score.
     * @return The current score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the inventory with all the Items inside it.
     * @return The inventory of Items.
     */
    public ArrayList<Item> getInventoryItems() {
        return this.inventory;
    }
    
    /**
     * Checks if there are 4 of the specific Item already in the player's
     * inventory.
     * @param item The Item to check for.
     * @return True or False on whether there are already 4 of that
     * Item in the inventory.
     */
    private boolean isItemFull(Item item) {
        return getNumOfItem(item.getClass().getName()) == MAX_ITEMS;
    }
}
