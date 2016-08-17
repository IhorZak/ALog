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

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import ua.pp.ihorzak.alog.ALog;
import ua.pp.ihorzak.alog.ALogConfiguration;
import ua.pp.ihorzak.alog.ALogger;
import ua.pp.ihorzak.alog.base.BaseTest;

import static ua.pp.ihorzak.alog.base.Utils.assertLog;

/**
 * {@link ALog} unit tests.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 * @since 12.08.2016
 */
public class ALogTest extends BaseTest {
    private static final String TAG = "Test";

    @Before
    public void setUp() {
        ALogConfiguration configuration = ALogConfiguration.builder()
                .tag(TAG)
                .threadPrefixEnabled(false)
                .methodPrefixEnabled(false)
                .lineLocationPrefixEnabled(false)
                .build();
        ALog.initialize(configuration);
    }

    @Test
    public void testV() {
        String message = "Message";
        ALog.v(message);
        assertLog(Log.VERBOSE, TAG, message);

        String messageFormat = "Message %s, %d";
        String stringArg = "title";
        int intArg = 100;
        String formattedMessage = "Message title, 100";
        ALog.v(messageFormat, stringArg, intArg);
        assertLog(Log.VERBOSE, TAG, formattedMessage);

        Throwable throwable = new RuntimeException();
        ALog.v(throwable);
        assertLog(Log.VERBOSE, TAG, Log.getStackTraceString(throwable));

        ALog.v(throwable, messageFormat, stringArg, intArg);
        assertLog(Log.VERBOSE, TAG, formattedMessage + '\n' + Log.getStackTraceString(throwable));
    }

    @Test
    public void testD() {
        String message = "Message";
        ALog.d(message);
        assertLog(Log.DEBUG, TAG, message);

        String messageFormat = "Message %s, %d";
        String stringArg = "title";
        int intArg = 100;
        String formattedMessage = "Message title, 100";
        ALog.d(messageFormat, stringArg, intArg);
        assertLog(Log.DEBUG, TAG, formattedMessage);

        Throwable throwable = new RuntimeException();
        ALog.d(throwable);
        assertLog(Log.DEBUG, TAG, Log.getStackTraceString(throwable));

        ALog.d(throwable, messageFormat, stringArg, intArg);
        assertLog(Log.DEBUG, TAG, formattedMessage + '\n' + Log.getStackTraceString(throwable));
    }

    @Test
    public void testI() {
        String message = "Message";
        ALog.i(message);
        assertLog(Log.INFO, TAG, message);

        String messageFormat = "Message %s, %d";
        String stringArg = "title";
        int intArg = 100;
        String formattedMessage = "Message title, 100";
        ALog.i(messageFormat, stringArg, intArg);
        assertLog(Log.INFO, TAG, formattedMessage);

        Throwable throwable = new RuntimeException();
        ALog.i(throwable);
        assertLog(Log.INFO, TAG, Log.getStackTraceString(throwable));

        ALog.i(throwable, messageFormat, stringArg, intArg);
        assertLog(Log.INFO, TAG, formattedMessage + '\n' + Log.getStackTraceString(throwable));
    }

    @Test
    public void testW() {
        String message = "Message";
        ALog.w(message);
        assertLog(Log.WARN, TAG, message);

        String messageFormat = "Message %s, %d";
        String stringArg = "title";
        int intArg = 100;
        String formattedMessage = "Message title, 100";
        ALog.w(messageFormat, stringArg, intArg);
        assertLog(Log.WARN, TAG, formattedMessage);

        Throwable throwable = new RuntimeException();
        ALog.w(throwable);
        assertLog(Log.WARN, TAG, Log.getStackTraceString(throwable));

        ALog.w(throwable, messageFormat, stringArg, intArg);
        assertLog(Log.WARN, TAG, formattedMessage + '\n' + Log.getStackTraceString(throwable));
    }

    @Test
    public void testE() {
        String message = "Message";
        ALog.e(message);
        assertLog(Log.ERROR, TAG, message);

        String messageFormat = "Message %s, %d";
        String stringArg = "title";
        int intArg = 100;
        String formattedMessage = "Message title, 100";
        ALog.e(messageFormat, stringArg, intArg);
        assertLog(Log.ERROR, TAG, formattedMessage);

        Throwable throwable = new RuntimeException();
        ALog.e(throwable);
        assertLog(Log.ERROR, TAG, Log.getStackTraceString(throwable));

        ALog.e(throwable, messageFormat, stringArg, intArg);
        assertLog(Log.ERROR, TAG, formattedMessage + '\n' + Log.getStackTraceString(throwable));
    }

    @Test
    public void testWtf() {
        String message = "Message";
        ALog.wtf(message);
        assertLog(Log.ASSERT, TAG, message);

        String messageFormat = "Message %s, %d";
        String stringArg = "title";
        int intArg = 100;
        String formattedMessage = "Message title, 100";
        ALog.wtf(messageFormat, stringArg, intArg);
        assertLog(Log.ASSERT, TAG, formattedMessage);

        Throwable throwable = new RuntimeException();
        ALog.wtf(throwable);
        assertLog(Log.ASSERT, TAG, Log.getStackTraceString(throwable));

        ALog.wtf(throwable, messageFormat, stringArg, intArg);
        assertLog(Log.ASSERT, TAG, formattedMessage + '\n' + Log.getStackTraceString(throwable));
    }

    @Test
    public void testT() {
        String customTag = "Custom";
        String message = "Message";

        ALogger customTagLogger = ALog.t(customTag);
        customTagLogger.v(message);
        assertLog(null, customTag, null);
        customTagLogger.d(message);
        assertLog(null, customTag, null);
        customTagLogger.i(message);
        assertLog(null, customTag, null);
        customTagLogger.w(message);
        assertLog(null, customTag, null);
        customTagLogger.e(message);
        assertLog(null, customTag, null);
        customTagLogger.wtf(message);
        assertLog(null, customTag, null);

        ALogger autoTagLogger = ALog.t(null);
        autoTagLogger.v(message);
        assertLog(null, getClass().getSimpleName(), null);
        autoTagLogger.d(message);
        assertLog(null, getClass().getSimpleName(), null);
        autoTagLogger.i(message);
        assertLog(null, getClass().getSimpleName(), null);
        autoTagLogger.w(message);
        assertLog(null, getClass().getSimpleName(), null);
        autoTagLogger.e(message);
        assertLog(null, getClass().getSimpleName(), null);
        autoTagLogger.wtf(message);
        assertLog(null, getClass().getSimpleName(), null);
    }
}
