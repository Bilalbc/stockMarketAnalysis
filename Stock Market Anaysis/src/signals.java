package stockMarketAnalysis;

public class signals {
	
	String filePath;
	readFile r; 
	formulas f;
	int length;
	
	
	
	public signals(String string) {
		filePath = string;
		r = new readFile(filePath);
		f = new formulas(filePath);
		length = f.values.length;
	}
	

	public int MACDSignal() {
		if(f.values[length-1][9]>f.values[length-1][10]) 
			if(f.values[length][9]>average(f.values, 9))
				return 1;
		
			else
				return 0;
		
		else 
			return 0;
	}
	
	public int FSSignal() {
		if(f.values[length-1][11]>f.values[length-1][12])
			if(f.values[length][11]>average(f.values, 12))
				return 1;
		
			else
				return 0;
						
		else 
			return 0;
	}
	
	public int makeSignal(String fileLoc) {
		new signals(fileLoc);
		
		if(MACDSignal() * FSSignal() == 1) 
			return 1;
		
		else return 0;
	}
	
	public double[][] getArray(){
		return f.values;
	}
	
	public double average(double[][] trend, int y) {
		double sum = 0;
		int counter = 0;
		for(int i =1; i <= 10; i++) {
			sum+=trend[trend.length-i][y];
			counter++;
		}
		return sum/counter;
	}

}