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
		for(int i =0;i<length;i++)
			System.out.println(values[i][9]+"asd");
		
		System.out.println(length);
		if(values[length-1][9]>values[length-1][10]) 
			if(values[length-1][9]>average(values, 9))
				return 1;
		
			else
				return 0;
		
		else 
			return 0;
	}
	
	public int FSSignal() {
		if(values[length-1][11]>values[length-1][12])
			if(values[length-1][11]>average(values, 12))
				return 1;
		
			else
				return 0;
						
		else 
			return 0;
	}
	
	public String makeSignal() {
		if(MACDSignal() * FSSignal() == 1) 
			return "1";
		
		else return "0";
	}
	
	public double[][] getArray(){
		return f.values;
	}
	
	private double average(double[][] trend, int y) {
		double sum = 0;
		int counter = 0;
		for(int i =1; i <= 10; i++) {
			sum+=trend[trend.length-i][y];
			counter++;
		}
		return sum/counter;
	}

}