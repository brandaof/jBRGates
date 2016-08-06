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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 *
 * @author Afonso Brandao
 */
class MethodMapping {

    private String id;

    private Method getter;

    private Method setter;

    MappingBean parent;

    private Type type;

    public MethodMapping(){
        this( null, null, null, null, null );
    }
    
    public MethodMapping( String id, Method getter, Method setter, MappingBean parent, Type type ){
        this.id = id;
        this.getter = getter;
        this.setter = setter;
        this.parent = parent;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getValue( Object obj )
            throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException{
        return getter.invoke(obj);
    }

    public Object setValue( Object value, Object obj )
            throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException{
        return setter.invoke(obj, value );
    }

    public MappingBean getParent() {
        return parent;
    }

    public void setParent(MappingBean parent) {
        this.parent = parent;
    }

    public Method getGetter() {
        return getter;
    }

    public void setGetter(Method getter) {
        this.getter = getter;
    }

    public Method getSetter() {
        return setter;
    }

    public void setSetter(Method setter) {
        this.setter = setter;
    }

    /**
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Type type) {
        this.type = type;
    }
}
