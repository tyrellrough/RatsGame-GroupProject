import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Level.java
 *
 * A level is the space given to the player and the rats where the player must
 * attempt to kill all the rats whilst the rats must attempt to reach a population goal.
 *
 * Version 1.4
 * Created 12/11/21
 * Last modified 28/11/21
 * @author (REMOVED)
 */
public class Level {
	private static final int LEVEL_COUNT = 5;
	private static final String FILE_PATH = "./levels/";
	private int levelNumber;
	private int width;
	private int height;
	private Tile[][] tileList;
	//if reached, the player loses.
	private int ratMaxPop; 
	private int bombDropRate;
	private int deathRatDropRate;
	private int gasDropRate;
	private int noEntryDropRate;
	private int poisonDropRate;
	private int maleSexChangeDropRate;
	private int femaleSexChangeDropRate;
	private int sterilisationDropRate;
	// If the timeCounter exceeds this value then score stops accumulating.
	private int expectedCompletionTime;
	// How long the player has been playing.
	private int timeCounter;
	private char[] ratStartingGender;
	private Point[] ratStartingPoints;
	private int initialNumberOfRats;

	/**
	 * Constructs a level using a level.txt file.
	 * @param fileName The name of the level's file.
	 */
	public Level(final String fileName) {
		this.readFile(fileName);
	}

	/**
	 * Gets the expected completion time.
	 * @return The expected completion time in seconds for this Level.
	 */
	public int getExpectedCompletionTime() {
		return this.expectedCompletionTime;
	}

	/**
	 * Gets the Level's number.
	 * @return The Level's number.
	 */
	public int getLevelNumber() {
		return this.levelNumber;
	}

	/**
	 * Gets the amount of rats that causes a loss.
	 * @return The maximum number of rats to cause a loss.
	 */
	public int getRatMaxPop() {
		return this.ratMaxPop;
	}

	/**
	 * Gets the Level's width.
	 * @return The Level's width.
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Gets the Level's height.
	 * @return The Level's height.
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * Gets Bomb's drop rate (per seconds).
	 * @return The Bomb's drop rate for this Level.
	 */
	public int getBombDropRate() {
		return this.bombDropRate;
	}

	/**
	 * Gets DeathRat's drop rate (per seconds).
	 * @return The DeathRat's drop rate for this Level.
	 */
	public int getDeathRatDropRate() {
		return this.deathRatDropRate;
	}

	/**
	 * Gets Gas' drop rate (per seconds).
	 * @return The Gas' drop rate for this Level.
	 */
	public int getGasDropRate() {
		return this.gasDropRate;
	}

	/**
	 * Gets NoEntry sign's drop rate (per seconds).
	 * @return The NoEntry sign's drop rate for this Level.
	 */
	public int getNoEntryDropRate() {
		return this.noEntryDropRate;
	}

	/**
	 * Gets Poison's drop rate (per seconds).
	 * @return The Poison's drop rate for this Level.
	 */
	public int getPoisonDropRate() {
		return this.poisonDropRate;
	}

	/**
	 * Gets MaleSexChange's drop rate (per seconds).
	 * @return The MaleSexChange's drop rate for this Level.
	 */
	public int getMaleSexChangeDropRate() {
		return this.maleSexChangeDropRate;
	}

	/**
	 * Gets FemaleSexChange drop rate (per seconds).
	 * @return The FemaleSexChange's drop rate for this Level.
	 */
	public int getFemaleSexChangeDropRate() {
		return this.femaleSexChangeDropRate;
	}

	/**
	 * Gets Sterilisation's drop rate (per seconds).
	 * @return The Sterilisation's drop rate for this Level.
	 */
	public int getSterilisationDropRate() {
		return this.sterilisationDropRate;
	}

	/**
	 * Gets the initial number of rats.
	 * @return The starting population of rats for the Level.
	 */
	public int getInitialNumberOfRats() {
		return this.initialNumberOfRats;
	}

