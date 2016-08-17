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

package ua.pp.ihorzak.alog;

import org.junit.Test;

import ua.pp.ihorzak.alog.test.BaseTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * {@link ALogConfiguration} unit tests.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 * @since 17.08.16
 */
public class ALogConfigurationTest extends BaseTest {
    @Test
    public void testBuilder() {
        ALogConfiguration.Builder builder = ALogConfiguration.builder();
        assertNotNull(builder);

        builder.enabled(true);
        assertTrue(builder.build().mIsEnabled);
        builder.enabled(false);
        assertFalse(builder.build().mIsEnabled);

        builder.minimalLevel(ALogLevel.WTF);
        assertEquals(ALogLevel.WTF, builder.build().mMinimalLevel);
        builder.minimalLevel(ALogLevel.DEBUG);
        assertEquals(ALogLevel.DEBUG, builder.build().mMinimalLevel);

        String customTag = "Custom";
        builder.tag(customTag);
        assertEquals(customTag, builder.build().mTag);
        builder.tag(null);
        assertNull(builder.build().mTag);

        builder.threadPrefixEnabled(true);
        assertTrue(builder.build().mIsThreadPrefixEnabled);
        builder.threadPrefixEnabled(false);
        assertFalse(builder.build().mIsThreadPrefixEnabled);

        builder.classPrefixEnabled(true);
        assertTrue(builder.build().mIsClassPrefixEnabled);
        builder.classPrefixEnabled(false);
        assertFalse(builder.build().mIsClassPrefixEnabled);

        builder.methodPrefixEnabled(true);
        assertTrue(builder.build().mIsMethodPrefixEnabled);
        builder.methodPrefixEnabled(false);
        assertFalse(builder.build().mIsMethodPrefixEnabled);

        builder.lineLocationPrefixEnabled(true);
        assertTrue(builder.build().mIsLineLocationPrefixEnabled);
        builder.lineLocationPrefixEnabled(false);
        assertFalse(builder.build().mIsLineLocationPrefixEnabled);

        builder.stackTraceLineCount(5);
        assertEquals(5, builder.build().mStackTraceLineCount);
        builder.stackTraceLineCount(-1);
        assertEquals(0, builder.build().mStackTraceLineCount);
        builder.stackTraceLineCount(2);
        assertEquals(2, builder.build().mStackTraceLineCount);
        builder.stackTraceLineCount(0);
        assertEquals(0, builder.build().mStackTraceLineCount);

        builder.jsonLevel(ALogLevel.WTF);
        assertEquals(ALogLevel.WTF, builder.build().mJsonLevel);
        builder.jsonLevel(ALogLevel.VERBOSE);
        assertEquals(ALogLevel.VERBOSE, builder.build().mJsonLevel);

        builder.xmlLevel(ALogLevel.ERROR);
        assertEquals(ALogLevel.ERROR, builder.build().mXmlLevel);
        builder.xmlLevel(ALogLevel.DEBUG);
        assertEquals(ALogLevel.DEBUG, builder.build().mXmlLevel);
    }
}
