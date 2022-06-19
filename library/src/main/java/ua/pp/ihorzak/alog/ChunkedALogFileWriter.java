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
 * Logging file writer which performs logging to to the multiple (chunk)
 * files with file count and file size limits. In case file size limit exceeds new file (chunk)
 * is created. In case file size limit exceeds the most old file is deleted.
 * In case specified chunk files directory contains files they will be deleted.
 * If logging message exceeds chunk size limit it will be ignored.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
final class ChunkedALogFileWriter implements ALogFileWriter {
    private final long mChunkSizeLimit;

    private final File mDirectoryFile;
    private final File[] mChunkFiles;

    private PrintWriter mPrintWriter;
    private int mCurrentChunk;
    private long mWritten;

    /**
     * Constructor.
     *
     * @param filesDirectoryPath Logging output files directory.
     * @param chunkSizeLimit Chunk file size limit (in chars). Must be positive number.
     * @param chunkCount Chunk file count limit. Must be positive number.
     * @param nameProvider Chunk file name provider.
     */
    ChunkedALogFileWriter(String filesDirectoryPath,
                          long chunkSizeLimit,
                          int chunkCount,
                          ALogChunkFileNameProvider nameProvider) {
        if (chunkSizeLimit <= 0) {
            throw new IllegalArgumentException("Chunk size limit must be positive number");
        }
        if (chunkCount <= 0) {
            throw new IllegalArgumentException("Chunk count must be positive number");
        }
        mChunkSizeLimit = chunkSizeLimit;
        mDirectoryFile = new File(filesDirectoryPath);
        mChunkFiles = new File[chunkCount];
        for (int i = 0; i < chunkCount; ++i) {
            mChunkFiles[i] = new File(mDirectoryFile, nameProvider.getName(i));
        }
    }

    @Override
    public void write(CharSequence message) throws FileNotFoundException {
        int messageLength = message.length();
        if (messageLength > mChunkSizeLimit) {
            return;
        }
        if (mPrintWriter == null) {
            initialize();
        } else {
            if ((mWritten + messageLength) > mChunkSizeLimit) {
                openNextChunk();
            }
        }
        mPrintWriter.append(message)
                    .flush();
        mWritten += message.length();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void initialize() throws FileNotFoundException {
        if (!mDirectoryFile.mkdirs()) {
            File[] files = mDirectoryFile.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
        mCurrentChunk = 0;
        openCurrentChunk();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void openNextChunk() throws FileNotFoundException {
        mPrintWriter.close();
        if (mCurrentChunk < mChunkFiles.length - 1) {
            ++mCurrentChunk;
        } else {
            mChunkFiles[0].delete();
            for (int i = 1; i < mChunkFiles.length; ++i) {
                mChunkFiles[i].renameTo(mChunkFiles[i - 1]);
            }
        }
        openCurrentChunk();
    }

    private void openCurrentChunk() throws FileNotFoundException {
        mPrintWriter = new PrintWriter(new FileOutputStream(mChunkFiles[mCurrentChunk], false));
        mWritten = 0L;
    }
}
