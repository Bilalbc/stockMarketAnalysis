package stockMarketAnalysis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class subPlot extends ApplicationFrame {

	public String fileLoc;
	private String fileName ="src//Data";
	private String title;
	private int period = 0;
	private formulas f;
	private int frame = 20;
	
	//declaring subplots
	XYPlot subplot1;
	XYPlot subplot2;
	XYPlot subplot3;
	
	//initialing size of window
	private static int WIDTH = 600;
	private static int HEIGHT = 650;
	
    public subPlot(String applicationTitle, int choice, String symbol) {
    	
    		super(applicationTitle);
    		JFreeChart chart = createChart(choice, symbol);
    	    ChartPanel panel = new ChartPanel(chart, true, true, true, true, true);
    	    panel.setPreferredSize(new java.awt.Dimension(WIDTH, HEIGHT));
    	    setContentPane(panel);
    		setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
    
//-----------------------------------------------------------------------------------------------------------//
    /*
     * user chooses what type of graph that they want to see, and the method determines the relevant values
     * and displays the main graph as well as two indicators 
     * 
     * returns the the graph to be embedded, or shown as a popup
     * 
     * takes two parameters, a choice, which determines what type graph is displayed
     * and a symbol so act as a title for the graph
     */
    public JFreeChart createChart(int choice, String symbol) {    	
    	
    	//initializes relevant information based on the user's choice of graph 
    		switch (choice) {
    	      
    		case 1: 
    			fileLoc = fileName+"\\"+symbol+" Daily - 1 Year.txt";//sets file path 
    			f = new formulas(fileLoc);//initializes formula class based on file path 
    			period = f.fileSize();//created period
      				title = "Daily"; //sets title
      				break;
    		case 2: 
    			fileLoc = fileName+"\\"+symbol+" Weekly - 2 Years.txt";
    			f = new formulas(fileLoc);
    			period = f.fileSize();
    	  			title = "Weekly";
    	  			break;
    		case 3: 
    			fileLoc = fileName+"\\"+symbol+" Monthly - 5 Years.txt";
    			f = new formulas(fileLoc);
    			period = f.fileSize();
      				title = "Monthly";
      				break;
    	}
    		
    	//creating individual subplots 
    		
	        //plot settings
	    	//create data points from arrays 
	        final XYDataset data1 = createDataset1(f.getDates(), f.getValues());
	        final XYItemRenderer renderer1 = new StandardXYItemRenderer();
	        //set line colors
	        	renderer1.setSeriesPaint( 0 , Color.BLACK );
	        	renderer1.setSeriesPaint( 1 , Color.RED );
	        	renderer1.setSeriesPaint( 2 , Color.BLUE);
	        	renderer1.setSeriesPaint( 3 , Color.RED );
	
	        //initializes range of graph 
	        final NumberAxis rangeAxis1 = new NumberAxis("");
	        rangeAxis1.setRange(f.getLowest()-5, f.getHighest()+3);
	        rangeAxis1.setTickUnit(new NumberTickUnit(1.00));
	        
	        //creates subplot1 from data points and plot settings
	        subplot1 = new XYPlot(data1, null, rangeAxis1, renderer1);
	        	subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
	        	//enable viewing crosshair
	        	subplot1.setDomainCrosshairVisible(true);
	        	subplot1.setRangeCrosshairVisible(true);
	        	
	        
	        final XYDataset data2 = createDataset2(f.getDates(), f.getValues());
	        final XYItemRenderer renderer2 = new StandardXYItemRenderer();
	        	renderer2.setSeriesPaint(0, Color.RED);
	        	renderer2.setSeriesPaint(1, Color.BLUE);
	        	renderer2.setSeriesPaint(2, Color.BLACK);
	        final NumberAxis rangeAxis2 = new NumberAxis("");
	        subplot2 = new XYPlot(data2, null, rangeAxis2, renderer2);
	        	subplot2.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
	        
	        //create subplot 3...
	        final XYDataset data3 = createDataset3(f.getDates(), f.getValues());
	        final XYItemRenderer renderer3 = new StandardXYItemRenderer();
	        	renderer3.setSeriesPaint( 0 , Color.BLACK );
		    	renderer3.setSeriesStroke( 0 , new BasicStroke( 1f ) );
	        final NumberAxis rangeAxis3 = new NumberAxis("");
	        subplot3 = new XYPlot(data3, null, rangeAxis3, renderer3);
	        	subplot3.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
	        

        
	    //formating xAxis as months
	    DateAxis xAxis = new DateAxis ("");
	    //from start of array(2013) to end of array(2018)
	    xAxis.setRange(f.dateArray[frame], f.dateArray[f.dateArray.length-1]);
	    xAxis.setDateFormatOverride(new SimpleDateFormat("MMM"));
	    xAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
	   	    
        // parent plot...
        final CombinedDomainXYPlot plot = new CombinedDomainXYPlot(new NumberAxis("Domain"));
        plot.setGap(10.0);
        
        // add the subplots...
        plot.add(subplot1, 20);
        plot.add(subplot2, 10);
        plot.add(subplot3, 10);
        plot.setDomainAxis(xAxis);
        plot.setOrientation(PlotOrientation.VERTICAL);

        JFreeChart chart = new JFreeChart("",
                JFreeChart.DEFAULT_TITLE_FONT, plot, true);
       
        //create png Images based on the grapg 
		createPNG(symbol);	

		//generate signals based off values
		signals s = new signals(f.values);
        
		return chart;
    }

    //-----------------------------------------------------------------------------------------------------------//
    /*
     * method to create gather data points for plotting on the graph 
     */
    private XYDataset createDataset1(Date d [], double x [][])  {
    	
    	//time series plots in order to plot data points to their respective time/date on the x axis
    	  final TimeSeries trend = new TimeSeries("Data");  
	      final TimeSeries bollingerUpper = new TimeSeries("Bollinger");
	      final TimeSeries SMA = new TimeSeries("SMA(20)"); 
	      final TimeSeries bollingerLower = new TimeSeries("Bollinger"); 
	      
	      // adding the data points to the time series  and their respective points on the x-axis
	      //adding boillinger bands and SMA to plot
	      for(int i=20;i<period-1;i++) {
	    		  bollingerLower.addOrUpdate(new Day(d[i]), x[i][8]);
	    		  SMA.addOrUpdate(new Day(d[i]), x[i][7]);   
	    		  bollingerUpper.addOrUpdate(new Day(d[i]), x[i][6]);   

	      }
	      //adding main price line to graph 
	      for(int i=20;i<period-1;i++)
	    	  trend.addOrUpdate(new Day(d[i]), x[i][4] );
      
	      //adding all lines to dataset 
	      TimeSeriesCollection dataset = new TimeSeriesCollection();          
	      dataset.addSeries( trend ); 
	      dataset.addSeries(bollingerLower);
	      dataset.addSeries(SMA);  
	      dataset.addSeries(bollingerUpper);  

	      //returning dataset 
	      return dataset;
    }

    //-----------------------------------------------------------------------------------------------------------//
    /*
     * method to create datapoints for the second subplot
     */
    private XYDataset createDataset2(Date d [], double x [][]) {
    	
    	f.createMACD();
    	f.createSignalLine();
    	
    	//initializing time series
        final TimeSeries MACD = new TimeSeries("MACD");
        final TimeSeries signalLine = new TimeSeries("Signal Line");
        final TimeSeries zeroLine = new TimeSeries("");
        
        //adding datapoints
        for(int i=26;i<period;i++)
        	MACD.addOrUpdate(new Day(d[i]), x[i][9]);
        
        for(int i=26;i<period;i++)
        	signalLine.addOrUpdate(new Day(d[i]), x[i][10]);
        
        for(int i=0;i<period;i++)
        	zeroLine.addOrUpdate(new Day(d[i]), 0);
        
        TimeSeriesCollection dataset = new TimeSeriesCollection(); 
        //making dataset
        dataset.addSeries(MACD);
        dataset.addSeries(signalLine);
        dataset.addSeries(zeroLine);
        
        //return data set 
        return dataset;
  }
    
    //-----------------------------------------------------------------------------------------------------------//
    /*
     * method to create data points for subplot 3
     */
    private XYDataset createDataset3(Date d [], double x [][]) {

    	//initializing time series
    	f.createK();
    	f.createFS();
        final TimeSeries K = new TimeSeries("%k");
        final TimeSeries D = new TimeSeries("%D");
        final TimeSeries zeroLine = new TimeSeries("");

        //adding datapoints 
        for(int i=20;i<period;i++)
        	K.addOrUpdate(new Day(d[i]), x[i][11]);
        
        for(int i=20;i<period;i++)
        	D.addOrUpdate(new Day(d[i]), x[i][12]);
        	
        for(int i=0;i<period;i++)
        	zeroLine.addOrUpdate(new Day(d[i]), 0);
        
        
        TimeSeriesCollection dataset = new TimeSeriesCollection(); 
        //creating dataset 
        dataset.addSeries(zeroLine);
        dataset.addSeries(K);
        dataset.addSeries(D);
        
        //return dataset 
        return dataset;

  }
    //-----------------------------------------------------------------------------------------------------------//
    /*
     * method to create PNG files based on jfree charts 
     */
    public void createPNG(String symbol) {
    	//creating individual jfreecharts based from each subplot
    	JFreeChart chart = new JFreeChart(title,
                JFreeChart.DEFAULT_TITLE_FONT, subplot1, true);
        
        JFreeChart indicator1 = new JFreeChart(title,
                JFreeChart.DEFAULT_TITLE_FONT, subplot2, true);
        
        JFreeChart indicator2 = new JFreeChart(title,
                JFreeChart.DEFAULT_TITLE_FONT, subplot3, true);
        //saving each chart as a png with specific file names for finding 
        try {
			ChartUtilities.saveChartAsPNG(new File("src\\Charts\\"+symbol +" "+ title+".png"), chart, 500, 300);
			ChartUtilities.saveChartAsPNG(new File("src\\Charts\\"+symbol +" "+ title+" - MACD.png"), indicator1, 400, 200);
			ChartUtilities.saveChartAsPNG(new File("src\\Charts\\"+symbol +" "+ title+" - FS.png"), indicator2, 400, 200);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
   

}