	/**
	 * Increments the time played counter.
	 */
	public void incrementTimeCounter() {
		this.timeCounter++;
	}

	/**
	 * Gets the time played (time counter).
	 * @return The amount of time the player has played so far.
	 */
	public int getTimePlayed() {
		return this.timeCounter;
	}

	/**
	 * Checks if the player has exceeded the expected completion time.
	 * @return The true or false on whether the player has exceeded the expected completion time.
	 */
	public boolean hasExceededExpectedCompletionTime() {
		return this.getTimePlayed() < this.getExpectedCompletionTime();
	}

	/**
	 * Gets the starting positions of the rats.
	 * @return The list of starting positions of the rats.
	 */
	public Point[] getRatStartingPoints() {
		return this.ratStartingPoints;
	}

	/**
	 * Gets the starting gender of the rats.
	 * @return The list of starting genders of the rats.
	 */
	public char[] ratStartingGender() {
		return this.ratStartingGender;
	}

	/**
	 * Gets all the Tiles.
	 * @return The list of all the Tiles.
	 */
	public Tile[][] getTileList() {
		return this.tileList;
	}

	/**
	 * Gets the number of total Levels.
	 * @return The number of total Levels.
	 */
	public static int getLevelCount() {
		return LEVEL_COUNT;
	}

	/**
	 * Used to access a tile in the array tileList.
	 * @param pos The index of the tileList array.
	 * @return The Tile at specified tileList index.
	 */
	public Tile getTile(Point pos) {
		if (pos.x >= width || pos.x < 0) {
			System.out.println("X:" + pos.x + " X must be min 0 and max " + this.width);
			System.exit(0);
		} else if (pos.y >= height || pos.y < 0) {
			System.out.println("Y:" + pos.y + " Y must be min 0 and max " + this.height);
			System.exit(0);
		}
		return this.tileList[pos.x][pos.y];
	}

	/**
	 * Used to access a tile in the array tileList.
	 * @param pos The index of the tileList array.
	 * @return The true or false of whether a tile is valid.
	 */
	public boolean isValidTile(Point pos) {
		if (pos.x >= width || pos.x < 0) {
			return false;
		} else if (pos.y >= height || pos.y < 0) {
			return false;
		}
		return true;
	}

	/**
	 * Returns a list of the Item positions.
	 * @return The list of positions for every Item.
	 */
	public ArrayList<Point> getItemPositions() {
		ArrayList<Point> itemPositions = new ArrayList<Point>();
		// Height loop.
		for (int i = 0; i < this.height; i++) {
			// Width loop.
			for (int j = 0; j < this.width; j++) {
				if (this.tileList[j][i].getItemOnTile() != null) {
					Point point = new Point(j, i);
					itemPositions.add(point);
				}
			}
		}
		return itemPositions;
	}

	/**
	 * Returns a list of the all Items.
	 * @return The list of all Items in the Level.
	 */
	public ArrayList<Item> getAllItems() {
		ArrayList<Item> itemsOnTiles = new ArrayList<Item>();
		// Height loop.
		for (int i = 0; i < this.height; i++) {
			// Width loop.
			for (int j = 0; j < this.width; j++) {
				if (this.tileList[j][i].getItemOnTile() != null) {
					itemsOnTiles.add(tileList[j][i].getItemOnTile());
				}
			}
		}
		return itemsOnTiles;
	}

	/**
	 * Gets a list of all rat positions.
	 * @return The list of the rat positions.
	 */
	public ArrayList<Point> getRatPositions() {
		ArrayList<Point> ratPositions = new ArrayList<Point>();
		// Height loop.
		for (int i = 0; i < this.height; i++) {
			// Width loop.
			for (int j = 0; j < this.width; j++) {
				// Rats on the Tile loop.
				for (int z = 0; z < tileList[j][i].getNumberOfRatsOnTile(); z++) {
					Point ratPosition = new Point(j, i);
					ratPositions.add(ratPosition);
				}
			}
		}
		return ratPositions;
	}

