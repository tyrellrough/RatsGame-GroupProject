import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * RatManager.java
 *
 * Responsible for creating rats and the population of rats.
 *
 * @version 1.0
 * @since 01/12/21
 * Last modified 02/12/21
 * @author (REMOVED)
 */
public class RatManager {
    private static final int MAX_NUM_BABIES = 4;
    private static final char MALE_RAT = 'm';
    private static final char FEMALE_RAT = 'f';
    
    private transient Level currentLevel;
    private transient ArrayList<Rat> allRats;
    private int totalDeadRats;

    /**
     * Constructor for starting a level.
     * @param level The level where the baby rats will be.
     */
    public RatManager(Level level) {
        allRats = new ArrayList<>();
        currentLevel = level;
        startRats(level);
    }
    
    /**
     * 'Kills' the given Rat and removes it from the rats on the table.
     * @param ratToKill The rat to be killed/ removed.
     */
    public void killRat(Rat ratToKill) {
    	int difference = currentLevel.getExpectedCompletionTime();
    	difference -= currentLevel.getTimePlayed();
        
        if (difference > 0) {
            totalDeadRats += ratToKill.getNumOfBabies() + 1;
        }
        
        this.allRats.remove(ratToKill);
    }

    /**
     * Allows pregnant rats to give birth to rat babies when their pregnancy
     * period is done.
     */
    public void ratsGivingBirth() {
        ArrayList<Rat> readyToGiveBirth = ratsToGiveBirth();
        ArrayList<Rat> totalBabyRats = new ArrayList<>();
        
        readyToGiveBirth.forEach(pregRat -> {
        	if (!pregRat.doesMoveNextTick()) {
        		return;
        	}
        	
        	if (pregRat.getNumOfBabies() == 0) {
        		pregRat.setGiveBirth(false);
        		pregRat.setPregnant(false);
        		return;
        	}
        	
        	pregRat.setNumOfBabies(pregRat.getNumOfBabies() - 1);
        	
        	Point babyLoc = pregRat.getLocation();
        	char babySex = randomiseBabySex();
        	
        	totalBabyRats.add(new Rat(babyLoc, babySex, currentLevel));
        });
        
        this.allRats.addAll(totalBabyRats);
        setRatsOnTiles(totalBabyRats);
    }

    /**
     * Checks if there are rats that can breed on each tile.
     */
    public void breedRatsOnTiles() {
        Tile[][] tiles = this.currentLevel.getTileList();
        for (int i = 0; i < this.currentLevel.getHeight(); i++) {
            for (int j = 0; j < this.currentLevel.getWidth(); j++) {
                int totalRatsOnTile = tiles[j][i].getNumberOfRatsOnTile();
                if (totalRatsOnTile >= 2) {
                    ArrayList<Rat> ratsOnTile = tiles[j][i].getRatsOnTile();
                    this.ratsMating(ratsOnTile);
                }
            }
        }
    }
    
    /**
     * Checks if the player has lost the game from being overrun by rats.
     * @return True or false on whether the player lost the game.
     */
    public boolean hasLostGame() {
        return allRats.size() >= currentLevel.getRatMaxPop();
    }
    
    /**
     * Gets a list of all the rats on the level.
     * @return The list of all the rats on the level.
     */
    public ArrayList<Rat> getAllRats() {
        return allRats;
    }
    
    /**
     * Gets the number of female rats on the level currently.
     * @return The population of female rats on the level.
     */
    public int getFemaleRats() {
        int totalFemaleRats = 0;

        for (Rat eachRat : allRats) {
            if (eachRat.getSex() == FEMALE_RAT) {
                totalFemaleRats++;
            }
        }
        
        return totalFemaleRats;
    }

    /**
     * Gets the number of male rats on the level currently.
     * @return The population of male rats on the level.
     */
    public int getMaleRats() {
        int totalMaleRats = 0;

        for (Rat eachRat : this.allRats) {
            if (eachRat.getSex() == MALE_RAT) {
                totalMaleRats++;
            }
        }
        
        return totalMaleRats;
    }

