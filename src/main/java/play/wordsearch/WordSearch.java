package play.wordsearch;

import java.nio.file.*;

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
	}
}
