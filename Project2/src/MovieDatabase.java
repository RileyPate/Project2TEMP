import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Project #2
 * CS 2334, Section 011
 * Feb 26, 2016
 * <P>
 * Maintains the list of all shows and movies. Enabling the program
 * to parse and add shows and search for them
 * </P>
 */
public class MovieDatabase {
	
	/**List containing all the movies and TV shows*/
	private ArrayList<Show> mDb = new ArrayList<Show>();
	
	/**Constructor for MDb class. 
	 * 
	 * @param moviesFile Name of file containing movies
	 * @param TVFile Name of file containing TV shows
	 */
	public MovieDatabase(String moviesFile, String TVFile) {
		try {
			mDb.addAll(readShowsIntoDB(moviesFile, true));
			mDb.addAll(readShowsIntoDB(TVFile, false));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**Takes file of movies, parses them, and add them to database
	 * 
	 * @param file Name of File containing shows
	 * @param isMovie true if file contains movies, false if file containing TV
	 * @throws FileNotFoundException 
	 */
	private ArrayList<Show> readShowsIntoDB(String file, boolean isMovie) throws IOException {
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
	
		if (isMovie) {
			ArrayList<Show> movies = new ArrayList<Show>();
			
			String line = br.readLine();
			while (line != null) {
				movies.add(parseMovie(line));
				line = br.readLine();
			}
			br.close();
			fr.close();
			return movies;
		} else {
			ArrayList<Show> series = new ArrayList<Show>();
			ArrayList<Episode> episodes = new ArrayList<Episode>();
			String line = br.readLine();
			while (line != null) {
				if (line.contains("{")) {
					Episode toAdd = parseEpisode(line, (TVSeries)(series.get(series.size() - 1)));
					((TVSeries)series.get(series.size() - 1)).addEpisode(toAdd);
					episodes.add(toAdd);
				} else {
					series.add(parseTV(line));
				}
				line = br.readLine();
			}
			br.close();
			fr.close();
			series.addAll(episodes);
			return series;
		}
	}
	
	/**Search database for specific shows
	 * 
	 * @param movies Search for movies?
	 * @param tv Search for TV shows?
	 * @param episodes Search for specific episodes? (Can't be true if tv is false)
	 * @param exactMatch Only allow if title exactly matches search
	 * @param sortByTitle Sort by title (if true) or by year (if false)
	 * @param title Title to search for. "'-1'" will be if any
	 * @param years Years to search for. "-1" will be in first index if any
	 * @return
	 */
	public ArrayList<Show> searchFiles(boolean movies, boolean tv, 
			boolean episodes, boolean exactMatch, boolean sortByTitle, String title, ArrayList<String> years) {
		
		ArrayList<Show> shows = new ArrayList<Show>();
		
		for (Show show : mDb) {
			if ((show.toString().toLowerCase().matches(".*" + title.toLowerCase() + ".*") ||
					title.equals("-1")) &&
					(years.contains(show.getYear()) ||
					years.contains(show.getEndYear()) ||
					years.get(0).equals("-1"))) {
				if ((movies && show instanceof Movie) || (tv && show instanceof TVSeries) ||
						(episodes && show instanceof Episode)) {
					shows.add(show);
				}
			}
		}
		
		if (exactMatch) {
			ArrayList<String> titles = new ArrayList<String>();
			for (Show show : mDb) {
				titles.add(show.getName());
			}
			Collections.sort(mDb, Show.TITLE_COMPARATOR);
			int index = Collections.binarySearch(titles, title);
			if (index > 0) {
				shows.add(mDb.get(index));
			}
		}
		
		if (sortByTitle) {
			Collections.sort(shows, Show.TITLE_COMPARATOR);
		} else {
			Collections.sort(shows, Show.YEAR_COMPARATOR);
		}
		return shows;
	}
	
	/**
	 * Takes information about movie and converts it into a Movie instance
	 * 
	 * Used code from Dustin Gier's Project 1
	 * @param Information about movie
	 * @return Returns Movie created based off information in the line
	 */
	private Movie parseMovie(String line) {
		String name = "";
		String year = "";
		ArrayList<String> extraInfo = new ArrayList<String>();
		
		//find where parens with the year released is
		int index = line.indexOf("(");

		while (true) {
			if (((Character.getNumericValue(line.charAt(index + 1)) == 1 || 
				Character.getNumericValue(line.charAt(index + 1)) == 2) &&
				Character.getNumericValue(line.charAt(index + 2)) >= 0 && 
				Character.getNumericValue(line.charAt(index + 2)) <= 9 &&
				Character.getNumericValue(line.charAt(index + 3)) >= 0 && 
				Character.getNumericValue(line.charAt(index + 3)) <= 9 &&
				Character.getNumericValue(line.charAt(index + 4)) >= 0 && 
				Character.getNumericValue(line.charAt(index + 4)) <= 9) ||
				(line.charAt(index + 1) == '?' &&
				line.charAt(index + 2) == '?' &&
				line.charAt(index + 3) == '?' &&
				line.charAt(index + 4) == '?')) 
			{
				//index = line.indexOf("(", index + 1);
				break;
			} else {
				index = line.indexOf("(", index + 1);
			}
		}
		
		//set name and year based on where the parens with year are
		name = line.substring(0, index - 1);
		year = line.substring(index + 1, index + 5);
		
		//check if extra info is after year in first parens
		if (index + 5 != line.indexOf(")", index)) {
			extraInfo.add(line.substring(index + 6, line.indexOf(")")));
			index += 5;
		}
		
		//look for extra information by going through each set of parens
		while (line.indexOf("(", index + 1) != -1) {
			extraInfo.add(line.substring((line.indexOf("(", index + 1) + 1), (line.indexOf(")", line.indexOf("(", index + 1) + 1))));
			index = line.indexOf(")", line.indexOf("(", index + 1) + 1) + 1;
		}
		
		//return the movie based on the string
		return new Movie(name, year, extraInfo);
	}
	
	/**
	 * Parses a TVSeries from a TVSeries file
	 * 
	 * @param line String to be parsed
	 * @return TVSeries based on line
	 */
	private TVSeries parseTV(String line) {
		String name = "";
		String year = "";
		String endYear = "";
		
		name = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
		year = line.substring(line.length() - 9, line.length() - 5);
		endYear = line.substring(line.length() - 4, line.length());
		
		return new TVSeries(name, year, endYear);
	}
	
	/**
	 * Parses episode from TV file
	 * 
	 * @param line String to be parsed
	 * @param series Series of which this episode is a part
	 * @return Episode based on line
	 */
	private Episode parseEpisode(String line, TVSeries series) {
		String name = "";
		String year = "";
		String episodeNumber = "";
		
		if (line.contains("#")) {
			if (line.indexOf("{") + 1 < line.indexOf("#") - 2) {
				name = line.substring(line.indexOf("{") + 1, line.indexOf("#") - 2);
			}
			episodeNumber = line.substring(line.indexOf("#"), line.lastIndexOf(")") - 1);
		} else {
			name = line.substring(line.indexOf("{") + 1, line.indexOf("}") - 1);
		}
		
		year = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
		
		return new Episode(name, year, episodeNumber, series);
	}
}
