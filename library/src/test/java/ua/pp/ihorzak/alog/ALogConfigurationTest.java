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

import android.os.Bundle;

import org.junit.Test;

import java.util.Collection;
import java.util.Map;

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
 */
public class ALogConfigurationTest extends BaseTest {
    @Test
    public void testBuilder() {
        ALogConfiguration.Builder builder = ALogConfiguration.builder();
        assertNotNull(builder);
    }

    @Test
    public void testBuilder_Enabled() {
        ALogConfiguration.Builder builder = ALogConfiguration.builder();
        assertTrue(builder.build().mIsEnabled);
        builder.enabled(false);
        assertFalse(builder.build().mIsEnabled);
        builder.enabled(true);
        assertTrue(builder.build().mIsEnabled);
    }

    @Test
    public void testBuilder_MinimalLevel() {
        ALogConfiguration.Builder builder = ALogConfiguration.builder();
        assertEquals(ALogLevel.VERBOSE, builder.build().mMinimalLevel);
        builder.minimalLevel(ALogLevel.WTF);
        assertEquals(ALogLevel.WTF, builder.build().mMinimalLevel);
        builder.minimalLevel(ALogLevel.DEBUG);
        assertEquals(ALogLevel.DEBUG, builder.build().mMinimalLevel);
    }

    @Test
    public void testBuilder_Tag() {
        ALogConfiguration.Builder builder = ALogConfiguration.builder();
        assertNull(builder.build().mTag);
        String customTag = "Custom";
        builder.tag(customTag);
        assertEquals(customTag, builder.build().mTag);
        builder.tag(null);
        assertNull(builder.build().mTag);
    }

    @Test
    public void testBuilder_ThreadPrefixEnabled() {
        ALogConfiguration.Builder builder = ALogConfiguration.builder();
        assertTrue(builder.build().mIsThreadPrefixEnabled);
        builder.threadPrefixEnabled(false);
        assertFalse(builder.build().mIsThreadPrefixEnabled);
        builder.threadPrefixEnabled(true);
        assertTrue(builder.build().mIsThreadPrefixEnabled);
    }

    @Test
    public void testBuilder_ClassPrefixEnabled() {
        ALogConfiguration.Builder builder = ALogConfiguration.builder();
        assertFalse(builder.build().mIsClassPrefixEnabled);
        builder.classPrefixEnabled(true);
        assertTrue(builder.build().mIsClassPrefixEnabled);
        builder.classPrefixEnabled(false);
        assertFalse(builder.build().mIsClassPrefixEnabled);
    }

    @Test
    public void testBuilder_MethodPrefixEnabled() {
        ALogConfiguration.Builder builder = ALogConfiguration.builder();
        assertTrue(builder.build().mIsMethodPrefixEnabled);
        builder.methodPrefixEnabled(false);
        assertFalse(builder.build().mIsMethodPrefixEnabled);
        builder.methodPrefixEnabled(true);
        assertTrue(builder.build().mIsMethodPrefixEnabled);
    }

    @Test
    public void testBuilder_LineLocationPrefixEnabled() {
        ALogConfiguration.Builder builder = ALogConfiguration.builder();
        assertTrue(builder.build().mIsLineLocationPrefixEnabled);
        builder.lineLocationPrefixEnabled(false);
        assertFalse(builder.build().mIsLineLocationPrefixEnabled);
        builder.lineLocationPrefixEnabled(true);
        assertTrue(builder.build().mIsLineLocationPrefixEnabled);
    }

    @Test
    public void testBuilder_StackTraceLineCount() {
        ALogConfiguration.Builder builder = ALogConfiguration.builder();
        assertEquals(0, builder.build().mStackTraceLineCount);
        builder.stackTraceLineCount(5);
        assertEquals(5, builder.build().mStackTraceLineCount);
        builder.stackTraceLineCount(-1);
        assertEquals(0, builder.build().mStackTraceLineCount);
        builder.stackTraceLineCount(2);
        assertEquals(2, builder.build().mStackTraceLineCount);
        builder.stackTraceLineCount(0);
        assertEquals(0, builder.build().mStackTraceLineCount);
    }

    @Test
    public void testBuilder_JsonLevel() {
        ALogConfiguration.Builder builder = ALogConfiguration.builder();
        assertEquals(ALogLevel.INFO, builder.build().mJsonLevel);
        builder.jsonLevel(ALogLevel.WTF);
        assertEquals(ALogLevel.WTF, builder.build().mJsonLevel);
        builder.jsonLevel(ALogLevel.VERBOSE);
        assertEquals(ALogLevel.VERBOSE, builder.build().mJsonLevel);
    }

