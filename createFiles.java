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
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class createFiles {
	
	File file;
	BufferedWriter writer;
	BufferedReader reader;
	int length;
	int run = 0;
	ArrayList<String> followingStocks;
	
	//-----------------------------------------------------------------------------------------------------------//
	/*
	 * constructor method 
	 */
	public createFiles() {}
	
	//-----------------------------------------------------------------------------------------------------------//
	/*
	 * constructor method 
	 * initializes email file with text  and date 
	 */
	public createFiles(ArrayList<String> followingStocks) {
		 file = new File("src//Email Files//status.txt");
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		 LocalDateTime date = LocalDateTime.now();  
		
		 this.followingStocks = followingStocks;
		 
		 
		try {
			//initialize file writer 
			writer = new BufferedWriter(new FileWriter(file));
			
			//write text into file 
			writer.append("##########################################################################################################################################\n"
						+ "\t\t\t\t\t\t\t       Daily Report\n"
						+ "\t\t\t\t\t\t\t   "+dtf.format(date)+"\n"
						+ "##########################################################################################################################################");
			
			//write columns 
			writer.append("\nSTOCK\t\tOPEN\t\tLOW\t\tHIGH\t\tCLOSE\t\tAVERAGE\t\tSIGNAL");
			
			//close writer 
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------//
	/*
	 * create text file based upon signals and provide relevant information regarding prices and
	 * stock data 
	 */
	public void createTxtFile(String [] signals, double[][] values) {
		String signal;
		String symb;
		launchPage l = new launchPage();
		String open, low, high, close, average;

		//declare decimal formatting for proper display 
		DecimalFormat df = new DecimalFormat(".##");
		
		length = values.length;
		
		try {
			//initialize writer 
			writer = new BufferedWriter(new FileWriter(file, true));
			
				symb = followingStocks.get(run);
				
				
				//making signals based of the individual signal off of daily weekly and monthly overall signals
				//if all are providing the same buy signal, write BUY
				if(Integer.parseInt(signals[0])*Integer.parseInt(signals[1])*Integer.parseInt(signals[2]) == 1) 
						
						signal = "BUY";			
				else//else make sell signal
						signal = "SELL";

				//type out relevant prices 
				open = df.format(values[length-1][1]);
				low = df.format(values[length-1][3]);
				high = df.format(values[length-1][2]);
				close = df.format(values[length-1][4]);
				average = df.format(values[length-1][5]);
				
			writer.append("\n\n"+symb+"             "+open+ "           "+low
						+"           "+high+"           "+close
						+"           "+average+"            "+signal);
			
			run++;
			writer.close();//close writer 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------//
	/*
	 * method to make image file and include all images of stock to send in email 
	 */
	public void createImageFile(String symb) {

		//sets displacement of each image 
	   	 int displacementx = 0, displacementy = 0;
	   	 //sets size of image file 
	     int width = 1740, height = 950;
	   	 
	     //writes images into email file
		try {
			//makes image file to send 
		     File imgFile = new File("src//Email Files//"+symb+".png");
		     //initializes individual images
		     BufferedImage img1, img2, img3, img4, img5, img6, img7, img8, img9;
		     
		     //creating graphics 
		     Graphics2D g2;
		     
		     //initializing combined image 
 		   	 BufferedImage combinedImage = new BufferedImage(width, height,
			   			BufferedImage.TYPE_INT_ARGB);
 		   	 //making each image based off desired graph and type 
		 		   	 img1 = ImageIO.read(new File("src//Charts//"+symb+" Daily.png"));
				   	 img2 = ImageIO.read(new File("src//Charts//"+symb+" Weekly.png"));
				   	 img3 = ImageIO.read(new File("src//Charts//"+symb+" Monthly.png"));
		 		   	 img4 = ImageIO.read(new File("src//Charts//"+symb+" Daily - FS.png"));
		 		   	 img5 = ImageIO.read(new File("src//Charts//"+symb+" Weekly - FS.png"));
				   	 img6 = ImageIO.read(new File("src//Charts//"+symb+" Monthly - FS.png"));
			 	 	 img7 = ImageIO.read(new File("src//Charts//"+symb+" Daily - MACD.png"));
			 		 img8 = ImageIO.read(new File("src//Charts//"+symb+" Weekly - MACD.png"));
					 img9 = ImageIO.read(new File("src//Charts//"+symb+" Monthly - MACD.png"));
				   	 
					 int height1 = img1.getHeight();
					 int width1 = img1.getWidth();
					 
				     g2 = combinedImage.createGraphics();
				      
				     
				     //drawing image into email file, and incrementing/displacing images for formatting 
				     Color oldColor = g2.getColor();
				     g2.setPaint(Color.BLACK);
				     g2.fillRect(0, 0, width, height);
				     g2.setColor(oldColor);
				     g2.drawImage(img1, null, 20, 20);
				     g2.drawImage(img2, null, width1+90, 20);
				     g2.drawImage(img3, null, 2*(width1+90), 20);
				     
				     g2.drawImage(img4, null, 60,  height1+70);
				     g2.drawImage(img5, null, width1+130, height1+70);
				     g2.drawImage(img6, null, 2*(width1+110), height1+70);
				     
				     g2.drawImage(img7, null, 60, 2*height1+70);
					 g2.drawImage(img8, null, width1+130, 2*height1+70);
					 g2.drawImage(img9, null, 2*(width1+110), 2*height1+70);
					 
				     g2.dispose();

				     ImageIO.write(combinedImage,"PNG", imgFile);
					 displacementy += 500;	  
					 
		    }catch (IOException ie) {
		      ie.printStackTrace();
		    }
		 System.out.println("Reading complete.");
	}
}