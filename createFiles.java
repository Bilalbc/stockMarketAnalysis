package stockMarketAnalysis;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class createFiles {
	
	File file;
	BufferedWriter writer;
	BufferedReader reader;
	int length;
	int run = 0;
	ArrayList<String> followingStocks;
	
	public createFiles(ArrayList<String> followingStocks) {
		 file = new File("src//Email Files//test.txt");
		
		 this.followingStocks = followingStocks;
		 
		 
		try {
			writer = new BufferedWriter(new FileWriter(file));
			
			writer.append("##############################################################\n\t\t\t\t\t\ttest\n##############################################################");
			
			writer.append("\nSTOCK\t\tOPEN\t\tLOW\t\tHIGH\t\tCLOSE\t\tAVERAGE\t\tSIGNAL");
			
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createTxtFile(String [] signals, double[][] values) {
		String signal;
		String symb;
		launchPage l = new launchPage();
		
		length = values.length;
		//Write Content
		//FileWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(file, true));
			
				symb = followingStocks.get(run);
				
				if(Integer.parseInt(signals[0])*Integer.parseInt(signals[1])*Integer.parseInt(signals[2]) == 1) 
						
						signal = "BUY";			
				else
						signal = "SELL";
				
				writer.append("\n\n"+symb+"\t\t\t"+(float)values[length-1][1]+ "\t\t"+(float)values[length-1][3]+"\t"+(float)values[length-1][2]+"\t\t"+(float)values[length-1][4]+"\t\t"+(float)values[length-1][5]+"\t\t"+signal);
				
			run++;
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void createImageFile() {
		try {
		     int width = 1000, height = 1000;
		      // TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed
		      // into integer pixels
		      
		     BufferedImage img1;
		     BufferedImage img2;
		     BufferedImage img3;
			 String [] type = new String[3];
			 type[2]= "Daily - MACD";
			 type[1]= "Daily - FS";
		   	 type[0]= "Daily";
		     
		   	 int displacement = 0;
		   	 
		   	 BufferedImage combinedImage = new BufferedImage(width, height,
		   			BufferedImage.TYPE_INT_ARGB);
		      
		      for(int i = 0;i<followingStocks.size();i++) {
		    	  for(int j=0;j<3;j++) {
		    		  
			      	img1 = ImageIO.read(new File("src//Charts//"+followingStocks.get(0)+" Daily.png"));
			      	img2 = ImageIO.read(new File("src//Charts//"+followingStocks.get(i)+" Daily - MACD.png"));
			   // 	img3 = ImageIO.read(new File("src//Charts//"+followingStocks.get(i)+" Daily - FS.png"));
			      
				      Graphics2D g2 = combinedImage.createGraphics();
				      Color oldColor = g2.getColor();
				      g2.setPaint(Color.BLACK);
				      g2.fillRect(0, 0, width, height);
				      g2.setColor(oldColor);
				      g2.drawImage(img1, null, displacement, 0);
				      g2.drawImage(img2, null, displacement, 500);
				 //     g2.drawImage(img3, null, displacement, 1000);
				      g2.dispose();

			    	  ImageIO.write(combinedImage,"PNG", new File("src//Charts//test.png"));
		    	  }
		    	  displacement += 300;
		      }
		    }catch (IOException ie) {
		      ie.printStackTrace();
		    }
		 System.out.println("Reading complete.");
	}
}