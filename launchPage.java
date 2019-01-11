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
	
	String close = "",open = "",vol = "",symbol2 = "",high = "",low= "",date = "";
	
	private static String fileLoc;
	private String title;
 	private int period = 0;
 	private formulas f;
 	private signals s;
 	private int frame = 20;
	readFile r;

	public ArrayList <String> emails = new ArrayList();
	public ArrayList <String> followingStocks = new ArrayList();
	public String [] signals;
	HashSet<String> hs;

	@SuppressWarnings("restriction")
public void start(Stage primaryStage) throws Exception {
		
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
		
		//Name Label
		Label email = new Label("Email:");
		email.setTextFill(Color.WHITE);
		email.setFont(new Font("Helvetica",15));
		//Top left
		
		GridPane.setConstraints(email,6,14);
		//Name input
		//Textfield
		TextField newInput = new TextField("ex.SmartInvest1234@hotmail.com");
		newInput.setStyle("-fx-background-color: white; ");
		GridPane.setConstraints(newInput, 7, 14);
		
		Button loginButton = new Button("Next");
		loginButton.setStyle("-fx-background-color: white; ");
		loginButton.setOnAction(e -> {
			emails.add(newInput.getText());
			listView.getItems().addAll(newInput.getText());
			window.setScene(main);
		});
		//Coloumn, Row//16,30
		GridPane.setConstraints(loginButton, 6, 15);
		
		//Label Title
		Label title = new Label("SmartInvest");
		title.setFont(Font.font("Copperplate Gothic Light", 27));
		//title.setTextFill(Color.web("#0076a3"));
		title.setTextFill(Color.WHITE);
		GridPane.setConstraints(title, 7, 4);

	//BackgroundImage myBI= new BackgroundImage(new Image("/cool-background-designs-26.jpg"),BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
		//     BackgroundSize.DEFAULT);
		
		grid.getChildren().addAll(email, newInput, loginButton, title);
	//	grid.setBackground(new Background(myBI));
		grid.setStyle("-fx-background-color: black");
		Scene launch = new Scene(grid,420,300);
     	//launch.setFill(Color.BLACK);
		window.setScene(launch);
		
		//MAIN GUI:
		
		GridPane layout2 = new GridPane();
		layout2.setPadding(new Insets(10,10,10,10));
		//The spacing vertically is 8 and horizonatlly is 10.
		layout2.setVgap(1);
		layout2.setHgap(1);
		
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

		Label change = new Label("Change(In price):");
		change.setFont(new Font("Arial", 20));
		change.setTextFill(Color.WHITE);
		layout2.setConstraints(change, 40,35);
		
		Label time = new Label("Date: " + date);
		time.setFont(new Font("Arial", 20));
		time.setTextFill(Color.WHITE);
		layout2.setConstraints(time, 2,10);
		
		Label stock = new Label("Symbol " + symbol2);
		stock.setFont(new Font("Arial", 20));
		stock.setTextFill(Color.WHITE);
		layout2.setConstraints(stock, 2,20);
		
		Label graphDisplay = new Label("Graph Display:");
		graphDisplay.setFont(new Font("Arial", 20));
		graphDisplay.setTextFill(Color.WHITE);
		layout2.setConstraints(graphDisplay, 2, 40);
		
		ChoiceBox <String> freq = new ChoiceBox<>();
		freq.getItems().addAll("Daily", "Weekly", "Monthly");
		freq.setValue("Daily");
		//stocksMenu.setStyle("-fx-background-color: blue");
		layout2.setConstraints(freq, 2, 85);
		
		searchSymbol.setOnAction(e -> {
			yahooFinance search = new yahooFinance();
			String symb = symbol.getText();
			try {
				
				symbol2 = search.getSymbol(symb).toString();
				date = search.getDate(symb).toString();
			    open = search.getOpeningPrice(symb).toString();
			    close = search.getClosePrice(symb).toString();
				high = search.getHighestPrice(symb).toString();
			    low = search.getLowestPrice(symb).toString();
				vol = search.getVolume(symb).toString();
				
				stock.setText("Symbol: " + symbol2);
				time.setText("Date: " + date);
				openingPrice.setText("Opening Price: " + open);
				closePrice.setText("Close Price: " + close);
				highestPrice.setText("Highest Price: " + high);
				lowestPrice.setText("Lowest Price: " + low);
				volume.setText("Volume: " + vol);
				
				sendingQuotes send = new sendingQuotes();
		 		send.sendQuotes(symb);
		 		subPlot sendSymbol = new subPlot(symb, frame, symbol2);
				
			} catch (IOException e1) {
				e1.printStackTrace();
	
			}
			
		});
		
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
			
		subPlot save = new subPlot("Test", choice, symbol2);
		save.createPNG(symbol2);
			
		});
		layout2.setConstraints(set, 2, 75);	
		
		Button addEmail = new Button("Add emails");
		layout2.setConstraints(addEmail,2,55);
		addEmail.setOnAction(e -> {
			window.setScene(emailPage);
		});
		
		Button setFollow = new Button("Following");
		layout2.setConstraints(setFollow,2,65);
		setFollow.setOnAction(e -> {
			window.setScene(follow);
		 	followingList.getItems().addAll(followingStocks);
			makePers();
		});
		
		//BackgroundImage BI2= new BackgroundImage(new Image("/lll.jpg"),BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
			//     BackgroundSize.DEFAULT);
		  
		 //Open, high, low, close, volume

		layout2.getChildren().addAll(highestPrice, lowestPrice, time, title2, openingPrice, volume, change, closePrice, graphDisplay
				,freq, set, addEmail, symbol, searchSymbol, stock, setFollow);
		layout2.setStyle("-fx-background-color: black");
		//layout2.setBackground(new Background(BI2));
		//layout2.setBackground(new Background(myBI));
		main = new Scene(layout2,900,900);
		
		window.setOnCloseRequest(e -> {
			e.consume();
			closeProgram();
		});
		
		//Add Email GUI
		
		GridPane layout3 = new GridPane();
		layout3.setPadding(new Insets(10,10,10,10));
		//The spacing vertically is 8 and horizonatlly is 10.
		layout3.setVgap(8);
		layout3.setHgap(10);
		
		layout3.setStyle("-fx-background-color: black");
		
		 emailPage = new Scene(layout3,500,500);
		 
		 Button backButton = new Button("Return");
		 layout3.setConstraints(backButton,0,1);
		 backButton.setOnAction(e -> {
			 window.setScene(main);
		
		 });
		 
		 	listView = new ListView<>();
			listView.getItems();
			listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			
			TextField emailAddition = new TextField("ex.SmartInvest1234@hotmail.com");
			//Coloumn, Row
			GridPane.setConstraints(emailAddition, 1, 1);
			
			Button add = new Button("Add");
			layout3.setConstraints(add,2,1);
			
			add.setOnAction(e -> {
				listView.getItems().addAll(emailAddition.getText());
				
			});
			
			Button send = new Button("Send Now");
			layout3.setConstraints(send,0,4);
			send.setOnAction(e->{
				emails.addAll(listView.getSelectionModel().getSelectedItems());
				sendEmails sendEmail = new sendEmails();
				sendEmail.emailCode(emails, date, symbol2, close, open, high, low, vol);
			});
		 
			layout3.getChildren().addAll(backButton, listView, emailAddition, add, send);
		//	layout3.setBackglayout4.setStyle("-fx-background-color: black");
			layout3.setStyle("-fx-background-color: black");

			GridPane.setConstraints(loginButton, 6, 15);
			
			//FOLLOW SCENE
			
			GridPane layout4 = new GridPane();
			layout4.setPadding(new Insets(10,10,10,10));
			layout4.setVgap(1);
			layout4.setHgap(1);
			
			 Button backButton2 = new Button("Return");
			 layout4.setConstraints(backButton2, 100, 100);
			 backButton2.setOnAction(e -> {
				 window.setScene(main);
			 });
			 
			 
			 Button followButton = new Button("follow");
			 layout4.setConstraints(followButton, 0 , 100);	
			 	followButton.setOnAction(e -> {
			 		if(typeFollowed.getText().length()/3<5){
			 			makePers();
			 		}
			 		else
			 			System.out.println("no");
			 });
			 
			 followingList = new ListView<>();
			 followingList.getItems();
		   	 followingList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		   
		   	 layout4.setConstraints(backButton,0,1);
		   	 
		   	Button removeFollow = new Button ("Remove");
			layout4.setConstraints(removeFollow,20,20);
			removeFollow.setOnAction(e -> {
				followingStocks.remove(followingList.getSelectionModel().getSelectedItems());
				String selectedIndex;
				String text ="";
				selectedIndex = followingList.getSelectionModel().getSelectedItem();
				followingList.getItems().remove(selectedIndex);
				deleteTxt(selectedIndex, "src//Data//persFile.txt");
			});

		   	typeFollowed = new TextField("[Enter stocks symbols here you want to follow]");
		   	layout4.setConstraints(typeFollowed, 100, 0);
		   	 
			layout4.getChildren().addAll(backButton2, followingList, typeFollowed, followButton, removeFollow);
			layout4.setStyle("-fx-background-color: black");
			//layout4.setBackground(new Background(BI2));
			follow = new Scene(layout4,450,450);
			
			window.show();
	
	}
	
	private void makePers() {
	 	followingList.getItems().clear();
		followingStocks.clear();
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("src//Data//persFile.txt", true));
			BufferedReader reader = new BufferedReader(new FileReader("src//Data//persFile.txt"));
			

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
	        		System.out.println(i);
		            if(hs.add(temp.get(i))) 
		                followingStocks.add(line); 
	        }
	            line = reader.readLine(); 
	            
	        } 
	        writer.flush(); 
	          
	        // closing resources 
	        reader.close(); 
	        writer.close(); 

		 	followingList.getItems().addAll(followingStocks);
		 	
		} catch (IOException e1) {}

	}
	
	private void deleteTxt(String selectedIndex, String file) {
		
		int x =0;
		ArrayList<String>temp = new ArrayList<String>();

		HashSet<String> hash = new HashSet<String>(); 
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			
			for (String line; (line = reader.readLine()) != null; x++) {
				System.out.println("asdfasdfadsf");
				 if (!line.equals(selectedIndex)) {
					temp.add(line);
					if(!hash.add(temp.get(x-1)))
						temp.remove(x);
				 }
			}
			for(int i=0;i<temp.size();i++) {
				System.out.println(temp.get(i)+"asd");
				writer.append(temp.get(i));
				writer.newLine();
			}
			
			temp.clear();
			writer.close();
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void closeProgram() {
		Boolean anwser = closeProgram.display("", "Sure you want to exit?");
		if (anwser)
			window.close();
		createFile();
		
	}
	 
	 public void drawGraph (int change, String symb) {
		 subPlot chart = new subPlot("ABX", change, symb);
			chart.pack();
		    RefineryUtilities.centerFrameOnScreen(chart);
		    chart.setVisible(true);
	 }
	 
	 public void createFile() {
		 signals = new String[3];
		 String [] type = new String[3];
		 type[0]= "Monthly - 5 years.txt";
		 type[1]= "Weekly - 2 years.txt";
		 type[2]= "Daily - 1 year.txt";
		 
		 createFiles c = new createFiles(followingStocks);
		 
		 
		 for(int i=0;i<followingStocks.size();i++) {
			 for(int j=0;j<3;j++) {
				 f = new formulas("src//Data//"+followingStocks.get(i)+" "+ type[j]);
				 signals s = new signals(f.values);
				 signals[j] = s.makeSignal();
			 }
			 c.createTxtFile(signals, f.values);
			 c.createImageFile();
		 }
	 }
	 
	 private BufferedImage loadBufferedImage(String string)
		{
		    try
		    {
		        BufferedImage bi = ImageIO.read(this.getClass().getResource(string));
		        return bi;
		    } catch (IOException e)
		    {
		        e.printStackTrace();
		    }
		    return null;
		}
}