    /**
     * Gets the number of total dead rats currently.
     * @return The number of dead rats.
     */
    public int getTotalDeadRats() {
        return this.totalDeadRats;
    }
    
    /**
     * Sets the reference to the RatManager instance after a game save has
     * been loaded.
     * @param allRats The instance of RatManager currently being used.
     */
    public void setAllRats(ArrayList<Rat> allRats) {
    	this.allRats = allRats;
    }
    
    /**
     * Sets the reference to the Level instance after a game save has been
     * loaded.
     * @param currentLevel The instance of Level currently being used.
     */
    public void setCurrentLevel(Level currentLevel) {
    	this.currentLevel = currentLevel;
    }

    /**
     * Sets the Rats onto the tiles in the level.
     * @param ratsToPutOnTiles The list of Rats to put on the tiles.
     */
    private void setRatsOnTiles(ArrayList<Rat> ratsToPutOnTiles) {
        for (Rat eachRat : ratsToPutOnTiles) {
            Point tileCoord = eachRat.getLocation();
            this.currentLevel.getTile(tileCoord).addRat(eachRat);
        }
    }

    /**
     * Creates rats for a fresh new level.
     * @param level The current Level.
     */
    private void startRats(Level level) {
        Rat ratStarter;
        
        for (int i = 0; i < level.ratStartingGender().length; i++) {
            Point coord = level.getRatStartingPoints()[i];
            char sex = level.ratStartingGender()[i];
            ratStarter = new Rat(coord, sex, this.currentLevel);
            this.allRats.add(ratStarter);
        }

        setRatsOnTiles(this.allRats);
    }

    /**
     * Makes adult females and males that can breed, mate with each other and
     * impregnate the adult female rats.
     * @param ratsOnTile The rats on a tile, where there are 2 or more rats on
     * it.
     */
    private void ratsMating(ArrayList<Rat> ratsOnTile) {
        ArrayList<Rat> adultFemales = new ArrayList<>();
        ArrayList<Rat> adultMales = new ArrayList<>();

        for (Rat eachRat : ratsOnTile) {
            if ((!eachRat.isBaby())
            		&& (!eachRat.isSterile())
            		&& (!eachRat.isPregnant())) {
                if (eachRat.getSex() == FEMALE_RAT) {
                    adultFemales.add(eachRat);
                } else {
                    adultMales.add(eachRat);
                }
            }
        }

        if ((adultFemales.size() > 0) && (adultMales.size() > 0)) {
            int maxMating = 0;
            if (adultFemales.size() != adultMales.size()) {
                maxMating = Math.min(adultFemales.size(), adultMales.size());
            }

            for (int i = 0; i <= maxMating; i++) {
                adultFemales.get(i).setMating(true);
                adultMales.get(i).setMating(true);
                adultFemales.get(i).setPregnant(true);
                
                int numOfBabies = new Random().nextInt(MAX_NUM_BABIES);
                adultFemales.get(i).setNumOfBabies(numOfBabies);
            }
        }
    }

    /**
     * Gets the pregnant rats who have finished their pregnancy period.
     * @return The list of pregnant rats that are going to give birth right now.
     */
    private ArrayList<Rat> ratsToGiveBirth() {
        ArrayList<Rat> readyToGiveBirth = new ArrayList<>();
        
        for (Rat eachRat : this.allRats) {
            if ((eachRat.isPregnant()) && (eachRat.getGiveBirth())) {
                readyToGiveBirth.add(eachRat);
            }
        }
        
        return readyToGiveBirth;
    }
    
    /**
     * Randomizes the sex of a baby rat.
     * @return The randomized sex of the baby rat.
     */
    private char randomiseBabySex() {
        char[] bothSexes = {FEMALE_RAT, MALE_RAT};
        Random random = new Random();
        int randomSex = random.nextInt(bothSexes.length);
        return bothSexes[randomSex];
    }
}
