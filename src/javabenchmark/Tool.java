/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012, ndim.org
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright notice, this 
 *   list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or
 *   other materials provided with the distribution.
 * - Neither the name of ndim nor the names of its contributors may
 *   be used to endorse or copy products derived from this software without
 *   specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package javabenchmark;

import java.util.Random;
import javafx.scene.chart.XYChart;

/**
 *
 * @author Alexander Heusel
 */
public class Tool
{
    public static float[] fillRandom(final float[] a)
    {
        final Random rnd = new Random();
        for(int i = 0; i < a.length; i++)
        {
            a[i] = rnd.nextFloat();
        }
        return a;
    }
    
    public static long[] syntheticIntervals(final int nrIntervals, final long dev)
    {
        final Random rnd = new Random();
        final long[] res = new long[nrIntervals];
        long iter = (long)(10000.0*rnd.nextDouble());
        
        for(int i = 0; i < res.length; i++)
        {
            res[i] = iter;
            iter += (1000 + (long)((2.0*rnd.nextDouble() - 1.0)*(double)dev));
        }
        
        return res;
    }
    
    public static XYChart.Series createBinSeriesFromNanoTime(final long[] ivls, final int ivlPerBin)
    {
        final int nrIvls = ivls.length - 1;
        final int nrBins = (int)Math.ceil(((double)nrIvls) / ((double)ivlPerBin));
        final XYChart.Series<Double, Double> res = new XYChart.Series<Double, Double>();
                   
        // Create XY-series
        double ivlBegin;
        double ivlEnd;
        double center;
        double mean;
        for(int i = 0; i < nrBins - 1; i++)
        {
           ivlBegin =  ((double)(ivls[i*ivlPerBin] - ivls[0]))/1000.0;
           ivlEnd = ((double)(ivls[(i+1)*ivlPerBin] - ivls[0]))/1000.0;
           center = ivlBegin + ((ivlEnd - ivlBegin) / 2.0);
           mean = (ivlEnd - ivlBegin) / (double)ivlPerBin;
           res.getData().add(new XYChart.Data(center, mean));
        }
        ivlBegin = ((double)(ivls[(nrBins - 1)*ivlPerBin] - ivls[0]))/1000.0;
        ivlEnd = ((double)(ivls[ivls.length - 1] - ivls[0]))/1000.0;
        center = ivlBegin + ((ivlEnd - ivlBegin) / 2.0);
        mean = (ivlEnd - ivlBegin) / (double)((ivls.length - 1) - ((nrBins - 1)*ivlPerBin) + 1);
        res.getData().add(new XYChart.Data(center, mean));
        
        // Return series
        return res;
    }
    
    public static double[] createBinsFromIntervals(final int nrIvls, final long[] ivls, final int ivlPerBin)
    {
        final int remainder = nrIvls % ivlPerBin;
        final int nrBins = remainder > 0 ? (nrIvls / ivlPerBin + 1) : (nrIvls / ivlPerBin);
        final int binEnd = nrBins - 1;
         
        final double[] bins = new double[nrBins*2];
                   
        // Fill bins
        int binIdx;
        double zero = ivls[0];
        double a;
        double b;
        for(int i = 0; i < nrIvls; i++)
        {
            // Get index of the target-bin
            binIdx = i / ivlPerBin;
            
            //Get begin and end of the interval and set the beginning of the
            // first interval as zero.
            a = ivls[i*2] - zero;
            b = ivls[i*2 + 1] - zero;            
            // Accumulate center coordinate of the bin
            bins[binIdx*2] += a + ((b - a) / 2.0);
            // Accumulate value of the bin
            bins[binIdx*2 + 1] += b - a;
        }
        
        // Calculate mean-value for each bin
        for(int i = 0; i < binEnd; i++)
        {
            bins[i*2] /= ivlPerBin;
            bins[i*2 + 1] /= ivlPerBin;
        }
        if(remainder > 0)
        {
            bins[binEnd*2] /= remainder;
            bins[binEnd*2 + 1] /= remainder; 
        }
        else
        {
            bins[binEnd*2] /= ivlPerBin;
            bins[binEnd*2 + 1] /= ivlPerBin;             
        }
        
        return bins;
    }
        
    
}
