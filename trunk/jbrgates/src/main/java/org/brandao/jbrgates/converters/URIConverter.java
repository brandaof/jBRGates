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

import java.net.URI;
import java.net.URISyntaxException;

import org.brandao.jbrgates.FactoryBean;
import org.brandao.jbrgates.JSONException;

/**
 *
 * @author Brandao
 * @version 1.1
 */
public class URIConverter extends StringConverter{

    public Object getObject(Object value, FactoryBean factory, Class baseType) throws JSONException {
        try{
            return new URI(String.valueOf(value));
        }catch (URISyntaxException ex) {
            throw new JSONException(ex);
        }
    }

}
