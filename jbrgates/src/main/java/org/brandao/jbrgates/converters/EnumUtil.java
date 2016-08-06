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

package org.brandao.jbrgates.converters;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class EnumUtil {

	private static final Class<?> rootEnumClass;
	
	private static final Method valueOf;
	
	static{
		try{
			rootEnumClass =
                Class.forName("java.lang.Enum");
			valueOf =
				rootEnumClass.getMethod(
	                "valueOf",
	                new Class[]{Class.class,String.class});
		}
		catch(Throwable e){
			throw new ExceptionInInitializerError(e);
		}
	}
	
    private Class<?> enumClass;

    public EnumUtil(Class<?> enumClass){
        this.enumClass = enumClass;
    }

    public Object getEnumConstants(){
        return getEnumConstants(this.enumClass);
    }
    
    public static Object getEnumConstants(Class<?> clazz){
        try{
            Method m =
                Class.class.getMethod("getEnumConstants", new Class[]{});
            return m.invoke(clazz, new Object[]{});
        }
        catch( Exception e ){
            throw new RuntimeException(e);
        }

    }

    public Object valueOf( String value ) throws ClassNotFoundException, 
    		NoSuchMethodException, SecurityException, IllegalAccessException, 
    		IllegalArgumentException, InvocationTargetException{
        return valueOf(this.enumClass,value);
    }

    public static Object getEnumConstant( Class<?> enumClazz, Integer index ){
        Object cons = getEnumConstants(enumClazz);
        return Array.get(cons, index.intValue());
    }

    public static Object valueOf( Class<?> enumClazz, String value ) 
    		throws ClassNotFoundException, NoSuchMethodException, SecurityException, 
    		IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        return valueOf.invoke(rootEnumClass, new Object[]{enumClazz,value});

    }

}
