package stockMarketAnalysis;
import java.util.*;   
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import java.util.*;   
import java.text.SimpleDateFormat;

/*
 *
 */
public class formulas {
	
	double SMA;//Standard moving Average
	double SD;//Standard deviation 
	
	public String path ="";//file path 
	readFile r;
	int lines;
	//Date	
	//Open	High	Low	Close*	Adj Close**	Volume
	Date [] dateArray; // array to store dates 
	double [][] values;// array to store price values
	double [][] temp; // temporary array to store intermediate values
	

//-----------------------------------------------------------------------------------------------------------//
	/*
	 * constructor method used in order to create the class 
	 * and initialize arrays 
	 * 
	 * String parameter is file path used to get information
	 */
	public formulas (String string) {
		
		r = new readFile(string);//initializes class
		//create arrays and designate sizes
		values = new double [r.findRows()][13];
		temp = new double [r.findRows()][3];
		dateArray = new Date[r.findRows()]; 
		
		this.path = string; // sets file path
		
		initialize(string);//initialize arrays 
	}
	

//-----------------------------------------------------------------------------------------------------------//
	/*
	 * method used to directly initialize all values in arrays 
	 * allows quick initialization, without calling each method directly
	 * 
	 * returns values array with all relevant information
	 */
	public double[][] initialize(String string) {
		readFile r = new readFile(string);
		// add all values to array 
		r.makeArray(); 
		createValues(r.getArray());
		createDateArray(r.getArray());
		createBollinger();
		createMACD();
		createSignalLine();
		createK();
		createD();
		createFS();
		
		return values;//return array 
		
	}

//-----------------------------------------------------------------------------------------------------------//
	/*
	 * method used to separate the dates that correspond with values, 
	 * as the values are converted into doubles, and the date cannot be 
	 * 
	 * parameter: string array containing all information from the text file 
	 * 			  of a stock - from readFile class 
	 */
	public void createDateArray(String [][] n) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//initializing format of date 
		//run through all the rows in the array 
		for(int i=0;i<n.length;i++)
			//runs through each element in the row
			for(int j=0; j<n[i].length;j++) {
				//if this is the first element in the row
				//first element in each row is always the string representing the date
				if(j==0) {
					try {
						Date newDate = (Date)df.parse(n[i][0]);//create date object using the string representing the date 
						dateArray[i] = newDate;//add the new object into the array 
					}
					catch(Exception e) {//catch and print out errors
						System.out.println(e);
					}
				}
			}
	}
	
//-----------------------------------------------------------------------------------------------------------//
	/*
	 * method used in order to get all prices and values from the passed array 
	 * parses each string from array into a float for use as data points
	 * for subPlot class
	 * 
	 * parameter: string array containing all information from the text file 
	 * 			  of a stock - from readFile class 
	 */
	public void createValues(String[][] n) {
		//runs through each row in the array 
		for (int i=0; i<n.length;i++) {
			//cycles trough every element in the row
			for(int j=0;j<n[i].length;j++) {
				/*if the element being looked at is not the first element
				 *first element is reserved for dates, whereas all else 
				 *are to be used as data points
				 */
				if(j>0&&j<6){
					values[i][j] = Float.parseFloat(n[i][j]);//takes string and parses it as a floating point value
				}else {
					values[i][j] = 0;//if this was the first value, set as 0
				}
			}
		}
	}
	
//-----------------------------------------------------------------------------------------------------------//
	/*
	 * method used for creating the simple moving average of the adjusted close value 
	 * in the values array
	 * 
	 * parameters: period - how long of a time period the SMA will be calculated using
	 * 						usually is a 20 day period
	 * 
	 * 			   s - the first value that will be looked at to calculate the SMA.
	 * 				   incremental value that gives reference for where to begin reading values
	 * 
	 * return : returns a double value which is the SMA of the specific period passed on
	 */
	public double createSMA(int period, int s) {
		double sum = 0;
		int divisor = 0; 
		int start = s; //starting point to read from
		SMA =0;//simple moving average
		
		//runs for the length of the period passed on  
			for(int i=1;i<=period;i++) {
				//fail safe if the start is greater than the length of the values array 
				if(start>values.length) {
					return 1;
				}
				sum+=values[start-i][4];//getting close values, reading backwards, and add them together
				divisor++;	//increment the divisor
			}
			SMA = sum/divisor;//final calculation of the SMA

			return SMA;//returns SMA
	}

