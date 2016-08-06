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

/**
 *
 * @author Afonso Brandao
 */
interface JSONConstants {

    public static final StringBuffer HEX          = new StringBuffer( "0123456789abcdef" );
    public static final StringBuffer START_OBJECT = new StringBuffer("{ ");
    public static final StringBuffer END_OBJECT   = new StringBuffer(" }");
    public static final StringBuffer SEPARATOR    = new StringBuffer(", ");
    public static final StringBuffer START_ARRAY  = new StringBuffer("[ ");
    public static final StringBuffer END_ARRAY    = new StringBuffer(" ]");
    public static final StringBuffer EQUALS       = new StringBuffer(" : ");
    public static final StringBuffer QUOTE        = new StringBuffer("\"");
    public static final StringBuffer NULL         = new StringBuffer("null");
    //public static final StringBuffer DATE         = new StringBuffer("Date(%d)");
    public static final StringBuffer SUPER        = new StringBuffer("_super");
    public static final StringBuffer TRUE         = new StringBuffer("true");
    public static final StringBuffer FALSE        = new StringBuffer("false");

    public static final char START_OBJECT_STR = '{';
    public static final char END_OBJECT_STR   = '}';
    public static final char SEPARATOR_STR    = ',';
    public static final char START_ARRAY_STR  = '[';
    public static final char END_ARRAY_STR    = ']';
    public static final char EQUALS_STR       = ':';
    public static final char QUOTE_STR        = '\"';
    
}
