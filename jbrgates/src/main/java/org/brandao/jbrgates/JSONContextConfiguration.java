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

/**
 * Defines the object that provides the necessary 
 * resources to configure the context.
 * 
 * @author Brandao
 * 
 * @version 1.1
 */
public interface JSONContextConfiguration {

	/**
	 * Adds converter.
	 * @param type Type
	 * @param converter Converter.
	 */
    void addConverter(Class<?> type, JSONConverter converter);

    /**
     * Gets a converter.
     * @param type Type.
     * @return Converter.
     */
    JSONConverter getConverter(Class<?> type);

    /**
     * Gets the default converter.
     * @return Converter.
     */
    JSONConverter getDefaultConverter();

    /**
     * Remove a converter.
     * @param type Type.
     */
    void removeConverter(Class<?> type);

    /**
     * Sets the bean factory.
     * @param factory Factory.
     */
    void setFactoryBean( FactoryBean factory );

    /**
     * Gets the bean factory.
     * @return Factory.
     */
    FactoryBean getFactoryBean();
    
}
