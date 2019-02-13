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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * {@link ALogger} implementation that uses {@link Log} to perform logging.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
final class AndroidLogALogger extends BaseALogger {
    private static final int MAX_TAG_LENGTH = 23;

    private static final String ALOG_PACKAGE_NAME = "ua.pp.ihorzak.alog";

    private ALogConfiguration mConfiguration;

    private static int findStartStackTraceIndex(StackTraceElement[] elements) {
        int startStackTraceIndex = -1;
        boolean isALogStackTracePassed = false;
        for (int i = 0; i < elements.length; ++i) {
            String className = elements[i].getClassName();
            int packageNameEnd = className.lastIndexOf('.');
            boolean isALogElement = false;
            if (packageNameEnd != -1) {
                String packageName = className.substring(0, packageNameEnd);
                if (ALOG_PACKAGE_NAME.equals(packageName)) {
                    isALogElement = true;
                    isALogStackTracePassed = true;
                }
            }
            if (!isALogElement && isALogStackTracePassed) {
                startStackTraceIndex = i;
                break;
            }
        }
        return startStackTraceIndex;
    }

    AndroidLogALogger(ALogConfiguration configuration) {
        mConfiguration = configuration;
    }

    @Override
    public void v(Throwable throwable, String message, Object... args) {
        if (mConfiguration.mMinimalLevel.compareTo(ALogLevel.VERBOSE) >= 0) {
            log(ALogLevel.VERBOSE, throwable, message, args);
        }
    }

    @Override
    public void d(Throwable throwable, String message, Object... args) {
        if (mConfiguration.mMinimalLevel.compareTo(ALogLevel.DEBUG) >= 0) {
            log(ALogLevel.DEBUG, throwable, message, args);
        }
    }

    @Override
    public void i(Throwable throwable, String message, Object... args) {
        if (mConfiguration.mMinimalLevel.compareTo(ALogLevel.INFO) >= 0) {
            log(ALogLevel.INFO, throwable, message, args);
        }
    }

    @Override
    public void w(Throwable throwable, String message, Object... args) {
        if (mConfiguration.mMinimalLevel.compareTo(ALogLevel.WARNING) >= 0) {
            log(ALogLevel.WARNING, throwable, message, args);
        }
    }

    @Override
    public void e(Throwable throwable, String message, Object... args) {
        if (mConfiguration.mMinimalLevel.compareTo(ALogLevel.ERROR) >= 0) {
            log(ALogLevel.ERROR, throwable, message, args);
        }
    }

    @Override
    public void wtf(Throwable throwable, String message, Object... args) {
        if (mConfiguration.mMinimalLevel.compareTo(ALogLevel.WTF) >= 0) {
            log(ALogLevel.WTF, throwable, message, args);
        }
    }

    @Override
    public void json(String json) {
        json(mConfiguration.mJsonLevel, json);
    }

    @Override
    public void json(ALogLevel level, String json) {
        if (mConfiguration.mMinimalLevel.compareTo(mConfiguration.mJsonLevel) < 0) {
            return;
        }
        String message;
        if (json == null) {
            message = "Passed JSON string is null";
        } else {
            json = json.trim();
            if (json.length() == 0) {
                message = "Passed JSON string is empty";
            } else {
                try {
                    if (json.startsWith("{")) {
                        message = "JSON:\n" + new JSONObject(json).toString(mConfiguration.mJsonIndentSpaceCount);
                    } else if (json.startsWith("[")) {
                        message = "JSON:\n" + new JSONArray(json).toString(mConfiguration.mJsonIndentSpaceCount);
                    } else {
                        message = "Invalid JSON string: " + json;
                    }
                } catch (JSONException e) {
                    message = "Invalid JSON string: " + e.getMessage();
                }
            }
        }
        log(level, null, message);
    }

    @Override
    public void xml(String xml) {
        xml(mConfiguration.mXmlLevel, xml);
    }

