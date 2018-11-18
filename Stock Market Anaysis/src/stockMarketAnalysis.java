package stockAnalysisProgram;

public class stockMarketAnalysis {

	public static void main(String[]args) {
		
		
		
		readFile r = new readFile("C:\\Users\\haseebchaudhry\\git\\repository\\stockAnalysisProgram\\src\\Data\\Daily - 6 months.txt");
		r.makeArray();
		//r.printArray();
		
		formulas f = new formulas("C:\\Users\\haseebchaudhry\\git\\repository\\stockAnalysisProgram\\src\\Data\\Daily - 6 months.txt");
		
		f.createValues(r.getArray());
		
		f.createSMA(20);
		f.createBollinger(20);
		
		
	//	f.printArray();
		System.out.println();
	//	f.reverseArray(f.getArray());
		//f.printArray();
		
		
		 for(int i=10;i<70;i++){ 
			 System.out.println(f.values[i][6]);
			// System.out.println(f.values[i][6]);
		 }
		 
		 System.out.println();
		 
		 for(int i=10;i<70;i++)
			 System.out.println(f.values[i][7]);
		 
		 drawGraphs.main(args);
		
	}
}