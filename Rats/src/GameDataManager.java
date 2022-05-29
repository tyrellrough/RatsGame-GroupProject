import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * GameDataManager.java
 *
 * Allows the current game state to be saved and the saved file of the game to be loaded to be played.
 *
 * @version 1.2
 * @since 03/12/21
 * Last modified 03/12/21
 * @author (REMOVED)
 */
public class GameDataManager {
    private static final Gson gson = new Gson();
    private static final SimpleDateFormat TIME_DATE_FORMATTER = new SimpleDateFormat("'('dd-MM-yyyy'_'HH-mm-ss')'");
    
    private static final String SAVE_FOLDER_PATH = "./saves/";
    private static final int OBJECT_SAVED_LENGTH = "_Level.txt".length();
    private static final String LEVEL_END = "_Level.txt";
    private static final String RATMG_END = "_RatMg.txt";
    private static final String PLAYER_END = "_Playr.txt";
    private static final String INVENTORY = "inventory";
    private static final String TILE_LIST = "tileList";
    private static final String ITEM_ON_TILE = "itemOnTile";
    private static final String ITEM_TYPE = "type";
    private static final String BOMB = "Bomb";
    private static final String GAS = "Gas";
    private static final String POISON = "Poison";
    private static final String STERILISATION = "Sterilisation";
    private static final String DEATH_RAT = "DeathRat";
    private static final String MALE_CHANGE = "MaleSexChange";
    private static final String NO_ENTRY = "NoEntry";
    private static final String FEMALE_CHANGE = "FemaleSexChange";
    private static final String LOAD_SUCCESS ="Loaded successfully";


    private String levelFileName;
    private String ratMFileName;
    private String playerFileName;
    
    private Level levelLoaded;
    private RatManager ratMgLoaded;
    private Player playerLoaded;

    /**
     * Gets all save files.
     * @return A list of all the save file names.
     */
    public ArrayList<String> getAllSaveFiles() {
        ArrayList<String> fileNames = new ArrayList<>();

        File folder = new File(SAVE_FOLDER_PATH);
        String[] listOfFileNames = folder.list();

        if (listOfFileNames != null) {
            for (String singleFile : listOfFileNames) {
                String file = singleFile.substring(0, singleFile.length() - OBJECT_SAVED_LENGTH);

                if (!fileNames.contains(file)) {
                    fileNames.add(file);
                }
            }
        }
        
        return fileNames;
    }

    /**
     * Saves the game's current state into a file.
     * @param level The current level to be saved.
     * @param ratManager The RatManager for the game.
     * @param player The Player for the game.
     */
    public void saveGame(Level level, RatManager ratManager, Player player) {
        Date todayDate = new Date(System.currentTimeMillis());
        String dateAndTime = TIME_DATE_FORMATTER.format(todayDate);
        String levelFileName = level.getClass().getName() + level.getLevelNumber();
        String baseFileName = SAVE_FOLDER_PATH + levelFileName + dateAndTime;

        if (this.createFile(baseFileName)) {
            writeJsonToFile(level, ratManager, player);
        }
    }

    /**
     * Loads the given save file for a saved game.
     * @param saveFileName The save file name to load.
     */
    public void loadSaveFile(String saveFileName) {
        if (getAllSaveFiles().contains(saveFileName)) {
            String fullFileName = SAVE_FOLDER_PATH + saveFileName;
            if (readSaveFiles(fullFileName)) {
                System.out.println(LOAD_SUCCESS);
            }
        }
    }

    /**
     * Gets the Level that was loaded from the saved file.
     * @return The Level that was loaded.
     */
    public Level getLevelLoaded() {
        return this.levelLoaded;
    }

    /**
     * Gets the RatManager for the game that was loaded.
     * @return The RatManager that was loaded.
     */
    public RatManager getRatMgLoaded() {
        return this.ratMgLoaded;
    }

    /**
     * Gets the Player that was loaded from the saved game.
     * @return The Player from the save file.
     */
    public Player getPlayerLoaded() {
        return this.playerLoaded;
    }
    
