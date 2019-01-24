package stockMarketAnalysis;

import java.util.ArrayList;

import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import java.util.Date;
import java.util.HashSet;

import org.jfree.ui.RefineryUtilities;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.imageio.ImageIO;

import javafx.scene.*;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import javafx.scene.image.*;

public class launchPage extends Application{
	Stage window;
	Scene launch, main, emailPage, follow;
	Label timInterval;
	ListView<String> listView, followingList;
	TextField typeFollowed;
	
	//Declaring and initializing variables.
	String close = "",open = "",vol = "",symbol2 = "",high = "",low= "",date = "";
	
	private static String fileLoc;
	private String title;
	private String title2;
 	private int period = 0;
 	private formulas f;
 	private signals s;
 	private int frame = 20;
	readFile r;

	//Declaring ArrayLists
	public ArrayList <String> emails = new ArrayList();
	public ArrayList <String> followingStocks = new ArrayList();
	public String [] signals;
	HashSet<String> hs;

	@SuppressWarnings("restriction")

	//Main start method for launchPage
	public void start(Stage primaryStage) throws Exception {

		//Creates instance of yahooFinance class.
		yahooFinance stats = new yahooFinance();
		window = primaryStage;
		window.setTitle("SmartInvest");
		window.setResizable(false);
		
		//LAUNCH SCENE:
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		//The spacing vertically is 8 and horizonatlly is 10.
		grid.setVgap(8);
		grid.setHgap(10);
		
		//email Label
		Label email = new Label("Email:");
		email.setTextFill(Color.WHITE);
		email.setFont(new Font("Helvetica",15));
		GridPane.setConstraints(email,6,14);
		
		
		//email Textfield
		TextField newInput = new TextField("ex.SmartInvest1234@hotmail.com");
		newInput.setStyle("-fx-background-color: white; ");
		GridPane.setConstraints(newInput, 7, 14);
		
		//login button
		Button loginButton = new Button("Next");
		loginButton.setStyle("-fx-background-color: white; ");
		//When button is clicked, these actions occur (adds email to arrayList and ListView and changes scene)
		loginButton.setOnAction(e -> {
			emails.add(newInput.getText());
			listView.getItems().addAll(newInput.getText());
			window.setScene(main);
		});
		GridPane.setConstraints(loginButton, 6, 15);
		
		//Label Title
		Label title = new Label("SmartInvest");
		
		title.setFont(Font.font("Copperplate Gothic Light", 27));
		title.setTextFill(Color.WHITE);
		GridPane.setConstraints(title, 7, 4);
		

		//Makes background image for the first scene.
	BackgroundImage myBI= new BackgroundImage(new Image("file:/C:/Users/bilal/git/repository/stockMarketAnalysis/src/stockMarketAnalysis/launchImage.jpg"),BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
		     BackgroundSize.DEFAULT);
		
		//Adds all components to the layout.
		grid.getChildren().addAll(email, newInput, loginButton, title);
		grid.setBackground(new Background(myBI));
		//Makes the first scene and sets its layout and size. Sets it as the main scene.
		Scene launch = new Scene(grid,420,300);
		window.setScene(launch);
		
		//MAIN GUI:
		
		//making layout for second scene
		GridPane layout2 = new GridPane();
		layout2.setPadding(new Insets(10,10,10,10));
		layout2.setVgap(1);
		layout2.setHgap(1);
		
		//Making several components for the Graphical User Interface: These include labels for display purpsoes and buttons.
		TextField symbol = new TextField("ex.GLD");
		layout2.setConstraints(symbol,90,20);
		
		Button searchSymbol = new Button("Search");
		layout2.setConstraints(searchSymbol,100,20);
	
		
		Label title2 = new Label("SmartInvest");
		title2.setFont(new Font("Copperplate Gothic Light", 40));
		title2.setTextFill(Color.WHITE);
		layout2.setConstraints(title2, 2,4);
		

		Label openingPrice = new Label("Opening Price: " + open);
		openingPrice.setFont(new Font("Arial", 20));
		openingPrice.setTextFill(Color.WHITE);
		layout2.setConstraints(openingPrice, 2,25);
		
		Label highestPrice = new Label("Highest Price: " + high);
		highestPrice.setFont(new Font("Arial", 20));
		highestPrice.setTextFill(Color.WHITE);
		layout2.setConstraints(highestPrice, 2,30);
		
		Label lowestPrice = new Label("Lowest Price: " + low);
		lowestPrice.setFont(new Font("Arial", 20));
		lowestPrice.setTextFill(Color.WHITE);
		layout2.setConstraints(lowestPrice, 2,35);
		
		Label closePrice = new Label("Close Price: " + close);
		closePrice.setFont(new Font("Arial", 20));
		closePrice.setTextFill(Color.WHITE);
		layout2.setConstraints(closePrice, 40,25);
		
		Label volume = new Label("Volume(Number of trades): " + vol);
		volume.setFont(new Font("Arial", 20));
		volume.setTextFill(Color.WHITE);
		layout2.setConstraints(volume, 40,30);
		
		Label time = new Label("Date: " + date);
		time.setFont(new Font("Arial", 20));
		time.setTextFill(Color.WHITE);
		layout2.setConstraints(time, 2,10);
		
		Label stock = new Label("Symbol " + symbol2);
		stock.setFont(new Font("Arial", 20));
		stock.setTextFill(Color.WHITE);
		layout2.setConstraints(stock, 2,20);
		
		//Choice box which allows the user to select the frequency for the graph.
		ChoiceBox <String> freq = new ChoiceBox<>();
		freq.getItems().addAll("Daily", "Weekly", "Monthly");
		freq.setValue("Daily");
		layout2.setConstraints(freq, 2, 85);
		
		//One action, calls neccessary methods from the yahooFinance class after creating an instance of the class.
		searchSymbol.setOnAction(e -> {
			yahooFinance search = new yahooFinance();
			String symb = symbol.getText();
			try {

				//Gets all the quotes of the stocks for the symbol passed through
				symbol2 = search.getSymbol(symb).toString();
				date = search.getDate(symb).toString();
			    open = search.getOpeningPrice(symb).toString();
			    close = search.getClosePrice(symb).toString();
				high = search.getHighestPrice(symb).toString();
			    low = search.getLowestPrice(symb).toString();
				vol = search.getVolume(symb).toString();
				
				//Strings the values with the labels.
				stock.setText("Symbol: " + symbol2);
				time.setText("Date: " + date);
				openingPrice.setText("Opening Price: " + open);
				closePrice.setText("Close Price: " + close);
				highestPrice.setText("Highest Price: " + high);
				lowestPrice.setText("Lowest Price: " + low);
				volume.setText("Volume: " + vol);
				
				sendingQuotes send = new sendingQuotes();
				System.out.println(symb+ "THIS IS THE SYMBOL");
		 		send.sendQuotes(symb);
				
			} catch (IOException e1) {
				e1.printStackTrace();
	
			}
			
		});
		
		//When the draw graph button is clicked, check the selected frequency 
		//the choice box. Call the draw graph method depending on the user's selection. 
		Button set = new Button("Draw Graph");
		set.setOnAction(e -> {
			int choice;
			if (freq.getValue().equals("Daily")) {
			drawGraph(1, symbol2);
			choice = 1;
			}
			else if (freq.getValue().equals("Weekly")){
			drawGraph(2, symbol2);
			choice = 2;
			}
			else if (freq.getValue().equals("Monthly"))
			drawGraph(3, symbol2);
			choice = 3;
			
		//Creates an instance of the subPlot class and calls the createPNG method. 
		subPlot save = new subPlot("Test", choice, symbol2);
		save.createPNG(symbol2);
			
		});
		layout2.setConstraints(set, 2, 75);	
		
		//Button which switches scenes.
		Button addEmail = new Button("Add emails");
		layout2.setConstraints(addEmail,2,55);
		addEmail.setOnAction(e -> {
			window.setScene(emailPage);
		});

		//Button which switches scenes. 
		Button setFollow = new Button("Following");
		layout2.setConstraints(setFollow,2,65);
		setFollow.setOnAction(e -> {
			window.setScene(follow);
		 	followingList.getItems().addAll(followingStocks);
			makePers();//adds followed stocks to file for storage
		});
		

		//Second background image which is displayed in the main scene.
		BackgroundImage BI2= new BackgroundImage(new Image("file:/C:/Users/bilal/git/repository/stockMarketAnalysis/src/stockMarketAnalysis/mainImage.jpg"),BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
			     BackgroundSize.DEFAULT);


		//Adds all the components of the GUI to the layout.
		layout2.getChildren().addAll(highestPrice, lowestPrice, time, title2, openingPrice, volume, closePrice
				,freq, set, addEmail, symbol, searchSymbol, stock, setFollow);
		layout2.setBackground(new Background(BI2));
		//Makes the main scene and sets its layout.
		main = new Scene(layout2,900,500);
		

		//Calls the methods below when the window is requested to close. 
		window.setOnCloseRequest(e -> {
			e.consume();
			closeProgram();
		});
		

		//Add Email GUI
		
		//Making the layout for the add Email GUI.
		GridPane layout3 = new GridPane();
		layout3.setPadding(new Insets(10,10,10,10));
		layout3.setVgap(8);
		layout3.setHgap(10);
		
		layout3.setStyle("-fx-background-color: black");
		
		//Creates the scene and sets the size and layout.
		emailPage = new Scene(layout3,550,500);
		 
		//A button which returns the user to the previous scene.
		 Button backButton = new Button("Return");
		 layout3.setConstraints(backButton,0,1);
		 backButton.setOnAction(e -> {
			 window.setScene(main);
		 });	
		 
		 	//A ListView to display the the list of emails the user adds. 
		 	listView = new ListView<>();
			listView.getItems();
			listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			
			//TextField for the user to type in their email address that they want in the list.
			TextField emailAddition = new TextField("ex.SmartInvest1234@hotmail.com");
			GridPane.setConstraints(emailAddition, 1, 1);
			
			//A button which adds the email's to the ListView. 
			Button add = new Button("Add");
			layout3.setConstraints(add,2,1);
			add.setOnAction(e -> {
				listView.getItems().addAll(emailAddition.getText());
				
			});

			//This button is called when the user wishes to send the email. Uses the selection model method to check the selected emails on the ListView and adds those to the ArrayList. 
			Button send = new Button("Send Now");
			layout3.setConstraints(send,0,4);
			send.setOnAction(e->{
				emails.addAll(listView.getSelectionModel().getSelectedItems());
				createFile();
				//Makes an object of the sendEmails class.
				sendEmails sendEmail = new sendEmails();
				//Class the emailCode method.
				sendEmail.emailCode(followingStocks, emails, date, symbol2, close, open, high, low, vol);
			});


			 
			//Adds all the GUI components to the layout.
			layout3.getChildren().addAll(backButton, listView, emailAddition, add, send);
			layout3.setStyle("-fx-background-color: black");
			
			//FOLLOW SCENE
			
			//Creates the layout for the follow scene.
			GridPane layout4 = new GridPane();
			layout4.setPadding(new Insets(10,10,10,10));
			layout4.setVgap(1);
			layout4.setHgap(1);
			
			//A button which returns the user to the previous scene.
			 Button backButton2 = new Button("Return");
			 layout4.setConstraints(backButton2, 100, 100);
			 backButton2.setOnAction(e -> {
				 window.setScene(main);
			 });
			 
			 
			 //Makes a ListView to display stocks being followed.
			 followingList = new ListView<>();
			 followingList.getItems();
		   	 followingList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			 
		   	 //A button which adds the typed in stock to the followingList and the ArrayList. 
			 Button followButton = new Button("follow");
			 layout4.setConstraints(followButton, 0 , 100);	
			 	followButton.setOnAction(e -> {
			 		followingStocks.add(typeFollowed.getText());
			 		followingList.getItems().addAll(typeFollowed.getText());
			 		
			 	});
			 
			 	 
				layout4.setConstraints(backButton,0,1);
			   	
				//The button allows for the removal of the selected Stocks from both the ListView and the ArrayList. 
			   	Button removeFollow = new Button ("Remove");
				layout4.setConstraints(removeFollow,0,90);
				removeFollow.setOnAction(e -> {
					followingStocks.remove(followingList.getSelectionModel().getSelectedItems());
					String selectedIndex;
					String text ="";
					selectedIndex = followingList.getSelectionModel().getSelectedItem();
					followingList.getItems().remove(selectedIndex);
				//	deleteTxt(selectedIndex, "src//Data//persFile.txt");
				});

				//A TextField where the user can enter the stock symbol they wish to add to the following list.
			   	typeFollowed = new TextField("[Enter stocks symbols here you want to follow]");
			   	layout4.setConstraints(typeFollowed, 100, 0);
			   	 
			   	//Adds all the GUI components to the layout.
				layout4.getChildren().addAll(backButton2, followingList, typeFollowed, followButton, removeFollow);
				layout4.setStyle("-fx-background-color: black");
				//Creates follow scene and sets its layout and its size.
				follow = new Scene(layout4,450,450);
				
				//displays window
				window.show();
	
	}
	
