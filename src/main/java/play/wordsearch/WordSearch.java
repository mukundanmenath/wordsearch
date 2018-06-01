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
	
	public void printAlphaGrid()
	{
		for (int i = 0; i < alphaGrid.length; i++)
		{
			for (int j = 0; j < alphaGrid.length; j++)
			{
				System.out.print(alphaGrid[i][j] + " ");
			}
			System.out.println();
		}
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
				
				String wordPattern = "([A-Z]{2,})+(,[A-Z]{2,})*";
				if (!searchWordsLine.matches(wordPattern))
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
		printAlphaGrid();
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
	
	/**
	 * This is the method to search the alpha grid based on the search words
	 * Returned result has the formatted coordinates for each search word
	 * Sample result:
	 * BONES: (0,6),(0,7),(0,8),(0,9),(0,10)
	 * KHAN: (5,9),(5,8),(5,7),(5,6)
	 * KIRK: (4,7),(3,7),(2,7),(1,7)
	 * SCOTTY: (0,5),(1,5),(2,5),(3,5),(4,5),(5,5)
	 * SPOCK: (2,1),(3,2),(4,3),(5,4),(6,5)
	 * SULU: (3,3),(2,2),(1,1),(0,0)
	 * UHURA: (4,0),(3,1),(2,2),(1,3),(0,4)
	 */
	public String searchWords()
	{
		String result = "";
		for (int i = 0; i < wordsToSearch.length; i++)
		{
			if (i > 0 && i < wordsToSearch.length)
				result += "\n";
			result += searchOneWord(wordsToSearch[i]);
		}
		return result;
	}
	
	private String searchOneWord(String word)
	{
		System.out.println("searchOneWord: " + word);
		String output = word + ": ";
		char[] searchWordArr = word.toCharArray();
		String locations = searchOneWordInTheGrid(searchWordArr);
		return output + locations;
	}
	
	private String searchOneWordInTheGrid(char[] searchWordArr)
	{
		List<String> outputArr = new ArrayList<String>();
		int firstLetterRow = 0, firstLetterCol = 0;
		for (int i = 0; i < alphaGrid.length; i++)
		{
			for (int j = 0; j < alphaGrid.length; j++)
			{
				if (searchWordArr[0] == alphaGrid[i][j])
				{
					firstLetterRow = i;
					firstLetterCol = j;
					if (matchRemainingConsecutiveLetters(searchWordArr, firstLetterRow, firstLetterCol, outputArr))
						return formatCollectedLocations(outputArr);
					else
					{
						outputArr.clear();
					}
				}
			}
		}
		
		return "";
	}
	
	private boolean matchRemainingConsecutiveLetters(char[] searchWordArr, int firstLetterRow, int firstLetterCol, List<String> outputArr)
	{
		if (matchRemainingConsecutiveLettersWestToEast(searchWordArr, firstLetterRow, firstLetterCol, outputArr))
			return true;
		else if (matchRemainingConsecutiveLettersEastToWest(searchWordArr, firstLetterRow, firstLetterCol, outputArr))
			return true;
		else if (matchRemainingConsecutiveLettersNorthToSouth(searchWordArr, firstLetterRow, firstLetterCol, outputArr))
			return true;
		else if (matchRemainingConsecutiveLettersSouthToNorth(searchWordArr, firstLetterRow, firstLetterCol, outputArr))
			return true;
		else if (matchRemainingConsecutiveLettersNorthWestToSouthEast(searchWordArr, firstLetterRow, firstLetterCol, outputArr))
			return true;
		else if (matchRemainingConsecutiveLettersSouthEastToNorthWest(searchWordArr, firstLetterRow, firstLetterCol, outputArr))
			return true;
		else if (matchRemainingConsecutiveLettersSouthWestToNorthEast(searchWordArr, firstLetterRow, firstLetterCol, outputArr))
			return true;
		else if (matchRemainingConsecutiveLettersNorthEastToSouthWest(searchWordArr, firstLetterRow, firstLetterCol, outputArr))
			return true;
		else
			return false;
	}
	
	private boolean isInvalidBounds(int row, int col)
	{
		return (row < 0 || row >= alphaGrid.length || col < 0 || col >= alphaGrid.length);
	}
	
	private boolean matchRemainingConsecutiveLettersWestToEast(char[] searchWordArr, int row, int col, List<String> outputArr)
	{
		if (isInvalidBounds(row, col))
			return false;
		
		outputArr.clear();
		
		for (int i = 0; i < searchWordArr.length && (col + i) < alphaGrid.length; i++)
		{
			if (searchWordArr[i] == alphaGrid[row][col + i])
			{
				outputArr.add("(" + (col + i) + "," + row + ")");
			}
			else
			{
				return false;
			}
		}
		return (outputArr.size() == searchWordArr.length);
	}
	
	private boolean matchRemainingConsecutiveLettersEastToWest(char[] searchWordArr, int row, int col, List<String> outputArr)
	{
		if (isInvalidBounds(row, col))
			return false;
		
		outputArr.clear();
		
		for (int i = 0; i < searchWordArr.length && (col - i) >= 0; i++)
		{
			if (searchWordArr[i] == alphaGrid[row][col - i])
			{
				outputArr.add("(" + (col - i) + "," + row + ")");
			}
			else
			{
				return false;
			}
		}
		return (outputArr.size() == searchWordArr.length);
	}

	private boolean matchRemainingConsecutiveLettersNorthToSouth(char[] searchWordArr, int row, int col, List<String> outputArr)
	{
		if (isInvalidBounds(row, col))
			return false;
		
		outputArr.clear();
		
		for (int i = 0; i < searchWordArr.length && (row + i) < alphaGrid.length; i++)
		{
			if (searchWordArr[i] == alphaGrid[row + i][col])
			{
				outputArr.add("(" + col + "," + (row + i) + ")");
			}
			else
			{
				return false;
			}
		}
		return (outputArr.size() == searchWordArr.length);
	}

	private boolean matchRemainingConsecutiveLettersSouthToNorth(char[] searchWordArr, int row, int col, List<String> outputArr)
	{
		if (isInvalidBounds(row, col))
			return false;
		
		outputArr.clear();
		
		for (int i = 0; i < searchWordArr.length && (row - i) >= 0; i++)
		{
			if (searchWordArr[i] == alphaGrid[row - i][col])
			{
				outputArr.add("(" + col + "," + (row - i) + ")");
			}
			else
			{
				return false;
			}
		}
		return (outputArr.size() == searchWordArr.length);
	}

	private boolean matchRemainingConsecutiveLettersNorthWestToSouthEast(char[] searchWordArr, int row, int col, List<String> outputArr)
	{
		if (isInvalidBounds(row, col))
			return false;
		
		outputArr.clear();
		
		for (int i = 0; i < searchWordArr.length && (row + i) < alphaGrid.length && (col + i) < alphaGrid.length; i++)
		{
			if (searchWordArr[i] == alphaGrid[row + i][col + i])
			{
				outputArr.add("(" + (col + i) + "," + (row + i) + ")");
			}
			else
			{
				return false;
			}
		}
		return (outputArr.size() == searchWordArr.length);
	}

	private boolean matchRemainingConsecutiveLettersSouthEastToNorthWest(char[] searchWordArr, int row, int col, List<String> outputArr)
	{
		if (isInvalidBounds(row, col))
			return false;
		
		outputArr.clear();
		
		for (int i = 0; i < searchWordArr.length && (row - i) >= 0 && (col - i) >= 0; i++)
		{
			if (searchWordArr[i] == alphaGrid[row - i][col - i])
			{
				outputArr.add("(" + (col - i) + "," + (row - i) + ")");
			}
			else
			{
				return false;
			}
		}
		return (outputArr.size() == searchWordArr.length);
	}

	private boolean matchRemainingConsecutiveLettersSouthWestToNorthEast(char[] searchWordArr, int row, int col, List<String> outputArr)
	{
		if (isInvalidBounds(row, col))
			return false;
		
		outputArr.clear();
		
		for (int i = 0; i < searchWordArr.length && (row - i) >= 0 && (col + i) < alphaGrid.length; i++)
		{
			if (searchWordArr[i] == alphaGrid[row - i][col + i])
			{
				outputArr.add("(" + (col + i) + "," + (row - i) + ")");
			}
			else
			{
				return false;
			}
		}
		return (outputArr.size() == searchWordArr.length);
	}

	private boolean matchRemainingConsecutiveLettersNorthEastToSouthWest(char[] searchWordArr, int row, int col, List<String> outputArr)
	{
		if (isInvalidBounds(row, col))
			return false;
		
		outputArr.clear();
		
		for (int i = 0; i < searchWordArr.length && (row + i) < alphaGrid.length && (col - i) >= 0; i++)
		{
			if (searchWordArr[i] == alphaGrid[row + i][col - i])
			{
				outputArr.add("(" + (col - i) + "," + (row + i) + ")");
			}
			else
			{
				return false;
			}
		}
		return (outputArr.size() == searchWordArr.length);
	}

	private String formatCollectedLocations(List<String> outputArr)
	{
		String outputStr = null;
			
		if (outputArr.size() > 0)
			outputStr = outputArr.get(0);
		
		for (int i = 1; i < outputArr.size(); i++)
			outputStr += "," + outputArr.get(i);
	
		System.out.println("outputStr: " + outputStr);
		return outputStr;
	}
}
