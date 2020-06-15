package test;

import org.junit.Test;

public class ImdbMovieSearch extends ParentTestIMDb {


	@Test
			public void searchMovie(){
		setUp();

		testMovieSearch("It", "Richard Thomas", "1990");

		tearDown();
	}
}
