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
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 *
 * @author Afonso Brandao
 */
public class JSONEncoderArrayTest extends TestCase implements Test{

    static class MyObject implements Serializable{
        private Long a;
        private byte b;

        public MyObject( Long a, byte b ){
            setA( a );
            setB( b );
        }

        public Long getA() {
            return a;
        }

        public void setA(Long a) {
            this.a = a;
        }

        public byte getB() {
            return b;
        }

        public void setB(byte b) {
            this.b = b;
        }
    }

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

    public void testObject() throws IOException{

        MyObject o0 = new MyObject( 10l, (byte)25 );
        MyObject o1 = new MyObject( 20l, (byte)-25 );
        MyObject o2 = new MyObject( 1l, (byte)127 );


        MyObject[] array = new MyObject[]{ null, o0, o1, o2 };

        String ex = String.format( 
                        "[ null, %s, %s, %s ]",
                        "{ \"b\" : 25, \"a\" : 10, \"class\" : \"org.brandao.jbrgates.JSONEncoderArrayTest$MyObject\" }",
                        "{ \"b\" : -25, \"a\" : 20, \"class\" : \"org.brandao.jbrgates.JSONEncoderArrayTest$MyObject\" }",
                        "{ \"b\" : 127, \"a\" : 1, \"class\" : \"org.brandao.jbrgates.JSONEncoderArrayTest$MyObject\" }" );

        JSONEncoder jse = new JSONEncoder();
        jse.encode( array );

        assertEquals( ex, jse.toString() );
    }

    public void testCollection() throws IOException{

        MyObject o0 = new MyObject( 10l, (byte)25 );
        MyObject o1 = new MyObject( 20l, (byte)-25 );
        MyObject o2 = new MyObject( 1l, (byte)127 );


        Collection<MyObject> collection = new ArrayList<MyObject>();
        collection.add( null );
        collection.add( o0 );
        collection.add( o1 );
        collection.add( o2 );

        Collection[] array = new Collection[]{ null, collection,collection,collection };
        String ex = String.format(
                        "[ null, %s, %s, %s ]",
                        "{ \"b\" : 25, \"a\" : 10, \"class\" : \"org.brandao.jbrgates.JSONEncoderArrayTest$MyObject\" }",
                        "{ \"b\" : -25, \"a\" : 20, \"class\" : \"org.brandao.jbrgates.JSONEncoderArrayTest$MyObject\" }",
                        "{ \"b\" : 127, \"a\" : 1, \"class\" : \"org.brandao.jbrgates.JSONEncoderArrayTest$MyObject\" }" );

        String result = String.format(
                    "[ null, %s, %s, %s ]",
                    ex,
                    ex,
                    ex );

        JSONEncoder jse = new JSONEncoder();
        jse.encode( array );

        assertEquals( result, jse.toString() );
    }

    public void testInt() throws IOException{
        int[] array = new int[]{ 0, 100, 200, 150 };
        JSONEncoder jse = new JSONEncoder();
        jse.encode( array );
        assertEquals( "[ 0, 100, 200, 150 ]", jse.toString() );
    }

    public void testLong() throws IOException{
        long[] array = new long[]{ 0, 100, 200, 150 };
        JSONEncoder jse = new JSONEncoder();
        jse.encode( array );
        assertEquals( "[ 0, 100, 200, 150 ]", jse.toString() );
    }

    public void testDouble() throws IOException{
        double[] array = new double[]{ 0, 15.2, 20.2, 1.01 };
        JSONEncoder jse = new JSONEncoder();
        jse.encode( array );
        assertEquals( "[ 0.0, 15.2, 20.2, 1.01 ]", jse.toString() );
    }

    public void testFloat() throws IOException{
        float[] array = new float[]{ 0f, 15.2f, 20.2f, 1.01f };
        JSONEncoder jse = new JSONEncoder();
        jse.encode( array );
        assertEquals( "[ 0.0, 15.2, 20.2, 1.01 ]", jse.toString() );
    }

    public void testShort() throws IOException{
        short[] array = new short[]{ 0, 100, 200, 150 };
        JSONEncoder jse = new JSONEncoder();
        jse.encode( array );
        assertEquals( "[ 0, 100, 200, 150 ]", jse.toString() );
    }

    public void testBoolean() throws IOException{
        boolean[] array = new boolean[]{ true, false, false, true };
        JSONEncoder jse = new JSONEncoder();
        jse.encode( array );
        assertEquals( "[ true, false, false, true ]", jse.toString() );
    }

    public void testChar() throws IOException{
        char[] array = new char[]{ 'A', 'F', 'O', 'N' };
        JSONEncoder jse = new JSONEncoder();
        jse.encode( array );
        assertEquals( "[ \"A\", \"F\", \"O\", \"N\" ]", jse.toString() );
    }

    public void testByte() throws IOException{
        byte[] array = new byte[]{ (byte)0, (byte)-100, (byte)127, (byte)99 };
        JSONEncoder jse = new JSONEncoder();
        jse.encode( array );
        assertEquals( "[ 0, -100, 127, 99 ]", jse.toString() );
    }

