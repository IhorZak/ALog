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
 * Represents logging levels. Minimal value is the highest logging level, maximal value is the
 * lowest logging level.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
public enum ALogLevel {
    /**
     * "What a Terrible Failure" logging level, which corresponds to
     * {@link android.util.Log#ASSERT} Android logging level.
     */
    WTF {
        @Override
        protected char getLabel() {
            return LABEL_WTF;
        }

        @Override
        protected int getAndroidPriority() {
            return Log.ASSERT;
        }
    },
    /**
     * Error logging level, which corresponds to {@link android.util.Log#ERROR} Android logging
     * level.
     */
    ERROR {
        @Override
        protected char getLabel() {
            return LABEL_ERROR;
        }

        @Override
        protected int getAndroidPriority() {
            return Log.ERROR;
        }
    },
    /**
     * Warning logging level, which corresponds to {@link android.util.Log#WARN} Android logging
     * level.
     */
    WARNING {
        @Override
        protected char getLabel() {
            return LABEL_WARNING;
        }

        @Override
        protected int getAndroidPriority() {
            return Log.WARN;
        }
    },
    /**
     * Information logging level, which corresponds to {@link android.util.Log#INFO} Android
     * logging level.
     */
    INFO {
        @Override
        protected char getLabel() {
            return LABEL_INFO;
        }

        @Override
        protected int getAndroidPriority() {
            return Log.INFO;
        }
    },
    /**
     * Debug logging level, which corresponds to {@link android.util.Log#DEBUG} Android logging
     * level.
     */
    DEBUG {
        @Override
        protected char getLabel() {
            return LABEL_DEBUG;
        }

        @Override
        protected int getAndroidPriority() {
            return Log.DEBUG;
        }
    },
    /**
     * Verbose logging level, which corresponds to {@link android.util.Log#VERBOSE} Android logging
     * level.
     */
    VERBOSE {
        @Override
        protected char getLabel() {
            return LABEL_VERBOSE;
        }

        @Override
        protected int getAndroidPriority() {
            return Log.VERBOSE;
        }
    };

    private static final char LABEL_WTF = 'A';
    private static final char LABEL_ERROR = 'E';
    private static final char LABEL_WARNING = 'W';
    private static final char LABEL_INFO = 'I';
    private static final char LABEL_DEBUG = 'D';
    private static final char LABEL_VERBOSE = 'V';

    /**
     * Gets corresponding single letter label.
     *
     * @return Corresponding single letter label.
     */
    protected abstract char getLabel();

    /**
     * Gets corresponding Android {@link Log} logging level.
     *
     * @return Corresponding Android {@link Log} logging level.
     */
    protected abstract int getAndroidPriority();
}
