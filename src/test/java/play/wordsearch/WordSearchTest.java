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
	public void whenInputFileSuppliedToWordSearchHasOnlyTheSearchWordsAndNoAlphaGridThrowError() throws Exception 
	{
		//first line of the input file: AB,AD,AC,AD
		//second line of the input file: <empty>
		WordSearch ws = new WordSearch("/file-with-no-grid.txt");
	}

	@Test(expected = Exception.class)
	public void whenInputFileSuppliedToWordSearchHasSearchWordsAndIncompleteAlphaGridThrowError() throws Exception 
	{
		//first line of the input file: AB,AD,AC,AD
		//second line of the input file: A [or A,B]
		//third line of the input file: <empty> [or C,]
		WordSearch ws = new WordSearch("/file-with-incomplete-grid1.txt");
	}

	@Test(expected = Exception.class)
	public void whenInputFileSuppliedToWordSearchHasSearchWordsAndElementsAreNotSingleLettersThrowError() throws Exception 
	{
		//first line of the input file: AB,BA,CD,DC,AC,CA,BD,DB,AD,DA,CB,BC
		//second line of the input file: A,BB
		//third line of the input file: C,DD
		WordSearch ws = new WordSearch("/file-with-invalid-grid1.txt");
	}

	@Test
	public void whenInputFileSuppliedToWordSearchIsMinimalAndGood() throws Exception 
	{
		//first line of the input file: AB,BA,CD,DC,AC,CA,BD,DB,AD,DA,CB,BC
		//second line of the input file: A,B
		//third line of the input file: C,D
		WordSearch ws = new WordSearch("/file-with-minimal-grid.txt");
		String[][] alphaGrid = ws.getAlphaGrid();
		assertEquals("alphaGrid is square matrix", alphaGrid[0].length, alphaGrid[1].length);
	}
	
}
