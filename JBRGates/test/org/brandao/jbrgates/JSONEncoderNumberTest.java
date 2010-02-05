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
public class JSONEncoderNumberTest extends TestCase implements Test{

    public JSONEncoderNumberTest(){
        super();
    }

    public void testInt() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( 100 );
        assertEquals( jse.toString(), "100" );
    }

    public void testInteger() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( new Integer(100) );
        assertEquals( jse.toString(), "100" );
    }

    public void testIntNegativo() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( -100 );
        assertEquals( jse.toString(), "-100" );
    }

    public void testLong() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( 15632453214L );
        assertEquals( jse.toString(), "15632453214" );
    }

    public void testLongObject() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( new Long(15632453214L) );
        assertEquals( jse.toString(), "15632453214" );
    }

    public void testLongNegativo() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( -15632453214L );
        assertEquals( jse.toString(), "-15632453214" );
    }

    public void testDouble() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( 0.2 );
        assertEquals( jse.toString(), "0.2" );
    }

    public void testDoubleObject() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( new Double(0.2) );
        assertEquals( jse.toString(), "0.2" );
    }

    public void testDoubleNegativo() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( -0.2 );
        assertEquals( jse.toString(), "-0.2" );
    }

    public void testFloat() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( 0.2f );
        assertEquals( jse.toString(), "0.2" );
    }

    public void testFloatObject() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( new Float(0.2) );
        assertEquals( jse.toString(), "0.2" );
    }

    public void testFloatNegativo() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( -0.2f );
        assertEquals( jse.toString(), "-0.2" );
    }

}
