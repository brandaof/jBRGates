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

package org.brandao.jbrgates.converters;

import org.brandao.jbrgates.FactoryBean;
import org.brandao.jbrgates.JSONException;

/**
 *
 * @author Brandao
 * @version 1.1
 */
public class CharacterConverter extends StringConverter{

	private static final StringBuffer EMPTY_STRING = 
			new StringBuffer("\"\"");
	
    public StringBuffer getJsonObject(Object value) throws JSONException {
    	return value.equals('\u0000')? EMPTY_STRING : super.getJsonObject(value);
    }
	
    public Object getObject(Object value, FactoryBean factory, Class<?> baseType) throws JSONException {
        String val = String.valueOf(value);
        return val.length() == 0? '\u0000' : val.charAt(0);
    }

}
