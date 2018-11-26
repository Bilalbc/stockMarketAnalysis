

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class subPlot extends ApplicationFrame {

	private String fileLoc;
	private String title;
	private int period = 0;
	private formulas f;
	private int frame = 20;
	
	LinkedList subTitles = new LinkedList();
	
	XYPlot subplot1;
	XYPlot subplot2;
	XYPlot subplot3;
	
	private static int WIDTH = 600;
	private static int HEIGHT = 650;
	
    public subPlot(String applicationTitle, int choice ) {
    	
    		super(applicationTitle);
    		JFreeChart chart = createChart(choice);
    	    ChartPanel panel = new ChartPanel(chart, true, true, true, true, true);
    	    panel.setPreferredSize(new java.awt.Dimension(WIDTH, HEIGHT));
    	    setContentPane(panel);
    		setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
    /*
     * choice the graph display that the user chose to view the data in
     * returns the the gr
     */
    public JFreeChart createChart(int choice) {
    		readFile r;
    		
    		switch (choice) {
    	      
    		case 1: 
    			fileLoc = "src\\Data\\Daily - 6 months.txt";
    			r = new readFile(fileLoc);
    			f = new formulas(fileLoc);
    			r.makeArray(); 
    			f.createValues(r.getArray());
    			f.createDateArray(r.getArray());
    			f.createBollinger(frame);
    			//f.createSMA(frame);
      				period = 130;
      				title = "Daily - 6 Months"; 
      				break;
    		case 2: 
    			fileLoc = "src\\Data\\Weekly - 2 years.txt";
    			r = new readFile(fileLoc);
    			f = new formulas(fileLoc);
    			r.makeArray(); 
    			f.createValues(r.getArray());
    			f.createDateArray(r.getArray());
    			f.createBollinger(frame);
    			//f.createSMA(frame);
    	  			period = 106;
    	  			title = "Weekly - 2 Years";
    	  			break;
    		case 3: 
    			fileLoc = "src\\Data\\Monthly - 5 years.txt";
    			r = new readFile(fileLoc);
    			f = new formulas(fileLoc);
    			r.makeArray(); 
    			f.createValues(r.getArray());
    			f.createDateArray(r.getArray());
    			f.createBollinger(frame);
    			//f.createSMA(frame);
      				period = f.values.length;
      				title = "Monthly - 5 Years";
      				break;
    	}
        // create subplot 1...
        final XYDataset data1 = createDataset1(f.getDates(), f.getValues());
        final XYItemRenderer renderer1 = new StandardXYItemRenderer();
        	renderer1.setSeriesPaint( 0 , Color.BLACK );
        	renderer1.setSeriesPaint( 1 , Color.RED );
        	renderer1.setSeriesPaint( 2 , Color.RED );
        	renderer1.setSeriesPaint( 3 , Color.RED );
        final NumberAxis rangeAxis1 = new NumberAxis("");
        subplot1 = new XYPlot(data1, null, rangeAxis1, renderer1);
        	subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
   
        // create subplot 2...
        final XYDataset data2 = createDataset2(f.getDates(), f.getValues());
        final XYItemRenderer renderer2 = new StandardXYItemRenderer();
        	renderer2.setSeriesPaint(0, Color.RED);
        	renderer2.setSeriesPaint(1, Color.BLUE);
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
        
        NumberAxis yAxis = new NumberAxis("Price");
        
        
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
        plot.setRangeAxis(yAxis);
        plot.setDomainAxis(xAxis);
        plot.setOrientation(PlotOrientation.VERTICAL);

        JFreeChart chart = new JFreeChart("STOCK MARKET ANALYSIS",
                JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        
       // chart.setBackgroundPaint(Color.BLACK);
        chart.setBorderPaint(Color.GREEN);
        chart.clearSubtitles();
        
        TextTitle subtitle1 = new TextTitle(title);
		chart.addSubtitle(subtitle1);
		
		//subTitles.ad
		chart.setSubtitles(subTitles);
        
        
        createPNG();
        
		return chart;
    }

    private XYDataset createDataset1(Date d [], double x [][])  {
    	
    	  final TimeSeries trend = new TimeSeries("Data");  
	      final TimeSeries bollingerUpper = new TimeSeries("Bollinger");
	      final TimeSeries bollingerMiddle = new TimeSeries("Bollinger"); 
	      final TimeSeries bollingerLower = new TimeSeries("Bollinger"); 
	      
	     // XYSeriesCollection xySeriesCollection = new XYSeriesCollection();

	      for(int i=20;i<period;i++) {
	    	
	    		  bollingerLower.addOrUpdate(new Day(d[i]), x[i][8]);
	    		  bollingerMiddle.addOrUpdate(new Day(d[i]), x[i][7]);   
	    		  bollingerUpper.addOrUpdate(new Day(d[i]), x[i][6]);   

	      }
      for(int i=20;i<period;i++)
    	  trend.add(new Day(d[i]), x[i][4] );
      
      TimeSeriesCollection dataset = new TimeSeriesCollection();          
      dataset.addSeries( trend ); 
      dataset.addSeries(bollingerLower);
      dataset.addSeries(bollingerMiddle);  
      dataset.addSeries(bollingerUpper);  
	  
      //f.values.length
     
      return dataset;
    }

    private XYDataset createDataset2(Date d [], double x [][]) {

        // create dataset 2...
    	f.createMACD();
    	f.createSignalLine();
        final TimeSeries MACD = new TimeSeries("MACD");
        final TimeSeries signalLine = new TimeSeries("Signal Line");

        for(int i=26;i<period;i++)
        	MACD.addOrUpdate(new Day(d[i]), x[i][9]);
        
        for(int i=26;i<period;i++)
        	signalLine.addOrUpdate(new Day(d[i]), x[i][10]);
        
        TimeSeriesCollection dataset = new TimeSeriesCollection(); 
        
        dataset.addSeries(MACD);
        dataset.addSeries(signalLine);
        
        return dataset;
  }
    
    private XYDataset createDataset3(Date d [], double x [][]) {

        // create dataset 3...
        final TimeSeries MACD = new TimeSeries("MACD");

        for(int i=24;i<period;i++)
        	MACD.addOrUpdate(new Day(d[i]), x[i][9]);
        
        TimeSeriesCollection dataset = new TimeSeriesCollection(); 
        
        dataset.addSeries(MACD);
        
        return dataset;

  }
    
    public void createPNG() {
    	JFreeChart chart = new JFreeChart(title,
                JFreeChart.DEFAULT_TITLE_FONT, subplot1, true);
        
        JFreeChart indicator1 = new JFreeChart(title,
                JFreeChart.DEFAULT_TITLE_FONT, subplot2, true);
        
        JFreeChart indicator2 = new JFreeChart(title,
                JFreeChart.DEFAULT_TITLE_FONT, subplot3, true);
       // ChartUtilities.applyCurrentTheme(chart);
        
        try {
			ChartUtilities.saveChartAsPNG(new File("src\\Charts\\"+title+".png"), chart, 500, 300);
			ChartUtilities.saveChartAsPNG(new File("src\\Charts\\"+title+" - MACD.png"), indicator1, 400, 200);
			ChartUtilities.saveChartAsPNG(new File("src\\Charts\\"+title+" - FS.png"), indicator2, 400, 200);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public static void main(final String[] args) {
  	  Scanner console = new Scanner(System.in);
	     int choice = console.nextInt();
	     
	  subPlot chart = new subPlot(
      "ABX" ,
      choice);

	  chart.pack();
	  chart.setResizable(false);
	  RefineryUtilities.centerFrameOnScreen( chart );
	  chart.setVisible( true );
   
	  console.close();
}

}