	/**
	 * Gets a list of all the Rat objects.
	 * @return The list of Rat objects in the Level.
	 */
	public ArrayList<Rat> getAllRats() {
		ArrayList<Rat> ratsOnTiles = new ArrayList<Rat>();
		// Height loop.
		for (int i = 0; i < this.height; i++) {
			// Width loop
			for (int j = 0; j < this.width; j++) {
				// Rats on the Tile loop.
				for (int z = 0; z < tileList[j][i].getNumberOfRatsOnTile(); z++) {
					ratsOnTiles.add(this.tileList[j][i].getRatOnTile(z));
				}
			}
		}
		return ratsOnTiles;
	}

	/**
	 * Gets the class names of all the Items.
	 * @return The list of Item names.
	 */
	public ArrayList<String> getAllItemNames() {
		ArrayList<String> listItemNames = new ArrayList<String>();
		// Height loop.
		for (int i = 0; i < this.height; i++) {
			// Width loop.
			for (int j = 0; j < this.width; j++) {
				if (this.tileList[j][i].getItemOnTile() != null) {
					listItemNames.add(tileList[j][i].getItemOnTile().getClass().getName());
				}
			}
		}
		return listItemNames;
	}

 	/**
 	 * Populates a level's attributes.
 	 * @param fileName The Level's file to be read.
 	 */
	private void readFile(String fileName) {
		Scanner in = null;
		// Tries to read a file, exits if unable.
	      try {
			in = new Scanner(getClass().getResourceAsStream(FILE_PATH + fileName));
		} catch (Exception ex) {
			System.out.println("Cannot open" + fileName);
			System.exit(0);
		}
	    // Reads Level's number.
	    this.levelNumber = in.nextInt();
	    in.nextLine();
	    
	    // Reads width, height, and tiles.
    	this.width = in.nextInt(); 
		this.height = in.nextInt();
		this.tileList = new Tile[this.width][this.height];
		// Height loop.
		for (int i = 0; i < this.height; i++) {
			// A string of the current line.
			String tileLine = in.next();
			// Width loop.
            for (int j = 0; j < this.width; j++) {
            	// Picks each character from the current line string.
            	char tileType = tileLine.charAt(j); 
            	Tile t = new Tile(tileType);
            	tileList[j][i] = t;
            }     
        } 
		in.nextLine();
		// Reads number of rats, rat starting points, and rat starting genders.
		this.initialNumberOfRats = in.nextInt();
		this.ratStartingPoints = new Point[this.initialNumberOfRats];
		this.ratStartingGender = new char[this.initialNumberOfRats];
        for (int ratCounter = 0; ratCounter < this.initialNumberOfRats; ratCounter++) {
        	in.nextLine();
        	int ratXPos = in.nextInt();
        	int ratYPos = in.nextInt();
        	Point ratPos = new Point(ratXPos, ratYPos);
        	this.ratStartingPoints[ratCounter] = ratPos;
        	in.nextLine();
        	char currentRatGender = in.next().charAt(0);
        	this.ratStartingGender[ratCounter] = currentRatGender;
        }
        
        // Reads item drop rates, expected completion time, number of rats to cause a loss.
        in.nextLine();
        this.bombDropRate = in.nextInt();
        in.nextLine();
        this.deathRatDropRate = in.nextInt();
        in.nextLine();
        this.gasDropRate = in.nextInt();
        in.nextLine();
        this.noEntryDropRate = in.nextInt();
        in.nextLine();
        this.poisonDropRate = in.nextInt();
        in.nextLine();
        this.maleSexChangeDropRate = in.nextInt();
        in.nextLine();
        this.femaleSexChangeDropRate = in.nextInt();
        in.nextLine();
        this.sterilisationDropRate = in.nextInt();
        in.nextLine();
        this.expectedCompletionTime = in.nextInt();
        in.nextLine();
        this.ratMaxPop = in.nextInt();
  
	}
}
