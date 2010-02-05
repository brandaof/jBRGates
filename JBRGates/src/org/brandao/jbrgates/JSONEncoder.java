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
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
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
 * @author Afonso Brandao
 */
public class JSONEncoder  implements JSONConstants{

    private OutputStream out;
    private StringBuffer buffer;
    private Map<Object,StringBuffer> cache;

    /**
     * Creates a default encoder.
     * The result of coding is returned
     * by the toString().
     */
    public JSONEncoder(){
        this( new ByteArrayOutputStream() );
    }

    /**
     * Creates a new encoder.
     * @param out Output stream.
     */
    public JSONEncoder( OutputStream out ){
        this.out   = out;
        this.cache = new HashMap<Object,StringBuffer>();
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
        if( obj == null )
            return;
        else
        if( obj instanceof Serializable ){
            encoderObject0( clazz == null? obj.getClass() : clazz, obj );
        }
        else
            throw new JSONException( obj.getClass().getName() + " : not implement java.io.Serializable" );
    }

    private void encoderObject0( Class<?> clazz, Object obj ) throws IOException{

        if( obj == null )
            innerWrite( NULL );
        else
        if( clazz == Class.class ){
            innerWrite(
                    (new StringBuffer( QUOTE ))
                        .append( String.valueOf( ((Class)obj).getName() ) )
                        .append( QUOTE ) );
        }
        else
        if( clazz == String.class )
            string( obj );
            /*
            innerWrite(
                    (new StringBuffer( QUOTE ))
                        .append( String.valueOf( obj ) )
                        .append( QUOTE ) );
             */
        else
        if( Character.class == ClassType.getWrapper( clazz ) ){
            string( String.valueOf(obj) );
        }
        else
        if( MappingBean.isStandardProperty( clazz ) )
            innerWrite( 
                    new StringBuffer(
                        String.valueOf( obj ) ) );
        else
        if( clazz == Date.class )
            innerWrite(
                    new StringBuffer(
                        String.valueOf( ((Date)obj).getTime() ) ) );
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

    }

    private void encoderObject1( Object obj, Class clazz ) throws IOException{
        if( cache.containsKey( obj ) ){
            StringBuffer s = cache.get( obj );
            innerWrite( s.length() == 0? NULL : s );
        }
        else{
            cache.put( obj, buffer );
            StringBuffer old = this.buffer;
            this.buffer = new StringBuffer();
            innerWrite( START_OBJECT );
            MappingBean mapping = MappingBean.getMapping( clazz );
            encoderFields( mapping.getMethods().values(), obj );
            innerWrite( END_OBJECT );

            StringBuffer tmp = this.buffer;
            this.buffer = old;
            innerWrite( tmp );
            this.buffer = old;
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
                if( Character.isUnicodeIdentifierPart( tmp.codePointAt( i ) ) )
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

    public String toString(){
        if( out instanceof ByteArrayOutputStream )
            return new String( ((ByteArrayOutputStream)out).toByteArray() );
        else
            return null;
    }

    public void flush() throws IOException{
        out.flush();
    }
}
