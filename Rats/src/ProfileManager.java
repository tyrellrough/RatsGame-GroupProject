import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * ProfileManager.java
 *
 * Handles the users' profiles - deleting and creating new profiles.
 *
 * @version 1.0
 * @since 23/11/21
 * Last modified 30/11/21
 * @author (REMOVED)
 */
public class ProfileManager {
    private static final String FILE_PATH = "./data/profiles.txt";
    private static final String FIND_ERROR = "Cannot find file : profile.txt";
    
    private ArrayList<Profile> allProfiles = new ArrayList<>();
    private HighScoreTable highScores;
    private Profile currentProfile;

    /**
     * Creates a new Profile.
     * @param newPlayer The name that the player has chosen for their profile.
     */
    public void newProfile(String newPlayer) {
        Profile newProfile = new Profile(newPlayer, this.allProfiles);
        this.allProfiles.add(newProfile);
        appendProfileToFile(newProfile);
        setCurrentProfile(newProfile);
    }

    /**
     * Removes the given profile.
     * @param profileToRemove The profile to be removed.
     */
    public void removeProfile(Profile profileToRemove) {
    	highScores.removeProfileEntries(profileToRemove);
    	allProfiles.remove(profileToRemove);
        overWriteFile(arrayToString());
    }

    /**
     * Finds the profile with the given id, if it exists.
     * @param idForProfile The id used to find the profile.
     * @return The profile with the matching id or null.
     */
    public Profile findProfile(String idForProfile) {
        Profile profileFound = null;

        for (Profile eachProfile : this.allProfiles) {
            if (idForProfile.equals(eachProfile.getProfileID())) {
                profileFound = eachProfile;
            }
        }

        return profileFound;
    }
    
    /**
     * Overwrites the profile's file that holds all the profiles' data.
     */
    public void overWriteFile() {
    	overWriteFile(arrayToString());
    }

    /**
     * Reads the profile file.
     * @return True if the file was read successfully, false otherwise.
     */
    public boolean readFile() {
        try {
            allProfiles = new ArrayList<>();
            File inputFile = new File(FILE_PATH);

            if (!inputFile.exists()) {
                return true;
            }

            Scanner fileScanner = new Scanner(inputFile);

            populateProfileArray(fileScanner);
            fileScanner.close();
            
            currentProfile = getRecentProfile();
            return true;
        } catch(Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    /**
     * Gets all the profiles.
     * @return All the profiles.
     */
    public ArrayList<Profile> getAllProfiles() {
        return allProfiles;
    }
    
    /**
     * Gets the most recently used Profile when opening the game.
     * @return The most recently used Profile.
     */
    public Profile getRecentProfile() {
    	if (allProfiles.size() == 0) {
    		return null;
    	}
    	
    	return allProfiles.get(allProfiles.size() - 1);
    }

    /**
     * Gets the current profile being used.
     * @return The current profile.
     */
    public Profile getCurrentProfile() {
        return currentProfile;
    }

    /**
     * Sets the profile that is currently being used.
     * @param currentProfile The profile to be the current profile.
     */
    public void setCurrentProfile(Profile currentProfile) {
        this.currentProfile = currentProfile;
    }
    
    /**
     * Sets the current instance of HighScoreTable being used.
     * @param highScores The current HighScoreTable being used.
     */
    public void setHighScores(HighScoreTable highScores) {
    	this.highScores = highScores;
    }
    
    /**
     * Creates a list of profiles in string formatting.
     * @return The list of profiles.
     */
    private ArrayList<String> arrayToString() {
        ArrayList<String> stringProfiles = new ArrayList<>();

        for (Profile diffProfile : this.allProfiles) {
            String id = diffProfile.getProfileID();
            String name = diffProfile.getPlayerName();
            int level = diffProfile.getMaxLevel();

            String profileToString = id + " " + name + " " + level;

            stringProfiles.add(profileToString);
        }

        return stringProfiles;
    }
    
    /**
     * Populates the list of profiles using the profile file.
     * @param scanner The profile file to take data from.
     */
    private void populateProfileArray(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String profileID = scanner.next();
            String playerName = scanner.next();
            int maxLevel = scanner.nextInt();
            scanner.nextLine(); // Discard CR/LF characters left.

            Profile oldProfile = new Profile(profileID, playerName, maxLevel);
            this.allProfiles.add(oldProfile);
        }
    }
    
    /**
     * Overwrites the profile's file that holds all the profiles' data.
     * @param stringProfiles The data to be written into the file.
     */
    private void overWriteFile(ArrayList<String> stringProfiles) {
        try {
        	FileWriter fileWriter = new FileWriter(FILE_PATH, false);
            BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
            PrintWriter writeInToFile = new PrintWriter(bufferWriter);
            		
            for (String profileLine : stringProfiles) {
                writeInToFile.println(profileLine);
            }
            
            writeInToFile.close();
        } catch (IOException e) {
            System.out.println(FIND_ERROR);
            System.exit(0);
        }
    }

    /**
     * Appends the new Profile to the file containing all the profiles.
     * @param newProfile The new profile to append to the file.
     */
    private void appendProfileToFile(Profile newProfile) {
        String id = newProfile.getProfileID();
        String name = newProfile.getPlayerName();
        int level = newProfile.getMaxLevel();

        String profileToWrite = id + " " + name + " " + level;

        try {
        	FileWriter fileWriter = new FileWriter(FILE_PATH, true);
            BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
            PrintWriter writeIntToFile = new PrintWriter(bufferWriter);
            
            writeIntToFile.println(profileToWrite);
            writeIntToFile.close();
        } catch (IOException e) {
            System.out.println(FIND_ERROR);
            System.exit(0);
        }
    }
}
