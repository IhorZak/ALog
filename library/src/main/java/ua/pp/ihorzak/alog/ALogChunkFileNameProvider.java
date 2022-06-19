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
 * Represents provider of names for chunked {@link ALogFileConfiguration}.
 * Instances should be passed to
 * {@link ALogFileConfiguration#chunked(String, long, int, ALogChunkFileNameProvider)}.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
public interface ALogChunkFileNameProvider {
    /**
     * Retrieves the name for the chunk file with provided index (zero based).
     *
     * @param chunkIndex The index (zero based) of chunk file to provide name.
     * @return The name for chunk file with provided index.
     */
    String getName(int chunkIndex);
}
