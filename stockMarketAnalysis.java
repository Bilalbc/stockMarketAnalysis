import javafx.application.Application;

public class stockMarketAnalysis {

	public static void main(String[]args) {
		
		Application.launch(launchPage.class, args);
		
		readFile r = new readFile();
		r.makeArray();
		//r.printArray();
		
		formulas f = new formulas();
		f.createValues(r.getArray());
		f.printArray();
		System.out.println();
		f.reverseArray(f.getArray());
		f.printArray();
	
	
		
		
		
		 for(int i=0;i<10;i++){ 
			// System.out.println(f.values[i][3]);
		 }
		 


		
	}


	
}
