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
public enum MetricPrefix
{

    YOTTA(1e24, "yotta", "Y"),
    ZETTA(1e21, "zetta", "Z"),
    EXA(1e18, "exa", "E"),
    PETA(1e15, "peta", "P"),
    TERA(1e12, "tera", "T"),
    GIGA(1e9, "giga", "G"),
    MEGA(1e6, "mega", "M"),
    KILO(1e3, "kilo", "k"),
    HECTO(1e2, "hecto", "h"),
    DECA(1e1, "deca", "da"),
    ONE(1e0, "", ""),
    DECI(1e-1, "deci", "d"),
    CENTI(1e-2, "centi", "c"),
    MILLI(1e-3, "milli", "m"),
    MICRO(1e-6, "micro", "µ"),
    NANO(1e-9, "nano", "n"),
    PICO(1e-12, "pico", "p"),
    FEMTO(1e-15, "femto", "f"),
    ATTO(1e-18, "atto", "a"),
    ZEPTO(1e-21, "zepto", "z"),
    YOCTO(1e-24, "yocto", "y");
    private final double _exponent;
    private final String _prefix;
    private final String _SISymbol;

    MetricPrefix(final double exponent, final String prefix, final String SISymbol)
    {
        _exponent = exponent;
        _prefix = prefix;
        _SISymbol = SISymbol;
    }

    public double getExponent()
    {
        return _exponent;
    }

    public String getPrefix()
    {
        return _prefix;
    }

    public String getSISymbol()
    {
        return _SISymbol;
    }

    @Override
    public String toString()
    {
        return _SISymbol;
    }

    public static double convert(final MetricPrefix src, final MetricPrefix tgt, final double v)
    {
        return (src.getExponent() * v) / tgt.getExponent();
    }
}
