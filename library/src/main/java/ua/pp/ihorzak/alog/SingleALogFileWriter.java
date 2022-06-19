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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Logging file writer which performs logging to a single file.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
final class SingleALogFileWriter implements ALogFileWriter {
    private final String mFilePath;
    private final boolean mAppend;

    private PrintWriter mPrintWriter;

    /**
     * Constructor.
     *
     * @param filePath Logging output file path.
     * @param append Flag which indicates behavior in case output file already exists: true if
     *               its content should be appended, false if its content should be overwritten.
     */
    SingleALogFileWriter(String filePath, boolean append) {
        mFilePath = filePath;
        mAppend = append;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void write(CharSequence message) throws FileNotFoundException {
        if (mPrintWriter == null) {
            File file = new File(mFilePath);
            File directoryFile = file.getParentFile();
            if (directoryFile != null) {
                directoryFile.mkdirs();
            }
            mPrintWriter = new PrintWriter(new FileOutputStream(file, mAppend));
        }
        mPrintWriter.append(message)
                    .flush();
    }
}
