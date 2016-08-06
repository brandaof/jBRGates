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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author Afonso Brandao
 */
class LocaleUtils {

    private static Map<String, Locale> locales;

    static{
        locales = new HashMap<String,Locale>();
        
        for( Locale locale: Locale.getAvailableLocales() ){
            String key =
                String.format(
                                "%s-%s",
                                locale.getLanguage(),
                                locale.getCountry() ).toUpperCase();
            
            locales.put( key, locale );
        }
    }

    public static Locale getLocale( String key ){
        return key == null? null : locales.get( key.toUpperCase() );
    }
    
    public static String getKey( Locale locale ){
        return locale == null?
                    null :
                    String.format(
                                    "%s-%s",
                                    locale.getLanguage(),
                                    locale.getCountry() );
    }

}
