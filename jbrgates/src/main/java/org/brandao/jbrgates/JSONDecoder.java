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
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This is a json decoder.
 * <p>If not informed "class", the method decode(Class&lt;T&gt; type) should be used.</p>
 * 
 * <pre>
 * Ex1:
 * 
 * Class:
 * 
 * public class MyObject implements Serializable{
 *   private int id;
 *   private String name;
 *
 *   ...
 *
 * }
 *
 * code:
 * ...
 * JSONDecoder jde = new JSONDecoder( "{ "id": 1, "name" : "Jose", "class" : "MyObject" }" );
 * MyObject obj = (MyObject)jde.decode();
 * </pre>
 * 
 * <pre>
 * 
 * Ex2:
 * ...
 * JSONDecoder jde = new JSONDecoder( "{ "id": 1, "name" : "Jose" }" );
 * MyObject obj = jde.decode( MyObject.class );
 * </pre>
 * 
 * <h4>Summary of decoding rules for json type into java type</h4>
 *
 * <table border="1" cellpadding="1" cellspacing="0">
 *   <tr>
 *     <td bgcolor="#CCCCFF">Json Type</td>
 *     <td bgcolor="#CCCCFF">Java Type</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="2">Object</td>
 *     <td>java.util.Map</td>
 *   </tr>
 *   <tr>
 *     <td>Object</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="3">Array</td>
 *     <td>Object[]</td>
 *   </tr>
 *   <tr>
 *     <td>java.util.Collection</td>
 *   </tr>
 *   <tr>
 *     <td>int[], long[], double[], float[], short[], boolean[], char[],
 *       byte[]</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="11">String</td>
 *     <td>char</td>
 *   </tr>
 *   <tr>
 *     <td>java.lang.CharSequence</td>
 *   </tr>
 *   <tr>
 *     <td>java.lang.Enum</td>
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
 *     <td>String (language-country)</td>
 *     <td>java.util.Locale</td>
 *   </tr>
*   <tr>
 *     <td rowspan="4">number</td>
 *     <td>java.lang.Number</td>
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
 *     <td rowspan="2">true/false</td>
 *     <td>boolean</td>
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
public class JSONDecoder implements JSONConstants{

    private InputStream in;
    private FactoryBean factory;
    private JSONContextConfiguration context;

    /**
     * Creates a default decoder.
     * @param value JSON object.
     */
    public JSONDecoder( String value ){
        this( new ByteArrayInputStream( value.getBytes() ),
                new DefaultIOCFactoryBean(), new DefaultJSONContext() );
    }

    /**
     * Create a new decoder.
     * @param in Input stream.
     */
    public JSONDecoder( InputStream in ){
        this( in, new DefaultIOCFactoryBean(), new DefaultJSONContext() );
    }

