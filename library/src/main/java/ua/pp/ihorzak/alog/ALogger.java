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

/**
 * Interface to send logging messages.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
public interface ALogger {
    /**
     * Sends empty {@link ALogLevel#VERBOSE} logging message.
     */
    void v();

    /**
     * Sends {@link ALogLevel#VERBOSE} logging message.
     *
     * @param message Message. Can be a <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args Arguments for format string in message.
     */
    void v(String message, Object... args);

    /**
     * Sends {@link ALogLevel#VERBOSE} logging message.
     *
     * @param throwable {@link Throwable} to log.
     */
    void v(Throwable throwable);

    /**
     * Sends {@link ALogLevel#VERBOSE} logging message.
     *
     * @param throwable {@link Throwable} to log.
     * @param message Message. Can be a <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args Arguments for format string in message.
     */
    void v(Throwable throwable, String message, Object... args);

    /**
     * Sends empty {@link ALogLevel#DEBUG} logging message.
     */
    void d();

    /**
     * Sends {@link ALogLevel#DEBUG} logging message.
     *
     * @param message Message. Can be a <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args Arguments for format string in message.
     */
    void d(String message, Object... args);

    /**
     * Sends {@link ALogLevel#DEBUG} logging message.
     *
     * @param throwable {@link Throwable} to log.
     */
    void d(Throwable throwable);

    /**
     * Sends {@link ALogLevel#DEBUG} logging message.
     *
     * @param throwable {@link Throwable} to log.
     * @param message Message. Can be a <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args Arguments for format string in message.
     */
    void d(Throwable throwable, String message, Object... args);

    /**
     * Sends empty {@link ALogLevel#INFO} logging message.
     */
    void i();

    /**
     * Sends {@link ALogLevel#INFO} logging message.
     *
     * @param message Message. Can be a <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args Arguments for format string in message.
     */
    void i(String message, Object... args);

    /**
     * Sends {@link ALogLevel#INFO} logging message.
     *
     * @param throwable {@link Throwable} to log.
     */
    void i(Throwable throwable);

    /**
     * Sends {@link ALogLevel#INFO} logging message.
     *
     * @param throwable {@link Throwable} to log.
     * @param message Message. Can be a <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args Arguments for format string in message.
     */
    void i(Throwable throwable, String message, Object... args);

    /**
     * Sends empty {@link ALogLevel#WARNING} logging message.
     */
    void w();

    /**
     * Sends {@link ALogLevel#WARNING} logging message.
     *
     * @param message Message. Can be a <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args Arguments for format string in message.
     */
    void w(String message, Object... args);

    /**
     * Sends {@link ALogLevel#WARNING} logging message.
     *
     * @param throwable {@link Throwable} to log.
     */
    void w(Throwable throwable);

    /**
     * Sends {@link ALogLevel#WARNING} logging message.
     *
     * @param throwable {@link Throwable} to log.
     * @param message Message. Can be a <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args Arguments for format string in message.
     */
    void w(Throwable throwable, String message, Object... args);

    /**
     * Sends empty {@link ALogLevel#ERROR} logging message.
     */
    void e();

    /**
     * Sends {@link ALogLevel#ERROR} logging message.
     *
     * @param message Message. Can be a <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args Arguments for format string in message.
     */
    void e(String message, Object... args);

    /**
     * Sends {@link ALogLevel#ERROR} logging message.
     *
     * @param throwable {@link Throwable} to log.
     */
    void e(Throwable throwable);

    /**
     * Sends {@link ALogLevel#ERROR} logging message.
     *
     * @param throwable {@link Throwable} to log.
     * @param message Message. Can be a <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args Arguments for format string in message.
     */
    void e(Throwable throwable, String message, Object... args);

    /**
     * Sends empty {@link ALogLevel#WTF} logging message.
     */
    void wtf();

    /**
     * Sends {@link ALogLevel#WTF} logging message.
     *
     * @param message Message. Can be a <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args Arguments for format string in message.
     */
    void wtf(String message, Object... args);

    /**
     * Sends {@link ALogLevel#WTF} logging message.
     *
     * @param throwable {@link Throwable} to log.
     */
    void wtf(Throwable throwable);

    /**
     * Sends {@link ALogLevel#WTF} logging message.
     *
     * @param throwable {@link Throwable} to log.
     * @param message Message. Can be a <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args Arguments for format string in message.
     */
    void wtf(Throwable throwable, String message, Object... args);

    /**
     * Formats JSON string and sends logging message with it. Logging message level is set via
     * {@link ALogConfiguration}.
     *
     * @param json JSON string.
     */
    void json(String json);

    /**
     * Formats JSON string and sends logging message with it.
     *
     * @param json JSON string.
     * @param level Logging level. See {@link ALogLevel}.
     */
    void json(ALogLevel level, String json);

    /**
     * Formats XML string and sends logging message with it. Logging message level is set via
     * {@link ALogConfiguration}.
     *
     * @param xml XML string.
     */
    void xml(String xml);

    /**
     * Formats XML string and sends logging message with it.
     *
     * @param xml XML string.
     * @param level Logging level. See {@link ALogLevel}.
     */
    void xml(ALogLevel level, String xml);
}
