package play.wordsearch;

import java.nio.file.*;
import java.nio.charset.*;
import java.io.*;

/**
 * 
 *
 */
public class WordSearch 
{
	private String[] wordsToSearch = null;
	
	private String[][] alphaGrid = null;
	
	public String[] getWordsToSearch()
	{
		return this.wordsToSearch;
	}
	
	public String[][] getAlphaGrid()
	{
		return this.alphaGrid;
	}
	
	public WordSearch()
	{
	}
	
	public WordSearch(String inputFileName) throws Exception
	{
		try
		{
			if (inputFileName == null || inputFileName.trim().length() == 0)
				throw new Exception("Invalid file name");
			Path path = Paths.get(inputFileName);
			if (path.toFile().length() == 0)
				throw new Exception("Empty file");
			try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) 
			{
				String searchWordsLine = reader.readLine();
				if (searchWordsLine == null || searchWordsLine.trim().length() == 0)
					throw new Exception("No search string found");
				if (!searchWordsLine.matches("([A-Z]{2,15})+(,[A-Z]{2,15})*"))
					throw new Exception("Search words are not formatted correctly");
				
				wordsToSearch = searchWordsLine.split(",");
				
				String firstRow = reader.readLine();
				String[] firstRowElements = firstRow.split(",");
				
				if (firstRowElements.length < 2)
					throw new Exception("The grid size should be at least 2");
				
				alphaGrid = new String[firstRowElements.length][firstRowElements.length];
				validateGridRowAndPopulateGrid(firstRowElements, 0);
				
				for (int i = 1; i < firstRowElements.length; i++)
				{
					String row = reader.readLine();
					String[] rowElements = row.split(",");
					if (rowElements.length != firstRowElements.length)
						throw new Exception("Rows are of different lengths");
					
					validateGridRowAndPopulateGrid(rowElements, i);
				}
			}
			System.out.println(inputFileName + " Input file is valid");
		} catch (Exception e)
		{
			System.out.println(inputFileName + " Error is: " + e.getMessage());
			throw e;
		}
	}
	
	private void validateGridRowAndPopulateGrid(String[] rowElements, int rowNumber) throws Exception
	{
		for (int j = 0; j < rowElements.length; j++)
		{
			if (rowElements[j].length() != 1)
				throw new Exception("Grid has invalid element");
			this.alphaGrid[rowNumber][j] = rowElements[j];
		}
	}
	
	public String searchWords()
	{
		return "AB: (0,0),(0,1)";
	}
}
