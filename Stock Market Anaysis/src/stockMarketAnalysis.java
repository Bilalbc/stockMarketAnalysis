package stockMarketAnalysis;

public class stockMarketAnalysis {

	
	public static void main(String[]args) {
		
		String fileLoc =  "src//Data//daily - 6 months.txt";
		readFile r = new readFile(fileLoc);
		formulas f = new formulas(fileLoc);
		
		signals s = new signals("");
		
		if(s.makeSignal(fileLoc) == 1) {
			System.out.println("BUY");
		}
		else
			System.out.println("SELL");
		
		launchPage l = new launchPage();
		
	}
}
