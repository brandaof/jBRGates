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
