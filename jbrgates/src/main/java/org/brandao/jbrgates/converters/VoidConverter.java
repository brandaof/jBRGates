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
import org.brandao.jbrgates.JSONConverter;
import org.brandao.jbrgates.JSONException;

/**
 *
 * @author Brandao
 * @version 1.1
 */
public class VoidConverter implements JSONConverter{

    public StringBuffer getJsonObject(Object value) throws JSONException {
    	return 
			new StringBuffer("\"")
    			.append(String.valueOf(value))
    		.append("\"");
    }

    public Object getObject(Object value, FactoryBean factory, Class baseType) throws JSONException {
    	return Void.class;
    }

}
