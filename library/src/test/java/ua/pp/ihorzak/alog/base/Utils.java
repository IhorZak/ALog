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

package ua.pp.ihorzak.alog.base;

import org.robolectric.shadows.ShadowLog;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests util methods.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 * @since 17.08.16
 */
public class Utils {
    /**
     * Asserts last logged message.
     *
     * @param priority Expected priority of last logged message or null to ignore priority check.
     * @param tag Expected tag of last logged message or null to ignore tag check.
     * @param message Expected message data of last logged message or null to ignore message data check.
     */
    public static void assertLog(Integer priority, String tag, String message) {
        List<ShadowLog.LogItem> logItemList = ShadowLog.getLogs();
        ShadowLog.LogItem logItem = logItemList.get(logItemList.size() - 1);
        if (priority != null) {
            assertEquals((int) priority, logItem.type);
        }
        if (tag != null) {
            assertEquals(tag, logItem.tag);
        }
        if (message != null) {
            assertEquals(message, logItem.msg);
        }
    }
}
