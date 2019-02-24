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

package ua.pp.ihorzak.alog.test;

import org.robolectric.shadows.ShadowLog;

import java.util.List;

import ua.pp.ihorzak.alog.ALogger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests util methods.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
public final class Utils {
    private Utils() {}

    /**
     * Asserts last logged message and checks equality of expected message data and actual message
     * data.
     *
     * @param priority Expected priority of last logged message or null to ignore priority check.
     * @param tag Expected tag of last logged message or null to ignore tag check.
     * @param message Expected message data of last logged message or null to ignore message data check.
     */
    public static void assertLogEquals(Integer priority, String tag, String message) {
        ShadowLog.LogItem logItem = getLastLogItem(priority, tag);
        if (message != null) {
            assertEquals(message, logItem.msg);
        }
    }

    /**
     * Asserts last logged message and checks equality of expected message data prefix and actual
     * message data prefix.
     *
     * @param priority Expected priority of last logged message or null to ignore priority check.
     * @param tag Expected tag of last logged message or null to ignore tag check.
     * @param prefix Expected message data prefix of last logged message or null to ignore message
     *               data prefix check.
     */
    public static void assertLogStartsWith(Integer priority, String tag, String prefix) {
        ShadowLog.LogItem logItem = getLastLogItem(priority, tag);
        if (prefix != null) {
            assertNotNull(logItem.msg);
            assertTrue(logItem.msg.startsWith(prefix));
        }
    }

    /**
     * Asserts last logged message and checks equality of expected message data suffix and actual
     * message data suffix.
     *
     * @param priority Expected priority of last logged message or null to ignore priority check.
     * @param tag Expected tag of last logged message or null to ignore tag check.
     * @param suffix Expected message data suffix of last logged message or null to ignore message
     *               data suffix check.
     */
    public static void assertLogEndsWith(Integer priority, String tag, String suffix) {
        ShadowLog.LogItem logItem = getLastLogItem(priority, tag);
        if (suffix != null) {
            assertNotNull(logItem.msg);
            assertTrue(logItem.msg.endsWith(suffix));
        }
    }

    /**
     * Helper method to call correspondent ALog method from package different from ua.pp.ihorzak.alog.
     *
     * @param logger {@link ALogger} instance.
     * @param throwable Throwable or null.
     * @param message Message or null.
     * @param args Message arguments if needed.
     */
    public static void v(ALogger logger, Throwable throwable, String message, Object... args) {
        logger.v(throwable, message, args);
    }

    /**
     * Helper method to call correspondent ALog method from package different from ua.pp.ihorzak.alog.
     *
     * @param logger {@link ALogger} instance.
     * @param throwable Throwable or null.
     * @param message Message or null.
     * @param args Message arguments if needed.
     */
    public static void d(ALogger logger, Throwable throwable, String message, Object... args) {
        logger.d(throwable, message, args);
    }

    /**
     * Helper method to call correspondent ALog method from package different from ua.pp.ihorzak.alog.
     *
     * @param logger {@link ALogger} instance.
     * @param throwable Throwable or null.
     * @param message Message or null.
     * @param args Message arguments if needed.
     */
    public static void i(ALogger logger, Throwable throwable, String message, Object... args) {
        logger.i(throwable, message, args);
    }

    /**
     * Helper method to call correspondent ALog method from package different from ua.pp.ihorzak.alog.
     *
     * @param logger {@link ALogger} instance.
     * @param throwable Throwable or null.
     * @param message Message or null.
     * @param args Message arguments if needed.
     */
    public static void w(ALogger logger, Throwable throwable, String message, Object... args) {
        logger.w(throwable, message, args);
    }

    /**
     * Helper method to call correspondent ALog method from package different from ua.pp.ihorzak.alog.
     *
     * @param logger {@link ALogger} instance.
     * @param throwable Throwable or null.
     * @param message Message or null.
     * @param args Message arguments if needed.
     */
    public static void e(ALogger logger, Throwable throwable, String message, Object... args) {
        logger.e(throwable, message, args);
    }

    /**
     * Helper method to call correspondent ALog method from package different from ua.pp.ihorzak.alog.
     *
     * @param logger {@link ALogger} instance.
     * @param throwable Throwable or null.
     * @param message Message or null.
     * @param args Message arguments if needed.
     */
    public static void wtf(ALogger logger, Throwable throwable, String message, Object... args) {
        logger.wtf(throwable, message, args);
    }

    private static ShadowLog.LogItem getLastLogItem(Integer priority, String tag) {
        List<ShadowLog.LogItem> logItemList = ShadowLog.getLogs();
        ShadowLog.LogItem logItem = logItemList.get(logItemList.size() - 1);
        if (priority != null) {
            assertEquals((int) priority, logItem.type);
        }
        if (tag != null) {
            assertEquals(tag, logItem.tag);
        }
        return logItem;
    }
}
