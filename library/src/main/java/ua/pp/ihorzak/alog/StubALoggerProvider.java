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
 * Provider of empty {@link ALogger} instance. Must be used in case when logging
 * is disabled via {@link ALogConfiguration}.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
enum StubALoggerProvider {
    /**
     * Singleton instance of empty {@link ALogger} instance provider.
     */
    INSTANCE;

    final ALogger mLogger = new StubALogger();

    /**
     * Empty {@link ALogger} implementation. Used in case when logging is disabled via
     * {@link ALogConfiguration}.
     */
    private static final class StubALogger extends BaseALogger {
        @Override
        public void v(Throwable throwable, String message, Object... args) {
            // Do nothing.
        }

        @Override
        public void d(Throwable throwable, String message, Object... args) {
            // Do nothing.
        }

        @Override
        public void i(Throwable throwable, String message, Object... args) {
            // Do nothing.
        }

        @Override
        public void w(Throwable throwable, String message, Object... args) {
            // Do nothing.
        }

        @Override
        public void e(Throwable throwable, String message, Object... args) {
            // Do nothing.
        }

        @Override
        public void wtf(Throwable throwable, String message, Object... args) {
            // Do nothing.
        }

        @Override
        public void json(String json) {
            // Do nothing.
        }

        @Override
        public void json(ALogLevel level, String json) {
            // Do nothing.
        }

        @Override
        public void xml(String xml) {
            // Do nothing.
        }

        @Override
        public void xml(ALogLevel level, String xml) {
            // Do nothing.
        }

        @Override
        public void hex(byte[] bytes) {
            // Do nothing.
        }

        @Override
        public void hex(ALogLevel level, byte[] bytes) {
            // Do nothing.
        }
    }
}
