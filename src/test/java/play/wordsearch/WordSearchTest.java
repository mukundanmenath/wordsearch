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
	
	@Test(expected = Exception.class)
	public void whenEmptyFileNameIsSuppliedToWordSearchThrowError() throws Exception 
	{
		WordSearch ws = new WordSearch("");
	}
	
	@Test(expected = Exception.class)
	public void whenEmptyFileIsSuppliedToWordSearchThrowError() throws Exception 
	{
		WordSearch ws = new WordSearch("/empty.txt");
	}

	@Test(expected = Exception.class)
	public void whenNonEmptyInputFileIsNotValidThrowError() throws Exception 
	{
		WordSearch ws = new WordSearch("/file-with-no-grid.txt");
	}
}