    @Test
    public void testBuilder_XmlLevel() {
        ALogConfiguration.Builder builder = ALogConfiguration.builder();
        assertEquals(ALogLevel.INFO, builder.build().mXmlLevel);
        builder.xmlLevel(ALogLevel.ERROR);
        assertEquals(ALogLevel.ERROR, builder.build().mXmlLevel);
        builder.xmlLevel(ALogLevel.DEBUG);
        assertEquals(ALogLevel.DEBUG, builder.build().mXmlLevel);
    }

    @Test
    public void testBuilder_ArrayFormatterEnabled() {
        ALogConfiguration.Builder builder = ALogConfiguration.builder();
        ALogConfiguration defaultConfiguration = builder.build();
        assertNotNull(defaultConfiguration.mArrayFormatter);
        assertTrue(defaultConfiguration.mArrayFormatter instanceof ArrayALogFormatter);
        builder.arrayFormatterEnabled(false);
        assertNull(builder.build().mArrayFormatter);
        builder.arrayFormatterEnabled(true);
        ALogConfiguration enabledConfiguration = builder.build();
        assertNotNull(enabledConfiguration.mArrayFormatter);
        assertTrue(enabledConfiguration.mArrayFormatter instanceof ArrayALogFormatter);
    }

    @Test
    public void testBuilder_CollectionFormatterEnabled() {
        ALogConfiguration.Builder builder = ALogConfiguration.builder();
        ALogConfiguration defaultConfiguration = builder.build();
        assertNotNull(defaultConfiguration.mCollectionFormatter);
        assertTrue(defaultConfiguration.mCollectionFormatter instanceof CollectionALogFormatter);
        builder.collectionFormatterEnabled(false);
        assertNull(builder.build().mCollectionFormatter);
        builder.collectionFormatterEnabled(true);
        ALogConfiguration enabledConfiguration = builder.build();
        assertNotNull(enabledConfiguration.mCollectionFormatter);
        assertTrue(enabledConfiguration.mCollectionFormatter instanceof CollectionALogFormatter);
    }

    @Test
    public void testBuilder_MapFormatterEnabled() {
        ALogConfiguration.Builder builder = ALogConfiguration.builder();
        ALogConfiguration defaultConfiguration = builder.build();
        assertNotNull(defaultConfiguration.mMapFormatter);
        assertTrue(defaultConfiguration.mMapFormatter instanceof MapALogFormatter);
        builder.mapFormatterEnabled(false);
        assertNull(builder.build().mMapFormatter);
        builder.mapFormatterEnabled(true);
        ALogConfiguration enabledConfiguration = builder.build();
        assertNotNull(enabledConfiguration.mMapFormatter);
        assertTrue(enabledConfiguration.mMapFormatter instanceof MapALogFormatter);
    }

    @Test
    public void testBuilder_FormatterBundle() {
        ALogConfiguration.Builder builder = ALogConfiguration.builder();
        ALogConfiguration defaultConfiguration = builder.build();
        assertNotNull(defaultConfiguration.mFormatterMap);
        assertTrue(defaultConfiguration.mFormatterMap.isEmpty());
        ALogFormatter<Bundle> formatter = ALogFormatter.create(bundle -> {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Bundle [ ");
            for (String key : bundle.keySet()) {
                stringBuilder.append(key).append(" ");
            }
            stringBuilder.append("]");
            return stringBuilder.toString();
        });
        builder.formatter(Bundle.class, formatter);
        ALogConfiguration configuration = builder.build();
        assertNotNull(configuration.mFormatterMap);
        assertEquals(1, configuration.mFormatterMap.size());
        assertTrue(configuration.mFormatterMap.containsKey(Bundle.class));
        assertEquals(formatter, configuration.mFormatterMap.get(Bundle.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuilder_FormatterPrimitive() {
        ALogConfiguration.Builder builder = ALogConfiguration.builder();
        ALogFormatter<Integer> formatter = ALogFormatter.create(String::valueOf);
        builder.formatter(int.class, formatter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuilder_FormatterPrimitiveWrapper() {
        ALogConfiguration.Builder builder = ALogConfiguration.builder();
        ALogFormatter<Character> formatter = ALogFormatter.create(String::valueOf);
        builder.formatter(Character.class, formatter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuilder_FormatterArray() {
        ALogConfiguration.Builder builder = ALogConfiguration.builder();
        ALogFormatter<Object[]> formatter = ALogFormatter.create(objects -> "Array");
        builder.formatter(Object[].class, formatter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuilder_FormatterCollection() {
        ALogConfiguration.Builder builder = ALogConfiguration.builder();
        ALogFormatter<Collection<Object>> formatter = ALogFormatter.create(collection -> "Collection");
        builder.formatter(Collection.class, formatter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuilder_FormatterMap() {
        ALogConfiguration.Builder builder = ALogConfiguration.builder();
        ALogFormatter<Map<String, Object>> formatter = ALogFormatter.create(map -> "Map");
        builder.formatter(Map.class, formatter);
    }
}
