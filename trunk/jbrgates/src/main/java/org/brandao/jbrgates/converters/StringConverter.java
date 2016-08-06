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
import org.brandao.jbrgates.JSONEncoder;
import org.brandao.jbrgates.JSONException;

/**
 *
 * @author Brandao
 * @version 1.1
 */
public class StringConverter implements JSONConverter{

    public StringBuffer getJsonObject(Object value) throws JSONException {
        StringBuffer tmp = new StringBuffer(String.valueOf( value ));
        StringBuffer buf = new StringBuffer();
        
        buf.append(JSONEncoder.QUOTE);
        
        for( int i=0;i<tmp.length();i++ ) {
            char ch = tmp.charAt( i );
            switch (ch) {
            case '\\':
            case '"':
            case '/':
                buf.append( '\\' );
                buf.append(ch);
                break;
            case '\b':
                buf.append("\\b");
                break;
            case '\t':
                buf.append("\\t");
                break;
            case '\n':
                buf.append("\\n");
                break;
            case '\f':
                buf.append("\\f");
                break;
            case '\r':
                buf.append("\\r");
                break;
            default:
                if( ((ch >= 0x0020) && (ch <= 0x007e)) )
                    buf.append( tmp.charAt( i ) );
                else{
                    String hex = "000" + Integer.toHexString( ch );
                    buf.append( "\\u")
                            .append( hex.substring( hex.length() - 4 , hex.length() ) );
                }
            }
        }
        
        buf.append(JSONEncoder.QUOTE);
        
        return buf;
    }

    public Object getObject(Object value, FactoryBean factory, Class<?> baseType) throws JSONException {
        if(value instanceof String)
            return String.valueOf(value);
        else
            throw new JSONException("expected Stirng: " + value );
    }

}
