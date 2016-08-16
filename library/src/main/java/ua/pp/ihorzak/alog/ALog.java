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

/**
 * Simple customizable logger for Android. Can be customized with the help of
 * {@link ALogConfiguration} instance which should be passed to
 * {@link #initialize(ALogConfiguration)}. In most cases ALog should be initialized once in
 * {@link Application#onCreate()} method. If it won't be initialized explicitly, it will be
 * initialized on the first log method call with default configuration.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 * @since 12.08.2016
 */
public final class ALog {
    private ALog() {}

    private static ALogConfiguration configuration = null;
    private static ALogger logger = null;

    /**
     * Initializes {@link ALog} with configuration. In most cases it should be called once in
     * {@link Application#onCreate()} method.
     *
     * @param configuration {@link ALogConfiguration} instance. If null {@link ALog} is inialized
     *                      with default configuration.
     */
    public static void initialize(ALogConfiguration configuration) {
        ALog.configuration = configuration != null ? configuration : ALogConfiguration.builder().build();
        logger = ALog.configuration.mIsEnabled ? new AndroidLogALogger(configuration) : new StubALogger();
    }

    /**
     * Gets {@link ALogger} instance with specified log tag.
     *
     * @param tag Log tag.
     * @return {@link ALogger} instance with specified log tag.
     */
    public static ALogger t(String tag) {
        verifyInitialization();
        if (configuration.mIsEnabled && !Utils.equals(configuration.mTag, tag)) {
            return new AndroidLogALogger(configuration.copyBuilder().tag(tag).build());
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
        if (configuration.mIsEnabled && configuration.mStackTraceLineCount != stackTraceLineCount) {
            return new AndroidLogALogger(configuration.copyBuilder().stackTraceLineCount(stackTraceLineCount).build());
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
        if (configuration.mIsEnabled && (!Utils.equals(configuration.mTag, tag) || configuration.mStackTraceLineCount != stackTraceLineCount)) {
            return new AndroidLogALogger(configuration.copyBuilder().tag(tag).stackTraceLineCount(stackTraceLineCount).build());
        } else {
            return logger;
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
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.WARNING) <= 0) {
            logger.w(throwable, message, args);
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
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.ERROR) <= 0) {
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
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.ERROR) <= 0) {
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
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.ERROR) <= 0) {
            logger.e(throwable, message, args);
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
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.WTF) <= 0) {
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
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.WTF) <= 0) {
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
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(ALogLevel.WTF) <= 0) {
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
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(configuration.mJsonLevel) <= 0) {
            logger.json(json);
        }
    }

    /**
     * Formats JSON string and sends logging message with it.
     *
     * @param json JSON string.
     * @param level Logging level. See {@link ALogLevel}.
     */
    public static void json(ALogLevel level, String json) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(level) <= 0) {
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
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(configuration.mXmlLevel) <= 0) {
            logger.xml(xml);
        }
    }

    /**
     * Formats XML string and sends logging message with it.
     *
     * @param xml XML string.
     * @param level Logging level. See {@link ALogLevel}.
     */
    public static void xml(ALogLevel level, String xml) {
        verifyInitialization();
        if (configuration.mIsEnabled && configuration.mMinimalLevel.compareTo(level) <= 0) {
            logger.xml(level, xml);
        }
    }

    private static void verifyInitialization() {
        if (configuration == null || logger == null) {
            initialize(null);
        }
    }
}
