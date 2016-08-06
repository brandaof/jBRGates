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
