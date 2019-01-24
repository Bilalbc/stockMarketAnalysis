package stockMarketAnalysis;
import java.io.*; 
import java.util.ArrayList;
import java.util.StringTokenizer;

public class readFile {
	
	long fileSize;
	int rows;
	static File fileLoc;
	String items [][];
	
	//-----------------------------------------------------------------------------------------------------------//
	/*
	 * constructor method used to initialize class 
	 * 
	 * parameters: - String: used to initialize new file
	 */
	public readFile(String string) {
		
		fileLoc = new File (string);
	}

	//-----------------------------------------------------------------------------------------------------------//
	/*
	 * method used to determine number of rows in the file in order
	 * to initialize size of arrays in formulas class
	 * 
	 * returns integer value representing size of file
	 */
	public int findRows() {
		rows=0;//initiaize variable representing file size
		try {
			BufferedReader file = new BufferedReader(new FileReader(fileLoc));//initializes reader to read the file
			
			try {
				//loop while line contains information
				while(file.readLine() != null){
					rows++;//increment rows variable
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return rows;//return number of rows
	}
	
	//-----------------------------------------------------------------------------------------------------------//
	/*
	 * method used in order to create substrings of each individual element in the file,
	 * and create an array of strings containing all the information for use in the
	 * formulas class 
	 */
	public void makeArray() {
		int r = 0;//incrementing variable
		items = new String[findRows()][7];//creates array based on size of file and amount of elements in each row
		
		try {
			BufferedReader file = new BufferedReader(new FileReader(fileLoc));//initialize file reader 
			
			try {
				String line = null;//initialize line 

				//loop while the line has information
				while((line=file.readLine())!=null) {
					//initializing tokenizer, and declaring the char at which the string will be divided at,
					//creating substrings
					//tokenizes line being read and divides it into substrings at commas
					StringTokenizer z = new StringTokenizer(line,",");
					//while there are more possible tokens to make into substrings 
					while(z.hasMoreTokens()) {
						for(int c = 0; c < 7;c++) {//loop for the amount of elements in each line
							items[r][c] = z.nextToken();//assigns the string to a place in the array 
						}
						r++;//new row in the file, as well as in the 2d array 
					}
				}
				file.close();//close file
			}
			catch (IOException e){
				System.out.println("Sorry there was an error reading");
			}
			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------//
	/*
	 * method to return items array 
	 */
	public String[][] getArray() {
		return items;//return array 
	}
	
}