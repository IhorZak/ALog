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
 * {@link ALogFormatter} which contains {@link ALogConfiguration} instance.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
abstract class ConfigurationALogFormatter<T> extends ALogFormatter<T> {
    protected final ALogConfiguration mConfiguration;

    /**
     * Constructor.
     *
     * @param configuration {@link ALogConfiguration} instance.
     */
    ConfigurationALogFormatter(ALogConfiguration configuration) {
        mConfiguration = configuration;
    }
}
