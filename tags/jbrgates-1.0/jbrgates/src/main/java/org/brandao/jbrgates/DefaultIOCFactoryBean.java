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
 *
 * @author Afonso Brandao
 */
public class DefaultIOCFactoryBean implements FactoryBean{

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
