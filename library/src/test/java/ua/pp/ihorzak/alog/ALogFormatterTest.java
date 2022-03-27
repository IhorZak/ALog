/*
 * Copyright 2017 Ihor Zakhozhyi
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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ua.pp.ihorzak.alog.test.BaseTest;

/**
 * {@link ALogFormatter} unit tests.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
public class ALogFormatterTest extends BaseTest {
    @Test
    public void testCreate_ALogFormatterDelegate() {
        final String loggingString = "Custom logging string";
        ALogFormatterDelegate<String> delegate = object -> loggingString;
        ALogFormatter<String> formatter = ALogFormatter.create(delegate);
        ALogFormatter<Object> objectFormatter = new ObjectALogFormatter(ALogConfiguration.builder().build());
        assertEquals(loggingString, formatter.format("Some logging message", objectFormatter));
    }

    @Test
    public void testCreate_ALogFormatterComplexDelegate() {
        final String loggingString = "Custom logging string";
        ALogFormatterComplexDelegate<String> complexDelegate = (object, objectFormatter) -> loggingString;
        ALogFormatter<String> formatter = ALogFormatter.create(complexDelegate);
        ALogFormatter<Object> objectFormatter = new ObjectALogFormatter(ALogConfiguration.builder().build());
        assertEquals(loggingString, formatter.format("Some logging message", objectFormatter));
    }
}
