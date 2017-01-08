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
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class ALogFormatter<T> {
    public static <T> ALogFormatter<T> create(ALogFormatterDelegate<T> delegate) {
        return new DelegateALogFormatter<>(delegate);
    }

    @SuppressWarnings("unchecked")
    String format(Object object) {
        return toLoggingString((T) object);
    }

    abstract String toLoggingString(T object);
}
