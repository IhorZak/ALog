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

import java.util.Collection;
import java.util.Iterator;

/**
 * {@link ALogFormatter} to transform collections into logging strings.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
final class CollectionALogFormatter extends ConfigurationALogFormatter<Collection<?>> {
    /**
     * Constructor.
     *
     * @param configuration {@link ALogConfiguration} instance.
     */
    CollectionALogFormatter(ALogConfiguration configuration) {
        super(configuration);
    }

    @Override
    public String toLoggingString(Collection<?> object, ALogFormatterDelegate<Object> objectFormatterDelegate) {
        StringBuilder builder = new StringBuilder();
        builder.append(object.getClass().getName())
               .append("(size = ")
               .append(object.size())
               .append(") [");
        Iterator<?> iterator = object.iterator();
        while (iterator.hasNext()) {
            builder.append(Utils.formatArgument(iterator.next(), mConfiguration));
            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }
        builder.append(']');
        return builder.toString();
    }
}
