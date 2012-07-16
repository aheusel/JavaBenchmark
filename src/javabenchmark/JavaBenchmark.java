/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javabenchmark;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Alexander Heusel
 */
public class JavaBenchmark extends Application
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
         Application.launch(JavaBenchmark.class, args);
    }
    
    @Override
    public void start(Stage stage) throws Exception
    {
        
        Parent root = FXMLLoader.load(getClass().getResource("BenchmarkUI.fxml"));
        
        stage.setScene(new Scene(root));
        stage.show();
        
        
//        stage.setTitle("Java Benchmark");
//        //defining the axes
//        final NumberAxis xAxis = new NumberAxis();
//        final NumberAxis yAxis = new NumberAxis();
//        xAxis.setLabel("Milliseconds");
//        //creating the chart
//        final LineChart<Number,Number> lineChart = 
//                new LineChart<Number,Number>(xAxis,yAxis);
//                
//        lineChart.setTitle("Benchmark");
//
//        final long[] s = Tool.syntheticIntervals(1000, 300);
//        XYChart.Series series = Tool.createBinSeriesFromNanoTime(s, 21);
//        series.setName("Test");
//        
//        
//        Scene scene  = new Scene(lineChart,800,600);
//        lineChart.getData().add(series);
//        series.getNode().setStyle("-fx-stroke-width: 1px;" );
//        lineChart.setCreateSymbols(false);
//        stage.setScene(scene);
//        stage.show();        
    }
}
