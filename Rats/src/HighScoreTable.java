import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * HighScoreTable.java
 *
 * A store of the ten-highest scores achieved for each level.
 *
 * @version 1.0
 * @since 10/11/21
 * Last modified 05/12/21
 * @author (REMOVED)
*/
public class HighScoreTable {
	private static final String SAVE_FILE_NAME = "./data/high_scores.csv";
	private static final int MAX_ENTRIES = 10;
	
	private List<List<HighScoreEntry>> table;
	
	/**
	 * Saves a table of high scores to a file.
	 * @return Whether the save was successful.
	 */
	public boolean saveToFile() {
		try {
			File file = new File(SAVE_FILE_NAME);
			
			// Create a new file if it doesn't exist yet.
			if (!file.exists() && !file.createNewFile()) {
                throw new Exception("Can't to create file: " + SAVE_FILE_NAME);
			}
			
			FileOutputStream outStream = new FileOutputStream(file);
            OutputStreamWriter out = new OutputStreamWriter(outStream);
			
			// Serialise the table so that it can be stored.
			//
            // We use a CSV-like format here, where the scores for each
            // level are stored on different lines. For each line, a comma
			// separates each entry.
			//
            // Each entry is stored as a key/value pair, with the key being
            // the profile ID, and the value being the score. The key/value
			// pair are separated by a colon.
			for (List<HighScoreEntry> levelScores : table) {
				for (HighScoreEntry entry : levelScores) {
                    String entryStr = entry.getProfile().getProfileID();
                    entryStr += ":" + entry.getScore() + ",";
                    
                    out.write(entryStr);
				}
				out.write("\n");
			}

			out.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Load a table of high scores from a file.
	 * @param profiles The game's profile manager.
	 * @return Whether the load was successful.
	 */
	public boolean loadFromFile(ProfileManager profiles) {
		table = new ArrayList<List<HighScoreEntry>>();
		File file = new File(SAVE_FILE_NAME);
		
        // Initialise new tables instead if no previous save file exists.
		if (!file.exists()) {
			for (int i = 0; i < Level.getLevelCount(); i++) {
				this.table.add(new ArrayList<HighScoreEntry>());
			}
			
			return true;
		}
		
		try {
			Scanner in = new Scanner(new FileInputStream(file));
			
			// Deserialise the data based upon the format set out in
			// the saveToFile() method.
			while (in.hasNextLine()) {
                List<HighScoreEntry> scores= new ArrayList<HighScoreEntry>();
				this.table.add(scores);
				
				String[] entries = in.nextLine().split(",");
				
                // Split the key/value pair, instantiate a new entry, and store
				// it.
				for (String entry : entries) {
					String[] keyValue = entry.split(":");
					
					if (keyValue.length == 2) {
                        Profile profile = profiles.findProfile(keyValue[0]);
                        int score = Integer.valueOf(keyValue[1]);
						
                        scores.add(new HighScoreEntry(score, profile));
					}
				}
			}
			
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Updates the high score table for a specific level after a game ends.
	 * @param profile The profile which is being actively used currently.
	 * @param level The identifier of the level (zero indexed).
	 * @param score The score that was achieved by the player.
	 */
	public void profileGotScore(Profile profile, int level, int score) {
		List<HighScoreEntry> levelScores = this.table.get(level - 1);
		int numEntries = levelScores.size();
		
        // If the table is already full and the profile didn't achieve a higher
        // score than the lowest within the table, there's nothing more for us
		// to do - return.
		if (numEntries == MAX_ENTRIES && levelScores.get(numEntries - 1).getScore() >= score) {
			return;
		}
		
		int currentPosition = findProfilePlacement(level - 1, profile);
		
		if (currentPosition != -1) {
			HighScoreEntry currentEntry = levelScores.get(currentPosition - 1);
			
			// If the profile already has an entry but didn't achieve a higher score than
			// that entry, there's nothing more for us to do - return.
			if (currentEntry.getScore() >= score) {
				return;
			}
			
			levelScores.remove(currentPosition - 1);
		}
		
		int insertPosition = findScorePlacement(level - 1, score);
		HighScoreEntry newEntry = new HighScoreEntry(score, profile);
		
		if (insertPosition != -1) {
			levelScores.add(insertPosition - 1, newEntry);
		} else {
			levelScores.add(newEntry);
		}
		
		// Truncate back down to 10 entries if over.
		if (levelScores.size() > MAX_ENTRIES) {
			levelScores.remove(levelScores.size() - 1);
		}
		
		saveToFile();
	}
	
	/**
	 * Remove all high score entries for a particular profile.
	 * @param profile The profile to remove entries for.
	 */
	public void removeProfileEntries(Profile profile) {
		for (int i = 0; i < this.table.size(); i++) {
			List<HighScoreEntry> entries = this.table.get(i);

			// We start from the back of the list and step backwards to avoid confusion
			// with changing indices due to previous removals and shifts.
			for (int j = entries.size() - 1; j >= 0; j--) {
				if (entries.get(j).getProfile() == profile) {
					entries.remove(j);
				}
			}
		}
		
		saveToFile();
	}
	
	/**
	 * Returns a list of high scores for a particular level.
	 * @param level The identifier of the level (zero indexed).
	 * @return A list of high scores.
	 */
	public List<HighScoreEntry> getLevelPlacements(int level) {
		return this.table.get(level - 1);
	}
	
	/**
	 * Returns the placement of a profile within a particular level's high scores.
	 * @param level The identifier of the level (zero indexed).
	 * @param profile The profile to search for.
	 * @return The profile's placement, otherwise -1.
	 */
	private int findProfilePlacement(int level, Profile profile) {
		List<HighScoreEntry> levelScores = this.table.get(level);
		
		for (int i = 0; i < levelScores.size(); i++) {
			if (levelScores.get(i).getProfile().getProfileID().equals(profile.getProfileID())) {
				return ++i;
			}
		}
		
		return -1;
	}
	
	/**
	 * Returns the placement of a score within a particular level's high scores.
	 * @param level The identifier of the level (zero indexed).
	 * @param score The score to search for.
	 * @return The score's placement, otherwise -1.
	 */
	private int findScorePlacement(int level, int score) {
		List<HighScoreEntry> levelScores = this.table.get(level);
		
		for (int i = 0; i < levelScores.size(); i++) {
			if (levelScores.get(i).getScore() < score) {
				return ++i;
			}
		}
		
		return -1;
	}
}
