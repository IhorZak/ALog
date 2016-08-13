/*
 * Copyright 2016 Ihor Zakhozhyi
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

import android.util.Log;

/**
 * {@link ALogger} implementation that uses {@link Log} to perform logging.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 * @since 13.08.2016
 */
final class AndroidLogALogger extends BaseALogger {
    private ALogConfiguration mConfiguration;

    public AndroidLogALogger(ALogConfiguration configuration) {
        mConfiguration = configuration;
    }

    @Override
    public void v(Throwable throwable, String message, Object... args) {
        // TODO Implement
    }

    @Override
    public void d(Throwable throwable, String message, Object... args) {
        // TODO Implement
    }

    @Override
    public void i(Throwable throwable, String message, Object... args) {
        // TODO Implement
    }

    @Override
    public void w(Throwable throwable, String message, Object... args) {
        // TODO Implement
    }

    @Override
    public void e(Throwable throwable, String message, Object... args) {
        // TODO Implement
    }

    @Override
    public void wtf(Throwable throwable, String message, Object... args) {
        // TODO Implement
    }

    @Override
    public void json(String json) {
        json(mConfiguration.mJsonLevel, json);
    }

    @Override
    public void json(ALogLevel level, String json) {
        // TODO Implement
    }

    @Override
    public void xml(String xml) {
        xml(mConfiguration.mXmlLevel, xml);
    }

    @Override
    public void xml(ALogLevel level, String xml) {
        // TODO Implement
    }
}
