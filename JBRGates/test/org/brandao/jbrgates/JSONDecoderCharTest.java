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
public class JSONDecoderCharTest extends TestCase implements Test{

    public JSONDecoderCharTest(){
        super();
    }

    public void testCharUnicodeString() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"A\"" );
        assertEquals( "A", jse.decode() );
    }

    public void testCharUnicode() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"A\"" );
        char result = jse.decode( char.class );
        assertEquals( 'A', result );
    }

    public void testCharString() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\uffff\"" );
        assertEquals( "\uffff", jse.decode() );
    }

    public void testChar() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\uffff\"" );
        char result = jse.decode( char.class );
        assertEquals( '\uffff', result );
    }

    public void testCharSpaceString() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\" \"" );
        assertEquals( " ", jse.decode() );
    }

    public void testCharSpace() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\" \"" );
        char result = jse.decode( char.class );
        assertEquals( ' ', result );
    }

    public void testQuantationMarkString() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\\"\"" );
        assertEquals( "\"", jse.decode() );
    }

    public void testQuantationMark() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\\"\"" );
        Character result = jse.decode( char.class );
        assertEquals( Character.valueOf( '\"' ), result );
    }

    public void testReverseSolidusString() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\\\\"" );
        assertEquals( "\\", jse.decode() );
    }

    public void testReverseSolidus() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\\\\"" );
        char result = jse.decode( char.class );
        assertEquals( '\\', result );
    }

    public void testReverseSolidusError() throws IOException{
        try{
            JSONDecoder jse = new JSONDecoder( "\"\\\"" );
            jse.decode();
            fail( "expected JSONException" );
        }
        catch( JSONException e ){
        }
    }

    public void testSolidusString() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\/\"" );
        assertEquals( "/", jse.decode() );
    }

    public void testSolidus() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\/\"" );
        char result = jse.decode( char.class );
        assertEquals( '/', result );
    }

    public void testSimpleSolidus() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"/\"" );
        char result = jse.decode( Character.class );
        assertEquals( '/', result );
    }

    public void testBackspaceString() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\b\"" );
        assertEquals( "\b", jse.decode() );
    }

    public void testBackspace() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\b\"" );
        char result = jse.decode( char.class );
        assertEquals( '\b', result );
    }

    public void testFormfeedString() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\f\"" );
        assertEquals( "\f", jse.decode() );
    }

    public void testFormfeed() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\f\"" );
        char result = jse.decode( char.class );
        assertEquals( '\f', result );
    }

    public void testNewLineString() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\n\"" );
        assertEquals( "\n", jse.decode() );
    }

    public void testNewLine() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\n\"" );
        char result = jse.decode( char.class );
        assertEquals( '\n', result );
    }

    public void testCarriageReturnString() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\r\"" );
        assertEquals( "\r", jse.decode() );
    }

    public void testCarriageReturn() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\r\"" );
        char result = jse.decode( char.class );
        assertEquals( '\r', result );
    }

    public void testHorizontalTabString() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\t\"" );
        assertEquals( "\t", jse.decode() );
    }

    public void testHorizontalTab() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\t\"" );
        char result = jse.decode( char.class );
        assertEquals( '\t', result );
    }

    public void testHexString() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\uffff\"" );
        assertEquals( "\uffff", jse.decode() );
    }

    public void testHex() throws IOException{
        JSONDecoder jse = new JSONDecoder( "\"\\uffff\"" );
        char result = jse.decode( char.class );
        assertEquals( '\uffff', result );
    }

}
