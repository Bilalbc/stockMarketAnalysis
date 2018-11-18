package stockAnalysisProgram;

import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
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

import stockAnalysisProgram.formulas;
import stockAnalysisProgram.readFile;

public class subPlot extends ApplicationFrame {

    public subPlot(final String title) {

        super(title);
        final JFreeChart chart = createCombinedChart();
        final ChartPanel panel = new ChartPanel(chart, true, true, true, false, true);
        panel.setPreferredSize(new java.awt.Dimension(600, 600));
        setContentPane(panel);

    }

	private String fileLoc;
	private String title;
	private int period = 0;
	private formulas f;
	private int frame = 20;
	
    private JFreeChart createCombinedChart() {
    	readFile r;

		fileLoc = "C:\\Users\\haseebchaudhry\\git\\repository\\stockAnalysisProgram\\src\\Data\\Monthly - 5 years.txt";
		r = new readFile(fileLoc);
		f = new formulas(fileLoc);
		r.makeArray(); 
		f.createValues(r.getArray());
		f.createDateArray(r.getArray());
		f.createSMA(frame);
			period = f.values.length;
			title = "monthly - 5 years";
    	
        // create subplot 1...
        final XYDataset data1 = createDataset1(f.getDates(), f.getValues());
        final XYItemRenderer renderer1 = new StandardXYItemRenderer();
        final NumberAxis rangeAxis1 = new NumberAxis("Range 1");
        final XYPlot subplot1 = new XYPlot(data1, null, rangeAxis1, renderer1);
        subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        
       
   
        // create subplot 2...
        final XYDataset data2 = createDataset2();
        final XYItemRenderer renderer2 = new StandardXYItemRenderer();
        final NumberAxis rangeAxis2 = new NumberAxis("Range 2");
        final XYPlot subplot2 = new XYPlot(data2, null, rangeAxis2, renderer2);
        subplot2.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);
        
        //create subplot 3...
        final XYDataset data3 = createDataset3();
        final XYItemRenderer renderer3 = new StandardXYItemRenderer();
        final NumberAxis rangeAxis3 = new NumberAxis("Range 2");
        final XYPlot subplot3 = new XYPlot(data3, null, rangeAxis3, renderer3);
        
        subplot2.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);
        
   
	    //formating xAxis as months
	    DateAxis xAxis = new DateAxis ("Date");
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

        // return a new chart containing the overlaid plot...
        return new JFreeChart("CombinedDomainXYPlot Demo",
                              JFreeChart.DEFAULT_TITLE_FONT, plot, true);

    }

    private XYDataset createDataset1(Date d [], double x [][])  {
    	
    	  final TimeSeries trend = new TimeSeries("Data");  
	      final TimeSeries bollingerUpper = new TimeSeries("Bollinger");
	      final TimeSeries bollingerMiddle = new TimeSeries("Bollinger"); 
	      final TimeSeries bollingerLower = new TimeSeries("Bollinger"); 
	      f.createBollinger(frame);
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

    private XYDataset createDataset2() {

        // create dataset 2...
        final XYSeries series2 = new XYSeries("Series 3");

        series2.add(10.0, 16853.2);
        series2.add(20.0, 19642.3);
        series2.add(30.0, 18253.5);
        series2.add(40.0, 15352.3);
        series2.add(50.0, 13532.0);
        series2.add(100.0, 12635.3);
        series2.add(110.0, 13998.2);
        series2.add(120.0, 11943.2);
        series2.add(130.0, 16943.9);
        series2.add(140.0, 17843.2);
        series2.add(150.0, 16495.3);
        series2.add(160.0, 17943.6);
        series2.add(170.0, 18500.7);
        series2.add(180.0, 19595.9);

        return new XYSeriesCollection(series2);

  }
    
    private XYDataset createDataset3() {

        // create dataset 3...
        final XYSeries series2 = new XYSeries("Series 3");

        series2.add(10.0, 16853.2);
        series2.add(20.0, 19642.3);
        series2.add(30.0, 18253.5);
        series2.add(40.0, 15352.3);
        series2.add(50.0, 13532.0);
        series2.add(100.0, 12635.3);
        series2.add(110.0, 13998.2);
        series2.add(120.0, 11943.2);
        series2.add(130.0, 16943.9);
        series2.add(140.0, 17843.2);
        series2.add(150.0, 16495.3);
        series2.add(160.0, 17943.6);
        series2.add(170.0, 18500.7);
        series2.add(180.0, 19595.9);

        return new XYSeriesCollection(series2);

  }
    public static void main(final String[] args) {

        final subPlot demo = new subPlot("CombinedDomainXYPlot Demo");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }

}