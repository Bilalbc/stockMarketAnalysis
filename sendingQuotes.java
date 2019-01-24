package stockMarketAnalysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class sendingQuotes {

		//Creates a global variable to store the symbol.
		public static String symb;
		
		//Specifies the filePath.
		private String filePath = "C:\\Users\\momo_\\Documents\\Com Sci\\Data";
		
		//This methods calls the other methods in this class.
		public void sendQuotes (String symbol) throws IOException {
		yahooFinance readToSend = new yahooFinance();
		symb = symbol;
		//Creates ArrayLists by taking the quotes from the yahooFinance class (symbol, time interval, frequency)
	    ArrayList<String> array = new ArrayList<String>(readToSend.readToHistory(symb, 1 , "Daily"));	
		ArrayList<String> array2 = new ArrayList<String>(readToSend.readToHistory(symb, 2, "Weekly"));
		ArrayList<String> array3 = new ArrayList<String>(readToSend.readToHistory(symb, 20, "Monthly"));
		  
		array3.remove(0);
		
		  //Class the three methods
		  saveToFileDaily(filePath, array, symb);
		  saveToFileWeekly(filePath, array2, symb);
		  saveToFileMonthly(filePath, array3, symb);
		  
		  System.out.println("The files were successfully updated");
		  
		}
		
		//Mainly used to pass through the symbol to the subPlot class.
		public static String returnSymbol () {
			return symb;
		}
		
		//Method saveToFileDaily
		public static void saveToFileDaily(String fileName, ArrayList list, String symb) throws FileNotFoundException {
			//Formatter
			Formatter format;
			//specifies the file path. 
			Path filePath = Paths.get(fileName+"\\"+symb+" Daily.txt");
			//updates the with file specified with the list.
			try {
				Files.write(filePath, list, Charset.defaultCharset());
			}catch(IOException e) {
				e.printStackTrace();
			}
			
		}
		
		//Method saveToFileWeekly (functions similarly to the method above)
		public static void saveToFileWeekly(String fileName, ArrayList list, String symb) throws FileNotFoundException {
			Formatter format;
			Path filePath = Paths.get(fileName+"\\"+symb+" Weekly.txt");
			try {
				Files.write(filePath, list, Charset.defaultCharset());
			}catch(IOException e) {
				e.printStackTrace();
			}
			
		}
		
		//Method saveToFileMonthly (functions similarly to the method above)
		public static void saveToFileMonthly(String fileName, ArrayList list, String symb) throws FileNotFoundException {
			Formatter format;
			Path filePath = Paths.get(fileName+"\\"+symb+" Monthly.txt");
			try {
				Files.write(filePath, list, Charset.defaultCharset());
			}catch(IOException e) {
				e.printStackTrace();
			}
			
		}
		

	}
