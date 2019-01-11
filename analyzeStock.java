package stockMarketAnalysis;


import java.io.IOException;
import java.util.Scanner;

import org.jfree.ui.RefineryUtilities;

import javafx.application.Application;
public class analyzeStock {

	public void historyStock () throws IOException {
		
		yahooFinance history = new yahooFinance();
 		history.getHistory("GLD", 2, "Monthly");
 		String[]stockNames = new String[]{"GLD","ABX","SLV"};
 		System.out.println(history.getStock(stockNames));
 		
 		 for (int i = 0; i < stockNames.length; i++) {
 	
 		String symbol = history.getSymbol(stockNames[i]).toString();
 		String date = history.getDate(stockNames[i]).toString();
		String close = history.getClosePrice(stockNames[i]).toString();
		String open = history.getOpeningPrice(stockNames[i]).toString();
		String high = history.getHighestPrice(stockNames[i]).toString();
		String low = history.getLowestPrice(stockNames[i]).toString();
		String vol = history.getVolume(stockNames[i]).toString();
		System.out.println(symbol + "\nDate: " + date + "\nclose: " + close + "\nOpen: " + open + "\nhigh: " + high + "\nlow: " + low + "\nvol: " + vol );
 		 
	}
 	
 		 }
 	
 	
 		

 	}