import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

/**
 * Project #2
 * CS 2334, Section 011
 * Feb 26, 2016
 * <P>
 * Tests the movie database
 * </P>
 */
public class MovieDatabaseTest {

	@Test
	public void readFilesTest() {
		ArrayList<Show> test = new ArrayList<Show>();
		test.add(new Movie("Test movie", "1942", null));
		test.add(new Movie("Test movie 2", "1654", null));
		
		MovieDatabase mdb = new MovieDatabase("movieFile.txt", "TVFile.txt"); 
		
		assertEquals(mdb.mDb, test); //don't want to change variable to public
	}
	
	@Test
	public void searchFilesTest() {
		ArrayList<Show> test = new ArrayList<Show>();
		test.add(new Movie("Test movie", "1942", null));
		test.add(new Movie("Test movie 2", "1654", null));
		
		MovieDatabase mdb = new MovieDatabase("movieFile.txt", "TVFile.txt"); 

		ArrayList<String> years = new ArrayList<String>();
		years.add("1942");
		
		assertEquals(mdb.searchFiles(true, false, false, true, true, "Test", years), test);
	}

}
