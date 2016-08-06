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

/**
 * Defines the object that provides the necessary 
 * resources to encode and decode a specific java type.
 * 
 * @author Brandao
 * @version 1.1
 */
public interface JSONConverter {

	/**
	 * Convert the java object to json object.
	 * @param value Java object.
	 * @return Json object.
	 * @throws JSONException Thrown if a problem occurs when converting.
	 */
    StringBuffer getJsonObject(Object value) throws JSONException;

    /**
	 * Convert the json object to java object.
     * @param value Json object.
     * @param factory Bean factory.
     * @param baseType Base type to converting object. (eg. Enum type)
     * @return Java object.
	 * @throws JSONException Thrown if a problem occurs when converting.
     */
    Object getObject(Object value, FactoryBean factory, Class<?> baseType) throws JSONException;

}
