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

package ua.pp.ihorzak.alog.sample;

import android.app.Application;

import ua.pp.ihorzak.alog.ALog;
import ua.pp.ihorzak.alog.ALogConfiguration;
import ua.pp.ihorzak.alog.ALogFileConfiguration;

/**
 * ALog sample application.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class ALogSampleApplication extends Application {
    private static final long LOG_FILE_LIFETIME = 3600L;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize ALog
        ALogFileManager aLogFileManager = new ALogFileManager(this, LOG_FILE_LIFETIME);
        aLogFileManager.deleteObsoleteFiles();
        ALogConfiguration aLogConfiguration = ALogConfiguration.builder()
                .file(ALogFileConfiguration.single(aLogFileManager.createFile().getPath(), false))
                .tag("ALogSampleApplication")
                .classPrefixEnabled(true)
                .jsonIndentSpaceCount(4)
                .xmlIndentSpaceCount(4)
                .build();
        ALog.initialize(aLogConfiguration);
    }
}
