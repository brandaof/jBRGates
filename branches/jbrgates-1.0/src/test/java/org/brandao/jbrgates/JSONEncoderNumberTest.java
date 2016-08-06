/*
 * JBRGates http://jgates.sourceforge.net/
 * Copyright (C) 2010 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * This library is free software. You can redistribute it
 * and/or modify it under the terms of the GNU General Public
 * License (GPL) version 3.0 or (at your option) any later
 * version.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/gpl.html
 *
 * Distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 *
 */


package org.brandao.jbrgates;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 *
 * @author Afonso Brandao
 */
public class JSONEncoderNumberTest extends TestCase implements Test{

    public JSONEncoderNumberTest(){
        super();
    }

    public void testInt() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( 100 );
        assertEquals( "100", jse.toString() );
    }

    public void testInteger() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( new Integer(100) );
        assertEquals( "100", jse.toString() );
    }

    public void testIntNegativo() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( -100 );
        assertEquals( "-100", jse.toString() );
    }

    public void testLong() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( 15632453214L );
        assertEquals( "15632453214", jse.toString() );
    }

    public void testLongObject() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( new Long(15632453214L) );
        assertEquals( "15632453214", jse.toString() );
    }

    public void testLongNegativo() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( -15632453214L );
        assertEquals( "-15632453214", jse.toString() );
    }

    public void testDouble() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( 0.2 );
        assertEquals( "0.2", jse.toString() );
    }

    public void testDoubleObject() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( new Double(0.2) );
        assertEquals( "0.2", jse.toString() );
    }

    public void testDoubleNegativo() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( -0.2 );
        assertEquals( "-0.2", jse.toString() );
    }

    public void testFloat() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( 0.2f );
        assertEquals( "0.2", jse.toString() );
    }

    public void testFloatObject() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( new Float(0.2) );
        assertEquals( "0.2", jse.toString() );
    }

    public void testFloatNegativo() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( -0.2f );
        assertEquals( "-0.2", jse.toString() );
    }

    public void testShort() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( (short)33 );
        assertEquals( "33", jse.toString() );
    }

    public void testShortObject() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( new Short((short)33) );
        assertEquals( "33", jse.toString() );
    }

    public void testShortNegativo() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( (short)-33 );
        assertEquals( "-33", jse.toString() );
    }

    public void testBigDecimal() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( new BigDecimal( 123.222 ) );
        assertEquals( (new BigDecimal( 123.222 )).toString(), jse.toString() );
    }

    public void testBigDecimalNegativo() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( new BigDecimal( -123.222 ) );
        assertEquals( (new BigDecimal( -123.222 )).toString(), jse.toString() );
    }

    public void testBigInteger() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( new BigInteger( "123", 10 ) );
        assertEquals( "123", jse.toString() );
    }

    public void testBigIntegerNegativo() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( new BigInteger( "-123", 10 ) );
        assertEquals( "-123", jse.toString() );
    }

    public void testByte() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( (byte)10 );
        assertEquals( "10", jse.toString() );
    }

    public void testByteNegativo() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( (byte)-22 );
        assertEquals( "-22", jse.toString() );
    }

}
