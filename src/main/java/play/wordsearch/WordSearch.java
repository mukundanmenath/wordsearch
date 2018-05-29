package play.wordsearch;

import java.nio.file.*;
import java.nio.charset.*;
import java.io.*;
import java.util.*;

/**
 * 
 *
 */
public class WordSearch 
{
	private String[] wordsToSearch = null;
	
	private char[][] alphaGrid = null;
	
	public String[] getWordsToSearch()
	{
		return this.wordsToSearch;
	}
	
	public char[][] getAlphaGrid()
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
				
				alphaGrid = new char[firstRowElements.length][firstRowElements.length];
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
			this.alphaGrid[rowNumber][j] = rowElements[j].charAt(0);
		}
	}
	
	public String searchWords()
	{
		//return "AB: (0,0),(0,1)";
		String result = searchOneWord(wordsToSearch[0]);
		return result;
	}
	
	private String searchOneWord(String word)
	{
		//return word + ": " + "(0,0),(0,1)";
		String output = word + ": ";
		char[] searchWordArr = word.toCharArray();
		String locations = searchInTheGrid(searchWordArr);
		return output + locations;
	}
	
	private String searchInTheGrid(char[] searchWordArr)
	{
		List<String> outputArr = new ArrayList<String>();
		outputArr.add("(0,0)");
		outputArr.add("(0,1)");
			
		String outputStr = null;
			
		if (outputArr.size() > 0)
			outputStr = outputArr.get(0);
		
		for (int i = 1; i < outputArr.size(); i++)
			outputStr += "," + outputArr.get(i);
	
		System.out.println("outputStr: " + outputStr);
		return outputStr;
	}
}
