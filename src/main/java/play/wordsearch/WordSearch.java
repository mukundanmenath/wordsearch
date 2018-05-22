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
    public static void main( String[] args )
    {
        System.out.println( "Hello from WordSearch!" );
    }
	
	public WordSearch()
	{
	}
	
	public WordSearch(String inputFileName) throws Exception
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
			
		}
		
	}
}
