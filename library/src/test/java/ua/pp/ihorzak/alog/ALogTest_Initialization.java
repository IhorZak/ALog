/*
 * Copyright 2019 Ihor Zakhozhyi
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

import org.junit.Test;

import ua.pp.ihorzak.alog.test.BaseTest;

import static ua.pp.ihorzak.alog.test.Utils.assertLogEndsWith;
import static ua.pp.ihorzak.alog.test.Utils.assertLogEquals;

/**
 * {@link ALog} initialization unit tests.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
public class ALogTest_Initialization extends BaseTest {
    @Test
    public void testInitialization_DefaultConfiguration() {
        String tag = "ALogInitializationTest";
        String message = "ALog was initialized with default configuration";
        ALog.t(tag).d(message);
        assertLogEndsWith(Log.DEBUG, tag, message);
    }

    @Test
    public void testInitialization_CustomConfiguration() {
        String tag = "ALogInitializationTest";
        ALog.initialize(
                ALogConfiguration
                        .builder()
                        .tag(tag)
                        .threadPrefixEnabled(false)
                        .methodPrefixEnabled(false)
                        .lineLocationPrefixEnabled(false)
                        .build()
        );
        String message = "ALog was initialized with custom configuration";
        ALog.wtf(message);
        assertLogEquals(Log.ASSERT, tag, message);
    }
}
