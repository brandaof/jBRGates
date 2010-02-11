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
import java.util.HashMap;
import java.util.Map;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 *
 * @author Afonso Brandao
 */
public class JSONEncoderArrayTest extends TestCase implements Test{

    public JSONEncoderArrayTest(){
        super();
    }

    public void testMap() throws IOException{
        Map<String,Object> map0 = new HashMap<String,Object>();
        map0.put("key1", 10 );
        map0.put("key2", "Text" );

        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("key3", -1.3 );
        map1.put("key4", 1f );

        Map<String,Object> map2 = new HashMap<String,Object>();
        map2.put("key5", "Text2" );
        map2.put("key6", "10000" );

        Map[] maps = new Map[]{ map0, map1, map2, null };

        JSONEncoder jse = new JSONEncoder();
        jse.encode( maps );

        String map = "[ ";
        String tmpMap = "";

        for( String key: map0.keySet() ){
            String t = String.format( "\"%s\" : %s" ,
                            key, 
                            map0.get( key ) instanceof String?
                                String.valueOf( "\""+map0.get( key )+"\"" ) :
                                String.valueOf( map0.get( key ) ) );

            tmpMap += tmpMap.length() == 0? t : ", " + t;
        }

        map += String.format( "{ %s }, " , tmpMap );

        tmpMap = "";
        for( String key: map1.keySet() ){
            String t = String.format( "\"%s\" : %s" ,
                            key, 
                            map1.get( key ) instanceof String?
                                String.valueOf( "\""+map1.get( key )+"\"" ) :
                                String.valueOf( map1.get( key ) ) );

            tmpMap += tmpMap.length() == 0? t : ", " + t;
        }

        map += String.format( "{ %s }, " , tmpMap );

        tmpMap = "";
        for( String key: map2.keySet() ){
            String t = String.format( "\"%s\" : %s" ,
                            key, 
                            map2.get( key ) instanceof String?
                                String.valueOf( "\""+map2.get( key )+"\"" ) :
                                String.valueOf( map2.get( key ) ) );

            tmpMap += tmpMap.length() == 0? t : ", " + t;
        }

        map += String.format( "{ %s }" , tmpMap );

        map += ", null ]";

        assertEquals( map, jse.toString() );
    }

}