//-----------------------------------------------------------------------------------------------------------//
	
	/*
	 * method for creating bollinger band 
	 * 
	 * involves the calculations of SMA and SD to determine the value of the Bollinger
	 * applies eperate formulas and procedures in order to calculate each element
	 */
	public void createBollinger() {
		int start = 20;//base start point
		double upper;//upper bollinger line
		double lower;//lower bollinger line
		SD = 0; //standard deviation

		//getting SMA 
		for(int j=20;j<values.length;j++) {
			values[j][7]=createSMA(20,j);
		}
		
		//loop to calculate the bollinger value for every point
		for(int j=20;j<values.length;j++) {
			//calculating standard deviation
			for(int i=0;i<20;i++) 
				SD +=Math.pow(values[start-i][4]-values[start][7], 2);
			
			SD = Math.sqrt(SD/20);//final calculation for SD value
				
			upper = values[j][7] + (2*SD);//assigning upper bollinger value
			lower = values[j][7] - (2*SD);//assigning lower bollinger value
			
			//adding to values array 
			values[j][8] = lower;
			values[j][6] = upper;

			start++;//increment start element
			SD = 0;//reset standard deviation
			
		}	
	}

//-----------------------------------------------------------------------------------------------------------//
	/*
	 * method used to determine MACD values
	 * 
	 * uses calculations of SMAs with a 12 and 20 day period
	 */
	public void createMACD() {
		//formula for MACD - 12 day SMA - 26 day SMA
		
		//calculating SMA of the 12 day period
		for(int i=12;i<values.length;i++) {
			temp[i][0] = (float) createSMA(12, i);
		}
		
		//calclating SMA of 20 day period
		for(int i=26;i<values.length;i++) {
			temp[i][1] = (float) createSMA(26, i);
		}
		
		//calculating MACD
		for(int i=26;i<values.length;i++) {
			values[i][9] = temp[i][0]-temp[i][1];
		}
	}

//-----------------------------------------------------------------------------------------------------------//
	/*
	 * method used for generation of the signal line values
	 * 
	 * involves the calculation of a 9 day SMA of the MACD line 
	 */
	public void createSignalLine() {
		//formula - 9 day SMA of MACD Line
		double sum = 0;
		int divisor = 0;
		int start = 9;
		SMA =0;//simple moving average
		
		//loop to run through the length of the array from the starting element
		for(int j=9;j<values.length;j++) {
			//cycles through 9 elements, and is used to go backwards through the array 
			for(int i=1;i<10;i++) {
				//fail safe in case starting value is large than the array 
				if(start>values.length) {
					return;
				}
				sum+=values[start-i][9];//getting MACD values
				divisor++;	//increment the divisor
			}
			SMA = sum/divisor;//calculating SMA
			values[j][10] = SMA;//adding SMA to array 
			divisor =0;
			
			sum =0;//reset sum
			start +=1;//increment start 
		}
	}

//-----------------------------------------------------------------------------------------------------------//
	/*
	 * creates value for use in the calculation of the full stochastic indicator 
	 * 
	 *involves the use of the highet and lowest value in a given period, and is based
	 *off of a 14 element period
	 */
	public void createK() {
		int start = 14;//starting element
		double highestHigh = values[0][4];//initialization of highest value
		double lowestLow = values[0][4];//initialization of lowest value
		
		//loop to run from the starting element through the rest of array 
		for(int i=14;i<values.length;i++) {
			//array that runs through 14 elements in order for the array to be run through backwards
			for(int j=0;j<14;j++) {

				if(start>values.length) //fail safe if start value is larger than array size
					return;
				
					//if the current value is bigger than the current highest value set 
					if(values[start-j][4]>highestHigh)
						//assign new highest value
						highestHigh = values[start-j][4];
					
					//if the current value is smaller than the current lowest value
					else if(values[start-j][4]<lowestLow)
						//assign new lowest value
						lowestLow = values[start-j][4];
				}
			temp[i][2] = ((values[i][4]-lowestLow)/(highestHigh-lowestLow))*100;//calculation of K value
			highestHigh = values[i][4];//reset highest high as first in new set
			lowestLow = values[i][4];//reset lowest value as first in new set 
			
			start++;//increment the starting value
		}
	}

