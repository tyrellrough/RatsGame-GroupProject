import java.util.ArrayList;
import java.util.UUID;

/**
 * Profile.java
 *
 * Profile Class holds data about the user's name and maximum unlocked level.
 *
 * @version 1.0
 * @since 23/11/21
 * Last modified 24/11/21
 * @author (REMOVED)
 */

public class Profile {
    private final String playerName;
    private final String profileID;
    private int maxLevel;

    /**
     * Constructor for a new Profile.
     * @param newPlayer The new profile's player name.
     * @param allProfiles The list of all the previous profiles.
     */
    public Profile(String newPlayer, ArrayList<Profile> allProfiles) {
        String possibleID;

        do {
            UUID newID = UUID.randomUUID();
            possibleID = newID.toString();
        } while (!isIDUnique(possibleID, allProfiles));

        this.profileID = possibleID;
        this.playerName = newPlayer;
        this.maxLevel = 1;
    }

    /**
     * Constructor to create a saved profile.
     * @param id The ID for this profile.
     * @param name The name for this profile.
     * @param maxLevel The max level unlocked for this profile.
     */
    public Profile(String id, String name, int maxLevel) {
        this.profileID = id;
        this.playerName = name;
        this.maxLevel = maxLevel;
    }

    /**
     * Gets the profile ID.
     * @return The profile ID.
     */
    public String getProfileID() {
        return this.profileID;
    }

    /**
     * Gets the player's name.
     * @return The player's name.
     */
    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * Gets the max level unlocked for this profile.
     * @return The max level.
     */
    public int getMaxLevel() {
        return this.maxLevel;
    }

    /**
     * Increments the max level unlocked, if the user has won the current max
     * level unlocked.
     * @param currentLevelWon The level number to check if it is the current
     *                        max level unlocked.
     */
    public void setMaxLevel(int currentLevelWon) {
        if (currentLevelWon == this.maxLevel) {
            this.maxLevel++;
        }
    }

    /**
     * Checks if the ID is unique.
     * @param id The ID to check.
     * @param allProfiles All the profiles available.
     * @return True or False on whether the ID is unique.
     */
    private boolean isIDUnique(String id, ArrayList<Profile> allProfiles) {
        boolean isUnique = false;
        int counter = 0;
        int arraySize = allProfiles.size() - 1;

        if (allProfiles.size() == 0) {
            isUnique = true;
        } else {
            while ((!isUnique) && (counter <= arraySize))  {
                if (!id.equals(allProfiles.get(counter).getProfileID())) {
                    isUnique = true;
                } else {
                    counter++;
                }
            }
        }

        return isUnique;
    }
}
