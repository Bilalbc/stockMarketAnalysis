package stockMarketAnalysis;

public class signals {
	
	String filePath;//file path 
	readFile r; //declare readFile class
	formulas f;//declare  formulas class
	int length;
	double[][] values;


	//-----------------------------------------------------------------------------------------------------------//
	/*
	 * constructor method used to initialize values
	 * 
	 * parameter - values array from formulas class
	 */
	public signals(double [][] v) {
		length = v.length;//length of the array 
		values = new double[length][12];//initializing values array 
		values = v;//copying elements from the values array to this class array 
	}
	
	//-----------------------------------------------------------------------------------------------------------//
	/*
	 * method used to determine the signal of MACD chart 
	 */
	public int MACDSignal() {
		/*
		 * checks the value of the signal line, and if it is greater than the value of the MACD line,
		 * indicating a crossover, which means it is a good time to buy 
		 */
		if(values[length-1][9]>values[length-1][10]) 
			return 1;  //buy signal
		
		else return 0;//sell signal
		
	}
	
	//-----------------------------------------------------------------------------------------------------------//
	/*
	 * method used to determine the signal being provided my the FS indicator 
	 */
	public int FSSignal() {
		/*
		 *  if the current FS value is greater than the current %D value, this indicates
		 *  that there has been a crossover, meaning it is a god time to buy 
		 */
		if(values[length-1][11]>values[length-1][12])
			return 1;//buy signa;
		
		else return 0;	//sell signal
	}
	
	//-----------------------------------------------------------------------------------------------------------//
	/*
	 * method that interprets signals from both the MACD and FS indicators, 
	 * and generates an overall signal based upon it
	 */
	public String makeSignal() {
		if(MACDSignal() * FSSignal() == 1) //if both signals are returning a value of 1(buy) send an overall buy signal
			return "1";
		
		else return "0";//if the two conflict, or both are saying sell, then send a sell signal 
	}
	
}