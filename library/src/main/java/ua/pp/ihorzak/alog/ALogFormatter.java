/*
 * Copyright 2017 Ihor Zakhozhyi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ua.pp.ihorzak.alog;

import java.util.Map;

/**
 * Responsible for custom specified class instances transformation to string for logging.
 * New instances of this class can be created via {@link #create(ALogFormatterDelegate)} call.
 * Should be used to configure {@link ALog} via
 * {@link ALogConfiguration.Builder#formatter(Class, ALogFormatter)},
 * {@link ALog#formatter(Class, ALogFormatter)} and
 * {@link ALog#formatters(Map)}.
 *
 * @param <T> Class of instances which can be transformed to logging string using this class
 *            instances.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class ALogFormatter<T> {
    /**
     * Factory method to create new {@link ALogFormatter} instances.
     *
     * @param delegate {@link ALogFormatterDelegate} instance to format objects to logging strings.
     * @param <T> Class of instances which can be transformed to logging string using created
     *            {@link ALogFormatter} instances.
     * @return {@link ALogFormatter} instance to be used for transforming specified class instances
     *         to logging strings.
     */
    public static <T> ALogFormatter<T> create(ALogFormatterDelegate<T> delegate) {
        return new DelegateALogFormatter<>(delegate);
    }

    /**
     * Factory method to create new {@link ALogFormatter} instances.
     *
     * @param delegate {@link ALogFormatterDelegate} instance to format objects to logging strings.
     * @param <T> Class of instances which can be transformed to logging string using created
     *            {@link ALogFormatter} instances.
     * @return {@link ALogFormatter} instance to be used for transforming specified class instances
     *         to logging strings.
     */
    public static <T> ALogFormatter<T> create(ALogFormatterComplexDelegate<T> delegate) {
        return new ComplexDelegateALogFormatter<>(delegate);
    }

    /**
     * Default constructor available only in package.
     */
    ALogFormatter() {}

    /**
     * Transforms passed object into logging string.
     *
     * @param object Object to be transformed into logging string.
     * @param objectFormatter Formatter to be used to transform this object contained instances to
     *                        logging strings.
     * @return Logging string that represents passed object.
     */
    @SuppressWarnings("unchecked")
    String format(Object object, ALogFormatter<Object> objectFormatter) {
        return toLoggingString((T) object, objectFormatter);
    }

    /**
     * Transforms passed object into logging string.
     *
     * @param object Object to be transformed into logging string.
     * @param objectFormatter Formatter to be used to transform this object contained instances to
     *                        logging strings.
     * @return Logging string that represents passed object.
     */
    abstract String toLoggingString(T object, ALogFormatter<Object> objectFormatter);
}
