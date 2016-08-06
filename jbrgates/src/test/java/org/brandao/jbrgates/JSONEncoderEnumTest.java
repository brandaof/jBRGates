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
public class JSONEncoderEnumTest extends TestCase implements Test{

    static enum EnumTest{
        VALUE1,
        VALUE2,
        VALUE3
    }

    public JSONEncoderEnumTest(){
        super();
    }

    public void testEnum() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( EnumTest.VALUE2 );
        assertEquals( "\"" + EnumTest.VALUE2.name() + "\"", jse.toString() );
    }


}
