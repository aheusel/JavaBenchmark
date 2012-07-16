/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javabenchmark.bytecode;

import java.util.HashMap;
import javabenchmark.Measurement;
import javabenchmark.units.MetricPrefix;
import javabenchmark.units.TimeUnit;
import javabenchmark.Tool;
import javabenchmark.units.Unit;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

/**
 *
 * @author Alexande Heusel
 */
public class MeasureBytecode implements Measurement
{
    private int _size;
    private int _nrCycles;
    private int _nrMeasurements;
    private int _intervalsPerBin;
    private final HashMap<String, Task<XYChart.Series<Double, Double>>> _tasks =
            new HashMap<String, Task<XYChart.Series<Double, Double>>>(); 

    public MeasureBytecode() {}
    

    @Override
    public String getDescription()
    {
        return "";
    }

    @Override
    public String getLabelX()
    {
        return "Runtime in";
    }

    @Override
    public String getLabelY()
    {
        return "Mean Time per Interation in";
    }

    @Override
    public Unit getUnitX()
    {
        return TimeUnit.SECOND;
    }

    @Override
    public MetricPrefix getPrefixX()
    {
        return MetricPrefix.MILLI;
    }

    @Override
    public Unit getUnitY()
    {
        return TimeUnit.SECOND;
    }

    @Override
    public MetricPrefix getPrefixY()
    {
        return MetricPrefix.MILLI;
    }    
    
    /**
     * 
     * @return 
     */
    public int getSize()
    {
        return _size;
    }    
    
    /**
     * @param size the _size to set
     */
    public void setSize(int size)
    {
        this._size = size;
    }

    /**
     * @return the _nrCycles
     */
    public int getNrCycles()
    {
        return _nrCycles;
    }

    /**
     * @param nrCycles the _nrCycles to set
     */
    public void setNrCycles(int nrCycles)
    {
        this._nrCycles = nrCycles;
    }

    /**
     * @return the _nrMeasurements
     */
    public int getNrMeasurements()
    {
        return _nrMeasurements;
    }

    /**
     * @param nrMeasurements the _nrMeasurements to set
     */
    public void setNrMeasurements(int nrMeasurements)
    {
        this._nrMeasurements = nrMeasurements;
    }

    /**
     * @return the _intervalsPerBin
     */
    public int getIntervalsPerBin()
    {
        return _intervalsPerBin;
    }

    /**
     * @param intervalsPerBin the _intervalsPerBin to set
     */
    public void setIntervalsPerBin(int intervalsPerBin)
    {
        this._intervalsPerBin = intervalsPerBin;
    }

    @Override
    public HashMap<String, Task<Series<Double, Double>>> getTasks()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}