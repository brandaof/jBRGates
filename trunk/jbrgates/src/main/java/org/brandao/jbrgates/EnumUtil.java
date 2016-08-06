package org.brandao.jbrgates;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EnumUtil {

	private static final Class rootEnumClass;
	
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
	
    private Class enumClass;

    public EnumUtil(Class enumClass){
        this.enumClass = enumClass;
    }

    public Object getEnumConstants(){
        return getEnumConstants(this.enumClass);
    }
    
    public static Object getEnumConstants(Class clazz){
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

    public static Object getEnumConstant( Class enumClazz, Integer index ){
        Object cons = getEnumConstants(enumClazz);
        return Array.get(cons, index.intValue());
    }

    public static Object valueOf( Class enumClazz, String value ) 
    		throws ClassNotFoundException, NoSuchMethodException, SecurityException, 
    		IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        return valueOf.invoke(rootEnumClass, new Object[]{enumClazz,value});

    }


}
