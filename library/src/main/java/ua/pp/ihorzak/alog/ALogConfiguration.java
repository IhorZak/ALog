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
 * Represents {@link ALog} configuration which can be used to configure {@link ALog} if passed to
 * {@link ALog#initialize(ALogConfiguration)}. Instances must be created via {@link Builder} which
 * can be obtained via {@link #builder()} call.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 * @since 13.08.2016
 */
public final class ALogConfiguration {
    /**
     * Creates {@link Builder} instance, which is used to configure {@link ALogConfiguration}.
     *
     * @return {@link Builder} instance.
     */
    public static Builder builder() {
        return new Builder();
    }

    private static final boolean DEFAULT_IS_ENABLED = true;
    private static final ALogLevel DEFAULT_MINIMAL_LEVEL = ALogLevel.VERBOSE;
    private static final ALogLevel DEFAULT_JSON_LEVEL = ALogLevel.INFO;
    private static final ALogLevel DEFAULT_XML_LEVEL = ALogLevel.INFO;
    private static final String DEFAULT_TAG = null;
    private static final boolean DEFAULT_IS_THREAD_PREFIX_ENABLED = true;
    private static final boolean DEFAULT_IS_CLASS_PREFIX_ENABLED = false;
    private static final boolean DEFAULT_IS_METHOD_PREFIX_ENABLED = true;
    private static final boolean DEFAULT_IS_LINE_LOCATION_PREFIX_ENABLED = true;
    private static final int DEFAULT_STACK_TRACE_LINE_COUNT = 0;
    private static final int DEFAULT_JSON_INDENT_SPACE_COUNT = 2;
    private static final int DEFAULT_XML_INDENT_SPACE_COUNT = 2;

    final boolean mIsEnabled;

    final ALogLevel mMinimalLevel;
    final ALogLevel mJsonLevel;
    final ALogLevel mXmlLevel;

    final String mTag;

    final boolean mIsThreadPrefixEnabled;
    final boolean mIsClassPrefixEnabled;
    final boolean mIsMethodPrefixEnabled;
    final boolean mIsLineLocationPrefixEnabled;

    final int mStackTraceLineCount;

    final int mJsonIndentSpaceCount;
    final int mXmlIndentSpaceCount;

    private ALogConfiguration(boolean isEnabled,
                              ALogLevel minimalLevel,
                              ALogLevel jsonLevel,
                              ALogLevel xmlLevel,
                              String tag,
                              boolean isThreadPrefixEnabled,
                              boolean isClassPrefixEnabled,
                              boolean isMethodPrefixEnabled,
                              boolean isLineLocationPrefixEnabled,
                              int stackTraceLineCount,
                              int jsonIndentSpaceCount,
                              int xmlIndentSpaceCount) {
        mIsEnabled = isEnabled;
        mMinimalLevel = minimalLevel;
        mJsonLevel = jsonLevel;
        mXmlLevel = xmlLevel;
        mTag = tag;
        mIsThreadPrefixEnabled = isThreadPrefixEnabled;
        mIsClassPrefixEnabled = isClassPrefixEnabled;
        mIsMethodPrefixEnabled = isMethodPrefixEnabled;
        mIsLineLocationPrefixEnabled = isLineLocationPrefixEnabled;
        mStackTraceLineCount = stackTraceLineCount;
        mJsonIndentSpaceCount = jsonIndentSpaceCount;
        mXmlIndentSpaceCount = xmlIndentSpaceCount;
    }

    Builder copyBuilder() {
        return new Builder(this);
    }

    /**
     * Builder to create configured {@link ALogConfiguration} instance.
     */
    public static class Builder {
        private boolean mIsEnabled;
        private ALogLevel mMinimalLevel;
        private ALogLevel mJsonLevel;
        private ALogLevel mXmlLevel;
        private String mTag;
        private boolean mIsThreadPrefixEnabled;
        private boolean mIsClassPrefixEnabled;
        private boolean mIsMethodPrefixEnabled;
        private boolean mIsLineLocationPrefixEnabled;
        private int mStackTraceLineCount;
        private int mJsonIndentSpaceCount;
        private int mXmlIndentSpaceCount;

        private Builder() {
            mIsEnabled = DEFAULT_IS_ENABLED;
            mMinimalLevel = DEFAULT_MINIMAL_LEVEL;
            mJsonLevel = DEFAULT_JSON_LEVEL;
            mXmlLevel = DEFAULT_XML_LEVEL;
            mTag = DEFAULT_TAG;
            mIsThreadPrefixEnabled = DEFAULT_IS_THREAD_PREFIX_ENABLED;
            mIsClassPrefixEnabled = DEFAULT_IS_CLASS_PREFIX_ENABLED;
            mIsMethodPrefixEnabled = DEFAULT_IS_METHOD_PREFIX_ENABLED;
            mIsLineLocationPrefixEnabled = DEFAULT_IS_LINE_LOCATION_PREFIX_ENABLED;
            mStackTraceLineCount = DEFAULT_STACK_TRACE_LINE_COUNT;
            mJsonIndentSpaceCount = DEFAULT_JSON_INDENT_SPACE_COUNT;
            mXmlIndentSpaceCount = DEFAULT_XML_INDENT_SPACE_COUNT;
        }

        private Builder(ALogConfiguration configuration) {
            mIsEnabled = configuration.mIsEnabled;
            mMinimalLevel = configuration.mMinimalLevel;
            mJsonLevel = configuration.mJsonLevel;
            mXmlLevel = configuration.mXmlLevel;
            mTag = configuration.mTag;
            mIsThreadPrefixEnabled = configuration.mIsThreadPrefixEnabled;
            mIsClassPrefixEnabled = configuration.mIsClassPrefixEnabled;
            mIsMethodPrefixEnabled = configuration.mIsMethodPrefixEnabled;
            mIsLineLocationPrefixEnabled = configuration.mIsLineLocationPrefixEnabled;
            mStackTraceLineCount = configuration.mStackTraceLineCount;
            mJsonIndentSpaceCount = configuration.mJsonIndentSpaceCount;
            mXmlIndentSpaceCount = configuration.mXmlIndentSpaceCount;
        }

        /**
         * Enables/disables logging. If set to false all logging calls will be ignored. It is useful
         * for disabling logging in release builds (if not using ProGuard to remove ALog calls).
         * If not called by default logging is enabled.
         *
         * @param isEnabled true if logging should be enabled, otherwise false.
         * @return This builder instance.
         */
        public Builder enabled(boolean isEnabled) {
            mIsEnabled = isEnabled;
            return this;
        }

        /**
         * Sets minimal logging level which should be processed. Log calls with lower logging level
         * than specified will be ignored. If not called by default minimal logging level is set
         * to {@link ALogLevel#VERBOSE}.
         *
         * @param minimalLevel Minimal logging level which should be precessed.
         *                     See {@link ALogLevel}.
         * @return This builder instance.
         */
        public Builder minimalLevel(ALogLevel minimalLevel) {
            mMinimalLevel = minimalLevel;
            return this;
        }

        /**
         * Sets logging level for {@link ALog#json(String)} calls. Later JSON can be logged with
         * different logging level via {@link ALog#json(ALogLevel, String)} calls. If not called
         * by default JSON logging level is set to {@link ALogLevel#INFO}.
         *
         * @param jsonLevel Logging level for {@link ALog#json(String)} calls.
         *                  See {@link ALogLevel}.
         * @return This builder instance.
         */
        public Builder jsonLevel(ALogLevel jsonLevel) {
            mJsonLevel = jsonLevel;
            return this;
        }

        /**
         * Sets logging level for {@link ALog#xml(String)} calls. Later XML can be logged with
         * different logging level via {@link ALog#xml(ALogLevel, String)} calls. If not called
         * by default XML logging level is set to {@link ALogLevel#INFO}.
         *
         * @param xmlLevel Logging level for {@link ALog#xml(String)} calls.
         *                 See {@link ALogLevel}.
         * @return This builder instance.
         */
        public Builder xmlLevel(ALogLevel xmlLevel) {
            mXmlLevel = xmlLevel;
            return this;
        }

        /**
         * Sets default tag for log messages. Later messages can be logged with different tag
         * by using {@link ALogger} instance got from {@link ALog#t(String)} or
         * {@link ALog#tst(String, int)}. If tag is set to null, the name of class where logging
         * method called is used. If this method is not called by default tag is set to null.
         *
         * @param tag Default tag for log messages.
         * @return This builder instance.
         */
        public Builder tag(String tag) {
            mTag = tag;
            return this;
        }

        /**
         * Enables/disables thread name in message prefix. If not called by default this option is
         * enabled.
         *
         * @param isThreadPrefixEnabled true if logging message prefix should contain thread name of
         *                              logging method call location, otherwise false.
         * @return This builder instance.
         */
        public Builder threadPrefixEnabled(boolean isThreadPrefixEnabled) {
            mIsThreadPrefixEnabled = isThreadPrefixEnabled;
            return this;
        }

        /**
         * Enables/disables location class name in message prefix. If not called by default this
         * option is disabled.
         *
         * @param isClassPrefixEnabled true if logging message prefix should contain class name of
         *                             logging method call location, otherwise false.
         * @return This builder instance.
         */
        public Builder classPrefixEnabled(boolean isClassPrefixEnabled) {
            mIsClassPrefixEnabled = isClassPrefixEnabled;
            return this;
        }

        /**
         * Enables/disables location method name in message prefix. If not called by default this
         * option is enabled.
         *
         * @param isMethodPrefixEnabled true if logging message prefix should contain method name of
         *                              logging method call location, otherwise false.
         * @return This builder instance.
         */
        public Builder methodPrefixEnabled(boolean isMethodPrefixEnabled) {
            mIsMethodPrefixEnabled = isMethodPrefixEnabled;
            return this;
        }

        /**
         * Enables/disables logging call location in message prefix. If not called by default this
         * option is enabled.
         *
         * @param isLineLocationPrefixEnabled true if logging message prefix should contain
         *                                    logging method call location, otherwise false.
         * @return This builder instance.
         */
        public Builder lineLocationPrefixEnabled(boolean isLineLocationPrefixEnabled) {
            mIsLineLocationPrefixEnabled = isLineLocationPrefixEnabled;
            return this;
        }

        /**
         * Sets the count of stack trace lines to output with logging message. If not called by
         * default this option is set to 0.
         *
         * @param stackTraceLineCount Count of stack trace lines to output with logging message.
         * @return This builder instance.
         */
        public Builder stackTraceLineCount(int stackTraceLineCount) {
            mStackTraceLineCount = Math.max(stackTraceLineCount, 0);
            return this;
        }

        /**
         * Sets the count of indent space count for {@link ALog#json(ALogLevel, String)} and
         * {@link ALog#json(String)} log messages.
         *
         * @param jsonIndentSpaceCount Indent space count for {@link ALog#json(ALogLevel, String)}
         *                             and {@link ALog#json(String)} log messages.
         * @return This builder instance.
         */
        public Builder jsonIndentSpaceCount(int jsonIndentSpaceCount) {
            mJsonIndentSpaceCount = Math.max(jsonIndentSpaceCount, 0);
            return this;
        }

        /**
         * Sets the count of indent space count for {@link ALog#xml(ALogLevel, String)} and
         * {@link ALog#xml(String)} log messages.
         *
         * @param xmlIndentSpaceCount Indent space count for {@link ALog#xml(ALogLevel, String)}
         *                             and {@link ALog#xml(String)} log messages.
         * @return This builder instance.
         */
        public Builder xmlIndentSpaceCount(int xmlIndentSpaceCount) {
            mXmlIndentSpaceCount = Math.max(xmlIndentSpaceCount, 0);
            return this;
        }

        /**
         * Builds new {@link ALogConfiguration} instance with set to this builder instance
         * parameters.
         *
         * @return New {@link ALogConfiguration} instance.
         */
        public ALogConfiguration build() {
            return new ALogConfiguration(mIsEnabled, mMinimalLevel, mJsonLevel, mXmlLevel, mTag,
                    mIsThreadPrefixEnabled, mIsClassPrefixEnabled, mIsMethodPrefixEnabled,
                    mIsLineLocationPrefixEnabled, mStackTraceLineCount, mJsonIndentSpaceCount,
                    mXmlIndentSpaceCount);
        }
    }
}