//-----------------------------------------------------------------------------------------------------------//	
	/*
	 * determining the %D value, which is based upon the 3 day SMA
	 * of the %K value. Used for plotting
	 * 
	 * involves the use of the temp array to get %K values
	 */
	public void createD() {
		double sum = 0;
		int divisor = 0;
		int start = 3;
		SMA =0;//simple moving average
		
		//from the starting value for the length of the array 
		for(int j=3;j<values.length;j++) {
			//runs through 3 elements, used in order to run through the array backwards
			for(int i=0;i<3;i++) {
				if(start>values.length) {//fail safe if start is too large
					return;
				}
				sum+=temp[start-i][2];//getting %k Values
				divisor++;	//increment divisor
			}
			SMA = sum/divisor;//calculate SMA
			values[j][12] = SMA;//assign %D value
			
			//reseting values 
			divisor =0;
			
			sum =0;
			start +=1;
		}
		
	}

//-----------------------------------------------------------------------------------------------------------//	
	/*
	 * calculating of fast stochastic value for use in plotting
	 * involves the calculation of 5 day SMA of %k value
	 */
	public void createFS() {
		//5 day SMA of %K Line
		double sum = 0;
		int divisor = 0;
		int start = 5;
		SMA =0;//simple moving average
				
		//runs through array from the 5th element
		for(int j=5;j<values.length;j++) {
			//runs through 5 elements, used in order to run backwards through the array 
			for(int i=0;i<5;i++) {
				if(start>values.length) {//fail safe
					return;
				}
				sum+=temp[start-i][2];//getting %K values
				divisor++;	
			}
			SMA = sum/divisor;//calculation of SMA
			values[j][11] = SMA;//setting value of FS
			
			//reseting values
			divisor =0;
					
			sum =0;
			start +=1;
		}
		createD();
	}

//-----------------------------------------------------------------------------------------------------------//
	/*
	 * method used in order to determine the size of the file being read in order to 
	 * Initialize the size of the individual arrays 
	 * 
	 *return - an integer representing the size of the file
	 */
	public int fileSize() {
		lines = 0;//incremental value
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));//Initializing reader to go through file 
			
			while (reader.readLine() != null) lines++;//increment value while line being read has content
			//close the reader 
			reader.close();
			
			
		} catch (FileNotFoundException e) {} catch (IOException e) {}//excepton catch 

		return lines;//return lines
	}

//-----------------------------------------------------------------------------------------------------------//
	/*
	 * method used to determine the highest close value in the array 
	 * used in the subPlot class for determining scaling of graph
	 */
	public double getHighest() {
		double highest = values[0][4];//initializes the highest value as first element
		
		for(int i =0;i<values.length;i++) {//runs through array 
			if(highest<values[i][4])//if number is greater than current
				highest = values[i][4];//set number as new largest 
		}
		
		return highest;//return largest number
	}

//-----------------------------------------------------------------------------------------------------------//
	/*
	 * method used in order to get the lowest close value in the array 
	 * used in subPlot class in order to determine scaling of the graph
	 */
	public double getLowest() {
		double lowest = values[0][4];//sets first element to lowest 
		
		//runs through entire array 
		for(int i=0;i<values.length;i++) {
			//if the value is lower than current lowest 
			if(lowest>values[i][4])
				//set new lowest value 
				lowest = values[i][4];
		}
		return lowest;//return new lowest value
	}

//-----------------------------------------------------------------------------------------------------------//
	/*
	 * method to return values array 
	 */
	public double[][] getValues(){
		return values;//return array 
	}

//-----------------------------------------------------------------------------------------------------------//	
	/*
	 * method used to get the date array 
	 */
	public Date[] getDates(){
		return dateArray;//return date array 
	}

}