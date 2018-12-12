package stockAnalysisProgram;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


public class formulas {
	
	double SMA;
	double SD;
	
	public String path ="";
	readFile r;
	//Date	
	//Open	High	Low	Close*	Adj Close**	Volume
	Date [] dateArray;
	double [][] values;
	float [][] SMAPeriod;
	
	
	public formulas (String string) {
		
		r = new readFile(string);
		 
		values = new double [r.findRows()][13];
		SMAPeriod = new float [r.findRows()][2];
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
	public double createSMA(int period, int s) {
		double sum = 0;
		int divisor = 0;
		int start = s;
		SMA =0;//simple moving average
		
			for(int i=1;i<=period;i++) {
				if(start>values.length) {
					return 1;
				}
				sum+=values[start-i][4];//getting close values
				divisor++;	
			}
			SMA = sum/divisor;

			return SMA;
	}
	public void createBollinger(int period) {
		int start = period;
		double upper;//upper bollinger line
		double lower;//lower bollinger line
		SD = 0; //standard deviation

		//getting SMA
		for(int j=period;j<values.length;j++) {
			values[j][7]=createSMA(period,j);
		}
		for(int j=period;j<values.length;j++) {
			//calculating standard deviation
			for(int i=0;i<period;i++) 
				SD +=Math.pow(values[start-i][4]-values[start][7], 2);
			
			SD = Math.sqrt(SD/period);
				
			upper = values[j][7] + (2*SD);
			lower = values[j][7] - (2*SD);
			
			values[j][8] = lower;
			values[j][6] = upper;

			start++;
			SD = 0;
			
		}	
	}
	
	public void createMACD() {
		//12 day SMA - 26 day SMA
		
		for(int i=12;i<values.length;i++) {
			SMAPeriod[i][0] = (float) createSMA(12, i);
			//System.out.println(SMAPeriod[i][0]);
		}
		
		for(int i=26;i<values.length;i++) {
			SMAPeriod[i][1] = (float) createSMA(26, i);
		//	System.out.println(SMAPeriod[i][1]);
		}
		
		//calculating MACD
		for(int i=26;i<values.length;i++) {
			values[i][9] = SMAPeriod[i][0]-SMAPeriod[i][1];
		}
	}
	
	public void createSignalLine() {
		//9 day SMA of MACD Line
		double sum = 0;
		int divisor = 0;
		int start = 9;
		SMA =0;//simple moving average
		
		for(int j=9;j<values.length;j++) {
			for(int i=1;i<10;i++) {
				if(start>values.length) {
					return;
				}
				sum+=values[start-i][9];//getting MACD values
				divisor++;	
			}
			SMA = sum/divisor;
			values[j][10] = SMA;
			divisor =0;
			
			sum =0;
			start +=1;
		}
	}
	public void createK(int period) {
		int start=period;
		double highestHigh = values[0][4];
		double lowestLow = values[0][4];
		
		for(int i=period;i<values.length;i++) {
			for(int j=0;j<period;j++) {

				if(start>values.length) 
					return;
				
					if(values[start-j][4]>highestHigh)
						highestHigh = values[start-j][4];
					
					if(values[start-j][4]<lowestLow)
						lowestLow = values[start-j][4];
				
					System.out.println(j);
					
					System.out.println(values[j][4]);
					System.out.println(values[i][11]+"\n");
				}
			
			System.out.println("####################"+lowestLow);
			System.out.println("####################"+highestHigh);
			
			values[i][11] = ((values[i][4]-lowestLow)/(highestHigh-lowestLow))*100;
			
			highestHigh = values[start][4];
			lowestLow =values[start][4];
			
			start++;
			}
		createD();
	}
	
	public void createD() {
		double sum = 0;
		int divisor = 0;
		int start = 3;
		SMA =0;//simple moving average
		
		for(int j=3;j<values.length;j++) {
			for(int i=0;i<3;i++) {
				if(start>values.length) {
					return;
				}
				sum+=values[start-i][11];//getting %k Values
				divisor++;	
			}
			SMA = sum/divisor;
			values[j][12] = SMA;
			divisor =0;
			
			sum =0;
			start +=1;
		}
		
	}
	
	public double getHighest() {
		double highest = values[0][4];
		
		for(int i =0;i<values.length;i++) {
			if(highest<values[i][4])
				highest = values[i][4];
		}
		
		return highest;
	}
	
	public double getLowest() {
		double lowest = values[0][4];
		
		for(int i=0;i<values.length;i++) {
			if(lowest>values[i][4])
				lowest = values[i][4];
		}
		return lowest;
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

}