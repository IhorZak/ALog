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

import android.util.Log;

/**
 * {@link ALogPrinter} implementation that uses {@link Log} to print logging messages.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
final class AndroidLogALogPrinter implements ALogPrinter {
    private static final int MAX_TAG_LENGTH = 23;
    private static final int MAX_ANDROID_LOG_MESSAGE_LENGTH = 2038;

    @Override
    public void print(ALogLevel level, CharSequence tag, CharSequence message) {
        String tagString = null;
        if (tag != null) {
            if (tag.length() > MAX_TAG_LENGTH) {
                tagString = tag.subSequence(0, MAX_TAG_LENGTH - 1).toString() + '\u2026';
            } else {
                tagString = tag.toString();
            }
        }
        int androidPriority = level.getAndroidPriority();
        int offset = 0;
        do {
            int subMessageEnd = Math.min(message.length(), offset + MAX_ANDROID_LOG_MESSAGE_LENGTH);
            Log.println(androidPriority, tagString, message.subSequence(offset, subMessageEnd).toString());
            offset += subMessageEnd;
        } while (offset < message.length());
    }
}
