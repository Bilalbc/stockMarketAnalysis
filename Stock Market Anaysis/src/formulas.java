import java.util.*; 
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class formulas {
	
	double SMA;
	double SD;
	
	public String path ="";
	readFile r;
	//Date	
	//Open	High	Low	Close*	Adj Close**	Volume
	Date [] dateArray;
	double [][] values;
	double [][] bollingerBand;
	
	
	public formulas (String string) {
		
		r = new readFile(string);
		 
		values = new double [r.findRows()][7];
		dateArray = new Date[r.findRows()];
	}
	
	public void createDateArray(String [][] n) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for(int i=0;i<n.length;i++)
			for(int j=0; j<n[i].length;j++) {
				if(j==0) {
					try {
						Date newDate = (Date)df.parse(n[i][0]);
						dateArray[i] = newDate;
					}
					catch(Exception e) {
						System.out.println(e);
					}
				}
			}
	}
	public void createValues(String[][] n) {
		for (int i=0; i<n.length;i++) {
			for(int j=0;j<n[i].length;j++) {
				if(j>0){
					values[i][j] = Float.parseFloat(n[i][j]);
				}else {
					values[i][j] = 0;
				}
			}
		}
	}
	public void createBollinger(double [][] v) {
		double sum = 0;
		int divisor = 0;
		double upper;//upper bollinger line
		double lower;//lower bollinger line
		
		bollingerBand = new double[values.length][3];
		SMA =0;//simple moving average
		SD = 0; //standard deviation
		
		//calculating SMA
		for(int j=0;j<v.length-20;j++) {
			for(int i=0;i<19;i++) {
				sum+=v[i+j][3];//getting close values
				divisor++;	
			}
			SMA = sum/divisor;
			bollingerBand[j][1]=SMA;
			divisor = 0;
			
			//calculating standard deviation
			for(int i=0+j;i<19+j;i++) {
				SD +=(v[i][3]/sum)*(v[i][3]/sum);
				Math.sqrt(SD/20);
			}
			upper = SMA + (2*SD);
			lower = SMA - (2*SD);
			
			bollingerBand[j][0] = lower;
			bollingerBand[j][2] = upper;
			
			sum = 0;
		}
		for(int i=0;i<v.length;i++)
			System.out.println(bollingerBand[i][2]);
	}
	
	public void printValues() {
		for(int i=0; i<values.length;i++) {
			for(int j=0; j<values[i].length;j++) {
				System.out.printf("%.2f ", values[i][j]);
			}
			System.out.println();
		}
		
	}
	
	public void printDate() {
		for(int i=0; i<dateArray.length;i++)
			System.out.println(dateArray[i]);
		System.out.println();
	}
	public void reverseArray(double [][] r) {
		for(int i=0;i<10/2;i++) {
		    r[i] = r[10 - i - 1];	    	
		}
	}
	
	public double[][] getValues(){
		return values;
	}
	
	public Date[] getDates(){
		return dateArray;
	}
	
	public double [][] getBollinger(){
		return bollingerBand;
	}
}