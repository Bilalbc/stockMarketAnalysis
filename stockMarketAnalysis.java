package stockAnalysisProgram;

import java.io.IOException;
import java.util.Scanner;

import org.jfree.ui.RefineryUtilities;

import javafx.application.Application;
public class stockMarketAnalysis {
 	public static void main(String[]args) throws IOException {
 	
 		sendingQuotes send = new sendingQuotes();
 		send.sendQuotes();
 		Application.launch(launchPage.class, args);
 		analyzeStock history = new analyzeStock();
 		history.historyStock();
		
 		 }
 		

 	}
