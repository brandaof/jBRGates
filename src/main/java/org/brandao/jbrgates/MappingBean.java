/*
 * jBRGates http://jbrgates.brandao.org/
 * Copyright (C) 2006-2016 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brandao.jbrgates;

import java.io.Externalizable;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Afonso Brandao
 */
class MappingBean {

    private static Map<Class<?>,MappingBean> cache = new HashMap<Class<?>,MappingBean>();

    private boolean external;

    private Class<?> type;

    private Map<String, MethodMapping> getters;

    public MappingBean( Class<?> type ){
        this.type = type;
        this.getters = new HashMap<String, MethodMapping>();
    }

    public boolean isExternal() {
        return external;
    }

    public Class<?> getType() {
        return type;
    }

    public Map<String, MethodMapping> getMethods() {
        return getters;
    }


    public static MappingBean getMapping( Class<?> clazz ){
        MappingBean mapping = cache.get( clazz );
        return mapping == null? create( clazz ) : mapping;
    }

    private synchronized static MappingBean create( Class<?> clazz ){
        if( cache.containsKey( clazz ) )
            return cache.get(clazz);
        else{
            MappingBean mapping = create0( clazz );
            cache.put( clazz , mapping);
            return mapping;
        }
        
    }

    private static MappingBean create0( Class<?> clazz ){
        
        if( Serializable.class.isAssignableFrom( clazz ) )
            return createSerializableMapping( clazz );
        else
            throw new JSONException( clazz.getName() + " : not implement java.io.Serializable" );
    }

    private static MappingBean createSerializableMapping( Class<?> clazz ){

        if( cache.containsKey( clazz ) )
            return cache.get( clazz );
        
        MappingBean mapping = new MappingBean( clazz );
        cache.put( clazz, mapping );
        mapping.external = clazz.isAssignableFrom( Externalizable.class );
        if( !mapping.isExternal() )
            return createSerializableMapping0( mapping, clazz );
        else
            return mapping;
    }

    private static MappingBean createSerializableMapping0( MappingBean mapping, Class<?> clazz ){
        Class<?> superClass = clazz.getSuperclass();
        if( isSerializable( superClass ) )
            createSerializableMapping0( mapping, superClass );

        //addClassType( mapping, clazz );
        fields( mapping, clazz );
        return mapping;
    }

    private static void fields( MappingBean mapping, Class<?> clazz ){
        Field[] fields = clazz.getDeclaredFields();

        for( Field f: fields ){
            int mod = f.getModifiers();
            if( !Modifier.isStatic( mod ) && !Modifier.isTransient( mod ) )
                mapping.addMethod( f.getName(), getGetter( f ), getSetter( f ) );
        }

    }

    @Deprecated
    private static void addClassType( MappingBean mapping, Class<?> clazz ){
        try{
            mapping.addMethod( "class" , Object.class.getDeclaredMethod( "getClass" ), null);
        }
        catch( Exception ex ){
            throw new JSONException(
                    String.format( "%s : %s",
                        clazz.getName(),
                        ex.getMessage() ) );
        }

    }
    
    private static Method getGetter( Field field ){
        try{
            String fieldName = field.getName();
            String pre = field.getType() == Boolean.TYPE? "is" : "get";
            String methodName = pre +
                                String.valueOf( fieldName.charAt( 0 ) ).toUpperCase() +
                                fieldName.substring( 1, fieldName.length() );

            return field.getDeclaringClass().getMethod( methodName );
        }
        catch( JSONException ex ){
            throw new JSONException(
                    String.format( "%s.%s : %s",
                        String.valueOf( field.getDeclaringClass() ),
                        field.getName(),
                        ex.getMessage() ) );
        }
        catch( Throwable ex ){
            throw new JSONException( ex );
        }
    }

    private static Method getSetter( Field field ){
        try{
            String fieldName = field.getName();
            String pre = "set";
            String methodName = pre +
                                String.valueOf( fieldName.charAt( 0 ) ).toUpperCase() +
                                fieldName.substring( 1, fieldName.length() );

            return field.getDeclaringClass().getMethod( methodName, field.getType() );
        }
        catch( JSONException ex ){
            throw new JSONException(
                    String.format( "%s.%s : %s",
                        String.valueOf( field.getDeclaringClass() ),
                        field.getName(),
                        ex.getMessage() ) );
        }
        catch( Throwable ex ){
            throw new JSONException( ex );
        }
    }

    private void addMethod( String id, Method getter, Method setter ){
        this.getMethods().put( id , new MethodMapping( id, getter, setter, this, getter.getGenericReturnType() ) );
    }

    private static boolean isSerializable( Class<?> clazz ){
        return clazz != null &&
               clazz != Object.class &&
               Serializable.class.isAssignableFrom( clazz );
    }

    public static boolean isStandardProperty(Class<?> clazz) {
        return clazz.isPrimitive()                  ||
            clazz.isAssignableFrom(Float.class)     ||
            clazz.isAssignableFrom(Short.class)     ||
            clazz.isAssignableFrom(Long.class)      ||
            clazz.isAssignableFrom(Byte.class)      ||
            clazz.isAssignableFrom(Integer.class)   ||
            clazz.isAssignableFrom(String.class)    ||
            clazz.isAssignableFrom(Double.class)    ||
            //clazz.isAssignableFrom(Character.class) ||
            clazz.isAssignableFrom(Boolean.class);
    }

}
