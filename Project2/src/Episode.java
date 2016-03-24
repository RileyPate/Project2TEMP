/**
 * Project #1
 * CS 2334, Section 011
 * Feb 16, 2016
 * <P>
 * Individual episode within a TV series
 * </P>
 */
public class Episode extends Show {

	/**Number of episode within series*/
	private String episodeNumber;
	/**Series that this episode is in*/
	private TVSeries series;
	
	/**Constructor for individual episode
	 * 
	 * @param name Name of episode. null if episode has no name.
	 * @param year Year released
	 * @param episodeNumber Number within series
	 */
	public Episode(String name, String year, String episodeNumber, TVSeries series) {
		super(series.getName() + ": " + name, year, year);
		this.episodeNumber = episodeNumber;
		this.series = series;
	}
	
	/**
	 * Returns a string representing the episode
	 */
	public String toString() {
		if (getName() == null) {
			return "EPISODE: " + getName() + ": " + episodeNumber + " (" + getYear() + ")";
		}
		return "EPISODE: " + getName() + " (" + getYear() + ")";
	}
}
