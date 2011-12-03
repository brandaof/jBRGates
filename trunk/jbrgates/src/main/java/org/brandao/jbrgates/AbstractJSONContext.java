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

import java.util.LinkedHashMap;
import java.util.Map;
import org.brandao.jbrgates.converters.DefaultConverter;

/**
 *
 * @author Brandao
 * @version 1.1
 */
public abstract class AbstractJSONContext implements JSONContext, JSONContextConfiguration{

    private Map<Class,JSONConverter> converters;
    private FactoryBean factory;
    private DefaultConverter defaultConverter;

    public AbstractJSONContext(){
        this.converters = new LinkedHashMap<Class,JSONConverter>();
        this.factory = new DefaultIOCFactoryBean();
        this.defaultConverter = new DefaultConverter();
    }

    public String encode(Object value) throws JSONException {
        try{
            JSONEncoder encoder = new JSONEncoder();
            encoder.encode(value);
            return encoder.toString();
        }
        catch( Exception e ){
            throw new JSONException(e);
        }
    }

    public Object decode(String value, Class<?> type) throws JSONException {
        try{
            JSONDecoder encoder = new JSONDecoder(value);
            return encoder.decode(type);
        }
        catch( Exception e ){
            throw new JSONException(e);
        }
    }

    public Object decode(String value) throws JSONException {
        return decode(value, null);
    }

    public void addConverter(Class type, JSONConverter converter) {
        this.converters.put(type, converter);
    }

    public JSONConverter getConverter(Class type) {
        return this.converters.get(type);
    }

    public void removeConverter(Class type) {
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
