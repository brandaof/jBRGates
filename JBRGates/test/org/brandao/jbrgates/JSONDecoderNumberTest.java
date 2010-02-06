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
import junit.framework.Test;
import junit.framework.TestCase;

/**
 *
 * @author Afonso Brandao
 */
public class JSONDecoderNumberTest extends TestCase implements Test{

    public JSONDecoderNumberTest(){
        super();
    }

    public void testIntString() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( 10 ) );
        Object value = jse.decode();
        assertEquals( "10", value );
    }

    public void testInt() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( 10 ) );
        Object value = jse.decode( int.class );
        assertEquals( 10, value );
    }

    public void testInteger() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( 10 ) );
        Object value = jse.decode( Integer.class );
        assertEquals( 10, value );
    }

    public void testIntNegativoString() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( -100 ) );
        Object value = jse.decode();
        assertEquals( "-100", value );
    }

    public void testIntNegativo() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( -100 ) );
        Object value = jse.decode( int.class );
        assertEquals( -100, value );
    }

    public void testIntegerNegativo() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( -100 ) );
        Object value = jse.decode( Integer.class );
        assertEquals( -100, value );
    }


    public void testLongString() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( 99999999999L ) );
        Object value = jse.decode();
        assertEquals( "99999999999", value );
    }

    public void testLong() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( 99999999999L ) );
        Object value = jse.decode( long.class );
        assertEquals( 99999999999L, value );
    }

    public void testLongObject() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( 99999999999L ) );
        Object value = jse.decode( Long.class );
        assertEquals( 99999999999L, value );
    }


    public void testLongNegativoString() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( -99999999999L ) );
        Object value = jse.decode();
        assertEquals( "-99999999999", value );
    }

    public void testLongNegativo() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( -99999999999L ) );
        Object value = jse.decode( long.class );
        assertEquals( -99999999999L, value );
    }

    public void testLongNegativoObject() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( -99999999999L ) );
        Object value = jse.decode( Long.class );
        assertEquals( -99999999999L, value );
    }



    public void testDoubleString() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( 2.03 ) );
        Object value = jse.decode();
        assertEquals( "2.03", value );
    }

    public void testDouble() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( 2.03 ) );
        Object value = jse.decode( double.class );
        assertEquals( 2.03, value );
    }

    public void testDoubleObject() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( 2.03 ) );
        Object value = jse.decode( Double.class );
        assertEquals( 2.03, value );
    }




    public void testDoubleNegativoString() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( -2.03 ) );
        Object value = jse.decode();
        assertEquals( "-2.03", value );
    }

    public void testDoubleNegativo() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( -2.03 ) );
        Object value = jse.decode( double.class );
        assertEquals( -2.03, value );
    }

    public void testDoubleNegativoObject() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( -2.03 ) );
        Object value = jse.decode( Double.class );
        assertEquals( -2.03, value );
    }


    public void testFloatString() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( 2.03f ) );
        Object value = jse.decode();
        assertEquals( "2.03", value );
    }

    public void testFloat() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( 2.03f ) );
        Object value = jse.decode( float.class );
        assertEquals( 2.03f, value );
    }

    public void testFloatObject() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( 2.03f ) );
        Object value = jse.decode( Float.class );
        assertEquals( 2.03f, value );
    }



    public void testFloatNegativoString() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( -2.03f ) );
        Object value = jse.decode();
        assertEquals( "-2.03", value );
    }

    public void testFloatNegativo() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( -2.03f ) );
        Object value = jse.decode( float.class );
        assertEquals( -2.03f, value );
    }

    public void testFloatNegativoObject() throws IOException{
        JSONDecoder jse = new JSONDecoder( String.valueOf( -2.03f ) );
        Object value = jse.decode( Float.class );
        assertEquals( -2.03f, value );
    }

}
