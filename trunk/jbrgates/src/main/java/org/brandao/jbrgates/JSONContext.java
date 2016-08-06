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
 * resources to encode and decode a Java object to Json.
 * 
 * <p>The method {@link #encode(Object)} convert a java object to json object.</p>
 * <pre>
 * Ex:
 *     double[] javaObject = new double[]{1.0,25.0};
 *     JsonContext context = new DefaultJsonContext();
 *     String jsonObject = context.encode(javaObject);
 *     
 * Output:
 * [1.0, 25.0]
 * </pre>
 *     
 * 
 * <pre>
 * Ex2:
 * 
 *     MyObject javaObject = new MyObject();
 *     javaObject.filed1 = 12L;
 *     javaObject.field2 = "Test";
 *     
 *     JsonContext context = new DefaultJsonContext();
 *     String jsonObject = context.encode(javaObject);
 *     
 * Output:
 * {"field1": 12, "field2": "Test"}
 * </pre>
 * 
 * <pre>
 * Ex3:
 * 
 *     MyObject javaObject = new MyObject();
 *     javaObject.filed1 = new Date();
 *     javaObject.filed2 = MyEnum.VALUE1;
 *     
 *     JsonContext context = new DefaultJsonContext();
 *     String jsonObject = context.encode(javaObject);
 *     
 * Output:
 * {"field1": "2016-08-06 00:00:00", "field2": "VALUE1"}
 * </pre>
 * 
 * <p>The method {@link #decode(Object)} convert a json object to java object.</p>
 * <pre>
 * Ex:
 *     String jsonObject   = "[1.0, 25.0]";
 *     JsonContext context = new DefaultJsonContext();
 *     double[] javaObject = context.decode(jsonObject, double[].class);
 *     
 * </pre>
 * 
 * <pre>
 * Ex2:
 * 
 *     String jsonObject   = "{\"field1\": 12, \"field2\": \"Test\"};
 *     JsonContext context = new DefaultJsonContext();
 *     MyObject javaObject = context.decode(jsonObject, MyObject.class);
 *     
 * </pre>
 * 
 * @author Brandao
 * @version 1.1
 */
public interface JSONContext {

	/**
	 * Encode a object to Json.
	 * @param value Object.
	 * @return Json.
	 * @throws JSONException Thrown if a problem occurs when encoding.
	 */
    String encode(Object value) throws JSONException;

    /**
     * Decode a Json object.
     * @param value Json object.
     * @return Object.
     * @throws JSONException Thrown if a problem occurs when decoding.
     */
    Object decode(String value) throws JSONException;

    /**
     * Decode a JSON object to a specific type.
     * @param value Json object
     * @param type Type.
     * @return Object.
     * @throws JSONException Thrown if a problem occurs when decoding.
     */
    Object decode(String value, Class<?> type) throws JSONException;
    
}
