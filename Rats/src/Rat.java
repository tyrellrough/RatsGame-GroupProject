import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Rat.java
 *
 * Holds attributes related to a rat and handles level navigation.
 *
 * @version 1.0
 * @since 19/11/21
 * Last modified 02/12/21
 * @author (REMOVED)
*/
public class Rat {
	private static final int UNTIL_ADULT = Game.TPS * 15;
	private static final int UNTIL_MATING_FINISHED = Game.TPS * 5;
	private static final int UNTIL_GIVING_BIRTH = Game.TPS * 5;
	private static final int NUM_DIRECTION = 4;
	private static final int DIRECTION_RAND_3 = 3;
    private static final char NORTH = 'N';
    private static final char EAST = 'E';
    private static final char SOUTH = 'S';
    private static final char WEST = 'W';
    private static final char LEFT = 'L';
    private static final char FORWARD = 'F';
    private static final char RIGHT = 'R';
    private static final char MALE = 'm';
    private static final char FEMALE = 'f';
			
    private transient Level level;
    
    private Point location;
    private char sex;
    private char orientation;
    private boolean baby;
    private boolean sterile;
    private boolean pregnant;
    private boolean mating;
    private boolean giveBirth;
    private int gasExposureTime;
    private int numOfBabies;
    
    private int ticksUntilMatingFinished;
    private int ticksUntilGivingBirth;
    private int ticksUntilAdult;
    private int ticksUntilMove;
    
    /**
     * Constructs a new starting Rat.
     * @param location The rat's starting location on the level.
     * @param sex A character representing the rat's sex.
     * @param level The current game level being played.
     */
    public Rat(Point location, char sex, Level level){
        this.sex = sex;
        this.location = location;
        this.level = level;
        baby = true;
        ticksUntilAdult = UNTIL_ADULT;
        ticksUntilMatingFinished = UNTIL_MATING_FINISHED;
        ticksUntilGivingBirth = UNTIL_GIVING_BIRTH;
        randomOrientation();
    }
    
    /**
     * Determines if the rat should move during the current game tick, and
     * calls 'move()' if so.
     */
    public void tick() {
    	if (baby && ticksUntilAdult-- == 0) {
    		baby = false;
    	}
    	
    	if ((!baby && sex == FEMALE && mating)
    			&& ticksUntilMatingFinished-- == 0) {
    		mating = false;
    		pregnant = true;
    		ticksUntilMatingFinished = UNTIL_MATING_FINISHED;
    	} else  if ((!baby && sex == MALE && mating)
    			&& ticksUntilMatingFinished-- == 0) {
    		mating = false;
    		ticksUntilMatingFinished = UNTIL_MATING_FINISHED;
    	}
    	
    	if ((!baby && sex == FEMALE && pregnant && !giveBirth)
    			&& ticksUntilGivingBirth-- == 0) {
    		ticksUntilGivingBirth = UNTIL_GIVING_BIRTH;
    		giveBirth = true;
    	}
    	
    	if (ticksUntilMove-- == 0) {
    		if (baby) {
    			ticksUntilMove = Game.TPS;
    		} else {
    			ticksUntilMove = Game.TPS * 2;
    		}
    		
            move();
    	}
    }
    
    /**
     * Moves this rat an adjacent path or tunnel tile based upon the
     * requirements of the spec.
     */
    public void move() {
    	if (mating) {
    		return;
    	}
    	
    	List<Character> directions = directionsCanMove();
    	
    	// We're at a dead-end. Turn around.
    	if (directions.size() == 0) {
    		orientation = invert(orientation);
    		move(getNextLocation(orientation));
    		return;
    	}
    	
    	// We're on a straight path. Continue forward.
    	if (directions.size() == 1 && directions.get(0) == FORWARD) {
    		move(getNextLocation(orientation));
    		return;
    	}
    	
    	// We're at a right-hand turn. Turn right.
    	if (directions.size() == 1 && directions.get(0) == RIGHT) {
    		orientation = turnRight(orientation);
    		move(getNextLocation(orientation));
    		return;
    	}
    	
    	// We're at a left-hand turn. Turn left.
        if (directions.size() == 1 && directions.get(0) == LEFT) {
    		orientation = turnLeft(orientation);
    		move(getNextLocation(orientation));
    		return;
    	}
    	
    	// At this point, we must be at a junction. Choose a random direction.
    	int randomDirection = new Random().nextInt(directions.size());
    	char direction = directions.get(randomDirection);
    	
    	switch (direction) {
            case LEFT:
    			orientation = turnLeft(orientation);
        		move(getNextLocation(orientation));
        		break;
    		case RIGHT:
    			orientation = turnRight(orientation);
        		move(getNextLocation(orientation));
        		break;
        	default:
        		move(getNextLocation(orientation));
    	}
    }

