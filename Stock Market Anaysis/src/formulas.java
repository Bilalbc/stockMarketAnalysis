package stockAnalysisProgram;
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
		 
		values = new double [r.findRows()][9];
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
				if(j>0&&j<6){
					values[i][j] = Float.parseFloat(n[i][j]);
				}else {
					values[i][j] = 0;
				}
			}
		}
	}
	
	public void createSMA(int period) {
		double sum = 0;
		int divisor = 0;
		int start = period;
		SMA =0;//simple moving average
		
		for(int j=period;j<values.length;j++) {
			for(int i=1;i<period+1;i++) {
				if(start>values.length) {
					return;
				}
				sum+=values[start-i][4];//getting close values
				divisor++;	
			}
			SMA = sum/divisor;
			values[j][7]=SMA;
			divisor = 0;
		
			sum = 0;
			start+=1;
		}
	}
	public void createBollinger(int period) {
		int start = period;
		double upper;//upper bollinger line
		double lower;//lower bollinger line
		
		SD = 0; //standard deviation
		
		//calculating SMA
		for(int j=period;j<values.length;j++) {
			//calculating standard deviation
			for(int i=1;i<period+1;i++) {
				SD +=Math.pow(values[start-i][4]-values[start-i][7], 2);
				SD = Math.sqrt(SD/period);
			}
			upper = values[j][7] + (2*SD);
			lower = values[j][7] - (2*SD);
			
			values[j][8] = lower;
			values[j][6] = upper;
			
			start+=1;
		}
	}
	
	public void createEMA() {
		
	}
	public void createMACD() {
		
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