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
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class sendingQuotes {
	
	private String filepathABX1 = "C:\\Users\\momo_\\Documents\\Com Sci\\Data\\Daily - 6 months.txt";
	private String filepathABX2 = "C:\\Users\\momo_\\Documents\\Com Sci\\Data\\Weekly - 2 years.txt";
	private String filepathABX3 = "C:\\Users\\momo_\\Documents\\Com Sci\\Data\\Monthly - 5 years.txt";
	
	public void sendQuotes (String symbol) throws IOException {
	yahooFinance readToSend = new yahooFinance();
	String symb = symbol;
    ArrayList<String> arrayABX1 = new ArrayList<String>(readToSend.readToHistory(symb, 1 , "Daily"));	
	ArrayList<String> arrayABX2 = new ArrayList<String>(readToSend.readToHistory(symb, 2, "Weekly"));
	//ArrayList<String> arrayABX3 = new ArrayList<String>(readToSend.readToHistory(symb, 5, "Monthly"));
	   
	  saveToFile(filepathABX1, arrayABX1);
	  saveToFile(filepathABX2, arrayABX2);
	  //saveToFile(filepathABX3, arrayABX3);
      
	  System.out.println("The files were successfully updated");
	}
	
	public static void saveToFile(String fileName, ArrayList list) {
		Path filePath = Paths.get(fileName);

		try {
			Files.write(filePath, list, Charset.defaultCharset());
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
	
	