    /**
     * Create a new decoder.
     * @param in Input stream
     * @param factoryBean Bean factory.
     */
    public JSONDecoder( InputStream in, FactoryBean factoryBean, JSONContextConfiguration context ){
        this.in = in;
        this.factory = factoryBean;
        this.context = context;
        
        if( in == null )
            throw new NullPointerException( "input stream" );

        if( factory == null )
            throw new NullPointerException( "bean factory" );

        if( context == null )
            throw new NullPointerException( "context" );
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
     * Decode a JSON object to specific type.
     * @param <T> Type.
     * @param type Class type.
     * @return Object.
     * @throws JSONException Thrown if a problem occurs when decoding.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Decodes a JSON object in a specific type.
     * @param type Class type.
     * @return Object.
     * @throws JSONException Thrown if a problem occurs when decoding.
     */
    public Object decode( Type type ) throws JSONException{
        try{
            Class<?> clazz = getClass( type );
            JSONParsing parsing = new JSONParsing( in );
            return decoder0( parsing.object( clazz ), type );
        }
        catch( JSONException e ){
            throw e;
        }
        catch( Exception e ){
            throw new JSONException( e );
        }
    }

    /**
     * Decodes a JSON object in a specific collection type.
     * @param collectionType Collection type.
     * @param entityType Entity type.
     * @return Collection.
     * @throws JSONException Thrown if a problem occurs when decoding.
     */
    public Object decodeCollection( 
    		Type collectionType,  
			Type entityType) throws JSONException{
    	
    	ParameterizedType type = 
			new ParameterizedTypeImp(collectionType, new Type[]{entityType});
    	
    	return this.decode(type);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private Object decoder0( Object data, Type clazz ) throws Exception{
        Class<?> clazzType = getClass( clazz );
        
        if(clazzType == null)
        	clazzType = Object.class;
        
        if( data == null )
            return null;
        else
        if( data instanceof List ){
            return array( (List<Object>)data, clazz );
        }
        else
    	if( clazzType != Object.class && clazzType.isAssignableFrom( Map.class ) ){
            return getMap( (Map)data, clazz );
    	}
    	else{
            return getObject( data, clazz );
    	}
        /*
        else
        if( clazz != null ){
            if( Date.class.isAssignableFrom( clazzType ) )
                return objectDate( data, clazzType );
            else
            if( Calendar.class.isAssignableFrom( clazzType ) )
                return objectDate( data, clazzType );
            else
            if( clazzType != Object.class && clazzType.isAssignableFrom( Map.class ) )
                return getMap( (Map)data, clazz );
            else
            if( clazzType.isEnum() )
                return objectEnum( data, clazzType );
            
        }
        */

    }

    /*
    private Object objectEnum( Object data, Class clazz ){
        int index = (Integer)toValue( (String)data, Integer.class );
        return clazz.getEnumConstants()[index];
    }
    
    private Object objectDate( Object data, Class clazz ){
        if( Date.class.isAssignableFrom( clazz ) ){
            Date dta = (Date) factory.getInstance( clazz );
            dta.setTime( (Long)toValue( (String)data, Long.class ) );
            return dta;
        }
        else
        if( Calendar.class.isAssignableFrom( clazz ) ){
            long time = (Long)toValue( (String)data, Long.class );
            Calendar dta = (Calendar) factory.getInstance( clazz );
            dta.setTime( new Date( time ) );
            return dta;
        }
        else
            throw new JSONException( "invalid date" );
    }
    */
    
	@SuppressWarnings("unchecked")
	private Object array( List<?> data, Type type ) throws Exception{
        
        Class<?> classType = getClass( type );
        Class<?> collectionType = getCollectionType( type );
        
        if( classType == null )
            return data;
        else
        if( Collection.class.isAssignableFrom( classType ) ){
            Collection<Object> set = (Collection<Object>) factory.getInstance( classType );
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

	@SuppressWarnings({ "unchecked", "unused" })
	private Object getMap( Map<?,?> map, Type type ) throws Exception{
        Class<?> classType = getClass( type );
        Type[] types = getMapType( type );
        Type keyTpye = types[0];
        Type valueType = types[1];

        Set<?> keys = map.keySet();
        Map<Object,Object> values = (Map<Object,Object>)factory.getInstance( classType );
        for( Object k: keys ){
            Object key = decoder0( k, String.class );
            Object value = decoder0( map.get( k ), valueType );
            values.put( (String)key , value);
        }

        return values;
    }

    private Object getObject( Object data, Type clazz ) throws Exception{

        if( !(data instanceof Map) )
            return toValue( String.valueOf( data ), clazz );
        else{
            Map<?,?> values = (Map<?,?>)data;
            Class<?> classType = getClass( (Map<?,?>)values, clazz );
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

    private Class<?> getClass( Map<?,?> mappedObj, Type classType ) throws ClassNotFoundException{
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

            JSONConverter converter = context.getConverter((Class<?>) type);

            if(converter == null)
                converter = context.getDefaultConverter();

            return converter.getObject(value, this.factory, (Class<?>)type);
        }
        catch( JSONException e ){
            throw e;
        }
        catch( Exception e ){
            throw new JSONException( e );
        }
    }

    private static Class<?> getClass( java.lang.reflect.Type type ){
        if( type instanceof ParameterizedType )
            return (Class<?>)((ParameterizedType)type).getRawType();
        else
            return (Class<?>)type;
    }

    private static Class<?> getCollectionType( java.lang.reflect.Type type ){
        if( type instanceof ParameterizedType )
            return (Class<?>)((ParameterizedType)type).getActualTypeArguments()[0];
        else
            return Object.class;
    }

    private static Type[] getMapType( java.lang.reflect.Type type ){
        if( type instanceof ParameterizedType )
            return ((ParameterizedType)type).getActualTypeArguments();
        else
            return new Class[]{String.class, Object.class};
    }

    public JSONContextConfiguration getContext() {
        return context;
    }

    private static class ParameterizedTypeImp implements ParameterizedType{

        private Type rawType;
        private Type[] typeArguments;

        public ParameterizedTypeImp( Type rawType, Type[] typeArguments ){
            this.rawType = rawType;
            this.typeArguments = typeArguments;
        }
    	
		public Type[] getActualTypeArguments() {
			return typeArguments;
		}

		public Type getRawType() {
			return rawType;
		}

		public Type getOwnerType() {
			return null;
		}
    	
    }
}