    /**
     * Reads the save files.
     * @param baseName The common part of the file's names.
     * @return True or false on whether the files have been read properly.
     */
    private boolean readSaveFiles(String baseName) {
        levelFileName = baseName + LEVEL_END;
        ratMFileName = baseName + RATMG_END;
        playerFileName = baseName + PLAYER_END;

        try {
        	JsonObject level = gson.fromJson(new FileReader(levelFileName), JsonObject.class);
        	JsonObject ratMg = gson.fromJson(new FileReader(ratMFileName), JsonObject.class);
        	JsonObject player = gson.fromJson(new FileReader(playerFileName), JsonObject.class);
        	
        	ArrayList<Item> items = new ArrayList<>();
        	ArrayList<Item> inventory = new ArrayList<>();
        	
        	// Gson doesn't handle inheritance well. We need to store a 'type' field within the JSON
            // and manually instantiate the subclasses via 'itemFromJson()'.
        	// It also doesn't like abstract classes, so we do this for the same reason.
        	for (JsonElement outer : level.get(TILE_LIST).getAsJsonArray()) {
        		for (JsonElement inner : outer.getAsJsonArray()) {
        			if (!inner.getAsJsonObject().has(ITEM_ON_TILE)) {
        				continue;
        			}
        			
        			items.add(itemFromJson(inner.getAsJsonObject().get(ITEM_ON_TILE)));
        			inner.getAsJsonObject().remove(ITEM_ON_TILE);
        		}
        	}
        	
        	player.get(INVENTORY).getAsJsonArray().forEach(itemJson -> {
        		inventory.add(itemFromJson(itemJson));
        	});
        	
        	// No clear() method. We need to remove each item manually.
        	while (player.get(INVENTORY).getAsJsonArray().size() != 0) {
        		player.get(INVENTORY).getAsJsonArray().remove(0);
        	}
        	
            this.levelLoaded = gson.fromJson(level, Level.class);
            this.ratMgLoaded = gson.fromJson(ratMg, RatManager.class);
            this.playerLoaded = gson.fromJson(player, Player.class);
            
            // Add the items back into the tiles and inventory.
            items.forEach(item -> levelLoaded.getTile(item.getCoordinates()).setItemOnTile(item));
            inventory.forEach(item -> playerLoaded.getInventoryItems().add(item));
            
            if ((this.levelLoaded != null) && (this.ratMgLoaded != null) && (this.playerLoaded != null)) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Converts JSON into a valid Item by instantiating as the correct subclass.
     * @param data The parsed JSON data of the item.
     * @return A valid Item, or null if the instantiation failed.
     */
    private Item itemFromJson(JsonElement data) {
    	switch (data.getAsJsonObject().get(ITEM_TYPE).getAsString()) {
            case BOMB:
				return gson.fromJson(data, Bomb.class);
            case DEATH_RAT:
				return gson.fromJson(data, DeathRat.class);
            case MALE_CHANGE:
				return gson.fromJson(data, MaleSexChange.class);
            case FEMALE_CHANGE:
				return gson.fromJson(data, FemaleSexChange.class);
            case GAS:
				return gson.fromJson(data, Gas.class);
            case NO_ENTRY:
				return gson.fromJson(data, NoEntry.class);
            case POISON:
				return gson.fromJson(data, Poison.class);
            case STERILISATION:
				return gson.fromJson(data, Sterilisation.class);
			default:
				return null;
    	}
    }

    /**
     * Creates new files for saving the game.
     * @param baseName The common part of the save file names.
     * @return True or false on whether the files were created properly.
     */
    private boolean createFile(String baseName) {
        try {
            levelFileName = baseName + LEVEL_END;
            ratMFileName = baseName + RATMG_END;
            playerFileName = baseName + PLAYER_END;
            
            File levelSave = new File(levelFileName);
            File rMSave = new File(ratMFileName);
            File playerSave = new File(playerFileName);

            if ((levelSave.createNewFile()) && (rMSave.createNewFile()) && (playerSave.createNewFile())) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Writes the data to the save files just created.
     * @param level The current level to be saved.
     * @param ratManager The RatManager to be saved.
     * @param player The Player to be saved.
     */
    private void writeJsonToFile(Level level, RatManager ratManager, Player player) {
        try { 
        	FileWriter levelFileWriter = new FileWriter(levelFileName, false);
            BufferedWriter levelBuffer = new BufferedWriter(levelFileWriter);
            FileWriter rMFileWriter = new FileWriter(ratMFileName, false);
            BufferedWriter rMBuffer = new BufferedWriter(rMFileWriter);
            FileWriter playerFileWriter = new FileWriter(playerFileName, false);
            BufferedWriter playerBuffer = new BufferedWriter(playerFileWriter);
        	
            gson.toJson(level, levelBuffer);
            levelBuffer.close();
            levelFileWriter.close();

            gson.toJson(ratManager, rMBuffer);
            rMBuffer.close();
            rMFileWriter.close();

            gson.toJson(player, playerBuffer);
            playerBuffer.close();
            playerFileWriter.close();

        } catch (IOException e) {
            System.exit(0);
        }
    }
}