    /**
     * Returns the rat's orientation.
     * @return The cardinal direction that the rat is facing.
     */
    public char getOrientation(){
        return orientation;
    }
    
    /**
     * Returns the amount of time a rat has been exposed to gas.
     * @return The amount of time a rat has been exposed to gas.
     */
    public int getGasExposureTime(){
        return gasExposureTime;
    }

    /**
     * Sets the gas exposure time.
     * @param gasExposureTime The new gas exposure time.
     */
    public void setGasExposureTime(int gasExposureTime){
        this.gasExposureTime = gasExposureTime;
    }

    /**
     * Gets the number of babies a rat has.
     * @return The number of babies a rat has.
     */
    public int getNumOfBabies(){
        return numOfBabies;
    }
    
    /**
     * Sets the number of babies a rat has.
     * @param numOfBabies The number of babies.
     */
    public void setNumOfBabies(int numOfBabies){
        this.numOfBabies = numOfBabies;
    }

    /**
     * Gets the current location of a rat as a point.
     * @return The current location of a rat.
     */
    public Point getLocation(){
        return location;
    }

    /**
     * Returns the sex of a rat.
     * @return The sex of a rat.
     */
    public char getSex(){
        return sex;
    }

    /**
     * Sets the sex of a rat.
     * @param sex The new sex of a rat.
     */
    public void setSex(char sex){
        this.sex = sex;
    }

    /**
     * Returns whether the rat is pregnant.
     * @return Whether the rat is pregnant or not.
     */
    public boolean isPregnant(){
        return pregnant;
    }

    /**
     * Sets if the rat is pregnant or not.
     * @param pregnant The new boolean for pregnant.
     */
    public void setPregnant(boolean pregnant){
        this.pregnant = pregnant;
    }

    /**
     * Returns whether the rat is sterile or not.
     * @return Whether the rat is sterile or not.
     */
    public boolean isSterile(){
        return sterile;
    }
    
    /**
     * Sets whether the rat is sterile or not.
     * @param sterile The new boolean for isSterile.
     */
    public void setSterile(boolean sterile){
        this.sterile = sterile;
    }

    /**
     * Returns whether a rat is a baby or not.
     * @return Whether a rat is a baby or not.
     */
    public boolean isBaby(){
        return baby;
    }

    /**
     * Changes the giveBirth attribute to determine when a pregnant rat can
     * start giving birth.
     * @param giveBirth The new give birth boolean.
     */
    public void setGiveBirth(Boolean giveBirth){
        this.giveBirth = giveBirth;
    }

    /**
     * Returns the result of whether the rat can give birth or not.
     * @return Whether a pregnant rat can give birth or not.
     */
    public Boolean getGiveBirth(){
        return giveBirth;
    }

    /**
     * Returns whether a rat is mating or not.
     * @return Whether a rat is currently mating or not.
     */
    public Boolean isMating(){
        return mating;
    }

    /**
     * Sets whether the rat is currently mating or not.
     * @param mating The new value for mating.
     */
    public void setMating(Boolean mating){
        this.mating = mating;
    }
    
    /**
     * Determines if this rat is about to move on the next game tick.
     * @return Whether the rat is going to move or not.
     */
    public boolean doesMoveNextTick() {
    	return ticksUntilMove == 0;
    }
    
    /**
     * Sets the reference to the Level instance after a game save has been
     * loaded.
     * @param level The instance of Level currently being used.
     */
    public void setLevel(Level level) {
    	this.level = level;
    }
    
