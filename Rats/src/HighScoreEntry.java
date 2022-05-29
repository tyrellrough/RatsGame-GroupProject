/**
 * HighScoreEntry.java
 *
 * A single entry within a HighScoreTable which stores a score and the profile which achieved it.
 *
 * @version 1.0
 * @since 10/11/21
 * Last modified 18/11/21
 * @author (REMOVED)
*/
public class HighScoreEntry {
	private final int score;
	private final Profile profile;
	
	/**
	 * Construct a new HighScoreEntry table from its raw parts.
	 * @param score The score achieved.
	 * @param profile The profile which achieved the score.
	 */
	public HighScoreEntry(int score, Profile profile) {
		this.score = score;
		this.profile = profile;
	}
	
	/**
	 * Returns the stored score.
	 * @return The score achieved by the player.
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Returns the stored profile.
	 * @return The Profile which achieved the score.
	 */
	public Profile getProfile() {
		return profile;
	}
}
