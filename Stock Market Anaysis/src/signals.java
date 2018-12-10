package stockAnalysisProgram;
public class signals {
	
	String filePath;
	readFile r; 
	formulas f;
	
	public signals(String string) {
		filePath = string;
		r = new readFile(filePath);
		f = new formulas(filePath);
	}
	

	public boolean MACDSignal() {
		if(f.values[f.values.length-1][9]>f.values[f.values.length-1][10]) 
			return true;
		
		else 
			return false;
	}
	
	public boolean FSSignal() {
		
		return true;
	}
	
	public boolean bollingerSignal() {
		
		return true;
		
	}
	
	public void makeSignal() {
		
		MACDSignal();
		FSSignal();
		bollingerSignal();
		if(MACDSignal()&&FSSignal()&&bollingerSignal())
			System.out.println("money");
		
		else
			System.out.println("no money");
			
	}

}