import java.util.ArrayList;

/**
 * Project #1
 * CS 2334, Section 011
 * Feb 16, 2016
 * <P>
 * Class for storing information about a single movie
 * </P>
 */
public class Movie extends Show {
	
	/**List to store any extra info about the movie*/
	private ArrayList<String> extraInfo = new ArrayList<String>();
	
	/**Constructor for movie
	 * 
	 * @param name Name of movie
	 * @param year Year movie was released
	 * @param extraInfo Extra info about the movie
	 */
	public Movie(String name, String year, ArrayList<String> extraInfo) {
		super(name, year, year);
		this.extraInfo = extraInfo;
	}

	/**Return string version of this movie
	@Override
	*/
	public String toString() {
		if (extraInfo.isEmpty()) {
			return "MOVIE: " + getName() + " (" + getYear() + ")";
		}
		String extraInfoString = "";
		for (int i = 0; i < extraInfo.size(); i++) {
			extraInfoString += extraInfo.get(i);
			if (i != extraInfo.size() - 1) {
				extraInfoString += " ";
			}
		}
		return "MOVIE (" + extraInfoString + "):" + getName() + " (" + getYear() + ")";
	}
}
