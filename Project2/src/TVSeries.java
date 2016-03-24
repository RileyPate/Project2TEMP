import java.util.ArrayList;

/**
 * Project #1
 * CS 2334, Section 011
 * Feb 16, 2016
 * <P>
 * Class for storing information about TVShows
 * </P>
 */
public class TVSeries extends Show {

	/**ArrayList of episodes within a series*/
	private ArrayList<Episode> episodes = new ArrayList<Episode>();
	
	/**Constructor for TV Series
	 * 
	 * @param name Name of series
	 * @param year Year series was first produced
	 * @param endYear Year series was last produced
	 */
	public TVSeries(String name, String year, String endYear) {
		super(name, year, endYear);
	}
	
	/**Constructor for TV Series
	 * 
	 * @param name Name of series
	 * @param year Year series was first produced
	 * @param endYear Year series was last produced
	 * @param episodes Individual episodes within a series
	 */
	public TVSeries(String name, String year, String endYear, ArrayList<Episode> episodes) {
		super(name, year, endYear);
		this.episodes = episodes;
	}

	/**
	 * Adds episode to the series
	 * 
	 * @param episode Episode object to add
	 */
	public void addEpisode(Episode episode) {
		episodes.add(episode);
	}
	/**
	 * Return string version of series
	 */
	public String toString() {
		return "SERIES: " + getName() + " (" + getYear() + "-" + getEndYear() + ")";
	}
	
	public String episodeToString() {
		return episodes.get(episodes.size() - 1).toString();
	}
}