	/*
	 *method used to create a file on the computer containing information of the stocks that 
	 *the user has chosen to follow
	 */
	private void makePers() {
		//clearing list and array 
	 	followingList.getItems().clear();
		followingStocks.clear();
		
		try {
			//writer and reader for file
			BufferedWriter writer = new BufferedWriter(new FileWriter("src//Data//persFile.txt", true));
			BufferedReader reader = new BufferedReader(new FileReader("src//Data//persFile.txt"));
			

			//typing all of the stocks that the user has followed 
			ArrayList<String>temp = new ArrayList<String>();
			writer.append(typeFollowed.getText()+"\n");
			String line = reader.readLine(); 
	          
	        // set store unique values 
	        hs = new HashSet<String>(); 
	          
	        // loop for each line of input.txt 
	        while(line != null) 
	        { 
	        	temp.add(line);
	            // write only if not 
	            // present in hashset 
	        	for(int i=temp.size()-1;i<=(temp.size()-typeFollowed.getText().length()/3);i++) {
		            if(hs.add(temp.get(i))) //if this item is new in the hash set 
		                followingStocks.add(line); 
	        }
	            line = reader.readLine(); //read next line
	            
	        } 
	        writer.flush(); 
	          
	        // closing resources 
	        reader.close(); 
	        writer.close(); 

		 	followingList.getItems().addAll(followingStocks);//add the stocks as a display in window 
		 	
		} catch (IOException e1) {}

	}
	
