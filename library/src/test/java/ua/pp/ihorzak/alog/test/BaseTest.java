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

import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Base unit tests class with configured robolectric.
 *
 * @author Ihor Zakhozhyi <ihorzak@gmail.com>
 * @since 17.08.16
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = ua.pp.ihorzak.alog.BuildConfig.class, sdk = 23)
public abstract class BaseTest {}
