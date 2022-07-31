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

import java.util.Iterator;

/**
 * {@link ALogFormatter} to transform iterables into logging strings.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
final class IterableALogFormatter extends ConfigurationALogFormatter<Iterable<?>> {
    /**
     * Constructor.
     *
     * @param configuration {@link ALogConfiguration} instance.
     */
    IterableALogFormatter(ALogConfiguration configuration) {
        super(configuration);
    }

    @Override
    public String toLoggingString(Iterable<?> object, ALogFormatter<Object> objectFormatter) {
        StringBuilder builder = new StringBuilder();
        builder.append(object.getClass().getName())
               .append("(size = ");
        int sizeOffset = builder.length();
        builder.append(") [");;
        int size = 0;
        Iterator<?> iterator = object.iterator();
        while (iterator.hasNext()) {
            builder.append(Utils.formatArgument(iterator.next(), mConfiguration));
            ++size;
            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }
        builder.append(']');
        builder.insert(sizeOffset, size);
        return builder.toString();
    }
}
