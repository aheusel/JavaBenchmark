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

package javabenchmark.bytecode;

import java.util.Arrays;
import javabenchmark.Tool;

/**
 *
 * @author Alexander Heusel
 */
public class BytecodePerformanceTest
{
    private static final class Abs implements Operator
    {
        @Override
        public final float f(float x)
        {
            return Math.abs(x);
        }
        
    }
    
    public static float[] transform(final Operator op, final int offa, final int offb, final int length, final float[] a, final float[] b)
    {
        for(int i = 0; i < length; i++)
        {
            a[offa + i] = op.f(b[offb + i]);
        }
        return a;
    }
    
    public static void execInterface(final Operator op, final int nrCycles, final float[] a, final float[] b)
    {
        for(int i = 0; i < nrCycles; i++)
        {
            transform(op, 0, 0, a.length, a, b);
        }
    }    
    
    public static float[] transform(final int offa, final int offb, final int length, final float[] a, final float[] b)
    {
        for(int i = 0; i < length; i++)
        {
            a[offa + i] = Math.abs(b[offb + i]);
        }
        return a;
    }
    
    
    public static void execStatic(final int nrCycles, final float[] a, final float[] b)
    {
        for(int i = 0; i < nrCycles; i++)
        {
            transform(0, 0, a.length, a, b);
        }
    }

    public static void execBytecode(final Algorithm op, final int nrCycles, final float[] a, final float[] b)
    {
        for(int i = 0; i < nrCycles; i++)
        {
            op.transform(0, 0, a.length, a, b);
        }
    }    

    
    public static void measure() throws NoSuchMethodException, Exception
    {
        
        final int size = 10000;
        final int nrCycles = 10000000;
        final float[] a = new float[size];
        final float[] b = Tool.fillRandom(new float[size]);
              
        final Algorithm alg = Transform.compile("ndimtest.MyImpl", Math.class.getDeclaredMethod("abs", Float.TYPE));
        final Abs abs = new Abs();
        
        final long startExecStatic = System.nanoTime();
        execStatic(nrCycles, a, b);
        final long startExecInterface = System.nanoTime();
        execInterface(abs, nrCycles, a, b);
        final long startExecBytecode = System.nanoTime();
        execBytecode(alg, nrCycles, a, b);
        final long end = System.nanoTime();
        
        final double timeExecStatic = (double)(startExecInterface - startExecStatic);
        final double timeExecInterface = (double)(startExecBytecode - startExecInterface);
        final double timeExecBytecode = (double)(end - startExecBytecode);
        
        final double maxTime = Math.max(Math.max(timeExecStatic, timeExecInterface), timeExecBytecode);
        
        System.out.format("execStatic: %f, execInterface: %f execBytecode: %f\n",
                timeExecStatic/maxTime,
                timeExecInterface/maxTime,
                timeExecBytecode/maxTime);
        
        
    }
}