    /**
     * Determines whether the rat can move onto a specific tile.
     * @param location The location of the tile to check.
     * @return True or false on whether the rat can move onto the specified Tile.
     */
    private boolean canMoveOnto(Point location) {
    	if (!level.isValidTile(location)) {
    		return false;
    	}
    	
    	Tile tile = level.getTile(location);
    	
    	if (tile.getTileType() == Tile.TileType.GRASS) {
    		return false;
    	}
    	
    	if (tile.getItemOnTile() != null
    			&& tile.getItemOnTile() instanceof NoEntry) {
    		((NoEntry) tile.getItemOnTile()).contactMade();
    		return false;
    	}
    	
    	return true;
    }
    
    /**
     * Moves the Rat to a new valid position on the Level.
     * @param newLocation The Rat's starting location on the Level.
     */
    private void move(Point newLocation) {
    	Tile curTile = level.getTile(location);
    	Tile newTile = level.getTile(newLocation);
    	
    	for (int i = 0; i < curTile.getNumberOfRatsOnTile(); i++) {
    		if (curTile.getRatOnTile(i) == this) {
    			curTile.removeRat(i);
    			break;
    		}
    	}
    	
    	newTile.addRat(this);
    	location = newLocation;
    }
    
    /**
     * Translate the rat's current location forward based upon its orientation.
     * @return A new location which may or may not be a valid tile.
     */
    private Point getNextLocation(char orientation) {
    	Point newPos = new Point(location);
    	
        switch (orientation) {
    		case NORTH:
    			newPos.translate(0, -1);
    			break;
    		case EAST:
    			newPos.translate(+1, 0);
    			break;
    		case SOUTH:
    			newPos.translate(0, +1);
    			break;
    		default:
    			newPos.translate(-1, 0);
    			break;
        }
    	
    	return newPos;
    }
    
    /**
     * Translates the rat's current position to the left.
     * @return The left direction based on where the Rat is, if it were to turn left.
     */
    private char turnLeft(char orientation) {
        switch (orientation) {
    		case NORTH:
    			return WEST;
    		case EAST:
    			return NORTH;
    		case SOUTH:
    			return EAST;
    		default:
    			return SOUTH;
        }
    }
    
    /**
     * Translates the rat's current position to the right.
     * @return The right direction based on where the Rat is, if it were to turn right.
     */
    private char turnRight(char orientation) {
        switch (orientation) {
    		case NORTH:
    			return EAST;
    		case EAST:
    			return SOUTH;
    		case SOUTH:
    			return WEST;
    		default:
    			return NORTH;
        }
    }
    
    /**
     * Inverts the rat's current position.
     * @return The inverted direction of where the Rat is going.
     */
    private char invert(char orientation) {
        switch (orientation) {
    		case NORTH:
    			return SOUTH;
    		case EAST:
    			return WEST;
    		case SOUTH:
    			return NORTH;
    		default:
    			return EAST;
        }
    }
    
    /**
     * Changes the rat's orientation to a random direction
     */
    private void randomOrientation(){
        switch (new Random().nextInt(NUM_DIRECTION)) {
            case 0:
            	orientation = NORTH;
                break;
            case 1:
            	orientation = WEST;
                break;
            case 2:
            	orientation = SOUTH;
                break;
            case DIRECTION_RAND_3:
                // NOTE: Checkstyle considered '3' to be a magic number in this
            	// switch so had to be a constant, but none of the others were
            	// flagged as magic numbers.
            	orientation = EAST;
                break;
             default:
        }
    }

    /**
     * Determines what relative directions the rat can move (ie.forward = FORWARD,
     * left = LEFT, right = RIGHT).
     * @return A list of characters representing the relative directions.
     */
    private List<Character> directionsCanMove() {
        List<Character> directions = new ArrayList<Character>();

        if (canMoveOnto(getNextLocation(turnLeft(orientation)))) {
            directions.add(LEFT);
        }
        if (canMoveOnto(getNextLocation(orientation))) {
            directions.add(FORWARD);
        }
        if (canMoveOnto(getNextLocation(turnRight(orientation)))) {
            directions.add(RIGHT);
        }

        return directions;
    }
}
