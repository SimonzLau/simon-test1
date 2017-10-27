import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SudokuChecker 
{

	static final String FILENAME = "input_sudoku.txt";
	static int N = 9;

	// parser input test file.
	static int[][] parserInFile() 
	{

		BufferedReader br = null;
		FileReader fr = null;
		int gridT[][] = new int[N][N];

		try {
			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);
			String sCurrentLine;
			int j = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				if (sCurrentLine.trim().length() > 0) {
					for (int i=0; i<sCurrentLine.length(); i++){
						char c=sCurrentLine.charAt(i);
						gridT[i][j] = Integer.parseInt(sCurrentLine.valueOf(c));
					}
					j++;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return gridT;
	}

	public SudokuChecker( int[][] input )
	{
		puzzle = input;

	}

	/**
	 *check to be sure there is an entry 1-9 in each position in the matrix
	 *exit with false as soon as you find one that is not
	 *@return true if the board has correct entries, false otherwise
	 */
	public boolean completed()
	{
		for(int i = 0; i < 9;i++)
		{
			for(int j = 0;j < 9;j++)
			{
				if(puzzle[i][j] > 9 || puzzle[i][j] < 1)
				{
					return false;
				}
			}
		}
		return true;
	}

	public boolean checkPuzzle()
	{
		// use checkRow to check each row
		for(int i = 0; i < 9;i++)
		{
			if(!checkRow(i))
			{
				return false;
			}
		}

		// use checkColumn to check each column

		for(int i = 0; i < 9;i++)
		{
			if(!checkColumn(i))
			{
				return false;
			}
		}

		// use checkSquare to check each of the 9 blocks
		for(int i = 0; i < 9;i += 3)
		{
			for(int j = 0; j < 9;j += 3)
			{
				if(!checkSquare(i, j))
				{
					return false;
				}
			}
		}
		return true;
	}

	/**
	 *Ensures that row r is legal
	 *@param r the row to check
	 *@return  true if legal, false otherwise.
	 */
	public boolean checkRow( int r )
	{
		resetCheck();
		for( int c=0; c<9; c++ )
		{
			if( !digitCheck( puzzle[r][c] ) )
			{
				return false;
			}
		}
		return true;
	}

	/**
	 *Ensures that column c is legal
	 *@param c the column to check
	 *@return  true if legal, false otherwise.
	 */
	public boolean checkColumn( int c )
	{
		resetCheck();
		for( int r=0; r<9; r++ )
		{
			if( !digitCheck( puzzle[r][c] ) )
			{
				return false;
			}
		}
		return true;
	}
	/**
	 *Ensures that a given square is legal
	 *@param row    the initial row of the square
	 *@param column the intial column of the square
	 *@return       true if legal, false otherwise.
	 */
	public boolean checkSquare( int row, int column )
	{
		resetCheck();
		for(int r = 0; r < 3;r++)
		{
			for(int c = 0; c < 3;c++)
			{
				if( !digitCheck( puzzle[r + row][c + column] ) )
					return false;
			}
		}
		return true;
	}

	/**
	 *Ensures that sum of row is equal to 45
	 *@param matrix the matrix
	 *@return true if the sum of row are 45 , false otherwise.
	 */
	public boolean checkSum( int[][] mat )
	{
		int rowTotal = 0;
		for(int i=0; i<mat.length; i++) {
			rowTotal = 0;
			for(int j=0; j<mat[i].length; j++) {
				if (!checkSquare( i, j ))
					return false;
				rowTotal += mat[i][j];
			}
			if (rowTotal != 45){
				return false;
			}
		}
		return true;
	}


	/**
	 *Keeps track of numbers used during a row/column/square check
	 *@param n the number currently being checked
	 *@return  true if the number has not been used yet, false otherwise
	 */
	public boolean digitCheck( int n )
	{
		if( n != 0 && digits[n] )
		{
			return false;
		}
		else
		{
			digits[n] = true;
			return true;
		} 
	}

	/**
	 *Resets digits to false
	 */
	public void resetCheck()
	{
		digits = new boolean[10];
	}

	// ***** Instance Variables *****
	int[][] puzzle;
	boolean[] digits;

	// ***** Built-in Self-Test *****
	public static void main(String[] args)
	{

		SudokuChecker sudoku = new SudokuChecker(  parserInFile() ); 
		if (sudoku.completed()){
			System.out.println("Is an invalid Sudoku: " + sudoku.checkSum(  parserInFile() ));
		} else {
			System.out.println("Sudoku is uncompleted");
		}
	}
}
