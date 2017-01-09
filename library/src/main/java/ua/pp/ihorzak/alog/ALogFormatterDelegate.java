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

/**
 * {@link ALogFormatter} delegate that is responsible for custom specified class instances
 * transformation to string for logging. Should be used for {@link ALogFormatter} creation
 * via {@link ALogFormatter#create(ALogFormatterDelegate)}.
 *
 * @param <T> Class of instances which can be transformed to logging string using this class
 *            instances.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
@SuppressWarnings("WeakerAccess")
public interface ALogFormatterDelegate<T> {
    /**
     * Transforms passed object into logging string.
     *
     * @param object Object to be transformed into logging string.
     * @return Logging string that represents passed object.
     */
    String toLoggingString(T object);
}
