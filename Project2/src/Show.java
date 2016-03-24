import java.util.Comparator;

/**
 * Project #1
 * CS 2334, Section 011
 * Feb 16, 2016
 * <P>
 * Superclass for movies and TV shows to extend
 * </P>
 */
public class Show {

	/**Name of show*/
	private String name;
	/**Year show is first released*/
	private String year;
	/**Year show is last released. Equal to year if movie*/
	private String endYear;
	
	/**Constructor for generic show
	 * 
	 * @param name Name of show
	 * @param year Release year of show
	 * @param endYear Last year of show
	 */
	public Show(String name, String year, String endYear) {
		this.name = name;
		this.year = year;
		this.endYear = endYear;
	}
	
	/**Return string of show
	 * @Override
	 */
	public String toString() {
		return name + " " + year + "-" + endYear;
	}
	
	/**
	 * @return Name of show
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return Year first released
	 */
	public String getYear() {
		return year;
	}
	
	/**
	 * @return Year last released
	 */
	public String getEndYear() {
		return endYear;
	}
	
	public static final Comparator<Show> TITLE_COMPARATOR = new Comparator<Show>() {
		@Override
		public int compare(Show o1, Show o2) {
			return o1.name.compareTo(o2.name); 
		}
	};
	
	public static final Comparator<Show> YEAR_COMPARATOR  = new Comparator<Show>() {
		@Override
		public int compare(Show o1, Show o2) {
			if (o1.year.equals(o2.year)) {
				return o1.year.compareTo(o2.year);
			}
			return o1.name.compareTo(o2.name); 
		}
	};
}
