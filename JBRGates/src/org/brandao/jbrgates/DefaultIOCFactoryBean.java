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
class DefaultIOCFactoryBean implements IOCFactoryBean{

    public <T> T getInstance( Class<T> type ){
        try{
            if( type == Map.class )
                return (T) new HashMap();
            else
            if( type == Set.class )
                return (T) new HashSet();
            else
            if( type == List.class )
                return (T) new LinkedList();
            else
                return type.newInstance();
        }
        catch( Throwable e ){
            throw new JSONException( e );
        }
    }

}
