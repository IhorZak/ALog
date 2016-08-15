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
    private static final int MAX_TAG_LENGTH = 23;

    private ALogConfiguration mConfiguration;

    public AndroidLogALogger(ALogConfiguration configuration) {
        mConfiguration = configuration;
    }

    @Override
    public void v(Throwable throwable, String message, Object... args) {
        log(ALogLevel.VERBOSE, throwable, message, args);
    }

    @Override
    public void d(Throwable throwable, String message, Object... args) {
        log(ALogLevel.DEBUG, throwable, message, args);
    }

    @Override
    public void i(Throwable throwable, String message, Object... args) {
        log(ALogLevel.INFO, throwable, message, args);
    }

    @Override
    public void w(Throwable throwable, String message, Object... args) {
        log(ALogLevel.WARNING, throwable, message, args);
    }

    @Override
    public void e(Throwable throwable, String message, Object... args) {
        log(ALogLevel.ERROR, throwable, message, args);
    }

    @Override
    public void wtf(Throwable throwable, String message, Object... args) {
        log(ALogLevel.WTF, throwable, message, args);
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

    @SuppressWarnings("WrongConstant")
    private void log(ALogLevel level, Throwable throwable, String message, Object... args) {
        String tag = mConfiguration.mTag;
        StringBuilder messageBuilder = new StringBuilder();
        boolean isAutoTag = tag == null;
        boolean needStackTrace = isAutoTag ||
                mConfiguration.mIsClassPrefixEnabled ||
                mConfiguration.mIsMethodPrefixEnabled ||
                mConfiguration.mIsLineLocationPrefixEnabled ||
                mConfiguration.mStackTraceLineCount > 0;
        if (mConfiguration.mIsThreadPrefixEnabled || needStackTrace) {
            messageBuilder.append('[');
            Thread currentThread = Thread.currentThread();
            if (mConfiguration.mIsThreadPrefixEnabled) {
                messageBuilder.append(currentThread.getName());
            }
            if (needStackTrace) {
                if (messageBuilder.length() > 0) {
                    messageBuilder.append('|');
                }
                StackTraceElement[] stackTrace = currentThread.getStackTrace();
                // TODO Implement
            }
            messageBuilder.append(']');
        }
        if (message != null) {
            if (messageBuilder.length() > 0) {
                messageBuilder.append(' ');
            }
            messageBuilder.append(args.length == 0 ? message : String.format(message, args));
        }
        if (throwable != null) {
            if (messageBuilder.length() > 0) {
                messageBuilder.append('\n');
            }
            messageBuilder.append(Log.getStackTraceString(throwable));
        }
        if (tag.length() > MAX_TAG_LENGTH) {
            tag = tag.substring(0, MAX_TAG_LENGTH);
        }
        Log.println(level.getAndroidPriority(), tag, messageBuilder.toString());
    }
}
