import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class MACD extends JFrame {

	String fileLoc;
	readFile r;
	formulas f;
	
   public MACD(String title) {
      super(title);
      
      	
		fileLoc = "C:\\Users\\bilalc\\git\\repository\\Stock Market Anaysis\\src\\Data\\daily - 6 months.txt";
		r = new readFile(fileLoc);
		f = new formulas(fileLoc);
		r.makeArray(); 
		f.createValues(r.getArray());
		f.createDateArray(r.getArray());
		
      // Create Category plot
		CategoryPlot barPlot = new CategoryPlot();
		XYPlot plot = new XYPlot();
      
      
      // Add the first dataset and render as bar
		CategoryItemRenderer lineRenderer = new LineAndShapeRenderer();
      
		org.jfree.chart.JFreeChart xyLineChart = ChartFactory.createXYLineChart(
				title,
				"Month","Price",
				createLineDataset(f.getDates(), f.getValues()),
				PlotOrientation.VERTICAL,
				true,true,false);
  	
  	  	plot = xyLineChart.getXYPlot();
  		
  	  	JFreeChart barChart = ChartFactory.createBarChart(
  	  			"CAR USAGE STATIStICS", 
  	  			"Category", "Score", 
  	  			createDataset(f.getDates(),f.getValues()),
  	  			PlotOrientation.VERTICAL, 
  	  			true, true, false);
  	  
  	  	barPlot = barChart.getCategoryPlot();
  	  	
  	  	DateAxis xAxis = new DateAxis ("Date");
	    //from start of array(2013) to end of array(2018)
  	  	xAxis.setRange(f.dateArray[10], f.dateArray[f.dateArray.length-1]);
  	  	xAxis.setDateFormatOverride(new SimpleDateFormat("MMM"));
	    xAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
	    
	    plot.setDomainAxis(xAxis);
	//    barPlot.setDomainAxis(xAxis);
    //  plot.setRenderer(lineRenderer);

      // Add the second dataset and render as lines
      CategoryItemRenderer barRenderer = new BarRenderer();
     
      barPlot.setDataset(1, createDataset(f.getDates(), f.getValues()));
      barPlot.setRenderer(1, barRenderer);

      // Set Axis
      barPlot.setDomainAxis(new CategoryAxis("Time"));
      plot.setRangeAxis(new NumberAxis("Value"));

      JFreeChart chart = new JFreeChart(barPlot);
      chart.setTitle("Combined Bar And Line Chart | BORAJI.COM");

      ChartPanel panel = new ChartPanel(chart);
      setContentPane(panel);
   }

   private TimeSeriesCollection createLineDataset(Date d [], double x [][]) {

      // First Series
	  TimeSeriesCollection dataset = new TimeSeriesCollection();
      TimeSeries trend = new TimeSeries("Data");
      
      // Second Series
      TimeSeries trend2 = new TimeSeries("data");
      for(int i=0;i<x.length;i++)
      trend2.add(new Day(d[i]) , x[i][4]);

      dataset.addSeries(trend2);
      dataset.addSeries(trend);

      return dataset;
   }
   
   private DefaultCategoryDataset createDataset(Date d [], double x [][]) {
	   DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	   
	   String series2 ="data";
	   for(int i=0;i<x.length;i++){
		   dataset.addValue(x[i][4], series2, d[i]);	   
	   }
	   
	   
	   return dataset;
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> {
         MACD example = new MACD(
               "Line Chart and Bar chart Example");
         example.setSize(800, 400);
         example.setLocationRelativeTo(null);
         example.setVisible(true);
      });
   }
}