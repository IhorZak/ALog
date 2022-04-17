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

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * {@link ALogPrinter} implementation that uses file to print logging messages.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
final class FileALogPrinter implements ALogPrinter {
    private static final String THREAD_NAME = "FileALogPrinter";
    private static final String FORMAT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    private final String mFilePath;
    private final boolean mAppend;
    private final Executor mExecutor;
    private final DateFormat mDateFormat;

    private PrintWriter mPrintWriter;
    private boolean mHasInitializationFailed;

    /**
     * Constructor.
     *
     * @param filePath Path of the file to print logging messages into.
     * @param append True if file should be appended in case it exists, false if file content
     *               should be overwritten in case it exists.
     */
    @SuppressLint("SimpleDateFormat")
    FileALogPrinter(String filePath, boolean append) {
        mFilePath = filePath;
        mAppend = append;
        mExecutor = Executors.newSingleThreadExecutor(runnable -> new Thread(runnable, THREAD_NAME));
        mDateFormat = new SimpleDateFormat(FORMAT_DATE_TIME_PATTERN);
        mHasInitializationFailed = false;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void print(ALogLevel level, CharSequence tag, CharSequence message) {
        if (!mHasInitializationFailed) {
            mExecutor.execute(
                    () -> {
                        if (mPrintWriter == null) {
                            try {
                                File file = new File(mFilePath);
                                File directoryFile = file.getParentFile();
                                if (directoryFile != null) {
                                    directoryFile.mkdirs();
                                }
                                mPrintWriter = new PrintWriter(new FileOutputStream(file, mAppend));
                            } catch (Throwable throwable) {
                                mHasInitializationFailed = true;
                                throwable.printStackTrace();
                            }
                        }
                        if (mPrintWriter != null) {
                            mPrintWriter.append(mDateFormat.format(new Date()))
                                        .append(' ')
                                        .append(level.getLabel())
                                        .append(' ')
                                        .append(tag)
                                        .append(' ')
                                        .append(message)
                                        .append('\n')
                                        .flush();
                        }
                    }
            );
        }
    }
}
