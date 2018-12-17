package stockMarketAnalysis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class createFiles {
	
	File file;
	
	public createFiles() {
		 file = new File("src//Email Files//test.txt");
	}
	
	public void createTxtFile(ArrayList<String> followingStocks) {
		
		String signal;
		String symb;
		launchPage l = new launchPage();
		  
		//Create the file

		 
		//Write Content
		FileWriter writer;
		try {
			System.out.println("test");
			writer = new FileWriter(file);
			
			writer.write("##############################################################\n\t\t\t\t\t\ttest\n##############################################################");
			
			writer.write("\nSTOCK\t\tOPEN\t\tLOW\t\tHIGH\t\tCLOSE\t\tAVERAGE\t\tSIGNAL");
			
			for(int i = 0;i<followingStocks.size();i++) {
				symb = followingStocks.get(i);
				System.out.println(followingStocks.size());
				System.out.println(symb);
				
			signals s = new signals("src//Data//"+symb+" daily - 1 year.txt");
			formulas f = new formulas("src//Data//"+symb+" daily - 1 year.txt");
			
			if(s.makeSignal("src//Data//"+symb+" daily - 1 year.txt") *
				s.makeSignal("src//Data//"+symb+" Weekly - 2 years.txt")* 
				s.makeSignal("src//Data//"+symb+" Monthly - 5 years.txt") == 1) 
					
					signal = "BUY";			
			else
					signal = "SELL";
			
			writer.write("\n\n"+symb+"\t\t\t"+(float)f.values[12][1]+ "\t\t"+(float)f.values[12][3]+"\t"+(float)f.values[12][2]+"\t\t"+(float)f.values[12][4]+"\t\t"+(float)f.values[12][5]+"\t\t"+signal);	
			
			
			}
			
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void createImageFile() {
		
	}
	
	public static void main(String[]args) {
		createFiles c = new createFiles();
		//c.createFile("src//Data//Daily - 6 months.txt");
	}

}