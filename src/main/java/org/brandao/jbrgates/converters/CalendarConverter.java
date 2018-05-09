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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.brandao.jbrgates.FactoryBean;
import org.brandao.jbrgates.JSONConverter;
import org.brandao.jbrgates.JSONEncoder;
import org.brandao.jbrgates.JSONException;

/**
 *
 * @author Brandao
 * @version 1.1
 */
public class CalendarConverter implements JSONConverter{

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.sss'Z'");
	
    public StringBuffer getJsonObject(Object value) throws JSONException {
    	try{
	    	Calendar cal = (Calendar)value;
	        return 
        		new StringBuffer(JSONEncoder.QUOTE)
	        		.append(sdf.format(cal.getTime()))
        		.append(JSONEncoder.QUOTE);
    	}
    	catch(Throwable e){
    		throw new JSONException(e);
    	}
    }

    public Object getObject(Object value, FactoryBean factory, Class<?> baseType) throws JSONException {
    	try{
    		String strValue = (String)value;
	        Calendar dta    = (Calendar) factory.getInstance(baseType);
	        Date date       = sdf.parse(strValue);
	        dta.setTime(date);
	        return dta;
    	}
    	catch(Throwable e){
    		throw new JSONException(e);
    	}
    }

}
