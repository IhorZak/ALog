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

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * {@link ALogPrinter} implementation that uses file writer to print logging messages.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
final class FileALogPrinter implements ALogPrinter {
    private static final String THREAD_NAME = "FileALogPrinter";
    private static final String FORMAT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    private final ALogFileWriter mFileWriter;
    private final Executor mExecutor;
    private final DateFormat mDateFormat;

    private boolean mHasPrinterFailed;

    /**
     * Constructor.
     *
     * @param fileWriter Entity used to perform write operations to storage file.
     */
    @SuppressLint("SimpleDateFormat")
    FileALogPrinter(ALogFileWriter fileWriter) {
        mFileWriter = fileWriter;
        mExecutor = Executors.newSingleThreadExecutor(runnable -> new Thread(runnable, THREAD_NAME));
        mDateFormat = new SimpleDateFormat(FORMAT_DATE_TIME_PATTERN);
        mHasPrinterFailed = false;
    }

    @Override
    public void print(ALogLevel level, CharSequence tag, CharSequence message) {
        if (!mHasPrinterFailed) {
            mExecutor.execute(
                    () -> {
                        try {
                            StringBuilder builder = new StringBuilder();
                            builder.append(mDateFormat.format(new Date()))
                                   .append(' ')
                                   .append(level.getLabel())
                                   .append(' ')
                                   .append(tag)
                                   .append(' ')
                                   .append(message);
                            if (message.length() == 0 || message.charAt(message.length() - 1) != '\n') {
                                builder.append('\n');
                            }
                            mFileWriter.write(builder);
                        } catch (Throwable throwable) {
                            mHasPrinterFailed = true;
                            throwable.printStackTrace();
                        }
                    }
            );
        }
    }
}
