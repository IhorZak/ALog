/*
 * Copyright 2022 Ihor Zakhozhyi
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
 * {@link ALogFormatter} which uses {@link ALogFormatterComplexDelegate} for objects transformation
 * to logging strings.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
final class ComplexDelegateALogFormatter<T> extends ALogFormatter<T> {
    private final ALogFormatterComplexDelegate<T> mComplexDelegate;

    /**
     * Constructor.
     *
     * @param complexDelegate {@link ALogFormatterComplexDelegate} instance.
     */
    ComplexDelegateALogFormatter(ALogFormatterComplexDelegate<T> complexDelegate) {
        mComplexDelegate = complexDelegate;
    }

    @Override
    String toLoggingString(T object, ALogFormatter<Object> objectFormatter) {
        return mComplexDelegate.toLoggingString(object, objectFormatter);
    }
}
