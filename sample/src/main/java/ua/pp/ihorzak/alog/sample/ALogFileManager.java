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

package ua.pp.ihorzak.alog.sample;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Entity which is responsible for managing of {@link ua.pp.ihorzak.alog.ALog}
 * output files.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
public final class ALogFileManager {
    private static final String DIRECTORY_NAME = "log";
    private static final String FILE_NAME_DATE_TIME_PATTERN = "yyyyMMddHHmmssSSS";
    private static final String FILE_EXTENSION = ".log";

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat FILE_NAME_DATE_TIME_FORMAT = new SimpleDateFormat(FILE_NAME_DATE_TIME_PATTERN);

    private static String buildLogFileName() {
        return FILE_NAME_DATE_TIME_FORMAT.format(new Date()) + FILE_EXTENSION;
    }

    private final File mLogFileDirectory;
    private final long mLifetime;

    private final Executor mCleanUpExecutor;

    /**
     * Constructor.
     *
     * @param context {@link Context} to get parent directory of logs directory.
     * @param lifetime Duration of logging files lifetime in seconds.
     */
    public ALogFileManager(@NonNull Context context, long lifetime) {
        mLogFileDirectory = new File(context.getFilesDir(), DIRECTORY_NAME);
        mLifetime = lifetime;
        mCleanUpExecutor = Executors.newSingleThreadExecutor();
    }

    /**
     * Creates {@link File} instance for new logging file.
     *
     * @return {@link File} instance of new logging file.
     */
    @NonNull
    public File createFile() {
        return new File(mLogFileDirectory, buildLogFileName());
    }

    /**
     * Deletes logging files that are older than specified lifetime from
     * current time.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void deleteObsoleteFiles() {
        mCleanUpExecutor.execute(() -> {
            File[] files = mLogFileDirectory.listFiles();
            if (files != null) {
                long currentTimestamp = new Date().getTime();
                for (File file : files) {
                    try {
                        String fileName = file.getName();
                        int fileExtensionIndex = fileName.indexOf(FILE_EXTENSION);
                        String fileTimestampString;
                        if (fileExtensionIndex >= 0) {
                            fileTimestampString = fileName.substring(0, fileExtensionIndex);
                        } else {
                            fileTimestampString = fileName;
                        }
                        Date fileDate = FILE_NAME_DATE_TIME_FORMAT.parse(fileTimestampString);
                        long fileTimestamp = 0L;
                        if (fileDate != null) {
                            fileTimestamp = fileDate.getTime();
                        }
                        long fileLifetime = TimeUnit.MILLISECONDS.toSeconds(currentTimestamp - fileTimestamp);
                        if (fileLifetime >= mLifetime) {
                            file.delete();
                        }
                    } catch (ParseException e) {
                        file.delete();
                    }
                }
            }
        });
    }
}
