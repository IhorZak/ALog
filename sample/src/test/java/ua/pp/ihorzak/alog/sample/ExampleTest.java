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

package ua.pp.ihorzak.alog.sample;

import org.junit.Assert;
import org.junit.Test;

import ua.pp.ihorzak.alog.ALog;

/**
 * JVM unit test which contains ALog call example.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 */
public class ExampleTest {
    @Test
    public void testAddition_2and2_shouldBe4() {
        ALog.d("ALog call in JVM unit test");
        Assert.assertEquals(4, 2 + 2);
    }
}
