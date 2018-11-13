import org.jfree.chart.ChartPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class drawGraphs extends JFrame  {

   public drawGraphs( String applicationTitle , String chartTitle ) {
      super(applicationTitle);
      setSize(600, 600);
      setResizable(false);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setLayout(null);
      setTitle("Pig Latin GUI");
		
      Container c = getContentPane();
      c.setBackground(new Color(0, 30, 51));
      
	  readFile r = new readFile();
	  r.makeArray(); 
	  formulas f = new formulas();
	  f.createValues(r.getArray());
	 
	 
      org.jfree.chart.JFreeChart lineChart = ChartFactory.createLineChart(
         chartTitle,
         "Month","Price",
         createDataset(f.getArray()),
         PlotOrientation.VERTICAL,
         true,true,false);
         
      ChartPanel chartPanel = new ChartPanel( lineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
      c.add(chartPanel);
      
      setVisible(true);
      setContentPane( chartPanel );
  
   }

   private DefaultCategoryDataset createDataset(double f [][] ) {

      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
      
      //f.values.length
      for(int i=0;i<f.length;i++){ 
    	  
    		  dataset.addValue(f[i][3], "ABX Daily", ""+i);
    	  }
      return dataset;
   }
   
   public static void main( String[ ] args ) {
	   drawGraphs chart = new drawGraphs(
         "ABX" ,
         "Daily - 6 Months");

      chart.pack( );
      RefineryUtilities.centerFrameOnScreen( chart );
      chart.setVisible( true );
   }
}

