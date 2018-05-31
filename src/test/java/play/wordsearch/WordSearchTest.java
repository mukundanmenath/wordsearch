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
		//first line of the input file: AB
		//second line of the input file: A,B
		//third line of the input file: C,D
		WordSearch ws = new WordSearch("/file-with-minimal-grid.txt");
		char[][] alphaGrid = ws.getAlphaGrid();
		assertEquals("alphaGrid is square matrix", alphaGrid[0].length, alphaGrid[1].length);
		assertEquals("found AB", "AB: (0,0),(1,0)", ws.searchWords());
	}
	
 	@Test
	public void whenInputFileSuppliedToWordSearchIsMinimalAndGoodWithTwoWords() throws Exception 
	{
		//first line of the input file: AB,BA
		//second line of the input file: A,B
		//third line of the input file: C,D
		WordSearch ws = new WordSearch("/file-with-minimal-grid-two-search-words.txt");
		char[][] alphaGrid = ws.getAlphaGrid();
		assertEquals("alphaGrid is square matrix", alphaGrid[0].length, alphaGrid[1].length);
		assertEquals("found AB,BA", "AB: (0,0),(1,0)\nBA: (1,0),(0,0)", ws.searchWords());
	}

	@Test
	public void whenInputFileSuppliedToWordSearchIsMinimalAndGoodWithSearchOneWordDown() throws Exception 
	{
		//first line of the input file: AC
		//second line of the input file: A,B
		//third line of the input file: C,D
		WordSearch ws = new WordSearch("/file-with-minimal-grid-search-down.txt");
		char[][] alphaGrid = ws.getAlphaGrid();
		assertEquals("alphaGrid is square matrix", alphaGrid[0].length, alphaGrid[1].length);
		assertEquals("found AC", "AC: (0,0),(0,1)", ws.searchWords());
	}
	
	@Test
	public void whenInputFileSuppliedToWordSearchIsMinimalAndGoodWithSearchTwoWordsDown() throws Exception 
	{
		//first line of the input file: AC,CA
		//second line of the input file: A,B
		//third line of the input file: C,D
		WordSearch ws = new WordSearch("/file-with-minimal-grid-search-two-words-down.txt");
		char[][] alphaGrid = ws.getAlphaGrid();
		assertEquals("alphaGrid is square matrix", alphaGrid[0].length, alphaGrid[1].length);
		assertEquals("found AC,CA", "AC: (0,0),(0,1)\nCA: (0,1),(0,0)", ws.searchWords());
	}
	
	@Test
	public void whenInputFileSuppliedToWordSearchIsMinimalAndGoodWithSearchOneWordAcross() throws Exception 
	{
		//first line of the input file: AD
		//second line of the input file: A,B
		//third line of the input file: C,D
		WordSearch ws = new WordSearch("/file-with-minimal-grid-search-one-word-across.txt");
		char[][] alphaGrid = ws.getAlphaGrid();
		assertEquals("alphaGrid is square matrix", alphaGrid[0].length, alphaGrid[1].length);
		assertEquals("found AD", "AD: (0,0),(1,1)", ws.searchWords());
	}
	
	@Test
	public void whenInputFileSuppliedToWordSearchIsMinimalAndGoodWithSearchOneWordAcrossReverse() throws Exception 
	{
		//first line of the input file: DA
		//second line of the input file: A,B
		//third line of the input file: C,D
		WordSearch ws = new WordSearch("/file-with-minimal-grid-search-one-word-across-reverse.txt");
		char[][] alphaGrid = ws.getAlphaGrid();
		assertEquals("alphaGrid is square matrix", alphaGrid[0].length, alphaGrid[1].length);
		assertEquals("found DA", "DA: (1,1),(0,0)", ws.searchWords());
	}

	@Test
	public void whenInputFileSuppliedToWordSearchIsMinimalAndGoodWithSearchTwoWordsAcross() throws Exception 
	{
		//first line of the input file: AD,DA
		//second line of the input file: A,B
		//third line of the input file: C,D
		WordSearch ws = new WordSearch("/file-with-minimal-grid-search-two-words-across.txt");
		char[][] alphaGrid = ws.getAlphaGrid();
		assertEquals("alphaGrid is square matrix", alphaGrid[0].length, alphaGrid[1].length);
		assertEquals("found AD,DA", "AD: (0,0),(1,1)\nDA: (1,1),(0,0)", ws.searchWords());
	}

	@Test
	public void whenInputFileSuppliedToWordSearchIsMinimalAndGoodWithSearchOneWordSouthWestToNorthEast() throws Exception 
	{
		//first line of the input file: CB
		//second line of the input file: A,B
		//third line of the input file: C,D
		WordSearch ws = new WordSearch("/file-with-minimal-grid-search-one-word-sw-ne.txt");
		char[][] alphaGrid = ws.getAlphaGrid();
		assertEquals("alphaGrid is square matrix", alphaGrid[0].length, alphaGrid[1].length);
		assertEquals("found CB", "CB: (0,1),(1,0)", ws.searchWords());
	}
	
	@Test
	public void whenInputFileSuppliedToWordSearchIsMinimalAndGoodWithSearchOneWordNorthEastToSouthWest() throws Exception 
	{
		//first line of the input file: BC
		//second line of the input file: A,B
		//third line of the input file: C,D
		WordSearch ws = new WordSearch("/file-with-minimal-grid-search-one-word-ne-sw.txt");
		char[][] alphaGrid = ws.getAlphaGrid();
		assertEquals("alphaGrid is square matrix", alphaGrid[0].length, alphaGrid[1].length);
		assertEquals("found BC", "BC: (1,0),(0,1)", ws.searchWords());
	}

	@Test
	public void whenInputFileSuppliedToWordSearchIsMinimalAndGoodWithSearchTwoWordsSouthWestToNorthEastAndReverse() throws Exception 
	{
		//first line of the input file: CB,BC
		//second line of the input file: A,B
		//third line of the input file: C,D
		WordSearch ws = new WordSearch("/file-with-minimal-grid-search-one-word-sw-ne-ne-sw.txt");
		char[][] alphaGrid = ws.getAlphaGrid();
		assertEquals("alphaGrid is square matrix", alphaGrid[0].length, alphaGrid[1].length);
		assertEquals("found CB,BC", "CB: (0,1),(1,0)\nBC: (1,0),(0,1)", ws.searchWords());
	}

	@Test
	public void whenInputFileSuppliedToWordSearchIsGoodWithNonTrivialSearchWords() throws Exception 
	{
		WordSearch ws = new WordSearch("/sample-input-file.txt");
		char[][] alphaGrid = ws.getAlphaGrid();
		assertEquals("alphaGrid is square matrix", alphaGrid[0].length, alphaGrid[1].length);
		assertEquals("found", 
			"BONES: (0,6),(0,7),(0,8),(0,9),(0,10)\n" +
			"KHAN: (5,9),(5,8),(5,7),(5,6)\n" +
			"KIRK: (4,7),(3,7),(2,7),(1,7)\n" +
			"SCOTTY: (0,5),(1,5),(2,5),(3,5),(4,5),(5,5)\n" +
			"SPOCK: (2,1),(3,2),(4,3),(5,4),(6,5)\n" +
			"SULU: (3,3),(2,2),(1,1),(0,0)\n" +
			"UHURA: (4,0),(3,1),(2,2),(1,3),(0,4)", ws.searchWords());
	}

	@Test
	public void whenInputFileSuppliedToWordSearchIsGoodWithNonTrivialSearchWordsOne() throws Exception 
	{
		WordSearch ws = new WordSearch("/sample-input-file1.txt");
		char[][] alphaGrid = ws.getAlphaGrid();
		assertEquals("alphaGrid is square matrix", alphaGrid[0].length, alphaGrid[1].length);
		assertEquals("found", 
			"LAMPE: (3,10),(4,10),(5,10),(6,10),(7,10)\n" +
			"MENATH: (5,7),(5,6),(5,5),(5,4),(5,3),(5,2)\n" +
			"MILLER: (0,13),(1,12),(2,11),(3,10),(4,9),(5,8)\n" +
			"NANDA: (8,10),(8,11),(8,12),(8,13),(8,14)\n" +
			"STALL: (0,0),(1,1),(2,2),(3,3),(4,4)", ws.searchWords());
	}
}
