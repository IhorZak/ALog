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

import android.app.Application;

import java.util.Map;

/**
 * Simple customizable logger for Android. Can be customized with the help of
 * {@link ALogConfiguration} instance which should be passed to
 * {@link #initialize(ALogConfiguration)}. In most cases ALog should be initialized once in
 * {@link Application#onCreate()} method. If it won't be initialized explicitly, it will be
 * initialized on the first log method call with default configuration.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
@SuppressWarnings({"WeakerAccess"})
public final class ALog {
    private ALog() {}

    private static ALogConfiguration configuration = null;
    private static ALogger logger = null;

    /**
     * Initializes {@link ALog} with configuration. In most cases it should be called once in
     * {@link Application#onCreate()} method.
     *
     * @param configuration {@link ALogConfiguration} instance. If null {@link ALog} is initialized
     *                      with default configuration.
     */
    public static void initialize(ALogConfiguration configuration) {
        ALog.configuration = configuration != null ? configuration : ALogConfiguration.builder().build();
        logger = ALog.configuration.mIsEnabled ? new ConfigurationALogger(ALog.configuration) : StubALoggerProvider.INSTANCE.mLogger;
    }

    /**
     * Gets {@link ALogger} instance with specified log tag.
     *
     * @param tag Log tag.
     * @return {@link ALogger} instance with specified log tag.
     */
    public static ALogger t(String tag) {
        verifyInitialization();
        if (configuration == null) {
            return StubALoggerProvider.INSTANCE.mLogger;
        }
        if (configuration.mIsEnabled && !Utils.equals(configuration.mTag, tag)) {
            return new ConfigurationALogger(configuration.copyBuilder().tag(tag).build());
        } else {
            return logger;
        }
    }

    /**
     * Gets {@link ALogger} instance which outputs specified count of stack trace lines with
     * logging messages.
     *
     * @param stackTraceLineCount Count of stack trace lines to output with logging messages.
     * @return {@link ALogger} instance which outputs specified count of stack trace lines with
     *         logging messages.
     */
    public static ALogger st(int stackTraceLineCount) {
        verifyInitialization();
        if (configuration == null) {
            return StubALoggerProvider.INSTANCE.mLogger;
        }
        if (configuration.mIsEnabled && configuration.mStackTraceLineCount != stackTraceLineCount) {
            return new ConfigurationALogger(configuration.copyBuilder().stackTraceLineCount(stackTraceLineCount).build());
        } else {
            return logger;
        }
    }

    /**
     * Gets {@link ALogger} instance which outputs specified count of stack trace lines with
     * logging messages and uses specified log tag.
     *
     * @param tag Log tag.
     * @param stackTraceLineCount Count of stack trace lines to output with logging messages.
     * @return {@link ALogger} instance which outputs specified count of stack trace lines with
     *         logging messages and uses specified log tag.
     */
    public static ALogger tst(String tag, int stackTraceLineCount) {
        verifyInitialization();
        if (configuration == null) {
            return StubALoggerProvider.INSTANCE.mLogger;
        }
        if (configuration.mIsEnabled && (!Utils.equals(configuration.mTag, tag) || configuration.mStackTraceLineCount != stackTraceLineCount)) {
            return new ConfigurationALogger(configuration.copyBuilder().tag(tag).stackTraceLineCount(stackTraceLineCount).build());
        } else {
            return logger;
        }
    }

    /**
     * Gets {@link ALogger} instance which uses custom formatting for passed class instances with
     * the help of passed {@link ALogFormatter} instance.
     *
     * @param clazz Class of instances to be formatted with custom formatter.
     * @param formatter Custom formatter.
     * @return {@link ALogger} instance which uses custom formatting for passed class instances with
     *         the help of passed {@link ALogFormatter} instance.
     */
    public static ALogger formatter(Class<?> clazz, ALogFormatter<?> formatter) {
        verifyInitialization();
        if (configuration == null) {
            return StubALoggerProvider.INSTANCE.mLogger;
        }
        if (configuration.mIsEnabled) {
            return new ConfigurationALogger(configuration.copyBuilder().formatter(clazz, formatter).build());
        } else {
            return logger;
        }
    }

    /**
     * Gets {@link ALogger} instance which uses custom formatting for passed classes instances with
     * the help of passed {@link ALogFormatter} instances.
     *
     * @param formatterMap Map of classes and corresponding custom formatters.
     * @return {@link ALogger} instance which uses custom formatting for passed classes instances
     *         with the help of passed {@link ALogFormatter} instances.
     */
    @SuppressWarnings({"SpellCheckingInspection", "RedundantSuppression"})
    public static ALogger formatters(Map<Class<?>, ALogFormatter<?>> formatterMap) {
        verifyInitialization();
        if (configuration == null) {
            return StubALoggerProvider.INSTANCE.mLogger;
        }
        if (configuration.mIsEnabled) {
            ALogConfiguration.Builder builder = configuration.copyBuilder();
            for (Map.Entry<Class<?>, ALogFormatter<?>> formatterEntry : formatterMap.entrySet()) {
                builder.formatter(formatterEntry.getKey(), formatterEntry.getValue());
            }
            return new ConfigurationALogger(builder.build());
        } else {
            return logger;
        }
    }

    /**
     * Sends empty {@link ALogLevel#VERBOSE} logging message.
     */
    public static void v() {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.VERBOSE) >= 0) {
            logger.v();
        }
    }

    /**
     * Sends {@link ALogLevel#VERBOSE} logging message.
     *
     * @param object Object to log.
     */
    public static void v(Object object) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.VERBOSE) >= 0) {
            logger.v(object);
        }
    }

    /**
     * Sends {@link ALogLevel#VERBOSE} logging message.
     *
     * @param message Message. Can be a <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args Arguments for format string in message.
     */
    public static void v(String message, Object... args) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.VERBOSE) >= 0) {
            logger.v(message, args);
        }
    }

    /**
     * Sends {@link ALogLevel#VERBOSE} logging message.
     *
     * @param throwable {@link Throwable} to log.
     */
    public static void v(Throwable throwable) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.VERBOSE) >= 0) {
            logger.v(throwable);
        }
    }

    /**
     * Sends {@link ALogLevel#VERBOSE} logging message.
     *
     * @param throwable {@link Throwable} to log.
     * @param message Message. Can be a <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args Arguments for format string in message.
     */
    public static void v(Throwable throwable, String message, Object... args) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.VERBOSE) >= 0) {
            logger.v(throwable, message, args);
        }
    }

    /**
     * Sends empty {@link ALogLevel#DEBUG} logging message.
     */
    public static void d() {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.DEBUG) >= 0) {
            logger.d();
        }
    }

    /**
     * Sends {@link ALogLevel#DEBUG} logging message.
     *
     * @param object Object to log.
     */
    public static void d(Object object) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.DEBUG) >= 0) {
            logger.d(object);
        }
    }

    /**
     * Sends {@link ALogLevel#DEBUG} logging message.
     *
     * @param message Message. Can be a <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args Arguments for format string in message.
     */
    public static void d(String message, Object... args) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.DEBUG) >= 0) {
            logger.d(message, args);
        }
    }

    /**
     * Sends {@link ALogLevel#DEBUG} logging message.
     *
     * @param throwable {@link Throwable} to log.
     */
    public static void d(Throwable throwable) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.DEBUG) >= 0) {
            logger.d(throwable);
        }
    }

    /**
     * Sends {@link ALogLevel#DEBUG} logging message.
     *
     * @param throwable {@link Throwable} to log.
     * @param message Message. Can be a <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args Arguments for format string in message.
     */
    public static void d(Throwable throwable, String message, Object... args) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.DEBUG) >= 0) {
            logger.d(throwable, message, args);
        }
    }

    /**
     * Sends empty {@link ALogLevel#INFO} logging message.
     */
    public static void i() {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.INFO) >= 0) {
            logger.i();
        }
    }

    /**
     * Sends {@link ALogLevel#INFO} logging message.
     *
     * @param object Object to log.
     */
    public static void i(Object object) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.INFO) >= 0) {
            logger.i(object);
        }
    }

    /**
     * Sends {@link ALogLevel#INFO} logging message.
     *
     * @param message Message. Can be a <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args Arguments for format string in message.
     */
    public static void i(String message, Object... args) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.INFO) >= 0) {
            logger.i(message, args);
        }
    }

    /**
     * Sends {@link ALogLevel#INFO} logging message.
     *
     * @param throwable {@link Throwable} to log.
     */
    public static void i(Throwable throwable) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.INFO) >= 0) {
            logger.i(throwable);
        }
    }

    /**
     * Sends {@link ALogLevel#INFO} logging message.
     *
     * @param throwable {@link Throwable} to log.
     * @param message Message. Can be a <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args Arguments for format string in message.
     */
    public static void i(Throwable throwable, String message, Object... args) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.INFO) >= 0) {
            logger.i(throwable, message, args);
        }
    }

    /**
     * Sends empty {@link ALogLevel#WARNING} logging message.
     */
    public static void w() {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.WARNING) >= 0) {
            logger.w();
        }
    }

    /**
     * Sends {@link ALogLevel#WARNING} logging message.
     *
     * @param object Object to log.
     */
    public static void w(Object object) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.WARNING) >= 0) {
            logger.w(object);
        }
    }

    /**
     * Sends {@link ALogLevel#WARNING} logging message.
     *
     * @param message Message. Can be a <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args Arguments for format string in message.
     */
    public static void w(String message, Object... args) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.WARNING) >= 0) {
            logger.w(message, args);
        }
    }

    /**
     * Sends {@link ALogLevel#WARNING} logging message.
     *
     * @param throwable {@link Throwable} to log.
     */
    public static void w(Throwable throwable) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.WARNING) >= 0) {
            logger.w(throwable);
        }
    }

    /**
     * Sends {@link ALogLevel#WARNING} logging message.
     *
     * @param throwable {@link Throwable} to log.
     * @param message Message. Can be a <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args Arguments for format string in message.
     */
    public static void w(Throwable throwable, String message, Object... args) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.WARNING) >= 0) {
            logger.w(throwable, message, args);
        }
    }

    /**
     * Sends empty {@link ALogLevel#ERROR} logging message.
     */
    public static void e() {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.ERROR) >= 0) {
            logger.e();
        }
    }

    /**
     * Sends {@link ALogLevel#ERROR} logging message.
     *
     * @param object Object to log.
     */
    public static void e(Object object) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.ERROR) >= 0) {
            logger.e(object);
        }
    }

    /**
     * Sends {@link ALogLevel#ERROR} logging message.
     *
     * @param message Message. Can be a <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args Arguments for format string in message.
     */
    public static void e(String message, Object... args) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.ERROR) >= 0) {
            logger.e(message, args);
        }
    }

    /**
     * Sends {@link ALogLevel#ERROR} logging message.
     *
     * @param throwable {@link Throwable} to log.
     */
    public static void e(Throwable throwable) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.ERROR) >= 0) {
            logger.e(throwable);
        }
    }

    /**
     * Sends {@link ALogLevel#ERROR} logging message.
     *
     * @param throwable {@link Throwable} to log.
     * @param message Message. Can be a <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args Arguments for format string in message.
     */
    public static void e(Throwable throwable, String message, Object... args) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.ERROR) >= 0) {
            logger.e(throwable, message, args);
        }
    }

    /**
     * Sends empty {@link ALogLevel#WTF} logging message.
     */
    public static void wtf() {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.WTF) >= 0) {
            logger.wtf();
        }
    }

    /**
     * Sends {@link ALogLevel#WTF} logging message.
     *
     * @param object Object to log.
     */
    public static void wtf(Object object) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.WTF) >= 0) {
            logger.wtf(object);
        }
    }

    /**
     * Sends {@link ALogLevel#WTF} logging message.
     *
     * @param message Message. Can be a <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args Arguments for format string in message.
     */
    public static void wtf(String message, Object... args) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.WTF) >= 0) {
            logger.wtf(message, args);
        }
    }

    /**
     * Sends {@link ALogLevel#WTF} logging message.
     *
     * @param throwable {@link Throwable} to log.
     */
    public static void wtf(Throwable throwable) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.WTF) >= 0) {
            logger.wtf(throwable);
        }
    }

    /**
     * Sends {@link ALogLevel#WTF} logging message.
     *
     * @param throwable {@link Throwable} to log.
     * @param message Message. Can be a <a href="../util/Formatter.html#syntax">format string</a>.
     * @param args Arguments for format string in message.
     */
    public static void wtf(Throwable throwable, String message, Object... args) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.WTF) >= 0) {
            logger.wtf(throwable, message, args);
        }
    }

    /**
     * Formats JSON string and sends logging message with it. Logging message level is set via
     * {@link ALogConfiguration}.
     *
     * @param json JSON string.
     */
    public static void json(String json) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(configuration.mJsonLevel) >= 0) {
            logger.json(json);
        }
    }

    /**
     * Formats JSON string and sends logging message with it.
     *
     * @param level Logging level. See {@link ALogLevel}.
     * @param json JSON string.
     */
    public static void json(ALogLevel level, String json) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(level) >= 0) {
            logger.json(level, json);
        }
    }

    /**
     * Formats XML string and sends logging message with it. Logging message level is set via
     * {@link ALogConfiguration}.
     *
     * @param xml XML string.
     */
    public static void xml(String xml) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(configuration.mXmlLevel) >= 0) {
            logger.xml(xml);
        }
    }

    /**
     * Formats XML string and sends logging message with it.
     *
     * @param level Logging level. See {@link ALogLevel}.
     * @param xml XML string.
     */
    public static void xml(ALogLevel level, String xml) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(level) >= 0) {
            logger.xml(level, xml);
        }
    }

    /**
     * Formats bytes as hexadecimal format string and sends logging message with it. Logging
     * message level is set via {@link ALogConfiguration}.
     *
     * @param bytes Byte array.
     */
    public static void hex(byte[] bytes) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(configuration.mHexLevel) >= 0) {
            logger.hex(bytes);
        }
    }

    /**
     * Formats bytes as hexadecimal format string and sends logging message with it.
     *
     * @param level Logging level. See {@link ALogLevel}.
     * @param bytes Byte array.
     */
    public static void hex(ALogLevel level, byte[] bytes) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(level) >= 0) {
            logger.hex(level, bytes);
        }
    }

    private static void verifyInitialization() {
        if (configuration == null || logger == null) {
            initialize(null);
        }
    }
}