    public void testBigDecimal() throws IOException{
        BigDecimal[] array = new BigDecimal[]{ new BigDecimal(0), new BigDecimal(-100), new BigDecimal( 127 ) };
        JSONEncoder jse = new JSONEncoder();
        jse.encode( array );
        assertEquals( "[ 0, -100, 127 ]", jse.toString() );
    }

    public void testBigInteger() throws IOException{
        BigInteger[] array = new BigInteger[]{ new BigInteger("0",10), new BigInteger("-100",10), new BigInteger( "127",10 ) };
        JSONEncoder jse = new JSONEncoder();
        jse.encode( array );
        assertEquals( "[ 0, -100, 127 ]", jse.toString() );
    }

    public void testString() throws IOException{
        String[] array = new String[]{ "AA", "BB", "CC", "DD" };
        JSONEncoder jse = new JSONEncoder();
        jse.encode( array );
        assertEquals( "[ \"AA\", \"BB\", \"CC\", \"DD\" ]", jse.toString() );
    }

    public void testURL() throws IOException{
        URL[] array = new URL[]{
                            new URL("http://www.mysite.com"),
                            new URL("http://www.mysite.net"),
                            new URL("http://www.mysite.br")};
        JSONEncoder jse = new JSONEncoder();
        jse.encode( array );
        assertEquals(
            "[ " +
                "\"http:\\/\\/www.mysite.com\", " +
                "\"http:\\/\\/www.mysite.net\", " +
                "\"http:\\/\\/www.mysite.br\" ]", jse.toString() );
    }

    public void testURI() throws IOException, URISyntaxException{
        URI[] array = new URI[]{
                            new URI("http://www.mysite.com"),
                            new URI("http://www.mysite.net"),
                            new URI("http://www.mysite.br")};
        JSONEncoder jse = new JSONEncoder();
        jse.encode( array );
        assertEquals(
            "[ " +
                "\"http:\\/\\/www.mysite.com\", " +
                "\"http:\\/\\/www.mysite.net\", " +
                "\"http:\\/\\/www.mysite.br\" ]", jse.toString() );
    }

    public void testClass() throws IOException{
        Class[] array = new Class[]{ Integer.class, int.class, double.class, Float.class };
        JSONEncoder jse = new JSONEncoder();
        jse.encode( array );
        assertEquals( "[ \"java.lang.Integer\", \"int\", \"double\", \"java.lang.Float\" ]", jse.toString() );
    }

    public void testLocale() throws IOException{
        Locale[] array = new Locale[]{ Locale.US, Locale.CANADA, Locale.FRANCE };
        JSONEncoder jse = new JSONEncoder();
        jse.encode( array );
        assertEquals( 
                String.format(
                    "[ \"%s-%s\", \"%s-%s\", \"%s-%s\" ]",
                    Locale.US.getLanguage(),
                    Locale.US.getCountry(),
                    Locale.CANADA.getLanguage(),
                    Locale.CANADA.getCountry(),
                    Locale.FRANCE.getLanguage(),
                    Locale.FRANCE.getCountry() )
                ,jse.toString() );
    }

    public void testDate() throws IOException{
        Date[] array = new Date[]{ new Date(), new Date() };
        JSONEncoder jse = new JSONEncoder();
        jse.encode( array );
        assertEquals(
                String.format(
                    "[ %d, %d ]",
                    array[0].getTime(),
                    array[1].getTime()
                ), jse.toString() );
    }

    public void testTime() throws IOException{
        Time[] array = new Time[]{ new Time( (new Date()).getTime() ), new Time( (new Date()).getTime() ) };
        JSONEncoder jse = new JSONEncoder();
        jse.encode( array );
        assertEquals(
                String.format(
                    "[ %d, %d ]",
                    array[0].getTime(),
                    array[1].getTime()
                ), jse.toString() );
    }

    public void testTimestamp() throws IOException{
        Timestamp[] array = new Timestamp[]{ new Timestamp( (new Date()).getTime() ), new Timestamp( (new Date()).getTime() ) };
        JSONEncoder jse = new JSONEncoder();
        jse.encode( array );
        assertEquals(
                String.format(
                    "[ %d, %d ]",
                    array[0].getTime(),
                    array[1].getTime()
                ), jse.toString() );
    }

    public void testCalendar() throws IOException{
        Calendar[] array = new Calendar[]{ new GregorianCalendar(), new GregorianCalendar() };
        JSONEncoder jse = new JSONEncoder();
        jse.encode( array );
        assertEquals(
                String.format(
                    "[ %d, %d ]",
                    array[0].getTime().getTime(),
                    array[1].getTime().getTime()
                ), jse.toString() );
    }

    public void testNull() throws IOException{
        Object[] array = new Object[]{ null, null };
        JSONEncoder jse = new JSONEncoder();
        jse.encode( array );
        assertEquals( "[ null, null ]", jse.toString() );
    }

}
