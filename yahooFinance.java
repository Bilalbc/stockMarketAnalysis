package stockAnalysisProgram;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;

public class yahooFinance {

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

	
}
	
	

