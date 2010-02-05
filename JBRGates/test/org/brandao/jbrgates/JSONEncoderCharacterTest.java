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
public class JSONEncoderCharacterTest extends TestCase implements Test{

    public JSONEncoderCharacterTest(){
        super();
    }

    public void testCharacterUnicode() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( Character.valueOf( 'A' ) );
        assertEquals( jse.toString(), "\"A\"" );
    }

    public void testCharacter() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( Character.valueOf( '\uffff' ) );
        assertEquals( jse.toString(), "\"\\uffff\"" );
    }

    public void testCharSpace() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( Character.valueOf( ' ' ) );
        assertEquals( jse.toString(), "\" \"" );
    }

    public void testQuantationMark() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( Character.valueOf( '\"' ) );
        assertEquals( jse.toString(), "\"\\\"\"" );
    }

    public void testReverseSolidus() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( Character.valueOf( '\\' ) );
        assertEquals( jse.toString(), "\"\\\\\"" );
    }

    public void testSolidus() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( Character.valueOf( '/' ) );
        assertEquals( jse.toString(), "\"\\/\"" );
    }

    public void testBackspace() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( Character.valueOf( '\b' ) );
        assertEquals( jse.toString(), "\"\\b\"" );
    }

    public void testFormfeed() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( Character.valueOf( '\f' ) );
        assertEquals( jse.toString(), "\"\\f\"" );
    }

    public void testNewLine() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( Character.valueOf( '\n' ) );
        assertEquals( jse.toString(), "\"\\n\"" );
    }

    public void testCarriageReturn() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( Character.valueOf( '\r' ) );
        assertEquals( jse.toString(), "\"\\r\"" );
    }

    public void testHorizontalTab() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( Character.valueOf( '\t' ) );
        assertEquals( jse.toString(), "\"\\t\"" );
    }

    public void testHex() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( Character.valueOf( '\u0fff' ) );
        assertEquals( jse.toString(), "\"\\u0fff\"" );
    }

}
