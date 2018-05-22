package play.wordsearch;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple WordSearch.
 */
public class WordSearchTest
{
	@org.junit.Test(expected = Exception.class)
	public void whenNullFileNameIsSuppliedToWordSearchThrowError() throws Exception 
	{
		WordSearch ws = new WordSearch(null);
	}
}
