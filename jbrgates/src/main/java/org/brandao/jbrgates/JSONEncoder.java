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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * This class is the encoding of objects into JSON.<br>
 * Only serializable objects are encoded.<br>
 * Ex:<br>
 * <b>Class:</b><br>
 * <pre>
 * public class MyObject implements Serializable{
 *   private int id;
 *   private String name;
 * 
 *   ...
 * 
 * }
 * </pre>
 *
 * <b>Example code:</b><br>
 * <pre>
 * MyObject obj = new MyObject();
 * ...
 * JSONEncoder jen = new JSONEncoder();
 * jen.encode( obj );
 * </pre>
 *
 * <b>Output:</b><br>
 * <pre>
 * { "id": 1, "name" : "Jose", "class" : "MyObject" }
 * </pre>
 *
 * <h4>Summary of encoding rules for java type into json type</h4>
 * 
 * <table border="1" cellpadding="1" cellspacing="0">
 *   <tr>
 *     <td bgcolor="#CCCCFF">Java Type</td>
 *     <td bgcolor="#CCCCFF">Json Type</td>
 *   </tr>
 *   <tr>
 *     <td>java.util.Map</td>
 *     <td rowspan="2">Object</td>
 *   </tr>
 *   <tr>
 *     <td>Object</td>
 *   </tr>
 *   <tr>
 *     <td>Object[]</td>
 *     <td rowspan="3">Array</td>
 *   </tr>
 *   <tr>
 *     <td>java.util.Collection</td>
 *   </tr>
 *   <tr>
 *     <td>int[], long[], double[], float[], short[], boolean[], char[],
 *       byte[]</td>
 *   </tr>
 *   <tr>
 *     <td>char</td>
 *     <td rowspan="7">String</td>
 *   </tr>
 *   <tr>
 *     <td>java.lang.CharSequence</td>
 *   </tr>
 *   <tr>
 *     <td>java.lang.String</td>
 *   </tr>
 *   <tr>
 *     <td>java.net.URL</td>
 *   </tr>
 *   <tr>
 *     <td>java.net.URI</td>
 *   </tr>
 *   <tr>
 *     <td>java.lang.Character</td>
 *   </tr>
 *   <tr>
 *     <td>java.lang.Class</td>
 *   </tr>
 *   <tr>
 *     <td>java.util.Locale</td>
 *     <td>String (language-country)</td>
 *   </tr>
 *   <tr>
 *     <td>java.lang.Number</td>
 *     <td rowspan="4">number</td>
 *   </tr>
 *   <tr>
 *     <td>int, long, double, float, short, byte</td>
 *   </tr>
 *   <tr>
 *     <td>java.math.BigDecimal</td>
 *   </tr>
 *   <tr>
 *     <td>java.math.BigInteger</td>
 *   </tr>
 *   <tr>
 *     <td>java.util.Date</td>
 *     <td rowspan="4">number (milliseconds since 1970)</td>
 *   </tr>
 *   <tr>
 *     <td>java.sql.Time</td>
 *   </tr>
 *   <tr>
 *     <td>java.sql.Timestamp</td>
 *   </tr>
 *   <tr>
 *     <td>java.util.Calendar</td>
 *   </tr>
 *   <tr>
 *     <td>boolean</td>
 *     <td rowspan="2">true/false</td>
 *   </tr>
 *   <tr>
 *     <td>java.lang.Boolean</td>
 *   </tr>
 *   <tr>
 *     <td>null</td>
 *     <td>null</td>
 *   </tr>
 * </table>
 * 
 * @author Afonso Brandao
 * @version 1.0
 */
public class JSONEncoder  implements JSONConstants{

    private OutputStream out;
    private StringBuffer buffer;
    private Map<Object,StringBuffer> cache;
    private JSONContextConfiguration context;

    /**
     * Creates a default encoder.
     * The result of coding is returned
     * by the toString().
     */
    public JSONEncoder(){
        this( new ByteArrayOutputStream(), new DefaultJSONContext() );
    }

    /**
     * Creates a new encoder.
     * @param out Output stream.
     */
    public JSONEncoder( OutputStream out, JSONContextConfiguration context ){

        if( out == null )
            throw new NullPointerException();
        
        this.out     = out;
        this.cache   = new HashMap<Object,StringBuffer>();
        this.context = context;
    }

