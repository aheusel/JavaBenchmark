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

package javabenchmark.units;

/**
 *
 * @author Alexander Heusel
 */
public enum CountUnit implements Unit
{
    ITERATION(1, "iteration", "iterations", "it"),
    N(1, "N", "N", "#"),
    DOZEN(12, "dozen", "dozens", "doz"),
    GROSS(12*12, "gross", "gross", "gro");
    
    private final double _baseFactor;
    private final String _name;
    private final String _namePlural;
    private final String _symbol;

    CountUnit(final double baseFactor, final String name, final String namePlural, final String symbol)
    {
        _baseFactor = baseFactor;
        _name = name;
        _namePlural = namePlural;
        _symbol = symbol;
    }
    
    @Override
    public Number getBaseFactor()
    {
        return _baseFactor;
    }
    
    @Override
    public String getName()
    {
        return _name;
    }

    @Override
    public String getNamePlural()
    {
        return _namePlural;
    }

    @Override
    public String getSymbol()
    {
        return _symbol;
    }

    @Override
    public String toString()
    {
        return _symbol;
    }

    public static double convert(final CountUnit src, final CountUnit tgt, final double v)
    {
        return (src._baseFactor*v)/tgt._baseFactor;
    }

    
}
