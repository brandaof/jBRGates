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
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Decodes a JSON object.<br>
 * If not informed "class", the method 
 * decode(Class&lt;T&gt; type) should be used.<br>
 * 
 * Ex1:<br>
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
 * ...
 * JSONDecoder jde = new JSONDecoder( "{ "id": 1, "name" : "Jose", "class" : "MyObject" }" );
 * MyObject obj = (MyObject)jde.decode();
 * </pre>
 *
 * Ex2:<br>
 * <pre>
 * ...
 * JSONDecoder jde = new JSONDecoder( "{ "id": 1, "name" : "Jose" }" );
 * MyObject obj = jde.decode( MyObject.class );
 * </pre>
 *
 * @author Afonso Brandao
 */
public class JSONDecoder implements JSONConstants{

    private InputStream in;
    private FactoryBean factory;

    /**
     * Creates a default decoder.
     * @param value JSON object.
     */
    public JSONDecoder( String value ){
        this( new ByteArrayInputStream( value.getBytes() ),
                new DefaultIOCFactoryBean() );
    }

    /**
     * Create a new decoder.
     * @param in Input stream.
     */
    public JSONDecoder( InputStream in ){
        this( in, new DefaultIOCFactoryBean() );
    }

    /**
     * Create a new decoder.
     * @param in Input stream
     * @param factoryBean Bean factory.
     */
    public JSONDecoder( InputStream in, FactoryBean factoryBean ){
        this.in = in;
        this.factory = factoryBean;
    }

    /**
     * Decodes a JSON object.
     * @return Object.
     * @throws JSONException Thrown if a problem occurs when decoding.
     */
    public Object decode() throws JSONException{
        return decode( null );
    }

    /**
     * Decodes a JSON object in a specific type.
     * @param <T> 
     * @param type Class type.
     * @return Object.
     * @throws JSONException Thrown if a problem occurs when decoding.
     */
    public <T> T decode( Class<T> type ) throws JSONException{
        try{
            JSONParsing parsing = new JSONParsing( in );
            return (T) decoder0( parsing.object( type ), type );
        }
        catch( JSONException e ){
            throw e;
        }
        catch( Exception e ){
            throw new JSONException( e );
        }
    }

    private Object decoder0( Object data, Type clazz ) throws Exception{
        Class clazzType = getClass( clazz );
        if( data instanceof List ){
            return array( (List<Object>)data, clazz );
        }
        else
        if( clazz != null && Date.class.isAssignableFrom( clazzType ) ){
            return objectDate( data, clazzType );
        }
        else
        if( clazz != null && clazzType != Object.class && clazzType.isAssignableFrom( Map.class ) ){
            return getMap( (Map)data, clazz );
        }
        if( clazz != null && clazzType.isEnum() )
            return objectEnum( data, clazzType );
        else
            return getObject( data, clazz );

    }

    private Object objectEnum( Object data, Class clazz ){
        int index = (Integer)toValue( (String)data, Integer.class );
        return clazz.getEnumConstants()[index];
    }

    private Object objectDate( Object data, Class clazz ){
        Date dta = (Date) factory.getInstance( clazz );
        dta.setTime( (Long)toValue( (String)data, Long.class ) );
        return dta;
    }

    private Object array( List<Object> data, Type type ) throws Exception{
        
        Class classType = getClass( type );
        Class collectionType = getCollectionType( type );
        
        if( classType == null )
            return data;
        else
        if( Collection.class.isAssignableFrom( classType ) ){
            Collection<Object> set = (Collection) factory.getInstance( classType );
            for( Object dta: data )
                set.add( decoder0( dta, collectionType ) );

            return set;
        }
        else
        if( classType.isArray() ){
            Object array = Array.newInstance(classType.getComponentType(), data.size() );
            for( int i=0;i<data.size();i++ )
                Array.set(array, i, decoder0( data.get(i), classType.getComponentType() ));

            return array;
        }
        else
            return null;
    }

    private Object getMap( Map map, Type type ) throws Exception{
        Class classType = getClass( type );
        Type[] types = getMapType( type );
        Type keyTpye = types[0];
        Type valueType = types[1];

        Set<Object> keys = map.keySet();
        Map<String,Object> values = (Map)factory.getInstance( classType );
        for( Object k: keys ){
            Object key = decoder0( k, String.class );
            Object value = decoder0( map.get( k ), valueType );
            values.put( (String)key , value);
        }

        return values;
    }

    private Object getObject( Object data, Type clazz ) throws Exception{

        if( !(data instanceof Map) )
            return toValue( (String)data, clazz );
        else{
            Map values = (Map)data;
            Class classType = getClass( (Map<String,Object>)values, clazz );
            MappingBean mb = MappingBean.getMapping( getClass( classType ) );
            Object instance = factory.getInstance( classType );

            Map<String,MethodMapping> attributes = mb.getMethods();
            Set<String> keys = attributes.keySet();
            for( String key: keys ){
                if( !"class".equals( key ) ){
                    MethodMapping method = attributes.get( key );
                    Object value = decoder0( values.get( key ), method.getType() );
                    if( value != null )
                        method.setValue( value, instance );
                }
            }
            return instance;
            
        }
    }

    private Class getClass( Map<String,Object> mappedObj, Type classType ) throws ClassNotFoundException{
        if( classType == null ){
            String clazzName = (String) mappedObj.get( "class" );
            if( clazzName == null )
                throw new JSONException( "undetermined class: not found \"class\"" );
            else
                return Class.forName( clazzName, true,
                        Thread.currentThread().getContextClassLoader() );
        }
        else
            return getClass( classType );
    }

    private Object toValue( String value, Type type ){
        try{
            if( value == null )
                return null;
            else
            if( type == null || type == String.class || type == Object.class )
                return value;
            else
                return ClassType.getWrapper( (Class)type )
                    .getMethod( "valueOf" , String.class )
                        .invoke( type , value);
        }
        catch( Exception e ){
            throw new JSONException( e );
        }
    }

    private static Class getClass( java.lang.reflect.Type type ){
        if( type instanceof ParameterizedType )
            return (Class)((ParameterizedType)type).getRawType();
        else
            return (Class)type;
    }

    private static Class getCollectionType( java.lang.reflect.Type type ){
        if( type instanceof ParameterizedType )
            return (Class)((ParameterizedType)type).getActualTypeArguments()[0];
        else
            return Object.class;
    }

    private static Type[] getMapType( java.lang.reflect.Type type ){
        if( type instanceof ParameterizedType )
            return ((ParameterizedType)type).getActualTypeArguments();
        else
            return new Class[]{String.class, Object.class};
    }

}