    /**
     * Encodes an object into json.
     * @param obj Object to be encoded.
     * @throws IOException Thrown if a problem occurs when encoding.
     */
    public void encode( Object obj ) throws IOException {
        innerEncoder( obj, null );
    }

    /**
     * Encodes an object into json using the
     * type of the class informed.
     * @param obj Object to be encoded.
     * @param clazz Class type.
     * @throws IOException Thrown if a problem occurs when encoding.
     */
    public void encode( Object obj, Class clazz ) throws IOException {
        innerEncoder( obj, clazz );
    }

    private void innerEncoder( Object obj, Class clazz ) throws IOException{
        this.buffer = new StringBuffer();
        cache.clear();
        encoderObject( obj, clazz );
        out.write( buffer.toString().getBytes() );
    }
    
    private void encoderObject( Object obj, Class clazz ) throws IOException{
        
        if( obj instanceof Serializable ){
            encoderObject0( clazz == null? obj.getClass() : clazz, obj );
        }
        else
        if( obj == null )
            encoderObject0( null, null );
        else
            throw new JSONException( obj.getClass().getName() + " : not implement java.io.Serializable" );
    }

    private void encoderObject0( Class<?> clazz, Object obj ) throws IOException{

        if( obj == null )
            innerWrite( NULL );
        else
        if( obj instanceof Collection ){
            collectionEncoder( (Collection)obj );
        }
        else
        if( clazz.isArray() ){
            arrayEncoder( obj );
        }
        else
        if( obj instanceof Map ){
            mapEncoder( (Map<Object,Object>)obj );
        }
        else{
        	JSONConverter converter = this.context.getConverter(clazz);
        	if(converter != null){
        		innerWrite(converter.getJsonObject(obj));
        	}
        	else{
        		encoderObject1( obj, clazz );
        	}
        }
        /*
        if( clazz == Class.class ){
            innerWrite(
                    (new StringBuffer( QUOTE ))
                        .append( String.valueOf( ((Class)obj).getName() ) )
                        .append( QUOTE ) );
        }
        else
        if( CharSequence.class.isAssignableFrom(clazz) )
            string( obj );
        else
        if( Character.class == ClassType.getWrapper( clazz ) ){
            if( obj.equals( '\u0000' ) )
                string( "" );
            else
                string( String.valueOf(obj) );
        }
        else
        if( Locale.class.isAssignableFrom(clazz) )
            string( LocaleUtils.getKey((Locale)obj) );
        else
        if( BigDecimal.class.isAssignableFrom(clazz) )
            innerWrite( ((BigDecimal)obj).toString() );
        else
        if( BigInteger.class.isAssignableFrom(clazz) )
            innerWrite( ((BigInteger)obj).toString() );
        else
        if( URI.class.isAssignableFrom(clazz) )
            string( obj );
        else
        if( URL.class.isAssignableFrom(clazz) )
            string( obj );
        else
        if( MappingBean.isStandardProperty( clazz ) )
            innerWrite( 
                    new StringBuffer(
                        String.valueOf( obj ) ) );
        else
        if( Date.class.isAssignableFrom(clazz) )
            innerWrite(
                    new StringBuffer(
                        String.valueOf( ((Date)obj).getTime() ) ) );
        else
        if( Calendar.class.isAssignableFrom(clazz) )
            innerWrite(
                    new StringBuffer(
                        String.valueOf( ((Calendar)obj).getTime().getTime() ) ) );
        else
        if( obj instanceof Collection )
            collectionEncoder( (Collection)obj );
        else
        if( clazz.isArray() )
            arrayEncoder( obj );
        else
        if( clazz.isEnum() ){
            enumEncoder( obj );
        }
        else
        if( obj instanceof Map )
            mapEncoder( (Map<Object,Object>)obj );
        else
            encoderObject1( obj, clazz );
         */
    }

