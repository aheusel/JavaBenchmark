/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javabenchmark.bytecode;

import javabenchmark.Tool;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

/**
 *
 * @author Alexander Heusel
 */
public class MeasureStatic extends Task<XYChart.Series<Double, Double>>
{
    private final int _size;
    private final int _nrCycles;
    private final int _nrMeasurements;
    private final int _intervalsPerBin;

    public MeasureStatic(final int size, final int nrCycles, final int nrMeasurements, final int intervalsPerBin)
    {
        _size = size;
        _nrCycles = nrCycles;
        _nrMeasurements = nrMeasurements;
        _intervalsPerBin = intervalsPerBin;
    }
    
    @Override
    protected Series<Double, Double> call() throws Exception
    {
        final float[] a = new float[_size];
        final float[] b = Tool.fillRandom(new float[_size]);
        final long[] ivls = new long[_nrMeasurements*2];
        int i;
        
        for(i = 0; i < _nrMeasurements; i++)
        {
            if(isCancelled())
            {
                updateMessage("Cancelled");
                break;
            }
            
            ivls[i*2] = System.nanoTime();
            BytecodePerformanceTest.execStatic(_nrCycles, a, b);
            ivls[i*2 + 1] = System.nanoTime();
            
            updateMessage("Measurement " + i);
            updateProgress(i, _nrMeasurements);
            
        }
    
        // Create bins convert them to a series
        final double[] bins = Tool.createBinsFromIntervals(i, ivls, _intervalsPerBin);
        final XYChart.Series<Double, Double> res = new XYChart.Series<Double, Double>();
        for(int j = 0; j < bins.length / 2; j++)
        {
            res.getData().add(new XYChart.Data<Double, Double>(bins[j*2] / 1000.0, bins[j*2+1] / 1000.0));
        }
        
        
        return res;
    }
    
}
