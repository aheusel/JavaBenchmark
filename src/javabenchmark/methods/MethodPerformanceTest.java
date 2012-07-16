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

package javabenchmark.methods;

import javabenchmark.Tool;


/**
 *
 * @author Alexander Heusel
 */
public class MethodPerformanceTest
{
    
    public static void execInterface(Operator op, final int nrCycles, float[] a, float[] b)
    {
        for(int j = 0; j < nrCycles; j++)
        {
            for(int i = 0; i < a.length; i++)
            {
                a[i] = op.f(b, i*3);
            }
        }
    }
    
    public static void execFinal(B op, final int nrCycles, float[] a, float[] b)
    {
        for(int j = 0; j < nrCycles; j++)
        {
            for(int i = 0; i < a.length; i++)
            {
                a[i] = op.f(b, i*3);
            }
        }
    }

    public static void execVirtual(A op, final int nrCycles, float[] a, float[] b)
    {
        for(int j = 0; j < nrCycles; j++)
        {
            for(int i = 0; i < a.length; i++)
            {
                a[i] = op.f(b, i*3);
            }
        }
    }

    public static float f(final float[] x, final int idx)
    {
        float accu;
        accu = x[idx];
        float n2 = accu*accu;
        accu = x[idx + 1];
        n2 += accu*accu;
        accu = x[idx + 2];
        n2 += accu*accu;        
        return (float)Math.sqrt(n2);
    }
    
    public static void execStatic(final int nrCycles, final float[] a, final float[] b)
    {
        for(int j = 0; j < nrCycles; j++)
        {
            for(int i = 0; i < a.length; i++)
            {
                a[i] = f(b, i*3);
            }
        }
    }
    
    
    public static void measure()
    {
        final int size = 10000000;
        final int nrCycles = 100;
        final float[] a = new float[size];
        final float[] b = Tool.fillRandom(new float[size*3]);
        
        
        B op = new B();
        
        final long startExecVirtual = System.nanoTime();
        execVirtual(op, nrCycles, a, b);
        final long startExecFinal = System.nanoTime();
        execFinal(op, nrCycles, a, b);
        final long startExecInterface = System.nanoTime();
        execInterface(op, nrCycles, a, b);
        final long startExecStatic = System.nanoTime();
        execStatic(nrCycles, a ,b);
        final long end = System.nanoTime();
        
        final double timeExecVirtual = (double)(startExecFinal - startExecVirtual);
        final double timeExecFinal = (double)(startExecInterface - startExecFinal);
        final double timeExecInterface = (double)(startExecStatic - startExecInterface);
        final double timeExecStatic = (double)(end - startExecStatic);
        
        
        final double maxTime = Math.max(Math.max(Math.max(timeExecVirtual, timeExecFinal), timeExecInterface), timeExecStatic);
        
        System.out.format("execVirtual: %f, execFinal: %f execInterface: %f execStatic: %f\n",
                timeExecVirtual/maxTime,
                timeExecFinal/maxTime,
                timeExecInterface/maxTime,
                timeExecStatic/maxTime );
        
        
    }
    

}
