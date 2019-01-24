//Class Name: yahooFinance
//Description: The main idea of this class is to make all the methods for getting the quotes from the yahooFinance API by using the already provided methods by the API. Relies on the pom.xml file.
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

public class yahooFinance {

	
	//getSymbol method is used to get the symbol from Yahoo Finance. Return type: String.
	 public String getSymbol (String stockName) throws IOException {
		 //Creates a new Stock and gets the stockName.
		 Stock stock = YahooFinance.get(stockName);
		 //Gets the stockName symbol. 
		 String symbol = stock.getSymbol();
		 //returns the symbol.
		 return symbol; 
	 }
	
	//getDate method is used to get the current date.
	 public String getDate (String stockName) throws IOException {	
		 Stock stock = YahooFinance.get(stockName);
		 String date = null;
		 //Uses HistoricalQuote List and the .getHistory() method.
		 List<HistoricalQuote> history = stock.getHistory();
			for (HistoricalQuote quote : history) {
				//gets the the date of the historical quote and converts it to a readable format. 
				date = convertDate(quote.getDate());
			}
			return date;

		}
	 
	//getClosePrice method is used to get the current closing price of the stock specified (works similarly to the method above) Return type: BigDecimal.
	 public BigDecimal getClosePrice (String stockName) throws IOException {	
		 Stock stock = YahooFinance.get(stockName);
		 BigDecimal close = null;
		 List<HistoricalQuote> history = stock.getHistory();
			for (HistoricalQuote quote : history) {
				close = quote.getClose();
			}
			//returns a rounded close price value.
			return close.setScale(2, RoundingMode.CEILING);

		}
	 
	 //getOpeningPrice method is used to get the current opening price of the stock specified (works similarly to the method above) Return type: BigDecimal.
	 public BigDecimal getOpeningPrice (String stockName) throws IOException {	
		 Stock stock = YahooFinance.get(stockName);
		 BigDecimal open = null;
		 List<HistoricalQuote> history = stock.getHistory();
			for (HistoricalQuote quote : history) {
				open = quote.getOpen();
			}
			return open.setScale(2, RoundingMode.CEILING);

		}
	 
	//getHighestPrice method is used to get the current highest price of the stock specified (works similarly to the method above) Return type: BigDecimal.
	 public BigDecimal getHighestPrice (String stockName) throws IOException {	
		 Stock stock = YahooFinance.get(stockName);
		 BigDecimal high = null;
		 List<HistoricalQuote> history = stock.getHistory();
			for (HistoricalQuote quote : history) {
				high = quote.getHigh();
			}
			return high.setScale(2, RoundingMode.CEILING);

		}
	 
	 //getLowestPrice method is used to get the current lowest price of the stock specified (works similarly to the method above) Return type: BigDecimal.
	 public BigDecimal getLowestPrice (String stockName) throws IOException {	
		 Stock stock = YahooFinance.get(stockName);
		 BigDecimal low = null;
		 List<HistoricalQuote> history = stock.getHistory();
			for (HistoricalQuote quote : history) {
				low = quote.getLow();
			}
			return low.setScale(2, RoundingMode.CEILING);

		}
	 
	//getVolume method is used to get the current volume of the stock specified (works similarly to the method above) Return type: Long
	 public Long getVolume (String stockName) throws IOException {	
		 Stock stock = YahooFinance.get(stockName);
		 Long volume = null;
		 List<HistoricalQuote> history = stock.getHistory();
			for (HistoricalQuote quote : history) {
				volume = quote.getVolume();
			}
			return volume;

		}
	 
	 //A method which converts the date to something better displayed.
	 private String convertDate(Calendar cal) {
		 	//Uses the SimpleDateFormat import mainly to display in the yyyy-MM-dd method.
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String formatDate = format.format(cal.getTime());
			//returns a formated date.
			return formatDate;
		}
	 
	//Method: getHistory
	//Mainly a test method used to test the calender and the historical quote methods and how the interval part works.
	 
	/* public void getHistory(String stockName, int year, String searchType) throws IOException {
			Calendar from = Calendar.getInstance();
			Calendar to = Calendar.getInstance();
			from.add(Calendar.YEAR, Integer.valueOf("-" + year));

			Stock stock = YahooFinance.get(stockName);
			List<HistoricalQuote> history = stock.getHistory(from, to, getInterval(searchType));
			for (HistoricalQuote quote : history) {
				System.out.println("====================================");
				System.out.println("symbol : " + quote.getSymbol());
				System.out.println("date : " + convertDate(quote.getDate()));
				System.out.println("High Price  : " + quote.getHigh());
				System.out.println("Low Price : " + quote.getLow());
				System.out.println("Closed price : " + quote.getClose());
				System.out.println("Volume: " + quote.getVolume());
				System.out.println("=========================================");
			}
			

		}*/
	 
	 //readToHistory method is important as it gathers all the methods above and stores them in an ArrayList. 
	 public ArrayList readToHistory(String stockName, int year, String searchType) throws IOException {
		 	ArrayList<String> array = new ArrayList<String>();	
		 	//To and From Calender.
		 	Calendar from = Calendar.getInstance();
			Calendar to = Calendar.getInstance();
			//Calls the Calender.YEAR method to specify the interval of time passed in. 
			from.add(Calendar.YEAR, Integer.valueOf("-" + year));
			Stock stock = YahooFinance.get(stockName);
			//Goes through the List in a for loop and the HistoricalQuotes with a specified from and to interval now.
			List<HistoricalQuote> history = stock.getHistory(from, to, getInterval(searchType));
			for (HistoricalQuote quote : history) {
				//Stores all the quotes for one date as one element.
				array.add(convertDate(quote.getDate()).toString() + "," + quote.getOpen().toString() + "," + quote.getHigh().toString()+ "," + quote.getLow().toString()+ "," + quote.getClose().toString()+ "," + quote.getAdjClose().toString()+ "," + quote.getVolume().toString());
			}
			//returns the array list.
			return array;
			

		}
	 
	 //getInterval:
	 //Method which helps specify the intervals available for the APIs and sets the keyWord used to call the intervals using a basic switch statement.
	 private Interval getInterval(String searchType) {
			Interval interval = null;
			switch (searchType.toUpperCase()) {
			case "MONTHLY":
				interval = Interval.MONTHLY;
				break;
			case "WEEKLY":
				interval = Interval.WEEKLY;
				break;
			case "DAILY":
				interval = Interval.DAILY;
				break;

			}
			return interval;
		}
	 
		/*public Map<String, Stock> getStock(String[] stockNames) throws IOException {
			Map<String, Stock> stock = YahooFinance.get(stockNames);
			return stock;
		}*/

	
}
	
	

