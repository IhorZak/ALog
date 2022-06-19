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

/**
 * Represents configuration for {@link ALog} file output.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
public final class ALogFileConfiguration {
    final ALogFileWriter mWriter;

    private ALogFileConfiguration(ALogFileWriter writer) {
        mWriter = writer;
    }

    /**
     * Creates {@link ALogFileConfiguration} to perform logging output to the single file.
     * Created instance must be passed to
     * {@link ALogConfiguration.Builder#file(ALogFileConfiguration)}.
     *
     * @param filePath Logging output file path.
     * @param append Flag which indicates behavior in case output file already exists: true if
     *               its content should be appended, false if its content should be overwritten.
     * @return {@link ALogFileConfiguration} to perform logging output to the single file.
     */
    public static ALogFileConfiguration single(String filePath, boolean append) {
        return new ALogFileConfiguration(new SingleALogFileWriter(filePath, append));
    }

    /**
     * Creates {@link ALogFileConfiguration} to perform logging output to the multiple (chunk)
     * files with file count and file size limits. In case file size limit exceeds new file (chunk)
     * is created. In case file size limit exceeds the most old file is deleted.
     * Created instance must be passed to
     * {@link ALogConfiguration.Builder#file(ALogFileConfiguration)}.
     *
     * @param filesDirectoryPath Logging output files directory.
     * @param chunkSizeLimit Chunk file size limit.
     * @param chunkCount Chunk file count limit.
     * @param nameProvider Chunk file name provider.
     * @return {@link ALogFileConfiguration} to perform logging output to the multiple (chunk)
     *         files.
     */
    public static ALogFileConfiguration chunked(String filesDirectoryPath,
                                                long chunkSizeLimit,
                                                int chunkCount,
                                                ALogChunkFileNameProvider nameProvider) {
        return new ALogFileConfiguration(
                new ChunkedALogFileWriter(
                        filesDirectoryPath,
                        chunkSizeLimit,
                        chunkCount,
                        nameProvider
                )
        );
    }
}
