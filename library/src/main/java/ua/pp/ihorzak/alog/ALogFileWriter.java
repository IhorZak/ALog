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

import java.io.FileNotFoundException;

/**
 * Interface to write logging messages output to file.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
interface ALogFileWriter {
    /**
     * Writes specified message to logging file.
     *
     * @param message Message to be written.
     * @throws FileNotFoundException If file to write to doesn't exist and cannot be created.
     */
    void write(CharSequence message) throws FileNotFoundException;
}
