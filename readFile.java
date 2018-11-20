package stockAnalysisProgram;

import java.io.*; 
import java.util.ArrayList;
import java.util.StringTokenizer;
 
 public class readFile {
 	
 	long fileSize;
 	int rows;
 	//C:\\CompSci gr 12\\Summative\\Data\\change test.txt
 	static File fileLoc;
 	String items [][];
 	ArrayList<String> dates = new ArrayList<String>();
 	ArrayList<Double> values = new ArrayList<>();
 	
 	public readFile(String string) {
 		
 		fileLoc = new File (string);
 		
 		try {
 			
 			BufferedReader file = new BufferedReader(new FileReader(fileLoc));
 
 			try {
 				String line = file.readLine();
 
 				for (int i =0; i<10; i++){
 					System.out.println(line);
 					line = file.readLine();
 				}
 					file.close();
 				
 				}catch (IOException e){
 					System.out.println("Sorry there was an error reading");
 				}
 			}catch (FileNotFoundException e1) {
 				e1.printStackTrace();
 			}
 	}
 
 	public static void main(String[]args) {
 	}
 	
 	//finds the numbers of rows in document 
 	public int findRows() {
 		rows=0;
 		try {
 			BufferedReader file = new BufferedReader(new FileReader(fileLoc));
 			
 			try {
 				while(file.readLine() != null){
 					rows++;
 				}
 			}catch (IOException e) {
 				e.printStackTrace();
 			}
 			
 		} catch (FileNotFoundException e) {
 			e.printStackTrace();
 		}
 		
 		return rows;
 	}
 	
 	//makes data file into array
 	public void makeArray() {
 		int r = 0;
 		items = new String[findRows()][7];
 		
 		try {
 			BufferedReader file = new BufferedReader(new FileReader(fileLoc));
 			
 			try {
 				String line = null;
 
 				while((line=file.readLine())!=null) {
 					StringTokenizer z = new StringTokenizer(line,",");
 					while(z.hasMoreTokens()) {
 						for(int c = 0; c < 7;c++) {
 							/*if(c==0) {
 								dates.add(z.nextToken());
 							}else {
 								values.add(Double.parseDouble(z.nextToken()));
 							}*/
 							items[r][c] = z.nextToken();
 						}
 						r++;
 					}
 				}
 				file.close();
 			}
 			catch (IOException e){
 				System.out.println("Sorry there was an error reading");
 			}
 			
 		}catch(FileNotFoundException e) {
 			e.printStackTrace();
 		}
 	}
 	
 	//prints the array 
 	public void printArray() {
 		for(int i=0;i<items.length;i++) {
 			System.out.printf("%s - ", i);
 			for(int j=0; j<items[i].length;j++) {
 				System.out.printf("%s ", items[i][j]);
 			}
 				System.out.println();
 		}
 
 
 	}
 	
 	public String[][] getArray() {
 		return items;
 	}
 	
 	public String getFilePath() {
 		return fileLoc.toString();
 	}
 }