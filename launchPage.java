
import java.awt.Container;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import com.sun.prism.BasicStroke;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.paint.Color;


public class launchPage extends Application{
	Stage window;
	Scene launch, main, emailPage;
	Label timInterval;
	ListView<String> listView;

	
	public void start(Stage primaryStage) throws Exception {
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
		email.setTextFill(Color.web("#0076a3"));
		email.setFont(new Font("Arial",15));
		//Top left
		GridPane.setConstraints(email,6,14);
		//Name input
		//Textfield
		TextField newInput = new TextField("ex.SmartInvest1234@hotmail.com");
		GridPane.setConstraints(newInput, 7, 14);
		
		Button loginButton = new Button("Next");
		loginButton.setOnAction(e -> {
			System.out.println(newInput.getText());
			listView.getItems().addAll(newInput.getText());
			window.setScene(main);
		});
		//Coloumn, Row//16,30
		GridPane.setConstraints(loginButton, 6, 15);
		//Label Title
		Label title = new Label("SmartInvest");
		title.setFont(new Font("Arial", 27));
		title.setTextFill(Color.web("#0076a3"));
		GridPane.setConstraints(title, 7, 4);
		
		grid.getChildren().addAll(email, newInput, loginButton, title);
		grid.setStyle("-fx-background-color: black");
		Scene launch = new Scene(grid,400,300);
		launch.setFill(Color.BLACK);
		window.setScene(launch);
		
		//MAIN GUI:
		
		GridPane layout2 = new GridPane();
		layout2.setPadding(new Insets(10,10,10,10));
		//The spacing vertically is 8 and horizonatlly is 10.
		layout2.setVgap(1);
		layout2.setHgap(1);
		
		Label title2 = new Label("SmartInvest");
		title2.setFont(new Font("Arial", 40));
		title2.setTextFill(Color.web("#0076a3"));
		layout2.setConstraints(title2, 2,4);
		

		Label openingPrice = new Label("Opening Price:");
		openingPrice.setFont(new Font("Arial", 20));
		openingPrice.setTextFill(Color.web("#0076a3"));
		layout2.setConstraints(openingPrice, 2,25);
		
		Label highestPrice = new Label("Highest Price:");
		highestPrice.setFont(new Font("Arial", 20));
		highestPrice.setTextFill(Color.web("#0076a3"));
		layout2.setConstraints(highestPrice, 2,30);
		
		Label lowestPrice = new Label("Lowest Price:");
		lowestPrice.setFont(new Font("Arial", 20));
		lowestPrice.setTextFill(Color.web("#0076a3"));
		layout2.setConstraints(lowestPrice, 2,35);
		
		Label closePrice = new Label("Close Price:");
		closePrice.setFont(new Font("Arial", 20));
		closePrice.setTextFill(Color.web("#0076a3"));
		layout2.setConstraints(closePrice, 40,25);
		
		Label volume = new Label("Volume(Number of trades):");
		volume.setFont(new Font("Arial", 20));
		volume.setTextFill(Color.web("#0076a3"));
		layout2.setConstraints(volume, 40,30);
		
		Label change = new Label("Change(In price):");
		change.setFont(new Font("Arial", 20));
		change.setTextFill(Color.web("#0076a3"));
		layout2.setConstraints(change, 40,35);
		
		Label time = new Label("Date:");
		time.setFont(new Font("Arial", 20));
		time.setTextFill(Color.web("#0076a3"));
		layout2.setConstraints(time, 2,10);
		
		Label graphDisplay = new Label("Graph Display:");
		graphDisplay.setFont(new Font("Arial", 20));
		graphDisplay.setTextFill(Color.web("#0076a3"));
		layout2.setConstraints(graphDisplay, 2, 40);
		
		ChoiceBox <String> stocksMenu = new ChoiceBox<>();
		stocksMenu.getItems().addAll("GLD", "SLV", "ABX");
		stocksMenu.setValue("SLV");
		//stocksMenu.setStyle("-fx-background-color: blue");
		layout2.setConstraints(stocksMenu, 40, 50);
		
		ChoiceBox <String> freq = new ChoiceBox<>();
		freq.getItems().addAll("Daily", "Weekly", "Monthly");
		freq.setValue("Daily");
		//stocksMenu.setStyle("-fx-background-color: blue");
		layout2.setConstraints(freq, 40, 51);
		
		Button set = new Button("Draw Graoh");
		layout2.setConstraints(set, 30, 50);	
		
		Button addEmail = new Button("Add emails");
		layout2.setConstraints(addEmail,30,60);
		addEmail.setOnAction(e -> {
			window.setScene(emailPage);
		});
	
		
		  readFile r = new readFile();
		  r.makeArray(); 
		  formulas f = new formulas();
		  f.createValues(r.getArray());
		  f.createDateArray(r.getArray());
		  
		 
		  SwingNode testGraph = GraphTest();
		  layout2.setConstraints(testGraph,2,50);
		  
		 //Open, high, low, close, volume

		
		layout2.getChildren().addAll(testGraph, highestPrice, lowestPrice, time, title2, openingPrice, volume, change, closePrice, graphDisplay, stocksMenu
				, freq, set, addEmail);
		layout2.setStyle("-fx-background-color: black");
		main = new Scene(layout2,1200,1200);
		
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
				buttonClicked();
			});
		 
			layout3.getChildren().addAll(backButton, listView, emailAddition, add, send);
			
			
			window.show();
	
	}
	

	private void closeProgram() {
		Boolean anwser = closeProgram.display("", "Sure you want to exit?");
		if (anwser)
			window.close();
	}
	
	
	private void buttonClicked() {
		String message = "";
		ObservableList<String> emails;
		emails = listView.getSelectionModel().getSelectedItems();
		
		for (String e:emails) {
			message += e + "\n";
		}
		
		System.out.println("Emails sent to:\n" + message);
		
	}
	
	private XYDataset createDataset(Date d [], double f [][]) {
        final TimeSeries trend = new TimeSeries("Data");

    for(int i=0;i<100;i++)
        trend.add(new Day(d[i]), f[i][3] );

    TimeSeriesCollection dataset = new TimeSeriesCollection();
    dataset.addSeries( trend ); 

    //f.values.length

    return dataset;

		
	}
	
	private SwingNode GraphTest () {
		 readFile r = new readFile();
		  r.makeArray(); 
		  formulas f = new formulas();
		  f.createValues(r.getArray());
		  f.createDateArray(r.getArray());
		  
		  org.jfree.chart.JFreeChart xyLineChart = ChartFactory.createXYLineChart(
	                "test",
	                "Month","Price",
	                createDataset(f.getDates(), f.getValues()),
	                PlotOrientation.VERTICAL,
	                true,true,false);
	        final XYPlot plot = xyLineChart.getXYPlot( );

	        //formating xAxis as months
	        DateAxis xAxis = new DateAxis ("Date");
	        //from start of array(2013) to end of array(2018)
	        xAxis.setRange(f.dateArray[0], f.dateArray[f.dateArray.length-1]);
	        xAxis.setDateFormatOverride(new SimpleDateFormat("MMM"));
	        xAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);

	        //drawing lines in graph
	        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
	        renderer.setBaseShapesVisible(false);
	        

	        ChartPanel chartPanel = new ChartPanel( xyLineChart );
	        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
	 

	        plot.setDomainAxis(xAxis);
	        plot.setRenderer( renderer );
		  
		  final SwingNode chartSwingNode = new SwingNode();
		  chartSwingNode.setContent(
			      new ChartPanel(
			    	xyLineChart
			      )      
			    );
		  return chartSwingNode;
	
	
	}
}
	

