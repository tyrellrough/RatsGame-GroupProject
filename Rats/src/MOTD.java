import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

/**
 * MOTD.java
 * 
 * Responsible for fetching the latest MOTD message from the API.
 * 
 * @version 1.0
 * @since 25/11/2021
 * Last modified 04/12/2021
 * @author (REMOVED)
 */
public class MOTD {
	private static final int PUZZLE_SOLVE_CONST = 6;
	private static final int ENC_CONST = 65;
	private static final int ALPH_LEN = 26;
	private static final String PUZZLE_URL = "http://cswebcat.swansea.ac.uk/puzzle";
	private static final String MESSAGE_URL = "http://cswebcat.swansea.ac.uk/message?solution=";
	private static final String CS230 = "CS-230";
	
	/**
	 * Fetches the latest MOTD message by solving the required puzzle.
	 * @return The MOTD message to display or null if an error occurred.
	 */
	public String get() {
		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(PUZZLE_URL)).build();

		    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			String message = response.body();

			StringBuilder result = new StringBuilder(solve(message).size());
			for (Character c : solve(message)) {
				result.append(c);
			}
			
			String converted = result.toString();
			String convertedAdd = (solve(message).size() + PUZZLE_SOLVE_CONST) + converted + CS230;
			    
			HttpClient client1 = HttpClient.newHttpClient();
		    HttpRequest request1 = HttpRequest.newBuilder().uri(URI.create(MESSAGE_URL + convertedAdd)).build();

		    HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());

			return response1.body();
		 } catch(Exception ex) {
			 ex.printStackTrace();
			 return null;
		 }
	}
	
	/**
	 * Solves the puzzle and returns the solution.
	 * @param message The starting puzzle message.
	 * @return The puzzle solution.
	 */
	private ArrayList<Character> solve(String message) {
		ArrayList<Character> result = new ArrayList<>();
		
	    for (int i = 0; i < message.length(); i++) {
	    	int j = i + 1;
	    	
	        if (i % 2 >= 1) {
	        	char ch = (char) (((int) message.charAt(i) + j - ENC_CONST) % ALPH_LEN + ENC_CONST);
	            result.add(ch);
	        } else {
	        	char ch = (char) (((int) message.charAt(i) - j + ENC_CONST) % ALPH_LEN + ENC_CONST);
	            result.add(ch);
	        }
	    }
	    
	    return result;
	}
}