    @Override
    public void xml(ALogLevel level, String xml) {
        if (mConfiguration.mMinimalLevel.compareTo(mConfiguration.mXmlLevel) < 0) {
            return;
        }
        String message;
        if (xml == null) {
            message = "Passed XML string is null";
        } else {
            xml = xml.trim();
            if (xml.length() == 0) {
                message = "Passed XML string is empty";
            } else {
                try {
                    message = "XML:\n" + Utils.formatXml(xml, mConfiguration.mXmlIndentSpaceCount);
                } catch (XmlPullParserException | IOException e) {
                    message = "Invalid XML string: " + e.getMessage();
                }
            }
        }
        log(level, null, message);
    }

    @Override
    public void hex(byte[] bytes) {
        hex(mConfiguration.mHexLevel, bytes);
    }

    @Override
    public void hex(ALogLevel level, byte[] bytes) {
        if (mConfiguration.mMinimalLevel.compareTo(level) < 0) {
            return;
        }
        String message;
        if (bytes == null) {
            message = "Passed byte array is null";
        } else if (bytes.length == 0) {
            message = "Passed byte array is empty";
        } else {
            message = "Hex:\n" + Utils.formatBytesAsHexString(bytes);
        }
        log(level, null, message);
    }

    @SuppressWarnings("WrongConstant")
    private void log(ALogLevel level, Throwable throwable, String message, Object... args) {
        String tag = mConfiguration.mTag;
        StringBuilder messageBuilder = new StringBuilder();
        StringBuilder stackTraceSuffixBuilder = null;
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
                StackTraceElement[] stackTraceElements = currentThread.getStackTrace();
                int startStackIndex = findStartStackTraceIndex(stackTraceElements);
                if (startStackIndex > -1) {
                    StackTraceElement startStackTraceElement = stackTraceElements[startStackIndex];
                    if (isAutoTag) {
                        tag = Utils.getSimpleClassName(startStackTraceElement.getClassName());
                    }
                    if (mConfiguration.mIsClassPrefixEnabled) {
                        if (messageBuilder.length() > 0) {
                            messageBuilder.append('|');
                        }
                        messageBuilder.append(Utils.getSimpleClassName(startStackTraceElement.getClassName()));
                    }
                    if (mConfiguration.mIsMethodPrefixEnabled) {
                        if (messageBuilder.length() > 0) {
                            messageBuilder.append('|');
                        }
                        messageBuilder.append(startStackTraceElement.getMethodName());
                    }
                    if (mConfiguration.mIsLineLocationPrefixEnabled) {
                        if (messageBuilder.length() > 0) {
                            messageBuilder.append('|');
                        }
                        messageBuilder.append('(')
                                      .append(startStackTraceElement.getFileName())
                                      .append(':')
                                      .append(startStackTraceElement.getLineNumber())
                                      .append(')');
                    }
                    if (mConfiguration.mStackTraceLineCount > 0) {
                        stackTraceSuffixBuilder = new StringBuilder();
                        for (int i = startStackIndex; i < startStackIndex + mConfiguration.mStackTraceLineCount; ++i) {
                            stackTraceSuffixBuilder.append(stackTraceElements[i].toString()).append('\n');
                        }
                    }
                }
            }
            messageBuilder.append(']');
        }
        if (message != null) {
            if (messageBuilder.length() > 0) {
                messageBuilder.append(' ');
            }
            messageBuilder.append(Utils.formatMessageWithArguments(message, args, mConfiguration));
        }
        if (throwable != null) {
            if (messageBuilder.length() > 0) {
                messageBuilder.append('\n');
            }
            messageBuilder.append(Log.getStackTraceString(throwable));
        }
        if (stackTraceSuffixBuilder != null) {
            messageBuilder.append("\nStack trace:\n").append(stackTraceSuffixBuilder);
        }
        if (tag != null && tag.length() > MAX_TAG_LENGTH) {
            tag = tag.substring(0, MAX_TAG_LENGTH - 1) + '\u2026';
        }
        Log.println(level.getAndroidPriority(), tag, messageBuilder.toString());
    }
}
