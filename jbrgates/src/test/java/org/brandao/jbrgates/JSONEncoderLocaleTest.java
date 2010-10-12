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
import java.util.Locale;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 *
 * @author Afonso Brandao
 */
public class JSONEncoderLocaleTest extends TestCase implements Test{

    public JSONEncoderLocaleTest(){
        super();
    }

    public void testLocaleString() throws IOException{
        String locale =
                String.format(
                        "%s-%s",
                        Locale.US.getLanguage(),
                        Locale.US.getCountry() );

        JSONEncoder jse = new JSONEncoder();
        jse.encode( Locale.US );
        assertEquals(
            String.format( "\"%s\"" , locale ),
            jse.toString() );
    }

}