	/*
	 * method used for the user to delete stocks in the follow list 
	 */
	private void deleteTxt(String selectedIndex, String file) {
		
		int x =0;
		ArrayList<String>temp = new ArrayList<String>();

		HashSet<String> hash = new HashSet<String>(); 
		try {
			//initialize reader and writer
			BufferedReader reader = new BufferedReader(new FileReader(file));
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			
			//while the line has text 
			for (String line; (line = reader.readLine()) != null; x++) {
				
				//if it is not the text that we are looking to delete
				 if (!line.equals(selectedIndex)) {
					temp.add(line);//add to temporary array list
					if(!hash.add(temp.get(x-1)))//if this is not unique in the hash list remove it from array 
						temp.remove(x);
				 }
			}
			for(int i=0;i<temp.size();i++) {
				//write out the temporary array this time without the slected index
				writer.append(temp.get(i));
				writer.newLine();
			}
			//close and clear arrays
			
			temp.clear();
			writer.close();
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*
	 * method used to close program
	 */
	private void closeProgram() {
		Boolean anwser = closeProgram.display("", "Sure you want to exit?");
		if (anwser)
			window.close();
		createFile();
		
	}
	 
	/*
	 * method used to draw the graph when prompted 
	 */
	 public void drawGraph (int change, String symb) {
		 //call subplot class to draw graph 
		 subPlot chart = new subPlot(symb, change, symb);
			chart.pack();
			//center the graph
		    RefineryUtilities.centerFrameOnScreen(chart);
		    //set it to visible
		    chart.setVisible(true);
	 }
	 /*
	  * method used to create prompt the creation of the text and image file 
	  */
	 public void createFile() {
		 signals = new String[3];//array to store the return of making signals
		 String [] type = new String[3];
		 //Separate types of files
		 type[0]= "Monthly - 5 years.txt";
		 type[1]= "Weekly - 2 years.txt";
		 type[2]= "Daily - 1 year.txt";
		 
		 createFiles c = new createFiles(followingStocks);
		 
		 //loop to cycle through all the stocks on user's follow list 
		 for(int i=0;i<followingStocks.size();i++) {
			 //to run through all frequencies of the charts 
			 for(int j=0;j<3;j++) {
				 //creating values for each of these charts
				 f = new formulas("src//Data//"+followingStocks.get(i)+" "+ type[j]);
				 //making signals for the charts 
				 signals s = new signals(f.values);
				 signals[j] = s.makeSignal();

				 //creating files
				 c.createImageFile(followingStocks.get(i));
			 }
			 c.createTxtFile(signals, f.values);
		 }
	 }
	 
}