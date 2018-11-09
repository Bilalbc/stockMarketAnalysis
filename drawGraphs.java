
import org.jfree.chart.ChartPanel;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

public class drawGraphs extends JFrame  {

	private String fileLoc;
	private String title;
	private int period = 0;
	private formulas f;
	
	public drawGraphs( String applicationTitle, int choice ) {
		super(applicationTitle);
		setSize(600, 600);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setTitle("Stock Market Analysis");
		
		Container c = getContentPane();
		
		readFile r;
		 
		switch (choice) {
	      
		case 1: 
			fileLoc = "C:\\CompSci gr 12\\Summative\\Data\\daily - 6 months.txt";
			r = new readFile(fileLoc);
			f = new formulas(fileLoc);
			r.makeArray(); 
			f.createValues(r.getArray());
			f.createDateArray(r.getArray());
  				period = 130;
  				title = "daily - 6 months"; 
  				break;
		case 2: 
			fileLoc = "C:\\CompSci gr 12\\Summative\\Data\\weekly - 2 years.txt";
			r = new readFile(fileLoc);
			f = new formulas(fileLoc);
			r.makeArray(); 
			f.createValues(r.getArray());
			f.createDateArray(r.getArray());
	  			period = 106;
	  			title = "weekly - 2 years";
	  			break;
		case 3: 
			fileLoc = "C:\\CompSci gr 12\\Summative\\Data\\monthly - 5 years.txt";
			r = new readFile(fileLoc);
			f = new formulas(fileLoc);
			r.makeArray(); 
			f.createValues(r.getArray());
			f.createDateArray(r.getArray());
  				period = f.values.length;
  				title = "monthly - 5 years";
  				break;
	}
		org.jfree.chart.JFreeChart xyLineChart = ChartFactory.createXYLineChart(
				title,
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
	    renderer.setSeriesPaint( 0 , Color.RED );
	    renderer.setSeriesStroke( 0 , new BasicStroke( 1f ) );
	    renderer.setBaseShapesVisible(false);
	
		ChartPanel chartPanel = new ChartPanel( xyLineChart );
		chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
		c.add(chartPanel);
		
		
		plot.setDomainAxis(xAxis);
		plot.setRenderer( renderer ); 
      
		setVisible(true);
		setContentPane( chartPanel );
   }

	private XYDataset createDataset(Date d [], double f [][]) {
	      final TimeSeries trend = new TimeSeries("Data");       
	      
      for(int i=0;i<period;i++)
    	  trend.add(new Day(d[i]), f[i][3] );
      
      TimeSeriesCollection dataset = new TimeSeriesCollection();          
      dataset.addSeries( trend ); 
	  
      //f.values.length
     
      return dataset;
   }
   
   public static void main( String[ ] args ) {
	  Scanner console = new Scanner(System.in);
	     int choice = console.nextInt();
	     
	  drawGraphs chart = new drawGraphs(
         "ABX" ,
         choice);
	   
      chart.pack();
      RefineryUtilities.centerFrameOnScreen( chart );
      chart.setVisible( true );
      
	  console.close();
   }
}