    private void encoderObject1( Object obj, Class clazz ) throws IOException{
        if( cache.containsKey( obj ) ){
            StringBuffer s = cache.get( obj );
            innerWrite( s != null? s : NULL );
            //innerWrite( s.length() == 0? NULL : s );
            //innerWrite( NULL );
        }
        else{
            StringBuffer old = this.buffer;
            this.buffer = new StringBuffer();

            cache.put( obj, null );
            innerWrite( START_OBJECT );
            MappingBean mapping = MappingBean.getMapping( clazz );
            encoderFields( mapping.getMethods().values(), obj );
            innerWrite( END_OBJECT );
            cache.put( obj, this.buffer );

            StringBuffer tmp = this.buffer;
            this.buffer = old;
            innerWrite( tmp );
        }
    }

    private void encoderFields( Collection<MethodMapping> methods, Object obj ) throws IOException{
        boolean start = true;
        for( MethodMapping m: methods){
            if( start )
                start = false;
            else
                innerWrite( SEPARATOR );
            
            fieldEncoder( m, obj );
        }
        
    }

    private void fieldEncoder( MethodMapping m, Object obj ){
        try{
            Object value = m.getValue( obj );
            encoderObject( m.getId(), null );
            innerWrite( EQUALS );
            encoderObject0( value == null? null : value.getClass(), value );
        }
        catch( JSONException ex ){
            throw new JSONException(
                    String.format( "%s.%s : %s",
                        String.valueOf( m.getParent().getType() ),
                        m.getId(),
                        ex.getMessage() ), ex );
        }
        catch( Throwable ex ){
            throw new JSONException( ex );
        }
    }

    private void collectionEncoder( Collection collection ) throws IOException{
        arrayEncoder( collection.toArray() );
    }

    private void arrayEncoder( Object objects ) throws IOException{
        int length = Array.getLength(objects);

        innerWrite( START_ARRAY );
        for( int i=0;i<length;i++ ){

            if( i != 0 )
                innerWrite( SEPARATOR );

            encoderObject( Array.get(objects, i), null );
        }
        innerWrite( END_ARRAY );
    }

    private void mapEncoder( Map<Object,Object> objects ) throws IOException{
        innerWrite( START_OBJECT );
        Object[] keys = objects.keySet().toArray();

        for( int i=0;i<keys.length; i++ ){
            Object key = keys[i];

            if( i != 0 )
                innerWrite( SEPARATOR );

            encoderObject( key, String.class );
            innerWrite( EQUALS );
            encoderObject( objects.get( key ), null );
        }
        innerWrite( END_OBJECT );
    }

    private void enumEncoder( Object obj ) throws IOException{
        encoderObject0( Integer.class, ((Enum)obj).ordinal() );
    }

    private void string( Object obj ) throws IOException{
        String tmp = String.valueOf( obj );
        StringBuffer buf = new StringBuffer();
        innerWrite( QUOTE );
        for( int i=0;i<tmp.length();i++ ) {
            char ch = tmp.charAt( i );
            switch (ch) {
            case '\\':
            case '"':
            case '/':
                buf.append( '\\' );
                buf.append(ch);
                break;
            case '\b':
                buf.append("\\b");
                break;
            case '\t':
                buf.append("\\t");
                break;
            case '\n':
                buf.append("\\n");
                break;
            case '\f':
                buf.append("\\f");
                break;
            case '\r':
                buf.append("\\r");
                break;
            default:
                if( ((ch >= 0x0020) && (ch <= 0x007e))/* || Character.isUnicodeIdentifierPart( tmp.codePointAt( i ) )*/ )
                    buf.append( tmp.charAt( i ) );
                else{
                    String hex = "000" + Integer.toHexString( ch );
                    buf.append( "\\u")
                            .append( hex.substring( hex.length() - 4 , hex.length() ) );
                }
            }
        }

        innerWrite( buf );
        innerWrite( QUOTE );
    }

    private void innerWrite( StringBuffer value ) throws IOException{
        this.buffer.append( value );
    }

    private void innerWrite( String value ) throws IOException{
        this.buffer.append( value );
    }

    public String toString(){
        if( out instanceof ByteArrayOutputStream )
            return new String( ((ByteArrayOutputStream)out).toByteArray() );
        else
            return null;
    }

    public void flush() throws IOException{
        out.flush();
    }

    public JSONContextConfiguration getContext() {
        return context;
    }
}
