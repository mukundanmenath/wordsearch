package play.wordsearch;

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
		if (inputFileName == null)
			throw new Exception();
	}
}
