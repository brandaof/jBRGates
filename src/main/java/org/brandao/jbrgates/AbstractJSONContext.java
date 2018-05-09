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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.brandao.jbrgates.converters.BigDecimalConverter;
import org.brandao.jbrgates.converters.BigIntegerConverter;
import org.brandao.jbrgates.converters.CalendarConverter;
import org.brandao.jbrgates.converters.CharacterConverter;
import org.brandao.jbrgates.converters.ClassConverter;
import org.brandao.jbrgates.converters.DateConverter;
import org.brandao.jbrgates.converters.DefaultConverter;
import org.brandao.jbrgates.converters.EnumConverter;
import org.brandao.jbrgates.converters.LocaleConverter;
import org.brandao.jbrgates.converters.StringConverter;
import org.brandao.jbrgates.converters.URIConverter;
import org.brandao.jbrgates.converters.URLConverter;
import org.brandao.jbrgates.converters.VoidConverter;

/**
 * Abstract implementation of the 
 * {@link JSONContext} interface
 *  
 * @author Brandao
 * @version 1.1
 */
public abstract class AbstractJSONContext 
	implements JSONContext, JSONContextConfiguration{

    private Map<Class<?>,JSONConverter> converters;
    
    private FactoryBean factory;
    
    private JSONConverter defaultConverter;

    public AbstractJSONContext(){
        this.converters       = new LinkedHashMap<Class<?>,JSONConverter>();
        this.factory          = new DefaultIOCFactoryBean();
        this.defaultConverter = new StringConverter();
        loadConverters();
    }

    private void loadConverters(){
        addConverter(BigDecimal.class,		new BigDecimalConverter());
        addConverter(BigInteger.class, 		new BigIntegerConverter());
        addConverter(Class.class, 			new ClassConverter());
        addConverter(Locale.class, 			new LocaleConverter());
        addConverter(String.class, 			new StringConverter());
        addConverter(URI.class, 			new URIConverter());
        addConverter(URL.class, 			new URLConverter());
        addConverter(Enum.class, 			new EnumConverter());
        addConverter(Date.class, 			new DateConverter());
        addConverter(Calendar.class,		new CalendarConverter());

        addConverter(boolean.class,			new DefaultConverter(Boolean.class));
        addConverter(byte.class,			new DefaultConverter(Byte.class));
        addConverter(char.class, 			new CharacterConverter());
        addConverter(double.class,			new DefaultConverter(Double.class));
        addConverter(float.class,			new DefaultConverter(Float.class));
        addConverter(int.class,				new DefaultConverter(Integer.class));
        addConverter(long.class,			new DefaultConverter(Long.class));
        addConverter(short.class,			new DefaultConverter(Short.class));
        addConverter(void.class,			new VoidConverter());

        addConverter(CharSequence.class,	new StringConverter());
        addConverter(Boolean.class,			new DefaultConverter(Boolean.class));
        addConverter(Byte.class,			new DefaultConverter(Byte.class));
        addConverter(Character.class, 		new CharacterConverter());
        addConverter(Double.class,			new DefaultConverter(Double.class));
        addConverter(Float.class,			new DefaultConverter(Float.class));
        addConverter(Integer.class,			new DefaultConverter(Integer.class));
        addConverter(Long.class,			new DefaultConverter(Long.class));
        addConverter(Short.class,			new DefaultConverter(Short.class));
        addConverter(Void.class,			new VoidConverter());
        
    }

    /* encode */
    
    public String encode(Object value) throws JSONException {
        try{
            JSONEncoder encoder = new JSONEncoder(new ByteArrayOutputStream(), this);
            encoder.encode(value);
            return encoder.toString();
        }
        catch(Exception e){
            throw new JSONException(e);
        }
    }
    
	public void encode(Object value, OutputStream stream)
			throws JSONException {
        try{
            JSONEncoder encoder = new JSONEncoder(stream, this);
            encoder.encode(value);
        }
        catch( Exception e ){
            throw new JSONException(e);
        }
	}
    
    /* decode */
	
    public Object decode(String value, Class<?> type) throws JSONException {
        return decode(value, (Type)type);
    }

    public Object decode(String value) throws JSONException {
        return decode(value, (Type)null);
    }

    public Object decode(String value, Type type) throws JSONException{
    	return 
			value == null? 
				null : 
				this.decode(new ByteArrayInputStream(value.getBytes()), type);
    }
    
    public Object decodeCollection(String value, Type collectionType,
    		Type entityType) throws JSONException{
    	return 
			value == null? 
				null : 
				this.decodeCollection(
					new ByteArrayInputStream(value.getBytes()), 
					collectionType, entityType);
    }
    
    /* decode stream */
    
	public Object decode(InputStream stream) throws JSONException {
		return this.decode(stream, (Type)null);
	}

	public Object decode(InputStream stream, Class<?> type)
			throws JSONException {
		return this.decode(stream, (Type)type);
	}

	public Object decode(InputStream stream, Type type) throws JSONException {
        try{
            JSONDecoder encoder = new JSONDecoder(stream, this.factory, this);
            return encoder.decode(type);
        }
        catch( Exception e ){
            throw new JSONException(e);
        }
	}

	public Object decodeCollection(InputStream stream, Type collectionType,
			Type entityType) throws JSONException {
        try{
            JSONDecoder encoder = new JSONDecoder(stream, this.factory, this);
            return encoder.decodeCollection(collectionType, entityType);
        }
        catch( Exception e ){
            throw new JSONException(e);
        }
	}

    public void addConverter(Class<?> type, JSONConverter converter) {
        this.converters.put(type, converter);
    }
	
    public JSONConverter getConverter(Class<?> type) {
    	
    	if(type == null){
    		return null;
    	}
    	else;
    	if(type.isEnum()){
    		return this.converters.get(Enum.class);
    	}
    	else
    	if(Date.class.isAssignableFrom(type)){
    		return this.converters.get(Date.class);
    	}
    	else
    	if(Calendar.class.isAssignableFrom(type)){
    		return this.converters.get(Calendar.class);
    	}
    	else
        if( CharSequence.class.isAssignableFrom(type) )
    		return this.converters.get(CharSequence.class);
        else    		
    		return this.converters.get(type);
    }

    public void removeConverter(Class<?> type) {
        this.converters.remove(type);
    }

    public void setFactoryBean( FactoryBean factory ){
        this.factory = factory;
    }

    public JSONConverter getDefaultConverter(){
        return this.defaultConverter;
    }

    public FactoryBean getFactoryBean(){
        return this.factory;
    }

}
