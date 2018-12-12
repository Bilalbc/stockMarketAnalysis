package stockAnalysisProgram;

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
	
	public static String symb;
	
	//private String filepathABX1 = "C:\\Users\\momo_\\Documents\\Com Sci\\Data\\Daily - 6 months.txt";
	//private String filepathABX2 = "C:\\Users\\momo_\\Documents\\Com Sci\\Data\\Weekly - 2 years.txt";
	//private String filepathABX3 = "C:\\Users\\momo_\\Documents\\Com Sci\\Data\\Monthly - 5 years.txt";
	private String filePath = "C:\\Users\\momo_\\Documents\\Com Sci\\Data";
	
	public void sendQuotes (String symbol) throws IOException {
	yahooFinance readToSend = new yahooFinance();
	symb = symbol;
    ArrayList<String> array = new ArrayList<String>(readToSend.readToHistory(symb, 1 , "Daily"));	
	ArrayList<String> array2 = new ArrayList<String>(readToSend.readToHistory(symb, 2, "Weekly"));
	ArrayList<String> array3 = new ArrayList<String>(readToSend.readToHistory(symb, 20, "Monthly"));
	  
	  saveToFileDaily(filePath, array, symb);
	  saveToFileWeekly(filePath, array2, symb);
	  saveToFileMonthly(filePath, array3, symb);
	  
	  System.out.println("The files were successfully updated");
	  
	}
	
	
	public static String returnSymbol () {
		return symb;
	}
	
	public static void saveToFileDaily(String fileName, ArrayList list, String symb) throws FileNotFoundException {
		Formatter format;
		Path filePath = Paths.get(fileName+"\\"+symb+" Daily.txt");
		try {
			Files.write(filePath, list, Charset.defaultCharset());
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void saveToFileWeekly(String fileName, ArrayList list, String symb) throws FileNotFoundException {
		Formatter format;
		Path filePath = Paths.get(fileName+"\\"+symb+" Weekly.txt");
		try {
			Files.write(filePath, list, Charset.defaultCharset());
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
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
	
	

