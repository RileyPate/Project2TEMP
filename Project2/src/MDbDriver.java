import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Project #2
 * CS 2334, Section 011
 * Feb 26, 2016
 * <P>
 * Runs the program. Contains both the main method and initializes the database
 * </P>
 */
public class MDbDriver {

	/**Initializes the database and runs program based on user input
	 * @param args Useless for this program
	 */
	public static void main(String[] args) {
		BufferedReader inputReader= new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("What is the movie file?");
			String movieFile = inputReader.readLine();
			File movies = new File(movieFile);
			while (!movies.exists()) {
				System.out.println("Sorry, not valid input. Try again");
				movieFile = inputReader.readLine();
				movies = new File(movieFile);
			}
			System.out.println("What is the TV file?");
			String TVFile = inputReader.readLine();
			File TV = new File(TVFile);
			while (!TV.exists()) {
				System.out.println("Sorry, not valid input. Try again");
				TVFile = inputReader.readLine();
				TV = new File(TVFile);
			}
			MovieDatabase mDb = new MovieDatabase(movieFile, TVFile);
			while (true) {
				System.out.println("Search (M)ovies, (S)eries, or (B)oth? (n to quit)");
				String MSB = inputReader.readLine();
				while (!MSB.equalsIgnoreCase("m") && !MSB.equalsIgnoreCase("s") && !MSB.equalsIgnoreCase("b") && !MSB.equalsIgnoreCase("n")) {
					System.out.println("Sorry, not valid input. Try again");
					MSB = inputReader.readLine();
				}
				if (MSB.equalsIgnoreCase("n")) {
					System.out.println("Thank you, user");
					System.exit(0);
				}
				
			    System.out.println("Search (T)itle, (Y)ear, or (B)oth?");
			    String TYB = inputReader.readLine();
			    while (!TYB.equalsIgnoreCase("t") && !TYB.equalsIgnoreCase("y") && !TYB.equalsIgnoreCase("b")) {
					System.out.println("Sorry, not valid input. Try again");
					TYB = inputReader.readLine();
				}
			    
			    String episodes = "n";
			    if ((MSB.equalsIgnoreCase("s") || MSB.equalsIgnoreCase("b"))) {
			    	System.out.println("Include episodes in search and output (Y/N)?");
			        episodes = inputReader.readLine();
			        while (!episodes.equalsIgnoreCase("y") && !episodes.equalsIgnoreCase("n")) {
						System.out.println("Sorry, not valid input. Try again");
						episodes = inputReader.readLine();
					}
			    }
			    
			    String exactPartial = "";
			    String title = "-1";
			    if (TYB.equalsIgnoreCase("t") || TYB.equalsIgnoreCase("b")) {
			        System.out.println("Search for (e)xact or (p)artial matches?");
			        exactPartial = inputReader.readLine();
			        while (!exactPartial.equalsIgnoreCase("e") && !exactPartial.equalsIgnoreCase("p")) {
						System.out.println("Sorry, not valid input. Try again");
						exactPartial = inputReader.readLine();
					}
			    	System.out.println("Title?");
			    	title = inputReader.readLine();
			    }
			    
			    ArrayList<String> y = new ArrayList<String>();
			    String years = "-1";
			    if (TYB.equalsIgnoreCase("y") || TYB.equalsIgnoreCase("b")) {
			    	System.out.println("Year(s)?");
			    	years = inputReader.readLine();
			    	while (!years.matches("[0-9][0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]")
			    			&& !years.matches("([0-9][0-9][0-9][0-9])(,[0-9][0-9][0-9][0-9])*")) {
			    		System.out.println("Sorry, not valid input. Try Again");
			    		years = inputReader.readLine();
			    	}
			    	
			    	if (years.matches("[0-9][0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]")) {
			    		int startYear = Integer.parseInt(years.substring(0, 4));
			    		int endYear   = Integer.parseInt(years.substring(5, 9));
			    		if (startYear > endYear) {
			    			int hold = startYear;
			    			startYear = endYear;
			    			endYear = hold;
			    		}
			    		for (int i = startYear; i <= endYear; i++) {
			    			y.add(String.valueOf(i));
			    		}
			    	} else if (years.matches("([0-9][0-9][0-9][0-9])(,[0-9][0-9][0-9][0-9])*")) {
			    		int numberOfYears = 1 + (years.length() - 4) / 5;
			    		for (int i = 0; i < numberOfYears; i++) {
			    			y.add(String.valueOf(years.substring(i * 5, i * 5 + 4)));
			    		}
			    	}
			    } else {
			    	y.add("-1");
			    }
			    
			    String sort = "";
			    if (TYB.equalsIgnoreCase("b")) {
			    	System.out.println("Sort by (T)itle or (Y)ear?");
			    	sort = inputReader.readLine();
			    	while (!sort.equalsIgnoreCase("t") && !sort.equalsIgnoreCase("y")) {
						System.out.println("Sorry, not valid input. Try again");
						sort = inputReader.readLine();
					}
			    } else if (TYB.equalsIgnoreCase("t")) {
			    	sort = "t";
			    } else if (TYB.equalsIgnoreCase("y")) {
			    	sort = "y";
			    }
			    
			    ArrayList<Show> results = mDb.searchFiles((MSB.equalsIgnoreCase("m") || MSB.equalsIgnoreCase("b")), 
			    		(MSB.equalsIgnoreCase("s") || MSB.equalsIgnoreCase("b")), 
			    		episodes.equalsIgnoreCase("y"), exactPartial.equalsIgnoreCase("e"),
			    		sort.equalsIgnoreCase("t"), title, y);
			    
			    if (MSB.equalsIgnoreCase("m")) System.out.println("Searched for Movies");
			    if (MSB.equalsIgnoreCase("s")) System.out.println("Searched for TV Series");
			    if (MSB.equalsIgnoreCase("b")) System.out.println("Searched for Movies and TV Series");
			    if (episodes.equalsIgnoreCase("y")) System.out.println("Includes episodes");
			    if (exactPartial.equalsIgnoreCase("e")) System.out.println("Exact Match: " + (title.equalsIgnoreCase("-1") ? "Any" : title));
			    if (exactPartial.equalsIgnoreCase("p")) System.out.println("Partial Match: " + (title.equalsIgnoreCase("-1") ? "Any" : title));
			    if (years.equalsIgnoreCase("-1")) System.out.println("Years: Any");
			    else System.out.println("Years: " + years);
			    if (sort.equalsIgnoreCase("t")) System.out.println("Sorted by Title");
			    if (sort.equalsIgnoreCase("y")) System.out.println("Sorted by Years");
			    System.out.println("================================================================================");
			    
			    
			    for (Show show : results) {
			    	System.out.println(show.toString());
			    }
			    
			    System.out.println("Save (y/n)?");
			    String yn = inputReader.readLine();
			    
			    while (!yn.equalsIgnoreCase("y") && !yn.equalsIgnoreCase("n")) {
		    		System.out.println("Sorry, not valid input. Try Again");
		    		yn = inputReader.readLine();
			    }
	    		if(yn.equalsIgnoreCase("y")){
			    	System.out.println("Enter a file to save to:");
			    	File saved = new File(inputReader.readLine());
			    	
			    	PrintWriter out = new PrintWriter(saved);
			    	
			    	System.out.println("Saving...");
			    	for(Show show : results)
			    		out.println(show.toString());
			    	System.out.println("Done");
			    	out.close();
			    	
			    } else if (yn.equalsIgnoreCase("n")){
			    	System.out.println("Your results will not be saved.");
			    }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	        
	}
}
