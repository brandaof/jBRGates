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
 * Interface used to get an instance of an
 * object within a container IOC.
 *
 * @author Afonso Brandao
 * @version 1.0
 */
public interface FactoryBean {

    /**
     * Gets a new instance.
     * @param <T> 
     * @param type Class type.
     * @return Object.
     */
    public <T> T getInstance( Class<T> type );

}
