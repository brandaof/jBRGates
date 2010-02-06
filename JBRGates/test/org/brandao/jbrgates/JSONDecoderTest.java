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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 *
 * @author Afonso Brandao
 */
public class JSONDecoderTest extends TestCase implements Test{

    public JSONDecoderTest(){
        super();
    }

    public void testInputStream() throws IOException{
        try{
            JSONDecoder jse = new JSONDecoder( (InputStream)null );
        }
        catch( NullPointerException e ){
            return;
        }
        fail( "expected NullPointerException" );
    }

    public void testFactoryBean() throws IOException{
        try{
            JSONDecoder jse = new JSONDecoder( new ByteArrayInputStream( new byte[]{} ), null );
        }
        catch( NullPointerException e ){
            return;
        }
        fail( "expected NullPointerException" );
    }

    public void testDefault() throws IOException{
        try{
            JSONDecoder jse = new JSONDecoder( null, null );
        }
        catch( NullPointerException e ){
            return;
        }
        fail( "expected NullPointerException" );
    }

}
