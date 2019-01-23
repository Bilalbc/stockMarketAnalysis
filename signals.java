package stockMarketAnalysis;

public class signals {
	
	String filePath;
	readFile r; 
	formulas f;
	int length;
	double[][] values;

	public signals(double [][] v) {
		length = v.length;
		values = new double[length][12];
		values = v;
	}
	
	public int MACDSignal() {
		if(values[length-1][9]>values[length-1][10]) 
			return 1;  
		
		else return 0;
		
	}
	
	public int FSSignal() {
		if(values[length-1][11]>values[length-1][12])
			return 1;
		
		else return 0;	
	}
	
	public String makeSignal() {
		if(MACDSignal() * FSSignal() == 1) 
			return "1";
		
		else return "0";
	}
	
	public double[][] getArray(){
		return f.values;
	}
	

}