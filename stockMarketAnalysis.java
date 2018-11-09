
public class stockMarketAnalysis {

	public static void main(String[]args) {
		
		
		
		readFile r = new readFile("C:\\CompSci gr 12\\Summative\\Data\\change.txt");
		r.makeArray();
		//r.printArray();
		
/*	//	formulas f = new formulas();
		f.createValues(r.getArray());
		f.createDateArray(r.getArray());
		f.printDate();*/
		System.out.println();
/*		f.reverseArray(f.getValues());
		f.printArray();*/
		
	//	System.out.println(r.getFilePath());
		 for(int i=0;i<10;i++){ 
			// System.out.println(f.values[i][3]);
		 }
		 
		 drawGraphs.main(args);
		
	}
}
