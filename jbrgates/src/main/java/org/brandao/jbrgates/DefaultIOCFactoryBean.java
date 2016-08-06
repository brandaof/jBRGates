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

package org.brandao.jbrgates;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Default bean factoty.
 * 
 * @author Brandao
 */
public class DefaultIOCFactoryBean implements FactoryBean{

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T getInstance( Class<T> type ){
        try{
            if( Map.class.isAssignableFrom(type) )
                return (T) new HashMap();
            else
            if( Set.class.isAssignableFrom(type) )
                return (T) new HashSet();
            else
            if( List.class.isAssignableFrom(type) )
                return (T) new LinkedList();
            else
            if( Time.class.isAssignableFrom(type) )
                return (T) new Time( (new Date()).getTime() );
            else
            if( Timestamp.class.isAssignableFrom(type) )
                return (T) new Timestamp( (new Date()).getTime() );
            else
            if( Calendar.class.isAssignableFrom(type) )
                return (T) new GregorianCalendar();
            else
                return type.newInstance();
        }
        catch( Throwable e ){
            throw new JSONException( e );
        }
    }

}
