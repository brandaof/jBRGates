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

    public Object getObject(Object value, FactoryBean factory, Class<?> baseType) throws JSONException {
        try{
            return new URI(String.valueOf(value));
        }catch (URISyntaxException ex) {
            throw new JSONException(ex);
        }
    }

}
