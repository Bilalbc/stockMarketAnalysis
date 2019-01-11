package stockMarketAnalysis;

import java.io.IOException;
import java.math.BigDecimal;
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

	
	 public String getSymbol (String stockName) throws IOException {
		 Stock stock = YahooFinance.get(stockName);
		 String symbol = stock.getSymbol();
		 return symbol; 
	 }
	
	
	 public String getDate (String stockName) throws IOException {	
		 Stock stock = YahooFinance.get(stockName);
		 String date = null;
		 List<HistoricalQuote> history = stock.getHistory();
			for (HistoricalQuote quote : history) {
				date = convertDate(quote.getDate());
			}
			return date;

		}
	 
	 public BigDecimal getClosePrice (String stockName) throws IOException {	
		 Stock stock = YahooFinance.get(stockName);
		 BigDecimal close = null;
		 List<HistoricalQuote> history = stock.getHistory();
			for (HistoricalQuote quote : history) {
				close = quote.getClose();
			}
			return close;

		}
	 
	 public BigDecimal getOpeningPrice (String stockName) throws IOException {	
		 Stock stock = YahooFinance.get(stockName);
		 BigDecimal open = null;
		 List<HistoricalQuote> history = stock.getHistory();
			for (HistoricalQuote quote : history) {
				open = quote.getOpen();
			}
			return open;

		}
	 
	 public BigDecimal getHighestPrice (String stockName) throws IOException {	
		 Stock stock = YahooFinance.get(stockName);
		 BigDecimal high = null;
		 List<HistoricalQuote> history = stock.getHistory();
			for (HistoricalQuote quote : history) {
				high = quote.getHigh();
			}
			return high;

		}
	 
	 public BigDecimal getLowestPrice (String stockName) throws IOException {	
		 Stock stock = YahooFinance.get(stockName);
		 BigDecimal low = null;
		 List<HistoricalQuote> history = stock.getHistory();
			for (HistoricalQuote quote : history) {
				low = quote.getLow();
			}
			return low;

		}
	 
	 public Long getVolume (String stockName) throws IOException {	
		 Stock stock = YahooFinance.get(stockName);
		 Long volume = null;
		 List<HistoricalQuote> history = stock.getHistory();
			for (HistoricalQuote quote : history) {
				volume = quote.getVolume();
			}
			return volume;

		}
	 
	 
	 private String convertDate(Calendar cal) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String formatDate = format.format(cal.getTime());
			return formatDate;
		}
	 
	 public void getHistory(String stockName, int year, String searchType) throws IOException {
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
			

		}
	 
	 public ArrayList readToHistory(String stockName, int year, String searchType) throws IOException {
		 	ArrayList<String> array = new ArrayList<String>();	
		 	Calendar from = Calendar.getInstance();
			Calendar to = Calendar.getInstance();
			from.add(Calendar.YEAR, Integer.valueOf("-" + year));

			Stock stock = YahooFinance.get(stockName);
			List<HistoricalQuote> history = stock.getHistory(from, to, getInterval(searchType));
			for (HistoricalQuote quote : history) {
				//array.add(convertDate(quote.getDate()).toString());
				array.add(convertDate(quote.getDate()).toString() + "," + quote.getOpen().toString() + "," + quote.getHigh().toString()+ "," + quote.getLow().toString()+ "," + quote.getClose().toString()+ "," + quote.getAdjClose().toString()+ "," + quote.getVolume().toString());
				//array.add(quote.getLow().toString()+ ",");
				//array.add(quote.getClose().toString()+ ",");
				//array.add(quote.getAdjClose().toString()+ ",");
				//array.add(quote.getVolume().toString()+ ",");
				
			}
			return array;
			

		}
	 
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
	 
		public Map<String, Stock> getStock(String[] stockNames) throws IOException {
			Map<String, Stock> stock = YahooFinance.get(stockNames);
			return stock;
		}

	
}